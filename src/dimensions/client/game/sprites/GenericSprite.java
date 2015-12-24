package dimensions.client.game.sprites;

import dimensions.client.engine.AbstractSprite;
import dimensions.client.engine.GameSettings;

public class GenericSprite extends AbstractSprite
{
	public final static String TEXTURES = "/dimensions/client/game/assets/images/textures/";

	protected GenericSprite(String fileName)
	{
		super(GenericSprite.class.getResource(TEXTURES + fileName).toString());
	}
	
	protected GenericSprite()
	{
		this("red.png");
	}

	@Override
	public boolean isReadyToRemove()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
