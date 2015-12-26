package dimensions.client.game.sprites.statics;

import dimensions.client.engine.AbstractSprite;
import dimensions.client.engine.spriteinterfaces.Sprite;

public class Mud extends AbstractSprite
{

	public Mud()
	{
		super(Mud.class.getResource("/dimensions/client/game/assets/images/textures/mud.png").toString());
//		URL imageURL = Mud.class.getResource("/dimensions/client/game/assets/images/textures/mud.png");
//		final String path = imageURL.toString();
//		image = new Image(path);
	}


	@Override
	public boolean isReadyToRemove()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getZ()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int compareTo(Sprite o)
	{
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void setX(double x)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setY(double y)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setZ(double z)
	{
		// TODO Auto-generated method stub
		
	}

}
