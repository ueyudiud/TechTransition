package ttr.core.item.behavior;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ttr.api.item.BehaviorBase;

public class BehaviorShovel extends BehaviorBase
{
	private static final IBlockState GRASS_PATH = Blocks.GRASS_PATH.getDefaultState();
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (!player.canPlayerEdit(pos.offset(facing), facing, stack))
			return EnumActionResult.FAIL;
		else
		{
			IBlockState iblockstate = world.getBlockState(pos);
			Block block = iblockstate.getBlock();
			if (facing != EnumFacing.DOWN && world.getBlockState(pos.up()).getMaterial() == Material.AIR && block == Blocks.GRASS)
			{
				world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
				if (!world.isRemote)
				{
					world.setBlockState(pos, GRASS_PATH, 11);
					applyDamage(stack, 1, player);
				}
				return EnumActionResult.SUCCESS;
			}
			else
				return EnumActionResult.PASS;
		}
	}
}
