package dimensions.client.engine;

import java.util.Spliterator;
import java.util.function.Consumer;

import dimensions.client.engine.spriteinterfaces.Collidable;
import dimensions.client.engine.spriteinterfaces.Moveable;

public class Physics implements Runnable
{
	public static final long ONE_SECOND = 1_000_000_000;

	private final SpriteManager spriteManager;
	private final MovePhysics movementConsumer = new MovePhysics();

	private double gravity, maxVelocityX, maxVelocityY, minVelocityX, minVelocityY, maxAccelerationX, maxAccelerationY,
			fps;

	public Physics(SpriteManager spriteManager)
	{
		this.spriteManager = spriteManager;
		this.fps = 60;
		this.gravity = 1;
		this.maxVelocityY = this.maxVelocityX = 2.5;
		this.minVelocityX = this.minVelocityY = -this.maxVelocityX;
		this.maxAccelerationX = this.maxAccelerationY = 1;
	}

	private void checkForCollisons()
	{
		final Spliterator<Collidable> splitA = spriteManager.getCollidables();
		final Spliterator<Collidable> splitB = spriteManager.getCollidables();
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
		moveMoveables();
		checkForCollisons();
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
			double velocityX = m.getVelocityX();
			double velocityY = m.getVelocityY();

			if(velocityX > getMaxVelocityX())
				velocityX = getMaxVelocityX();
			else if(velocityX < -getMaxVelocityX())
				velocityX = -getMaxVelocityX();

			if(velocityY > getMaxVelocityY())
				velocityY = getMaxVelocityY();
			else if(velocityY < -getMaxVelocityY())
				velocityY = -getMaxVelocityY();

			final long diff = m.updateLastMoved(System.nanoTime());
			final double interpolation = calculateInterpolation(diff);
			velocityX *= interpolation;
			velocityY *= interpolation;
			m.setX(m.getX() + velocityX);
			m.setY(m.getY() + velocityY);
		}

	}
}
