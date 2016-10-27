package ttr.core.gui.machine.steam;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.util.LanguageManager;
import ttr.core.gui.abstracts.GuiContainerBase;
import ttr.core.tile.machine.steam.TEBronzeCompressor;

@SideOnly(Side.CLIENT)
public class GuiSteamCompressor extends GuiContainerBase
{
	private TEBronzeCompressor tile;
	
	public GuiSteamCompressor(EntityPlayer player, TEBronzeCompressor tile)
	{
		super(new ContainerSteamCompressor(player, tile));
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
			drawTexturedModalRect(xOffset + 87, yOffset + 24, 176, 0, scale, 18);
		}
		scale = tile.getPressureProgress(54);
		if (scale > 0)
		{
			drawTexturedModalRect(xOffset + 38, yOffset + 14 + 54 - scale, 176, 19, 10, scale);
		}
	}
	
	@Override
	public ResourceLocation getResourceLocation()
	{
		return new ResourceLocation("ttr", "textures/gui/machine/bronze/compressor.png");
	}
}