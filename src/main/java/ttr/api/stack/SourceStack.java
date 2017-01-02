/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.api.stack;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;
import ttr.api.recipe.MaterialInstance;
import ttr.api.util.Util;

/**
 * Created at 2016年12月20日 上午9:15:52
 * @author ueyudiud
 */
public class SourceStack implements AbstractStack
{
	EnumMaterial material;
	int size;
	List<ItemStack>[] mateched;
	
	public SourceStack(EnumMaterial material, int size)
	{
		this.material = material;
		this.size = size;
		this.mateched = new List[]{
				OreDictionary.getOres(EnumOrePrefix.dust.getDictName(material)),
				OreDictionary.getOres(EnumOrePrefix.ingot.getDictName(material))};
	}
	
	@Override
	public boolean similar(ItemStack stack)
	{
		for(List<ItemStack> list : this.mateched)
		{
			if(OreDictionary.containsMatch(false, list, stack))
				return true;
		}
		return false;
	}
	
	@Override
	public boolean contain(ItemStack stack)
	{
		return similar(stack) && this.size <= stack.stackSize;
	}
	
	@Override
	public int size(ItemStack stack)
	{
		return this.size;
	}
	
	@Override
	public AbstractStack split(ItemStack stack)
	{
		return this.size <= stack.stackSize ? null : new SourceStack(this.material, this.size - stack.stackSize);
	}
	
	@Override
	public ItemStack instance()
	{
		ItemStack stack = MaterialInstance.findItemStack(EnumOrePrefix.dust, this.material);
		return stack == null ? Util.copyAmount(this.mateched[0].get(0), this.size == 0 ? 1 : this.size) : stack;
	}
	
	@Override
	public List<ItemStack> display()
	{
		List<ItemStack> list = new ArrayList();
		for(List<ItemStack> stacks : this.mateched)
		{
			list.addAll(Util.sizeOf(stacks, this.size));
		}
		return list;
	}
	
	@Override
	public boolean valid()
	{
		for(List<ItemStack> stacks : this.mateched)
		{
			if(!stacks.isEmpty())
				return true;
		}
		return false;
	}
	
	@Override
	public boolean useContainer()
	{
		return false;
	}
}