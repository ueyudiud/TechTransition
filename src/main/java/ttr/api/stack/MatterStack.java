package ttr.api.stack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ttr.api.material.Mat;
import ttr.api.material.MatCondition;
import ttr.api.util.IDataChecker;

public class MatterStack implements AbstractStack
{
	public Mat material;
	public long size;
	private IDataChecker<? super MatCondition> filter;
	
	private Map<MatCondition, List<ItemStack>> stacks = new HashMap();
	private List<ItemStack> display;
	
	public MatterStack(Mat material, long size)
	{
		this(material, size, null);
	}
	public MatterStack(Mat material, long size, IDataChecker<? super MatCondition> checker)
	{
		this.material = material;
		this.size = size;
		filter = checker;
		for(MatCondition condition : MatCondition.register)
		{
			if((checker == null || checker.isTrue(condition)) && condition.isBelongTo(material))
			{
				stacks.put(condition, OreDictionary.getOres(condition.orePrefix + material.oreDictName));
			}
		}
	}
	
	private boolean match(List<ItemStack> list, ItemStack input)
	{
		for(ItemStack stack : list)
		{
			if(OreDictionary.itemMatches(stack, input, false))
				return true;
		}
		return false;
	}
	
	@Override
	public boolean contain(ItemStack stack)
	{
		int size = size(stack);
		return size == -1 ? false :
			stack.stackSize >= size;
	}

	@Override
	public boolean similar(ItemStack stack)
	{
		for(List<ItemStack> stacks : this.stacks.values())
		{
			if(match(stacks, stack)) return true;
		}
		return false;
	}
	
	@Override
	public int size(ItemStack stack)
	{
		if(stack == null) return -1;
		for(Entry<MatCondition, List<ItemStack>> entry : stacks.entrySet())
		{
			if(match(entry.getValue(), stack))
				return (int) ((size + entry.getKey().size - 1L) / entry.getKey().size);
		}
		return -1;
	}

	private ItemStack instance;

	@Override
	public ItemStack instance()
	{
		if(instance == null)
		{
			for(Entry<MatCondition, List<ItemStack>> entry : stacks.entrySet())
			{
				if(!entry.getValue().isEmpty())
				{
					instance = entry.getValue().get(0).copy();
					instance.stackSize = (int) ((size + entry.getKey().size - 1L) / entry.getKey().size);
					return instance;
				}
			}
		}
		return instance;
	}
	
	@Override
	public List<ItemStack> display()
	{
		if(display == null)
		{
			ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
			for(Entry<MatCondition, List<ItemStack>> entry : stacks.entrySet())
			{
				int size = (int) ((this.size + entry.getKey().size - 1L) / entry.getKey().size);;
				for(ItemStack stack : entry.getValue())
				{
					ItemStack stack2 = stack.copy();
					stack2.stackSize = size;
					builder.add(stack2);
				}
			}
			display = builder.build();
		}
		return display;
	}
	
	@Override
	public MatterStack split(ItemStack stack)
	{
		for(Entry<MatCondition, List<ItemStack>> entry : stacks.entrySet())
		{
			if(match(entry.getValue(), stack))
			{
				long size = this.size - stack.stackSize * entry.getKey().size;
				return size <= 0 ? null : new MatterStack(material, size, filter);
			}
		}
		return this;
	}
	
	@Override
	public boolean useContainer()
	{
		return false;
	}

	@Override
	public boolean valid()
	{
		return instance() != null;
	}
}