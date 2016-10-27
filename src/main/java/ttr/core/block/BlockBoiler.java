package ttr.core.block;

import java.util.List;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.core.tile.boiler.TEBoiler;
import ttr.core.tile.boiler.TECoalBoiler;

public class BlockBoiler extends BlockMachine
{
	public static enum BoilerType implements IStringSerializable
	{
		SIMPLE_BRONZE,
		SIMPLE_INVAR,
		SIMPLE_STEEL;

		@Override
		public String getName()
		{
			return name().toLowerCase();
		}
	}

	public static final PropertyEnum<BoilerType> BOILER_TYPE = PropertyEnum.create("type", BoilerType.class);
	
	public BlockBoiler()
	{
		setHardness(4.0F);
		setResistance(9.0F);
		setDefaultState(getDefaultState().withProperty(FACING_PRIMARYII, EnumFacing.NORTH).withProperty(WORKING, false).withProperty(BOILER_TYPE, BoilerType.SIMPLE_BRONZE));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BOILER_TYPE, FACING_PRIMARYII, WORKING);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(BOILER_TYPE).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(BOILER_TYPE, BoilerType.values()[meta]);
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		TileEntity tile = worldIn.getTileEntity(pos);
		EnumFacing facing;
		if(tile instanceof TEBoiler)
		{
			if((facing = ((TEBoiler) tile).getRotation()) != null)
			{
				state = state.withProperty(FACING_PRIMARYII, facing);
			}
			if(tile instanceof TECoalBoiler && ((TECoalBoiler) tile).isActived())
			{
				state = state.withProperty(WORKING, true);
			}
		}
		return state;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		list.add(new ItemStack(itemIn, 1, 0));
		list.add(new ItemStack(itemIn, 1, 1));
		list.add(new ItemStack(itemIn, 1, 2));
	}
	
	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.getValue(WORKING) ? 13 : 0;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		switch (state.getValue(BOILER_TYPE))
		{
		case SIMPLE_BRONZE : return new TECoalBoiler.TEBronzeCoalBoiler();
		case SIMPLE_INVAR  : return new TECoalBoiler.TEInvarCoalBoiler();
		case SIMPLE_STEEL  : return new TECoalBoiler.TESteelCoalBoiler();
		default : return null;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return createTileEntity(worldIn, getStateFromMeta(meta));
	}
}