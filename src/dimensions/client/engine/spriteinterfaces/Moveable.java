package dimensions.client.engine.spriteinterfaces;

import dimensions.client.engine.physics.Physics;
import dimensions.client.engine.physics.Velocity;

public interface Moveable extends Sprite
{
	void updateVelocity(Physics physics);
	Velocity getVelocity();
	long updateLastMoved(long nanoSeconds);
	boolean hasFixedScreenPosition();
}
