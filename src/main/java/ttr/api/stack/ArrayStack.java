package ttr.api.stack;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ttr.api.util.Util;

public class ArrayStack implements AbstractStack
{
	public static ArrayStack sizeOf(ArrayStack stack, int size)
	{
		return new ArrayStack(size, stack.array);
	}
	
	public int size;
	public final List<ItemStack> array;
	
	public ArrayStack(int size, Collection<ItemStack> collection)
	{
		this.array = ImmutableList.copyOf(collection);
		this.size = size;
	}
	public ArrayStack(Collection<ItemStack> collection)
	{
		this(1, collection);
	}
	public ArrayStack(int size, ItemStack...stacks)
	{
		this.array = ImmutableList.copyOf(stacks);
		this.size = size;
	}
	public ArrayStack(ItemStack...stacks)
	{
		this(1, stacks);
	}
	
	@Override
	public boolean similar(ItemStack stack)
	{
		if(stack == null) return false;
		for(ItemStack target : this.array)
		{
			if(OreDictionary.itemMatches(target, stack, false))
				return true;
		}
		return false;
	}
	
	@Override
	public boolean contain(ItemStack stack)
	{
		return similar(stack) && stack.stackSize >= this.size;
	}
	
	@Override
	public int size(ItemStack stack)
	{
		return this.size;
	}
	
	@Override
	public AbstractStack split(ItemStack stack)
	{
		return this.size >= stack.stackSize ?
				new ArrayStack(this.size - stack.stackSize, this.array) :
					null;
	}
	
	@Override
	public ItemStack instance()
	{
		if(this.array.isEmpty()) return new ItemStack(Blocks.FIRE);
		ItemStack stack = this.array.get(0);
		stack = stack.copy();
		stack.stackSize = this.size == 0 ? this.size : this.size;
		return stack;
	}
	
	@Override
	public List<ItemStack> display()
	{
		
		return Util.sizeOf(this.array, this.size);
	}
	
	@Override
	public boolean valid()
	{
		return !this.array.isEmpty();
	}
	
	@Override
	public boolean useContainer()
	{
		return false;
	}
}