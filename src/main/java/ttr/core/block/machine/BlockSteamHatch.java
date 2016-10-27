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
import ttr.core.tile.machine.steam.TESteamInputHatch;

public class BlockSteamHatch extends BlockMachine
{
	public static enum Material implements IStringSerializable
	{
		BRONZE,
		STEEL;
		
		@Override
		public String getName()
		{
			return name().toLowerCase();
		}
	}
	
	public static final PropertyEnum<Material> MACHINE_TYPE1 = PropertyEnum.create("type", Material.class);
	
	public BlockSteamHatch()
	{
		setHardness(3.0F);
		setResistance(8.0F);
		setDefaultState(getDefaultState().withProperty(FACING_PRIMARYI, EnumFacing.NORTH));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, MACHINE_TYPE1, FACING_PRIMARYI);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(MACHINE_TYPE1).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(MACHINE_TYPE1, Material.values()[meta]);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		list.add(new ItemStack(itemIn, 1, 0));
		list.add(new ItemStack(itemIn, 1, 1));
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		switch (state.getValue(MACHINE_TYPE1))
		{
		case BRONZE : return new TESteamInputHatch.TESteamInputHatchBronze();
		case STEEL  : return new TESteamInputHatch.TESteamInputHatchSteel();
		default : return null;
		}
	}
}