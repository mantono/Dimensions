package dimensions.client.engine;

import dimensions.client.engine.physics.Coordinate2D;
import dimensions.client.engine.physics.Coordinate3D;
import dimensions.client.engine.physics.Velocity;
import dimensions.client.engine.spriteinterfaces.Sprite;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class AbstractSprite implements Sprite
{
	private final long timeCreated = System.currentTimeMillis();
	private final Image texture;
	private final Rectangle2D bounds;
	private final Coordinate2D screenPosition = new Coordinate2D();
	private final Coordinate3D worldPosition = new Coordinate3D();

	protected AbstractSprite(final String fileName)
	{
		texture = new Image(fileName);
		this.bounds = new Rectangle2D(screenPosition.getX(), screenPosition.getY(), texture.getWidth(), texture.getHeight());
	}

	protected Image getTexture()
	{
		return texture;
	}

	@Override
	public void centerOnScreen()
	{
		final double x = GameSettings.widthPlayableArea / 2 - getWidth() / 2;
		final double y = GameSettings.heightPlayableArea / 2 - getHeight() / 2;
		screenPosition.setX(x);
		screenPosition.setY(y);
	}
	
	@Override
	public Coordinate2D getScreenCoordinates()
	{
		return screenPosition;
	}

	@Override
	public Coordinate3D getWorldCoordinates()
	{
		return worldPosition;
	}

	@Override
	public double getWidth()
	{
		return texture.getWidth();
	}

	@Override
	public double getHeight()
	{
		return texture.getHeight();
	}

	@Override
	public void render(GraphicsContext context)
	{
		context.drawImage(texture, screenPosition.getX(), screenPosition.getY());
	}

	@Override
	public boolean isOutsideScreen()
	{
		return screenPosition.getX() < 0 || screenPosition.getY() < 0 || screenPosition.getX() > GameSettings.widthWindow || screenPosition.getY() > GameSettings.heightWindow;
	}

	@Override
	public double distanceFromScreen()
	{
		if(!isOutsideScreen())
			return 0;
		double distanceX = 0;
		double distanceY = 0;

		if(screenPosition.getX() < 0)
			distanceX = screenPosition.getX();
		else if(screenPosition.getX() > 0)
			distanceX = screenPosition.getX() - GameSettings.widthWindow;

		if(screenPosition.getY() < 0)
			distanceY = screenPosition.getY();
		else if(screenPosition.getY() > 0)
			distanceY = screenPosition.getY() - GameSettings.heightWindow;

		return distanceX > distanceY ? distanceX : distanceY;

	}
	
	

	@Override
	public long timeCreated()
	{
		return timeCreated;
	}

	@Override
	public Rectangle2D getBounds()
	{
		return bounds;
	}

	@Override
	public void move(double x, double y)
	{
		final Velocity v = new Velocity(x, y);
		screenPosition.move(v);
	}
}
