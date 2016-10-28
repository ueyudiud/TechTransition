package ttr.core.tile.machine.steam;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.recipe.TemplateRecipeMap.TemplateRecipe;
import ttr.core.gui.machine.steam.ContainerSteamFurnace;
import ttr.core.gui.machine.steam.GuiSteamFurnace;
import ttr.core.tile.TESteamMachine;
import ttr.load.TTrLangs;

public abstract class TESteamFurnace extends TESteamMachine
{
	private final long maxTemperature;
	
	public TESteamFurnace(String name, int steamAmount, long power, float efficiency, float durationMultipler, long maxTemperature)
	{
		super(name, 3, 1, 0, 0, steamAmount, power, efficiency, durationMultipler);
		this.maxTemperature = maxTemperature;
	}

	@Override
	protected boolean matchRecipeSpecial(TemplateRecipe recipe)
	{
		return super.matchRecipeSpecial(recipe) && recipe.customValue <= maxTemperature;
	}

	@Override
	protected TemplateRecipeMap getRecipeMap()
	{
		return TemplateRecipeMap.SMELTING;
	}
	
	@Override
	public Container getContainer(EnumFacing side, EntityPlayer player)
	{
		return new ContainerSteamFurnace(player, this);
	}
	
	public static class TESteamFurnaceBronze extends TESteamFurnace
	{
		public TESteamFurnaceBronze()
		{
			super(TTrLangs.steamFurnaceBronze, 2000, 32, 0.75F, 1.0F, 1300L);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public GuiContainer getGui(EnumFacing side, EntityPlayer player)
		{
			return new GuiSteamFurnace.GuiSteamFurnaceBronze(player, this);
		}
	}
	
	public static class TESteamFurnaceSteel extends TESteamFurnace
	{
		public TESteamFurnaceSteel()
		{
			super(TTrLangs.steamFurnaceSteel, 4000, 64, 0.875F, 1.0F, 1600L);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public GuiContainer getGui(EnumFacing side, EntityPlayer player)
		{
			return new GuiSteamFurnace.GuiSteamFurnaceSteel(player, this);
		}
	}
}