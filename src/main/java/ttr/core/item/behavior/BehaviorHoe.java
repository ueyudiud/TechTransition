package ttr.core.item.behavior;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
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
import net.minecraftforge.event.ForgeEventFactory;
import ttr.api.item.BehaviorBase;

public class BehaviorHoe extends BehaviorBase
{
	private static final IBlockState FARMLAND = Blocks.FARMLAND.getDefaultState();
	private static final IBlockState DIRT = Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT);
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (!player.canPlayerEdit(pos.offset(facing), facing, stack))
			return EnumActionResult.FAIL;
		else
		{
			int hook = ForgeEventFactory.onHoeUse(stack, player, world, pos);
			if (hook != 0) return hook > 0 ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;

			IBlockState iblockstate = world.getBlockState(pos);
			Block block = iblockstate.getBlock();

			if (facing != EnumFacing.DOWN && world.isAirBlock(pos.up()))
			{
				if (block == Blocks.GRASS || block == Blocks.GRASS_PATH)
				{
					setBlock(stack, player, world, pos, FARMLAND);
					return EnumActionResult.SUCCESS;
				}
				if (block == Blocks.DIRT)
				{
					switch (iblockstate.getValue(BlockDirt.VARIANT))
					{
					case DIRT:
						setBlock(stack, player, world, pos, FARMLAND);
						return EnumActionResult.SUCCESS;
					case COARSE_DIRT:
						setBlock(stack, player, world, pos, DIRT);
						return EnumActionResult.SUCCESS;
					default :;
					}
				}
			}
			return EnumActionResult.PASS;
		}
	}

	protected void setBlock(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, IBlockState state)
	{
		worldIn.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

		if (!worldIn.isRemote)
		{
			worldIn.setBlockState(pos, state, 11);
			applyDamage(stack, 1, player);
		}
	}
}