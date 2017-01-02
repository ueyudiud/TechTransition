/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.gui.machine.eletrical;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import ttr.api.gui.SlotOutput;
import ttr.core.gui.abstracts.ContainerTPC;
import ttr.core.gui.abstracts.FluidSlot;
import ttr.core.tile.electric.TEElectricalCanner;

/**
 * Created at 2016年12月21日 下午1:35:33
 * @author ueyudiud
 */
public class ContainerCanner extends ContainerTPC<TEElectricalCanner>
{
	public ContainerCanner(EntityPlayer player, TEElectricalCanner inventory)
	{
		super(player, inventory);
		addSlotToContainer(new Slot(inventory, 0, 35, 25));
		addSlotToContainer(new Slot(inventory, 1, 53, 25));
		addSlotToContainer(new Slot(inventory, 2, 35, 63));
		addSlotToContainer(new SlotOutput(inventory, 3, 107, 25));
		addSlotToContainer(new SlotOutput(inventory, 4, 125, 25));
		addSlotToContainer(new Slot(inventory, 5, 80, 63));
		addSlotToContainer(new FluidSlot(inventory, 0, 53, 63));
		addSlotToContainer(new FluidSlot(inventory, 1, 107, 63));
		this.locateRecipeInput = new TransLocation("input", 36, 39);
		this.locateRecipeOutput = new TransLocation("output", 39, 41);
	}
}