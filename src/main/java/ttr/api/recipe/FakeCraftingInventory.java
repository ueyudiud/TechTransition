package ttr.api.recipe;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ttr.api.util.Log;

public class FakeCraftingInventory extends InventoryCrafting
{
	private static final FakeCraftingInventory INVALID =
			new FakeCraftingInventory(0, 0, new ItemStack[0]);

	static
	{
		INVALID.valid = false;
	}

	private boolean valid = true;

	public boolean isValid()
	{
		return valid;
	}

	private final ItemStack[] itemstacks;
	private int xSize;
	private int ySize;

	public static FakeCraftingInventory init(Object...recipe)
	{
		String shape = "";
		int idx = 0;
		int xSize = 0;
		int ySize = 0;
		ItemStack[] itemstacks;
		
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
		
		if (xSize * ySize != shape.length())
		{
			String ret = "Invalid shaped fake inventory: ";
			for (Object tmp :  recipe)
			{
				ret += tmp + ", ";
			}
			throw new RuntimeException(ret);
		}
		
		HashMap<Character, ItemStack> itemMap = new HashMap();
		
		for (; idx < recipe.length; idx += 2)
		{
			Character chr = (Character)recipe[idx];
			Object in = recipe[idx + 1];
			
			if (in instanceof ItemStack)
			{
				itemMap.put(chr, ((ItemStack)in).copy());
			}
			else if (in instanceof Item)
			{
				itemMap.put(chr, new ItemStack((Item)in));
			}
			else if (in instanceof Block)
			{
				itemMap.put(chr, new ItemStack((Block)in));
			}
			else if (in instanceof String)
			{
				if(!OreDictionary.getOres((String)in).isEmpty())
				{
					ItemStack stack = OreDictionary.getOres((String)in).get(0);
					if(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
					{
						stack.setItemDamage(0);
					}
					itemMap.put(chr, stack);
				}
				else
					return INVALID;
			}
			else
			{
				String ret = "Invalid fake crafting inventory: ";
				for (Object tmp :  recipe)
				{
					ret += tmp + ", ";
				}
				Log.warn("Fail to make inventory.", new RuntimeException(ret));
				return INVALID;
			}
		}
		
		itemstacks = new ItemStack[xSize * ySize];
		int x = 0;
		for (char chr : shape.toCharArray())
		{
			itemstacks[x++] = itemMap.get(chr);
		}
		
		return new FakeCraftingInventory(xSize, ySize, itemstacks);
	}
	private FakeCraftingInventory(int xSize, int ySize, ItemStack...stacks)
	{
		super(null, xSize, ySize);
		itemstacks = stacks;
		this.xSize = xSize;
		this.ySize = ySize;
	}
	
	@Override
	public ItemStack getStackInRowAndColumn(int x, int y)
	{
		if (x >= 0 && x < xSize && y >= 0 && y < ySize)
		{
			int k = x + y * xSize;
			return getStackInSlot(k);
		}
		else
			return null;
	}
	
	@Override
	public int getSizeInventory() {return itemstacks.length;}
	
	@Override
	public ItemStack getStackInSlot(int i) {return i >= itemstacks.length || i < 0 ? null : itemstacks[i];}
	
	@Override
	public ItemStack decrStackSize(int i, int size)
	{
		if(itemstacks[i] == null) return null;
		ItemStack ret = itemstacks[i].copy();
		itemstacks[i].stackSize -= size;
		if(itemstacks[i].stackSize < 1)
		{
			itemstacks[i] = null;
		}
		ret.stackSize = Math.min(size, ret.stackSize);
		return ret;
	}
	
	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) { itemstacks[i] = ItemStack.copyItemStack(itemstack); }
}