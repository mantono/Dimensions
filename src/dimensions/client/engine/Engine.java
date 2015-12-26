package dimensions.client.engine;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class Engine
{	
	private final Timeline loop;

	public Engine()
	{
		this(60);
	}

	public Engine(final double fps)
	{
		this.loop = new Timeline(fps);
		loop.setCycleCount(Animation.INDEFINITE);
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
}
