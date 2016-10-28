package ttr.core.block;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import ic2.api.tile.IWrenchable;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ttr.api.data.EnumToolType;
import ttr.api.data.V;
import ttr.api.util.Util;
import ttr.core.tile.TEBase;
import ttr.core.tile.TEMachineBase;

public class BlockMachine extends Block implements ITileEntityProvider, IWrenchable
{
	/** Six facing allowed. */
	public static final PropertyEnum<EnumFacing> FACING_PRIMARYI = PropertyEnum.create("facing_primary", EnumFacing.class);
	/** Four facing allowed. */
	public static final PropertyEnum<EnumFacing> FACING_PRIMARYII = PropertyEnum.create("facing_primary", EnumFacing.class, EnumFacing.HORIZONTALS);
	public static final PropertyEnum<EnumFacing> FACING_OUTPUT = PropertyEnum.create("facing_output", EnumFacing.class);
	public static final PropertyBool WORKING = PropertyBool.create("working");
	
	public BlockMachine(Material blockMaterialIn, MapColor blockMapColorIn)
	{
		super(blockMaterialIn, blockMapColorIn);
	}
	public BlockMachine(Material materialIn)
	{
		super(materialIn);
	}
	public BlockMachine()
	{
		super(V.MATERIAL_MACHINE);
		setSoundType(SoundType.METAL);
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING_PRIMARYI);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack)
	{
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile instanceof TEBase)
		{
			((TEBase) tile).setRotation(getFacing(placer));
			if(stack.hasTagCompound())
			{
				((TEBase) tile).onBlockPlacedBy(state, placer, stack);
			}
		}
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		TileEntity tile = worldIn.getTileEntity(pos);
		EnumFacing facing;
		if(tile instanceof TEBase)
		{
			if(getBlockState().getProperties().contains(FACING_PRIMARYI))
			{
				if((facing = ((TEBase) tile).getRotation()) != null)
				{
					state = state.withProperty(FACING_PRIMARYI, facing);
				}
			}
			else if(getBlockState().getProperties().contains(FACING_PRIMARYII))
			{
				if((facing = ((TEBase) tile).getRotation()) != null)
				{
					state = state.withProperty(FACING_PRIMARYII, facing);
				}
			}
			if(getBlockState().getProperties().contains(WORKING))
			{
				if(tile instanceof TEMachineBase)
				{
					state = state.withProperty(WORKING, ((TEMachineBase) tile).isActived());
				}
			}
			return getActualState(state, (TEBase) tile);
		}
		return state;
	}
	
	protected IBlockState getActualState(IBlockState state, TEBase tile)
	{
		return state;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
	{
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile instanceof TEBase)
		{
			((TEBase) tile).onNeighbourBlockChange();
		}
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
	{
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TEBase)
		{
			((TEBase) tile).onNeighbourBlockChange();
		}
	}

	public static EnumFacing getFacing(EntityLivingBase player)
	{
		return EnumFacing.HORIZONTALS[MathHelper.floor_double(player.rotationYaw * 4.0F / 360F + 0.5D) & 3].getOpposite();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileEntity tile = worldIn.getTileEntity(pos);
		return Util.onTileActivatedGeneral(playerIn, hand, heldItem, side, hitX, hitY, hitZ, tile);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return createTileEntity(worldIn, getStateFromMeta(meta));
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		if(!worldIn.isRemote)
		{
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile instanceof IInventory)
			{
				IInventory inventory = (IInventory) tile;
				for(int i = 0; i < inventory.getSizeInventory(); ++i)
				{
					ItemStack stack = inventory.removeStackFromSlot(i);
					if(stack != null)
					{
						float f = 0.7F;
						EntityItem entityitem = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack.copy());
						entityitem.motionX = RANDOM.nextFloat() * 0.04F - 0.02F;
						entityitem.motionY = 0.2F + RANDOM.nextFloat() * 0.1F;
						entityitem.motionZ = RANDOM.nextFloat() * 0.04F - 0.02F;
						entityitem.setPickupDelay(10);
						worldIn.spawnEntityInWorld(entityitem);
					}
				}
			}
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public String getHarvestTool(IBlockState state)
	{
		return EnumToolType.wrench.name();
	}
	
	@Override
	public int getHarvestLevel(IBlockState state)
	{
		return 0;
	}
	
	private static ThreadLocal<TileEntity> threadTile = new ThreadLocal();
	
	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tile,
			ItemStack stack)
	{
		threadTile.set(tile);
		super.harvestBlock(worldIn, player, pos, state, tile, stack);
		threadTile.set(null);
	}
	
	@Override
	protected ItemStack createStackedBlock(IBlockState state)
	{
		return new ItemStack(this, 1, getMetaFromState(state));
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		ArrayList<ItemStack> list = new ArrayList();
		TileEntity tile = threadTile.get();
		if(tile == null)
		{
			tile = world.getTileEntity(pos);
		}
		if(tile instanceof TEBase)
		{
			((TEBase) tile).getWrenchDrops(state, getMetaFromState(state), list);
			return list;
		}
		list.add(createStackedBlock(state));
		return list;
	}

	@Override
	public EnumFacing getFacing(World world, BlockPos pos)
	{
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TEBase)
			return ((TEBase) tile).getRotation();
		return null;
	}
	
	@Override
	public boolean setFacing(World world, BlockPos pos, EnumFacing newDirection, EntityPlayer player)
	{
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TEBase)
			return ((TEBase) tile).setRotation(newDirection);
		return false;
	}

	@Override
	public boolean wrenchCanRemove(World world, BlockPos pos, EntityPlayer player)
	{
		return false;
	}

	@Override
	@Deprecated
	public List<ItemStack> getWrenchDrops(World world, BlockPos pos, IBlockState state, TileEntity tile,
			EntityPlayer player, int fortune)
	{
		return ImmutableList.of();
	}
}