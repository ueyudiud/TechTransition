package ttr.api.stack;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import ttr.api.util.Util;

public class BaseStack implements AbstractStack
{
	public static final BaseStack EMPTY = new BaseStack((ItemStack) null);
	
	public static BaseStack sizeOf(BaseStack stack, int size)
	{
		return size <= 0 ? null : new BaseStack(stack.stack, size, stack.useContainer);
	}
	
	private ImmutableList<ItemStack> list;
	public ItemStack stack;
	private boolean useContainer;
	
	public BaseStack(String modid, String name, int size, int meta)
	{
		this(modid, name, size, meta, false);
	}
	public BaseStack(String modid, String name, int size, int meta, boolean useContainer)
	{
		Item item = GameRegistry.findItem(modid, name);
		if(item != null)
		{
			this.stack = new ItemStack(item, size, meta);
		}
		useContainer = false;
	}
	public BaseStack(Block block)
	{
		this(block, 1);
	}
	public BaseStack(Block block, int size)
	{
		this(block, size, OreDictionary.WILDCARD_VALUE);
	}
	public BaseStack(Block block, int size, int meta)
	{
		if(block != null)
		{
			this.stack = new ItemStack(block, size, meta);
		}
		this.useContainer = false;
	}
	public BaseStack(Item item)
	{
		this(item, 1);
	}
	public BaseStack(Item item, int size)
	{
		this(item, size, OreDictionary.WILDCARD_VALUE);
	}
	public BaseStack(Item item, int size, int meta)
	{
		if(item != null)
		{
			this.stack = new ItemStack(item, size, meta);
		}
		this.useContainer = false;
	}
	public BaseStack(ItemStack stack)
	{
		this(stack, false);
	}
	public BaseStack(ItemStack stack, int size)
	{
		if(stack != null)
		{
			this.stack = Util.copyAmount(stack, size);
		}
	}
	public BaseStack(ItemStack stack, int size, int meta)
	{
		if(stack != null)
		{
			this.stack = Util.copyAmount(stack, size);
			this.stack.setItemDamage(meta);
		}
	}
	public BaseStack(ItemStack stack, boolean useContainer)
	{
		this.stack = ItemStack.copyItemStack(stack);
		this.useContainer = useContainer;
	}
	public BaseStack(ItemStack stack, int size, boolean useContainer)
	{
		if(stack != null)
		{
			this.stack = stack.copy();
			this.stack.stackSize = size;
		}
		this.useContainer = useContainer;
	}
	
	@Override
	public boolean similar(ItemStack stack)
	{
		return this.stack == null ? stack == null :
			OreDictionary.itemMatches(this.stack, stack, false);
	}
	
	@Override
	public boolean contain(ItemStack stack)
	{
		return similar(stack) &&
				(this.stack == null || this.stack.stackSize <= stack.stackSize);
	}
	
	@Override
	public int size(ItemStack stack)
	{
		return this.stack == null ? 0 :
			this.stack.stackSize;
	}
	
	@Override
	public AbstractStack split(ItemStack stack)
	{
		return sizeOf(this, this.stack.stackSize - stack.stackSize);
	}
	
	@Override
	public ItemStack instance()
	{
		if(this.stack != null)
		{
			ItemStack ret = this.stack.copy();
			if(ret.getItemDamage() == OreDictionary.WILDCARD_VALUE)
			{
				ret.setItemDamage(0);
			}
			return ret;
		}
		return null;
	}
	
	@Override
	public List<ItemStack> display()
	{
		if(this.list == null)
			if(this.stack != null)
			{
				this.list = ImmutableList.of(this.stack.copy());
			}
			else
			{
				this.list = ImmutableList.of();
			}
		return this.list;
	}
	
	@Override
	public boolean valid()
	{
		return this.stack != null;
	}
	
	@Override
	public boolean useContainer()
	{
		return this.useContainer;
	}
	
	@Override
	public String toString()
	{
		return "[" + this.stack.getUnlocalizedName() + "]" + "x" + this.stack.stackSize;
	}
	
	@Override
	public int hashCode()
	{
		return this.stack == null ? 31 :
			this.stack.getItem().hashCode() * 31 + this.stack.getItemDamage();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this.stack == null) return obj == EMPTY;
		if(obj == this)
			return true;
		else if(!(obj instanceof BaseStack))
			return false;
		BaseStack stack1 = (BaseStack) obj;
		return ItemStack.areItemStacksEqual(this.stack, stack1.stack) && this.useContainer == stack1.useContainer;
	}
}