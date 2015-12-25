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
import dimensions.client.engine.spriteinterfaces.Sprite;
import dimensions.client.game.sprites.GenericSprite;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Engine implements EventHandler<ActionEvent>
{
	private final Stage stage;
	private final Scene scene;
	private final Group root;
	private final Canvas mainCanvas, playerCanvas, hudCanvas;
	private final GraphicsContext mainRenderer, playerRenderer, hudRenderer;

	private final GameSettings settings;
	
	private final Timeline loop;

	private Player player;

	private final Set<Sprite> sprites = new HashSet<Sprite>();
	private final Set<NPC> npcs = new HashSet<NPC>();
	private final Set<Moveable> moveables = new HashSet<Moveable>();
	private final Set<Collidable> collidables = new HashSet<Collidable>();

	private final BlockingQueue<NPC> npcQueue = new PriorityBlockingQueue<NPC>(50);
	private final BlockingQueue<Moveable> moveableQueue = new PriorityBlockingQueue<Moveable>(40);
	private final BlockingQueue<Sprite> spriteQueue = new PriorityBlockingQueue<Sprite>(60);

	private final InputEventManager inputs = new InputEventManager();

	public Engine(Stage stage)
	{
		this(stage, new GameSettings(60, 1440, 900, 32, false));
	}

	public Engine(Stage stage, GameSettings settings)
	{
		this.loop = new Timeline(settings.fps);
		loop.setCycleCount(Animation.INDEFINITE);
		
		this.stage = stage;
		this.settings = settings;

		this.root = new Group();
		this.scene = new Scene(root);
		this.stage.setScene(scene);

		final String path = GenericSprite.class.getResource("/dimensions/client/game/assets/images/textures/" + "mud.png").toString();
		final Image mudImage = new Image(path);
		final Rectangle screen = new Rectangle(0, 0, settings.widthWindow, settings.heightWindow);
		screen.setFill(new ImagePattern(mudImage, 0, 0, 32, 32, false));

		root.getChildren().add(screen);

		this.mainCanvas = new Canvas(settings.widthWindow, settings.heightWindow);
		this.playerCanvas = new Canvas(settings.widthWindow, settings.heightWindow);
		this.hudCanvas = new Canvas(settings.widthWindow, settings.heightWindow);

		this.mainRenderer = mainCanvas.getGraphicsContext2D();
		this.playerRenderer = mainCanvas.getGraphicsContext2D();
		this.hudRenderer = mainCanvas.getGraphicsContext2D();

		this.root.getChildren().add(mainCanvas);
		this.root.getChildren().add(playerCanvas);
		this.root.getChildren().add(hudCanvas);

		inputs.listenTo(scene);
	}
	
	public void play()
	{
		loop.play();
	}
	
	protected void stop()
	{
		loop.stop();
	}
	
	public void addKeyFrame(KeyFrame frame)
	{
		loop.getKeyFrames().add(frame);
	}
	
	public void addKeyFrame(EventHandler<ActionEvent> eventLoop, double fps)
	{
		final Duration frameDuation = Duration.millis(1000 / fps);
		final KeyFrame frame = new KeyFrame(frameDuation, eventLoop);
		loop.getKeyFrames().add(frame);
	}

	public void addNPC(final NPC npc)
	{
		npcQueue.offer(npc);
	}

	@Override
	public void handle(ActionEvent event)
	{
		//pollQueues();
		//moveMoveables();
		//actNPC();
		checkForCollisons();
		removeSprites();
		//updateGraphics();
		if(player != null)
		{
			mainCanvas.setTranslateX(player.getX());
			mainCanvas.setTranslateY(player.getY());
		}

	}

	void actNPC()
	{
		for(NPC npc : npcs)
			npc.act();
	}

	public void updateGraphics()
	{
		mainRenderer.clearRect(0, 0, settings.widthWindow, settings.heightWindow);
		for(Sprite sprite : sprites)
			sprite.render(mainRenderer);

		player.render(playerRenderer);
	}

	private void removeSprites()
	{
		Iterator<Sprite> iterStatics = sprites.iterator();
		synchronized(iterStatics)
		{
			while(iterStatics.hasNext())
				if(iterStatics.next().isReadyToRemove())
					iterStatics.remove();
		}

		Iterator<NPC> iterNpc = npcs.iterator();
		synchronized(iterNpc)
		{
			while(iterNpc.hasNext())
				if(iterNpc.next().isReadyToRemove())

					iterNpc.remove();
		}
	}

	private void checkForCollisons()
	{
		// TODO Auto-generated method stub

	}

	void moveMoveables()
	{
		for(Moveable sprite : moveables)
			sprite.move();
		player.move();
	}

	synchronized void pollQueues()
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
			//addMoveable(player);
			inputs.createDefaultKeyBindings(player);
			centerPlayerOnScreen();
		}
	}
	
	private void centerPlayerOnScreen()
	{
		final double x = settings.widthPlayableArea/2 - player.getWidth()/2;
		final double y = settings.heightPlayableArea/2 - player.getHeight()/2;
		player.setXY(x, y);
	}
}
