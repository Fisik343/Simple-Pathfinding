package skyEngine.Menu;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

import skyEngine.Core.Renderer;

public class RadioButton
{

	private int quantity;
	private float x;
	private float y;
	private float gap;
	private Texture unchecked;
	private Texture checked;
	private ArrayList<Checkbox> boxCollection;
	private ArrayList<Boolean> previousChecked;
	private boolean isHorizontal = false;
	private boolean locked = true;
	private boolean isOneChecked = false;

	/**
	 * Creates a new instance of a RadioButton object (stacked vertically) with
	 * default spacing
	 * 
	 * @param quantity
	 *            The number of radio buttons
	 * @param x
	 *            X-coordinate of the first radio button
	 * @param y
	 *            Y-coordinate of the first radio button
	 * @param unchecked
	 *            Texture of an unchecked radio button
	 * @param checked
	 *            Texture of a checked radio button
	 */
	public RadioButton(int quantity, float x, float y, Texture unchecked, Texture checked)
	{
		if (quantity < 1)
		{
			this.quantity = 1;
		} else
		{

			this.quantity = quantity;
		}
		this.x = x;
		this.y = y;
		this.unchecked = unchecked;
		this.checked = checked;
		gap = unchecked.getImageHeight() / 2;
		isHorizontal = false;
		initializeRadioButtons();
	}

	/**
	 * Creates a new instance of a RadioButton object with default spacing
	 * 
	 * @param quantity
	 *            The number of radio buttons
	 * @param x
	 *            X-coordinate of the first radio button
	 * @param y
	 *            Y-coordinate of the first radio button
	 * @param unchecked
	 *            Texture of an unchecked radio button
	 * @param checked
	 *            Texture of a checked radio button
	 * @param isHorizontal
	 *            True if the radio buttons are horizontally aligned, false if
	 *            vertical
	 */
	public RadioButton(int quantity, float x, float y, Texture unchecked, Texture checked, boolean isHorizontal)
	{
		if (quantity < 1)
		{
			this.quantity = 1;
		} else
		{

			this.quantity = quantity;
		}
		this.x = x;
		this.y = y;
		this.unchecked = unchecked;
		this.checked = checked;
		this.isHorizontal = isHorizontal;
		if (isHorizontal)
		{
			gap = unchecked.getImageWidth() / 2;
		} else
		{
			gap = unchecked.getImageHeight() / 2;
		}
		initializeRadioButtons();
	}

	/**
	 * Creates an instance of a RadioButton object (stacked vertically)
	 * 
	 * @param quantity
	 *            The number of radio buttons
	 * @param x
	 *            X-coordinate of the first radio button
	 * @param y
	 *            Y-coordinate of the first radio button
	 * @param gap
	 *            The space between radio buttons
	 * @param unchecked
	 *            Texture of an unchecked radio button
	 * @param checked
	 *            Texture of a checked radio button
	 */
	public RadioButton(int quantity, float x, float y, float gap, Texture unchecked, Texture checked)
	{
		if (quantity < 1)
		{
			this.quantity = 1;
		} else
		{

			this.quantity = quantity;
		}
		this.x = x;
		this.y = y;
		this.gap = gap;
		this.unchecked = unchecked;
		this.checked = checked;
		isHorizontal = false;
		initializeRadioButtons();
	}

	/**
	 * Creates an instance of a RadioButton object
	 * 
	 * @param quantity
	 *            The number of radio buttons
	 * @param x
	 *            X-coordinate of the first radio button
	 * @param y
	 *            Y-coordinate of the first radio button
	 * @param gap
	 *            The space between radio buttons
	 * @param unchecked
	 *            Texture of an unchecked radio button
	 * @param checked
	 *            Texture of a checked radio button
	 * @param isHorizontal
	 *            True if the radio buttons are horizontally aligned, false if
	 *            vertical
	 */
	public RadioButton(int quantity, float x, float y, float gap, Texture unchecked, Texture checked,
			boolean isHorizontal)
	{
		if (quantity < 1)
		{
			this.quantity = 1;
		} else
		{

			this.quantity = quantity;
		}
		this.x = x;
		this.y = y;
		this.gap = gap;
		this.unchecked = unchecked;
		this.checked = checked;
		this.isHorizontal = isHorizontal;
		initializeRadioButtons();
	}

	/**
	 * Sets up the ArrayList of Checkbox objects
	 */
	private void initializeRadioButtons()
	{
		boxCollection = new ArrayList<Checkbox>();
		if (isHorizontal)
		{
			for (int i = 0; i < quantity; i++)
			{
				boxCollection.add(new Checkbox(x + i * (unchecked.getImageWidth() + gap), y, unchecked, checked));
			}
		} else
		{
			for (int i = 0; i < quantity; i++)
			{
				boxCollection.add(new Checkbox(x, y + i * (unchecked.getImageHeight() + gap), unchecked, checked));
			}
		}

		previousChecked = new ArrayList<Boolean>();
		for (Checkbox c : boxCollection)
		{
			previousChecked.add(c.isChecked());
		}
	}

	/**
	 * The update method for RadioButton objects
	 */
	public void update()
	{
		for (Checkbox c : boxCollection)
		{
			c.update();
			if (c.isChecked())
			{
				if (!previousChecked.get(boxCollection.indexOf(c)))
				{
					setChecked(boxCollection.indexOf(c));
				}
				isOneChecked = true;
			}
		}

		if (locked)
		{
			if (!isOneChecked)
			{
				for (Boolean b : previousChecked)
				{
					if (b)
					{
						setChecked(previousChecked.indexOf(b));
						isOneChecked = true;
						break;
					}
				}
				if (!isOneChecked)
				{
					setChecked(0);
				}
			}
			isOneChecked = false;
		}

		for (Checkbox c : boxCollection)
		{
			previousChecked.set(boxCollection.indexOf(c), c.isChecked());
		}
	}

	/**
	 * The render method for RadioButton objects
	 * 
	 * @param r
	 *            An instance of a Renderer object
	 */
	public void render(Renderer r)
	{
		for (Checkbox c : boxCollection)
		{
			c.render(r);
		}
	}

	/**
	 * Sets only one box to be checked
	 * 
	 * @param index
	 *            Index of the box to be checked
	 */
	public void setChecked(int index)
	{
		if (index < boxCollection.size())
		{
			for (Checkbox c : boxCollection)
			{
				if (boxCollection.indexOf(c) == index)
				{
					c.setChecked(true);
				} else
				{
					c.setChecked(false);
				}
			}
		} else
		{
			setChecked(0);
		}
	}

	/**
	 * Gets the index of the selected radio button
	 * 
	 * @return The index of the selected radio button or -1 if none are selected
	 */
	public int getSelectedIndex()
	{
		for (Checkbox c : boxCollection)
		{
			if (c.isChecked())
			{
				return boxCollection.indexOf(c);
			}
		}
		return -1;
	}

	/**
	 * Locks the RadioButton object such that one object must always be selected
	 */
	public void lock()
	{
		locked = true;
	}

	/**
	 * Unlocks the RadioButton object such that it is possible for zero options
	 * to be selected
	 */
	public void unlock()
	{
		locked = false;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public float getGap()
	{
		return gap;
	}

	public boolean isLocked()
	{
		return locked;
	}

	public boolean isOneChecked()
	{
		return isOneChecked;
	}
}
