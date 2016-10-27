package ttr.core.gui.boiler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.core.TTr;
import ttr.core.tile.boiler.TECoalBoiler;

@SideOnly(Side.CLIENT)
public abstract class GuiCoalBoiler extends GuiBoiler<TECoalBoiler>
{
	public GuiCoalBoiler(EntityPlayer player, TECoalBoiler tile)
	{
		super(tile, new ContainerCoalBoiler(player, tile));
	}

	@Override
	protected void drawOther(int xOffset, int yOffset, int aMouseXPosition, int aMouseYPosition)
	{
		super.drawOther(xOffset, yOffset, aMouseXPosition, aMouseYPosition);
		int scale = tile.getBurnProgress(14);
		if (scale > 0)
		{
			drawTexturedModalRect(xOffset + 117, yOffset + 44 + 14 - scale, 177, 14 - scale, 15, scale + 1);
		}
	}

	@SideOnly(Side.CLIENT)
	public static class GuiCoalBoilerBronze extends GuiCoalBoiler
	{
		public GuiCoalBoilerBronze(EntityPlayer player, TECoalBoiler tile)
		{
			super(player, tile);
		}
		
		@Override
		public ResourceLocation getResourceLocation()
		{
			return new ResourceLocation(TTr.MODID, "textures/gui/boiler/simple_bronze.png");
		}
	}

	@SideOnly(Side.CLIENT)
	public static class GuiCoalBoilerInvar extends GuiCoalBoiler
	{
		public GuiCoalBoilerInvar(EntityPlayer player, TECoalBoiler tile)
		{
			super(player, tile);
		}
		
		@Override
		public ResourceLocation getResourceLocation()
		{
			return new ResourceLocation(TTr.MODID, "textures/gui/boiler/simple_invar.png");
		}
	}

	@SideOnly(Side.CLIENT)
	public static class GuiCoalBoilerSteel extends GuiCoalBoiler
	{
		public GuiCoalBoilerSteel(EntityPlayer player, TECoalBoiler tile)
		{
			super(player, tile);
		}
		
		@Override
		public ResourceLocation getResourceLocation()
		{
			return new ResourceLocation(TTr.MODID, "textures/gui/boiler/simple_steel.png");
		}
	}
}