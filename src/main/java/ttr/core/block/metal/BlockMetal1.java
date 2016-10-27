package ttr.core.block.metal;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import ttr.api.data.M;
import ttr.api.material.Mat;

public class BlockMetal1 extends BlockMetalBase
{
	public static enum EnumType implements IStringSerializable
	{
		aluminium(M.aluminium),
		titanium(M.titanium),
		chromium(M.chromium),
		nickel(M.nickel),
		zinc(M.zinc),
		silver(M.silver),
		tungsten(M.tungsten),
		osmium(M.osmium),
		iridium(M.iridium),
		platinum(M.platinum);

		final Mat material;

		EnumType(Mat material)
		{
			this.material = material;
		}
		
		public String oreName()
		{
			return "block" + material.oreDictName;
		}

		public String localName()
		{
			return material.localName + " Block";
		}

		@Override
		public String getName()
		{
			return name();
		}
	}
	
	public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);

	@Override
	protected IProperty getMetalProperty()
	{
		return TYPE;
	}
	
	@Override
	public float getBlockHardness(IBlockState state, World worldIn, BlockPos pos)
	{
		return state.getValue(TYPE).material.blockHardness;
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion)
	{
		return world.getBlockState(pos).getValue(TYPE).material.blockExplosionResistance;
	}

	@Override
	public String getHarvestTool(IBlockState state)
	{
		return state.getValue(TYPE).material.blockHarvestTool;
	}
	
	@Override
	public int getHarvestLevel(IBlockState state)
	{
		return state.getValue(TYPE).material.blockHarvestLevel;
	}
}