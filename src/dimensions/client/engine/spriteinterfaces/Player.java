package dimensions.client.engine.spriteinterfaces;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public interface Player extends Collidable, Destroyable, Moveable, EventHandler<KeyEvent>
{
	void moveRight();
	void moveLeft();
	void moveUp();
	void moveDown();
	void stop();
}
