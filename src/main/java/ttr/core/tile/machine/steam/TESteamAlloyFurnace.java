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
import ttr.core.gui.machine.steam.ContainerSteamAlloyFurnace;
import ttr.core.gui.machine.steam.GuiSteamAlloyFurnace;
import ttr.core.tile.TESteamMachine;
import ttr.load.TTrLangs;

public abstract class TESteamAlloyFurnace extends TESteamMachine
{
	private final long maxTemperature;

	public TESteamAlloyFurnace(String name, int steamAmount, long power, float efficiency, float durationMultipler, long maxTemperature)
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
	protected IRecipeMap<TemplateRecipe> getRecipeMap()
	{
		return TemplateRecipeMap.ALLOY_SMELTING;
	}
	
	@Override
	public Container getContainer(EnumFacing side, EntityPlayer player)
	{
		return new ContainerSteamAlloyFurnace(player, this);
	}

	public static class TESteamAlloyFurnaceBronze extends TESteamAlloyFurnace
	{
		public TESteamAlloyFurnaceBronze()
		{
			super(TTrLangs.steamAlloyFurnaceBronze, 2000, 32, 0.75F, 1.0F, 1300L);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public GuiContainer getGui(EnumFacing side, EntityPlayer player)
		{
			return new GuiSteamAlloyFurnace.GuiSteamAlloyFurnaceBronze(player, this);
		}
	}

	public static class TESteamAlloyFurnaceSteel extends TESteamAlloyFurnace
	{
		public TESteamAlloyFurnaceSteel()
		{
			super(TTrLangs.steamAlloyFurnaceSteel, 4000, 64, 0.875F, 1.0F, 1600L);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public GuiContainer getGui(EnumFacing side, EntityPlayer player)
		{
			return new GuiSteamAlloyFurnace.GuiSteamAlloyFurnaceSteel(player, this);
		}
	}
}