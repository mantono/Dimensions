package dimensions.client.engine;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import dimensions.client.engine.spriteinterfaces.Collidable;
import dimensions.client.engine.spriteinterfaces.Moveable;
import dimensions.client.engine.spriteinterfaces.NPC;
import dimensions.client.engine.spriteinterfaces.Player;
import dimensions.client.game.sprites.dynamic.DimensionPlayer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Engine implements EventHandler<ActionEvent>
{
	private final Stage stage;
	private final Scene scene;
	private final Group root, moveable, light;
	private final Canvas canvas;
	private final GraphicsContext renderer;

	private final GameSettings settings;
	private boolean keepRunning = true;

	private Player player;

	private final Set<AbstractSprite> statics = new HashSet<AbstractSprite>();
	private final Set<NPC> npcs = new HashSet<NPC>();
	private final Set<Moveable> moveableSprites = new HashSet<Moveable>();
	private final Set<Collidable> collidables = new HashSet<Collidable>();

	private final BlockingQueue<NPC> npcQueue = new PriorityBlockingQueue<NPC>(50);
	private final BlockingQueue<Moveable> moveableQueue = new PriorityBlockingQueue<Moveable>(40);
	private final BlockingQueue<AbstractSprite> staticSpriteQueue = new PriorityBlockingQueue<AbstractSprite>(60);

	public Engine(Stage stage)
	{
		this(stage, new GameSettings(60, 800, 600, 32));
	}

	public Engine(Stage stage, GameSettings settings)
	{
		this.stage = stage;
		this.settings = settings;

		this.moveable = new Group();
		this.light = new Group();
		this.root = new Group(moveable, light);

		this.scene = new Scene(root);
		this.stage.setScene(scene);

		this.canvas = new Canvas(settings.width, settings.height);
		this.renderer = canvas.getGraphicsContext2D();
		this.root.getChildren().add(canvas);

	}

	public void addNPC(final NPC npc)
	{
		npcQueue.offer(npc);
	}

	@Override
	public void handle(ActionEvent event)
	{
		pollQueues();
		moveMoveables();
		checkForCollisons();
		removeSprites();
		if(player != null)
		{
			root.setTranslateX(player.getX());
			root.setTranslateY(player.getY());
		}
		
	}

	// public void updateGraphics()
	// {
	// for(Sprite sprite : root.getChildren())
	// renderer.drawImage(sprite.getTexture(), sprite.getX(), sprite.getY());
	// }

	private void removeSprites()
	{
		Iterator<AbstractSprite> iterStatics = statics.iterator();
		while(iterStatics.hasNext())
			if(iterStatics.next().isReadyToRemove())
				iterStatics.remove();

		Iterator<NPC> iterNpc = npcs.iterator();
		while(iterNpc.hasNext())
			if(iterNpc.next().isReadyToRemove())
				iterNpc.remove();
	}

	private void checkForCollisons()
	{
		// TODO Auto-generated method stub

	}

	private void moveMoveables()
	{
		if(player != null)
			player.move();
		for(Moveable sprite : npcs)
			sprite.move();
		for(Moveable sprite : moveableSprites)
			sprite.move();
	}

	private void pollQueues()
	{
		NPC npc = npcQueue.poll();
		if(npc != null)
		{
			npcs.add(npc);
			collidables.add(npc);
			moveableSprites.add(npc);
		}

		Moveable moveable = moveableQueue.poll();
		if(moveable != null)
		{
			moveableSprites.add(moveable);
			root.getChildren().add((AbstractSprite) moveable);
		}

		Node sprite = staticSpriteQueue.poll();
		if(sprite != null)
		{
			root.getChildren().add(sprite);
		}

	}

	public void addStaticSprite(AbstractSprite sprite)
	{
		staticSpriteQueue.offer(sprite);
	}

	public void addMoveable(Moveable moveable)
	{
		moveableQueue.offer(moveable);
	}

	public void addPlayer(Player player)
	{
		if(this.player == null)
		{
			this.player = player;
			addMoveable(player);
		}
	}
}
