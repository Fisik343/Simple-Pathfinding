package skyEngine.Menu;

import org.newdawn.slick.opengl.Texture;

import skyEngine.Core.Input;
import skyEngine.Core.Renderer;

public class Button
{

	private float x;
	private float y;
	private int width;
	private int height;
	private Texture image;
	private boolean active = false;
	private boolean killswitch = false;

	/**
	 * Constructor for a Button object
	 * 
	 * @param x
	 *            X-coordinate of the button
	 * @param y
	 *            Y-coordinate of the button
	 * @param image
	 *            Texture of the button
	 */
	public Button(float x, float y, Texture image)
	{
		this.x = x;
		this.y = y;
		this.image = image;
		this.width = image.getImageWidth();
		this.height = image.getImageHeight();
	}

	/**
	 * Update method for Button objects
	 */
	public void update()
	{
		if (Input.isButton(Input.leftMouseButton))
		{
			if (Input.getMouseX() >= x && Input.getMouseX() <= x + width)
			{
				if (Input.getMouseY() >= y && Input.getMouseY() <= y + height)
				{
					if (active)
					{
						killswitch = true;
					}
					active = true;
				} else
				{
					active = false;
					killswitch = false;
				}
			} else
			{
				active = false;
				killswitch = false;
			}
		} else
		{
			active = false;
			killswitch = false;
		}
	}

	/**
	 * Render method for Button objects
	 * 
	 * @param r
	 *            An instance of a Renderer object
	 */
	public void render(Renderer r)
	{
		r.drawTextureQuad(x, y, image);
	}

	/**
	 * Gets if the button has been pressed
	 * 
	 * @return True if the button was pressed last update
	 */
	public boolean isPressed()
	{
		if (active)
		{
			if (killswitch)
			{
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * Gets if the button is down
	 * 
	 * @return True if the button is down
	 */
	public boolean isDown()
	{
		if (active)
		{
			return true;
		}
		return false;
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public int getWidth()
	{
		return image.getImageWidth();
	}

	public int getHeight()
	{
		return image.getImageHeight();
	}
	
	public Texture getImage()
	{
		return image;
	}

}
