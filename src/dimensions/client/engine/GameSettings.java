package dimensions.client.engine;

public class GameSettings
{	
	public static double fps; 
	public static int widthWindow, heightWindow, widthPlayableArea, heightPlayableArea;
	public static short spriteBlockSize;
	public static boolean debugMode;
	
	public static void initate(double fps, int width, int height, int blockSize, boolean debugMode)
	{
		GameSettings.fps = fps;
		GameSettings.widthPlayableArea = GameSettings.widthWindow = width;
		GameSettings.heightPlayableArea = GameSettings.heightWindow = height;
		GameSettings.spriteBlockSize = (short) blockSize;
		GameSettings.debugMode = debugMode;
	}
}
