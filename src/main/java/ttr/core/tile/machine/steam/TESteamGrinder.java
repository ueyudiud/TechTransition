package ttr.core.tile.machine.steam;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.recipe.IRecipeMap;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.recipe.TemplateRecipeMap.TemplateRecipe;
import ttr.core.gui.machine.steam.ContainerSteamGrinder;
import ttr.core.gui.machine.steam.GuiSteamGrinder;
import ttr.core.tile.TESteamMachine;
import ttr.load.TTrLangs;

public abstract class TESteamGrinder extends TESteamMachine
{
	public TESteamGrinder(String name, int steamAmount, long power, float efficiency, float durationMultipler)
	{
		super(name, 1, 2, 0, 0, steamAmount, power, efficiency, durationMultipler);
	}

	@Override
	protected IRecipeMap<TemplateRecipe> getRecipeMap()
	{
		return TemplateRecipeMap.GRINDING_STEAM;
	}

	@Override
	public Container getContainer(EnumFacing side, EntityPlayer player)
	{
		return new ContainerSteamGrinder(player, this);
	}
	
	public static class TESteamGrinderBronze extends TESteamGrinder
	{
		public TESteamGrinderBronze()
		{
			super(TTrLangs.steamGrinderBronze, 2000, 32, 0.75F, 1.0F);
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public GuiContainer getGui(EnumFacing side, EntityPlayer player)
		{
			return new GuiSteamGrinder.GuiSteamGrinderBronze(player, this);
		}
	}
	
	public static class TESteamGrinderSteel extends TESteamGrinder
	{
		public TESteamGrinderSteel()
		{
			super(TTrLangs.steamGrinderSteel, 4000, 64, 0.875F, 1.0F);
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public GuiContainer getGui(EnumFacing side, EntityPlayer player)
		{
			return new GuiSteamGrinder.GuiSteamGrinderSteel(player, this);
		}
	}
}