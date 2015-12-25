package dimensions.client.game.sprites.dynamic;

import java.util.EnumSet;

import dimensions.client.engine.GameSettings;
import dimensions.client.engine.spriteinterfaces.Player;
import dimensions.client.engine.spriteinterfaces.Sprite;
import dimensions.client.game.sprites.GenericSprite;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class DimensionPlayer extends GenericSprite implements Player
{
	private float velocityX, velocityY;
	private boolean leftPressed, rightPressed, upPressed, downPressed;
	private final float maxVelocity = 1.7f;
	private final float acceleration = maxVelocity/30;

	public DimensionPlayer()
	{
		super("player.png");
		velocityX = velocityY = 0;
		leftPressed = rightPressed = upPressed = downPressed = false;
//		setOnKeyPressed(new KeyPressedHandler());
//		setOnKeyReleased(new KeyReleasedHandler());
	}

	@Override
	public void move()
	{
		if(leftPressed)
			velocityX -= acceleration;
		else if(rightPressed)
			velocityX += acceleration;
		else
			velocityX = normalize(velocityX);
		
		if(downPressed)
			velocityY += acceleration;
		else if(upPressed)
			velocityY -= acceleration;
		else
			velocityY = normalize(velocityY);
		
		if(velocityX > maxVelocity)
			velocityX = maxVelocity;
		else if(velocityX < -maxVelocity)
			velocityX = -maxVelocity;
		if(velocityY > maxVelocity)
			velocityY = maxVelocity;
		else if(velocityY < -maxVelocity)
			velocityY = -maxVelocity;
		
		move(velocityX, velocityY);		
	}

	private float normalize(float velocity)
	{
		return velocity*0.3f;
	}

	@Override
	public double getLife()
	{
		// TODO Auto-generated method stub
		return 100;
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
		return false;
	}

	@Override
	public boolean hasPixelCollision(PixelReader pixels)
	{
		return false;
	}

	@Override
	public void setWorldX()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWorldY()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWorldZ()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rightPressed()
	{
		//direction = RIGHT;
		rightPressed = true;
		leftPressed = false;
	}

	@Override
	public void leftPressed()
	{
		leftPressed = true;
		rightPressed = false;
	}

	@Override
	public void upPressed()
	{
		upPressed = true;
		downPressed = false;
	}

	@Override
	public void downPressed()
	{
		downPressed = true;
		upPressed = false;
	}

	@Override
	public int compareTo(Sprite o)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void stop()
	{
		velocityX = velocityY = 0;
	}

	@Override
	public void rightReleased()
	{
		rightPressed = false;
	}

	@Override
	public void leftReleased()
	{
		leftPressed = false;
	}

	@Override
	public void upReleased()
	{
		upPressed = false;
	}

	@Override
	public void downReleased()
	{
		downPressed = false;
	}

	@Override
	public boolean hasFixedScreenPosition()
	{
		return true;
	}

}
