package ttr.core.gui.abstracts;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import ttr.core.tile.IToolClickHandler;

public abstract class ContainerBase extends Container
{
	public InventoryPlayer player;
	public IInventory inv;
	protected int[] lastNumbers;
	public List<FluidSlot> fluidSlotList;
	public List<FluidStack> fluidList;
	
	public ContainerBase(InventoryPlayer player, IInventory inventory)
	{
		this.player = player;
		inv = inventory;
		//		if(inv instanceof IContainerNetworkUpdate)
		//			lastNumbers = new int[((IContainerNetworkUpdate) inv).getProgressSize()];
		fluidList = new ArrayList<FluidStack>();
		fluidSlotList = new ArrayList<FluidSlot>();
	}
	public ContainerBase(IInventory inventory)
	{
		inv = inventory;
		//		if(inv instanceof IContainerNetworkUpdate)
		//			lastNumbers = new int[((IContainerNetworkUpdate) inv).getProgressSize()];
		fluidList = new ArrayList<FluidStack>();
		fluidSlotList = new ArrayList<FluidSlot>();
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return inv.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i)
	{
		ItemStack itemstack = null;
		Slot slot = inventorySlots.get(i);
		
		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if(transferStackInSlot(slot, itemstack, itemstack1, i))
			{
				if (itemstack1.stackSize == 0)
				{
					slot.putStack((ItemStack) null);
				}
				else
				{
					slot.onSlotChanged();
				}
				return null;
			}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack) null);
			}
			else
			{
				slot.onSlotChanged();
			}
			
			if (itemstack1.stackSize == itemstack.stackSize)
				return null;
		}

		return itemstack;
	}
	
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
	{
		if(slotId >= 0 && getSlot(slotId) instanceof SlotTool)
		{
			IInventory inv = getSlot(slotId).inventory;
			if(inv instanceof IToolClickHandler)
			{
				ItemStack tStack = player.inventory.getItemStack();
				if (tStack != null)
				{
					ItemStack stack = ((IToolClickHandler) inv).onToolClick(tStack, player, getSlot(slotId).getSlotIndex());
					player.inventory.setItemStack(stack.stackSize <= 0 ? null : stack);
				}
				return null;
			}
		}
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}

	public abstract boolean transferStackInSlot(Slot slot, ItemStack baseItemStack, ItemStack itemstack, int locate);
	
	protected FluidSlot addSlotToContainer(FluidSlot slot)
	{
		slot.slotNumber = fluidSlotList.size();
		fluidSlotList.add(slot);
		fluidList.add((FluidStack) null);
		return slot;
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int start, int end, boolean flag)
	{
		boolean flag1 = false;
		int k = flag ? end - 1 : start;
		
		Slot slot;
		ItemStack itemstack1;
		
		if (stack.isStackable())
		{
			while (stack.stackSize > 0 && (!flag && k < end || flag && k >= start))
			{
				slot = inventorySlots.get(k);
				itemstack1 = slot.getStack();
				
				if (slot.isItemValid(stack))
				{
					if (itemstack1 != null && itemstack1.getItem() == stack.getItem() && (stack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack, itemstack1))
					{
						int l = itemstack1.stackSize + stack.stackSize;
						
						if (l <= stack.getMaxStackSize())
						{
							stack.stackSize = 0;
							itemstack1.stackSize = l;
							slot.onSlotChanged();
							flag1 = true;
						}
						else if (itemstack1.stackSize < stack.getMaxStackSize())
						{
							stack.stackSize -= stack.getMaxStackSize() - itemstack1.stackSize;
							itemstack1.stackSize = stack.getMaxStackSize();
							slot.onSlotChanged();
							flag1 = true;
						}
					}
				}
				
				if (flag)
				{
					--k;
				}
				else
				{
					++k;
				}
			}
		}
		
		if (stack.stackSize > 0)
		{
			if (flag)
			{
				k = end - 1;
			}
			else
			{
				k = start;
			}
			
			while (!flag && k < end || flag && k >= start)
			{
				slot = inventorySlots.get(k);
				itemstack1 = slot.getStack();
				
				if (slot.isItemValid(stack) && itemstack1 == null)
				{
					slot.putStack(stack.copy());
					slot.onSlotChanged();
					stack.stackSize = 0;
					flag1 = true;
					break;
				}
				
				if (flag)
				{
					--k;
				}
				else
				{
					++k;
				}
			}
		}
		
		return flag1;
	}

	public class TransLocation
	{
		String name;
		int startId;
		int endId;
		
		public TransLocation(String name, int id)
		{
			this.name = name;
			startId = id;
			endId = id + 1;
		}
		public TransLocation(String name, int startId, int endId)
		{
			this.name = name;
			this.startId = startId;
			this.endId = endId;
		}

		public String getName()
		{
			return name;
		}

		public boolean conrrect(int i)
		{
			return startId < 0 || endId < 0 ? false : i >= startId && i < endId;
		}

		public boolean mergeItemStack(ItemStack itemstack, boolean isFront)
		{
			return startId < 0 || endId < 0 ? true : ContainerBase.this.mergeItemStack(itemstack, startId, endId, isFront);
		}
	}
}