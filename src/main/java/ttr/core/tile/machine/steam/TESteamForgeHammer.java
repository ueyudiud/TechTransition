package ttr.core.tile.machine.steam;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.core.gui.machine.steam.ContainerSteamForgeHammer;
import ttr.core.gui.machine.steam.GuiSteamForgeHammer;
import ttr.core.tile.TESteamMachine;
import ttr.load.TTrLangs;

public abstract class TESteamForgeHammer extends TESteamMachine
{
	public TESteamForgeHammer(String name, int steamAmount, long power, float efficiency, float durationMultipler)
	{
		super(name, 1, 1, 0, 0, steamAmount, power, efficiency, durationMultipler);
	}
	
	@Override
	protected TemplateRecipeMap getRecipeMap()
	{
		return TemplateRecipeMap.FORGE_HAMMER;
	}

	@Override
	public Container getContainer(EnumFacing side, EntityPlayer player)
	{
		return new ContainerSteamForgeHammer(player, this);
	}

	public static class TESteamForgeHammerBronze extends TESteamForgeHammer
	{
		public TESteamForgeHammerBronze()
		{
			super(TTrLangs.steamForgeHammerBronze, 2000, 32, 0.75F, 1.0F);
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public GuiContainer getGui(EnumFacing side, EntityPlayer player)
		{
			return new GuiSteamForgeHammer.GuiSteamForgeHammerBronze(player, this);
		}
	}

	public static class TESteamForgeHammerSteel extends TESteamForgeHammer
	{
		public TESteamForgeHammerSteel()
		{
			super(TTrLangs.steamForgeHammerSteel, 4000, 64, 0.875F, 1.0F);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public GuiContainer getGui(EnumFacing side, EntityPlayer player)
		{
			return new GuiSteamForgeHammer.GuiSteamForgeHammerSteel(player, this);
		}
	}
}