package dimensions.client.engine;

public class GameSettings
{
	public final int fps, widthWindow, heightWindow, widthPlayableArea, heightPlayableArea;
	public final short spriteBlockSize;
	public final boolean debugMode;
	
	public GameSettings(int fps, int width, int height, int blockSize, boolean debugMode)
	{
		this.fps = fps;
		this.widthPlayableArea = this.widthWindow = width;
		this.heightPlayableArea = this.heightWindow = height;
		this.spriteBlockSize = (short) blockSize;
		this.debugMode = debugMode;
	}
}
