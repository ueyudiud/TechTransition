package ttr.core.block.machine;

import static ttr.core.block.machine.BlockSteamMachine1a.MACHINE_TYPE1;

import java.util.List;

import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.core.block.BlockMachine;
import ttr.core.block.machine.BlockSteamMachine1a.MachineType1;
import ttr.core.tile.TEBase;
import ttr.core.tile.TEMachineInventory;
import ttr.core.tile.TEMachineRecipeMap;
import ttr.core.tile.machine.steam.TESteamAlloyFurnace;
import ttr.core.tile.machine.steam.TESteamCutter;
import ttr.core.tile.machine.steam.TESteamExtractor;
import ttr.core.tile.machine.steam.TESteamForgeHammer;
import ttr.core.tile.machine.steam.TESteamFurnace;
import ttr.core.tile.machine.steam.TESteamGrinder;
import ttr.core.tile.machine.steam.TESteamPressor;

public class BlockSteamMachine1b extends BlockMachine
{
	public BlockSteamMachine1b()
	{
		setHardness(4.5F);
		setResistance(9.0F);
		setDefaultState(getDefaultState().withProperty(FACING_PRIMARYII, EnumFacing.NORTH).withProperty(WORKING, false));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, MACHINE_TYPE1, WORKING, FACING_PRIMARYII, FACING_OUTPUT);
	}

	@Override
	protected IBlockState getActualState(IBlockState state, TEBase tile)
	{
		return state.withProperty(WORKING, ((TEMachineRecipeMap) tile).isActived()).withProperty(FACING_OUTPUT, ((TEMachineInventory) tile).getFacing("exhaust"));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(MACHINE_TYPE1).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(MACHINE_TYPE1, MachineType1.values()[meta]);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		list.add(new ItemStack(itemIn, 1, 0));
		list.add(new ItemStack(itemIn, 1, 1));
		list.add(new ItemStack(itemIn, 1, 2));
		list.add(new ItemStack(itemIn, 1, 3));
		list.add(new ItemStack(itemIn, 1, 4));
		list.add(new ItemStack(itemIn, 1, 5));
		list.add(new ItemStack(itemIn, 1, 6));
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		switch (state.getValue(MACHINE_TYPE1))
		{
		case FURNACE       : return new TESteamFurnace.TESteamFurnaceSteel();
		case ALLOY_FURNACE : return new TESteamAlloyFurnace.TESteamAlloyFurnaceSteel();
		case GRINDER       : return new TESteamGrinder.TESteamGrinderSteel();
		case EXTRACTOR     : return new TESteamExtractor.TESteamExtractorSteel();
		case FORGE_HAMMER  : return new TESteamForgeHammer.TESteamForgeHammerSteel();
		case CUTTER        : return new TESteamCutter.TESteamCutterSteel();
		case PRESSOR       : return new TESteamPressor.TESteamPressorSteel();
		default : return null;
		}
	}
}