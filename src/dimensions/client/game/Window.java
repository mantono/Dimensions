package dimensions.client.game;

import dimensions.client.engine.Engine;
import dimensions.client.engine.GameSettings;
import dimensions.client.engine.GraphicsRenderer;
import dimensions.client.engine.InputEventManager;
import dimensions.client.engine.Logic;
import dimensions.client.engine.Physics;
import dimensions.client.engine.SpriteManager;
import dimensions.client.game.sprites.dynamic.DimensionPlayer;
import dimensions.client.game.sprites.dynamic.npc.SimpleNPC;
import dimensions.client.game.sprites.statics.Mud;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Window extends Application
{
	private Engine engine;
	
	public static void main(String[] args)
	{
		launch(args);
	}

	public void start(Stage stage)
	{
		stage.setTitle("Greate game");
		
		engine = new Engine(60, 5);
		SpriteManager spriteManager = new SpriteManager();
		GameSettings.initate(60, 1440, 900, 32, false);
		GraphicsRenderer renderer = new GraphicsRenderer(stage, spriteManager);
		renderer.start();
		InputEventManager inputs = new InputEventManager(stage.getScene());
		Logic logic = new Logic(spriteManager);
		Physics physics = new Physics(spriteManager);
		
		spriteManager.addSprite(new Mud());
		spriteManager.addSprite(new SimpleNPC());
		spriteManager.addSprite(new SimpleNPC());
		spriteManager.addSprite(new SimpleNPC());
		spriteManager.addSprite(new SimpleNPC());
		spriteManager.addSprite(new SimpleNPC());
		spriteManager.addSprite(new SimpleNPC());
		spriteManager.addSprite(new SimpleNPC());
		spriteManager.addPlayer(new DimensionPlayer());
		
		inputs.createDefaultKeyBindings(spriteManager.getPlayer());

		engine.addTask(spriteManager, 10);
		engine.addTask(logic, 20);
		//engine.addTask(renderer, 60);
		engine.addTask(physics, 60);
		
		stage.setOnCloseRequest(new Quit());

		stage.show();
	}
	
	private class Quit implements EventHandler<WindowEvent>
	{
		@Override
		public void handle(WindowEvent event)
		{
			engine.stop();
			System.exit(0);
		}	
	}
}