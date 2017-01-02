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
import ttr.api.recipe.TemplateRecipeMap.TemplateRecipe;
import ttr.api.tile.IContainerableTile;
import ttr.core.gui.machine.eletrical.ContainerAlloySmelter;
import ttr.core.gui.machine.eletrical.GuiAlloySmelter;
import ttr.core.tile.TEElectricalMachineRecipeMapTemplate;
import ttr.load.TTrLangs;

/**
 * Created at 2016年12月21日 下午2:43:32
 * @author ueyudiud
 */
public class TEElectricalAlloySmelter extends TEElectricalMachineRecipeMapTemplate implements IContainerableTile
{
	public TEElectricalAlloySmelter()
	{
		super(4, 1, 0, 0, 16384, 1);
		this.inventory = new Inventory(1, TTrLangs.eleAlloySmelter, 64);
	}
	
	@Override
	protected boolean matchRecipeSpecial(TemplateRecipe recipe)
	{
		return recipe.customValue <= getTemperature();
	}
	
	private long getTemperature()
	{
		switch (getPluginLevel("thermal"))
		{
		case 1 : return 1300;
		case 2 : return 1500;
		case 3 : return 1800;
		case 4 : return 2200;
		default: return 1000;
		}
	}
	
	@Override
	public Container getContainer(EnumFacing side, EntityPlayer player)
	{
		return new ContainerAlloySmelter(player, this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiContainer getGui(EnumFacing side, EntityPlayer player)
	{
		return new GuiAlloySmelter(player, this);
	}
	
	@Override
	protected TemplateRecipeMap getRecipeMap()
	{
		return TemplateRecipeMap.ALLOY_SMELTING;
	}
}