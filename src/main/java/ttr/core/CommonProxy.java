package ttr.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import ttr.api.tile.IContainerableTile;
import ttr.core.gui.machine.eletrical.ContainerEleMachineConfig;
import ttr.core.tile.TEMachineRecipeMap;

public class CommonProxy implements IGuiHandler
{
	public static final int ELE_MACHINE_CONFIG_ID = 10;
	
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
			if(ID == ELE_MACHINE_CONFIG_ID) return tile instanceof TEMachineRecipeMap ? new ContainerEleMachineConfig(player, (TEMachineRecipeMap) tile) : null;
			else if(tile instanceof IContainerableTile)
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