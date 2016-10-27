package ttr.core.gui.machine.steam;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import ttr.api.gui.SlotOutput;
import ttr.core.gui.abstracts.ContainerTPC;
import ttr.core.tile.machine.steam.TESteamAlloyFurnace;

public class ContainerSteamAlloyFurnace extends ContainerTPC<TESteamAlloyFurnace>
{
	public ContainerSteamAlloyFurnace(EntityPlayer player, TESteamAlloyFurnace inventory)
	{
		super(player, inventory);
		addSlotToContainer(new Slot(inventory, 0, 17, 25));
		addSlotToContainer(new Slot(inventory, 1, 35, 25));
		addSlotToContainer(new Slot(inventory, 2, 53, 25));
		addSlotToContainer(new SlotOutput(inventory, 3, 107, 25));
		addSlotToContainer(new Slot(inventory, 4, 80, 63));
		locateRecipeInput = new TransLocation("input", 36, 39);
		locateRecipeOutput = new TransLocation("output", 39);
	}
}