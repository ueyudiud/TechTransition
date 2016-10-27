package ttr.core.gui.machine.steam;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import ttr.api.gui.SlotOutput;
import ttr.core.gui.abstracts.ContainerTPC;
import ttr.core.tile.machine.steam.TEBronzeCompressor;

public class ContainerSteamCompressor extends ContainerTPC<TEBronzeCompressor>
{
	public ContainerSteamCompressor(EntityPlayer player, TEBronzeCompressor inventory)
	{
		super(player, inventory);
		addSlotToContainer(new Slot(inventory, 0, 62, 25));
		addSlotToContainer(new SlotOutput(inventory, 1, 116, 25));
		locateRecipeInput = new TransLocation("input", 36);
		locateRecipeOutput = new TransLocation("output", 37);
	}
}