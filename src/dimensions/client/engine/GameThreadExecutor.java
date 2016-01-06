package dimensions.client.engine;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class GameThreadExecutor extends ScheduledThreadPoolExecutor
{
	private boolean isPaused = false;
	private long pauseStart, pauseEnd;
	private final Lock lock = new ReentrantLock();
	private final Condition unpausedState = lock.newCondition();
	private final UncaughtExceptionHandler exceptionHandler = new ThreadExceptionHandler();

	GameThreadExecutor(int threadAmount)
	{
		super(threadAmount);
		this.pauseStart = this.pauseEnd = System.nanoTime();
	}

	@Override
	protected void beforeExecute(Thread thread, Runnable runnable)
	{
		super.beforeExecute(thread, runnable);
		lock.lock();
		try
		{
			thread.setUncaughtExceptionHandler(exceptionHandler);
			while(isPaused)
				unpausedState.await();
		}
		catch(InterruptedException ie)
		{
			thread.interrupt();
		}
		finally
		{
			lock.unlock();
		}
	}

	@Override
	public java.util.concurrent.ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, java.util.concurrent.TimeUnit unit)
	{
		return super.scheduleAtFixedRate(new ExceptionThrower(command), initialDelay, period, unit);
	}

	@Override
	protected void afterExecute(Runnable runnable, Throwable throwable)
	{
		super.afterExecute(runnable, throwable);
		if(throwable != null)
			System.err.println("Thread pool got exception: " + throwable.getMessage() + "\n" + throwable.getCause());
	}

	synchronized boolean isPaused()
	{
		return isPaused;
	}

	void pause()
	{
		lock.lock();
		try
		{
			isPaused = true;
			this.pauseStart = System.nanoTime();
		}
		finally
		{
			lock.unlock();
		}
	}

	void resume()
	{
		lock.lock();
		try
		{
			isPaused = false;
			this.pauseEnd = System.nanoTime();
			unpausedState.signalAll();
		}
		finally
		{
			lock.unlock();
		}
	}

	synchronized long pauseDuration()
	{
		if(isPaused)
			return -1;
		final long pauseDuration = pauseEnd - pauseStart;
		pauseEnd = pauseStart = 0;
		return pauseDuration;
	}
}
