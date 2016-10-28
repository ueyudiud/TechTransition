package ttr.core.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import ttr.api.inventory.Inventory;

public class TEMachineInventory extends TEMachineBase implements ISidedInventory
{
	public Inventory inventory;

	public boolean allowInput = true;
	public boolean allowOutput = true;
	
	public TEMachineInventory()
	{
		addFacing("input", EnumFacing.WEST);
		addFacing("output", EnumFacing.EAST);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		inventory.readFromNBT(nbt);
		allowInput = nbt.getBoolean("allowInput");
		allowOutput = nbt.getBoolean("allowOutput");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		inventory.writeToNBT(nbt);
		nbt.setBoolean("allowInput", allowInput);
		nbt.setBoolean("allowOutput", allowOutput);
		return super.writeToNBT(nbt);
	}

	@Override
	protected void initServer()
	{
		inventory.setTile(this);
		super.initServer();
	}
	
	@Override
	public void onLoad()
	{
		super.onLoad();
		if(worldObj.isRemote)
		{
			inventory.setTile(this);
		}
	}

	@Override
	public int getSizeInventory()
	{
		return inventory.getSizeInventory();
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return inventory.getInventoryStackLimit();
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return inventory.getStackInSlot(index);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return inventory.removeStackFromSlot(index);
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return inventory.decrStackSize(index, count, true);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return inventory.isUseableByPlayer(player);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		inventory.setInventorySlotContents(index, stack);
	}
	
	@Override
	public String getName()
	{
		return inventory.getName();
	}
	
	@Override
	public ITextComponent getDisplayName()
	{
		return inventory.getDisplayName();
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side.ordinal() == getFacingID("input") ? getInputSlots() : side.ordinal() == getFacingID("output") ? getOutputSlots() : new int[0];
	}
	
	protected int[] getInputSlots()
	{
		return new int[0];
	}
	
	protected int[] getOutputSlots()
	{
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction)
	{
		return allowInput && canInsertItemFromSlot(index, stack);
	}
	
	protected boolean canInsertItemFromSlot(int index, ItemStack stack)
	{
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		return allowOutput && canExtractItemFromSlot(index, stack);
	}
	
	protected boolean canExtractItemFromSlot(int index, ItemStack stack)
	{
		return false;
	}
}