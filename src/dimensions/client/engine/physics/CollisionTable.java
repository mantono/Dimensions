package dimensions.client.engine.physics;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import dimensions.client.engine.GameSettings;
import dimensions.client.engine.spriteinterfaces.Collidable;

/**
 * This class implements a data structure with the use of hash functions for
 * {@link Collidable} objects, in order to better perform collision calculations
 * at a lower performance cost. The goal is to reduce the time complexity
 * compared to more naive approaches as usually are O(n<sup>2</sup>).
 * 
 * The CollisionTable does not perform any actual collision detection, it only
 * tries to reduce the amount of elements that are used in the calculation of
 * each collision detection.
 * 
 * Unlike many other more complex data structures/solutions to this problem (not
 * the naive O(n<sup>2</sup>) ones) this implementation is not intended to add
 * or recreate the entire data structure upon each frame, but rather let each
 * {@link CollisionRecord} stay in the data structure until it's considered obsolete.
 *
 */
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
	private final long maxTime;
	private final Lock hashLock = new ReentrantLock();
	private final Condition hashFinished = hashLock.newCondition();
	private final BlockingQueue<Collidable> incoming = new ArrayBlockingQueue<Collidable>(300);

	/**
	 * Constructor for this class.
	 * 
	 * @param elementCount the expected amount of elements that this data
	 * structure will hold.
	 * @param maxTime the maximum amount of time in nanoseconds that may elapse
	 * before a stored position from a {@link Collidable} object is considered
	 * obsolete and will be removed. What value that is appropriate depends on
	 * the pace of the {@link Collidable} objects in this game. Setting this
	 * value to high will generate less overhead since each
	 * {@link CollisionRecord} can be kept for a longer time, but it also
	 * increases the chance of missing collisions because the
	 * {@link CollisionRecord} instances in this table will not updates its
	 * position as often.
	 */
	public CollisionTable(final int elementCount, final long maxTime)
	{
		setArraySize(elementCount / 3);
		collidableArray = new CollisionSet[arraySize];
		this.maxTime = maxTime;
	}

	/**
	 * Constructor for this class. This constructor will use the default array
	 * size of 11 upon initialization and set the max time for a
	 * {@link CollisionRecord} to 0.1 seconds.
	 */
	public CollisionTable()
	{
		this(0, PhysicsEngine.ONE_SECOND / 10);
	}

	/**
	 * Adds a new to {@link Collidable} to the internal {@link Queue}.
	 * 
	 * @param collidable the {@link Collidable} object that should be added.
	 * @return true if it was successfully added to the queue, else false.
	 */
	public boolean add(Collidable collidable)
	{
		return incoming.offer(collidable);
	}

	/**
	 * Poll the queue for new {@link Collidable} objects and add it to the
	 * table, if it's not empty.
	 */
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

	/**
	 * Gets the size for this CollisionTable.
	 * 
	 * @return returns the current amount of {@link CollisionRecord} instances,
	 * not taking into account the amount of objects in the queue.
	 */
	private int size()
	{
		return count;
	}

	/**
	 * This method will retrieve all nearby {@link Collidable} objects (wrapped
	 * in a {@link CollisionRecord}) that are candidates for a collision with
	 * the given parameter {@link Collidable}.
	 * 
	 * @param collidable the {@link Collidable} we want to find collision
	 * candidates for.
	 * @param scanPercentage determines how big part of the internal array that
	 * should be scanned for likely candidates, where 0 means the absolute
	 * minimum scan range and 1 will check the entire array for elements.
	 * 
	 * Increasing the number will increase the time complexity and reduce the
	 * performance but also reduce the chance of missing any collisions. Setting
	 * this to 1 will give a time complexity of O(n<sup>2</sup>) if this method
	 * is called for every {@link Collidable}, but with more overhead than a
	 * simple double for-loop. It is recommended that this parameter is set as
	 * low as possible, as long as no collisions are missed
	 * @return a set containing {@link CollisionRecord} of all collision
	 * candidates.
	 * @throws IllegalArgumentException if <code>scanPercentage</code> is &lt; 0
	 * or &gt; 1.
	 */
	public Set<CollisionRecord> getCollidables(final Collidable collidable, float scanPercentage)
	{
		if(scanPercentage < 0)
			throw new IllegalArgumentException("Parameter scanPercentage cannot be negative.");
		if(scanPercentage > 1)
			throw new IllegalArgumentException("Parameter scanPercentage cannot be greater than 1.");

		final Set<CollisionRecord> collisionCandidates = new HashSet<CollisionRecord>();

		hashLock.lock();
		waitForHash();

		final int hashRadius = Math.round(arraySize * scanPercentage);

		final int index = hashIndex(collidable, collidableArray);
		for(int i = index - hashRadius; i <= index + hashRadius; i++)
		{
			if(i < 0)
				i = 0;
			final Set<CollisionRecord> set = collidableArray[i % collidableArray.length];
			if(set != null)
				collisionCandidates.addAll(removeObsolete(set));
		}

		hashLock.unlock();

		return collisionCandidates;
	}

	/**
	 * Removes all instance of {@link CollisionRecord} which is considered
	 * obsolete. If the {@link CollisionRecord} is considered obsolete but
	 * {@link Collidable#isReadyToRemove()} returns false, then it will be added
	 * to the queue in order to receive a new {@link CollisionRecord}.
	 * 
	 * @param collisionCandidates a set of {@link CollisionRecord}.
	 * @return the remaining {@link CollisionRecord} instances which are not
	 * considered obsolete.
	 */
	private Set<CollisionRecord> removeObsolete(Set<CollisionRecord> collisionCandidates)
	{
		final Iterator<CollisionRecord> recordIterator = collisionCandidates.iterator();
		while(recordIterator.hasNext())
		{
			final CollisionRecord record = recordIterator.next();
			if(record.isObsolete(maxTime) || record.getCollidable().isReadyToRemove())
			{
				recordIterator.remove();
				count--;
				if(!record.getCollidable().isReadyToRemove())
					add(record.getCollidable());
			}
		}

		return collisionCandidates;
	}

	/**
	 * Adds a {@link CollisionRecord} to array of {@link CollisionSet}.
	 * 
	 * @param record the {@link CollisionRecord} that should be added.
	 * @param array the array that the record should be added to.
	 * @return true if it was successfully added to the {@link CollisionSet} at
	 * the given array index, else false.
	 */
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

	/**
	 * Expands the size of the internal array.
	 */
	private void expand()
	{
		hashLock.lock();
		advanceTotNextPrime();
		rehashTable();
		hashFinished.signalAll();
		hashLock.unlock();
	}

	/**
	 * Shrinks the size of the internal array. This method is currently not
	 * used.
	 */
	private void shrink()
	{
		hashLock.lock();
		previousPrime();
		rehashTable();
		hashFinished.signalAll();
		hashLock.unlock();
	}

	/**
	 * Waits for this class {@link ReentrantLock} to be released that is locking
	 * the internal array.
	 * 
	 * A more compact version of {@link Condition#await()} with exception
	 * handling taken care of, so this will not have to repeated every time
	 * <code>hashFinished.await()</code> would be called.
	 */
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

	/**
	 * Creates a new array when the conditions require it change size. All
	 * elements will have to be rehashed to fit in the new arrays size.
	 */
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

	/**
	 * Computes the hash for a {@link Collidable} and converts it to an index
	 * matching the size of an array.
	 * 
	 * @param collidable the {@link Collidable} to compute the hash for.
	 * @param array the array which length has to be taken into consideration.
	 * @return an index based on the hash for the {@link Collidable} and is
	 * within bounds for the array.
	 */
	private int hashIndex(Collidable collidable, Object[] array)
	{
		final int hash = computeHash(collidable.getCenterOfSprite());
		final int index = Math.abs(hash % array.length);
		return index;
	}

	/**
	 * This is where the magic happens.
	 * 
	 * Computes the two dimensional data of a {@link Coordinate2D} (x and y
	 * value) so it can be sorted into a one dimensional array. This is done by
	 * measuring the distance from the given coordinate and three other
	 * coordinates on the screen and with those variables compute a hash. The
	 * purpose of this hash is that it should turn out the same (or very
	 * similar) for coordinates that are located close to each other.
	 * 
	 * @param screenCoordinates the coordinates that will be computed.
	 * @return the hash for this coordinate.
	 */
	public int computeHash(Coordinate2D screenCoordinates)
	{
		final double d1 = screenCoordinates.distance(0, 0);
		final double d2 = screenCoordinates.distance(0, GameSettings.heightWindow);
		final double d3 = screenCoordinates.distance(GameSettings.widthWindow, GameSettings.heightWindow);
		final double diff = d1 - d3;
		final double d2Squared = Math.sqrt(d2 + 1);

		final double result = diff / d2Squared;

		return (int) result;
	}

	/**
	 * Changes the array size (on next rehash of table) to the next greater
	 * prime number.
	 */
	private void advanceTotNextPrime()
	{
		arraySize = PRIMES[++primeIndex];
	}

	/**
	 * Changes the array size (on next rehash of table) to the next lesser prime
	 * number.
	 */
	private void previousPrime()
	{
		arraySize = PRIMES[--primeIndex];
	}

	/**
	 * Finds the next prime number that is equal or greater to the given
	 * argument, and sets the future array size to that number.
	 * 
	 * @param initialApproximateArraySize the number that should be compared to.
	 */
	private void setArraySize(int initialApproximateArraySize)
	{
		if(initialApproximateArraySize < 11)
			return;
		for(int i = 0; i < PRIMES.length; i++)
		{
			if(PRIMES[i] >= initialApproximateArraySize)
			{
				arraySize = PRIMES[i];
				primeIndex = i;
				return;
			}
		}
	}
}
