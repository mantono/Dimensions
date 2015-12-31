package dimensions.client.engine;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import dimensions.client.engine.spriteinterfaces.Collidable;
import dimensions.client.engine.spriteinterfaces.Moveable;
import dimensions.client.engine.spriteinterfaces.NPC;
import dimensions.client.engine.spriteinterfaces.Player;
import dimensions.client.engine.spriteinterfaces.Sprite;

public class SpriteManager implements Runnable
{
	private Player player;

	private final Set<Sprite> sprites = Collections.newSetFromMap(new ConcurrentHashMap<Sprite, Boolean>());
	private final Set<NPC> npcs = Collections.newSetFromMap(new ConcurrentHashMap<NPC, Boolean>());
	private final Set<Moveable> moveables = Collections.newSetFromMap(new ConcurrentHashMap<Moveable, Boolean>());
	private final Set<Collidable> collidables = Collections.newSetFromMap(new ConcurrentHashMap<Collidable, Boolean>());

	private final BlockingQueue<Sprite> spriteQueue = new ArrayBlockingQueue<Sprite>(1000);

	@Override
	public void run()
	{
		pollQueues();
		removeSprites();
	}

	private void pollQueues()
	{
		if(spriteQueue.remainingCapacity() == 0)
			System.out.println("Warning: Queue has reached the maximum capacity.");
		final Sprite sprite = spriteQueue.poll();
		if(sprite == null)
			return;

		sprites.add(sprite);
		System.out.println(sprite + " added.");

		if(sprite instanceof Moveable)
			moveables.add((Moveable) sprite);
		if(sprite instanceof NPC)
			npcs.add((NPC) sprite);
		if(sprite instanceof Collidable)
			collidables.add((Collidable) sprite);
		if(sprite instanceof Player && player == null)
			player = (Player) sprite;
	}

	public Spliterator<Sprite> getSprites()
	{
		return sprites.spliterator();
	}

	public Spliterator<NPC> getNpcs()
	{
		return npcs.spliterator();
	}

	public Spliterator<Collidable> getCollidables()
	{
		return collidables.spliterator();
	}

	public Spliterator<Moveable> getMoveables()
	{
		return moveables.spliterator();
	}

	public Player getPlayer()
	{
		return player;
	}

	public void addSprite(Sprite sprite) throws InterruptedException
	{
		spriteQueue.offer(sprite, 10l, TimeUnit.MILLISECONDS);
	}

	private void removeSprites()
	{
		Iterator<Sprite> iterator = sprites.iterator();
		while(iterator.hasNext())
		{
			final Sprite sprite = iterator.next();
			if(sprite.isReadyToRemove())
			{
				npcs.remove(sprite);
				moveables.remove(sprite);
				collidables.remove(sprite);
				iterator.remove();
				System.out.println(sprite + " removed (" + sprites.size() + ")");
			}
		}
	}
}
