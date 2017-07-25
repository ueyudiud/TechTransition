/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.api.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ttr.api.block.IBlockName;

/**
 * Created at 2016年12月20日 下午10:53:08
 * @author ueyudiud
 */
public class ItemBlockBase extends ItemBlock
{
	public ItemBlockBase(Block block, boolean register, String name)
	{
		super(block);
		if(register)
		{
			registerItemBlock(block, this);
		}
		else
		{
			setRegistryName(name);
			GameRegistry.register(this);
		}
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return this.block instanceof IBlockName ? ((IBlockName) this.block).getBlockDisplayName(stack) :
			super.getItemStackDisplayName(stack);
	}
	
	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}
}