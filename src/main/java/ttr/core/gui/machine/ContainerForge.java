package ttr.core.gui.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ttr.api.fuel.FuelHandler;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.core.gui.abstracts.ContainerTPC;
import ttr.core.tile.machine.TEForge;

public class ContainerForge extends ContainerTPC<TEForge>
{
	private TransLocation locateFuel = new TransLocation("fuel", 42);
	
	public ContainerForge(EntityPlayer player, TEForge inventory)
	{
		super(player, inventory);
		addSlotToContainer(new Slot(inventory, 0, 71, 17));
		addSlotToContainer(new Slot(inventory, 1, 71, 35));
		addSlotToContainer(new Slot(inventory, 2, 71, 53));
		addSlotToContainer(new Slot(inventory, 3, 125, 17));
		addSlotToContainer(new Slot(inventory, 4, 125, 35));
		addSlotToContainer(new Slot(inventory, 5, 125, 53));
		addSlotToContainer(new Slot(inventory, 6, 26, 53));
		locateRecipeInput = new TransLocation("input", 36, 39);
		locateRecipeOutput = new TransLocation("output", 39, 42);
	}
	
	@Override
	public boolean transferStackInSlot(Slot slot, ItemStack baseItemStack, ItemStack itemstack, int locate)
	{
		if(locateRecipeInput.conrrect(locate) || locateRecipeOutput.conrrect(locate))
		{
			if(!locatePlayer.mergeItemStack(itemstack, true))
				return true;
		}
		else if(locateFuel .conrrect(locate))
		{
			if(!locatePlayer.mergeItemStack(itemstack, true))
				return true;
		}
		else if(TemplateRecipeMap.FORGE.containInput(itemstack) &&
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