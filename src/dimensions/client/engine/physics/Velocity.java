package dimensions.client.engine.physics;

public class Velocity
{
	private double x = 0;
	private double y = 0;

	public Velocity(final double x, final double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Velocity(final Coordinate2D previousPosition, final Coordinate2D currentPosition)
	{
		this.x = currentPosition.getX() - previousPosition.getX();
		this.y = currentPosition.getY() - previousPosition.getY();
	}

	public Velocity()
	{
	}

	public double getSpeed()
	{
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	public double getDirection()
	{
		return Math.atan2(y, x);
	}

	public void interpolate(final double interpolationFactor)
	{
		x *= interpolationFactor;
		y *= interpolationFactor;
	}

	public void setX(final double x)
	{
		this.x = x;
	}

	public double getX()
	{
		return x;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public double getY()
	{
		return y;
	}
	
	public Velocity getVelocityOfCollision(final Velocity other)
	{
		return new Velocity(x - other.x, y - other.y);
	}

	public double accelerateX(double acceleration)
	{
		return this.x += acceleration;
	}

	public double accelerateY(double acceleration)
	{
		return this.y += acceleration;
	}
	
	public void reduce(final double multiplier)
	{
		reduceX(multiplier);
		reduceY(multiplier);
	}

	public void reduceX(final double multiplier)
	{
		if(multiplier > 1)
			throw new IllegalArgumentException("Argument multiplier can't be greater than 1.0");
		if(multiplier < 0)
			throw new IllegalArgumentException("Argument multiplier can't be less than 0");
		x *= multiplier;
	}
	
	public void reduceY(final double multiplier)
	{
		if(multiplier > 1)
			throw new IllegalArgumentException("Argument multiplier can't be greater than 1.0");
		if(multiplier < 0)
			throw new IllegalArgumentException("Argument multiplier can't be less than 0");
		y *= multiplier;
	}

	public void applyPhysics(Physics physics)
	{
		if(x > physics.getMaxVelocityX())
			x = physics.getMaxVelocityX();
		else if(x < -physics.getMaxVelocityX())
			x = -physics.getMaxVelocityX();

		if(y > physics.getMaxVelocityY())
			y = physics.getMaxVelocityY();
		else if(y < -physics.getMaxVelocityY())
			y = -physics.getMaxVelocityY();
	}
}
