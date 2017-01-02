/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.block.machine;

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
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.core.block.BlockMachine;
import ttr.core.tile.generator.TESmallSteamTurbine;

/**
 * Created at 2016年12月28日 上午12:36:03
 * @author ueyudiud
 */
public class BlockTurbine extends BlockMachine
{
	public static enum EnumType implements IStringSerializable
	{
		STEAM_TURBINE;
		
		@Override
		public String getName()
		{
			return name().toLowerCase();
		}
	}
	
	public static final PropertyEnum<EnumType> MACHINE_TYPE = PropertyEnum.create("type", EnumType.class);
	
	public BlockTurbine()
	{
		setHardness(6.0F);
		setResistance(12.0F);
		setDefaultState(getDefaultState().withProperty(FACING_PRIMARYI, EnumFacing.NORTH).withProperty(WORKING, false));
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING_PRIMARYI, WORKING, MACHINE_TYPE);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(MACHINE_TYPE).ordinal();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(MACHINE_TYPE, EnumType.values()[meta]);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		list.add(new ItemStack(this, 1, 0));
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		switch (state.getValue(MACHINE_TYPE))
		{
		case STEAM_TURBINE : return new TESmallSteamTurbine();
		default : return null;
		}
	}
}