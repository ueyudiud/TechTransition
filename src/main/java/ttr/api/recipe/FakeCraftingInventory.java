package ttr.api.recipe;

import java.util.HashMap;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ttr.api.stack.AbstractStack;
import ttr.api.util.Util;

public class FakeCraftingInventory extends InventoryCrafting
{
	public static FakeCraftingInventory createWithSingle(Object object)
	{
		return new FakeCraftingInventory(1, 1, object);
	}
	public static FakeCraftingInventory createShapeless(Object...recipe)
	{
		try
		{
			int xSize, ySize;
			switch (recipe.length)
			{
			case 1 : return createWithSingle(recipe[0]);
			case 2 :
				xSize = 2; ySize = 1;
			case 3 :
			case 4 :
				xSize = 2; ySize = 2;
				break;
			default :
				xSize = 3; ySize = 3;
				break;
			}
			Object[] array = new Object[xSize * ySize];
			System.arraycopy(recipe, 0, array, 0, recipe.length);
			return new FakeCraftingInventory(xSize, ySize, array);
		}
		catch(Exception exception)
		{
			String ret = "Invalid fake crafting inventory: ";
			for (Object tmp : recipe)
			{
				ret += tmp + ", ";
			}
			throw new RuntimeException(ret.substring(0, ret.length() - 2), exception);
		}
	}
	public static FakeCraftingInventory createShaped(Object...recipe)
	{
		try
		{
			String shape = "";
			int idx = 0;
			int xSize = 0;
			int ySize = 0;
			
			if (recipe[idx] instanceof String[])
			{
				String[] parts = ((String[])recipe[idx++]);
				
				for (String s : parts)
				{
					xSize = s.length();
					shape += s;
				}
				
				ySize = parts.length;
			}
			else
			{
				while (recipe[idx] instanceof String)
				{
					String s = (String)recipe[idx++];
					shape += s;
					xSize = s.length();
					ySize++;
				}
			}
			
			if (xSize * ySize != shape.length()) throw new RuntimeException();
			
			HashMap<Character, Object> itemMap = new HashMap();
			
			for (; idx < recipe.length; idx += 2)
			{
				itemMap.put((Character) recipe[idx], recipe[idx + 1]);
			}
			
			Object[] map = new Object[xSize * ySize];
			idx = 0;
			for (char chr : shape.toCharArray())
			{
				map[idx++] = itemMap.get(chr);
			}
			
			return new FakeCraftingInventory(xSize, ySize, map);
		}
		catch(Exception exception)
		{
			String ret = "Invalid fake crafting inventory: ";
			for (Object tmp : recipe)
			{
				ret += tmp + ", ";
			}
			throw new RuntimeException(ret.substring(0, ret.length() - 2), exception);
		}
	}
	private static ItemStack decode(Object in)
	{
		if (in instanceof ItemStack)
		{
			return ((ItemStack)in).copy();
		}
		else if (in instanceof Item)
		{
			return new ItemStack((Item)in);
		}
		else if (in instanceof Block)
		{
			return new ItemStack((Block)in);
		}
		else if (in instanceof String)
		{
			return Util.getFromOreDict((String) in);
		}
		else if(in instanceof AbstractStack)
		{
			if(((AbstractStack) in).valid())
			{
				return ((AbstractStack) in).instance();
			}
			return null;
		}
		else throw new RuntimeException();
	}
	
	private final int x;
	private final int y;
	private final Object[] stacks;
	
	FakeCraftingInventory(int x, int y, Object...stacks)
	{
		super(null, 0, 0);
		this.x = x;
		this.y = y;
		this.stacks = stacks;
	}
	
	public boolean isValid()
	{
		try
		{
			for(Object object : this.stacks)
			{
				if(object != null && decode(object) == null)
					return false;
			}
			return true;
		}
		catch (Exception exception)
		{
			return false;
		}
	}
	
	@Override
	public @Nullable ItemStack decrStackSize(int index, int count)
	{
		return null;
	}
	
	@Override
	public @Nullable ItemStack getStackInSlot(int index)
	{
		if(this.stacks[index] == null) return null;
		return decode(this.stacks[index]);
	}
	
	@Override
	public @Nullable ItemStack getStackInRowAndColumn(int row, int column)
	{
		return row >= 0 && row < this.x && column >= 0 && column <= this.y ?
				getStackInSlot(row + column * this.x) : null;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		;
	}
}