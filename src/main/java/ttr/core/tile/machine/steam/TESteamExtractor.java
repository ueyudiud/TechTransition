package ttr.core.tile.machine.steam;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.core.gui.machine.steam.ContainerSteamExtractor;
import ttr.core.gui.machine.steam.GuiSteamExtractor;
import ttr.core.tile.TESteamMachine;
import ttr.load.TTrLangs;

public abstract class TESteamExtractor extends TESteamMachine
{
	public TESteamExtractor(String name, int steamAmount, long power, float efficiency, float durationMultipler)
	{
		super(name, 1, 1, 0, 0, steamAmount, power, efficiency, durationMultipler);
	}

	@Override
	protected TemplateRecipeMap getRecipeMap()
	{
		return TemplateRecipeMap.EXTRACT_STEAM;
	}

	@Override
	public Container getContainer(EnumFacing side, EntityPlayer player)
	{
		return new ContainerSteamExtractor(player, this);
	}
	
	public static class TESteamExtractorBronze extends TESteamExtractor
	{
		public TESteamExtractorBronze()
		{
			super(TTrLangs.steamExtractorBronze, 2000, 32, 0.75F, 1.0F);
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public GuiContainer getGui(EnumFacing side, EntityPlayer player)
		{
			return new GuiSteamExtractor.GuiSteamExtractorBronze(player, this);
		}
	}
	
	public static class TESteamExtractorSteel extends TESteamExtractor
	{
		public TESteamExtractorSteel()
		{
			super(TTrLangs.steamExtractorSteel, 4000, 64, 0.875F, 1.0F);
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public GuiContainer getGui(EnumFacing side, EntityPlayer player)
		{
			return new GuiSteamExtractor.GuiSteamExtractorSteel(player, this);
		}
	}
}