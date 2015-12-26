package dimensions.client.engine.spriteinterfaces;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public interface Sprite extends Comparable<Sprite>
{
	void render(GraphicsContext renderer);
	Rectangle2D getBounds();
	double getX();
	double getY();
	double getZ();
	void setWorldX(double x);
	void setWorldY(double y);
	void setWorldZ(double z);
	double getWidth();
	double getHeight();
	void setX(double x);
	void setY(double y);
	void setZ(double z);
	void move(double x, double y);
	boolean isOutsideScreen();
	double distanceFromScreen();
	void centerOnScreen();
	boolean isReadyToRemove();
	long timeCreated();
	
	default void setXY(double x, double y)
	{
		setX(x);
		setY(y);
	}
	
	default double getWorldX()
	{
		return getX();
	}
	
	default double getWorldY()
	{
		return getY();
	}
	default double getWorldZ()
	{
		return getZ();
	}
	
	@Override
	default int compareTo(Sprite other)
	{
		if(this.getZ() != other.getZ())
			return (int) (this.getZ() - other.getZ());
		if(this.getY() != other.getY())
			return (int) (this.getY() - other.getY());
		return (int) (this.getX() - other.getX());
	}
	
	
}
