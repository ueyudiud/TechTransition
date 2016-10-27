package ttr.core.gui.machine.steam;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import ttr.api.gui.SlotOutput;
import ttr.core.gui.abstracts.ContainerTPC;
import ttr.core.tile.machine.steam.TESteamPressor;

public class ContainerSteamPressor extends ContainerTPC<TESteamPressor>
{
	public ContainerSteamPressor(EntityPlayer player, TESteamPressor inventory)
	{
		super(player, inventory);
		addSlotToContainer(new Slot(inventory, 0, 35, 25));
		addSlotToContainer(new Slot(inventory, 1, 53, 25));
		addSlotToContainer(new SlotOutput(inventory, 2, 107, 25));
		addSlotToContainer(new Slot(inventory, 3, 80, 63));
		locateRecipeInput = new TransLocation("input", 36, 38);
		locateRecipeOutput = new TransLocation("output", 38);
	}
}