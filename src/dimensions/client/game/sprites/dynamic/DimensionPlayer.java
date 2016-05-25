package dimensions.client.game.sprites.dynamic;

import dimensions.client.engine.GameSettings;
import dimensions.client.engine.physics.Coordinate2D;
import dimensions.client.engine.physics.PhysicsEngine;
import dimensions.client.engine.physics.Velocity;
import dimensions.client.engine.spriteinterfaces.Collidable;
import dimensions.client.engine.spriteinterfaces.Player;
import dimensions.client.engine.spriteinterfaces.Sprite;
import dimensions.client.game.sprites.GenericSprite;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;

public class DimensionPlayer extends GenericSprite implements Player
{
	private Point2D velocity = new Point2D(0, 0);
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
	public void updateVelocity()
	{
		if(leftPressed)
			velocity = velocity.subtract(acceleration, 0);
		else if(rightPressed)
			velocity = velocity.add(acceleration, 0);
		else
			velocity = velocity.subtract(0.8, 0);

		if(downPressed)
			velocity = velocity.subtract(0, acceleration);
		else if(upPressed)
			velocity = velocity.add(0, acceleration);
		else
			velocity = velocity.subtract(0, 0.8);
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
		return new Bounds[] {new BoundingBox(getPosition().getX()+8, getPosition().getY()+4, 38, 80)};
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
		velocity = new Point2D(0, 0);
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
	public synchronized long updateLastMoved(long nanoSeconds)
	{
		final long diff = nanoSeconds - lastMoved;
		lastMoved = nanoSeconds;
		return diff;
	}

	@Override
	public Point2D getVelocity()
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

	@Override
	public void setVelocity(Point2D velocity)
	{
		this.velocity = velocity;
	}
}
