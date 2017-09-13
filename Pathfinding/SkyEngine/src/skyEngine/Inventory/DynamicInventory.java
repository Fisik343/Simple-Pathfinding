package skyEngine.Inventory;

import java.util.ArrayList;

public class DynamicInventory
{

	private ArrayList<AbstractItem> inventory = new ArrayList<AbstractItem>();

	/**
	 * Adds an item to the inventory but tries to stack it with an already
	 * existing item with the same itemID as it. If it cannot stack the item, it
	 * adds the item to the end of the inventory ArrayList.
	 * 
	 * @param item
	 *            The item to add
	 */
	public void addItem(AbstractItem item)
	{
		int length = inventory.size();
		for (int i = 0; i < length; i++)
		{
			if (inventory.get(i).getItemID() == item.getItemID())
			{
				if (inventory.get(i).getQuantity() < inventory.get(i).getMaxQuantity())
				{
					if (inventory.get(i).getMaxQuantity() - inventory.get(i).getQuantity() >= item.getQuantity())
					{
						inventory.get(i).addToQuantity(item.getQuantity());
						return;
					} else
					{
						item.subtractFromQuantity(inventory.get(i).getMaxQuantity() - inventory.get(i).getQuantity());
						inventory.get(i).setQuantity(inventory.get(i).getMaxQuantity());
					}
				}
			}
		}
		inventory.add(item);
		removeNulls();
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
		removeNulls();
		int length = inventory.size();
		for (int i = 0; i < length; i++)
		{
			if (inventory.get(i).equals(item))
			{
				inventory.remove(i);
				i--;
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
		removeNulls();
		int length = inventory.size();
		for (int i = 0; i < length; i++)
		{
			if (inventory.get(i).getItemID() == itemID)
			{
				inventory.remove(i);
				i--;
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
		removeNulls();
		int length = inventory.size();
		for (int i = 0; i < length; i++)
		{
			if (inventory.get(i).getName().equalsIgnoreCase(itemName))
			{
				inventory.remove(i);
				i--;
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
		removeNulls();
		if (index >= 0 && index < inventory.size())
		{
			inventory.remove(index);
		}
	}

	/**
	 * Creates an entirely new instance of the inventory with no AbstractItem
	 * objects inside of it
	 */
	public void clearInventory()
	{
		inventory = new ArrayList<AbstractItem>();
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
		removeNulls();
		if (index >= 0 && index < inventory.size())
		{
			return inventory.get(index);
		}
		return null;
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
		if (index >= 0 && index < inventory.size())
		{
			inventory.set(index, item);
		}
		removeNulls();
	}

	/**
	 * Tries to insert an item at a specified index. If the given index is
	 * outside the bounds of the inventory, it is added at the appropriate side
	 * of the inventory.
	 * 
	 * @param item
	 *            The AbstractItem object to insert at index
	 * @param index
	 *            The index to insert the AbstractItem at
	 */
	public void insertItemAtIndex(AbstractItem item, int index)
	{
		if (index >= 0 && index <= inventory.size())
		{
			inventory.add(index, item);
		} else if (index < 0)
		{
			inventory.add(0, item);
		} else if (index > inventory.size())
		{
			inventory.add(item);
		}
		removeNulls();
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
		removeNulls();
		return inventory.contains(item);
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
		removeNulls();
		int length = inventory.size();
		for (int i = 0; i < length; i++)
		{
			if (inventory.get(i).getItemID() == itemID)
			{
				return true;
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
		removeNulls();
		int length = inventory.size();
		for (int i = 0; i < length; i++)
		{
			if (inventory.get(i).getName().equals(itemName))
			{
				return true;
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
		removeNulls();
		return inventory.indexOf(item);
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
		removeNulls();
		int length = inventory.size();
		for (int i = 0; i < length; i++)
		{
			if (inventory.get(i).getItemID() == itemID)
			{
				return i;
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
		removeNulls();
		int length = inventory.size();
		for (int i = 0; i < length; i++)
		{
			if (inventory.get(i).getName().equals(itemName))
			{
				return i;
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
		removeNulls();
		return inventory.size();
	}

	/**
	 * Sorts the inventory by item quantity from low to high
	 */
	public void sortByQuantityAscending()
	{
		removeNulls();
		int sortLength = inventory.size() - 1;
		boolean isSorted = false;
		AbstractItem temporary = null;

		while (!isSorted)
		{
			isSorted = true;
			for (int i = 0; i < sortLength; i++)
			{
				if (inventory.get(i).getQuantity() > inventory.get(i + 1).getQuantity())
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
				if (inventory.get(i).getQuantity() > inventory.get(i + 1).getQuantity())
				{
					temporary = inventory.get(i);
					inventory.set(i, inventory.get(i + 1));
					inventory.set(i + 1, temporary);
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
		removeNulls();
		int sortLength = inventory.size() - 1;
		boolean isSorted = false;
		AbstractItem temporary = null;

		while (!isSorted)
		{
			isSorted = true;
			for (int i = 0; i > sortLength; i++)
			{
				if (inventory.get(i).getQuantity() > inventory.get(i + 1).getQuantity())
				{
					isSorted = false;
					break;
				}
			}

			if (isSorted)
			{
				break;
			}

			for (int i = 0; i > sortLength; i++)
			{
				if (inventory.get(i).getQuantity() > inventory.get(i + 1).getQuantity())
				{
					temporary = inventory.get(i);
					inventory.set(i, inventory.get(i + 1));
					inventory.set(i + 1, temporary);
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
		removeNulls();
		int sortLength = inventory.size() - 1;
		boolean isSorted = false;
		AbstractItem temporary = null;

		while (!isSorted)
		{
			isSorted = true;
			for (int i = 0; i < sortLength; i++)
			{
				if (inventory.get(i).getName().compareToIgnoreCase(inventory.get(i + 1).getName()) > 0)
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
				if (inventory.get(i).getName().compareToIgnoreCase(inventory.get(i + 1).getName()) > 0)
				{
					temporary = inventory.get(i);
					inventory.set(i, inventory.get(i + 1));
					inventory.set(i + 1, temporary);
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
		removeNulls();
		int sortLength = inventory.size() - 1;
		boolean isSorted = false;
		AbstractItem temporary = null;

		while (!isSorted)
		{
			isSorted = true;
			for (int i = 0; i < sortLength; i++)
			{
				if (inventory.get(i).getName().compareToIgnoreCase(inventory.get(i + 1).getName()) < 0)
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
				if (inventory.get(i).getName().compareToIgnoreCase(inventory.get(i + 1).getName()) < 0)
				{
					temporary = inventory.get(i);
					inventory.set(i, inventory.get(i + 1));
					inventory.set(i + 1, temporary);
					temporary = null;
				}
			}
		}
	}

	/**
	 * Sorts the inventory by itemID from low to high
	 */
	public void sortByItemIDAscending()
	{
		removeNulls();
		int sortLength = inventory.size() - 1;
		boolean isSorted = false;
		AbstractItem temporary = null;

		while (!isSorted)
		{
			isSorted = true;
			for (int i = 0; i < sortLength; i++)
			{
				if (inventory.get(i).getItemID() > inventory.get(i + 1).getItemID())
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
				if (inventory.get(i).getItemID() > inventory.get(i + 1).getItemID())
				{
					temporary = inventory.get(i);
					inventory.set(i, inventory.get(i + 1));
					inventory.set(i + 1, temporary);
					temporary = null;
				}
			}
		}
	}

	/**
	 * Sorts the inventory by itemID from high to low
	 */
	public void sortByItemIDDescending()
	{
		removeNulls();
		int sortLength = inventory.size() - 1;
		boolean isSorted = false;
		AbstractItem temporary = null;
		
		while (!isSorted)
		{
			isSorted = true;
			for (int i = 0; i < sortLength; i++)
			{
				if (inventory.get(i).getItemID() < inventory.get(i + 1).getItemID())
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
				if (inventory.get(i).getItemID() < inventory.get(i + 1).getItemID())
				{
					temporary = inventory.get(i);
					inventory.set(i, inventory.get(i + 1));
					inventory.set(i + 1, temporary);
					temporary = null;
				}
			}
		}
	}

	public ArrayList<AbstractItem> getInventory()
	{
		removeNulls();
		return inventory;
	}

	public void setInventory(ArrayList<AbstractItem> inventory)
	{
		this.inventory = inventory;
		removeNulls();
	}
	
	private void removeNulls()
	{
		int length = inventory.size();
		for (int i = 0; i < length; i++)
		{
			if (inventory.get(i) == null)
			{
				inventory.remove(i);
				i -= 1;
				length -= 1;
			}
		}
	}

}
