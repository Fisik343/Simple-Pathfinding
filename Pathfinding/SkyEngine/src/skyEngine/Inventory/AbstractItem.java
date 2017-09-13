package skyEngine.Inventory;

public abstract class AbstractItem
{
	protected int quantity = 0;
	protected int maxQuantity = Integer.MAX_VALUE;
	protected String name = null;
	protected int itemID = -1;

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	/**
	 * Adds an amount to the current quantity of the item
	 * 
	 * @param amount
	 *            The amount to add
	 */
	public void addToQuantity(int amount)
	{
		quantity += amount;
		if (quantity > maxQuantity)
		{
			quantity = maxQuantity;
		}
	}

	/**
	 * Subtracts an amount from the current quantity of the item
	 * 
	 * @param amount
	 *            The amount to subtract
	 */
	public void subtractFromQuantity(int amount)
	{
		quantity -= amount;
		if (quantity < 0)
		{
			quantity = 0;
		}
	}

	public int getMaxQuantity()
	{
		return maxQuantity;
	}

	public void setMaxQuantity(int maxQuantity)
	{
		this.maxQuantity = maxQuantity;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getItemID()
	{
		return itemID;
	}

	public void setItemID(int itemID)
	{
		this.itemID = itemID;
	}

}
