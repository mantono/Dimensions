package dimensions.client.game;

import dimensions.client.engine.Engine;
import dimensions.client.engine.GameSettings;
import dimensions.client.engine.GraphicsRenderer;
import dimensions.client.engine.InputEventManager;
import dimensions.client.engine.Logic;
import dimensions.client.engine.SpriteManager;
import dimensions.client.engine.physics.Physics;
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

	public void start(Stage stage) throws InterruptedException
	{
		stage.setTitle("Greate game");
		stage.setOnCloseRequest(new Quit());
		
		engine = new Engine(60, 5);
		SpriteManager spriteManager = new SpriteManager();
		GameSettings.initate(60, 1440, 900, 32, false);
		GraphicsRenderer renderer = new GraphicsRenderer(stage, spriteManager);
		renderer.start();
		InputEventManager inputs = new InputEventManager(stage.getScene());
		Logic logic = new Logic(spriteManager);
		Physics physics = new Physics(spriteManager);
		
		engine.addTask(spriteManager, 30);
		engine.addTask(logic, 20);
		engine.addTask(physics, 60);
		
		spriteManager.addSprite(new Mud());
		spriteManager.addPlayer(new DimensionPlayer());
		
		for(int i = 0; i < 500; i++)
			spriteManager.addSprite(new SimpleNPC());
		
		inputs.createDefaultKeyBindings(spriteManager.getPlayer());

		stage.show();
	}
	
	private class Quit implements EventHandler<WindowEvent>
	{
		@Override
		public void handle(WindowEvent event)
		{
			engine.stop();
			System.out.println("Gracefully closed.");
			System.exit(0);
		}	
	}
}