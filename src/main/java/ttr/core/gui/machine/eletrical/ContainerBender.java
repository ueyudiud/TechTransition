/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.gui.machine.eletrical;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import ttr.api.gui.SlotOutput;
import ttr.core.gui.abstracts.ContainerTPC;
import ttr.core.tile.electric.TEElectricalBender;

/**
 * Created at 2016年12月21日 下午1:35:33
 * @author ueyudiud
 */
public class ContainerBender extends ContainerTPC<TEElectricalBender>
{
	public ContainerBender(EntityPlayer player, TEElectricalBender inventory)
	{
		super(player, inventory);
		addSlotToContainer(new Slot(inventory, 0, 35, 25));
		addSlotToContainer(new Slot(inventory, 1, 53, 25));
		addSlotToContainer(new SlotOutput(inventory, 2, 107, 25));
		addSlotToContainer(new Slot(inventory, 3, 80, 63));
		this.locateRecipeInput = new TransLocation("input", 36, 38);
		this.locateRecipeOutput = new TransLocation("output", 38);
	}
}