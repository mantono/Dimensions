package dimensions.client.game.sprites.dynamic;

import dimensions.client.engine.GameSettings;
import dimensions.client.engine.physics.Coordinate2D;
import dimensions.client.engine.physics.Physics;
import dimensions.client.engine.physics.Velocity;
import dimensions.client.engine.spriteinterfaces.Collidable;
import dimensions.client.engine.spriteinterfaces.Player;
import dimensions.client.engine.spriteinterfaces.Sprite;
import dimensions.client.game.sprites.GenericSprite;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;

public class DimensionPlayer extends GenericSprite implements Player
{
	//private double velocityX, velocityY;
	private final Velocity velocity = new Velocity();
	private boolean leftPressed, rightPressed, upPressed, downPressed;
	private final float acceleration = 0.1f;
	private long lastMoved = System.nanoTime();
	private boolean remove = false;

	public DimensionPlayer()
	{
		super("player.png");
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
			velocity.accelerateX(-acceleration);
		else if(rightPressed)
			velocity.accelerateX(acceleration);
		else
			velocity.reduceX(0.8);

		if(downPressed)
			velocity.accelerateY(acceleration);
		else if(upPressed)
			velocity.accelerateY(-acceleration);
		else
			velocity.reduceY(0.8);
	}


	@Override
	public double getLife()
	{
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public Bounds[] getHitBoxes()
	{
		return new Bounds[] {new BoundingBox(getScreenCoordinates().getX()+8, getScreenCoordinates().getY()+4, 38, 80)};
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
	public void stop()
	{
		velocity.reduce(0);
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
	public void centerOnScreen()
	{
		final double x = GameSettings.widthPlayableArea / 2 - getWidth() / 2;
		final double y = GameSettings.heightPlayableArea / 2 - getHeight() / 2;
		getScreenCoordinates().setX(x);
		getScreenCoordinates().setY(y);
	}

	@Override
	public synchronized long updateLastMoved(long nanoSeconds)
	{
		final long diff = nanoSeconds - lastMoved;
		lastMoved = nanoSeconds;
		return diff;
	}

	@Override
	public Velocity getVelocity()
	{
		return velocity;
	}

	@Override
	public double getMass()
	{
		return 1;
	}

	@Override
	public void onCollision(Collidable other)
	{
		//remove = true;
	}
	
	@Override
	public boolean isReadyToRemove()
	{
		return false;
	}
}
