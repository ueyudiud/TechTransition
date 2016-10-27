package ttr.core.block.metal;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockMetalBase<T extends Enum> extends Block
{
	private Object[] values;

	public BlockMetalBase()
	{
		super(Material.IRON);
		values = getMetalProperty().getValueClass().getEnumConstants();
	}
	
	protected abstract IProperty getMetalProperty();

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, getMetalProperty());
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(getMetalProperty(), (T) values[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((Enum) state.getValue(getMetalProperty())).ordinal();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		for(int i = 0; i < values.length; ++i)
		{
			list.add(new ItemStack(itemIn, 1, i));
		}
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type)
	{
		return false;
	}
	
	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon)
	{
		return true;
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return getMetaFromState(state);
	}
}