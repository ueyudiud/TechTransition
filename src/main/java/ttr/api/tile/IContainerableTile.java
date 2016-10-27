package ttr.api.tile;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IContainerableTile
{
	Container getContainer(EnumFacing side, EntityPlayer player);
	
	@SideOnly(Side.CLIENT)
	GuiContainer getGui(EnumFacing side, EntityPlayer player);
}
