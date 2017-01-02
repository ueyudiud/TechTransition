/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.gui.machine.eletrical;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import ttr.api.gui.SlotOutput;
import ttr.core.gui.abstracts.ContainerTPC;
import ttr.core.tile.electric.TEElectricalAlloySmelter;

/**
 * Created at 2016年12月21日 下午2:46:58
 * @author ueyudiud
 */
public class ContainerAlloySmelter extends ContainerTPC<TEElectricalAlloySmelter>
{
	public ContainerAlloySmelter(EntityPlayer player, TEElectricalAlloySmelter inventory)
	{
		super(player, inventory);
		addSlotToContainer(new Slot(inventory, 0, 35, 16));
		addSlotToContainer(new Slot(inventory, 1, 53, 16));
		addSlotToContainer(new Slot(inventory, 2, 35, 34));
		addSlotToContainer(new Slot(inventory, 3, 53, 34));
		addSlotToContainer(new SlotOutput(inventory, 4, 107, 25));
		addSlotToContainer(new Slot(inventory, 5, 80, 63));
		this.locateRecipeInput = new TransLocation("input", 36, 40);
		this.locateRecipeOutput = new TransLocation("output", 40);
	}
}