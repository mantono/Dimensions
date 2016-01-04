package dimensions.client.game.sprites.dynamic.npc;

import java.security.SecureRandom;

import dimensions.client.engine.physics.Coordinate2D;
import dimensions.client.engine.physics.Coordinate3D;
import dimensions.client.engine.physics.Physics;
import dimensions.client.engine.physics.Velocity;
import dimensions.client.engine.spriteinterfaces.Collidable;
import dimensions.client.engine.spriteinterfaces.NPC;
import dimensions.client.engine.spriteinterfaces.Sprite;
import dimensions.client.game.sprites.GenericSprite;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.PixelReader;

public class SimpleNPC extends GenericSprite implements NPC
{
	private final SecureRandom rand = new SecureRandom();
	private long lastMoved = System.nanoTime();
	private final Velocity velocity = new Velocity();
	private boolean remove = false;

	public SimpleNPC()
	{
		getScreenCoordinates().setX((500 + rand.nextInt(600) - 300));
		getScreenCoordinates().setY(350 + rand.nextInt(400) - 200);
	}

	@Override
	public Bounds[] getHitBoxes()
	{
		return new Bounds[] {new BoundingBox(getScreenCoordinates().getX(), getScreenCoordinates().getY(), 8, 8)};
	}

	@Override
	public boolean hasPixelCollision(PixelReader pixels)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateVelocity(Physics physics)
	{
		velocity.accelerateX(rand.nextDouble()*1 -0.5);
		velocity.accelerateY(rand.nextDouble()*1 -0.5);
	}
	
	@Override
	public synchronized long updateLastMoved(long nanoSeconds)
	{
		final long diff = nanoSeconds - lastMoved;
		lastMoved = nanoSeconds;
		return diff;
	}

	@Override
	public double getLife()
	{
		return 100;
	}
	
	@Override
	public double getMass()
	{
		return 1;
	}

	@Override
	public void act()
	{
	}

	@Override
	public boolean hasFixedScreenPosition()
	{
		return false;
	}

	@Override
	public Velocity getVelocity()
	{
		return velocity;
	}
	
	@Override
	public boolean isReadyToRemove()
	{
		return remove;
	}

	@Override
	public void onCollision(Collidable other)
	{
		remove = true;
	}
}
