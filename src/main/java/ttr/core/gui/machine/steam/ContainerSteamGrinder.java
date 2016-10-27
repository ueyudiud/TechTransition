package ttr.core.gui.machine.steam;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import ttr.api.gui.SlotOutput;
import ttr.core.gui.abstracts.ContainerTPC;
import ttr.core.tile.machine.steam.TESteamGrinder;

public class ContainerSteamGrinder extends ContainerTPC<TESteamGrinder>
{
	public ContainerSteamGrinder(EntityPlayer player, TESteamGrinder inventory)
	{
		super(player, inventory);
		addSlotToContainer(new Slot(inventory, 0, 53, 25));
		addSlotToContainer(new SlotOutput(inventory, 1, 107, 25));
		addSlotToContainer(new SlotOutput(inventory, 2, 125, 25));
		addSlotToContainer(new Slot(inventory, 3, 80, 63));
		locateRecipeInput = new TransLocation("input", 36);
		locateRecipeOutput = new TransLocation("output", 37, 39);
	}
}