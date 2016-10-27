package ttr.core.gui.abstracts;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ttr.core.tile.TEMachineBase;

public abstract class ContainerTP<T extends TEMachineBase> extends ContainerWithTile<T>
{
	protected final TransLocation locatePlayerBag = new TransLocation("player_bag", 0, 27);
	protected final TransLocation locatePlayerHand = new TransLocation("player_hand", 27, 36);
	protected final TransLocation locatePlayer = new TransLocation("player", 0, 36);
	
	public ContainerTP(EntityPlayer player, T inventory)
	{
		super(player, inventory);
	}
	public ContainerTP(EntityPlayer player,
			T inventory, int playerInvUpos, int playerInvVpos)
	{
		super(player, inventory);
		int i;
		
		for (i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18 + playerInvUpos, 84 + i * 18 + playerInvVpos));
			}
		}
		
		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18 + playerInvUpos, 142 + playerInvVpos));
		}
	}
	
	protected void dropInventoryItem(IInventory inv, EntityPlayer player)
	{
		for(int i = 0; i < inv.getSizeInventory(); ++i)
		{
			ItemStack itemstack = inv.removeStackFromSlot(i);
			if(itemstack != null)
			{
				player.dropItem(itemstack, false);
			}
		}
	}
}