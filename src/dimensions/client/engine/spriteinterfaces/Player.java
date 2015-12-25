package dimensions.client.engine.spriteinterfaces;

public interface Player extends Collidable, Destroyable, Moveable
{
	void rightPressed();
	void rightReleased();
	void leftPressed();
	void leftReleased();
	void upPressed();
	void upReleased();
	void downPressed();
	void downReleased();
	void stop();
}
