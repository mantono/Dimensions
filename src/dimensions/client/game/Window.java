package dimensions.client.game;

import dimensions.client.engine.Engine;
import dimensions.client.engine.GameSettings;
import dimensions.client.engine.GraphicsRenderer;
import dimensions.client.engine.InputEventManager;
import dimensions.client.engine.Physics;
import dimensions.client.engine.SpriteManager;
import dimensions.client.engine.spriteinterfaces.Logic;
import dimensions.client.game.sprites.dynamic.DimensionPlayer;
import dimensions.client.game.sprites.dynamic.TestClass;
import dimensions.client.game.sprites.dynamic.npc.SimpleNPC;
import dimensions.client.game.sprites.statics.Mud;
import javafx.application.Application;
import javafx.stage.Stage;

public class Window extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}

	public void start(Stage stage)
	{
		stage.setTitle("Greate game");
		
		Engine engine = new Engine(60);
		SpriteManager spriteManager = new SpriteManager();
		GameSettings.initate(60, 1440, 900, 32, false);
		GraphicsRenderer renderer = new GraphicsRenderer(stage, spriteManager);
		InputEventManager inputs = new InputEventManager(stage.getScene());
		Logic logic = new Logic(spriteManager);
		Physics physics = new Physics(spriteManager);
		
		spriteManager.addSprite(new Mud());
		spriteManager.addSprite(new TestClass());
		spriteManager.addSprite(new SimpleNPC());
		spriteManager.addPlayer(new DimensionPlayer());
		
		inputs.createDefaultKeyBindings(spriteManager.getPlayer());

		engine.addKeyFrame(spriteManager, 60);
		engine.addKeyFrame(renderer, 60);
		engine.addKeyFrame(physics, 60);
		engine.addKeyFrame(logic, 60);
		
		engine.play();

		stage.show();
	}
}