/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.gui.machine.steam;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.core.gui.abstracts.GuiContainerBase;
import ttr.core.tile.machine.steam.TEBronzeBlastFurnace;

/**
 * Created at 2016年12月20日 下午2:27:04
 * @author ueyudiud
 */
@SideOnly(Side.CLIENT)
public class GuiBronzeBlastFurnace extends GuiContainerBase
{
	private TEBronzeBlastFurnace tile;
	
	public GuiBronzeBlastFurnace(EntityPlayer player, TEBronzeBlastFurnace tile)
	{
		super(new ContainerBronzeBlastFurnace(player, tile));
		this.tile = tile;
	}
	
	@Override
	protected void drawOther(int xOffset, int yOffset, int aMouseXPosition, int aMouseYPosition)
	{
		int scale = this.tile.getRecipeProgress(20);
		if (scale > 0)
		{
			drawTexturedModalRect(xOffset + 58, yOffset + 28, 176, 0, scale, 20);
		}
	}
	
	@Override
	public ResourceLocation getResourceLocation()
	{
		return new ResourceLocation("ttr", "textures/gui/machine/bronze/blast_furnace.png");
	}
}