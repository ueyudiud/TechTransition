package ttr.core.block.metal;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import ttr.api.enums.EnumMaterial;

public class BlockMetal1 extends BlockMetalBase
{
	public static enum EnumType implements IStringSerializable
	{
		aluminium(EnumMaterial.Aluminium),
		titanium(EnumMaterial.Titanium),
		chromium(EnumMaterial.Chromium),
		nickel(EnumMaterial.Nickel),
		zinc(EnumMaterial.Zinc),
		silver(EnumMaterial.Silver),
		tungsten(EnumMaterial.Tungsten),
		osmium(EnumMaterial.Osmium),
		iridium(EnumMaterial.Iridium),
		platinum(EnumMaterial.Platinum);
		
		final EnumMaterial material;
		
		EnumType(EnumMaterial material)
		{
			this.material = material;
		}
		
		public String oreName()
		{
			return "block" + this.material.oreDictName;
		}
		
		public String localName()
		{
			return this.material.localName + " Block";
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