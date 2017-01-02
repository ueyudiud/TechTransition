/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.gui.machine.steam;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import ttr.api.gui.SlotOutput;
import ttr.core.gui.abstracts.ContainerTPC;
import ttr.core.tile.machine.steam.TEBronzeBlastFurnace;

/**
 * Created at 2016年12月20日 下午2:24:37
 * @author ueyudiud
 */
public class ContainerBronzeBlastFurnace extends ContainerTPC<TEBronzeBlastFurnace>
{
	public ContainerBronzeBlastFurnace(EntityPlayer player, TEBronzeBlastFurnace inventory)
	{
		super(player, inventory);
		addSlotToContainer(new Slot(inventory, 0, 34, 16));
		addSlotToContainer(new Slot(inventory, 1, 34, 34));
		addSlotToContainer(new SlotOutput(inventory, 2, 86, 25));
		addSlotToContainer(new SlotOutput(inventory, 3, 104, 25));
		this.locateRecipeInput = new TransLocation("input", 36, 38);
		this.locateRecipeOutput = new TransLocation("output", 38, 40);
	}
}