package dimensions.client.engine.physics;

public class Coordinate3D extends Coordinate2D
{
	private double z = 0;
	
	public Coordinate3D(final double x, final double y, final double z)
	{
		super(x, y);
		this.z = z;
	}
	
	public double getZ()
	{
		return z;
	}
	
	public void setZ(double z)
	{
		this.z = z;
	}
}

