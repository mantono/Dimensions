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
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
		stage.setOnCloseRequest(new CloseWindow());
		
		engine = new Engine(60, 5);
		SpriteManager spriteManager = new SpriteManager();
		GameSettings.initate(60, 1440, 900, 32, false);
		GraphicsRenderer renderer = new GraphicsRenderer(stage, spriteManager);
		renderer.start();
		InputEventManager inputs = new InputEventManager(stage.getScene());
		Logic logic = new Logic(spriteManager);
		Physics physics = new Physics(spriteManager, engine, new Rectangle2D(GameSettings.widthWindow*0.375, GameSettings.heightWindow*0.375, GameSettings.widthWindow*0.25, GameSettings.heightWindow*0.25));		
		engine.addTask(spriteManager, 30);
		engine.addTask(logic, 20);
		engine.addTask(physics, 60);
		
		spriteManager.addSprite(new Mud());
		spriteManager.addSprite(new DimensionPlayer());
		
		inputs.createDefaultKeyBindings(spriteManager.getPlayer());
		inputs.addKeyBinding(KeyCode.ESCAPE, new PressEsacpe());
		inputs.addKeyBinding(KeyCode.P, e -> engine.togglePause(e));
		
		for(int i = 0; i < 500; i++)
			spriteManager.addSprite(new SimpleNPC());

		stage.show();
	}
	
	private void quit()
	{
			engine.stop();
			System.out.println("Gracefully closed.");
			System.exit(0);
	}
	
	private class PressEsacpe implements EventHandler<KeyEvent>
	{
		@Override
		public void handle(KeyEvent event)
		{
			quit();
		}	
	}
	
	private class CloseWindow implements EventHandler<WindowEvent>
	{
		@Override
		public void handle(WindowEvent event)
		{
			quit();
		}	
	}
}