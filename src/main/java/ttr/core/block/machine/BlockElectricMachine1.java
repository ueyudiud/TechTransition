/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.block.machine;

import java.util.List;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.core.block.BlockMachine;
import ttr.core.tile.TEBase;
import ttr.core.tile.electric.TEElectricalAlloySmelter;
import ttr.core.tile.electric.TEElectricalAssembler;
import ttr.core.tile.electric.TEElectricalBender;
import ttr.core.tile.electric.TEElectricalCanner;
import ttr.core.tile.electric.TEElectricalCompressor;
import ttr.core.tile.electric.TEElectricalCutter;
import ttr.core.tile.electric.TEElectricalExtractor;
import ttr.core.tile.electric.TEElectricalFurnace;
import ttr.core.tile.electric.TEElectricalGrinder;
import ttr.core.tile.electric.TEElectricalLathe;
import ttr.core.tile.electric.TEElectricalOreWasher;
import ttr.core.tile.electric.TEElectricalPressor;
import ttr.core.tile.electric.TEElectricalPyrolysisor;
import ttr.core.tile.electric.TEElectricalWiremill;

/**
 * Created at 2016年12月21日 下午1:03:20
 * @author ueyudiud
 */
public class BlockElectricMachine1 extends BlockMachine
{
	public static enum MachineType3 implements IStringSerializable
	{
		GRINDER,
		COMPRESSOR,
		EXTRACTOR,
		ALLOY_SMELTER,
		WIREMILL,
		PRESSOR,
		ASSEMBLER,
		BENDER,
		CANNER,
		CUTTER,
		FURNACE,
		LATHE,
		ORE_WASHER,
		PYROLYSISOR;
		
		@Override
		public String getName()
		{
			return name().toLowerCase();
		}
	}
	
	public static final PropertyEnum<MachineType3> MACHINE_TYPE1 = PropertyEnum.create("type", MachineType3.class);
	
	public BlockElectricMachine1()
	{
		setHardness(6.0F);
		setResistance(12.0F);
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
		return state.withProperty(WORKING, tile.isActived());
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(MACHINE_TYPE1).ordinal();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(MACHINE_TYPE1, MachineType3.values()[meta]);
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
		list.add(new ItemStack(itemIn, 1, 7));
		list.add(new ItemStack(itemIn, 1, 8));
		list.add(new ItemStack(itemIn, 1, 9));
		list.add(new ItemStack(itemIn, 1, 10));
		list.add(new ItemStack(itemIn, 1, 11));
		list.add(new ItemStack(itemIn, 1, 12));
		list.add(new ItemStack(itemIn, 1, 13));
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		switch (state.getValue(MACHINE_TYPE1))
		{
		case GRINDER       : return new TEElectricalGrinder();
		case COMPRESSOR    : return new TEElectricalCompressor();
		case EXTRACTOR     : return new TEElectricalExtractor();
		case ALLOY_SMELTER : return new TEElectricalAlloySmelter();
		case WIREMILL      : return new TEElectricalWiremill();
		case PRESSOR       : return new TEElectricalPressor();
		case ASSEMBLER     : return new TEElectricalAssembler();
		case BENDER        : return new TEElectricalBender();
		case CANNER        : return new TEElectricalCanner();
		case CUTTER        : return new TEElectricalCutter();
		case FURNACE       : return new TEElectricalFurnace();
		case LATHE         : return new TEElectricalLathe();
		case ORE_WASHER    : return new TEElectricalOreWasher();
		case PYROLYSISOR   : return new TEElectricalPyrolysisor();
		default : return null;
		}
	}
}