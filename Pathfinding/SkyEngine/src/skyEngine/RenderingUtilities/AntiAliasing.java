package skyEngine.RenderingUtilities;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.PixelFormat;

import skyEngine.Core.Window;

public class AntiAliasing
{

	private static int antiAliasAlpha = 0;
	private static int antiAliasDepth = 0;
	private static int antiAliasStencil = 0;
	private static int antiAliasSamples = 0;

	private static PixelFormat pixForm = new PixelFormat(antiAliasAlpha, antiAliasDepth, antiAliasStencil,
			antiAliasSamples);

	/**
	 * Disables anti-aliasing without changing the anti-aliasing variables
	 */
	public static void disableAntiAlias()
	{
		try
		{
			Display.destroy();
			Display.create();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		Window.setViewParameters();
	}

	/**
	 * Enables anti-aliasing without changing the anti-aliasing variables
	 */
	public static void enableAntiAlias()
	{
		try
		{
			Display.destroy();
			Display.create(pixForm);
		} catch (Exception e)
		{
			e.printStackTrace();
			disableAntiAlias();
		}

		Window.setViewParameters();
	}

	/**
	 * Sets the alpha bits of the PixelFormat for anti-aliasing. Using this
	 * method enables anti-aliasing if it is disabled. If the desired value
	 * fails, it will be set to the previous value.
	 * 
	 * @param alpha
	 *            Desired number of alpha bits
	 */
	public static void setAntiAliasAlpha(int alpha)
	{
		int previousAlpha = antiAliasAlpha;
		antiAliasAlpha = alpha;

		if (antiAliasAlpha < 0)
		{
			antiAliasAlpha = 0;
		} else if (antiAliasAlpha > 8)
		{
			antiAliasAlpha = 8;
		}

		try
		{
			pixForm = new PixelFormat(antiAliasAlpha, antiAliasDepth, antiAliasStencil, antiAliasSamples);
			Display.destroy();
			Display.create(pixForm);
		} catch (Exception e)
		{
			e.printStackTrace();
			setAntiAliasAlpha(previousAlpha);
		}

		Window.setViewParameters();
	}

	/**
	 * Sets the depth bits of the PixelFormat for anti-aliasing. Using this
	 * method enables anti-aliasing if it is disabled. If the desired value
	 * fails, it will be set to the previous value.
	 * 
	 * @param depth
	 *            Desired number of depth bits
	 */
	public static void setAntiAliasDepth(int depth)
	{
		int previousDepth = antiAliasDepth;
		antiAliasDepth = depth;

		if (antiAliasDepth < 0)
		{
			antiAliasDepth = 0;
		} else if (antiAliasDepth > 24)
		{
			antiAliasDepth = 24;
		}

		try
		{
			pixForm = new PixelFormat(antiAliasAlpha, antiAliasDepth, antiAliasStencil, antiAliasSamples);
			Display.destroy();
			Display.create(pixForm);
		} catch (Exception e)
		{
			e.printStackTrace();
			setAntiAliasDepth(previousDepth);
		}

		Window.setViewParameters();
	}

	/**
	 * Sets the stencil bits of the PixelFormat for anti-aliasing. Using this
	 * method enables anti-aliasing if it is disabled. If the desired value
	 * fails, it will be set to the previous value.
	 * 
	 * @param stencil
	 *            Desired number of stencil bits
	 */
	public static void setAntiAliasStencil(int stencil)
	{
		int previousStencil = antiAliasStencil;
		antiAliasStencil = stencil;

		if (antiAliasStencil < 0)
		{
			antiAliasStencil = 0;
		} else if (antiAliasStencil > 8)
		{
			antiAliasStencil = 8;
		}

		try
		{
			pixForm = new PixelFormat(antiAliasAlpha, antiAliasDepth, antiAliasStencil, antiAliasSamples);
			Display.destroy();
			Display.create(pixForm);
		} catch (Exception e)
		{
			e.printStackTrace();
			setAntiAliasStencil(previousStencil);
		}

		Window.setViewParameters();
	}

	/**
	 * Sets the number of samples of the PixelFormat for anti-aliasing. Using
	 * this method enables anti-aliasing if it is disabled. If the desired value
	 * fails, it will be set to the previous value.
	 * 
	 * @param samples
	 *            Desired number of anti-aliasing samples
	 */
	public static void setAntiAliasSamples(int samples)
	{
		int previousSamples = antiAliasSamples;
		antiAliasSamples = samples;

		if (antiAliasSamples < 0)
		{
			antiAliasSamples = 0;
		} else if (antiAliasSamples > 16)
		{
			antiAliasSamples = 16;
		}

		try
		{
			pixForm = new PixelFormat(antiAliasAlpha, antiAliasDepth, antiAliasStencil, antiAliasSamples);
			Display.destroy();
			Display.create(pixForm);
		} catch (Exception e)
		{
			e.printStackTrace();
			setAntiAliasSamples(previousSamples);
		}

		Window.setViewParameters();
	}

	/**
	 * Sets all values of the PixelFormat for anti-aliasing. Using this method
	 * enables anti-aliasing if it is disabled. If the desired values fail, they
	 * will be set to their previous values.
	 * 
	 * @param alpha
	 *            Desired number of alpha bits
	 * @param depth
	 *            Desired number of depth bits
	 * @param stencil
	 *            Desired number of stencil bits
	 * @param samples
	 *            Desired number of anti-aliasing samples
	 */
	public static void setAntiAliasValues(int alpha, int depth, int stencil, int samples)
	{
		int previousAlpha = antiAliasAlpha;
		int previousDepth = antiAliasDepth;
		int previousStencil = antiAliasStencil;
		int previousSamples = antiAliasSamples;

		antiAliasAlpha = alpha;
		antiAliasDepth = depth;
		antiAliasStencil = stencil;
		antiAliasSamples = samples;

		if (antiAliasAlpha < 0)
		{
			antiAliasAlpha = 0;
		} else if (antiAliasAlpha > 8)
		{
			antiAliasAlpha = 8;
		}
		if (antiAliasDepth < 0)
		{
			antiAliasDepth = 0;
		} else if (antiAliasDepth > 24)
		{
			antiAliasDepth = 24;
		}
		if (antiAliasStencil < 0)
		{
			antiAliasStencil = 0;
		} else if (antiAliasStencil > 8)
		{
			antiAliasStencil = 8;
		}
		if (antiAliasSamples < 0)
		{
			antiAliasSamples = 0;
		} else if (antiAliasSamples > 16)
		{
			antiAliasSamples = 16;
		}

		try
		{
			pixForm = new PixelFormat(antiAliasAlpha, antiAliasDepth, antiAliasStencil, antiAliasSamples);
			Display.destroy();
			Display.create(pixForm);
		} catch (Exception e)
		{
			e.printStackTrace();
			setAntiAliasValues(previousAlpha, previousDepth, previousStencil, previousSamples);
		}

		Window.setViewParameters();
	}

	/**
	 * This method sets the anti-aliasing values to be used when the game is
	 * launched.
	 * 
	 * @param alpha
	 *            Desired number of alpha bits
	 * @param depth
	 *            Desired number of depth bits
	 * @param stencil
	 *            Desired number of stencil bits
	 * @param samples
	 *            Desired number of anti-aliasing samples
	 */
	public static void setInitialAntiAliasValues(int alpha, int depth, int stencil, int samples)
	{
		antiAliasAlpha = alpha;
		antiAliasDepth = depth;
		antiAliasStencil = stencil;
		antiAliasSamples = samples;
		pixForm = new PixelFormat(alpha, depth, stencil, samples);
	}

	public static int getAntiAliasAlpha()
	{
		return antiAliasAlpha;
	}

	public static int getAntiAliasDepth()
	{
		return antiAliasDepth;
	}

	public static int getAntiAliasStencil()
	{
		return antiAliasStencil;
	}

	public static int getAntiAliasSamples()
	{
		return antiAliasSamples;
	}

	public static PixelFormat getPixelFormat()
	{
		return pixForm;
	}

}
