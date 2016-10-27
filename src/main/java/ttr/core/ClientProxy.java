package ttr.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.tile.IContainerableTile;
import ttr.core.render.TESRCauldron;
import ttr.core.render.TESRMachine;
import ttr.core.tile.machine.TECauldron;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	private TESRMachine defaultRender;
	
	@Override
	public void registerRender()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TECauldron.class, new TESRCauldron());
	}
	
	@Override
	public void registerMachineRender(Class<? extends TileEntity> tileEntityClass)
	{
		if(defaultRender == null)
		{
			defaultRender = new TESRMachine();
		}
		ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, defaultRender);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID >= 0)
		{
			TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
			if(tile instanceof IContainerableTile)
				return ((IContainerableTile) tile).getGui(EnumFacing.VALUES[ID], player);
		}
		return null;
	}
}
