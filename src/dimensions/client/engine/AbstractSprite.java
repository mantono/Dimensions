package dimensions.client.engine;

import dimensions.client.engine.spriteinterfaces.Sprite;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class AbstractSprite implements Sprite
{
	private final long timeCreated = System.currentTimeMillis();
	private final Image texture;
	private final Rectangle2D bounds;

	private double x, y, z, worldX, worldY, worldZ;

	protected AbstractSprite(final String fileName)
	{
		texture = new Image(fileName);
		this.bounds = new Rectangle2D(x, y, texture.getWidth(), texture.getHeight());
	}

	protected Image getTexture()
	{
		return texture;
	}

	@Override
	public void centerOnScreen()
	{
		final double x = GameSettings.widthPlayableArea/2 - getWidth()/2;
		final double y = GameSettings.heightPlayableArea/2 - getHeight()/2;
		setXY(x, y);
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
		context.drawImage(texture, x, y);
	}

	@Override
	public boolean isOutsideScreen()
	{
		return x < 0 || y < 0 || x > GameSettings.widthWindow || y > GameSettings.heightWindow;
	}

	@Override
	public double distanceFromScreen()
	{
		if(!isOutsideScreen())
			return 0;
		double distanceX = 0;
		double distanceY = 0;

		if(x < 0)
			distanceX = x;
		else if(x > 0)
			distanceX = x - GameSettings.widthWindow;

		if(y < 0)
			distanceY = y;
		else if(y > 0)
			distanceY = y - GameSettings.heightWindow;

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
	public double getX()
	{
		return x;
	}

	@Override
	public double getY()
	{
		return y;
	}

	@Override
	public double getZ()
	{
		return z;
	}

	@Override
	public double getWorldX()
	{
		return worldX;
	}

	@Override
	public double getWorldY()
	{
		return worldY;
	}

	@Override
	public double getWorldZ()
	{
		return worldZ;
	}

	@Override
	public void setX(double x)
	{
		this.x = x;
	}

	@Override
	public void setY(double y)
	{
		this.y = y;
	}

	@Override
	public void setZ(double z)
	{
		this.z = z;
	}

	@Override
	public void move(double x, double y)
	{
		this.x += x;
		this.y += y;
	}
}
