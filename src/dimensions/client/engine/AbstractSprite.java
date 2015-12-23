package dimensions.client.engine;

import dimensions.client.engine.spriteinterfaces.Sprite;
import javafx.scene.image.ImageView;

public abstract class AbstractSprite extends ImageView implements Sprite
{	
	private final long timeCreated = System.currentTimeMillis();
	
	protected AbstractSprite(final String fileName)
	{
		super(fileName);
	}
	
	@Override
	public long timeCreated()
	{
		return timeCreated;
	}
}
