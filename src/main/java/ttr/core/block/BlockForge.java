package ttr.core.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.enums.EnumTools;
import ttr.core.tile.machine.TEForge;

public class BlockForge extends BlockMachine
{
	public BlockForge()
	{
		super(Material.ROCK);
		setHardness(3.5F);
		setResistance(8.0F);
		setDefaultState(getDefaultState().withProperty(WORKING, false));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, WORKING);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}
	
	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return ((TEForge) world.getTileEntity(pos)).isBurning() ? 15 : 0;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TEForge();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if (stateIn.getValue(WORKING))
		{
			double d0 = pos.getX() + 0.5D;
			double d1 = pos.getY() + 1.0D;
			double d2 = pos.getZ() + 0.5D;
			double d3 = 0.52D;
			double d4 = rand.nextDouble() * 0.6 - 0.3;
			double d5 = rand.nextDouble() * 0.6 - 0.3;

			if (rand.nextDouble() < 0.1D)
			{
				worldIn.playSound(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			}

			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d5, 0.0D, 0.0D, 0.0D);
		}
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState();
	}
	
	@Override
	public String getHarvestTool(IBlockState state)
	{
		return EnumTools.pickaxe.name();
	}
}