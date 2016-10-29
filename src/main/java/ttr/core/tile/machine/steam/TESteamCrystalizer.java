package ttr.core.tile.machine.steam;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.core.gui.machine.steam.ContainerSteamCrystalizer;
import ttr.core.gui.machine.steam.GuiSteamCrystalizer.GuiSteamCrystalizerBronze;
import ttr.core.tile.TESteamMachine;
import ttr.load.TTrLangs;

public abstract class TESteamCrystalizer extends TESteamMachine
{
	public TESteamCrystalizer(String name, int maxSteamCap, long maxPower, float efficiency, float durationMultipler)
	{
		super(name, 1, 1, 1, 0, maxSteamCap, maxPower, efficiency, durationMultipler);
	}

	@Override
	protected TemplateRecipeMap getRecipeMap()
	{
		return TemplateRecipeMap.CRYSTALIZER;
	}
	
	public static class TESteamCrystalizerBronze extends TESteamCrystalizer
	{
		public TESteamCrystalizerBronze()
		{
			super(TTrLangs.steamCrystalizerBronze, 2000, 32, 0.75F, 1.0F);
		}

		@Override
		public Container getContainer(EnumFacing side, EntityPlayer player)
		{
			return new ContainerSteamCrystalizer(player, this);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public GuiContainer getGui(EnumFacing side, EntityPlayer player)
		{
			return new GuiSteamCrystalizerBronze(player, this);
		}
	}
}