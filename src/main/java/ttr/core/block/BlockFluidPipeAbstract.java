package ttr.core.block;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ttr.core.tile.TEBase;
import ttr.core.tile.pipe.TEFluidPipe;

public abstract class BlockFluidPipeAbstract extends BlockMachine
{
	public static enum PipeMaterial implements IStringSerializable
	{
		COPPER,
		BRONZE,
		STEEL,
		STAINLESSSTEEL,
		TUNGSTEN,
		TITANIUM,
		TUNGSTENSTEEL;

		@Override
		public String getName()
		{
			return name().toLowerCase();
		}
	}
	
	public static final PropertyEnum<PipeMaterial> MATERIAL = PropertyEnum.create("material", PipeMaterial.class);

	public static final PropertyBool DOWN = PropertyBool.create("down");
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool EAST = PropertyBool.create("east");
	
	public static final IProperty[] SIDE_LINK_PROPERTIES = {DOWN, UP, NORTH, SOUTH, WEST, EAST};

	public BlockFluidPipeAbstract()
	{
		super(Material.IRON);
		setHardness(2.0F);
		setResistance(3.0F);
		setDefaultState(
				getDefaultState()
				.withProperty(DOWN, false)
				.withProperty(UP, false)
				.withProperty(NORTH, true)
				.withProperty(SOUTH, true)
				.withProperty(WEST, false)
				.withProperty(EAST, false));
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, SIDE_LINK_PROPERTIES);
	}
	
	@Override
	protected IBlockState getActualState(IBlockState state, TEBase tile)
	{
		for(EnumFacing facing : EnumFacing.VALUES)
		{
			state = state.withProperty(SIDE_LINK_PROPERTIES[facing.ordinal()], ((TEFluidPipe) tile).isLink(facing));
		}
		return state;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	public abstract float getPipeSize();

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn)
	{
		final float f1 = 0.0F;
		final float f2 = 0.5F - getPipeSize() / 2.0F;
		final float f3 = 0.5F + getPipeSize() / 2.0F;
		final float f4 = 1.0F;
		collidingBoxes.add(new AxisAlignedBB(f2, f2, f2, f3, f3, f3));
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile instanceof TEFluidPipe)
		{
			if(((TEFluidPipe) tile).isLink(EnumFacing.DOWN))
			{
				collidingBoxes.add(new AxisAlignedBB(f2, f1, f2, f3, f2, f3));
			}
			if(((TEFluidPipe) tile).isLink(EnumFacing.UP))
			{
				collidingBoxes.add(new AxisAlignedBB(f2, f3, f2, f3, f4, f3));
			}
			if(((TEFluidPipe) tile).isLink(EnumFacing.NORTH))
			{
				collidingBoxes.add(new AxisAlignedBB(f2, f2, f1, f3, f3, f2));
			}
			if(((TEFluidPipe) tile).isLink(EnumFacing.SOUTH))
			{
				collidingBoxes.add(new AxisAlignedBB(f2, f2, f3, f3, f3, f4));
			}
			if(((TEFluidPipe) tile).isLink(EnumFacing.WEST))
			{
				collidingBoxes.add(new AxisAlignedBB(f1, f2, f2, f2, f3, f3));
			}
			if(((TEFluidPipe) tile).isLink(EnumFacing.EAST))
			{
				collidingBoxes.add(new AxisAlignedBB(f3, f3, f2, f4, f3, f3));
			}
		}
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
	{
		return FULL_BLOCK_AABB;
	}
	
	@Override
	public EnumFacing getFacing(World world, BlockPos pos)
	{
		return EnumFacing.SOUTH;
	}
	
	@Override
	public boolean setFacing(World world, BlockPos pos, EnumFacing newDirection, EntityPlayer player)
	{
		return false;
	}
	
	@Override
	public boolean wrenchCanRemove(World world, BlockPos pos, EntityPlayer player)
	{
		return false;
	}
}