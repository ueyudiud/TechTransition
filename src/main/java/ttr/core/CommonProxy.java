package ttr.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import ttr.api.tile.IContainerableTile;

public class CommonProxy implements IGuiHandler
{
	public void registerRender()
	{

	}
	
	public void registerMachineRender(Class<? extends TileEntity> tileEntityClass)
	{

	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID >= 0)
		{
			TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
			if(tile instanceof IContainerableTile)
				return ((IContainerableTile) tile).getContainer(EnumFacing.VALUES[ID], player);
		}
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
}