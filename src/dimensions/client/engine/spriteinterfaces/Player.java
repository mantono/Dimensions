package dimensions.client.engine.spriteinterfaces;

public interface Player extends Collidable, Destroyable, Moveable
{
	void moveRight();
	void moveLeft();
	void moveUp();
	void moveDown();
	void stop();
}
