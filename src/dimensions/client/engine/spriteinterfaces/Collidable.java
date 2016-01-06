package dimensions.client.engine.spriteinterfaces;

import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.PixelReader;

public interface Collidable extends Moveable
{
	default boolean intersects(Bounds[] bounds)
	{
		for(Bounds rectOther : bounds)
			for(Bounds rectThis : getHitBoxes())
				if(rectThis.intersects(rectOther))
					return true;
		
		return false;
	}
	
	default boolean hasCollision(Collidable other)
	{
		if(getBounds().intersects(other.getBounds()))
			return intersects(other.getHitBoxes());
		return false;
	}
	
	void onCollision(Collidable other);
	Bounds[] getHitBoxes();
	boolean hasPixelCollision(PixelReader pixels);
}
