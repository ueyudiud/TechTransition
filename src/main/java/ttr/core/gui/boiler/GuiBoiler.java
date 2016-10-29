package ttr.core.gui.boiler;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.core.gui.abstracts.GuiContainerBase;
import ttr.core.tile.boiler.TEBoiler;

@SideOnly(Side.CLIENT)
public abstract class GuiBoiler<T extends TEBoiler> extends GuiContainerBase
{
	protected T tile;

	public GuiBoiler(T tile, ContainerBoiler<?> container)
	{
		super(container);
		this.tile = tile;
	}

	@Override
	protected void drawOther(int xOffset, int yOffset, int aMouseXPosition, int aMouseYPosition)
	{
		int scale = tile.getSteamProgress(54);
		if (scale > 0)
		{
			drawTexturedModalRect(xOffset + 70, yOffset + 25 + 54 - scale, 194, 54 - scale, 10, scale);
		}
		scale = tile.getWaterProgress(54);
		if (scale > 0)
		{
			drawTexturedModalRect(xOffset + 83, yOffset + 25 + 54 - scale, 204, 54 - scale, 10, scale);
		}
		scale = tile.getTemperatureProgress(54);
		if (scale > 0)
		{
			drawTexturedModalRect(xOffset + 96, yOffset + 25 + 54 - scale, 214, 54 - scale, 10, scale);
		}
	}
}