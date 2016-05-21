package dimensions.client.engine.physics;

import static org.junit.Assert.*;

import java.util.PriorityQueue;

import org.junit.Test;

import dimensions.client.engine.GameSettings;
import dimensions.client.engine.spriteinterfaces.Collidable;
import dimensions.client.engine.spriteinterfaces.Moveable;
import dimensions.client.game.sprites.GenericSprite;
import dimensions.client.game.sprites.dynamic.npc.SimpleNPC;

public class CollisionTableTest
{

	@Test
	public void testGetCollidables()
	{
		CollisionTable ct = new CollisionTable();
		
		final Coordinate2D[] coords = new Coordinate2D[7];
		
		int n = 0;
		
		coords[n++] = new Coordinate2D(0, 0);
		coords[n++] = new Coordinate2D(10, 10);
		coords[n++] = new Coordinate2D(12, 15);
		coords[n++] = new Coordinate2D(100, 100);
		coords[n++] = new Coordinate2D(400, 600);
		coords[n++] = new Coordinate2D(399, 601);
		coords[n++] = new Coordinate2D(1000, 1000);
		
		GameSettings.widthWindow = 1200;
		GameSettings.heightWindow = 1480;
		
		for(int i = 0; i < coords.length-1; i++)
		{
			final int hash1 = ct.computeHash(coords[i]);
			final int hash2 = ct.computeHash(coords[i+1]);
			final String err = coords[i] + " --> " + hash1 + ", " + coords[i+1] + " --> " + hash2;  
			assertTrue(err, hash1 <= hash2);
		}
	}

}
