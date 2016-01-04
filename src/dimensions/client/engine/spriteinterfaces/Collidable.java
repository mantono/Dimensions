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
				if(rectThis.contains(rectOther))
					return true;
		
		return false;
	}
	
	default boolean hasCollision(Collidable other)
	{
		return intersects(other.getHitBoxes());
	}
	
	void onCollision(Collidable other);
	Bounds[] getHitBoxes();
	boolean hasPixelCollision(PixelReader pixels);
}
