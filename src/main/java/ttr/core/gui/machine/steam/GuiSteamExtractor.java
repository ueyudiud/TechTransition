package ttr.core.gui.machine.steam;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.core.gui.abstracts.GuiContainerBase;
import ttr.core.tile.machine.steam.TESteamExtractor;

@SideOnly(Side.CLIENT)
public abstract class GuiSteamExtractor extends GuiContainerBase
{
	public static class GuiSteamExtractorBronze extends GuiSteamExtractor
	{
		public GuiSteamExtractorBronze(EntityPlayer player, TESteamExtractor tile)
		{
			super(player, tile);
		}
		
		@Override
		public ResourceLocation getResourceLocation()
		{
			return new ResourceLocation("ttr", "textures/gui/machine/steam/extractor_bronze.png");
		}
	}
	
	public static class GuiSteamExtractorSteel extends GuiSteamExtractor
	{
		public GuiSteamExtractorSteel(EntityPlayer player, TESteamExtractor tile)
		{
			super(player, tile);
		}
		
		@Override
		public ResourceLocation getResourceLocation()
		{
			return new ResourceLocation("ttr", "textures/gui/machine/steam/extractor_steel.png");
		}
	}

	protected TESteamExtractor tile;

	public GuiSteamExtractor(EntityPlayer player, TESteamExtractor tile)
	{
		super(new ContainerSteamExtractor(player, tile));
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