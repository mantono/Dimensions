package dimensions.client.engine;

import dimensions.client.engine.physics.Coordinate2D;
import dimensions.client.engine.physics.Velocity;
import dimensions.client.game.sprites.GenericSprite;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GraphicsRenderer extends AnimationTimer
{
	private final Stage stage;
	private final Scene scene;
	private final Group root;
	private final Canvas mainCanvas, hudCanvas;
	private final GraphicsContext mainRenderer, hudRenderer;

	private final SpriteManager spriteManager;

	public GraphicsRenderer(Stage stage, SpriteManager spriteManager)
	{
		this.stage = stage;
		this.spriteManager = spriteManager;

		this.root = new Group();
		this.scene = new Scene(root);
		this.stage.setScene(scene);

		final String path = GenericSprite.class.getResource("/dimensions/client/game/assets/images/textures/" + "mud.png").toString();
		final Image mudImage = new Image(path);
		final Rectangle screen = new Rectangle(0, 0, GameSettings.widthWindow, GameSettings.heightWindow);
		screen.setFill(new ImagePattern(mudImage, 0, 0, 32, 32, false));

		root.getChildren().add(screen);

		this.mainCanvas = new Canvas(GameSettings.widthWindow, GameSettings.heightWindow);
		this.hudCanvas = new Canvas(GameSettings.widthWindow, GameSettings.heightWindow);

		this.mainRenderer = mainCanvas.getGraphicsContext2D();
		this.hudRenderer = mainCanvas.getGraphicsContext2D();

		this.root.getChildren().add(mainCanvas);
		this.root.getChildren().add(hudCanvas);
	}

	private void renderGraphics()
	{
		mainRenderer.clearRect(0, 0, GameSettings.widthWindow, GameSettings.heightWindow);
		spriteManager.getSprites().forEachRemaining(e -> e.render(mainRenderer));
	}

	private void playerMovementCompensation()
	{
		if(spriteManager.getPlayer() != null && spriteManager.getPlayer().hasFixedScreenPosition())
		{
			final Velocity playerVelocity = spriteManager.getPlayer().getVelocity();
			//mainCanvas.setTranslateX(mainCanvas.getTranslateX() - playerVelocity.getX());
			//mainCanvas.setTranslateY(playerVelocity.getY() + mainCanvas.getTranslateY());
		}
		
	}
	
	@Override
	public void handle(long now)
	{
		playerMovementCompensation();
		renderGraphics();
	}

}
