package ttr.core.gui.machine.steam;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.core.gui.abstracts.GuiContainerBase;
import ttr.core.tile.machine.steam.TESteamAlloyFurnace;

@SideOnly(Side.CLIENT)
public abstract class GuiSteamAlloyFurnace extends GuiContainerBase
{
	public static class GuiSteamAlloyFurnaceBronze extends GuiSteamAlloyFurnace
	{
		public GuiSteamAlloyFurnaceBronze(EntityPlayer player, TESteamAlloyFurnace tile)
		{
			super(player, tile);
		}

		@Override
		public ResourceLocation getResourceLocation()
		{
			return new ResourceLocation("ttr", "textures/gui/machine/steam/alloysmelter_bronze.png");
		}
	}

	public static class GuiSteamAlloyFurnaceSteel extends GuiSteamAlloyFurnace
	{
		public GuiSteamAlloyFurnaceSteel(EntityPlayer player, TESteamAlloyFurnace tile)
		{
			super(player, tile);
		}

		@Override
		public ResourceLocation getResourceLocation()
		{
			return new ResourceLocation("ttr", "textures/gui/machine/steam/alloysmelter_steel.png");
		}
	}
	
	protected TESteamAlloyFurnace tile;
	
	public GuiSteamAlloyFurnace(EntityPlayer player, TESteamAlloyFurnace tile)
	{
		super(new ContainerSteamAlloyFurnace(player, tile));
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