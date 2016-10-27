package ttr.api.inventory;

import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import ttr.api.stack.AbstractStack;
import ttr.api.util.LanguageManager;

public class Inventory implements IItemHandlerModifiable, IInventory
{
	public enum FDType
	{
		F(true, false),
		D(false, true),
		FD(true, true);
		
		boolean d;
		boolean f;
		
		FDType(boolean doFill, boolean doDrain)
		{
			d = doDrain;
			f = doFill;
		}
	}
	
	private static final TileEntity INSTANCE = new TileEntity()
	{
		
	};
	
	private TileEntity tile = INSTANCE;
	
	private final String name;
	public final ItemStack[] stacks;
	private final int limit;
	
	public Inventory(int size, String name, int stackLimit)
	{
		this.name = name;
		stacks = new ItemStack[size];
		limit = stackLimit;
	}
	
	public void readFromNBT(NBTTagCompound nbt)
	{
		readFromNBT(stacks, nbt, "items");
	}
	
	public static void readFromNBT(ItemStack[] stacks, NBTTagCompound nbt, String tag)
	{
		NBTTagList nbttaglist = nbt.getTagList(tag, 10);
		for (int j = 0; j < nbttaglist.tagCount(); j++)
		{
			NBTTagCompound slot = nbttaglist.getCompoundTagAt(j);
			int index = slot.getByte("slot");
			if ((index >= 0) && (index < stacks.length))
			{
				stacks[index] = ItemStack.loadItemStackFromNBT(slot);
			}
		}
	}
	
	public void writeToNBT(NBTTagCompound data)
	{
		writeToNBT(stacks, data, "items");
	}
	
	public static void writeToNBT(ItemStack[] stacks, NBTTagCompound data, String tag)
	{
		NBTTagList slots = new NBTTagList();
		for (byte index = 0; index < stacks.length; index = (byte)(index + 1))
		{
			if ((stacks[index] != null) && (stacks[index].stackSize > 0))
			{
				NBTTagCompound slot = new NBTTagCompound();
				slot.setByte("slot", index);
				stacks[index].writeToNBT(slot);
				slots.appendTag(slot);
			}
		}
		data.setTag(tag, slots);
	}
	
	@Override
	public int getSizeInventory()
	{
		return stacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return stacks[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int size)
	{
		return decrStackSize(i, size, true);
	}

	public ItemStack decrStackSize(int i, int size, boolean process)
	{
		if(size <= 0) return null;
		if(stacks[i] == null) return null;
		ItemStack stack = stacks[i];
		if(stack.stackSize <= size)
		{
			if(process)
			{
				stacks[i] = null;
			}
			return stack;
		}
		else
		{
			if(process)
				return stack.splitStack(size);
			else
			{
				ItemStack ret = stack.copy();
				ret.stackSize = size;
				return ret;
			}
		}
	}
	
	public int addStack(int i, ItemStack stack, boolean process)
	{
		if(stack == null || stack.stackSize == 0) return 0;
		int size = Math.min(limit, stack.getMaxStackSize());
		if(stacks[i] == null)
		{
			size = Math.min(stack.stackSize, size);
			if(process)
			{
				ItemStack stack1 = stack.copy();
				stack1.stackSize = size;
				stacks[i] = stack1;
			}
			return size;
		}
		else if(stacks[i].isItemEqual(stack) && ItemStack.areItemStackTagsEqual(stacks[i], stack))
		{
			size = Math.min(stack.stackSize, size - stacks[i].stackSize);
			if(process)
			{
				stacks[i].stackSize += size;
			}
			return size;
		}
		return 0;
	}
	
	public boolean addStack(int i, AbstractStack stack, boolean process)
	{
		if(stack == null || stack.instance() == null) return true;
		if(stacks[i] == null)
		{
			ItemStack stack2 = stack.instance().copy();
			if(addStack(i, stack2, false) == stack2.stackSize)
			{
				if(process)
				{
					addStack(i, stack2, true);
				}
				return true;
			}
			return false;
		}
		else if(stack.similar(stacks[i]))
		{
			int size = Math.min(limit, stacks[i].getMaxStackSize());
			int size1 = stack.size(stacks[i]);
			if(size1 > size - stacks[i].stackSize)
				return false;
			if(process)
			{
				stacks[i].stackSize += size1;
			}
			return true;
		}
		return false;
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		ItemStack stack = stacks[index];
		stacks[index] = null;
		return stack;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack)
	{
		stacks[i] = ItemStack.copyItemStack(stack);
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentString(LanguageManager.translateToLocal(getName()));
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return limit;
	}

	@Override
	public void markDirty()
	{
		tile.markDirty();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return tile == INSTANCE ? true :
			player.getDistanceSq(tile.getPos()) <= 64;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack)
	{
		return true;
	}
	
	public void setTile(TileEntity tile)
	{
		this.tile = tile;
	}
	
	public static boolean matchFluidInput(IFluidTank tank, FluidStack fluid)
	{
		if(fluid == null) return true;
		if(tank.getFluidAmount() == 0) return false;
		return !fluid.isFluidEqual(tank.getFluid()) ? false : tank.getFluid().containsFluid(fluid);
	}
	
	public boolean fillOrDrainInventoryTank(IFluidTank tank, int inputSlot, int outputSlot)
	{
		return fillOrDrainInventoryTank(tank, inputSlot, outputSlot, FDType.FD);
	}
	public boolean fillOrDrainInventoryTank(IFluidTank tank, int inputSlot, int outputSlot, FDType type)
	{
		ItemStack input = stacks[inputSlot];
		if(input == null) return true;
		input = input.copy();
		input.stackSize = 1;
		if(input.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null))
		{
			IFluidHandler handler = input.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
			if(type.d)
			{
				FluidStack stack = handler.drain(Integer.MAX_VALUE, false);
				if(stack != null)
				{
					int amt = tank.fill(stack, false);
					if(amt > 0)
					{
						FluidStack stack2 = handler.drain(amt, false);
						if(stack2 != null)
						{
							stack2 = handler.drain(tank.fill(stack, true), true);
							if(addStack(outputSlot, input, true) != 0)
							{
								stacks[inputSlot].stackSize--;
								if(stacks[inputSlot].stackSize == 0)
								{
									stacks[inputSlot] = null;
								}
								return true;
							}
						}
					}
				}
			}
			if(type.f)
			{
				FluidStack stack = tank.getFluid();
				if(stack != null)
				{
					stack = stack.copy();
					int amt = handler.fill(stack, true);
					if(amt > 0 && addStack(outputSlot, input, true) != 0)
					{
						tank.drain(amt, true);
						stacks[inputSlot].stackSize--;
						if(stacks[inputSlot].stackSize == 0)
						{
							stacks[inputSlot] = null;
						}
						return true;
					}
				}
			}
		}
		return false;
	}

	//	public void chargeOrReleaseEnergy(ITileEleCharger charger, int slot)
	//	{
	//		chargeOrReleaseEnergy(charger, slot, FDType.D);
	//	}
	//	public void chargeOrReleaseEnergy(ITileEleCharger charger, int slot, FDType type)
	//	{
	//		if(stacks[slot] == null) return;
	//		try
	//		{
	//			double amount;
	//			if(type.f)
	//			{
	//				amount = ElectricItem.manager.charge(stacks[slot], charger.getOfferedEnergy(), charger.getChargerTier(), false, false);
	//				if(amount > 0)
	//				{
	//					charger.drawEnergy(amount);
	//					return;
	//				}
	//			}
	//			else if(type.d)
	//			{
	//				amount = ElectricItem.manager.discharge(stacks[slot], charger.getDemandedEnergy(), charger.getChargerTier(), false, true, false);
	//				if(amount > 0)
	//				{
	//					charger.injectEnergy(amount, 1D);
	//					return;
	//				}
	//				amount = Info.itemEnergy.getEnergyValue(stacks[slot]);
	//				if(amount > 0 && charger.getDemandedEnergy() >= amount)
	//				{
	//					decrStackSize(slot, 1);
	//					charger.injectEnergy(amount, 1D);
	//					return;
	//				}
	//			}
	//		}
	//		catch (Exception e)
	//		{
	//			if(Config.debug)
	//			{
	//				TTrLog.warn("Catching an exception during charging or releasing energy from item.", e);
	//			}
	//		}
	//	}
	
	@Override
	public int getSlots()
	{
		return getSizeInventory();
	}
	
	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
	{
		int size = addStack(slot, stack, !simulate);
		if(size == stack.stackSize) return null;
		if(!simulate)
		{
			stack = stack.copy();
			stack.stackSize -= size;
		}
		return stack;
	}
	
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		return decrStackSize(slot, amount, !simulate);
	}
	
	@Override
	public void openInventory(EntityPlayer player)
	{

	}
	
	@Override
	public void closeInventory(EntityPlayer player)
	{

	}
	
	@Override
	public int getField(int id)
	{
		return 0;
	}
	
	@Override
	public void setField(int id, int value)
	{

	}
	
	@Override
	public int getFieldCount()
	{
		return 0;
	}
	
	@Override
	public void clear()
	{
		Arrays.fill(stacks, null);
	}
	
	@Override
	public void setStackInSlot(int slot, ItemStack stack)
	{
		stacks[slot] = ItemStack.copyItemStack(stack);
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}
}