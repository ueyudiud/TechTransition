package ttr.core.gui.machine;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.util.LanguageManager;
import ttr.core.gui.abstracts.GuiContainerBase;
import ttr.core.tile.machine.TEForge;

@SideOnly(Side.CLIENT)
public class GuiForge extends GuiContainerBase
{
	private static final ResourceLocation location = new ResourceLocation("ttr", "textures/gui/forge.png");
	
	private TEForge tile;
	
	public GuiForge(EntityPlayer player, TEForge tile)
	{
		super(new ContainerForge(player, tile));
		this.tile = tile;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y)
	{
		String s = tile.hasCustomName() ? tile.getDisplayName().getFormattedText() : LanguageManager.translateToLocal(tile.getName());
		fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawOther(int xOffset, int yOffset, int aMouseXPosition, int aMouseYPosition)
	{
		if (tile.isBurning())
		{
			int i1 = tile.getBurnProgress(13);
			drawTexturedModalRect(xOffset + 26, yOffset + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
		}
		if (tile.isSmelting())
		{
			int i1 = tile.getRecipeProgress(24);
			drawTexturedModalRect(xOffset + 94, yOffset + 34, 176, 14, i1 + 1, 16);
		}
		int i2 = (int) (54 * (double) tile.temperature / 3000D);
		if(i2 > 0)
		{
			drawTexturedModalRect(xOffset + 43, yOffset + 16 + 54 - i2, 176, 31 + 54 - i2, 5, i2);
		}
	}

	@Override
	public ResourceLocation getResourceLocation()
	{
		return location;
	}
}