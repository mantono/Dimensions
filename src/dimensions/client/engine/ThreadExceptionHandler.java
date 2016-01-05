package dimensions.client.engine;

import java.lang.Thread.UncaughtExceptionHandler;

public class ThreadExceptionHandler implements UncaughtExceptionHandler
{
	@Override
	public void uncaughtException(Thread thread, Throwable throwable)
	{
		if(throwable != null)
			System.err.println("Thread pool got exception: " + throwable.getMessage() + "\n" + throwable.getCause());
	}
}
