package dimensions.client.engine.physics;

public class WorldPhysics implements Physics
{
	private double gravity, maxVelocityX, maxVelocityY, minVelocityX, minVelocityY, maxAccelerationX, maxAccelerationY,
			fps;

	public WorldPhysics()
	{
		this.fps = 60;
		this.gravity = 1;
		this.maxVelocityY = this.maxVelocityX = 2.8;
		this.minVelocityX = this.minVelocityY = -this.maxVelocityX;
		this.maxAccelerationX = this.maxAccelerationY = 1;
	}

	public WorldPhysics(final double fps, final double gravity, final Velocity minimumVelocity, final Velocity maximumVelocity, final double maximumAcceleration)
	{
		this.fps = fps;
		this.gravity = gravity;
		this.minVelocityX = minimumVelocity.getX();
		this.minVelocityY = minimumVelocity.getY();
		this.maxVelocityX = maximumVelocity.getX();
		this.maxVelocityY = maximumVelocity.getY();
	}

	@Override
	public double getFPS()
	{
		return fps;
	}

	public void setFPS(double fps)
	{
		this.fps = fps;
	}

	@Override
	public double getGravity()
	{
		return gravity;
	}

	public void setGravity(double gravity)
	{
		this.gravity = gravity;
	}

	@Override
	public double getMaxVelocityX()
	{
		return maxVelocityX;
	}

	@Override
	public double getMaxVelocityY()
	{
		return maxVelocityY;
	}
	
	public void setMaxVelocityX(double maxVelocityX)
	{
		this.maxVelocityX = maxVelocityX;
	}

	public void setMaxVelocityY(double maxVelocityY)
	{
		this.maxVelocityY = maxVelocityY;
	}

	@Override
	public double getMinVelocityX()
	{
		return minVelocityX;
	}

	public void setMinVelocityX(double minVelocityX)
	{
		this.minVelocityX = minVelocityX;
	}

	@Override
	public double getMinVelocityY()
	{
		return minVelocityY;
	}

	public void setMinVelocityY(double minVelocityY)
	{
		this.minVelocityY = minVelocityY;
	}

	@Override
	public double getMaxAccelerationX()
	{
		return maxAccelerationX;
	}

	public void setMaxAccelerationX(double maxAccelerationX)
	{
		this.maxAccelerationX = maxAccelerationX;
	}

	@Override
	public double getMaxAccelerationY()
	{
		return maxAccelerationY;
	}
	
	public void setMaxAccelerationY(double maxAccelerationY)
	{
		this.maxAccelerationY = maxAccelerationY;
	}
}
