package skyEngine.Core;

import org.lwjgl.opengl.Display;

import skyEngine.RenderingUtilities.AntiAliasing;

public class GameContainer implements Runnable
{

	// Core Components
	private AbstractGame game;
	private Thread gameThread;
	private boolean isGameRunning = false;
	private Window window;
	private Renderer renderer;
	private Input input;

	// Window Data
	private static int designWidth;
	private static int designHeight;
	private static boolean fullscreenOnStart;
	private static boolean maxResolutionOnStart;

	// Delta Time
	private double firstTime;
	private double lastTime;
	private double passedTime;
	private double unprocessedTime;

	// Frame Rates
	private double frameRateTime;
	private int findFrameRate;
	private int currentFrameRate;
	private double frameRateCap = Display.getDesktopDisplayMode().getFrequency();
	private double deltaTimeCap;
	private boolean doRender = false;

	/**
	 * This is the constructor for a GameContainer object. It essentially takes
	 * your AbstractGame and runs it on SkyEngine.
	 * 
	 * @param game
	 *            An AbstractGame object that is to be run on SkyEngine
	 */
	public GameContainer(AbstractGame game)
	{
		this.game = game;
		designWidth = 1920;
		designHeight = 1080;
		fullscreenOnStart = true;
		maxResolutionOnStart = true;
	}

	/**
	 * Starts the game if it is not already running
	 */
	public void startGame()
	{
		if (isGameRunning)
		{
			return;
		} else
		{
			window = new Window(this);
			renderer = new Renderer();
			input = new Input();

			gameThread = new Thread(this);
			gameThread.run();
		}
	}

	/**
	 * Stops the game if it is not already stopped
	 */
	public void stopGame()
	{
		if (!isGameRunning)
		{
			return;
		} else
		{
			isGameRunning = false;
		}
	}

	/**
	 * This is the main game loop where updates and rendering are called up.
	 */
	public void run()
	{
		isGameRunning = true;

		firstTime = 0;
		lastTime = System.nanoTime() / 1000000000.0;
		passedTime = 0;
		unprocessedTime = 0;
		deltaTimeCap = 1.0 / (double) frameRateCap;

		while (!Display.isCloseRequested())
		{
			doRender = false;

			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			unprocessedTime += passedTime;
			frameRateTime += passedTime;

			while (unprocessedTime >= deltaTimeCap)
			{
				game.update(this, (float) deltaTimeCap);
				input.update();
				unprocessedTime -= deltaTimeCap;
				doRender = true;

				if (frameRateTime >= 1)
				{
					frameRateTime = 0;
					currentFrameRate = findFrameRate;
					findFrameRate = 0;
				}
			}
			if (doRender)
			{
				renderer.clearScreen();
				game.render(this, renderer);
				window.update();
				findFrameRate++;
			} else
			{
				try
				{
					Thread.sleep(1);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		cleanUp();
	}

	/**
	 * Sets the Anti-Aliasing values to launch the game with so that they do not
	 * need to be altered later. Typically, these values should be read from a
	 * file.
	 * 
	 * @param alpha
	 *            The number of alpha bits
	 * @param depth
	 *            The number of depth bits
	 * @param stencil
	 *            The number of stencil bits
	 * @param samples
	 *            The number of samples
	 */
	public void setInitialAntiAliasing(int alpha, int depth, int stencil, int samples)
	{
		AntiAliasing.setInitialAntiAliasValues(alpha, depth, stencil, samples);
	}

	/**
	 * The method that cleans up all components of the engine
	 */
	private void cleanUp()
	{
		window.cleanUp();
	}

	public static int getDesignWidth()
	{
		return designWidth;
	}

	public static int getDesignHeight()
	{
		return designHeight;
	}

	public boolean isFullscreenOnStart()
	{
		return fullscreenOnStart;
	}

	public boolean isMaxResolutionOnStart()
	{
		return maxResolutionOnStart;
	}

	public Window getWindow()
	{
		return window;
	}

	public Renderer getRenderer()
	{
		return renderer;
	}

	public Input getInput()
	{
		return input;
	}

	public int getCurrentFrameRate()
	{
		return currentFrameRate;
	}

	public void setDesignWidth(int designWidth)
	{
		GameContainer.designWidth = designWidth;
	}

	public void setDesignHeight(int designHeight)
	{
		GameContainer.designHeight = designHeight;
	}

	public void setFullscreenOnStart(boolean fullscreenOnStart)
	{
		GameContainer.fullscreenOnStart = fullscreenOnStart;
	}

	public void setMaxResolutionOnStart(boolean maxResolutionOnStart)
	{
		GameContainer.maxResolutionOnStart = maxResolutionOnStart;
	}

	public double getFrameRateCap()
	{
		return frameRateCap;
	}

	public void setFrameRateCap(double frameRateCap)
	{
		this.frameRateCap = frameRateCap;
	}

}
