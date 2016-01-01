package dimensions.client.engine;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import dimensions.client.engine.physics.Physics;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class Engine
{
	private final GameThreadExecutor tasks;
	private final double fps;

	public Engine()
	{
		this(60, 5);
	}

	public Engine(final double fps, final int threadPoolSize)
	{
		tasks = new GameThreadExecutor(threadPoolSize);
		this.fps = fps;
	}

	public void execut(Runnable command)
	{
		tasks.execute(command);
	}

	public void stop()
	{
		tasks.shutdown();
	}
	
	public synchronized void togglePause(KeyEvent event)
	{
		if(tasks.isPaused())
			tasks.resume();
		else
			tasks.pause();
	}

	public void addTask(Runnable task)
	{
		addTask(task, fps);
	}

	public void addTask(Runnable task, double fps)
	{
		final long interval = (long) (Physics.ONE_SECOND / fps);
		tasks.scheduleAtFixedRate(task, 0, interval, TimeUnit.NANOSECONDS);
	}
}
