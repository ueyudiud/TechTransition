package ttr.core.gui.abstracts;

import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.gui.IFluidSyncableContainer;
import ttr.api.net.gui.PacketFluidUpdateAll;
import ttr.api.net.gui.PacketFluidUpdateSingle;
import ttr.core.TTr;
import ttr.core.tile.ITankSyncable;
import ttr.core.tile.TEMachineBase;

public abstract class ContainerWithTile<T extends TEMachineBase> extends ContainerBase implements IFluidSyncableContainer
{
	private int[] cachedFields;
	protected T inventoryTile;
	
	public ContainerWithTile(EntityPlayer aPlayer, T aTile)
	{
		super(aPlayer.inventory, aTile);
		this.inventoryTile = aTile;
		if(this.inventoryTile instanceof IInventory)
		{
			this.cachedFields = new int[((IInventory) this.inventoryTile).getFieldCount()];
		}
	}
	public ContainerWithTile(InventoryPlayer aPlayer, T aTile)
	{
		super(aPlayer, aTile);
		this.inventoryTile = aTile;
		if(this.inventoryTile instanceof IInventory)
		{
			this.cachedFields = new int[((IInventory) this.inventoryTile).getFieldCount()];
		}
	}
	
	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		if(listener instanceof EntityPlayer)
		{
			if(this.inventoryTile instanceof ITankSyncable)
			{
				TTr.network.sendToPlayer(new PacketFluidUpdateAll(this.windowId, (ITankSyncable) this.inventoryTile), (EntityPlayer) listener);
			}
		}
		if(this.inventoryTile instanceof IInventory)
		{
			listener.sendAllWindowProperties(this, this.inventoryTile);
		}
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		if(this.inventoryTile instanceof IInventory)
		{
			IInventory inventory = this.inventoryTile;
			
			for(int i = 0; i < this.cachedFields.length; ++i)
			{
				int newValue;
				if((newValue = inventory.getField(i)) != this.cachedFields[i])
				{
					for(IContainerListener listener : this.listeners)
					{
						listener.sendProgressBarUpdate(this, i, newValue);
					}
				}
				this.cachedFields[i] = newValue;
			}
		}
		if(this.inventoryTile instanceof ITankSyncable)
		{
			int[] array = new int[((ITankSyncable) this.inventoryTile).getTankSize()];
			int p = 0;
			for(int i = 0; i < this.fluidSlotList.size(); ++i)
			{
				FluidStack stack1 = this.fluidSlotList.get(i).getStack();
				FluidStack stack2 = this.fluidList.get(i);
				if((stack1 == null || stack2 == null) ? stack1 != stack2 :
					(stack1 != null && !stack1.isFluidStackIdentical(stack2)))
				{
					array[p++] = i;
				}
			}
			array = Arrays.copyOfRange(array, 0, p);
			for(IContainerListener listener : this.listeners)
			{
				if(listener instanceof EntityPlayer)
				{
					TTr.network.sendToPlayer(new PacketFluidUpdateSingle(this.windowId, (ITankSyncable) this.inventoryTile, array), (EntityPlayer) listener);
				}
			}
			for(int i : array)
			{
				for(i = 0; i < this.fluidSlotList.size(); ++i)
				{
					FluidStack stack1 = this.fluidSlotList.get(i).getStack();
					this.fluidList.set(i, stack1 != null ? stack1.copy() : null);
				}
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		if(this.inventoryTile instanceof IInventory)
		{
			((IInventory) this.inventoryTile).setField(id, data);
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return super.canInteractWith(player) && this.inventoryTile.getWorld().getTileEntity(this.inventoryTile.getPos()) == this.inventoryTile;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setFluid(int idx, FluidStack stack)
	{
		if(this.inventoryTile instanceof ITankSyncable)
		{
			((ITankSyncable) this.inventoryTile).setFluidStackToTank(idx, stack);
		}
	}
}