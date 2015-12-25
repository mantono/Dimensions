package dimensions.client.game;

import dimensions.client.engine.Engine;
import dimensions.client.engine.GraphicsRenderer;
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
		Engine engine = new Engine(stage);
		engine.addSprite(new Mud());
		engine.addMoveable(new TestClass());
		engine.addPlayer(new DimensionPlayer());
		engine.addNPC(new SimpleNPC());
		// stage.setTitle(title);

		engine.addKeyFrame(engine, 30);
		engine.addKeyFrame(new GraphicsRenderer(engine), 60);
		
		engine.play();

		stage.show();
	}
}