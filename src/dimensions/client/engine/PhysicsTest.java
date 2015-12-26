package dimensions.client.engine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PhysicsTest
{
	private SpriteManager spriteManager;
	private Physics physics;

	@Before
	public void setUp() throws Exception
	{
		spriteManager = new SpriteManager();
		physics = new Physics(spriteManager);
	}

	@Test
	public void testCalculateInterpolation()
	{
		final long oneSecondDividedBySixty = Physics.ONE_SECOND / 60;
		final double interpolation = physics.calculateInterpolation(oneSecondDividedBySixty);
		assertEquals(1, interpolation, 0.0001);
		
	}

}
