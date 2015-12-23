package dimensions.client.game.sprites.dynamic;

import java.util.EnumSet;

import dimensions.client.engine.spriteinterfaces.Player;
import dimensions.client.game.sprites.GenericSprite;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
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
		setOnKeyPressed(new KeyPressedHandler());
		setOnKeyReleased(new KeyReleasedHandler());
	}

	@Override
	public void move()
	{
		if(direction == 0)
		{
			speed = 0;
			previousDirection = direction;
			return;
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
	}

	@Override
	public double getLife()
	{
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public Bounds[] getHitBoxes()
	{
		// TODO Auto-generated method stub
		return null;
	}

	class KeyPressedHandler implements EventHandler<KeyEvent>
	{

		@Override
		public void handle(KeyEvent event)
		{
			switch(event.getCode())
			{
				case DOWN: direction = DOWN; break;
				case UP: direction = UP; break;
				case LEFT: direction = LEFT; break;
				case RIGHT: direction = RIGHT;break;
				default: return;
			}				
		}

	}

	class KeyReleasedHandler implements EventHandler<KeyEvent>
	{

		private final EnumSet<KeyCode> arrowKeys = EnumSet.of(KeyCode.LEFT, KeyCode.RIGHT, KeyCode.DOWN, KeyCode.UP);
		
		@Override
		public void handle(KeyEvent event)
		{
			if(arrowKeys.contains(event.getCode()))
				direction = 0;
		}

	}
}
