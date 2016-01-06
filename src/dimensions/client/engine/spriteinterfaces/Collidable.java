package dimensions.client.engine.spriteinterfaces;

import dimensions.client.engine.physics.Coordinate2D;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.PixelReader;

public interface Collidable extends Sprite
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
	
	default Coordinate2D getCenterOfSprite()
	{
		final Coordinate2D coords = getScreenCoordinates();
		final Bounds bounds = getBounds();
		final double x = bounds.getWidth()/2;
		final double y = bounds.getHeight()/2;
		return new Coordinate2D(coords.getX() + x, coords.getY() + y);
	}
	
	void onCollision(Collidable other);
	Bounds[] getHitBoxes();
	boolean hasPixelCollision(PixelReader pixels);
}
