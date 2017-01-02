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
		//		addFacing("input", EnumFacing.WEST);
		addFacing("output", EnumFacing.EAST);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.inventory.readFromNBT(nbt);
		this.allowInput = nbt.getBoolean("allowInput");
		this.allowOutput = nbt.getBoolean("allowOutput");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		this.inventory.writeToNBT(nbt);
		nbt.setBoolean("allowInput", this.allowInput);
		nbt.setBoolean("allowOutput", this.allowOutput);
		return super.writeToNBT(nbt);
	}
	
	@Override
	protected void initServer()
	{
		this.inventory.setTile(this);
		super.initServer();
	}
	
	@Override
	public void onLoad()
	{
		super.onLoad();
		if(this.worldObj.isRemote)
		{
			this.inventory.setTile(this);
		}
	}
	
	@Override
	public int getSizeInventory()
	{
		return this.inventory.getSizeInventory();
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return this.inventory.getInventoryStackLimit();
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.inventory.getStackInSlot(index);
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return this.inventory.removeStackFromSlot(index);
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return this.inventory.decrStackSize(index, count, true);
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.inventory.isUseableByPlayer(player);
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.inventory.setInventorySlotContents(index, stack);
	}
	
	@Override
	public String getName()
	{
		return this.inventory.getName();
	}
	
	@Override
	public ITextComponent getDisplayName()
	{
		return this.inventory.getDisplayName();
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
		return this.allowInput && canInsertItemFromSlot(index, stack);
	}
	
	protected boolean canInsertItemFromSlot(int index, ItemStack stack)
	{
		return false;
	}
	
	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		return this.allowOutput && canExtractItemFromSlot(index, stack);
	}
	
	protected boolean canExtractItemFromSlot(int index, ItemStack stack)
	{
		return false;
	}
}