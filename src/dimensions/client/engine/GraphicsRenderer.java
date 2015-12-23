package dimensions.client.engine;

import java.util.Set;

import dimensions.client.engine.spriteinterfaces.Sprite;
import javafx.scene.canvas.GraphicsContext;

public class GraphicsRenderer implements Runnable
{
	private final GraphicsContext graphics;
	private final Set<Sprite> sprites;
	
	protected GraphicsRenderer(final GraphicsContext graphics, final Set<Sprite> sprites)
	{
		this.graphics = graphics;
		this.sprites = sprites;
	}

	@Override
	public void run()
	{
		for(Sprite sprite : sprites)
			graphics.drawImage(sprite.getImage(), sprite.getX(), sprite.getY());
	}

}
