package ttr.core.tile;

import java.io.IOException;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.inventory.Inventory;
import ttr.api.recipe.IRecipeMap;
import ttr.api.recipe.TemplateRecipeMap.TemplateRecipe;

public abstract class TEMachineRecipeMap extends TEMachineInventory
{
	public static final int MachineEnabled = 3;
	public static final int Working = 4;
	public static final int OutPuting = 5;
	public static final int PowerNotEnough = 6;
	
	public long lastEnergyInput;

	public ItemStack[] itemInputs;
	public ItemStack[] itemOutputs;
	public FluidTank[] fluidInputTanks;
	public FluidTank[] fluidOutputTanks;
	public ItemStack[] outputCacheItems;
	public FluidStack[] outputCacheFluids;
	public int[] FACE_INPUT;
	public int[] FACE_OUTPUT;
	public boolean allowAutoOutput = false;

	public long duration;
	public long maxDuration = Long.MAX_VALUE;
	public long minPower;
	
	public TEMachineRecipeMap(int itemInputSize, int itemOutputSize, int fluidInputSize, int fluidOutputSize)
	{
		itemInputs = new ItemStack[itemInputSize];
		itemOutputs = new ItemStack[itemOutputSize];
		fluidInputTanks = new FluidTank[fluidInputSize];
		fluidOutputTanks = new FluidTank[fluidOutputSize];
		FACE_INPUT = new int[itemInputSize];
		FACE_OUTPUT = new int[itemOutputSize];
		int i = 0;
		for(; i < itemInputSize; ++i)
		{
			FACE_INPUT[i] = i;
		}
		for(; i < itemInputSize + itemOutputSize; ++i)
		{
			FACE_OUTPUT[i - itemInputSize] = i;
		}
		enable(MachineEnabled);
	}

	@Override
	protected void updateServer()
	{
		lastEnergyInput = getEnergyInput();
		if (is(MachineEnabled) && canUseMachine())
		{
			checkRecipe();
			onWorking();
		}
		super.updateServer();
	}
	
	protected boolean canUseMachine()
	{
		return true;
	}
	
	@Override
	public void writeToDescription(PacketBuffer buffer) throws IOException
	{
		super.writeToDescription(buffer);
		boolean bool = is(Working);
		buffer.writeBoolean(bool);
		if(bool)
		{
			buffer.writeLong(maxDuration).writeLong(duration);
		}
	}
	
	@Override
	public void readFromDescription1(PacketBuffer buffer) throws IOException
	{
		super.readFromDescription1(buffer);
		if(buffer.readBoolean())
		{
			maxDuration = buffer.readLong();
			duration = buffer.readLong();
		}
	}
	
	protected void checkRecipe()
	{
		if(is(Working))
		{
			if(duration >= maxDuration)
			{
				disable(Working);
				enable(OutPuting);
				duration = maxDuration;
			}
		}
		if(is(OutPuting))
		{
			if(!matchRecipeOutput())
				return;
			if(outputCacheItems != null)
			{
				for(int i = 0; i < outputCacheItems.length; ++i)
				{
					if(addStack(itemOutputs, i, outputCacheItems[i], false, getInventoryStackLimit()) != outputCacheItems[i].stackSize)
						return;
				}
			}
			if(outputCacheFluids != null)
			{
				for(int i = 0; i < outputCacheFluids.length; ++i)
				{
					if(fluidOutputTanks[i].fill(outputCacheFluids[i], false) != outputCacheFluids[i].amount)
						return;
				}
			}
			if(outputCacheItems != null)
			{
				for(int i = 0; i < outputCacheItems.length; ++i)
				{
					addStack(itemOutputs, i, outputCacheItems[i], true, getInventoryStackLimit());
				}
			}
			if(outputCacheFluids != null)
			{
				for(int i = 0; i < outputCacheFluids.length; ++i)
				{
					fluidOutputTanks[i].fill(outputCacheFluids[i], true);
				}
			}
			initRecipeOutput();
			disable(OutPuting);
		}
		if(!is(Working) && !is(OutPuting))
		{
			FluidStack[] fluidInputs = new FluidStack[fluidInputTanks.length];
			for(int i = 0; i < fluidInputs.length; ++i)
			{
				fluidInputs[i] = fluidInputTanks[i].getFluid();
			}
			TemplateRecipe recipe = getRecipeMap().findRecipe(worldObj, pos, getPower(), fluidInputs, itemInputs);
			if(recipe == null ||
					(recipe.outputsItem.length > itemOutputs.length) ||
					(recipe.outputsFluid.length > fluidOutputTanks.length) ||
					!matchRecipeSpecial(recipe))
				return;
			for(int i = 0; i < recipe.inputsItem.length; ++i)
			{
				if(recipe.inputsItem[i] != null)
				{
					decrStackSize(itemInputs, i, recipe.inputsItem[i].size(itemInputs[i]), true);
				}
			}
			for(int i = 0; i < recipe.inputsFluid.length; ++i)
			{
				if(recipe.inputsFluid[i] != null)
				{
					fluidInputTanks[i].drain(recipe.inputsFluid[i], true);
				}
			}
			initRecipeInput(recipe);
			enable(Working);
		}
	}

	protected void initRecipeInput(TemplateRecipe recipe)
	{
		maxDuration = recipe.duration;
		minPower = recipe.minPower;
		outputCacheFluids = new FluidStack[Math.min(fluidOutputTanks.length, recipe.outputsFluid.length)];
		for(int i = 0; i < outputCacheFluids.length; ++i)
		{
			outputCacheFluids[i] = recipe.outputsFluid[i];
			if(outputCacheFluids[i] != null)
			{
				outputCacheFluids[i] = outputCacheFluids[i].copy();
			}
		}
		outputCacheItems = new ItemStack[Math.min(itemOutputs.length, recipe.outputsItem.length)];
		for(int i = 0; i < outputCacheItems.length; ++i)
		{
			if(recipe.outputsItem[i] != null)
			{
				int multiply = 0;
				if(recipe.chancesOutputItem != null)
				{
					for(int chance : recipe.chancesOutputItem[i])
					{
						if(chance == 10000 || random.nextInt(10000) < chance)
						{
							multiply++;
						}
					}
				}
				else
				{
					multiply = 1;
				}
				outputCacheItems[i] = recipe.outputsItem[i].instance();
				outputCacheItems[i].stackSize *= multiply;
			}
		}
	}
	
	protected void initRecipeOutput()
	{
		outputCacheFluids = null;
		outputCacheItems = null;
		duration = 0;
		maxDuration = Long.MAX_VALUE;
		minPower = 0;
	}
	
	protected boolean matchRecipeOutput()
	{
		return true;
	}
	
	protected boolean matchRecipeSpecial(TemplateRecipe recipe)
	{
		return true;
	}

	protected abstract IRecipeMap<TemplateRecipe> getRecipeMap();

	protected void onWorking()
	{
		if(useEnergy())
		{
			if(is(Working))
			{
				++duration;
			}
		}
	}
	
	@Override
	public boolean isActived()
	{
		return is(MachineEnabled) && is(Working);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		Inventory.writeToNBT(itemInputs, nbt, "inputItem");
		Inventory.writeToNBT(itemOutputs, nbt, "outputItem");
		nbt.setBoolean("allowAutoOutput", allowAutoOutput);
		nbt.setLong("minPower", minPower);
		nbt.setLong("duration", duration);
		nbt.setLong("maxDuration", maxDuration);
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < fluidInputTanks.length; ++i)
		{
			NBTTagCompound nbt1 = fluidInputTanks[i].writeToNBT(new NBTTagCompound());
			nbt1.setByte("id", (byte) i);
			list.appendTag(nbt1);
		}
		nbt.setTag("inputFluid", list);
		list = new NBTTagList();
		for(int i = 0; i < fluidOutputTanks.length; ++i)
		{
			NBTTagCompound nbt1 = fluidOutputTanks[i].writeToNBT(new NBTTagCompound());
			nbt1.setByte("id", (byte) i);
			list.appendTag(nbt1);
		}
		nbt.setTag("outputFluid", list);
		if(outputCacheItems != null)
		{
			Inventory.writeToNBT(outputCacheItems, nbt, "outputCacheItems");
			nbt.setByte("outputCacheItemsLength", (byte) outputCacheItems.length);
		}
		if(outputCacheFluids != null)
		{
			list = new NBTTagList();
			for(int i = 0; i < outputCacheFluids.length; ++i)
			{
				if(outputCacheFluids[i] != null)
				{
					NBTTagCompound nbt1 = outputCacheFluids[i].writeToNBT(new NBTTagCompound());
					nbt1.setByte("id", (byte) i);
					list.appendTag(nbt1);
				}
			}
			nbt.setTag("outputCacheFluids", list);
			nbt.setByte("outputCacheFluidsLength", (byte) outputCacheFluids.length);
		}
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		Inventory.readFromNBT(itemInputs, nbt, "inputItem");
		Inventory.readFromNBT(itemOutputs, nbt, "outputItem");
		allowAutoOutput = nbt.getBoolean("allowAutoOutput");
		minPower = nbt.getLong("minPower");
		maxDuration = nbt.getLong("maxDuration");
		duration = nbt.getLong("duration");
		NBTTagList list = nbt.getTagList("inputFluid", NBT.TAG_COMPOUND);
		for(int i = 0; i < list.tagCount(); ++i)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte id = nbt1.getByte("id");
			if(id >= 0 && id < fluidInputTanks.length)
			{
				fluidInputTanks[id].readFromNBT(nbt1);
			}
		}
		list = nbt.getTagList("outputFluid", NBT.TAG_COMPOUND);
		for(int i = 0; i < list.tagCount(); ++i)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte id = nbt1.getByte("id");
			if(id >= 0 && id < fluidOutputTanks.length)
			{
				fluidOutputTanks[id].readFromNBT(nbt1);
			}
		}
		if(nbt.hasKey("outputCacheItems", NBT.TAG_COMPOUND))
		{
			outputCacheItems = new ItemStack[nbt.getByte("outputCacheItemsLength")];
			Inventory.readFromNBT(outputCacheItems, nbt, "outputCacheItems");
		}
		if(nbt.hasKey("outputCacheFluids", NBT.TAG_COMPOUND))
		{
			outputCacheFluids = new FluidStack[nbt.getByte("outputCacheFluidsLength")];
			list = nbt.getTagList("outputCacheFluids", NBT.TAG_COMPOUND);
			for(int i = 0; i < list.tagCount(); ++i)
			{
				NBTTagCompound nbt1 = list.getCompoundTagAt(i);
				byte id = nbt1.getByte("id");
				if(id >= 0 && id < outputCacheFluids.length)
				{
					outputCacheFluids[id] = FluidStack.loadFluidStackFromNBT(nbt1);
				}
			}
		}
	}
	
	protected abstract boolean useEnergy();

	protected abstract long getEnergyInput();

	protected abstract long getPower();

	@Override
	public int getSizeInventory()
	{
		return itemInputs.length + itemOutputs.length + inventory.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return index < itemInputs.length ? itemInputs[index] :
			index < itemInputs.length + itemOutputs.length ? itemOutputs[index - itemInputs.length] :
				inventory.getStackInSlot(index - itemInputs.length - itemOutputs.length);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return index < itemInputs.length ? removeStackFromSlot(itemInputs, index) :
			index < itemInputs.length + itemOutputs.length ? removeStackFromSlot(itemOutputs, index - itemInputs.length) :
				removeStackFromSlot(itemOutputs, index - itemInputs.length - itemOutputs.length);
	}

	protected ItemStack removeStackFromSlot(ItemStack[] stacks, int index)
	{
		ItemStack ret = stacks[index];
		stacks[index] = null;
		return ret;
	}
	
	protected static int addStack(ItemStack[] stacks, int i, ItemStack stack, boolean process, int limit)
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
	
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return index < itemInputs.length ? decrStackSize(itemInputs, index, count, true) :
			index < itemInputs.length + itemOutputs.length ? decrStackSize(itemOutputs, index - itemInputs.length, count, true) :
				inventory.decrStackSize(index - itemInputs.length - itemOutputs.length, count, true);
	}
	
	protected ItemStack decrStackSize(ItemStack[] stacks, int index, int count, boolean process)
	{
		if(count <= 0) return null;
		if(stacks[index] == null) return null;
		ItemStack stack = stacks[index];
		if(stack.stackSize <= count)
		{
			if(process)
			{
				stacks[index] = null;
			}
			return stack;
		}
		else
		{
			if(process)
				return stack.splitStack(count);
			else
			{
				ItemStack ret = stack.copy();
				ret.stackSize = count;
				return ret;
			}
		}
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if(index < itemInputs.length)
		{
			itemInputs[index] = ItemStack.copyItemStack(stack);
		}
		else if(index < itemInputs.length + itemOutputs.length)
		{
			itemOutputs[index - itemInputs.length] = ItemStack.copyItemStack(stack);
		}
		else
		{
			inventory.setInventorySlotContents(index - itemInputs.length - itemOutputs.length, stack);
		}
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == getFacing("input") ? FACE_INPUT : side == getFacing("output") ? FACE_OUTPUT : new int[0];
	}
	
	@Override
	protected int[] getInputSlots()
	{
		return FACE_INPUT;
	}
	
	@Override
	protected int[] getOutputSlots()
	{
		return FACE_OUTPUT;
	}
	
	@Override
	protected boolean canInsertItemFromSlot(int index, ItemStack stack)
	{
		return index < itemInputs.length;
	}

	@Override
	protected boolean canExtractItemFromSlot(int index, ItemStack stack)
	{
		return index < itemInputs.length + itemOutputs.length && index >= itemInputs.length;
	}

	@Override
	public int getFieldCount()
	{
		return 4;
	}

	@Override
	public int getField(int id)
	{
		switch (id)
		{
		case 0 : return (int) (duration & 0xFFFFFFFF);
		case 1 : return (int) ((duration >> 32) & 0xFFFFFFFF);
		case 2 : return (int) (maxDuration & 0xFFFFFFFF);
		case 3 : return (int) ((maxDuration >> 32) & 0xFFFFFFFF);
		default: return 0;
		}
	}

	@Override
	public void setField(int id, int value)
	{
		switch(id)
		{
		case 0 : duration &= 0xFFFFFFFF00000000L; duration |= value; break;
		case 1 : duration &= 0xFFFFFFFFL; duration |= value << 32; break;
		case 2 : maxDuration &= 0xFFFFFFFF00000000L; maxDuration |= value; break;
		case 3 : maxDuration &= 0xFFFFFFFFL; maxDuration |= value << 32; break;
		}
	}

	@SideOnly(Side.CLIENT)
	public int getRecipeProgress(int scale)
	{
		return maxDuration == 0 ? 0 : (int) (scale * duration / maxDuration);
	}
}