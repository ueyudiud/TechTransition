/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.gui.machine.eletrical;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import ttr.core.gui.abstracts.ContainerTP;
import ttr.core.gui.abstracts.SlotHolographic;
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
		super(player, inventory);
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
			SlotOption option = (SlotOption) slot;
			switch (option.optionID)
			{
			case 0 :
				this.inventoryTile.allowAutoOutputFluid = !this.inventoryTile.allowAutoOutputFluid;
				break;
			case 1 :
				this.inventoryTile.allowAutoOutputItem = !this.inventoryTile.allowAutoOutputItem;
				break;
			case 2 :
				int o = this.inventoryTile.autoOutputFace == null ? -1 : this.inventoryTile.autoOutputFace.ordinal();
				o++;
				if(this.inventoryTile.facing.ordinal() == o) o++;
				if(o == 6)
				{
					this.inventoryTile.autoOutputFace = null;
				}
				else
				{
					this.inventoryTile.autoOutputFace = EnumFacing.VALUES[o];
				}
				break;
			case 3 :
				this.inventoryTile.moveFully = !this.inventoryTile.moveFully;
				break;
			case 4 :
				this.inventoryTile.throwItemOut = !this.inventoryTile.throwItemOut;
				break;
			default:
				break;
			}
			this.inventoryTile.syncToPlayer(player);
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