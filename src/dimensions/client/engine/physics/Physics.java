package dimensions.client.engine.physics;

import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

import dimensions.client.engine.Engine;
import dimensions.client.engine.GameSettings;
import dimensions.client.engine.SpriteManager;
import dimensions.client.engine.spriteinterfaces.Collidable;
import dimensions.client.engine.spriteinterfaces.Moveable;
import dimensions.client.engine.spriteinterfaces.Player;
import javafx.geometry.Rectangle2D;

public class Physics implements Runnable
{
	public static final long ONE_SECOND = 1_000_000_000;

	private final Engine engine;
	private final SpriteManager spriteManager;
	private final Rectangle2D playerScreenBounds;
	private final MovePhysics movementConsumer = new MovePhysics();
	private final CollisionCheck collisionCheck = new CollisionCheck();
	private long pauseDuration = 0;
	private Velocity worldOffset = new Velocity(0, 0);

	private double gravity, maxVelocityX, maxVelocityY, minVelocityX, minVelocityY, maxAccelerationX, maxAccelerationY,
			fps;

	public Physics(final SpriteManager spriteManager, final Engine engine, final Rectangle2D playerScreenBounds)
	{
		this.engine = engine;
		this.spriteManager = spriteManager;
		this.fps = 60;
		this.gravity = 1;
		this.maxVelocityY = this.maxVelocityX = 2.8;
		this.minVelocityX = this.minVelocityY = -this.maxVelocityX;
		this.maxAccelerationX = this.maxAccelerationY = 1;
		this.playerScreenBounds = playerScreenBounds;
	}

	public Physics(final SpriteManager spriteManager, final Engine engine)
	{
		this(spriteManager, engine, new Rectangle2D(0, 0, GameSettings.widthWindow, GameSettings.heightWindow));
	}

	private void checkForCollisons()
	{
		Spliterator<Collidable> cSplit = spriteManager.getCollidables();
		cSplit.forEachRemaining(collisionCheck);
	}

	private void moveMoveables()
	{
		spriteManager.getMoveables().forEachRemaining(movementConsumer);
	}

	public double calculateInterpolation(final long nanoSecondsDiff)
	{
		final double frameDuration = ONE_SECOND / fps;
		return nanoSecondsDiff / frameDuration;
	}

	@Override
	public void run()
	{
		pauseDuration = engine.timePaused();

		final Player player = spriteManager.getPlayer();
		if(playerIsOutsideBounds(player))
		{
			worldOffset = correctPlayerPosition(player);
			worldOffset.applyPhysics(this);
		}
		else
		{
			worldOffset.reduce(0);
		}

		moveMoveables();
		checkForCollisons();
		spriteManager.getCollisionTable().addFromQueue();
	}

	private Velocity correctPlayerPosition(Player player)
	{
		double x, y;
		x = y = 0;

		final Coordinate2D playerCoords = player.getScreenCoordinates();

		if(playerCoords.getX() < playerScreenBounds.getMinX())
			x = playerScreenBounds.getMinX() - playerCoords.getX();
		else if(playerCoords.getX() > playerScreenBounds.getMaxX())
			x = playerScreenBounds.getMaxX() - playerCoords.getX();

		if(playerCoords.getY() < playerScreenBounds.getMinY())
			y = playerScreenBounds.getMinY() - playerCoords.getY();
		else if(playerCoords.getY() > playerScreenBounds.getMaxY())
			y = playerScreenBounds.getMaxY() - playerCoords.getY();

		return new Velocity(x, y);
	}

	private boolean playerIsOutsideBounds(Player player)
	{
		if(player == null)
			return false;
		final Coordinate2D playerCoords = player.getScreenCoordinates();
		return !playerScreenBounds.contains(playerCoords.getX(), playerCoords.getY());
	}

	public double getMaxVelocityY()
	{
		return maxVelocityY;
	}

	public void setFPS(double fps)
	{
		this.fps = fps;
	}

	public double getFPS()
	{
		return fps;
	}

	public void setMaxVelocityY(double maxVelocityY)
	{
		this.maxVelocityY = maxVelocityY;
	}

	public double getMinVelocityX()
	{
		return minVelocityX;
	}

	public void setMinVelocityX(double minVelocityX)
	{
		this.minVelocityX = minVelocityX;
	}

	public double getMinVelocityY()
	{
		return minVelocityY;
	}

	public void setMinVelocityY(double minVelocityY)
	{
		this.minVelocityY = minVelocityY;
	}

	public double getGravity()
	{
		return gravity;
	}

	public void setGravity(double gravity)
	{
		this.gravity = gravity;
	}

	public double getMaxAccelerationX()
	{
		return maxAccelerationX;
	}

	public void setMaxAccelerationX(double maxAccelerationX)
	{
		this.maxAccelerationX = maxAccelerationX;
	}

	public double getMaxAccelerationY()
	{
		return maxAccelerationY;
	}

	public void setMaxAccelerationY(double maxAccelerationY)
	{
		this.maxAccelerationY = maxAccelerationY;
	}

	public double getMaxVelocityX()
	{
		return maxVelocityX;
	}

	public void setMaxVelocityX(double maxVelocityX)
	{
		this.maxVelocityX = maxVelocityX;
	}

	private class MovePhysics implements Consumer<Moveable>
	{
		@Override
		public void accept(Moveable m)
		{
			m.updateVelocity(Physics.this);
			final long diff = m.updateLastMoved(System.nanoTime()) - pauseDuration;
			final double interpolation = calculateInterpolation(diff);

			final Velocity velocity = m.getVelocity();
			velocity.applyPhysics(Physics.this);
			velocity.interpolate(interpolation);
			final Coordinate3D worldPosition = m.getWorldCoordinates();
			worldPosition.move(velocity);

			final Coordinate2D screenPosition = m.getScreenCoordinates();
			screenPosition.move(velocity);
			screenPosition.move(worldOffset);
		}

	}

	private class CollisionCheck implements Consumer<Collidable>
	{

		@Override
		public void accept(Collidable t)
		{
			Set<CollisionRecord> otherCollidables = spriteManager.getCollisionTable().getCollidables(t, 2);
			for(CollisionRecord record : otherCollidables)
			{
				if(t != record.getCollidable() && t.hasCollision(record.getCollidable()))
				{
					t.onCollision(record.getCollidable());
					record.getCollidable().onCollision(t);
				}
			}
		}

	}
}
