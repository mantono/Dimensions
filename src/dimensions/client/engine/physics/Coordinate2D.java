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
	
	public Coordinate2D(final Coordinate2D coords)
	{
		this.x = coords.x;
		this.y = coords.y;
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
	
	public static double distance(final Coordinate2D coords1, final Coordinate2D coords2)
	{
		final double dx = Math.pow(coords1.x - coords2.x, 2);
		final double dy = Math.pow(coords1.y - coords2.y, 2);
		return Math.sqrt(dx + dy);
	}
	
	public double distance(final double x, final double y)
	{
		return Coordinate2D.distance(this, new Coordinate2D(x, y));
	}
	
	public double distance(final Coordinate2D coords)
	{
		return Coordinate2D.distance(this, coords);
	}
}
