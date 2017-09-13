package skyEngine.Inventory;

public class StaticInventory
{

	private AbstractItem[] inventory;

	/**
	 * Creates a StaticInventory object of a specified size
	 * 
	 * @param size
	 *            The number of elements in an inventory
	 */
	public StaticInventory(int size)
	{
		inventory = new AbstractItem[size];
	}

	/**
	 * Adds an item to the inventory but tries to stack it with an already
	 * existing item with the same itemID as it. If it cannot stack the item, it
	 * tries to find an empty spot in the inventory to add it. If it still
	 * fails, the item is not added to the inventory.
	 * 
	 * @param item
	 *            The item to add
	 */
	public void addItem(AbstractItem item)
	{
		int length = inventory.length;
		for (int i = 0; i < length; i++)
		{
			if (inventory[i] != null)
			{
				if (inventory[i].getItemID() == item.getItemID())
				{
					if (inventory[i].getQuantity() < inventory[i].getMaxQuantity())
					{
						if (inventory[i].getMaxQuantity() - inventory[i].getQuantity() >= item.getQuantity())
						{
							inventory[i].addToQuantity(item.getQuantity());
							return;
						} else
						{
							item.subtractFromQuantity(inventory[i].getMaxQuantity() - inventory[i].getQuantity());
							inventory[i].setQuantity(inventory[i].getMaxQuantity());
						}
					}
				}
			}
		}
		for (int i = 0; i < length; i++)
		{
			if (inventory[i] == null)
			{
				inventory[i] = item;
				return;
			}
		}
	}

	/**
	 * Tries to find instances of an AbstractItem object and remove all
	 * instances of it from the inventory
	 * 
	 * @param item
	 *            An AbstractItem object
	 */
	public void removeItem(AbstractItem item)
	{
		int length = inventory.length;
		for (int i = 0; i < length; i++)
		{
			if (inventory[i] != null)
			{
				if (inventory[i].equals(item))
				{
					inventory[i] = null;
				}
			}
		}
	}

	/**
	 * Tries to find instances of an AbstractItem object with a specific itemID
	 * value and remove all instances of it from the inventory
	 * 
	 * @param itemID
	 *            An itemID value
	 */
	public void removeItem(int itemID)
	{
		int length = inventory.length;
		for (int i = 0; i < length; i++)
		{
			if (inventory[i] != null)
			{
				if (inventory[i].getItemID() == itemID)
				{
					inventory[i] = null;
				}
			}
		}
	}

	/**
	 * Tries to find instances of an AbstractItem object with a specific name
	 * and remove all instances of it from the inventory
	 * 
	 * @param itemName
	 *            The name of the items to remove
	 */
	public void removeItem(String itemName)
	{
		int length = inventory.length;
		for (int i = 0; i < length; i++)
		{
			if (inventory[i] != null)
			{
				if (inventory[i].getName().equals(itemName))
				{
					inventory[i] = null;
				}	
			}
		}
	}

	/**
	 * Tries to remove the item located at a specific index of the inventory. If
	 * the provided index is outside of the bounds of the inventory, nothing
	 * happens.
	 * 
	 * @param index
	 *            Index of the item to remove
	 */
	public void removeItemAtIndex(int index)
	{
		if (index >= 0 && index < inventory.length)
		{
			inventory[index] = null;
		}
	}

	/**
	 * Creates an entirely new instance of the inventory with no AbstractItem
	 * objects inside of it
	 */
	public void clearInventory()
	{
		int size = inventory.length;
		inventory = new AbstractItem[size];
	}

	/**
	 * Gets the item at the specified index. If the given index is outside the
	 * bounds of the inventory, the return value is null.
	 * 
	 * @param index
	 *            The index to get the item from
	 * @return The item at the specified index (or null)
	 */
	public AbstractItem getItemAtIndex(int index)
	{
		if (index >= 0 && index < inventory.length)
		{
			if (inventory[index] != null)
			{
				return inventory[index];
			}
			else
			{
				return null;
			}
		} else
		{
			return null;
		}
	}

	/**
	 * Tries to set the item a specified index to a given AbstractItem object.
	 * If the given index is outside the bounds of the inventory, nothing
	 * happens.
	 * 
	 * @param item
	 *            The AbstractItem object to place at index
	 * @param index
	 *            The index to place the AbstractItem at
	 */
	public void setItemAtIndex(AbstractItem item, int index)
	{
		if (index >= 0 && index < inventory.length)
		{
			inventory[index] = item;
		}
	}

	/**
	 * Searches the inventory to try to find a specified AbstractItem object
	 * 
	 * @param item
	 *            The AbstractItem object to search for
	 * @return true if the item exists in the inventory, false otherwise
	 */
	public boolean containsItem(AbstractItem item)
	{
		int length = inventory.length;
		for (int i = 0; i < length; i++)
		{
			if (inventory[i] != null)
			{
				if (inventory[i].equals(item))
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Searches the inventory to try to find an AbstractItem object with a
	 * specified itemID
	 * 
	 * @param itemID
	 *            The itemID of the object to search for
	 * @return true if an item with the specified itemID exists in the
	 *         inventory, false otherwise
	 */
	public boolean containsItem(int itemID)
	{
		int length = inventory.length;
		for (int i = 0; i < length; i++)
		{
			if (inventory[i] != null)
			{
				if (inventory[i].getItemID() == itemID)
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Searches the inventory to try to find an AbstractItem object with a
	 * specified name
	 * 
	 * @param itemName
	 *            The name of the object to search for
	 * @return true if an item with the specified name exists in the inventory,
	 *         false otherwise
	 */
	public boolean containsItem(String itemName)
	{
		int length = inventory.length;
		for (int i = 0; i < length; i++)
		{
			if (inventory[i] != null)
			{
				if (inventory[i].getName().equals(itemName))
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Tries to find the index of a specified AbstractItem object in the
	 * inventory.
	 * 
	 * @param item
	 *            The AbstractItem object to search for
	 * @return The index of the item (-1 if it does not exist)
	 */
	public int indexOfItem(AbstractItem item)
	{
		int length = inventory.length;
		for (int i = 0; i < length; i++)
		{
			if (inventory[i] != null)
			{
				if (inventory[i].equals(item))
				{
					return i;
				}
			}			
		}
		return -1;
	}

	/**
	 * Tries to find the index of an AbstractItem object with a specified itemID
	 * value
	 * 
	 * @param itemID
	 *            The itemID value of the item to search for
	 * @return The index of the item with the itemID value (-1 if it does not
	 *         exist)
	 */
	public int indexOfItem(int itemID)
	{
		int length = inventory.length;
		for (int i = 0; i < length; i++)
		{
			if (inventory[i] != null)
			{
				if (inventory[i].getItemID() == itemID)
				{
					return i;
				}	
			}
		}
		return -1;
	}

	/**
	 * Tries to find the index of an AbstractItem object with a specified name
	 * 
	 * @param itemName
	 *            The name of the item to search for
	 * @return The index of the item with the name value (-1 if it does not
	 *         exist)
	 */
	public int indexOfItem(String itemName)
	{
		int length = inventory.length;
		for (int i = 0; i < length; i++)
		{
			if (inventory[i] != null)
			{
				if (inventory[i].getName().equals(itemName))
				{
					return i;
				}	
			}
		}
		return -1;
	}

	/**
	 * Gets the size of the inventory
	 * 
	 * @return The size of the inventory
	 */
	public int getSize()
	{
		return inventory.length;
	}

	/**
	 * Sets the size of the inventory to a specific size. If the new inventory
	 * is smaller than the old inventory, the items at the end of the inventory
	 * are removed to fit the new size of the inventory. If the new inventory is
	 * larger than the old inventory, empty spots will be created at the end of
	 * the new inventory.
	 * 
	 * @param size
	 *            The desired size of the new inventory
	 */
	public void setSize(int size)
	{
		int length;
		AbstractItem[] temp = inventory;
		inventory = new AbstractItem[size];

		if (inventory.length > temp.length)
		{
			length = temp.length;
		} else
		{
			length = inventory.length;
		}

		for (int i = 0; i < length; i++)
		{
			if (inventory[i] != null)
			{
				inventory[i] = temp[i];
			}
		}
	}

	/**
	 * Shifts all of the items as far as they can go toward the start of the
	 * array. As a result, all of the null values are moved to the end of the
	 * inventory array.
	 */
	public void moveEmptyToEnd()
	{
		int numberOfItems = 0;
		int length = inventory.length;
		AbstractItem[] temporary = new AbstractItem[length];

		for (int i = 0; i < length; i++)
		{
			if (inventory[i] != null)
			{
				temporary[numberOfItems] = inventory[i];
				numberOfItems++;
			}
		}

		inventory = temporary;
	}

	/**
	 * Sorts the inventory by item quantity from low to high
	 */
	public void sortByQuantityAscending()
	{
		int sortLength = inventory.length - 1;
		boolean isSorted = false;
		AbstractItem temporary = null;

		moveEmptyToEnd();
		for (int i = 0; i < inventory.length; i++)
		{
			if (inventory[i] == null)
			{
				sortLength = i - 1;
				break;
			}
		}

		while (!isSorted)
		{
			isSorted = true;
			for (int i = 0; i < sortLength; i++)
			{
				if (inventory[i].getQuantity() > inventory[i + 1].getQuantity())
				{
					isSorted = false;
					break;
				}
			}

			if (isSorted)
			{
				break;
			}

			for (int i = 0; i < sortLength; i++)
			{
				if (inventory[i].getQuantity() > inventory[i + 1].getQuantity())
				{
					temporary = inventory[i];
					inventory[i] = inventory[i + 1];
					inventory[i + 1] = temporary;
					temporary = null;
				}
			}
		}
	}

	/**
	 * Sorts the inventory by item quantity from high to low
	 */
	public void sortByQuantityDescending()
	{
		int sortLength = inventory.length - 1;
		boolean isSorted = false;
		AbstractItem temporary = null;

		moveEmptyToEnd();
		for (int i = 0; i < inventory.length; i++)
		{
			if (inventory[i] == null)
			{
				sortLength = i - 1;
				break;
			}
		}

		while (!isSorted)
		{
			isSorted = true;
			for (int i = 0; i < sortLength; i++)
			{
				if (inventory[i].getQuantity() < inventory[i + 1].getQuantity())
				{
					isSorted = false;
					break;
				}
			}

			if (isSorted)
			{
				break;
			}

			for (int i = 0; i < sortLength; i++)
			{
				if (inventory[i].getQuantity() < inventory[i + 1].getQuantity())
				{
					temporary = inventory[i];
					inventory[i] = inventory[i + 1];
					inventory[i + 1] = temporary;
					temporary = null;
				}
			}
		}
	}

	/**
	 * Sorts the inventory by name from A to Z
	 */
	public void sortByNameAscending()
	{
		int sortLength = inventory.length - 1;
		boolean isSorted = false;
		AbstractItem temporary = null;

		moveEmptyToEnd();
		for (int i = 0; i < inventory.length; i++)
		{
			if (inventory[i] == null)
			{
				sortLength = i - 1;
				break;
			}
		}

		while (!isSorted)
		{
			isSorted = true;
			for (int i = 0; i < sortLength; i++)
			{
				if (inventory[i].getName().compareToIgnoreCase(inventory[i + 1].getName()) > 0)
				{
					isSorted = false;
					break;
				}
			}

			if (isSorted)
			{
				break;
			}

			for (int i = 0; i < sortLength; i++)
			{
				if (inventory[i].getName().compareToIgnoreCase(inventory[i + 1].getName()) > 0)
				{
					temporary = inventory[i];
					inventory[i] = inventory[i + 1];
					inventory[i + 1] = temporary;
					temporary = null;
				}
			}
		}
	}

	/**
	 * Sorts the inventory by name from Z to A
	 */
	public void sortByNameDescending()
	{
		int sortLength = inventory.length - 1;
		boolean isSorted = false;
		AbstractItem temporary = null;

		moveEmptyToEnd();
		for (int i = 0; i < inventory.length; i++)
		{
			if (inventory[i] == null)
			{
				sortLength = i - 1;
				break;
			}
		}

		while (!isSorted)
		{
			isSorted = true;
			for (int i = 0; i < sortLength; i++)
			{
				if (inventory[i].getName().compareToIgnoreCase(inventory[i + 1].getName()) < 0)
				{
					isSorted = false;
					break;
				}
			}

			if (isSorted)
			{
				break;
			}

			for (int i = 0; i < sortLength; i++)
			{
				if (inventory[i].getName().compareToIgnoreCase(inventory[i + 1].getName()) < 0)
				{
					temporary = inventory[i];
					inventory[i] = inventory[i + 1];
					inventory[i + 1] = temporary;
					temporary = null;
				}
			}
		}
	}

	/**
	 * Sorts the inventory by itemID from low to high
	 */
	public void sortByIDAscending()
	{
		int sortLength = inventory.length - 1;
		boolean isSorted = false;
		AbstractItem temporary = null;

		moveEmptyToEnd();
		for (int i = 0; i < inventory.length; i++)
		{
			if (inventory[i] == null)
			{
				sortLength = i - 1;
				break;
			}
		}

		while (!isSorted)
		{
			isSorted = true;
			for (int i = 0; i < sortLength; i++)
			{
				if (inventory[i].getItemID() > inventory[i + 1].getItemID())
				{
					isSorted = false;
					break;
				}
			}

			if (isSorted)
			{
				break;
			}

			for (int i = 0; i < sortLength; i++)
			{
				if (inventory[i].getItemID() > inventory[i + 1].getItemID())
				{
					temporary = inventory[i];
					inventory[i] = inventory[i + 1];
					inventory[i + 1] = temporary;
					temporary = null;
				}
			}
		}
	}

	/**
	 * Sorts the inventory by itemID from high to low
	 */
	public void sortByIDDescending()
	{
		int sortLength = inventory.length - 1;
		boolean isSorted = false;
		AbstractItem temporary = null;

		moveEmptyToEnd();
		for (int i = 0; i < inventory.length; i++)
		{
			if (inventory[i] == null)
			{
				sortLength = i - 1;
				break;
			}
		}

		while (!isSorted)
		{
			isSorted = true;
			for (int i = 0; i < sortLength; i++)
			{
				if (inventory[i].getItemID() < inventory[i + 1].getItemID())
				{
					isSorted = false;
					break;
				}
			}

			if (isSorted)
			{
				break;
			}

			for (int i = 0; i < sortLength; i++)
			{
				if (inventory[i].getItemID() < inventory[i + 1].getItemID())
				{
					temporary = inventory[i];
					inventory[i] = inventory[i + 1];
					inventory[i + 1] = temporary;
					temporary = null;
				}
			}
		}
	}

	public AbstractItem[] getInventory()
	{
		return inventory;
	}

	public void setInventory(AbstractItem[] inventory)
	{
		this.inventory = inventory;
	}

}
