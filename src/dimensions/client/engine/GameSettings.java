package dimensions.client.engine;

public class GameSettings
{
	final int fps, width, height;
	final short spriteBlockSize;
	
	public GameSettings(int fps, int width, int height, int blockSize)
	{
		this.fps = fps;
		this.width = width;
		this.height = height;
		this.spriteBlockSize = (short) blockSize;
	}
}
