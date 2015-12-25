package dimensions.client.game;

import dimensions.client.engine.Engine;
import dimensions.client.engine.InputEventManager;
import dimensions.client.game.sprites.dynamic.DimensionPlayer;
import dimensions.client.game.sprites.dynamic.TestClass;
import dimensions.client.game.sprites.dynamic.npc.SimpleNPC;
import dimensions.client.game.sprites.statics.Mud;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

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
        //stage.setTitle(title);
        
        final Duration oneFrameAmt = Duration.millis(1000 / (float) 60);
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt, engine); // oneFrame

        // sets the game world's game loop (Timeline)
        Timeline time = TimelineBuilder.create().cycleCount(Animation.INDEFINITE).keyFrames(oneFrame).build();
        
 
        time.play();
        
        stage.show();
    }
}