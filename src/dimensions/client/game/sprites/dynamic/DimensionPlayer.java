package dimensions.client.game.sprites.dynamic;

import dimensions.client.engine.GameSettings;
import dimensions.client.engine.Physics;
import dimensions.client.engine.spriteinterfaces.Player;
import dimensions.client.engine.spriteinterfaces.Sprite;
import dimensions.client.game.sprites.GenericSprite;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;

public class DimensionPlayer extends GenericSprite implements Player
{
	private double velocityX, velocityY;
	private boolean leftPressed, rightPressed, upPressed, downPressed;
	private final float acceleration = 0.1f;
	private long lastMoved = System.nanoTime();

	public DimensionPlayer()
	{
		super("player.png");
		velocityX = velocityY = 0;
		leftPressed = rightPressed = upPressed = downPressed = false;
		centerOnScreen();
	}	

	@Override
	public void render(GraphicsContext context)
	{
		super.render(context);
	}

	@Override
	public void updateVelocity(Physics physics)
	{

		if(leftPressed)
			velocityX -= acceleration;
		else if(rightPressed)
			velocityX += acceleration;
		else
			velocityX = normalize(velocityX);

		if(downPressed)
			velocityY += acceleration;
		else if(upPressed)
			velocityY -= acceleration;
		else
			velocityY = normalize(velocityY);

	}

	private double normalize(double velocity)
	{
		return velocity * 0.8;
	}

	@Override
	public void setX(double x)
	{
		setWorldX(x);
	}

	@Override
	public void setY(double y)
	{
		setWorldY(y);
	}

	@Override
	public double getLife()
	{
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public Rectangle2D[] getHitBoxes()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean intersects(Rectangle2D[] bounds)
	{
		return false;
	}

	@Override
	public boolean hasPixelCollision(PixelReader pixels)
	{
		return false;
	}

	@Override
	public void rightPressed()
	{
		rightPressed = true;
		leftPressed = false;
	}

	@Override
	public void leftPressed()
	{
		leftPressed = true;
		rightPressed = false;
	}

	@Override
	public void upPressed()
	{
		upPressed = true;
		downPressed = false;
	}

	@Override
	public void downPressed()
	{
		downPressed = true;
		upPressed = false;
	}

	@Override
	public int compareTo(Sprite o)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void stop()
	{
		velocityX = velocityY = 0;
	}

	@Override
	public void rightReleased()
	{
		rightPressed = false;
	}

	@Override
	public void leftReleased()
	{
		leftPressed = false;
	}

	@Override
	public void upReleased()
	{
		upPressed = false;
	}

	@Override
	public void downReleased()
	{
		downPressed = false;
	}

	@Override
	public boolean hasFixedScreenPosition()
	{
		return true;
	}

	@Override
	public double getVelocityX()
	{
		return velocityX;
	}

	@Override
	public double getVelocityY()
	{
		return velocityY;
	}

	@Override
	public void setVelocityX(double velocity)
	{
		this.velocityX = velocity;
	}

	@Override
	public void setVelocityY(double velocity)
	{
		this.velocityX = velocity;
	}

	@Override
	public void centerOnScreen()
	{
		final double x = GameSettings.widthPlayableArea / 2 - getWidth() / 2;
		final double y = GameSettings.heightPlayableArea / 2 - getHeight() / 2;
		super.setX(x);
		super.setY(y);
	}

	@Override
	public synchronized long updateLastMoved(long nanoSeconds)
	{
		final long diff = nanoSeconds - lastMoved;
		lastMoved = nanoSeconds;
		return diff;
	}
}
