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
import dimensions.client.engine.spriteinterfaces.Sprite;
import javafx.geometry.Rectangle2D;

public class PhysicsEngine implements Runnable
{
	public static final long ONE_SECOND = 1_000_000_000;

	private final Engine engine;
	private final Physics physics;
	private final SpriteManager spriteManager;
	private final Rectangle2D playerScreenBounds;
	private final MovePhysics movementConsumer = new MovePhysics();
	private final CollisionCheck collisionCheck = new CollisionCheck();
	private long pauseDuration = 0;
	private Velocity worldOffset = new Velocity(0, 0);

	public PhysicsEngine(final Physics physics, final SpriteManager spriteManager, final Engine engine, final Rectangle2D playerScreenBounds)
	{
		this.physics = physics;
		this.engine = engine;
		this.spriteManager = spriteManager;
		this.playerScreenBounds = playerScreenBounds;
	}

	public PhysicsEngine(final Physics physics, final SpriteManager spriteManager, final Engine engine)
	{
		this(physics, spriteManager, engine, new Rectangle2D(0, 0, GameSettings.widthWindow, GameSettings.heightWindow));
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
		final double frameDuration = ONE_SECOND / physics.getFPS();
		return nanoSecondsDiff / frameDuration;
	}

	public double gravity(final Sprite sprite1, final Sprite sprite2)
	{
		final double productOfMass = sprite1.getMass() * sprite2.getMass();
		final double distance = sprite1.getScreenCoordinates().distance(sprite2.getScreenCoordinates());
		final double squareOfDistance = Math.pow(distance, 2);
		return physics.getGravity() * (productOfMass / squareOfDistance);
	}

	@Override
	public void run()
	{
		pauseDuration = engine.timePaused();

		final Player player = spriteManager.getPlayer();
		if(playerIsOutsideBounds(player))
		{
			worldOffset = correctPlayerPosition(player);
			worldOffset.applyPhysics(physics);
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

	private class MovePhysics implements Consumer<Moveable>
	{
		@Override
		public void accept(Moveable m)
		{
			m.updateVelocity(PhysicsEngine.this);
			final long diff = m.updateLastMoved(System.nanoTime()) - pauseDuration;
			final double interpolation = calculateInterpolation(diff);

			final Velocity velocity = m.getVelocity();
			velocity.applyPhysics(physics);
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
			final double diagonalLength = Math.sqrt(Math.pow(t.getWidth(), 2) + Math.pow(t.getHeight(), 2));
			final float additionalScanRange = (float) diagonalLength / 1000;

			Set<CollisionRecord> otherCollidables = spriteManager.getCollisionTable().getCollidables(t, 0.005f + additionalScanRange);

			/* Debug data */
			final float amountOfCollidables = spriteManager.size();
			final float ratio = (otherCollidables.size() / amountOfCollidables) * 100;
			System.out.println(otherCollidables.size() + "/" + amountOfCollidables + " - " + ratio + "%");
			/* End debug data */

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
