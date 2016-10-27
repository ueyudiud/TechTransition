package ttr.core.block;

import java.util.List;

import com.google.common.collect.ObjectArrays;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.data.EnumToolType;

public class BlockBrick extends Block
{
	public static enum EnumType implements IStringSerializable
	{
		BRONZE  (4.0F, 7.0F),
		BASIC   (4.5F, 8.0F),
		EXTENDED(5.0F, 9.0F),
		ADVANCED(5.5F, 10.0F);
		
		float hardness;
		float resistance;
		
		EnumType(float hardness, float resistance)
		{
			this.hardness = hardness;
			this.resistance = resistance;
		}
		
		@Override
		public String getName()
		{
			return name().toLowerCase();
		}
	}

	public static final PropertyEnum<EnumType> BRICK_TYPE = PropertyEnum.create("type", EnumType.class);
	
	public static final IProperty<Boolean>[] FACING;
	
	static
	{
		FACING = new IProperty[EnumFacing.VALUES.length];
		for(EnumFacing facing : EnumFacing.VALUES)
		{
			FACING[facing.ordinal()] = PropertyBool.create(facing.getName());
		}
	}

	public BlockBrick()
	{
		super(Material.IRON);
		setDefaultState(
				getDefaultState()
				.withProperty(FACING[0], false)
				.withProperty(FACING[1], false)
				.withProperty(FACING[2], false)
				.withProperty(FACING[3], false)
				.withProperty(FACING[4], false)
				.withProperty(FACING[5], false));
		BRONZE = getDefaultState().withProperty(BRICK_TYPE, EnumType.BRONZE);
		BASIC = getDefaultState().withProperty(BRICK_TYPE, EnumType.BASIC);
		EXTENDED = getDefaultState().withProperty(BRICK_TYPE, EnumType.EXTENDED);
		ADVANCED = getDefaultState().withProperty(BRICK_TYPE, EnumType.ADVANCED);
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, ObjectArrays.<IProperty>concat(BRICK_TYPE, FACING));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(BRICK_TYPE).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(BRICK_TYPE, EnumType.values()[meta]);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		for(EnumFacing facing : EnumFacing.VALUES)
		{
			if(worldIn.getBlockState(pos.offset(facing)) == state)
			{
				state = state.withProperty(FACING[facing.ordinal()], true);
			}
		}
		return state;
	}
	
	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return blockState.getValue(BRICK_TYPE).hardness;
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion)
	{
		return world.getBlockState(pos).getValue(BRICK_TYPE).resistance;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		for(EnumType type : EnumType.values())
		{
			list.add(new ItemStack(itemIn, 1, type.ordinal()));
		}
	}

	@Override
	public String getHarvestTool(IBlockState state)
	{
		return EnumToolType.wrench.name();
	}
	
	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type)
	{
		return false;
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return getMetaFromState(state);
	}
	
	public static IBlockState BRONZE;
	public static IBlockState BASIC;
	public static IBlockState EXTENDED;
	public static IBlockState ADVANCED;
}