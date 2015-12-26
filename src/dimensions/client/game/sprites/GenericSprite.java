package dimensions.client.game.sprites;

import dimensions.client.engine.AbstractSprite;

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
		return 1000 < distanceFromScreen();
	}

}
