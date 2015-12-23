package dimensions.client.engine.spriteinterfaces;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;

public interface Sprite extends Comparable<Sprite>
{
	Image getImage();
	Bounds getBounds();
	double getX();
	double getY();
	double getZ();
	boolean isOutsideScreen();
	int distanceFromScreen();
	boolean isReadyToRemove();
	long timeCreated();
	
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
