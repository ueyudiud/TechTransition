package ttr.core.gui.machine.steam;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.core.gui.abstracts.GuiContainerBase;
import ttr.core.tile.machine.steam.TESteamPressor;

@SideOnly(Side.CLIENT)
public abstract class GuiSteamPressor extends GuiContainerBase
{
	public static class GuiSteamPressorBronze extends GuiSteamPressor
	{
		public GuiSteamPressorBronze(EntityPlayer player, TESteamPressor tile)
		{
			super(player, tile);
		}
		
		@Override
		public ResourceLocation getResourceLocation()
		{
			return new ResourceLocation("ttr", "textures/gui/machine/steam/pressor_bronze.png");
		}
	}
	
	public static class GuiSteamPressorSteel extends GuiSteamPressor
	{
		public GuiSteamPressorSteel(EntityPlayer player, TESteamPressor tile)
		{
			super(player, tile);
		}
		
		@Override
		public ResourceLocation getResourceLocation()
		{
			return new ResourceLocation("ttr", "textures/gui/machine/steam/pressor_steel.png");
		}
	}

	protected TESteamPressor tile;

	public GuiSteamPressor(EntityPlayer player, TESteamPressor tile)
	{
		super(new ContainerSteamPressor(player, tile));
		this.tile = tile;
	}

	@Override
	protected void drawOther(int xOffset, int yOffset, int aMouseXPosition, int aMouseYPosition)
	{
		int scale = tile.getRecipeProgress(21);
		if (scale > 0)
		{
			drawTexturedModalRect(xOffset + 78, yOffset + 25, 176, 1, scale, 18);
		}
	}
}