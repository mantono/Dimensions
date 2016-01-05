package dimensions.client.engine.physics;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import dimensions.client.engine.GameSettings;
import dimensions.client.engine.spriteinterfaces.Collidable;

public class CollisionTable
{
	/**
	 * 
	 */
	private final static int[] PRIMES = {23, 53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593, 49157, 98317, 196613, 393241, 786433, 1572869, 3145739, 6291469, 12582917, 25165843, 50331653, 100663319, 201326611, 402653189, 805306457, 1610612741};

	private CollisionSet[] collidableArray;
	private volatile int arraySize = 11;
	private volatile int primeIndex = -1;
	private volatile int count = 0;
	private final Lock hashLock = new ReentrantLock();
	private final Condition hashFinished = hashLock.newCondition();
	private final BlockingQueue<Collidable> incoming = new ArrayBlockingQueue<Collidable>(300);

	public CollisionTable(final int initialApproximateArraySize)
	{
		setArraySize(initialApproximateArraySize);
		collidableArray = new CollisionSet[arraySize];
	}

	public CollisionTable()
	{
		collidableArray = new CollisionSet[arraySize];
	}

	public boolean add(Collidable collidable)
	{
		return incoming.offer(collidable);
	}

	synchronized void addFromQueue()
	{

		if(incoming.isEmpty())
			return;
		if(size() > arraySize * 5)
			expand();
		while(!incoming.isEmpty())
		{
			addToTable(new CollisionRecord(incoming.poll()), collidableArray);
			count++;
		}
	}

	private int size()
	{
		return count;
	}

	public Set<CollisionRecord> getCollidables(final Collidable collidable, final int hashRadius)
	{
		if(hashRadius < 0)
			throw new IllegalArgumentException("Parameter hashRange cannot be negative.");

		final Set<CollisionRecord> collisionCandidates = new HashSet<CollisionRecord>();

		hashLock.lock();
		waitForHash();

		final int index = hashIndex(collidable, collidableArray);
		for(int i = index - hashRadius; i <= index + hashRadius; i++)
		{
			if(i < 0)
				i = Math.abs(i);
			final Set<CollisionRecord> set = collidableArray[i % collidableArray.length];
			if(set != null)
				collisionCandidates.addAll(removeObsolete(set));
		}

		hashLock.unlock();

		return collisionCandidates;
	}

	private Set<CollisionRecord> removeObsolete(Set<CollisionRecord> collisionCandidates)
	{
		final Iterator<CollisionRecord> recordIterator = collisionCandidates.iterator();
		while(recordIterator.hasNext())
		{
			final CollisionRecord record = recordIterator.next();
			if(record.isObsolete(Physics.ONE_SECOND/5) || record.getCollidable().isReadyToRemove())
			{
				recordIterator.remove();
				count--;
				if(!record.getCollidable().isReadyToRemove()) 
					add(record.getCollidable());
			}
		}

		return collisionCandidates;
	}

	private boolean addToTable(CollisionRecord record, CollisionSet[] array)
	{
		try
		{
			hashLock.lock();
			final int index = hashIndex(record.getCollidable(), array);
			if(index >= array.length)
				throw new ConcurrentModificationException("Illegal state: Trying to insert in index " + index + " but size of array is " + array.length + "(" + arraySize + ")\n");
			if(array[index] == null)
				array[index] = new CollisionSet();
			return array[index].add(record);
		}
		finally
		{
			hashLock.unlock();
		}
	}

	private void expand()
	{
		hashLock.lock();
		advanceTotNextPrime();
		rehashTable();
		hashFinished.signalAll();
		hashLock.unlock();
	}

	private void shrink()
	{
		hashLock.lock();
		previousPrime();
		rehashTable();
		hashFinished.signalAll();
		hashLock.unlock();
	}

	private void waitForHash()
	{
		try
		{
			while(collidableArray.length != arraySize)
				hashFinished.await();
		}
		catch(InterruptedException e)
		{
			hashLock.unlock();
			e.printStackTrace();
		}
	}

	private void rehashTable()
	{
		final CollisionSet[] rehashedArray = new CollisionSet[arraySize];
		for(int i = 0; i < collidableArray.length; i++)
		{
			final CollisionSet set = collidableArray[i];
			if(set != null)
				for(CollisionRecord record : set)
					if(!record.getCollidable().isReadyToRemove())
						addToTable(record, rehashedArray);
		}

		collidableArray = rehashedArray;
	}

	private int hashIndex(Collidable collidable, Object[] array)
	{
		final int hash = computeHash(collidable.getScreenCoordinates());
		final int index = Math.abs(hash % array.length);
		return index;
	}

	private int computeHash(Coordinate2D screenCoordinates)
	{
		final double d1 = screenCoordinates.distance(0, 0);
		final double d2 = screenCoordinates.distance(0, GameSettings.heightWindow);
		final double d3 = screenCoordinates.distance(GameSettings.widthWindow, GameSettings.heightWindow);
		final double diff = d1 - d3;
		final double d2Squared = Math.sqrt(d2 + 1);

		final double result = diff / d2Squared;

		return (int) result;
	}

	private void advanceTotNextPrime()
	{
		arraySize = PRIMES[++primeIndex];
	}

	private void previousPrime()
	{
		arraySize = PRIMES[--primeIndex];
	}

	private void setArraySize(int initialApproximateArraySize)
	{
		for(int i = 0; i < PRIMES.length; i++)
		{
			if(PRIMES[i] > initialApproximateArraySize)
			{
				arraySize = PRIMES[i];
				primeIndex = i;
			}
		}
	}
}
