/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.block.machine;

import java.util.List;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.enums.EnumMaterial;
import ttr.core.block.BlockMachine;
import ttr.core.tile.machine.TETank;
import ttr.core.tile.machine.TETankBottom;
import ttr.core.tile.machine.TETankTop;

/**
 * Created at 2016年12月22日 下午8:53:36
 * @author ueyudiud
 */
public class BlockTank extends BlockMachine
{
	public static enum EnumType implements IStringSerializable
	{
		BOTTOM, SIDE, TOP;
		
		public String getName()
		{
			return name().toLowerCase();
		}
	}
	
	public static final PropertyEnum<EnumType> SIDE = PropertyEnum.create("type", EnumType.class);
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, SIDE);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(SIDE).ordinal();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(SIDE, EnumType.values()[meta]);
	}
	
	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos)
	{
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile instanceof TETank)
		{
			return ((TETank) tile).material.blockHardness;
		}
		return super.getBlockHardness(blockState, worldIn, pos);
	}
	
	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion)
	{
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TETank)
		{
			return ((TETank) tile).material.blockExplosionResistance;
		}
		return super.getExplosionResistance(world, pos, exploder, explosion);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		for(EnumMaterial material : EnumMaterial.getMaterials())
		{
			if(material != null && material.tankCapacity > 0)
			{
				for(EnumType type : EnumType.values())
				{
					ItemStack stack = new ItemStack(itemIn, 1, type.ordinal());
					stack.setTagCompound(new NBTTagCompound());
					stack.getTagCompound().setShort("material", material.id);
					list.add(stack);
				}
			}
		}
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		switch (state.getValue(SIDE))
		{
		case BOTTOM : return new TETankBottom();
		case SIDE : return new TETank();
		case TOP : return new TETankTop();
		default:
			break;
		}
		return super.createTileEntity(world, state);
	}
	
	@Override
	public boolean isNormalCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		if(side.getHorizontalIndex() == -1)
		{
			IBlockState state1 = world.getBlockState(pos.offset(side));
			if(state1.getBlock() == this)
			{
				switch(state.getValue(SIDE))
				{
				case BOTTOM : if(side == EnumFacing.UP) return false;
				case TOP : if(side == EnumFacing.DOWN) return false;
				case SIDE : return false;
				}
			}
		}
		return super.shouldSideBeRendered(state, world, pos, side);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
}