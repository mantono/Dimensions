package dimensions.client.engine.spriteinterfaces;

import javafx.geometry.Bounds;

public interface Collidable extends Moveable
{
	Bounds[] getHitBoxes();
}
