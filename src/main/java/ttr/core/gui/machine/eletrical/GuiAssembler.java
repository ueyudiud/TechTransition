/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.gui.machine.eletrical;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.core.gui.abstracts.GuiContainerBase;
import ttr.core.tile.electric.TEElectricalAssembler;

/**
 * Created at 2016年12月21日 下午1:37:16
 * @author ueyudiud
 */
@SideOnly(Side.CLIENT)
public class GuiAssembler extends GuiContainerBase
{
	protected TEElectricalAssembler tile;
	
	public GuiAssembler(EntityPlayer player, TEElectricalAssembler tile)
	{
		super(new ContainerAssembler(player, tile));
		this.tile = tile;
	}
	
	@Override
	public ResourceLocation getResourceLocation()
	{
		return new ResourceLocation("ttr", "textures/gui/machine/electric/assembler.png");
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