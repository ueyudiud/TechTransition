package ttr.core.gui.machine.steam;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import ttr.api.gui.SlotOutput;
import ttr.core.gui.abstracts.ContainerTPC;
import ttr.core.tile.machine.steam.TESteamCutter;

public class ContainerSteamCutter extends ContainerTPC<TESteamCutter>
{
	public ContainerSteamCutter(EntityPlayer player, TESteamCutter inventory)
	{
		super(player, inventory);
		addSlotToContainer(new Slot(inventory, 0, 53, 25));
		addSlotToContainer(new SlotOutput(inventory, 1, 107, 25));
		addSlotToContainer(new Slot(inventory, 2, 80, 63));
		locateRecipeInput = new TransLocation("input", 36);
		locateRecipeOutput = new TransLocation("output", 37);
	}
}