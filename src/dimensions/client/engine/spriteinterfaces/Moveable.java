package dimensions.client.engine.spriteinterfaces;

public interface Moveable extends Sprite
{
	void move();
	void setWorldX();
	void setWorldY();
	void setWorldZ();
	boolean hasFixedScreenPosition();
}
