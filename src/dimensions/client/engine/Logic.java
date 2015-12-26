package dimensions.client.engine;

import dimensions.client.engine.SpriteManager;

public class Logic implements Runnable
{
	private final SpriteManager spriteManager;
	
	public Logic(SpriteManager spriteManager)
	{
		this.spriteManager = spriteManager;
	}

	@Override
	public void run()
	{
		spriteManager.getNpcs().forEachRemaining(n -> n.act());
	}	
}
