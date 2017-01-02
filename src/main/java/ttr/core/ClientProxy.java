package ttr.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.tile.IContainerableTile;
import ttr.core.gui.machine.eletrical.GuiEleMachineConfig;
import ttr.core.render.TESRCauldron;
import ttr.core.render.TESRMachine;
import ttr.core.render.TESRTank;
import ttr.core.tile.TEMachineRecipeMap;
import ttr.core.tile.machine.TECauldron;
import ttr.core.tile.machine.TETankBottom;
import ttr.load.TTrIBF;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy implements IResourceManagerReloadListener
{
	private TESRMachine defaultRender;
	
	@Override
	public void registerRender()
	{
		((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(this);
		ClientRegistry.bindTileEntitySpecialRenderer(TECauldron.class, new TESRCauldron());
		ClientRegistry.bindTileEntitySpecialRenderer(TETankBottom.class, new TESRTank());
		TTrIBF.registerRneder();
	}
	
	@Override
	public void registerMachineRender(Class<? extends TileEntity> tileEntityClass)
	{
		if(this.defaultRender == null)
		{
			this.defaultRender = new TESRMachine();
		}
		ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, this.defaultRender);
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID >= 0)
		{
			TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
			if(ID == ELE_MACHINE_CONFIG_ID) return tile instanceof TEMachineRecipeMap ? new GuiEleMachineConfig(player, (TEMachineRecipeMap) tile) : null;
			if(tile instanceof IContainerableTile)
				return ((IContainerableTile) tile).getGui(EnumFacing.VALUES[ID], player);
		}
		return null;
	}
	
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager)
	{
		ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
		if(itemColors != null)
		{
			TTrIBF.registerItemColor(itemColors);
		}
		BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();
		if(blockColors != null)
		{
			TTrIBF.registerBlockColor(blockColors);
		}
	}
}