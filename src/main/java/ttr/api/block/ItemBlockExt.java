package ttr.api.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import ttr.api.util.LanguageManager;

public class ItemBlockExt extends ItemBlock
{
	public ItemBlockExt(Block block)
	{
		super(block);
		hasSubtypes = true;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return LanguageManager.translateToLocal(getUnlocalizedName(stack) + ".name");
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return getUnlocalizedName() + "@" + getDamage(stack);
	}
	
	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}
}