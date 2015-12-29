package dimensions.client.game.sprites.statics;

import dimensions.client.engine.AbstractSprite;
import dimensions.client.engine.physics.Coordinate2D;
import dimensions.client.engine.physics.Coordinate3D;
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
}
