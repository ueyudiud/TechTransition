package ttr.core.gui.boiler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ttr.api.fuel.FuelHandler;
import ttr.api.gui.SlotOutput;
import ttr.core.tile.boiler.TECoalBoiler;

public class ContainerCoalBoiler extends ContainerBoiler<TECoalBoiler>
{
	private TransLocation locateFuel = new TransLocation("fuel", 38);
	private TransLocation locateFuelOutput = new TransLocation("fuel_output", 39);
	
	public ContainerCoalBoiler(EntityPlayer player, TECoalBoiler inventory)
	{
		super(player, inventory);
		addSlotToContainer(new Slot(inventory, 2, 116, 62));
		addSlotToContainer(new SlotOutput(inventory, 3, 116, 26));
	}

	@Override
	public boolean transferStackInSlot(Slot slot, ItemStack baseItemStack, ItemStack itemstack, int locate)
	{
		if(locateFuelOutput.conrrect(locate))
		{
			if(!locatePlayer.mergeItemStack(itemstack, true))
				return true;
		}
		else if(locateRecipeInput.conrrect(locate) || locateRecipeOutput.conrrect(locate))
		{
			if(!locatePlayer.mergeItemStack(itemstack, true))
				return true;
		}
		else if(locateFuel.conrrect(locate))
		{
			if(!locatePlayer.mergeItemStack(itemstack, true))
				return true;
		}
		else if(matchFluid(itemstack) &&
				!locateRecipeInput.mergeItemStack(itemstack, false))
			return true;
		else if(FuelHandler.isItemFuel(itemstack, true) &&
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