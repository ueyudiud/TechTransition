package ttr.api.stack;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ttr.api.util.Util;

public class OreStack implements AbstractStack
{
	public static AbstractStack sizeOf(OreStack stack, int size)
	{
		return size <= 0 ? null : new OreStack(stack.oreName, size, stack.useContainer);
	}
	
	private ImmutableList<ItemStack> list;
	public String oreName;
	private List<ItemStack> ore;
	public int size;
	private boolean useContainer;
	
	public OreStack(String ore)
	{
		this(ore, 1);
	}
	public OreStack(String ore, int size)
	{
		this(ore, size, false);
	}
	public OreStack(String ore, int size, boolean useContainer)
	{
		this.oreName = ore;
		this.ore = OreDictionary.getOres(ore);
		this.size = size;
		this.useContainer = useContainer;
	}
	
	@Override
	public boolean similar(ItemStack stack)
	{
		for(ItemStack target : this.ore)
		{
			if(OreDictionary.itemMatches(target, stack, false))
				return true;
		}
		return false;
	}
	
	@Override
	public boolean contain(ItemStack stack)
	{
		return similar(stack) ? stack.stackSize >= this.size : false;
	}
	
	@Override
	public int size(ItemStack stack)
	{
		return this.size;
	}
	
	@Override
	public AbstractStack split(ItemStack stack)
	{
		return sizeOf(this, this.size - stack.stackSize);
	}
	
	@Override
	public ItemStack instance()
	{
		if(!display().isEmpty())
			return this.list.get(0);
		return null;
	}
	
	@Override
	public List<ItemStack> display()
	{
		if(this.list == null)
		{
			this.list = Util.sizeOf(this.ore, this.size);
		}
		return this.list;
	}
	
	@Override
	public boolean valid()
	{
		return !this.ore.isEmpty();
	}
	
	@Override
	public boolean useContainer()
	{
		return this.useContainer;
	}
}