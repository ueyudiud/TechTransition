package ttr.core.gui.machine.steam;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.core.gui.abstracts.GuiContainerBase;
import ttr.core.tile.machine.steam.TESteamGrinder;

@SideOnly(Side.CLIENT)
public abstract class GuiSteamGrinder extends GuiContainerBase
{
	public static class GuiSteamGrinderBronze extends GuiSteamGrinder
	{
		public GuiSteamGrinderBronze(EntityPlayer player, TESteamGrinder tile)
		{
			super(player, tile);
		}
		
		@Override
		public ResourceLocation getResourceLocation()
		{
			return new ResourceLocation("ttr", "textures/gui/machine/steam/grinder_bronze.png");
		}
	}
	
	public static class GuiSteamGrinderSteel extends GuiSteamGrinder
	{
		public GuiSteamGrinderSteel(EntityPlayer player, TESteamGrinder tile)
		{
			super(player, tile);
		}
		
		@Override
		public ResourceLocation getResourceLocation()
		{
			return new ResourceLocation("ttr", "textures/gui/machine/steam/grinder_steel.png");
		}
	}

	protected TESteamGrinder tile;

	public GuiSteamGrinder(EntityPlayer player, TESteamGrinder tile)
	{
		super(new ContainerSteamGrinder(player, tile));
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