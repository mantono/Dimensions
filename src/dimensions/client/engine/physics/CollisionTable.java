package dimensions.client.engine.physics;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import dimensions.client.engine.GameSettings;
import dimensions.client.engine.spriteinterfaces.Collidable;

public class CollisionTable extends HashSet<Collidable>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6885356347649275542L;
	private CollisionSet[] collidableArray;
	private int arraySize = 11;
	private int primeIndex = -1;
	private final static int[] PRIMES = {23, 53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593, 49157, 98317, 196613, 393241, 786433, 1572869, 3145739, 6291469, 12582917, 25165843, 50331653, 100663319, 201326611, 402653189, 805306457, 1610612741};

	public CollisionTable(final int initialApproximateArraySize)
	{
		super(initialApproximateArraySize);
		setArraySize(initialApproximateArraySize);
		collidableArray = new CollisionSet[arraySize];
	}

	public CollisionTable()
	{
		collidableArray = new CollisionSet[arraySize];
	}

	@Override
	public boolean add(Collidable collidable)
	{
		if(collidable.isOutsideScreen())
			return false;
		if(size() > arraySize * 5)
			expand();
		addToTable(new CollisionRecord(collidable), collidableArray);
		return super.add(collidable);		
	}

	@Override
	public boolean remove(Object object)
	{
		if(size() < arraySize * 2)
			shrink();
		if(!super.remove(object))
			return false;
		if(!(object instanceof Collidable))
			return false;
		Collidable collidable = (Collidable) object;
		final int i = hashIndex(collidable);
		if(collidableArray[i] != null)
			return collidableArray[i].remove(collidable);
		return false;
	}

	@Override
	public boolean removeAll(java.util.Collection<?> c)
	{
		final boolean removedElements = super.removeAll(c);
		if(!removedElements)
			return false;
		final Iterator<?> iter = c.iterator();
		while(iter.hasNext())
			remove(iter.next());
		return removedElements;
	}
	
	public Set<CollisionRecord> getCollidables(final Collidable collidable, final int hashRadius)
	{
		if(hashRadius < 0)
			throw new IllegalArgumentException("Parameter hashRange cannot be negative.");
		
		final Set<CollisionRecord> collisionCandidates = new HashSet<CollisionRecord>();
		final int index = hashIndex(collidable);
		for(int i = index - hashRadius; i <= index + hashRadius; i++)
				collisionCandidates.addAll(removeObsolete(collidableArray[i % collidableArray.length]));
		
		return collisionCandidates;
	}

	private Set<CollisionRecord> removeObsolete(Set<CollisionRecord> collisionCandidates)
	{
		final Iterator<CollisionRecord> recordIterator = collisionCandidates.iterator();
		while(recordIterator.hasNext())
		{
			final CollisionRecord record = recordIterator.next();
			if(record.isObsolete(Physics.ONE_SECOND/5) || record.getCollidable().isReadyToRemove())
				recordIterator.remove();
		}
		
		return collisionCandidates;
	}

	private boolean addToTable(CollisionRecord record, CollisionSet[] array)
	{
		final int index = hashIndex(record.getCollidable());
		if(array[index] == null)
			array[index] = new CollisionSet();
		return array[index].add(record);
	}

	private void expand()
	{
		advanceTotNextPrime();
		rehashTable();
	}

	private void shrink()
	{
		previousPrime();
		rehashTable();
	}

	private void rehashTable()
	{
		final CollisionSet[] rehashedArray = new CollisionSet[arraySize];
		for(int i = 0; i < collidableArray.length; i++)
		{
			final CollisionSet set = collidableArray[i];
			for(CollisionRecord record : set)
				if(!record.getCollidable().isReadyToRemove())
					addToTable(record, rehashedArray);
		}

		collidableArray = rehashedArray;
	}

	private int hashIndex(Collidable collidable)
	{
		final int hash = computeHash(collidable.getScreenCoordinates());
		final int index = hash % arraySize;
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

	private int setArraySize(int initialApproximateArraySize)
	{
		for(int i = 0; i < PRIMES.length; i++)
		{
			if(PRIMES[i] > initialApproximateArraySize)
			{
				arraySize = PRIMES[i];
				primeIndex = i;
			}
		}
		return -1;
	}
}
