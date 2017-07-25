/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.gui.machine.eletrical;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ttr.core.TTr;
import ttr.core.gui.abstracts.ContainerTP;
import ttr.core.gui.abstracts.SlotHolographic;
import ttr.core.network.PacketSlotConfig;
import ttr.core.tile.TEMachineRecipeMap;

/**
 * Created at 2016年12月21日 下午10:37:39
 * @author ueyudiud
 */
public class ContainerEleMachineConfig extends ContainerTP<TEMachineRecipeMap>
{
	private static class SlotOption extends SlotHolographic
	{
		int optionID;
		
		public SlotOption(int optionID, int aX, int aY)
		{
			super(null, optionID, aX, aY, false, false);
			this.optionID = optionID;
		}
		
		@Override
		public int getItemStackLimit(ItemStack stack)
		{
			return 0;
		}
		
		@Override
		public ItemStack getStack()
		{
			return null;
		}
		
		@Override
		public void putStack(ItemStack stack)
		{
			
		}
	}
	
	public ContainerEleMachineConfig(EntityPlayer player, TEMachineRecipeMap inventory)
	{
		super(player, inventory, 0, 0);
		addSlotToContainer(new SlotOption(0, 8, 63));
		addSlotToContainer(new SlotOption(1, 26, 63));
		addSlotToContainer(new SlotOption(2, 44, 63));
		addSlotToContainer(new SlotOption(3, 8, 45));
		addSlotToContainer(new SlotOption(4, 26, 45));
	}
	
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
	{
		Slot slot;
		if(slotId >= 0 && slotId < this.inventorySlots.size() && (slot = getSlot(slotId)) instanceof SlotOption)
		{
			if (player.worldObj.isRemote)
			{
				SlotOption option = (SlotOption) slot;
				TTr.network.sendToServer(new PacketSlotConfig(this.inventoryTile.getWorld(), this.inventoryTile.getPos(), option.optionID));
			}
			return null;
		}
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}
	
	@Override
	public boolean transferStackInSlot(Slot slot, ItemStack baseItemStack, ItemStack itemstack, int locate)
	{
		if(this.locatePlayerBag.conrrect(locate) && !this.locatePlayerHand.mergeItemStack(itemstack, false))
		{
			return true;
		}
		else if(this.locatePlayerHand.conrrect(locate) && !this.locatePlayerBag.mergeItemStack(itemstack, true))
		{
			return true;
		}
		return false;
	}
}