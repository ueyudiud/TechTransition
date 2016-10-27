package ttr.core.block;

import java.util.List;

import com.google.common.collect.ObjectArrays;

import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeBronzeBig;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeCopperBig;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeSteelBig;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeTungstenBig;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeStainlessSteelBig;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeTitaniumBig;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeTungstenSteelBig;

public class BlockFluidPipe1c extends BlockFluidPipeAbstract
{
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, ObjectArrays.concat(SIDE_LINK_PROPERTIES, MATERIAL));
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
		return 0.625F;
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
		case COPPER : return new TEFluidPipeCopperBig();
		case BRONZE : return new TEFluidPipeBronzeBig();
		case STEEL : return new TEFluidPipeSteelBig();
		case STAINLESSSTEEL : return new TEFluidPipeStainlessSteelBig();
		case TUNGSTEN : return new TEFluidPipeTungstenBig();
		case TITANIUM : return new TEFluidPipeTitaniumBig();
		case TUNGSTENSTEEL : return new TEFluidPipeTungstenSteelBig();
		default : return null;
		}
	}
}