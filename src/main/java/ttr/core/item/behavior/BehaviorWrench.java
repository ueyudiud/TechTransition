package ttr.core.item.behavior;

import java.util.List;

import ic2.api.tile.IWrenchable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ttr.api.item.BehaviorBase;
import ttr.api.util.Util;
import ttr.core.tile.TEBase;

public class BehaviorWrench extends BehaviorBase
{
	@Override
	public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos,
			EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		if(world.isRemote)
			return EnumActionResult.PASS;
		IBlockState state = world.getBlockState(pos);
		state = state.getActualState(world, pos);
		Block block = state.getBlock();
		EnumFacing side1 = Util.fixSide(side, hitX, hitY, hitZ);
		TileEntity tile = world.getTileEntity(pos);
		boolean flag = false;
		try
		{
			if(tile instanceof TEBase)
			{
				flag = true;
				if(player.isSneaking() || side == side1)
				{
					if(((TEBase) tile).setRotation(side1))
					{
						applyDamage(stack, 1, player);
						return EnumActionResult.SUCCESS;
					}
				}
				else if(((TEBase) tile).setRotation(side, side1))
				{
					applyDamage(stack, 2, player);
					return EnumActionResult.SUCCESS;
				}
			}
			if(block instanceof IWrenchable)
			{
				flag = true;
				IWrenchable wrenchable = (IWrenchable) block;
				if(wrenchable.getFacing(world, pos) != side)
				{
					if(wrenchable.setFacing(world, pos, side, player))
					{
						applyDamage(stack, 1, player);
						return EnumActionResult.SUCCESS;
					}
				}
				if(wrenchable.wrenchCanRemove(world, pos, player))
				{
					List<ItemStack> drops = wrenchable.getWrenchDrops(world, pos, state, tile, player, 0);
					world.setBlockToAir(pos);
					Util.spawnDropsInWorld(world, pos, drops);
					applyDamage(stack, 4, player);
					return EnumActionResult.SUCCESS;
				}
			}
			if(block.rotateBlock(world, pos, side))
			{
				applyDamage(stack, 1, player);
				return EnumActionResult.SUCCESS;
			}
			if(flag)
				return EnumActionResult.FAIL;
		}
		catch(Exception exception)
		{
			;
		}
		return super.onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ, hand);
	}
}