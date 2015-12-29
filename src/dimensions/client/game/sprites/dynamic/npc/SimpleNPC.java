package dimensions.client.game.sprites.dynamic.npc;

import java.security.SecureRandom;

import dimensions.client.engine.physics.Coordinate2D;
import dimensions.client.engine.physics.Coordinate3D;
import dimensions.client.engine.physics.Physics;
import dimensions.client.engine.physics.Velocity;
import dimensions.client.engine.spriteinterfaces.NPC;
import dimensions.client.engine.spriteinterfaces.Sprite;
import dimensions.client.game.sprites.GenericSprite;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.PixelReader;

public class SimpleNPC extends GenericSprite implements NPC
{
	private final SecureRandom rand = new SecureRandom();
	private double nextX = 1;
	private double nextY = 1;
	private double velocityY = 0;
	private double velocityX = 0;
	private long lastMoved = System.nanoTime();

	public SimpleNPC()
	{
		getScreenCoordinates().setX((500 + rand.nextInt(600) - 300));
		getScreenCoordinates().setY(350 + rand.nextInt(400) - 200);
	}

	@Override
	public Rectangle2D[] getHitBoxes()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean intersects(Rectangle2D[] bounds)
	{
		// TODO Auto-generated method stub
		return false;
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
		velocityX = rand.nextDouble()*3 -1.5;
		velocityY = rand.nextDouble()*3 -1.5;
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
	public void act()
	{
		nextX += rand.nextInt(1) - 0.5;
		nextY += nextX / 10;

		if(getScreenCoordinates().getX() < 0)
			nextX = 5;
		else if(getScreenCoordinates().getX() > 600)
			nextX = 5;

		if(getScreenCoordinates().getY() < 0)
			nextY = 5;
		else if(getScreenCoordinates().getY() > 600)
			nextY = 5;
	}

	@Override
	public boolean hasFixedScreenPosition()
	{
		return false;
	}

	@Override
	public Velocity getVelocity()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Coordinate2D getScreenCoordinates()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Coordinate3D getWorldCoordinates()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(Sprite o)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
