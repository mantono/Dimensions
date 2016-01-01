package dimensions.client.engine;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameThreadExecutor extends ScheduledThreadPoolExecutor
{
	private boolean isPaused = false;
	private long pauseStart, pauseEnd;
	private final Lock lock = new ReentrantLock();
	private final Condition unpausedState = lock.newCondition();

	public GameThreadExecutor(int threadAmount)
	{
		super(threadAmount);
		this.pauseStart = this.pauseEnd = System.nanoTime();
	}

	protected void beforeExecute(Thread thread, Runnable runnable)
	{
		super.beforeExecute(thread, runnable);
		lock.lock();
		try
		{
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
	
	public synchronized boolean isPaused()
	{
		return isPaused;
	}

	public void pause()
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

	public void resume()
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
	
	public long pauseDuration()
	{
		if(isPaused)
			return -1;
		return pauseEnd - pauseStart;
	}
}
