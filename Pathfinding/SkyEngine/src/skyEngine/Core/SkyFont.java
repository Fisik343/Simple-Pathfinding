package skyEngine.Core;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public class SkyFont
{

	private TrueTypeFont useFont;
	private int fontStyle = Font.PLAIN;
	private float fontSize = 32;
	private String fontName = "Times New Roman";

	private int red = 255;
	private int green = 255;
	private int blue = 255;
	private int alpha = 255;
	private Color compositeColor = new Color(red, green, blue, alpha);

	/**
	 * The default constructor for SkyFont. It creates a font that uses Times
	 * New Roman, plain, 32px.
	 */
	public SkyFont()
	{
		Font tempFont = new Font("Times New Roman", Font.PLAIN, 32);
		useFont = new TrueTypeFont(tempFont, true);
	}

	/**
	 * The SkyFont constructor which uses system fonts. If the system does not
	 * have the font, it creates an instance of Times New Roman, plain, 32px.
	 * 
	 * @param font
	 *            The desired font
	 * @param style
	 *            The desired style (ex. Font.BOLD)
	 * @param size
	 *            The desired size in pixels
	 */
	public SkyFont(String font, int style, float size)
	{
		try
		{
			fontStyle = style;
			fontSize = size;
			fontName = font;
			Font tempFont = new Font(fontName, fontStyle, (int) fontSize);
			useFont = new TrueTypeFont(tempFont, true);
		} catch (Exception e)
		{
			e.printStackTrace();
			Font tempFont = new Font("Times New Roman", Font.PLAIN, 32);
			useFont = new TrueTypeFont(tempFont, true);
		}

	}

	/**
	 * The SkyFont constructor which uses .ttf files. If the creation fails, it
	 * creates an instance of Times New Roman, plain, 32px.
	 * 
	 * @param path
	 *            The path to the .ttf file
	 * @param size
	 *            The desired size in pixels
	 */
	public SkyFont(String path, float size)
	{
		try
		{
			fontSize = size;
			InputStream is = ResourceLoader.getResourceAsStream(path);
			Font tempFont = Font.createFont(Font.TRUETYPE_FONT, is);
			tempFont = tempFont.deriveFont(fontSize);
			useFont = new TrueTypeFont(tempFont, true);
		} catch (Exception e)
		{
			e.printStackTrace();
			Font tempFont = new Font("Times New Roman", Font.PLAIN, 32);
			useFont = new TrueTypeFont(tempFont, true);
		}
	}

	/**
	 * Sets the color of the font
	 * 
	 * @param red
	 *            Amount red (0-255)
	 * @param green
	 *            Amount green (0-255)
	 * @param blue
	 *            Amount blue (0-255)
	 * @param alpha
	 *            Amount alpha (0-255)
	 */
	public void setColor(int red, int green, int blue, int alpha)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
		compositeColor = new Color(this.red, this.green, this.blue, this.alpha);
	}

	/**
	 * Changes the style of the font. Has no effect on fonts made with .ttf
	 * files
	 * 
	 * @param style
	 *            The style of the font to use from java.awt.Font
	 */
	public void setStyle(int style)
	{
		int oldStyle = fontStyle;
		fontStyle = style;
		TrueTypeFont oldFont = useFont;
		try
		{
			Font tempFont = new Font(fontName, fontStyle, (int) fontSize);
			useFont = new TrueTypeFont(tempFont, true);
		} catch (Exception e)
		{
			e.printStackTrace();
			useFont = oldFont;
			fontStyle = oldStyle;
		}

	}

	/**
	 * Draws a string on the screen
	 * 
	 * @param x
	 *            Position of text along the x-axis
	 * @param y
	 *            Position of text along the y-axis
	 * @param text
	 *            The text to be drawn
	 */
	public void drawText(float x, float y, String text)
	{
		Color.white.bind();
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		useFont.drawString(x, y, text, compositeColor);
		glDisable(GL_BLEND);
	}

	/**
	 * A clean way to get the width of a string
	 * 
	 * @param text
	 *            The text to get the width of
	 * @return The width of the text in pixels
	 */
	public float getTextWidth(String text)
	{
		return useFont.getWidth(text);
	}

	public TrueTypeFont getUseFont()
	{
		return useFont;
	}

	public int getFontStyle()
	{
		return fontStyle;
	}

	public float getFontSize()
	{
		return fontSize;
	}

	public int getRed()
	{
		return red;
	}

	public int getGreen()
	{
		return green;
	}

	public int getBlue()
	{
		return blue;
	}

	public int getAlpha()
	{
		return alpha;
	}

	public Color getColor()
	{
		return compositeColor;
	}

}
