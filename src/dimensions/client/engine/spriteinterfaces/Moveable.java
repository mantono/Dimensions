package dimensions.client.engine.spriteinterfaces;

import dimensions.client.engine.Physics;

public interface Moveable extends Sprite
{
	void updateVelocity(Physics physics);
	double getVelocityX();
	double getVelocityY();
	void setVelocityX(double velocity);
	void setVelocityY(double velocity);
	long updateLastMoved(long nanoSeconds);
	boolean hasFixedScreenPosition();
}
