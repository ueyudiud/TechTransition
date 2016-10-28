package ttr.core.tile.machine.steam;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.core.gui.machine.steam.ContainerSteamCutter;
import ttr.core.gui.machine.steam.GuiSteamCutter;
import ttr.core.tile.TESteamMachine;
import ttr.load.TTrLangs;

public abstract class TESteamCutter extends TESteamMachine
{
	public TESteamCutter(String name, int steamAmount, long power, float efficiency, float durationMultipler)
	{
		super(name, 1, 1, 0, 0, steamAmount, power, efficiency, durationMultipler);
	}

	@Override
	protected TemplateRecipeMap getRecipeMap()
	{
		return TemplateRecipeMap.CUTTING_STEAM;
	}

	@Override
	public Container getContainer(EnumFacing side, EntityPlayer player)
	{
		return new ContainerSteamCutter(player, this);
	}
	
	public static class TESteamCutterBronze extends TESteamCutter
	{
		public TESteamCutterBronze()
		{
			super(TTrLangs.steamCutterBronze, 2000, 32, 0.75F, 1.0F);
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public GuiContainer getGui(EnumFacing side, EntityPlayer player)
		{
			return new GuiSteamCutter.GuiSteamCutterBronze(player, this);
		}
	}
	
	public static class TESteamCutterSteel extends TESteamCutter
	{
		public TESteamCutterSteel()
		{
			super(TTrLangs.steamCutterSteel, 4000, 64, 0.875F, 1.0F);
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public GuiContainer getGui(EnumFacing side, EntityPlayer player)
		{
			return new GuiSteamCutter.GuiSteamCutterSteel(player, this);
		}
	}
}