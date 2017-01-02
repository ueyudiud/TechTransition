/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.gui.machine.eletrical;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.core.gui.abstracts.GuiContainerBase;
import ttr.core.tile.TEMachineRecipeMap;

/**
 * Created at 2016年12月21日 下午10:56:10
 * @author ueyudiud
 */
@SideOnly(Side.CLIENT)
public class GuiEleMachineConfig extends GuiContainerBase
{
	private TEMachineRecipeMap tile;
	
	public GuiEleMachineConfig(EntityPlayer player, TEMachineRecipeMap tile)
	{
		super(new ContainerEleMachineConfig(player, tile));
		this.tile = tile;
	}
	
	@Override
	protected void drawOther(int xOffset, int yOffset, int aMouseXPosition, int aMouseYPosition)
	{
		if(this.tile.allowAutoOutputFluid)
		{
			drawTexturedModalRect(xOffset + 7, yOffset + 62, 176, 0, 18, 18);
		}
		if(this.tile.allowAutoOutputItem)
		{
			drawTexturedModalRect(xOffset + 25, yOffset + 62, 176, 18, 18, 18);
		}
		if(this.tile.autoOutputFace != null)
		{
			drawTexturedModalRect(xOffset + 43, yOffset + 62, 18 * this.tile.autoOutputFace.ordinal(), 166, 18, 18);
		}
		if(this.tile.moveFully)
		{
			drawTexturedModalRect(xOffset + 7, yOffset + 44, 176, 36, 18, 18);
		}
		if(this.tile.throwItemOut)
		{
			drawTexturedModalRect(xOffset + 25, yOffset + 44, 176, 54, 18, 18);
		}
	}
	
	@Override
	public ResourceLocation getResourceLocation()
	{
		return new ResourceLocation("ttr", "textures/gui/machine/electric/config.png");
	}
}