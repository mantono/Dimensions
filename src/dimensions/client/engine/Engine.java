package dimensions.client.engine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import dimensions.client.engine.spriteinterfaces.Collidable;
import dimensions.client.engine.spriteinterfaces.Moveable;
import dimensions.client.engine.spriteinterfaces.NPC;
import dimensions.client.engine.spriteinterfaces.Player;
import dimensions.client.engine.spriteinterfaces.Sprite;
import dimensions.client.game.sprites.dynamic.DimensionPlayer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Engine implements EventHandler<ActionEvent>
{
	private final Stage stage;
	private final Scene scene;
	private final Group root;
	private final Canvas canvas;
	private final GraphicsContext renderer;

	private final GameSettings settings;

	private Player player;

	private final Set<Sprite> sprites = new HashSet<Sprite>();
	private final Set<NPC> npcs = new HashSet<NPC>();
	private final Set<Moveable> moveables = new HashSet<Moveable>();
	private final Set<Collidable> collidables = new HashSet<Collidable>();

	private final BlockingQueue<NPC> npcQueue = new PriorityBlockingQueue<NPC>(50);
	private final BlockingQueue<Moveable> moveableQueue = new PriorityBlockingQueue<Moveable>(40);
	private final BlockingQueue<Sprite> spriteQueue = new PriorityBlockingQueue<Sprite>(60);

	private final Map<KeyCode, Method> keyBindings = new HashMap<KeyCode, Method>();

	public Engine(Stage stage)
	{
		this(stage, new GameSettings(60, 800, 600, 32));
	}

	public Engine(Stage stage, GameSettings settings)
	{
		this.stage = stage;
		this.settings = settings;

		this.root = new Group();
		this.scene = new Scene(root);
		this.stage.setScene(scene);

		this.canvas = new Canvas(settings.width, settings.height);
		this.renderer = canvas.getGraphicsContext2D();
		this.root.getChildren().add(canvas);

		scene.setOnKeyPressed(new KeyPressedHandler());
		scene.setOnKeyReleased(new KeyReleasedHandler());
		// canvas.setOnKeyPressed(new KeyEventHandler());
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
		actNPC();
		checkForCollisons();
		removeSprites();
		updateGraphics();
		if(player != null)
		{
			root.setTranslateX(player.getX());
			root.setTranslateY(player.getY());
		}

	}

	private void actNPC()
	{
		for(NPC npc : npcs)
			npc.act();
	}

	public void updateGraphics()
	{
		renderer.clearRect(0, 0, settings.width, settings.height);
		for(Sprite sprite : sprites)
			sprite.render(renderer);
	}

	private void removeSprites()
	{
		Iterator<Sprite> iterStatics = sprites.iterator();
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
		for(Moveable sprite : moveables)
			sprite.move();
	}

	private void pollQueues()
	{
		NPC npc = npcQueue.poll();
		if(npc != null)
		{
			npcs.add(npc);
			collidables.add(npc);
			moveables.add(npc);
			sprites.add(npc);
		}

		Moveable moveable = moveableQueue.poll();
		if(moveable != null)
		{
			moveables.add(moveable);
			sprites.add(moveable);
		}

		Sprite sprite = spriteQueue.poll();
		if(sprite != null)
		{
			sprites.add(sprite);
		}

	}

	public void addSprite(Sprite sprite)
	{
		spriteQueue.offer(sprite);
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

			try
			{
				Class<? extends Player> c = player.getClass();
				keyBindings.put(KeyCode.RIGHT, c.getMethod("moveRight"));
				keyBindings.put(KeyCode.LEFT, c.getMethod("moveLeft"));
				keyBindings.put(KeyCode.UP, c.getMethod("moveUp"));
				keyBindings.put(KeyCode.DOWN, c.getMethod("moveDown"));
			}
			catch(NoSuchMethodException | SecurityException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class KeyPressedHandler implements EventHandler<KeyEvent>
	{

		@Override
		public void handle(KeyEvent event)
		{
			if(event.getEventType() != KeyEvent.KEY_PRESSED)
				return;
			final Method method = keyBindings.get(event.getCode());
			if(method == null)
				return;
			try
			{
				method.invoke(player);
			}
			catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	class KeyReleasedHandler implements EventHandler<KeyEvent>
	{

		private final EnumSet<KeyCode> arrowKeys = EnumSet.of(KeyCode.LEFT, KeyCode.RIGHT, KeyCode.DOWN, KeyCode.UP);

		@Override
		public void handle(KeyEvent event)
		{
			if(arrowKeys.contains(event.getCode()) && event.getEventType() == KeyEvent.KEY_RELEASED)
				player.stop();
		}

	}
}
