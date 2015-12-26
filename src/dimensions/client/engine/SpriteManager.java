package dimensions.client.engine;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import dimensions.client.engine.spriteinterfaces.Collidable;
import dimensions.client.engine.spriteinterfaces.Moveable;
import dimensions.client.engine.spriteinterfaces.NPC;
import dimensions.client.engine.spriteinterfaces.Player;
import dimensions.client.engine.spriteinterfaces.Sprite;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SpriteManager implements EventHandler<ActionEvent>
{
	private Player player;

	private final Set<Sprite> sprites = new HashSet<Sprite>();
	private final Set<NPC> npcs = new HashSet<NPC>();
	private final Set<Moveable> moveables = new HashSet<Moveable>();
	private final Set<Collidable> collidables = new HashSet<Collidable>();

	// private final Set<Class<? super Sprite>> spriteInterfaces = new
	// HashSet<Class<? super Sprite>>();
	// private final Map<Class<? super Sprite>, Set<? super Sprite>> spriteSets
	// = new HashMap<Class<? super Sprite>, Set<? super Sprite>>();

	// private final BlockingQueue<NPC> npcQueue = new
	// PriorityBlockingQueue<NPC>(50);
	// private final BlockingQueue<Moveable> moveableQueue = new
	// PriorityBlockingQueue<Moveable>(40);
	private final BlockingQueue<Sprite> spriteQueue = new PriorityBlockingQueue<Sprite>(60);

	@Override
	public void handle(ActionEvent event)
	{
		pollQueues();
		removeSprites();
	}

	// public void addNPC(final NPC npc)
	// {
	// npcQueue.offer(npc);
	// }

	private void pollQueues()
	{
		final Sprite sprite = spriteQueue.poll();
		if(sprite == null)
			return;

		sprites.add(sprite);

		if(sprite instanceof Moveable)
			moveables.add((Moveable) sprite);
		if(sprite instanceof NPC)
			npcs.add((NPC) sprite);
		if(sprite instanceof Collidable)
			collidables.add((Collidable) sprite);
		if(sprite instanceof Player && player == null)
			player = (Player) sprite;
		// NPC npc = npcQueue.poll();
		// if(npc != null)
		// {
		// npcs.add(npc);
		// collidables.add(npc);
		// moveables.add(npc);
		// sprites.add(npc);
		// }
		//
		// Moveable moveable = moveableQueue.poll();
		// if(moveable != null)
		// {
		// moveables.add(moveable);
		// sprites.add(moveable);
		// }
		//
		// Sprite sprite = spriteQueue.poll();
		// if(sprite != null)
		// {
		// sprites.add(sprite);
		// }

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

	public void addSprite(Sprite sprite)
	{
		spriteQueue.offer(sprite);
	}

	// public void addMoveable(Moveable moveable)
	// {
	// moveableQueue.offer(moveable);
	// }

	public void addPlayer(Player player)
	{
		if(this.player == null)
		{
			this.player = player;
			spriteQueue.offer(player);
			
//			inputs.createDefaultKeyBindings(player);
//			centerPlayerOnScreen();
		}
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
			}
		}
	}
}
