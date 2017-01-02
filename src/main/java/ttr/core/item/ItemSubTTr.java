package ttr.core.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ttr.api.item.IBehavior;
import ttr.api.item.ItemSub;
import ttr.load.TTrIBF;

public class ItemSubTTr extends ItemSub
{
	public ItemSubTTr()
	{
		super("ttr", "sub");
		TTrIBF.initSubItem(this);
	}
	
	public ItemStack get(String name)
	{
		return get(name, 1);
	}
	public ItemStack get(String name, int size)
	{
		if(!nameMap.containsKey(name))
			return null;
		return new ItemStack(this, size, nameMap.get(name));
	}
	
	@Override
	public void addSubItem(int meta, String name, String localized, IBehavior... behaviors)
	{
		super.addSubItem(meta, name, localized, behaviors);
	}

	public void addSubItem(int meta, String name, String localized, String oreDict, IBehavior... behaviors)
	{
		this.addSubItem(meta, name, localized, behaviors);
		OreDictionary.registerOre(oreDict, new ItemStack(this, 1, meta));
	}
}