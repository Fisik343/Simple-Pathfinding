package skyEngine.Core;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Renderer
{

	/**
	 * Clears the screen of color and depth data
	 */
	public void clearScreen()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	/**
	 * This draws a quad to the screen
	 * 
	 * @param x
	 *            Upper left X coordinate
	 * @param y
	 *            Upper left Y coordinate
	 * @param width
	 *            Width of the quad
	 * @param height
	 *            Height of the quad
	 * @param r
	 *            Red (0-255)
	 * @param g
	 *            Green (0-255)
	 * @param b
	 *            Blue (0-255)
	 * @param a
	 *            Alpha [Transparency] (0-255)
	 */
	public void drawColorQuad(float x, float y, float width, float height, float r, float g, float b, float a)
	{
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glColor4f(r / 255, g / 255, b / 255, a / 255);
		glBegin(GL_QUADS);
		glColor4f(r / 255, g / 255, b / 255, a / 255);
		glVertex2f(x, y);
		glVertex2f(x + width, y);
		glVertex2f(x + width, y + height);
		glVertex2f(x, y + height);
		glEnd();
		glDisable(GL_BLEND);
	}

	/**
	 * This draws a quad to the screen
	 * 
	 * @param x
	 *            Upper left X coordinate
	 * @param y
	 *            Upper left Y coordinate
	 * @param width
	 *            Width of the quad
	 * @param height
	 *            Height of the quad
	 * @param r
	 *            Red (0-255)
	 * @param g
	 *            Green (0-255)
	 * @param b
	 *            Blue (0-255)
	 * @param a
	 *            Alpha [Transparency] (0-255)
	 * @param degrees
	 *            The number of degrees (clockwise) to rotate the quad
	 */
	public void drawColorQuad(float x, float y, float width, float height, float r, float g, float b, float a,
			float degrees)
	{
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glColor4f(r / 255, g / 255, b / 255, a / 255);
		glPushMatrix();
		glTranslatef(x + width / 2, y + height / 2, 0f);
		glRotatef(degrees, 0, 0, 1f);
		glTranslatef(-(x + width / 2), -(y + height / 2), 0f);
		glBegin(GL_QUADS);
		glColor4f(r / 255, g / 255, b / 255, a / 255);
		glVertex2f(x, y);
		glVertex2f(x + width, y);
		glVertex2f(x + width, y + height);
		glVertex2f(x, y + height);
		glEnd();
		glPopMatrix();
		glDisable(GL_BLEND);
	}

	/**
	 * This method loads textures into a game environment
	 * 
	 * @param path
	 *            The path to the texture resource
	 * @param format
	 *            The format of the image (such as PNG or GIF)
	 * @return The texture that is given in the path
	 */
	public Texture loadTexture(String path, String format)
	{
		try
		{
			return TextureLoader.getTexture(format, ResourceLoader.getResourceAsStream(path));
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method draws a quad with a texture onto the screen. The quad has the
	 * dimensions of the image used as the texture.
	 * 
	 * @param x
	 *            Upper-left X coordinate
	 * @param y
	 *            Upper-left Y coordinate
	 * @param tex
	 *            The texture to be displayed with the quad
	 */
	public void drawTextureQuad(float x, float y, Texture tex)
	{
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_TEXTURE_2D);
		Color.white.bind();
		tex.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(x, y);
		glTexCoord2f(1, 0);
		glVertex2f(x + tex.getTextureWidth(), y);
		glTexCoord2f(1, 1);
		glVertex2f(x + tex.getTextureWidth(), y + tex.getTextureHeight());
		glTexCoord2f(0, 1);
		glVertex2f(x, y + tex.getTextureHeight());
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}

	/**
	 * This method draws a quad with a texture onto the screen. The quad has the
	 * dimensions of the image used as the texture.
	 * 
	 * @param x
	 *            Upper-left X coordinate
	 * @param y
	 *            Upper-left Y coordinate
	 * @param tex
	 *            The texture to be displayed with the quad
	 * @param degrees
	 *            The number of degrees (clockwise) to rotate the texture
	 */
	public void drawTextureQuad(float x, float y, Texture tex, float degrees)
	{
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_TEXTURE_2D);
		Color.white.bind();
		tex.bind();
		glPushMatrix();
		glTranslatef(x + tex.getTextureWidth() / 2, y + tex.getTextureHeight() / 2, 0f);
		glRotatef(degrees, 0, 0, 1f);
		glTranslatef(-(x + tex.getTextureWidth() / 2), -(y + tex.getTextureHeight() / 2), 0f);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(x, y);
		glTexCoord2f(1, 0);
		glVertex2f(x + tex.getTextureWidth(), y);
		glTexCoord2f(1, 1);
		glVertex2f(x + tex.getTextureWidth(), y + tex.getTextureHeight());
		glTexCoord2f(0, 1);
		glVertex2f(x, y + tex.getTextureHeight());
		glEnd();
		glPopMatrix();
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}

	/**
	 * This method draws a quad with a texture onto the screen. The quad has
	 * specified dimensions.
	 * 
	 * @param x
	 *            Upper-left X coordinate
	 * @param y
	 *            Upper-left Y coordinate
	 * @param width
	 *            The width of the quad to be rendered
	 * @param height
	 *            The height of the quad to be rendered
	 * @param tex
	 *            The texture to be displayed with the quad
	 * @param interpolation
	 *            Should the renderer use interpolation when scaling the texture
	 */
	public void drawTextureQuad(float x, float y, float width, float height, Texture tex, boolean interpolation)
	{
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_TEXTURE_2D);
		Color.white.bind();
		tex.bind();
		if (!interpolation)
		{
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		}
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(x, y);
		glTexCoord2f(1, 0);
		glVertex2f(x + width, y);
		glTexCoord2f(1, 1);
		glVertex2f(x + width, y + height);
		glTexCoord2f(0, 1);
		glVertex2f(x, y + height);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	}

	/**
	 * This method draws a quad with a texture onto the screen. The quad has
	 * specified dimensions.
	 * 
	 * @param x
	 *            Upper-left X coordinate
	 * @param y
	 *            Upper-left Y coordinate
	 * @param width
	 *            The width of the quad to be rendered
	 * @param height
	 *            The height of the quad to be rendered
	 * @param tex
	 *            The texture to be displayed with the quad
	 * @param interpolation
	 *            Should the renderer use interpolation when scaling the texture
	 * @param degrees
	 *            The number of degrees (clockwise) to rotate the texture
	 */
	public void drawTextureQuad(float x, float y, float width, float height, Texture tex, boolean interpolation,
			float degrees)
	{
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_TEXTURE_2D);
		Color.white.bind();
		tex.bind();
		if (!interpolation)
		{
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		}
		glPushMatrix();
		glTranslatef(x + width / 2, y + height / 2, 0f);
		glRotatef(degrees, 0, 0, 1f);
		glTranslatef(-(x + width / 2), -(y + height / 2), 0f);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(x, y);
		glTexCoord2f(1, 0);
		glVertex2f(x + width, y);
		glTexCoord2f(1, 1);
		glVertex2f(x + width, y + height);
		glTexCoord2f(0, 1);
		glVertex2f(x, y + height);
		glEnd();
		glPopMatrix();
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	}

	/**
	 * This method draws a quad with a texture to the screen. The quad is scaled
	 * by a specified amount (a scale of 1.0f uses the dimensions of the texture
	 * as the dimensions of the quad).
	 * 
	 * @param x
	 *            Upper-left X coordinate
	 * @param y
	 *            Upper-left Y coordinate
	 * @param scaleX
	 *            The factor that the quad should be scaled by along the x-axis
	 * @param scaleY
	 *            The factor that the quad should be scaled by along the y-axis
	 * @param tex
	 *            The texture to be displayed with the quad
	 * @param interpolation
	 *            Should the renderer use interpolation when scaling the texture
	 * @param degrees
	 *            The number of degrees (clockwise) to rotate the texture
	 */
	public void drawScaledTextureQuad(float x, float y, float scaleX, float scaleY, Texture tex, boolean interpolation)
	{
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_TEXTURE_2D);
		Color.white.bind();
		tex.bind();
		if (!interpolation)
		{
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		}
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(x, y);
		glTexCoord2f(1, 0);
		glVertex2f(x + tex.getTextureWidth() * scaleX, y);
		glTexCoord2f(1, 1);
		glVertex2f(x + tex.getTextureWidth() * scaleX, y + tex.getTextureHeight() * scaleY);
		glTexCoord2f(0, 1);
		glVertex2f(x, y + tex.getTextureHeight() * scaleY);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	}

	/**
	 * This method draws a quad with a texture to the screen. The quad is scaled
	 * by a specified amount (a scale of 1.0f uses the dimensions of the texture
	 * as the dimensions of the quad).
	 * 
	 * @param x
	 *            Upper-left X coordinate
	 * @param y
	 *            Upper-left Y coordinate
	 * @param scaleX
	 *            The factor that the quad should be scaled by along the x-axis
	 * @param scaleY
	 *            The factor that the quad should be scaled by along the y-axis
	 * @param tex
	 *            The texture to be displayed with the quad
	 * @param interpolation
	 *            Should the renderer use interpolation when scaling the texture
	 * @param degrees
	 *            The number of degrees (clockwise) to rotate the texture
	 */
	public void drawScaledTextureQuad(float x, float y, float scaleX, float scaleY, Texture tex, boolean interpolation,
			float degrees)
	{
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_TEXTURE_2D);
		Color.white.bind();
		tex.bind();
		if (!interpolation)
		{
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		}
		glPushMatrix();
		glTranslatef(x + tex.getTextureWidth() * scaleX / 2, y + tex.getTextureHeight() * scaleY / 2, 0f);
		glRotatef(degrees, 0, 0, 1f);
		glTranslatef(-(x + tex.getTextureWidth() * scaleX / 2), -(y + tex.getTextureHeight() * scaleY / 2), 0f);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(x, y);
		glTexCoord2f(1, 0);
		glVertex2f(x + tex.getTextureWidth() * scaleX, y);
		glTexCoord2f(1, 1);
		glVertex2f(x + tex.getTextureWidth() * scaleX, y + tex.getTextureHeight() * scaleY);
		glTexCoord2f(0, 1);
		glVertex2f(x, y + tex.getTextureHeight() * scaleY);
		glEnd();
		glPopMatrix();
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	}

	/**
	 * This is a simplified call to two OpenGL bindings which clamp the textures
	 * to the edge of the rendered quad.
	 */
	public void clampTextures()
	{
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	}

	/**
	 * This is a simplified call to two OpenGL bindings which disable clamping
	 * the textures to the edge of the rendered quad.
	 */
	public void unclampTextures()
	{
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
	}

}
