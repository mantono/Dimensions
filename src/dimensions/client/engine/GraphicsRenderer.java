package dimensions.client.engine;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class GraphicsRenderer implements EventHandler<ActionEvent>
{
	private final Engine engine;
	
	public GraphicsRenderer(final Engine engine)
	{
		this.engine = engine;
	}
	
	@Override
	public void handle(ActionEvent event)
	{
		engine.pollQueues();
		engine.moveMoveables();
		engine.actNPC();
		//engine.checkForCollisons();
		//engine.removeSprites();
		engine.updateGraphics();
	}

}

