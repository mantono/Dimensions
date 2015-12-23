package dimensions.client.game.sprites;

import dimensions.client.engine.AbstractSprite;
import dimensions.client.engine.spriteinterfaces.Sprite;
import dimensions.client.game.sprites.statics.Mud;
import javafx.geometry.Bounds;

public class GenericSprite extends AbstractSprite
{
	public final static String TEXTURES = "/dimensions/client/game/assets/images/textures/";

	protected GenericSprite(String fileName)
	{
		super(GenericSprite.class.getResource(TEXTURES + fileName).toString());
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
}
