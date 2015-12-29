package dimensions.client.engine.physics;

public class Coordinate2D
{
	private double x = 0;
	private double y = 0;

	public Coordinate2D()
	{
	}

	public Coordinate2D(final double x, final double y)
	{
		this.x = x;
		this.y = y;
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
	
	public void move(final Velocity velocity)
	{
		x += velocity.getX();
		y += velocity.getY();
	}
	
	public double distance(final double x, final double y)
	{
		final double dx = Math.pow(this.x - x, 2);
		final double dy = Math.pow(this.y - y, 2);
		return Math.sqrt(dx + dy);
	}
	
	public double distance(final Coordinate2D coords)
	{
		return distance(coords.x, coords.y);
	}
}
