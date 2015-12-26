package dimensions.client.engine;

import java.util.Spliterator;

import dimensions.client.engine.spriteinterfaces.Collidable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Physics implements EventHandler<ActionEvent>
{
	private final SpriteManager spriteManager;
	
	public Physics(SpriteManager spriteManager)
	{
		this.spriteManager = spriteManager;
	}

	private void checkForCollisons()
	{
		final Spliterator<Collidable> splitA = spriteManager.getCollidables();
		final Spliterator<Collidable> splitB = spriteManager.getCollidables();
	}

	private void moveMoveables()
	{
		spriteManager.getMoveables().forEachRemaining(m -> m.move());
	}
	
	@Override
	public void handle(ActionEvent event)
	{
		moveMoveables();
		checkForCollisons();
	}

}
