package dimensions.client.engine.physics;

public interface Physics
{
	double getFPS();
	double getGravity();
	double getMaxVelocityX();
	double getMaxVelocityY();
	double getMinVelocityX();
	double getMinVelocityY();
	double getMaxAccelerationX();
	double getMaxAccelerationY();
}