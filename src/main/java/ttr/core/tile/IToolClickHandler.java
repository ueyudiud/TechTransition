package ttr.core.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IToolClickHandler
{
	ItemStack onToolClick(ItemStack tStack, EntityPlayer player, int index);
}
