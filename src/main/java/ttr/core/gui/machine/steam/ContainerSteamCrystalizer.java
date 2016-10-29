package ttr.core.gui.machine.steam;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import ttr.api.gui.SlotOutput;
import ttr.core.gui.abstracts.ContainerTPC;
import ttr.core.gui.abstracts.FluidSlot;
import ttr.core.tile.machine.steam.TESteamCrystalizer;

public class ContainerSteamCrystalizer extends ContainerTPC<TESteamCrystalizer>
{
	public ContainerSteamCrystalizer(EntityPlayer player, TESteamCrystalizer inventory)
	{
		super(player, inventory);
		addSlotToContainer(new Slot(inventory, 0, 53, 25));
		addSlotToContainer(new FluidSlot(inventory, 0, 35, 25));
		addSlotToContainer(new SlotOutput(inventory, 1, 107, 25));
		locateRecipeInput = new TransLocation("input", 36);
		locateRecipeOutput = new TransLocation("output", 37);
	}
}