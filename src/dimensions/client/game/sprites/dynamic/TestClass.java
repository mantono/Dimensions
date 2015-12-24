package dimensions.client.game.sprites.dynamic;

import dimensions.client.engine.spriteinterfaces.Moveable;
import dimensions.client.game.sprites.GenericSprite;

public class TestClass extends GenericSprite implements Moveable
{
	private int direction = 1;

	public TestClass()
	{
		super("mud.png");
		setX(100);
		setY(160);
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
}
