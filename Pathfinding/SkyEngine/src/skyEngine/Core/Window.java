package skyEngine.Core;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import skyEngine.RenderingUtilities.AntiAliasing;
import skyEngine.RenderingUtilities.VSync;

public class Window
{

	// Design variables
	private static int designWidth;
	private static int designHeight;
	private static double designAspectRatio;

	// Render area variables
	private static int renderedWidth;
	private static int renderedHeight;

	// Window resolution variables
	private static int windowWidth;
	private static int windowHeight;

	// Viewport coordinate variables
	private static int viewportX;
	private static int viewportY;

	// Setting Variables
	private static boolean fullscreen;

	// OpenGL context variables
	private static boolean contextCreated = false;

	/**
	 * This is the constructor for a Window object.
	 * 
	 * @param gc
	 *            An instance of a GameContainer object
	 */
	public Window(GameContainer gc)
	{
		designWidth = GameContainer.getDesignWidth();
		designHeight = GameContainer.getDesignHeight();
		designAspectRatio = (double) designWidth / (double) designHeight;
		createWindow(gc.isFullscreenOnStart(), gc.isMaxResolutionOnStart());
	}

	/**
	 * This method creates an actual window that displays the game
	 */
	private static void createWindow(boolean isFSoS, boolean isMRoS)
	{
		// Get width and height of window
		if (isMRoS)
		{
			windowWidth = Display.getDesktopDisplayMode().getWidth();
			windowHeight = Display.getDesktopDisplayMode().getHeight();
		} else
		{
			windowWidth = designWidth;
			windowHeight = designHeight;
		}

		try
		{
			updateDisplayMode(windowWidth, windowHeight, isFSoS);
			Display.create(AntiAliasing.getPixelFormat());
			VSync.enableVSync();
			setViewParameters();
		} catch (Exception e)
		{
			e.printStackTrace();
			try
			{
				updateDisplayMode(windowWidth, windowHeight, isFSoS);
				AntiAliasing.setInitialAntiAliasValues(0, 0, 0, 0);
				Display.create(AntiAliasing.getPixelFormat());
				VSync.enableVSync();
				setViewParameters();
			} catch (Exception e1)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method is used to adjust DisplayMode settings like resolution and
	 * toggling fullscreen mode
	 * 
	 * @param width
	 *            The width of the desired resolution
	 * @param height
	 *            The height of the desired resolution
	 * @param fullscreen
	 *            True if the desired DisplayMode is fullscreen
	 */
	public static void updateDisplayMode(int width, int height, boolean fullscreen)
	{
		// Do not adjust DisplayMode if current DisplayMode is the same as the
		// target DisplayMode
		if (width == Display.getDisplayMode().getWidth() && height == Display.getDisplayMode().getHeight()
				&& fullscreen == Display.isFullscreen())
		{
			return;
		}

		try
		{
			DisplayMode target = null;
			if (width > Display.getDesktopDisplayMode().getWidth())
			{
				width = Display.getDesktopDisplayMode().getWidth();
			}
			if (height > Display.getDesktopDisplayMode().getHeight())
			{
				height = Display.getDesktopDisplayMode().getHeight();
			}

			if (fullscreen)
			{
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;
				for (int i = 0; i < modes.length; i++)
				{
					if ((modes[i].getWidth() == width) && (modes[i].getHeight() == height))
					{
						if ((target == null) || ((modes[i].getFrequency() >= freq)
								&& (modes[i].getBitsPerPixel() > target.getBitsPerPixel())))
						{
							target = modes[i];
							freq = target.getFrequency();
						}

						if ((modes[i].getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel())
								&& (modes[i].getFrequency() == Display.getDesktopDisplayMode().getFrequency()))
						{
							target = modes[i];
							break;
						}
					}
				}
			} else
			{
				target = new DisplayMode(width, height);
				windowWidth = width;
				windowHeight = height;
			}

			if (target == null)
			{
				System.out.println("Failed to find DisplayMode: " + width + "x" + height + " fs=" + fullscreen);
				return;
			}

			Display.setDisplayMode(target);
			Display.setFullscreen(fullscreen);

			if (contextCreated)
			{
				adjustRenderArea();
				adjustViewport();
			} else
			{
				contextCreated = true;
			}

		} catch (LWJGLException e)
		{
			System.out.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen + e);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Adjusts the area that the game will be rendered to
	 */
	private static void adjustRenderArea()
	{
		renderedWidth = windowWidth;
		renderedHeight = (int) (windowWidth / designAspectRatio + 0.5f);
		if (renderedHeight > windowHeight)
		{
			renderedHeight = windowHeight;
			renderedWidth = (int) (renderedHeight * designAspectRatio + 0.5f);
		}
	}

	/**
	 * Sets the viewport to fit the window size of the user
	 */
	private static void adjustViewport()
	{
		viewportX = (windowWidth / 2) - (renderedWidth / 2);
		viewportY = (windowHeight / 2) - (renderedHeight / 2);
		glViewport(viewportX, viewportY, renderedWidth, renderedHeight);
	}

	/**
	 * This method is used to update the window every frame and makes sure it
	 * doesn't crash
	 */
	public void update()
	{
		Display.update();
		Display.sync(Display.getDesktopDisplayMode().getFrequency());
	}

	/**
	 * Cleans up the OpenGL environment so that the player's computer doesn't
	 * freeze
	 */
	public void cleanUp()
	{
		Display.destroy();
	}

	/**
	 * Gets an array of DisplayMode objects that match the monitor's current
	 * bits per pixel and frequency settings
	 * 
	 * @return An array of DisplayModes
	 */
	public static DisplayMode[] getIdealDisplayModes()
	{
		ArrayList<DisplayMode> temporaryArrayList = new ArrayList<DisplayMode>();
		DisplayMode[] temporaryDisplayModeArray = null;
		DisplayMode[] finalDisplayModeArray;

		try
		{
			temporaryDisplayModeArray = Display.getAvailableDisplayModes();
		} catch (LWJGLException e)
		{
			e.printStackTrace();
		}

		for (DisplayMode m : temporaryDisplayModeArray)
		{
			if (m.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()
					&& m.getFrequency() == Display.getDesktopDisplayMode().getFrequency())
			{
				temporaryArrayList.add(m);
			}
		}

		finalDisplayModeArray = new DisplayMode[temporaryArrayList.size()];
		for (DisplayMode m : temporaryArrayList)
		{
			finalDisplayModeArray[temporaryArrayList.indexOf(m)] = m;
		}

		return finalDisplayModeArray;
	}

	/**
	 * Gets an array of DisplayMode objects that match the monitor's current
	 * bits per pixel and frequency settings that is sorted by aspect ratio from
	 * low to high
	 * 
	 * @return An array of DisplayModes sorted by aspect ratio
	 */
	public static DisplayMode[] getIdealDisplayModesByAspectRatio()
	{
		return sortByAspectRatio(getIdealDisplayModes());
	}

	/**
	 * Gets an array of DisplayMode objects that match the monitor's current
	 * bits per pixel and frequency settings that is sorted by width in pixels
	 * 
	 * @return An array of DisplayModes sorted by width
	 */
	public static DisplayMode[] getIdealDisplayModesByWidth()
	{
		return sortByWidth(getIdealDisplayModes());
	}

	/**
	 * Gets an array of DisplayMode objects that match the monitor's current
	 * bits per pixel and frequency settings that is sorted by height in pixels
	 * 
	 * @return An array of DisplayModes sorted by width
	 */
	public static DisplayMode[] getIdealDisplayModesByHeight()
	{
		return sortByHeight(getIdealDisplayModes());
	}

	/**
	 * Sorts an array of DisplayMode objects by aspect ratio
	 * 
	 * @param unsorted
	 *            The unsorted array
	 * @return The sorted array
	 */
	private static DisplayMode[] sortByAspectRatio(DisplayMode[] unsorted)
	{
		boolean isSorted = false;
		DisplayMode temporary = null;

		while (!isSorted)
		{
			isSorted = true;
			for (int i = 0; i < unsorted.length - 1; i++)
			{
				if (((double) unsorted[i].getWidth()
						/ (double) unsorted[i].getHeight()) > ((double) unsorted[i + 1].getWidth()
								/ (double) unsorted[i + 1].getHeight()))
				{
					isSorted = false;
					break;
				}
			}

			if (isSorted)
			{
				break;
			}

			for (int i = 0; i < unsorted.length - 1; i++)
			{
				if (((double) unsorted[i].getWidth()
						/ (double) unsorted[i].getHeight()) > ((double) unsorted[i + 1].getWidth()
								/ (double) unsorted[i + 1].getHeight()))
				{
					temporary = unsorted[i];
					unsorted[i] = unsorted[i + 1];
					unsorted[i + 1] = temporary;
					temporary = null;
				}
			}

		}

		isSorted = false;
		while (!isSorted)
		{
			isSorted = true;
			for (int i = 0; i < unsorted.length - 1; i++)
			{
				if (((double) unsorted[i].getWidth()
						/ (double) unsorted[i].getHeight()) == ((double) unsorted[i + 1].getWidth()
								/ (double) unsorted[i + 1].getHeight())
						&& unsorted[i].getWidth() > unsorted[i + 1].getWidth())
				{
					isSorted = false;
					break;
				}
			}

			if (isSorted)
			{
				break;
			}

			for (int i = 0; i < unsorted.length - 1; i++)
			{
				if (((double) unsorted[i].getWidth()
						/ (double) unsorted[i].getHeight()) == ((double) unsorted[i + 1].getWidth()
								/ (double) unsorted[i + 1].getHeight())
						&& unsorted[i].getWidth() > unsorted[i + 1].getWidth())
				{
					temporary = unsorted[i];
					unsorted[i] = unsorted[i + 1];
					unsorted[i + 1] = temporary;
					temporary = null;
				}
			}

		}
		return unsorted;
	}

	/**
	 * Sorts an array of DisplayMode objects by width
	 * 
	 * @param unsorted
	 *            The unsorted array
	 * @return The sorted array
	 */
	private static DisplayMode[] sortByWidth(DisplayMode[] unsorted)
	{
		boolean isSorted = false;
		DisplayMode temporary = null;

		while (!isSorted)
		{
			isSorted = true;
			for (int i = 0; i < unsorted.length - 1; i++)
			{
				if (unsorted[i].getWidth() > unsorted[i + 1].getWidth())
				{
					isSorted = false;
					break;
				}
			}

			if (isSorted)
			{
				break;
			}

			for (int i = 0; i < unsorted.length - 1; i++)
			{
				if (unsorted[i].getWidth() > unsorted[i + 1].getWidth())
				{
					temporary = unsorted[i];
					unsorted[i] = unsorted[i + 1];
					unsorted[i + 1] = temporary;
					temporary = null;
				}
			}

		}

		isSorted = false;
		while (!isSorted)
		{
			isSorted = true;
			for (int i = 0; i < unsorted.length - 1; i++)
			{
				if (unsorted[i].getWidth() == unsorted[i + 1].getWidth()
						&& unsorted[i].getHeight() > unsorted[i + 1].getHeight())
				{
					isSorted = false;
					break;
				}
			}

			if (isSorted)
			{
				break;
			}

			for (int i = 0; i < unsorted.length - 1; i++)
			{
				if (unsorted[i].getWidth() == unsorted[i + 1].getWidth()
						&& unsorted[i].getHeight() > unsorted[i + 1].getHeight())
				{
					temporary = unsorted[i];
					unsorted[i] = unsorted[i + 1];
					unsorted[i + 1] = temporary;
					temporary = null;
				}
			}

		}
		return unsorted;
	}

	/**
	 * Sorts an array of DisplayMode objects by height
	 * 
	 * @param unsorted
	 *            The unsorted array
	 * @return The sorted array
	 */
	private static DisplayMode[] sortByHeight(DisplayMode[] unsorted)
	{
		boolean isSorted = false;
		DisplayMode temporary = null;

		while (!isSorted)
		{
			isSorted = true;
			for (int i = 0; i < unsorted.length - 1; i++)
			{
				if (unsorted[i].getHeight() > unsorted[i + 1].getHeight())
				{
					isSorted = false;
					break;
				}
			}

			if (isSorted)
			{
				break;
			}

			for (int i = 0; i < unsorted.length - 1; i++)
			{
				if (unsorted[i].getHeight() > unsorted[i + 1].getHeight())
				{
					temporary = unsorted[i];
					unsorted[i] = unsorted[i + 1];
					unsorted[i + 1] = temporary;
					temporary = null;
				}
			}

		}

		isSorted = false;
		while (!isSorted)
		{
			isSorted = true;
			for (int i = 0; i < unsorted.length - 1; i++)
			{
				if (unsorted[i].getHeight() == unsorted[i + 1].getHeight()
						&& unsorted[i].getWidth() > unsorted[i + 1].getWidth())
				{
					isSorted = false;
					break;
				}
			}

			if (isSorted)
			{
				break;
			}

			for (int i = 0; i < unsorted.length - 1; i++)
			{
				if (unsorted[i].getHeight() == unsorted[i + 1].getHeight()
						&& unsorted[i].getWidth() > unsorted[i + 1].getWidth())
				{
					temporary = unsorted[i];
					unsorted[i] = unsorted[i + 1];
					unsorted[i + 1] = temporary;
					temporary = null;
				}
			}

		}
		return unsorted;
	}

	/**
	 * Calls a collection of methods to set the render area, viewport, and
	 * OpenGL matrix mode to the proper settings
	 */
	public static void setViewParameters()
	{
		adjustRenderArea();
		adjustViewport();
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, designWidth, designHeight, 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
	}

	public static boolean isFullscreen()
	{
		return fullscreen;
	}

}
