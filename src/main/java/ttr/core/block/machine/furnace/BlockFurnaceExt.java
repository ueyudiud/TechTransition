package ttr.core.block.machine.furnace;

import java.util.List;
import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.enums.EnumTools;
import ttr.core.block.BlockMachine;
import ttr.core.tile.TEBase;
import ttr.core.tile.machine.furnace.TEFurnace;
import ttr.core.tile.machine.furnace.TEFurnaceBrick;
import ttr.core.tile.machine.furnace.TEFurnaceObsidian;

public class BlockFurnaceExt extends BlockMachine
{
	public static enum FurnaceMaterial implements IStringSerializable
	{
		STONE,
		BRICK,
		OBSIDIAN;
		
		@Override
		public String getName()
		{
			return name().toLowerCase();
		}
	}

	private static final PropertyEnum<FurnaceMaterial> FURNACE_MATERIAL = PropertyEnum.create("material", FurnaceMaterial.class);

	public BlockFurnaceExt()
	{
		super(Material.ROCK);
		setHardness(3.5F);
		setResistance(8.0F);
		setSoundType(SoundType.STONE);
		setDefaultState(getDefaultState().withProperty(WORKING, false).withProperty(FURNACE_MATERIAL, FurnaceMaterial.STONE));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FURNACE_MATERIAL).ordinal();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(FURNACE_MATERIAL, FurnaceMaterial.values()[meta]);
	}

	@Override
	protected IBlockState getActualState(IBlockState state, TEBase tile)
	{
		return state.withProperty(WORKING, ((TEFurnace) tile).isBurning());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		list.add(new ItemStack(itemIn, 1, 0));
		list.add(new ItemStack(itemIn, 1, 1));
		list.add(new ItemStack(itemIn, 1, 2));
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return ((TEFurnace) world.getTileEntity(pos)).isBurning() ? 14 : 0;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FURNACE_MATERIAL, FACING_PRIMARYII, WORKING);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		switch (state.getValue(FURNACE_MATERIAL))
		{
		case STONE : return new TEFurnace();
		case BRICK : return new TEFurnaceBrick();
		case OBSIDIAN : return new TEFurnaceObsidian();
		default : return null;
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return createTileEntity(worldIn, getStateFromMeta(meta));
	}
	
	@Override
	public String getHarvestTool(IBlockState state)
	{
		return EnumTools.pickaxe.name();
	}

	@Override
	public int getHarvestLevel(IBlockState state)
	{
		switch (state.getValue(FURNACE_MATERIAL))
		{
		case STONE : return 0;
		case BRICK : return 1;
		case OBSIDIAN : return 3;
		default : return 0;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if (stateIn.getValue(WORKING))
		{
			EnumFacing enumfacing = stateIn.getValue(FACING_PRIMARYII);
			double d0 = pos.getX() + 0.5D;
			double d1 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
			double d2 = pos.getZ() + 0.5D;
			double d3 = 0.52D;
			double d4 = rand.nextDouble() * 0.6D - 0.3D;
			
			if (rand.nextDouble() < 0.1D)
			{
				worldIn.playSound(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			}
			
			switch (enumfacing)
			{
			case WEST:
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
				worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
				break;
			case EAST:
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
				worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
				break;
			case NORTH:
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D, new int[0]);
				worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D, new int[0]);
				break;
			case SOUTH:
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D, new int[0]);
				worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D, new int[0]);
				break;
			default :
				break;
			}
		}
	}
}