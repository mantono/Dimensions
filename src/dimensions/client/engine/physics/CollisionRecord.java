package dimensions.client.engine.physics;

import dimensions.client.engine.spriteinterfaces.Collidable;

public class CollisionRecord
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
}
