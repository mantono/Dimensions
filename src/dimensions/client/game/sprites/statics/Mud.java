package dimensions.client.game.sprites.statics;

import dimensions.client.engine.AbstractSprite;
import dimensions.client.engine.spriteinterfaces.Sprite;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;

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
	public Bounds getBounds()
	{
		return getBoundsInLocal();
	}

}
