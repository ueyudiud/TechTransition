package ttr.core.tile.machine.steam;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.core.gui.machine.steam.ContainerSteamPressor;
import ttr.core.gui.machine.steam.GuiSteamPressor;
import ttr.core.tile.TESteamMachine;
import ttr.load.TTrLangs;

public abstract class TESteamPressor extends TESteamMachine
{
	public TESteamPressor(String name, int steamAmount, long power, float efficiency, float durationMultipler)
	{
		super(name, 2, 1, 0, 0, steamAmount, power, efficiency, durationMultipler);
	}
	
	@Override
	protected TemplateRecipeMap getRecipeMap()
	{
		return TemplateRecipeMap.PRESS;
	}

	@Override
	public Container getContainer(EnumFacing side, EntityPlayer player)
	{
		return new ContainerSteamPressor(player, this);
	}

	public static class TESteamPressorBronze extends TESteamPressor
	{
		public TESteamPressorBronze()
		{
			super(TTrLangs.steamPressorBronze, 2000, 32, 0.75F, 1.0F);
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public GuiContainer getGui(EnumFacing side, EntityPlayer player)
		{
			return new GuiSteamPressor.GuiSteamPressorBronze(player, this);
		}
	}

	public static class TESteamPressorSteel extends TESteamPressor
	{
		public TESteamPressorSteel()
		{
			super(TTrLangs.steamPressorSteel, 4000, 64, 0.875F, 1.0F);
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public GuiContainer getGui(EnumFacing side, EntityPlayer player)
		{
			return new GuiSteamPressor.GuiSteamPressorSteel(player, this);
		}
	}
}