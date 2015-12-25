package dimensions.client.game.sprites.dynamic.npc;

import java.security.SecureRandom;

import dimensions.client.engine.spriteinterfaces.NPC;
import dimensions.client.engine.spriteinterfaces.Sprite;
import dimensions.client.game.sprites.GenericSprite;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;

public class SimpleNPC extends GenericSprite implements NPC
{
	private final SecureRandom rand = new SecureRandom();
	private double nextX = 1;
	private double nextY = 1;
	
	public SimpleNPC()
	{
		setX(200);
		setY(200);
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
	public void move()
	{
		setX(nextX);
		setY(nextY);
	}

	@Override
	public double getLife()
	{
		return 100;
	}

	@Override
	public void act()
	{
		nextX += rand.nextInt(1)-0.5;
		nextY += nextX/10;

		if(getX() < 0)
			nextX = 5;
		else if(getX() > 600)
			nextX = 5;
		
		if(getY() < 0)
			nextY = 5;
		else if(getY() > 600)
			nextY = 5;
	}

	@Override
	public void move(double x, double y)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWorldX()
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setWorldZ()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWorldY()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasFixedScreenPosition()
	{
		return false;
	}

	@Override
	public int compareTo(Sprite o)
	{
		// TODO Auto-generated method stub
		return 0;
	}


}
