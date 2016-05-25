package dimensions.client.engine.spriteinterfaces;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.image.Image;

public interface Sprite extends Comparable<Sprite>
{
	Image getTexture();
	Bounds getBounds();
	Point2D getPosition();
	void setPosition(Point2D position);
	Point3D getWorldCoordinates();
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
