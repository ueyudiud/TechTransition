/*
 * copyright© 2016-2017 ueyudiud
 */
package ttr.api.block;

import net.minecraft.item.ItemStack;

/**
 * @author ueyudiud
 */
public interface IBlockName
{
	String getBlockDisplayName(ItemStack stack);
}