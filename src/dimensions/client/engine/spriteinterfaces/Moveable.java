package dimensions.client.engine.spriteinterfaces;

import dimensions.client.engine.physics.PhysicsEngine;
import dimensions.client.engine.physics.Velocity;
import javafx.geometry.Point2D;

public interface Moveable extends Sprite
{
	double getMass();
	Point2D getVelocity();
	void setVelocity(Point2D velocity);
	void updateVelocity();
	long updateLastMoved(long nanoSeconds);
}
