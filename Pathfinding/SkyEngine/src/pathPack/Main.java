package pathPack;

import org.lwjgl.input.Keyboard;

import skyEngine.Core.AbstractGame;
import skyEngine.Core.GameContainer;
import skyEngine.Core.Input;
import skyEngine.Core.Renderer;
import skyEngine.RenderingUtilities.VSync;

public class Main extends AbstractGame
{

	final static int WIDTH_TILES = 96;
	final static int HEIGHT_TILES = 48;
	final static int TILE_SIZE = 16;

	int mouseX = 0;
	int mouseY = 0;

	int mouseTileX = 0;
	int mouseTileY = 0;

	int[][] tilesFromMouse = new int[WIDTH_TILES][HEIGHT_TILES];
	int maxDistance = 0;

	boolean firstRun = true;

	public static void main(String[] args)
	{
		GameContainer gc = new GameContainer(new Main());
		gc.setDesignWidth(TILE_SIZE * WIDTH_TILES);
		gc.setDesignHeight(TILE_SIZE * HEIGHT_TILES);
		gc.setFullscreenOnStart(false);
		gc.setMaxResolutionOnStart(false);
		VSync.disableVSync();
		gc.startGame();
	}

	@Override
	public void update(GameContainer gc, float deltaTime)
	{
		if (firstRun)
		{
			Input.setMousePosition(WIDTH_TILES * TILE_SIZE / 2, HEIGHT_TILES * TILE_SIZE / 2);
			firstRun = false;
		}
		if (Input.isKeyPressed(Keyboard.KEY_ESCAPE))
		{
			System.exit(0);
		}

		mouseX = Input.getMouseX();
		mouseY = Input.getMouseY();
		mouseTileX = mouseX / TILE_SIZE;
		mouseTileY = mouseY / TILE_SIZE;
		if (mouseTileX > WIDTH_TILES - 1)
		{
			mouseTileX = WIDTH_TILES - 1;
		} else if (mouseTileX < 0)
		{
			mouseTileX = 0;
		}
		if (mouseTileY > HEIGHT_TILES - 1)
		{
			mouseTileY = HEIGHT_TILES - 1;
		} else if (mouseTileY < 0)
		{
			mouseTileY = 0;
		}

		populateTiles(mouseTileX, mouseTileY);
		maxDistance = getMaxDistance();

		System.out.print(1 / deltaTime + " FPS   ");
		System.out.print("(" + mouseX + ", " + mouseY + ")   ");
		System.out.println("(" + mouseTileX + ", " + mouseTileY + ")   ");

	}

	@Override
	public void render(GameContainer gc, Renderer r)
	{
		for (int i = 0; i < WIDTH_TILES; i++)
		{
			for (int j = 0; j < HEIGHT_TILES; j++)
			{
				if (tilesFromMouse[i][j] == -1)
				{
					r.drawColorQuad(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, 0, 0, 0, 255);
				} else
				{
					r.drawColorQuad(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE,
							255 - 255 * (float) tilesFromMouse[i][j] / 70, 0,
							255 * (float) tilesFromMouse[i][j] / 70, 255);
				}
			}
		}
	}

	private int getMaxDistance()
	{
		int max = 0;
		for (int i = 0; i < WIDTH_TILES; i++)
		{
			for (int j = 0; j < HEIGHT_TILES; j++)
			{
				if (tilesFromMouse[i][j] > max)
				{
					max = tilesFromMouse[i][j];
				}
			}
		}
		return max;
	}

	private void populateTiles(int tileX, int tileY)
	{
		// Make all have max value
		for (int i = 0; i < WIDTH_TILES; i++)
		{
			for (int j = 0; j < HEIGHT_TILES; j++)
			{
				tilesFromMouse[i][j] = Integer.MAX_VALUE;
			}
		}
		// Set where the mouse is as a starting point
		tilesFromMouse[tileX][tileY] = 0;
		
		// Set blocks
		for (int i = 6; i < 89; i++)
		{
			tilesFromMouse[i][2] = -1;
			tilesFromMouse[i][3] = -1;
		}
		for (int i = 4; i < 42; i++)
		{
			tilesFromMouse[6][i] = -1;
			tilesFromMouse[7][i] = -1;
		}
		for (int i = 6; i < 89; i++)
		{
			tilesFromMouse[i][41] = -1;
			tilesFromMouse[i][42] = -1;
		}
		for (int i = 4; i < 38; i++)
		{
			tilesFromMouse[87][i] = -1;
			tilesFromMouse[88][i] = -1;
		}
		for (int i = 10; i < 89; i++)
		{
			tilesFromMouse[i][37] = -1;
			tilesFromMouse[i][38] = -1;
		}
		for (int i = 10; i < 38; i++)
		{
			tilesFromMouse[37][i] = -1;
			tilesFromMouse[38][i] = -1;
		}
		for (int i = 4; i < 32; i++)
		{
			tilesFromMouse[47][i] = -1;
			tilesFromMouse[48][i] = -1;
		}
		for (int i = 10; i < 38; i++)
		{
			tilesFromMouse[52][i] = -1;
			tilesFromMouse[53][i] = -1;
		}
		

		boolean isComplete = false;
		int populateValue = 0;
		if(tilesFromMouse[tileX][tileY] == -1)
		{
			isComplete = true;
		}
		while (!isComplete)
		{
			populateSurroundingTiles(populateValue);
			populateValue++;
			isComplete = true;
			for (int i = 0; i < WIDTH_TILES; i++)
			{
				for (int j = 0; j < HEIGHT_TILES; j++)
				{
					if (tilesFromMouse[i][j] == Integer.MAX_VALUE)
					{
						isComplete = false;
					}
				}
			}
		}
	}

	private void populateSurroundingTiles(int value)
	{
		for (int i = 0; i < WIDTH_TILES; i++)
		{
			for (int j = 0; j < HEIGHT_TILES; j++)
			{
				if (tilesFromMouse[i][j] == value)
				{
					// up and left
					if (i > 0 && j > 0)
					{
						if (tilesFromMouse[i - 1][j - 1] > value)
						{
							tilesFromMouse[i - 1][j - 1] = value + 1;
						}
					}
					// up
					if (j > 0)
					{
						if (tilesFromMouse[i][j - 1] > value)
						{
							tilesFromMouse[i][j - 1] = value + 1;
						}
					}
					// up and right
					if (i < WIDTH_TILES - 1 && j > 0)
					{
						if (tilesFromMouse[i + 1][j - 1] > value)
						{
							tilesFromMouse[i + 1][j - 1] = value + 1;
						}
					}
					// left
					if (i > 0)
					{
						if (tilesFromMouse[i - 1][j] > value)
						{
							tilesFromMouse[i - 1][j] = value + 1;
						}
					}
					// right
					if (i < WIDTH_TILES - 1)
					{
						if (tilesFromMouse[i + 1][j] > value)
						{
							tilesFromMouse[i + 1][j] = value + 1;
						}
					}
					// down and left
					if (i > 0 && j < HEIGHT_TILES - 1)
					{
						if (tilesFromMouse[i - 1][j + 1] > value)
						{
							tilesFromMouse[i - 1][j + 1] = value + 1;
						}
					}
					// down
					if (j < HEIGHT_TILES - 1)
					{
						if (tilesFromMouse[i][j + 1] > value)
						{
							tilesFromMouse[i][j + 1] = value + 1;
						}
					}
					// down and right
					if (i < WIDTH_TILES - 1 && j < HEIGHT_TILES - 1)
					{
						if (tilesFromMouse[i + 1][j + 1] > value)
						{
							tilesFromMouse[i + 1][j + 1] = value + 1;
						}
					}
				}
			}
		}
	}

}
