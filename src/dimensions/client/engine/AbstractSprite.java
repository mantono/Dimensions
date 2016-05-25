package dimensions.client.engine;

import dimensions.client.engine.spriteinterfaces.Sprite;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.image.Image;

public abstract class AbstractSprite implements Sprite
{
	private final long timeCreated = System.currentTimeMillis();
	private final Image texture;
	private Point2D screenPosition = new Point2D(0, 0);
	private Point3D worldPosition = new Point3D(0, 0, 0);

	protected AbstractSprite(final String fileName)
	{
		texture = new Image(fileName);
	}

	@Override
	public Image getTexture()
	{
		return texture;
	}

	@Override
	public void centerOnScreen()
	{
		final double x = GameSettings.widthPlayableArea / 2 - getBounds().getWidth() / 2;
		final double y = GameSettings.heightPlayableArea / 2 - getBounds().getHeight() / 2;
		setPosition(new Point2D(x, y));
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
	public Point2D getPosition()
	{
		return screenPosition;
	}

	@Override
	public void setPosition(Point2D position)
	{
		this.screenPosition = position;
	}

	@Override
	public Point3D getWorldCoordinates()
	{
		return worldPosition;
	}

	@Override
	public long timeCreated()
	{
		return timeCreated;
	}

	@Override
	public void move(double x, double y)
	{
		final Point2D movement = new Point2D(x, y);
		this.screenPosition = screenPosition.add(movement);
	}
}
