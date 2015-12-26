package dimensions.client.engine.spriteinterfaces;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.PixelReader;

public interface Collidable extends Moveable
{
	Rectangle2D[] getHitBoxes();
	boolean intersects(Rectangle2D[] bounds);
	boolean hasPixelCollision(PixelReader pixels);
	default void onCollision(Collidable other){}
}
