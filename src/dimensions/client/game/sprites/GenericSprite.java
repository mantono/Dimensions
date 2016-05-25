package dimensions.client.game.sprites;

import dimensions.client.engine.AbstractSprite;
import dimensions.client.engine.physics.Coordinate2D;
import dimensions.client.engine.physics.Coordinate3D;
import dimensions.client.engine.physics.Velocity;
import dimensions.client.engine.spriteinterfaces.Sprite;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

public class GenericSprite extends AbstractSprite
{
	public final static String TEXTURES = "/dimensions/client/game/assets/images/textures/";

	protected GenericSprite(String fileName)
	{
		super(GenericSprite.class.getResource(TEXTURES + fileName).toString());
	}
	
	protected GenericSprite()
	{
		this("red.png");
	}

	@Override
	public boolean isReadyToRemove()
	{
		return 1000 < distanceFromScreen();
	}

	@Override
	public Bounds getBounds()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
