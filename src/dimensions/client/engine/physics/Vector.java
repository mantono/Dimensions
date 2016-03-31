package dimensions.client.engine.physics;

public interface Vector
{
	default double getForce()
	{
		return Math.sqrt(Math.pow(getX(), 2) + Math.pow(getY(), 2));
	}

	default double getDirection()
	{
		return Math.atan2(getX(), getY());
	}

	double getX();
	double getY();
}
