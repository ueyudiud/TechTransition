/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.tile.electric;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.inventory.Inventory;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.tile.IContainerableTile;
import ttr.core.gui.machine.eletrical.ContainerCutter;
import ttr.core.gui.machine.eletrical.GuiCutter;
import ttr.core.tile.TEElectricalMachineRecipeMapTemplate;
import ttr.load.TTrLangs;

/**
 * Created at 2016年12月21日 下午3:08:39
 * @author ueyudiud
 */
public class TEElectricalCutter extends TEElectricalMachineRecipeMapTemplate implements IContainerableTile
{
	public TEElectricalCutter()
	{
		super(1, 1, 1, 0, 16384, 1);
		this.inventory = new Inventory(1, TTrLangs.eleCutter, 64);
	}
	
	@Override
	public Container getContainer(EnumFacing side, EntityPlayer player)
	{
		return new ContainerCutter(player, this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiContainer getGui(EnumFacing side, EntityPlayer player)
	{
		return new GuiCutter(player, this);
	}
	
	@Override
	protected TemplateRecipeMap getRecipeMap()
	{
		return TemplateRecipeMap.CUTTING;
	}
}