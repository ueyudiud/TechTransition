/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.gui.machine.eletrical;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import ttr.api.gui.SlotOutput;
import ttr.core.gui.abstracts.ContainerTPC;
import ttr.core.tile.electric.TEElectricalGrinder;

/**
 * Created at 2016年12月21日 下午12:46:28
 * @author ueyudiud
 */
public class ContainerGrinder extends ContainerTPC<TEElectricalGrinder>
{
	public ContainerGrinder(EntityPlayer player, TEElectricalGrinder inventory)
	{
		super(player, inventory);
		addSlotToContainer(new Slot(inventory, 0, 53, 25));
		addSlotToContainer(new SlotOutput(inventory, 1, 107, 16));
		addSlotToContainer(new SlotOutput(inventory, 2, 107, 34));
		addSlotToContainer(new SlotOutput(inventory, 3, 125, 16));
		addSlotToContainer(new SlotOutput(inventory, 4, 125, 34));
		addSlotToContainer(new Slot(inventory, 5, 80, 63));
		this.locateRecipeInput = new TransLocation("input", 36);
		this.locateRecipeOutput = new TransLocation("output", 37, 40);
	}
}