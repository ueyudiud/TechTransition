/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.gui.machine.eletrical;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import ttr.api.gui.SlotOutput;
import ttr.core.gui.abstracts.ContainerTPC;
import ttr.core.gui.abstracts.FluidSlot;
import ttr.core.tile.electric.TEElectricalPyrolysisor;

/**
 * Created at 2016年12月21日 下午1:35:33
 * @author ueyudiud
 */
public class ContainerPyrolysisor extends ContainerTPC<TEElectricalPyrolysisor>
{
	public ContainerPyrolysisor(EntityPlayer player, TEElectricalPyrolysisor inventory)
	{
		super(player, inventory);
		addSlotToContainer(new Slot(inventory, 0, 53, 25));
		addSlotToContainer(new SlotOutput(inventory, 1, 107, 25));
		addSlotToContainer(new Slot(inventory, 2, 80, 63));
		addSlotToContainer(new FluidSlot(inventory, 0, 53, 63));
		addSlotToContainer(new FluidSlot(inventory, 1, 107, 63));
		this.locateRecipeInput = new TransLocation("input", 36);
		this.locateRecipeOutput = new TransLocation("output", 37);
	}
}