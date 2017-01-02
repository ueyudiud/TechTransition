package ttr.api.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import ttr.api.enums.EnumTools;

public interface IToolableTile
{
	ActionResult<Float> DEFAULT_RESULT = new ActionResult<Float>(EnumActionResult.PASS, 0.0F);
	
	ActionResult<Float> onToolClick(EntityPlayer player, EnumTools tool, ItemStack stack,
			EnumFacing side, float hitX, float hitY, float hitZ);
}