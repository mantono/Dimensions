package dimensions.client.engine.spriteinterfaces;

import dimensions.client.engine.physics.Coordinate2D;
import dimensions.client.engine.physics.Coordinate3D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public interface Sprite extends Comparable<Sprite>
{
	void render(GraphicsContext renderer);
	Rectangle2D getBounds();
	Coordinate2D getScreenCoordinates();
	Coordinate3D getWorldCoordinates();
	double getWidth();
	double getHeight();
	void move(double x, double y);
	boolean isOutsideScreen();
	double distanceFromScreen();
	void centerOnScreen();
	boolean isReadyToRemove();
	long timeCreated();
	
	@Override
	default int compareTo(Sprite other)
	{
		if(this.getWorldCoordinates().getZ() != other.getWorldCoordinates().getZ())
			return (int) (this.getWorldCoordinates().getZ() - other.getWorldCoordinates().getZ());
		if(this.getWorldCoordinates().getY() != other.getWorldCoordinates().getY())
			return (int) (this.getWorldCoordinates().getY() - other.getWorldCoordinates().getY());
		return (int) (this.getWorldCoordinates().getX() - other.getWorldCoordinates().getX());
	}
	
	
}
