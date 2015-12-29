package dimensions.client.engine;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import dimensions.client.engine.physics.Physics;

public class Engine
{
	private final ScheduledThreadPoolExecutor tasks;
	private final double fps;

	public Engine()
	{
		this(60, 5);
	}

	public Engine(final double fps, final int threadPoolSize)
	{
		tasks = new ScheduledThreadPoolExecutor(threadPoolSize);
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
