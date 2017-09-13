package skyEngine.Menu;

import org.newdawn.slick.opengl.Texture;

import skyEngine.Core.Input;
import skyEngine.Core.Renderer;

public class Checkbox
{

	private Texture uncheckedTexture;
	private Texture checkedTexture;
	private boolean checked = false;
	private float x = 0;
	private float y = 0;
	private float width;
	private float height;

	/**
	 * Creates an instance of a Checkbox object and sets all necessary values.
	 * 
	 * @param x
	 *            Position of the Checkbox along the x-axis
	 * @param y
	 *            Position of the Checkbox along the y-axis
	 * @param uncheckedTexture
	 *            Texture of the unchecked Checkbox
	 * @param checkedTexture
	 *            Texture of the checked Checkbox
	 */
	public Checkbox(float x, float y, Texture uncheckedTexture, Texture checkedTexture)
	{
		this.x = x;
		this.y = y;
		this.uncheckedTexture = uncheckedTexture;
		this.checkedTexture = checkedTexture;
		width = checkedTexture.getImageWidth();
		height = checkedTexture.getImageHeight();
	}

	/**
	 * Creates an instance of a Checkbox object and sets all necessary values.
	 * Height and width are generated from the provided checkedTexture.
	 * 
	 * @param x
	 *            Position of the Checkbox along the x-axis
	 * @param y
	 *            Position of the Checkbox along the y-axis
	 * @param uncheckedTexture
	 *            Texture of the unchecked Checkbox
	 * @param checkedTexture
	 *            Texture of the checked Checkbox
	 * @param checked
	 *            Should the Checkbox be initialized as checked
	 */
	public Checkbox(float x, float y, Texture uncheckedTexture, Texture checkedTexture, boolean checked)
	{
		this.x = x;
		this.y = y;
		this.uncheckedTexture = uncheckedTexture;
		this.checkedTexture = checkedTexture;
		this.checked = checked;
		width = checkedTexture.getImageWidth();
		height = checkedTexture.getImageHeight();
	}

	/**
	 * The method used to update the Checkbox.
	 */
	public void update()
	{
		if (Input.isButtonPressed(Input.leftMouseButton))
		{
			if (Input.getMouseX() >= x && Input.getMouseX() <= x + width)
			{
				if (Input.getMouseY() >= y && Input.getMouseY() <= y + height)
				{
					setChecked(!checked);
				}
			}
		}
	}

	/**
	 * The method used to render the Checkbox
	 * 
	 * @param r
	 *            An instance of a renderer object
	 */
	public void render(Renderer r)
	{
		if (checked)
		{
			r.drawTextureQuad(x, y, checkedTexture);
		} else
		{
			r.drawTextureQuad(x, y, uncheckedTexture);
		}
	}

	public boolean isChecked()
	{
		return checked;
	}

	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getWidth()
	{
		return width;
	}

	public float getHeight()
	{
		return height;
	}

}
