package ttr.core.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMachineCasing extends Block
{
	public static enum EnumType implements IStringSerializable
	{
		BRASS(2.0F, 5.0F),
		BRONZE(3.0F, 5.0F),
		IRON(3.5F, 6.0F),
		ALUMINIUM(3.0F, 7.0F),
		STEEL(5.0F, 8.0F),
		STAINLESS_STEEL(5.0F, 9.0F),
		TITANIUM(6.0F, 10.0F),
		TUNGSTEN_STEEL(8.0F, 12.0F),
		ADVANCED(12.0F, 15.0F),
		HIGHLY_ADVANCED(16.0F, 20.0F),
		BRONZE_BRICK(3.5F, 5.5F),
		STEEL_BRICK(5.0F, 9.0F);

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
	
	public static final PropertyEnum<EnumType> CASING_TYPE = PropertyEnum.create("type", EnumType.class);
	
	public BlockMachineCasing()
	{
		super(Material.IRON);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, CASING_TYPE);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(CASING_TYPE).ordinal();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(CASING_TYPE, EnumType.values()[meta]);
	}

	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return blockState.getValue(CASING_TYPE).hardness;
	}
	
	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion)
	{
		return world.getBlockState(pos).getValue(CASING_TYPE).resistance;
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
	public int damageDropped(IBlockState state)
	{
		return getMetaFromState(state);
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type)
	{
		return false;
	}
}