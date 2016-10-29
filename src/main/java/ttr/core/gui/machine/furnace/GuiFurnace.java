package ttr.core.gui.machine.furnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.core.gui.abstracts.GuiContainerBase;
import ttr.core.tile.machine.furnace.TEFurnace;

@SideOnly(Side.CLIENT)
public class GuiFurnace extends GuiContainerBase
{
	private static final ResourceLocation location = new ResourceLocation("ttr", "textures/gui/furnace.png");
	
	private TEFurnace furnace;
	
	public GuiFurnace(EntityPlayer player, TEFurnace tile)
	{
		super(new ContainerFurnace(player, tile));
		furnace = tile;
	}

	@Override
	protected void drawOther(int xOffset, int yOffset, int aMouseXPosition, int aMouseYPosition)
	{
		if (furnace.isBurning())
		{
			int i1 = furnace.getBurnProgress(13);
			drawTexturedModalRect(xOffset + 56, yOffset + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
		}
		if (furnace.isSmelting())
		{
			int i1 = furnace.getRecipeProgress(24);
			drawTexturedModalRect(xOffset + 79, yOffset + 34, 176, 14, i1 + 1, 16);
		}
		int i2 = (int) (54 * (double) furnace.temperature / 3000D);
		if(i2 > 0)
		{
			drawTexturedModalRect(xOffset + 28, yOffset + 16 + 54 - i2, 176, 31 + 54 - i2, 5, i2);
		}
	}

	@Override
	public ResourceLocation getResourceLocation()
	{
		return location;
	}
}