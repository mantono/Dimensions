package dimensions.client.engine.physics;

import dimensions.client.engine.spriteinterfaces.Collidable;

public class CollisionRecord implements Comparable<CollisionRecord>
{
	private final Collidable collidable;
	private final long timestamp;
	
	public CollisionRecord(final Collidable collidable)
	{
		this.collidable = collidable;
		this.timestamp = System.nanoTime();
	}
	
	public Collidable getCollidable()
	{
		return collidable;
	}
	
	public long getTimestamp()
	{
		return timestamp;
	}
	
	public boolean isObsolete(long nanoSeconds)
	{
		return System.nanoTime() - timestamp > nanoSeconds;
	}

	@Override
	public int compareTo(CollisionRecord o)
	{
		return (int) ((this.timestamp - o.timestamp) % Integer.MAX_VALUE);
	}
}
