package ttr.core.tile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.FluidTankPropertiesWrapper;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import ttr.api.data.Capabilities;
import ttr.api.enums.EnumTools;
import ttr.api.inventory.IItemHandlerIO;
import ttr.api.inventory.Inventory;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.recipe.TemplateRecipeMap.TemplateRecipe;
import ttr.api.stack.AbstractStack;
import ttr.api.util.Util;
import ttr.core.CommonProxy;
import ttr.core.TTr;

public abstract class TEMachineRecipeMap extends TEMachineInventory implements ITankSyncable, IItemHandlerIO, IFluidHandler
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
	//For logistic configuration.
	public EnumFacing autoOutputFace = null;
	public boolean allowAutoOutputItem = false;
	public boolean allowAutoOutputFluid = false;
	public boolean throwItemOut = false;
	public boolean moveFully = false;
	
	public long duration;
	public long maxDuration = Long.MAX_VALUE;
	public long minPower;
	
	public TEMachineRecipeMap(int itemInputSize, int itemOutputSize, int fluidInputSize, int fluidOutputSize)
	{
		this.itemInputs = new ItemStack[itemInputSize];
		this.itemOutputs = new ItemStack[itemOutputSize];
		this.fluidInputTanks = new FluidTank[fluidInputSize];
		this.fluidOutputTanks = new FluidTank[fluidOutputSize];
		for(int i = 0; i < fluidInputSize; ++i)
		{
			this.fluidInputTanks[i] = new FluidTank(16000);
		}
		for(int i = 0; i < fluidOutputSize; ++i)
		{
			this.fluidOutputTanks[i] = new FluidTank(16000);
		}
		this.FACE_INPUT = new int[itemInputSize];
		this.FACE_OUTPUT = new int[itemOutputSize];
		int i = 0;
		for(; i < itemInputSize; ++i)
		{
			this.FACE_INPUT[i] = i;
		}
		for(; i < itemInputSize + itemOutputSize; ++i)
		{
			this.FACE_OUTPUT[i - itemInputSize] = i;
		}
		enable(MachineEnabled);
	}
	
	@Override
	public boolean onActived(EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing facing, float hitX,
			float hitY, float hitZ)
	{
		if(accessConfigGUIOpen() && hand == EnumHand.MAIN_HAND && EnumTools.wrench.match(heldItem))
		{
			if (isServer())
			{
				Util.damageOrDischargeItem(heldItem, 100L, 1, player);
				player.openGui(TTr.MODID, CommonProxy.ELE_MACHINE_CONFIG_ID, this.worldObj, this.pos.getX(), this.pos.getY(), this.pos.getZ());
			}
			return true;
		}
		return super.onActived(player, hand, heldItem, facing, hitX, hitY, hitZ);
	}
	
	protected boolean accessConfigGUIOpen()
	{
		return false;
	}
	
	@Override
	protected void updateServer()
	{
		this.lastEnergyInput = getEnergyInput();
		if (is(MachineEnabled) && canUseMachine())
		{
			updateLogistic();//Logistic only take effects when structure is usable.
			
			checkRecipe();
			onWorking();
		}
		super.updateServer();
	}
	
	protected boolean canUseMachine()
	{
		return true;
	}
	
	protected void checkRecipe()
	{
		if(is(Working))
		{
			if(this.duration >= this.maxDuration)
			{
				disable(Working);
				enable(OutPuting);
				this.duration = this.maxDuration;
			}
		}
		if(is(OutPuting))
		{
			if(!matchRecipeOutput())
				return;
			if(this.outputCacheItems != null)
			{
				for(int i = 0; i < this.outputCacheItems.length; ++i)
				{
					if(addStack(this.itemOutputs, i, this.outputCacheItems[i], false, getInventoryStackLimit()) != this.outputCacheItems[i].stackSize)
						return;
				}
			}
			if(this.outputCacheFluids != null)
			{
				for(int i = 0; i < this.outputCacheFluids.length; ++i)
				{
					if(this.fluidOutputTanks[i].fill(this.outputCacheFluids[i], false) != this.outputCacheFluids[i].amount)
						return;
				}
			}
			if(this.outputCacheItems != null)
			{
				for(int i = 0; i < this.outputCacheItems.length; ++i)
				{
					addStack(this.itemOutputs, i, this.outputCacheItems[i], true, getInventoryStackLimit());
				}
			}
			if(this.outputCacheFluids != null)
			{
				for(int i = 0; i < this.outputCacheFluids.length; ++i)
				{
					this.fluidOutputTanks[i].fill(this.outputCacheFluids[i], true);
				}
			}
			initRecipeOutput();
			disable(OutPuting);
		}
		if(!is(Working) && !is(OutPuting))
		{
			FluidStack[] fluidInputs = new FluidStack[this.fluidInputTanks.length];
			for(int i = 0; i < fluidInputs.length; ++i)
			{
				fluidInputs[i] = this.fluidInputTanks[i].getFluid();
			}
			TemplateRecipeMap map = getRecipeMap();
			TemplateRecipe recipe = map.findRecipe(this.worldObj, this.pos, getPower(), fluidInputs, this.itemInputs);
			if(recipe == null ||
					(recipe.outputsItem.length > this.itemOutputs.length) ||
					(recipe.outputsFluid.length > this.fluidOutputTanks.length) ||
					!matchRecipeSpecial(recipe))
				return;
			if(map.shapedItemInput)
			{
				for(int i = 0; i < recipe.inputsItem.length; ++i)
				{
					if(recipe.inputsItem[i] != null)
					{
						decrStackSize(this.itemInputs, i, recipe.inputsItem[i].size(this.itemInputs[i]), true);
					}
				}
			}
			else
			{
				for(int i = 0; i < this.itemInputs.length; ++i)
				{
					if(this.itemInputs[i] != null)
					{
						for (AbstractStack element : recipe.inputsItem)
						{
							if(element != null && element.contain(this.itemInputs[i]))
							{
								decrStackSize(this.itemInputs, i, element.size(this.itemInputs[i]), true);
							}
						}
					}
				}
			}
			for(int i = 0; i < recipe.inputsFluid.length; ++i)
			{
				if(recipe.inputsFluid[i] != null)
				{
					this.fluidInputTanks[i].drain(recipe.inputsFluid[i], true);
				}
			}
			initRecipeInput(recipe);
			enable(Working);
		}
	}
	
	protected void initRecipeInput(TemplateRecipe recipe)
	{
		this.maxDuration = recipe.duration;
		this.minPower = recipe.minPower;
		this.outputCacheFluids = new FluidStack[Math.min(this.fluidOutputTanks.length, recipe.outputsFluid.length)];
		for(int i = 0; i < this.outputCacheFluids.length; ++i)
		{
			this.outputCacheFluids[i] = recipe.outputsFluid[i];
			if(this.outputCacheFluids[i] != null)
			{
				this.outputCacheFluids[i] = this.outputCacheFluids[i].copy();
			}
		}
		this.outputCacheItems = new ItemStack[Math.min(this.itemOutputs.length, recipe.outputsItem.length)];
		for(int i = 0; i < this.outputCacheItems.length; ++i)
		{
			if(recipe.outputsItem[i] != null)
			{
				int multiply = 0;
				if(recipe.chancesOutputItem != null)
				{
					for(int chance : recipe.chancesOutputItem[i])
					{
						if(chance == 10000 || this.random.nextInt(10000) < chance)
						{
							multiply++;
						}
					}
				}
				else
				{
					multiply = 1;
				}
				this.outputCacheItems[i] = recipe.outputsItem[i].instance().copy();
				this.outputCacheItems[i].stackSize *= multiply;
			}
		}
	}
	
	protected void initRecipeOutput()
	{
		this.outputCacheFluids = null;
		this.outputCacheItems = null;
		this.duration = 0;
		this.maxDuration = Long.MAX_VALUE;
		this.minPower = 0;
	}
	
	protected boolean matchRecipeOutput()
	{
		return true;
	}
	
	protected boolean matchRecipeSpecial(TemplateRecipe recipe)
	{
		return true;
	}
	
	protected abstract TemplateRecipeMap getRecipeMap();
	
	protected void onWorking()
	{
		if(useEnergy())
		{
			if(is(Working))
			{
				++this.duration;
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
		Inventory.writeToNBT(this.itemInputs, nbt, "inputItem");
		Inventory.writeToNBT(this.itemOutputs, nbt, "outputItem");
		nbt.setBoolean("allowAutoOutputItem", this.allowAutoOutputItem);
		nbt.setBoolean("allowAutoOutputFluid", this.allowAutoOutputFluid);
		nbt.setBoolean("throwItemOut", this.throwItemOut);
		nbt.setBoolean("moveFully", this.moveFully);
		if(this.autoOutputFace != null)
		{
			nbt.setByte("autoOutputFace", (byte) this.autoOutputFace.ordinal());
		}
		nbt.setLong("minPower", this.minPower);
		nbt.setLong("duration", this.duration);
		nbt.setLong("maxDuration", this.maxDuration);
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < this.fluidInputTanks.length; ++i)
		{
			NBTTagCompound nbt1 = this.fluidInputTanks[i].writeToNBT(new NBTTagCompound());
			nbt1.setByte("id", (byte) i);
			list.appendTag(nbt1);
		}
		nbt.setTag("inputFluid", list);
		list = new NBTTagList();
		for(int i = 0; i < this.fluidOutputTanks.length; ++i)
		{
			NBTTagCompound nbt1 = this.fluidOutputTanks[i].writeToNBT(new NBTTagCompound());
			nbt1.setByte("id", (byte) i);
			list.appendTag(nbt1);
		}
		nbt.setTag("outputFluid", list);
		if(this.outputCacheItems != null)
		{
			Inventory.writeToNBT(this.outputCacheItems, nbt, "outputCacheItems");
			nbt.setByte("outputCacheItemsLength", (byte) this.outputCacheItems.length);
		}
		if(this.outputCacheFluids != null)
		{
			list = new NBTTagList();
			for(int i = 0; i < this.outputCacheFluids.length; ++i)
			{
				if(this.outputCacheFluids[i] != null)
				{
					NBTTagCompound nbt1 = this.outputCacheFluids[i].writeToNBT(new NBTTagCompound());
					nbt1.setByte("id", (byte) i);
					list.appendTag(nbt1);
				}
			}
			nbt.setTag("outputCacheFluids", list);
			nbt.setByte("outputCacheFluidsLength", (byte) this.outputCacheFluids.length);
		}
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		Inventory.readFromNBT(this.itemInputs, nbt, "inputItem");
		Inventory.readFromNBT(this.itemOutputs, nbt, "outputItem");
		this.allowAutoOutputFluid = nbt.getBoolean("allowAutoOutputFluid");
		this.allowAutoOutputItem = nbt.getBoolean("allowAutoOutputItem");
		this.throwItemOut = nbt.getBoolean("throwItemOut");
		this.moveFully = nbt.getBoolean("moveFully");
		if(nbt.hasKey("autoOutputFace"))
		{
			this.autoOutputFace = EnumFacing.VALUES[nbt.getByte("autoOutputFace")];
		}
		this.minPower = nbt.getLong("minPower");
		this.maxDuration = nbt.getLong("maxDuration");
		this.duration = nbt.getLong("duration");
		NBTTagList list = nbt.getTagList("inputFluid", NBT.TAG_COMPOUND);
		for(int i = 0; i < list.tagCount(); ++i)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte id = nbt1.getByte("id");
			if(id >= 0 && id < this.fluidInputTanks.length)
			{
				this.fluidInputTanks[id].readFromNBT(nbt1);
			}
		}
		list = nbt.getTagList("outputFluid", NBT.TAG_COMPOUND);
		for(int i = 0; i < list.tagCount(); ++i)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte id = nbt1.getByte("id");
			if(id >= 0 && id < this.fluidOutputTanks.length)
			{
				this.fluidOutputTanks[id].readFromNBT(nbt1);
			}
		}
		if(nbt.hasKey("outputCacheItems", NBT.TAG_COMPOUND))
		{
			this.outputCacheItems = new ItemStack[nbt.getByte("outputCacheItemsLength")];
			Inventory.readFromNBT(this.outputCacheItems, nbt, "outputCacheItems");
		}
		if(nbt.hasKey("outputCacheFluids", NBT.TAG_COMPOUND))
		{
			this.outputCacheFluids = new FluidStack[nbt.getByte("outputCacheFluidsLength")];
			list = nbt.getTagList("outputCacheFluids", NBT.TAG_COMPOUND);
			for(int i = 0; i < list.tagCount(); ++i)
			{
				NBTTagCompound nbt1 = list.getCompoundTagAt(i);
				byte id = nbt1.getByte("id");
				if(id >= 0 && id < this.outputCacheFluids.length)
				{
					this.outputCacheFluids[id] = FluidStack.loadFluidStackFromNBT(nbt1);
				}
			}
		}
	}
	
	@Override
	public void writeToDescription(NBTTagCompound nbt)
	{
		super.writeToDescription(nbt);
		byte state = 0;
		if(this.autoOutputFace != null)
		{
			state |= this.autoOutputFace.ordinal() + 1;
		}
		if(this.allowAutoOutputFluid)
		{
			state |= 0x8;
		}
		if(this.allowAutoOutputItem)
		{
			state |= 0x10;
		}
		if(this.moveFully)
		{
			state |= 0x20;
		}
		if(this.throwItemOut)
		{
			state |= 0x40;
		}
		nbt.setByte("lo", state);
	}
	
	@Override
	public void readFromDescription(NBTTagCompound nbt)
	{
		super.readFromDescription(nbt);
		short state = nbt.getShort("lo");
		if((state & 0x7) != 0)
		{
			this.autoOutputFace = EnumFacing.VALUES[(state & 0x7) - 1];
		}
		else
		{
			this.autoOutputFace = null;
		}
		this.allowAutoOutputFluid = (state & 0x8) != 0;
		this.allowAutoOutputItem = (state & 0x10) != 0;
		this.moveFully = (state & 0x20) != 0;
		this.throwItemOut = (state & 0x40) != 0;
	}
	
	protected abstract boolean useEnergy();
	
	protected abstract long getEnergyInput();
	
	protected abstract long getPower();
	
	protected void updateLogistic()
	{
		if(this.autoOutputFace != null && this.lastActived % 20 == 0)
		{
			TileEntity tile = this.worldObj.getTileEntity(this.pos.offset(this.autoOutputFace));
			if(tile == null)
			{
				if(this.throwItemOut && this.allowAutoOutputItem && this.worldObj.isAirBlock(this.pos.offset(this.autoOutputFace)))
				{
					for(int i : this.FACE_OUTPUT)
					{
						if(getStackInSlot(i) != null)
						{
							if(this.moveFully)
							{
								ItemStack stack = removeStackFromSlot(i);
								EntityItem entity = new EntityItem(this.worldObj, this.pos.getX() + (1 + this.autoOutputFace.getFrontOffsetX()) * .5, this.pos.getY() + (1 + this.autoOutputFace.getFrontOffsetY()) * .5, this.pos.getZ() + (1 + this.autoOutputFace.getFrontOffsetZ()) * .5, stack);
								entity.motionX = (this.random.nextDouble() - this.random.nextDouble()) * 0.01 + this.autoOutputFace.getFrontOffsetX() * .3;
								entity.motionY = (this.random.nextDouble() - this.random.nextDouble()) * 0.01 + this.autoOutputFace.getFrontOffsetY() * .3;
								entity.motionZ = (this.random.nextDouble() - this.random.nextDouble()) * 0.01 + this.autoOutputFace.getFrontOffsetZ() * .3;
								this.worldObj.spawnEntityInWorld(entity);
							}
							else
							{
								ItemStack stack = decrStackSize(i, 1);
								EntityItem entity = new EntityItem(this.worldObj, this.pos.getX() + (1 + this.autoOutputFace.getFrontOffsetX()) * .5, this.pos.getY() + (1 + this.autoOutputFace.getFrontOffsetY()) * .5, this.pos.getZ() + (1 + this.autoOutputFace.getFrontOffsetZ()) * .5, stack);
								entity.motionX = (this.random.nextDouble() - this.random.nextDouble()) * 0.01 + this.autoOutputFace.getFrontOffsetX() * .3;
								entity.motionY = (this.random.nextDouble() - this.random.nextDouble()) * 0.01 + this.autoOutputFace.getFrontOffsetY() * .3;
								entity.motionZ = (this.random.nextDouble() - this.random.nextDouble()) * 0.01 + this.autoOutputFace.getFrontOffsetZ() * .3;
								this.worldObj.spawnEntityInWorld(entity);
							}
							return;
						}
					}
				}
			}
			else
			{
				EnumFacing oppisite = this.autoOutputFace.getOpposite();
				if(this.allowAutoOutputItem)
				{
					if(tile.hasCapability(Capabilities.ITEM_HANDLER_IO, oppisite))
					{
						IItemHandlerIO io = tile.getCapability(Capabilities.ITEM_HANDLER_IO, oppisite);
						if(io.canInsertItem())
						{
							for(int i : this.FACE_OUTPUT)
							{
								ItemStack stack = getStackInSlot(i);
								if(stack != null)
								{
									stack = stack.copy();
									if(!this.moveFully)
									{
										stack.stackSize = 1;
									}
									if(io.tryInsertItem(stack, oppisite, true) > 0)
									{
										decrStackSize(i, io.tryInsertItem(stack, oppisite, false));
										break;
									}
								}
							}
						}
					}
					else if(tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, oppisite))
					{
						IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, oppisite);
						label:
							for(int j : this.FACE_OUTPUT)
							{
								ItemStack stack = getStackInSlot(j);
								if(stack != null)
								{
									ItemStack stack1 = stack.copy();
									if(!this.moveFully)
									{
										stack1.stackSize = 1;
									}
									for(int i = 0; i < handler.getSlots() && stack1 != null;
											stack1 = handler.insertItem(i++, stack1, true));
									if(!ItemStack.areItemsEqual(stack, stack1))
									{
										stack1 = stack.copy();
										for(int i = 0; i < handler.getSlots() && stack1 != null;
												stack1 = handler.insertItem(i++, stack1, false));
										if(stack1 == null)
										{
											removeStackFromSlot(j);
										}
										else
										{
											decrStackSize(j, stack.stackSize - stack1.stackSize);
										}
										break label;
									}
								}
							}
					}
					else if(tile instanceof ISidedInventory)
					{
						ISidedInventory inventory = (ISidedInventory) tile;
						label:
							for(int j : this.FACE_OUTPUT)
							{
								ItemStack stack = getStackInSlot(j);
								if(stack != null)
								{
									ItemStack stack1 = stack.copy();
									if(!this.moveFully)
									{
										stack1.stackSize = 1;
									}
									for(int i : inventory.getSlotsForFace(oppisite))
									{
										if(inventory.canInsertItem(i, stack1, oppisite))
										{
											ItemStack target = inventory.getStackInSlot(i);
											if(target == null || (ItemStack.areItemsEqual(stack1, target) && ItemStack.areItemStackTagsEqual(stack1, target)))
											{
												int size;
												if(target == null)
												{
													size = Math.min(stack1.stackSize, inventory.getInventoryStackLimit());
													decrStackSize(j, size);
													stack1.stackSize = size;
													inventory.setInventorySlotContents(i, stack1);
												}
												else
												{
													size = Math.min(stack1.stackSize, Math.min(inventory.getInventoryStackLimit(), stack1.getMaxStackSize()) - target.stackSize);
													decrStackSize(j, size);
													target.stackSize += size;
												}
												break label;
											}
										}
									}
								}
							}
					}
					else if(tile instanceof IInventory)
					{
						IInventory inventory = (IInventory) tile;
						label:
							for(int j : this.FACE_OUTPUT)
							{
								ItemStack stack = getStackInSlot(j);
								if(stack != null)
								{
									ItemStack stack1 = stack.copy();
									if(!this.moveFully)
									{
										stack1.stackSize = 1;
									}
									for(int i = 0; i < inventory.getSizeInventory(); ++i)
									{
										ItemStack target = inventory.getStackInSlot(i);
										if(target == null || (ItemStack.areItemsEqual(stack1, target) && ItemStack.areItemStackTagsEqual(stack1, target)))
										{
											int size;
											if(target == null)
											{
												size = Math.min(stack1.stackSize, inventory.getInventoryStackLimit());
												decrStackSize(j, size);
												stack1.stackSize = size;
												inventory.setInventorySlotContents(i, stack1);
											}
											else
											{
												size = Math.min(stack1.stackSize, Math.min(inventory.getInventoryStackLimit(), stack1.getMaxStackSize()) - target.stackSize);
												decrStackSize(j, size);
												target.stackSize += size;
											}
											break label;
										}
									}
								}
							}
					}
				}
				if(this.allowAutoOutputFluid)
				{
					if(tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, oppisite))
					{
						IFluidHandler handler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, oppisite);
						for(FluidTank tank : this.fluidOutputTanks)
						{
							if(tank.getFluid() != null)
							{
								int amount = handler.fill(tank.getFluid(), false);
								if(amount != 0)
								{
									handler.fill(tank.drain(amount, true), true);
									break;
								}
							}
						}
					}
					else if(tile instanceof net.minecraftforge.fluids.IFluidHandler)
					{
						net.minecraftforge.fluids.IFluidHandler handler = (net.minecraftforge.fluids.IFluidHandler) tile;
						for(FluidTank tank : this.fluidOutputTanks)
						{
							if(tank.getFluid() != null && handler.canFill(oppisite, tank.getFluid().getFluid()))
							{
								int amount = handler.fill(oppisite, tank.getFluid(), false);
								if(amount != 0)
								{
									handler.fill(oppisite, tank.drain(amount, true), true);
									break;
								}
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public int getSizeInventory()
	{
		return this.itemInputs.length + this.itemOutputs.length + this.inventory.getSizeInventory();
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return index < this.itemInputs.length ? this.itemInputs[index] :
			index < this.itemInputs.length + this.itemOutputs.length ? this.itemOutputs[index - this.itemInputs.length] :
				this.inventory.getStackInSlot(index - this.itemInputs.length - this.itemOutputs.length);
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return index < this.itemInputs.length ? removeStackFromSlot(this.itemInputs, index) :
			index < this.itemInputs.length + this.itemOutputs.length ? removeStackFromSlot(this.itemOutputs, index - this.itemInputs.length) :
				removeStackFromSlot(this.itemOutputs, index - this.itemInputs.length - this.itemOutputs.length);
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
		return index < this.itemInputs.length ? decrStackSize(this.itemInputs, index, count, true) :
			index < this.itemInputs.length + this.itemOutputs.length ? decrStackSize(this.itemOutputs, index - this.itemInputs.length, count, true) :
				this.inventory.decrStackSize(index - this.itemInputs.length - this.itemOutputs.length, count, true);
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
				markDirty();
			}
			return stack;
		}
		else
		{
			if(process)
			{
				markDirty();
				return stack.splitStack(count);
			}
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
		if(index < this.itemInputs.length)
		{
			this.itemInputs[index] = ItemStack.copyItemStack(stack);
		}
		else if(index < this.itemInputs.length + this.itemOutputs.length)
		{
			this.itemOutputs[index - this.itemInputs.length] = ItemStack.copyItemStack(stack);
		}
		else
		{
			this.inventory.setInventorySlotContents(index - this.itemInputs.length - this.itemOutputs.length, stack);
		}
		markDirty();
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == getFacing("input") ? this.FACE_INPUT : side == getFacing("output") ? this.FACE_OUTPUT : new int[0];
	}
	
	@Override
	protected int[] getInputSlots()
	{
		return this.FACE_INPUT;
	}
	
	@Override
	protected int[] getOutputSlots()
	{
		return this.FACE_OUTPUT;
	}
	
	@Override
	protected boolean canInsertItemFromSlot(int index, ItemStack stack)
	{
		return index < this.itemInputs.length;
	}
	
	@Override
	protected boolean canExtractItemFromSlot(int index, ItemStack stack)
	{
		return index < this.itemInputs.length + this.itemOutputs.length && index >= this.itemInputs.length;
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
		case 0 : return (int) (this.duration & 0xFFFFFFFF);
		case 1 : return (int) ((this.duration >> 32) & 0xFFFFFFFF);
		case 2 : return (int) (this.maxDuration & 0xFFFFFFFF);
		case 3 : return (int) ((this.maxDuration >> 32) & 0xFFFFFFFF);
		default: return 0;
		}
	}
	
	@Override
	public void setField(int id, int value)
	{
		switch(id)
		{
		case 0 : this.duration &= 0xFFFFFFFF00000000L; this.duration |= value; break;
		case 1 : this.duration &= 0xFFFFFFFFL; this.duration |= value << 32; break;
		case 2 : this.maxDuration &= 0xFFFFFFFF00000000L; this.maxDuration |= value; break;
		case 3 : this.maxDuration &= 0xFFFFFFFFL; this.maxDuration |= value << 32; break;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getRecipeProgress(int scale)
	{
		return this.maxDuration == 0 ? 0 : (int) (scale * this.duration / this.maxDuration);
	}
	
	@Override
	public FluidTankInfo getInfomation(int id)
	{
		if(id < this.fluidInputTanks.length)
			return this.fluidInputTanks[id].getInfo();
		else
			return this.fluidOutputTanks[id - this.fluidInputTanks.length].getInfo();
	}
	
	@Override
	public int getTankSize()
	{
		return this.fluidInputTanks.length + this.fluidOutputTanks.length;
	}
	
	@Override
	public void setFluidStackToTank(int id, FluidStack stack)
	{
		if(id < this.fluidInputTanks.length)
		{
			this.fluidInputTanks[id].setFluid(stack);
		}
		else
		{
			this.fluidOutputTanks[id - this.fluidInputTanks.length].setFluid(stack);
		}
	}
	
	@Override
	public boolean canExtractItem()
	{
		return this.allowOutput;
	}
	
	@Override
	public boolean canInsertItem()
	{
		return this.allowInput;
	}
	
	@Override
	public ItemStack extractItem(int size, EnumFacing direction, boolean simulate)
	{
		if(direction == this.facing) return null;
		for(int i = 0; i < this.itemOutputs.length; ++i)
		{
			if(this.itemOutputs[i] != null)
			{
				ItemStack stack = this.itemOutputs[i].copy();
				if(size > stack.stackSize)
				{
					if(!simulate)
					{
						this.itemOutputs[i] = null;
						markDirty();
					}
				}
				else if(!simulate)
				{
					this.itemOutputs[i].stackSize -= size;
					markDirty();
				}
				return stack;
			}
		}
		return null;
	}
	
	@Override
	public int tryInsertItem(ItemStack stack, EnumFacing direction, boolean simulate)
	{
		for(int i = 0; i < this.itemInputs.length; ++i)
		{
			if(this.itemInputs[i] != null && this.itemInputs[i].isItemEqual(stack) && ItemStack.areItemStackTagsEqual(this.itemInputs[i], stack))
			{
				int size = Math.min(stack.stackSize, Math.min(getInventoryStackLimit(), stack.getMaxStackSize()) - this.itemInputs[i].stackSize);
				if(size > 0)
				{
					if(!simulate)
					{
						this.itemInputs[i].stackSize += size;
						markDirty();
					}
					return size;
				}
			}
		}
		for(int i = 0; i < this.itemInputs.length; ++i)
		{
			if(this.itemInputs[i] == null)
			{
				int size = Math.min(stack.stackSize, getInventoryStackLimit());
				if(!simulate)
				{
					this.itemInputs[i] = stack.copy();
					this.itemInputs[i].stackSize = size;
					markDirty();
				}
				return size;
			}
		}
		return 0;
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties()
	{
		List<IFluidTankProperties> list = new ArrayList();
		for(FluidTank tank : this.fluidInputTanks)
		{
			list.add(new FluidTankPropertiesWrapper(tank));
		}
		for(FluidTank tank : this.fluidOutputTanks)
		{
			list.add(new FluidTankPropertiesWrapper(tank));
		}
		return list.toArray(new FluidTankProperties[list.size()]);
	}
	
	@Override
	public int fill(FluidStack resource, boolean doFill)
	{
		if(this.allowInput)
		{
			int amount;
			for(FluidTank tank : this.fluidInputTanks)
			{
				if(tank.getFluid() != null && (amount = tank.fill(resource, doFill)) != 0)
				{
					if(doFill)
						markDirty();
					return amount;
				}
			}
			for(FluidTank tank : this.fluidInputTanks)
			{
				if(tank.getFluid() == null && (amount = tank.fill(resource, doFill)) != 0)
				{
					if(doFill)
						markDirty();
					return amount;
				}
			}
		}
		return 0;
	}
	
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain)
	{
		if(this.allowOutput)
		{
			FluidStack out;
			for(FluidTank tank : this.fluidOutputTanks)
			{
				if((out = tank.drain(resource, doDrain)) != null)
				{
					if(doDrain)
						markDirty();
					return out;
				}
			}
		}
		return null;
	}
	
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		if(this.allowOutput)
		{
			FluidStack out;
			for(FluidTank tank : this.fluidOutputTanks)
			{
				if((out = tank.drain(maxDrain, doDrain)) != null)
				{
					if(doDrain)
						markDirty();
					return out;
				}
			}
		}
		return null;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return ((capability == Capabilities.ITEM_HANDLER_IO || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) && this.facing != facing) || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return capability == Capabilities.ITEM_HANDLER_IO ? Capabilities.ITEM_HANDLER_IO.cast(this) :
			capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this) : super.getCapability(capability, facing);
	}
}