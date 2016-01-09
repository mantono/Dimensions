package dimensions.client.engine.physics;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import dimensions.client.engine.GameSettings;
import dimensions.client.engine.spriteinterfaces.Collidable;

public class CollisionMatrix
{
	private final Set<CollisionRecord>[][] collisionSets;
	private final double searchRadius;
	private final double divider;

	public CollisionMatrix()
	{
		this(10, 50);
	}

	public CollisionMatrix(final double divider)
	{
		this(divider, 50);
	}

	public CollisionMatrix(final double divider, final double searchRadius)
	{
		this.searchRadius = searchRadius;
		this.divider = divider;
		final int x = (int) (GameSettings.widthWindow / divider);
		final int y = (int) (GameSettings.heightWindow / divider);
		collisionSets = new CollisionSet[x][y];
	}

	public void add(Collidable collidable)
	{
		final CollisionRecord cRecord = new CollisionRecord(collidable);
		final Coordinate2D coords = collidable.getScreenCoordinates();
		final int x = (int) (coords.getX() / divider);
		final int y = (int) (coords.getY() / divider);
		if(collisionSets[x][y] == null)
			collisionSets[x][y] = new CollisionSet();
		collisionSets[x][y].add(cRecord);
	}

	public Set<CollisionRecord> getCollidablesFor(final double x, final double y, final double radius)
	{
		final Set<CollisionRecord> collidables = new HashSet<CollisionRecord>();

		final double indexDistance = radius / divider;

		final int minX = (int) (x - indexDistance);
		final int maxX = (int) (x + indexDistance);

		final int minY = (int) (y - indexDistance);
		final int maxY = (int) (y + indexDistance);

		for(int xi = minX; xi < maxX; xi++)
		{
			for(int yi = minY; yi < maxY; yi++)
			{
				if(collisionSets[xi][yi] != null && !collisionSets[xi][yi].isEmpty())
				{
					final Iterator<CollisionRecord> iterator = collisionSets[xi][yi].iterator();
					while(iterator.hasNext())
					{
						final CollisionRecord record = iterator.next();
						if(record.isObsolete(PhysicsEngine.ONE_SECOND / 10) || record.getCollidable().isReadyToRemove())
							iterator.remove();
						else
							collidables.add(record);
					}
				}
			}
		}

		return collidables;
	}
}
