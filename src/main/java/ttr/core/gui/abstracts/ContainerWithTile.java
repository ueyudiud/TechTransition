package ttr.core.gui.abstracts;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

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
		super(aPlayer.inventory);
		inventoryTile = aTile;
		if(inventoryTile instanceof IInventory)
		{
			cachedFields = new int[((IInventory) inventoryTile).getFieldCount()];
		}
	}
	public ContainerWithTile(InventoryPlayer aPlayer, T aTile)
	{
		super(aPlayer);
		inventoryTile = aTile;
		if(inventoryTile instanceof IInventory)
		{
			cachedFields = new int[((IInventory) inventoryTile).getFieldCount()];
		}
	}

	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		if(listener instanceof EntityPlayer)
		{
			if(inventoryTile instanceof ITankSyncable)
			{
				TTr.network.sendToPlayer(new PacketFluidUpdateAll(windowId, (ITankSyncable) inventoryTile), (EntityPlayer) listener);
			}
		}
		if(inventoryTile instanceof IInventory)
		{
			listener.sendAllWindowProperties(this, inventoryTile);
		}
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		if(inventoryTile instanceof IInventory)
		{
			IInventory inventory = inventoryTile;
			
			for(int i = 0; i < cachedFields.length; ++i)
			{
				int newValue;
				if((newValue = inventory.getField(i)) != cachedFields[i])
				{
					for(IContainerListener listener : listeners)
					{
						listener.sendProgressBarUpdate(this, i, newValue);
					}
				}
				cachedFields[i] = newValue;
			}
		}
		if(inventoryTile instanceof ITankSyncable)
		{
			IntegerArray array = new IntegerArray();
			for(int i = 0; i < fluidSlotList.size(); ++i)
			{
				FluidStack stack1 = fluidSlotList.get(i).getStack();
				FluidStack stack2 = fluidList.get(i);
				if(!((stack1 == null && stack2 == null) || (stack1 != null && stack1.isFluidStackIdentical(stack2))))
				{
					array.add(i);
					break;
				}
			}
			for(IContainerListener listener : listeners)
			{
				if(listener instanceof EntityPlayer)
				{
					TTr.network.sendToPlayer(new PacketFluidUpdateSingle(windowId, (ITankSyncable) inventoryTile, array.toIntArray()), (EntityPlayer) listener);
				}
			}
			for(int i : array.toIntArray())
			{
				for(i = 0; i < fluidSlotList.size(); ++i)
				{
					FluidStack stack1 = fluidSlotList.get(i).getStack();
					fluidList.set(i, stack1 != null ? stack1.copy() : null);
				}
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		if(inventoryTile instanceof IInventory)
		{
			((IInventory) inventoryTile).setField(id, data);
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return super.canInteractWith(player) && inventoryTile.getWorld().getTileEntity(inventoryTile.getPos()) == inventoryTile;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setFluid(int idx, FluidStack stack)
	{
		if(inventoryTile instanceof ITankSyncable)
		{
			((ITankSyncable) inventoryTile).setFluidStackToTank(idx, stack);
		}
	}
}