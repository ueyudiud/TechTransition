/*
 * copyright© 2016-2017 ueyudiud
 */
package ttr.core.util;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author ueyudiud
 */
public abstract class BlockStateWrapper implements  IBlockState
{
	protected final IBlockState parent;
	
	protected BlockStateWrapper(IBlockState parent)
	{
		this.parent = parent;
	}
	
	protected abstract BlockStateWrapper wrap(IBlockState state);
	
	@Override
	public boolean onBlockEventReceived(World worldIn, BlockPos pos, int id, int param)
	{
		return this.parent.onBlockEventReceived(worldIn, pos, id, param);
	}
	
	@Override
	public void neighborChanged(World worldIn, BlockPos pos, Block blockIn)
	{
		this.parent.neighborChanged(worldIn, pos, blockIn);
	}
	
	@Override
	public Material getMaterial()
	{
		return this.parent.getMaterial();
	}
	
	@Override
	public boolean isFullBlock()
	{
		return this.parent.isFullBlock();
	}
	
	@Override
	public boolean canEntitySpawn(Entity entityIn)
	{
		return this.parent.canEntitySpawn(entityIn);
	}
	
	@Override
	public int getLightOpacity()
	{
		return this.parent.getLightOpacity();
	}
	
	@Override
	public int getLightOpacity(IBlockAccess world, BlockPos pos)
	{
		return this.parent.getLightOpacity(world, pos);
	}
	
	@Override
	public int getLightValue()
	{
		return this.parent.getLightValue();
	}
	
	@Override
	public int getLightValue(IBlockAccess world, BlockPos pos)
	{
		return this.parent.getLightValue(world, pos);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isTranslucent()
	{
		return this.parent.isTranslucent();
	}
	
	@Override
	public boolean useNeighborBrightness()
	{
		return this.parent.useNeighborBrightness();
	}
	
	@Override
	public MapColor getMapColor()
	{
		return this.parent.getMapColor();
	}
	
	@Override
	public IBlockState withRotation(Rotation rot)
	{
		return wrap(this.parent.withRotation(rot));
	}
	
	@Override
	public IBlockState withMirror(Mirror mirrorIn)
	{
		return wrap(this.parent.withMirror(mirrorIn));
	}
	
	@Override
	public boolean isFullCube()
	{
		return this.parent.isFullCube();
	}
	
	@Override
	public EnumBlockRenderType getRenderType()
	{
		return this.parent.getRenderType();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getPackedLightmapCoords(IBlockAccess source, BlockPos pos)
	{
		return this.parent.getPackedLightmapCoords(source, pos);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public float getAmbientOcclusionLightValue()
	{
		return this.parent.getAmbientOcclusionLightValue();
	}
	
	@Override
	public boolean isBlockNormalCube()
	{
		return this.parent.isBlockNormalCube();
	}
	
	@Override
	public boolean isNormalCube()
	{
		return this.parent.isNormalCube();
	}
	
	@Override
	public boolean canProvidePower()
	{
		return this.parent.canProvidePower();
	}
	
	@Override
	public int getWeakPower(IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		return this.parent.getWeakPower(blockAccess, pos, side);
	}
	
	@Override
	public boolean hasComparatorInputOverride()
	{
		return this.parent.hasComparatorInputOverride();
	}
	
	@Override
	public int getComparatorInputOverride(World worldIn, BlockPos pos)
	{
		return this.parent.getComparatorInputOverride(worldIn, pos);
	}
	
	@Override
	public float getBlockHardness(World worldIn, BlockPos pos)
	{
		return this.parent.getBlockHardness(worldIn, pos);
	}
	
	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer player, World worldIn, BlockPos pos)
	{
		return this.parent.getPlayerRelativeBlockHardness(player, worldIn, pos);
	}
	
	@Override
	public int getStrongPower(IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		return this.parent.getStrongPower(blockAccess, pos, side);
	}
	
	@Override
	public EnumPushReaction getMobilityFlag()
	{
		return this.parent.getMobilityFlag();
	}
	
	@Override
	public IBlockState getActualState(IBlockAccess blockAccess, BlockPos pos)
	{
		return wrap(this.parent.getActualState(blockAccess, pos));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos)
	{
		return this.parent.getSelectedBoundingBox(worldIn, pos);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, BlockPos pos, EnumFacing facing)
	{
		return this.parent.shouldSideBeRendered(blockAccess, pos, facing);
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return this.parent.isOpaqueCube();
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos)
	{
		return this.parent.getCollisionBoundingBox(worldIn, pos);
	}
	
	@Override
	public void addCollisionBoxToList(World worldIn, BlockPos pos, AxisAlignedBB p_185908_3_,
			List<AxisAlignedBB> p_185908_4_, Entity p_185908_5_)
	{
		this.parent.addCollisionBoxToList(worldIn, pos, p_185908_3_, p_185908_4_, p_185908_5_);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockAccess blockAccess, BlockPos pos)
	{
		return this.parent.getBoundingBox(blockAccess, pos);
	}
	
	@Override
	public RayTraceResult collisionRayTrace(World worldIn, BlockPos pos, Vec3d start, Vec3d end)
	{
		return this.parent.collisionRayTrace(worldIn, pos, start, end);
	}
	
	@Override
	public boolean isFullyOpaque()
	{
		return this.parent.isFullyOpaque();
	}
	
	@Override
	public boolean doesSideBlockRendering(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return this.parent.doesSideBlockRendering(world, pos, side);
	}
	
	@Override
	public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return this.parent.isSideSolid(world, pos, side);
	}
	
	@Override
	public Collection<IProperty<?>> getPropertyNames()
	{
		return this.parent.getPropertyNames();
	}
	
	@Override
	public <T extends Comparable<T>> T getValue(IProperty<T> property)
	{
		return this.parent.getValue(property);
	}
	
	@Override
	public <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> property, V value)
	{
		return wrap(this.parent.withProperty(property, value));
	}
	
	@Override
	public <T extends Comparable<T>> IBlockState cycleProperty(IProperty<T> property)
	{
		return wrap(this.parent.cycleProperty(property));
	}
	
	@Override
	public ImmutableMap<IProperty<?>, Comparable<?>> getProperties()
	{
		return this.parent.getProperties();
	}
	
	@Override
	public Block getBlock()
	{
		return this.parent.getBlock();
	}
}