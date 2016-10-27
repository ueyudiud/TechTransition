package ttr.core.gui.machine.furnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ttr.api.fuel.FuelHandler;
import ttr.api.gui.SlotOutput;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.core.gui.abstracts.ContainerTPC;
import ttr.core.tile.machine.furnace.TEFurnace;

public class ContainerFurnace extends ContainerTPC<TEFurnace>
{
	private TransLocation locateFuel = new TransLocation("fuel", 38);

	public ContainerFurnace(EntityPlayer player, TEFurnace inventory)
	{
		super(player, inventory);
		addSlotToContainer(new Slot(inventory, 0, 56, 17));
		addSlotToContainer(new SlotOutput(inventory, 1, 116, 35));
		addSlotToContainer(new Slot(inventory, 2, 56, 53));
		locateRecipeInput = new TransLocation("input", 36);
		locateRecipeOutput = new TransLocation("output", 37);
	}

	@Override
	public boolean transferStackInSlot(Slot slot, ItemStack baseItemStack, ItemStack itemstack, int locate)
	{
		if(locateRecipeInput.conrrect(locate) || locateRecipeOutput.conrrect(locate))
		{
			if(!locatePlayer.mergeItemStack(itemstack, true))
				return true;
		}
		else if(locateFuel.conrrect(locate))
		{
			if(!locatePlayer.mergeItemStack(itemstack, true))
				return true;
		}
		else if(TemplateRecipeMap.SMELTING.containInput(itemstack) &&
				!locateRecipeInput.mergeItemStack(itemstack, false))
			return true;
		else if(FuelHandler.isItemFuel(itemstack) &&
				!locateFuel.mergeItemStack(itemstack, false))
			return true;
		else if(locatePlayerBag.conrrect(locate))
		{
			if(!locatePlayerHand.mergeItemStack(itemstack, false))
				return true;
		}
		else if(locatePlayerHand.conrrect(locate))
		{
			if(!locatePlayerBag.mergeItemStack(itemstack, true))
				return true;
		}
		return false;
	}
}