package dimensions.client.game.sprites.dynamic;

import dimensions.client.engine.AbstractSprite;
import dimensions.client.engine.spriteinterfaces.Moveable;
import dimensions.client.engine.spriteinterfaces.Sprite;
import dimensions.client.game.sprites.statics.Mud;
import javafx.geometry.Bounds;

public class TestClass extends AbstractSprite implements Moveable
{
	private int direction = 1;

	public TestClass()
	{
		super(Mud.class.getResource("/dimensions/client/game/assets/images/textures/mud.png").toString());
		setX(100);
		setY(160);
	}

	@Override
	public boolean isOutsideScreen()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int distanceFromScreen()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isReadyToRemove()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int compareTo(Sprite arg0)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void move()
	{
		if(getX() > 500)
			direction = -1;
		else if(getX() < 0)
			direction = 1;
		
		setX(getX()+0.5*direction);
		setY(Math.sqrt(getX()));
	}

	@Override
	public Bounds getBounds()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getZ()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
