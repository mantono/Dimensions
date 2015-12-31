package dimensions.client.engine.spriteinterfaces;

import dimensions.client.engine.physics.Physics;
import dimensions.client.engine.physics.Velocity;

public interface Moveable extends Sprite
{
	void updateVelocity(Physics physics);
	Velocity getVelocity();
	double getMass();
	long updateLastMoved(long nanoSeconds);
	boolean hasFixedScreenPosition();
}
