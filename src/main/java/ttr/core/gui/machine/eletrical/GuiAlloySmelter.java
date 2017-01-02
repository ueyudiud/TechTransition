/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.gui.machine.eletrical;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.core.gui.abstracts.GuiContainerBase;
import ttr.core.tile.electric.TEElectricalAlloySmelter;

/**
 * Created at 2016年12月21日 下午2:48:20
 * @author ueyudiud
 */
@SideOnly(Side.CLIENT)
public class GuiAlloySmelter extends GuiContainerBase
{
	protected TEElectricalAlloySmelter tile;
	
	public GuiAlloySmelter(EntityPlayer player, TEElectricalAlloySmelter tile)
	{
		super(new ContainerAlloySmelter(player, tile));
		this.tile = tile;
	}
	
	@Override
	public ResourceLocation getResourceLocation()
	{
		return new ResourceLocation("ttr", "textures/gui/machine/electric/alloy_smelter.png");
	}
	
	@Override
	protected void drawOther(int xOffset, int yOffset, int aMouseXPosition, int aMouseYPosition)
	{
		int scale = this.tile.getRecipeProgress(21);
		if (scale > 0)
		{
			drawTexturedModalRect(xOffset + 78, yOffset + 25, 176, 1, scale, 18);
		}
	}
}