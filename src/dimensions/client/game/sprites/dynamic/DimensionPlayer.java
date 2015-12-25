package dimensions.client.game.sprites.dynamic;

import java.util.EnumSet;

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
	private final static byte DOWN = 1;
	private final static byte RIGHT = 2;
	private final static byte LEFT = 3;
	private final static byte UP = 4;
	private byte direction = 0;
	private byte previousDirection = 0;
	private float speed = 0;

	public DimensionPlayer()
	{
		super("player.png");
//		setOnKeyPressed(new KeyPressedHandler());
//		setOnKeyReleased(new KeyReleasedHandler());
	}

	@Override
	public void move()
	{
		if(direction == 0)
		{
			speed /= 1.3;
			previousDirection = direction;
		}
		
		if(direction != previousDirection)
			speed = 0.05f;
		else
			speed += 0.05; 
		previousDirection = direction;
		
		if(direction == DOWN)
			setY(getY()+1*speed);
		else if(direction == UP)
			setY(getY()-1*speed);
		else if(direction == LEFT)
			setX(getX()-1*speed);
		else if(direction == RIGHT)
			setX(getX()+1*speed);
		
		if(speed > 1.1)
			speed = 1.1f;
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
	public void moveRight()
	{
		direction = RIGHT;
	}

	@Override
	public void moveLeft()
	{
		direction = LEFT;		
	}

	@Override
	public void moveUp()
	{
		direction = UP;
	}

	@Override
	public void moveDown()
	{
		direction = DOWN;
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
		direction = 0;
	}

	@Override
	public void handle(KeyEvent event)
	{
		if(event.getCode().isArrowKey())
			handleArrowKeyInput(event);
	}

	private void handleArrowKeyInput(KeyEvent event)
	{
		switch(event.getCode())
		{
			case LEFT: moveLeft(); break;
			case RIGHT: moveRight(); break;
			case DOWN: moveDown(); break;
			case UP: moveUp(); break;
			default: stop();
		}
	}
}
