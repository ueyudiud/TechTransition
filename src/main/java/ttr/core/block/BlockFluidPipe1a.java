package ttr.core.block;

import java.util.List;

import com.google.common.collect.ObjectArrays;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeBronzeSmall;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeCopperSmall;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeSteelSmall;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeTungstenSmall;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeStainlessSteelSmall;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeTitaniumSmall;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeTungstenSteelSmall;

public class BlockFluidPipe1a extends BlockFluidPipeAbstract
{
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, ObjectArrays.<IProperty>concat(SIDE_LINK_PROPERTIES, MATERIAL));
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(MATERIAL).ordinal();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(MATERIAL, PipeMaterial.values()[meta]);
	}

	@Override
	public float getPipeSize()
	{
		return 0.375F;
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
		switch (state.getValue(MATERIAL))
		{
		case COPPER : return new TEFluidPipeCopperSmall();
		case BRONZE : return new TEFluidPipeBronzeSmall();
		case STEEL : return new TEFluidPipeSteelSmall();
		case STAINLESSSTEEL : return new TEFluidPipeStainlessSteelSmall();
		case TUNGSTEN : return new TEFluidPipeTungstenSmall();
		case TITANIUM : return new TEFluidPipeTitaniumSmall();
		case TUNGSTENSTEEL : return new TEFluidPipeTungstenSteelSmall();
		default : return null;
		}
	}
}