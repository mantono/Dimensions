package dimensions.client.engine;

public class ExceptionThrower implements Runnable
{
	private final Runnable runnable;

	protected ExceptionThrower(final Runnable runnable)
	{
		this.runnable = runnable;
	}

	@Override
	public void run()
	{
		try
		{
			runnable.run();
		}
		catch(Exception exception)
		{
			System.err.println(exception + ":" + exception.getMessage());
			exception.printStackTrace();
		}
	}

}
