package skyEngine.RenderingUtilities;

import org.lwjgl.opengl.Display;

public class VSync
{

	private static boolean vsyncEnabled = false;

	/**
	 * Enables Vertical Synchronization in the OpenGL Display
	 */
	public static void enableVSync()
	{
		Display.setVSyncEnabled(true);
		vsyncEnabled = true;
	}

	/**
	 * Disables Vertical Synchronization in the OpenGL Display
	 */
	public static void disableVSync()
	{
		Display.setVSyncEnabled(false);
		vsyncEnabled = false;
	}

	/**
	 * Enables Vertical Synchronization in the OpenGL Display if it is disabled;
	 * disables Vertical Synchronization in the OpenGL Display if it is enabled.
	 */
	public static void toggleVSync()
	{
		if (vsyncEnabled)
		{
			disableVSync();
		} else
		{
			enableVSync();
		}
	}

	public static boolean isVSyncEnabled()
	{
		return vsyncEnabled;
	}

}
