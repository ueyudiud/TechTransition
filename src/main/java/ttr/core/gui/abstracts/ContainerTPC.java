package ttr.core.gui.abstracts;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ttr.api.inventory.Inventory;
import ttr.core.tile.TEMachineBase;

public class ContainerTPC<T extends TEMachineBase> extends ContainerTP<T>
{
	protected static final IInventory INVENTORY = new Inventory(1, "", 64);
	
	protected TransLocation locateRecipeInput;
	protected TransLocation locateRecipeOutput;

	public ContainerTPC(EntityPlayer player, T inventory)
	{
		this(player, inventory, 0, 0);
	}
	public ContainerTPC(EntityPlayer player, T inventory,
			int playerInvUpos, int playerInvVpos)
	{
		super(player, inventory, playerInvUpos, playerInvVpos);
	}

	public void onInputChanged(IInventory inv, int slotId)
	{
		
	}
	
	public void onOutputChanged(IInventory inv, int slotId)
	{
		
	}
	
	@Override
	public boolean transferStackInSlot(Slot slot, ItemStack baseItemStack, ItemStack itemstack, int locate)
	{
		if(this.locateRecipeInput.conrrect(locate) || this.locateRecipeOutput.conrrect(locate))
		{
			if(!locatePlayer.mergeItemStack(itemstack, true))
				return true;
		}
		else if(locatePlayerBag.conrrect(locate))
		{
			if(!this.locateRecipeInput.mergeItemStack(itemstack, false))
			{
				if(!locatePlayerHand.mergeItemStack(itemstack, false))
					return true;
				return true;
			}
		}
		else if(locatePlayerHand.conrrect(locate))
		{
			if(!this.locateRecipeInput.mergeItemStack(itemstack, false))
			{
				if(!locatePlayerBag.mergeItemStack(itemstack, true))
					return true;
				return true;
			}
		}
		return false;
	}
}