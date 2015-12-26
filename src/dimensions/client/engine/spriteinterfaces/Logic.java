package dimensions.client.engine.spriteinterfaces;

import dimensions.client.engine.SpriteManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Logic implements EventHandler<ActionEvent>
{
	private final SpriteManager spriteManager;
	
	public Logic(SpriteManager spriteManager)
	{
		this.spriteManager = spriteManager;
	}

	@Override
	public void handle(ActionEvent event)
	{
		spriteManager.getNpcs().forEachRemaining(n -> n.act());
	}	
}
