package ttr.core.gui.machine.steam;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.util.LanguageManager;
import ttr.core.gui.abstracts.GuiContainerBase;
import ttr.core.tile.machine.steam.TESteamCutter;

@SideOnly(Side.CLIENT)
public abstract class GuiSteamCutter extends GuiContainerBase
{
	public static class GuiSteamCutterBronze extends GuiSteamCutter
	{
		public GuiSteamCutterBronze(EntityPlayer player, TESteamCutter tile)
		{
			super(player, tile);
		}

		@Override
		public ResourceLocation getResourceLocation()
		{
			return new ResourceLocation("ttr", "textures/gui/machine/steam/cutter_bronze.png");
		}
	}

	public static class GuiSteamCutterSteel extends GuiSteamCutter
	{
		public GuiSteamCutterSteel(EntityPlayer player, TESteamCutter tile)
		{
			super(player, tile);
		}

		@Override
		public ResourceLocation getResourceLocation()
		{
			return new ResourceLocation("ttr", "textures/gui/machine/steam/cutter_steel.png");
		}
	}
	
	protected TESteamCutter tile;
	
	public GuiSteamCutter(EntityPlayer player, TESteamCutter tile)
	{
		super(new ContainerSteamCutter(player, tile));
		this.tile = tile;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String s = tile.hasCustomName() ? tile.getDisplayName().getFormattedText() : LanguageManager.translateToLocal(tile.getName());
		fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
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