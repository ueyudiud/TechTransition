/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.tile.machine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.util.Util;

/**
 * Created at 2016年12月22日 下午7:13:55
 * @author ueyudiud
 */
public class TETankBottom extends TETank
{
	public int totalCapacity;
	private int totalGasAmount;
	private int totalLiquidAmount;
	
	private boolean init = true;
	private boolean marked = true;
	
	private long cooldown;
	private FluidStack[] output;
	
	private int[] capacityCache;
	public LinkedList<FluidStack> liquid = new LinkedList();
	public ArrayList<FluidStack> gas = new ArrayList(); //Gas is be distributed in every places in tank.
	
	public int tankSize;
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		if(!this.liquid.isEmpty())
		{
			NBTTagList list = new NBTTagList();
			nbt.setTag("liquid", list);
			for(FluidStack stack : this.liquid)
			{
				list.appendTag(stack.writeToNBT(new NBTTagCompound()));
			}
		}
		if(!this.gas.isEmpty())
		{
			NBTTagList list = new NBTTagList();
			nbt.setTag("gas", list);
			for(FluidStack stack : this.gas)
			{
				list.appendTag(stack.writeToNBT(new NBTTagCompound()));
			}
		}
		if(this.output != null)
		{
			NBTTagList list = new NBTTagList();
			nbt.setTag("output", list);
			for(FluidStack stack : this.output)
			{
				if(stack != null)
				{
					list.appendTag(stack.writeToNBT(new NBTTagCompound()));
				}
			}
		}
		nbt.setLong("cd", this.cooldown);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if(nbt.hasKey("liquid"))
		{
			NBTTagList list = nbt.getTagList("liquid", NBT.TAG_COMPOUND);
			for(int i = 0; i < list.tagCount(); ++i)
			{
				FluidStack stack = FluidStack.loadFluidStackFromNBT(list.getCompoundTagAt(i));
				if(stack != null)
				{
					this.liquid.add(stack);
					this.totalLiquidAmount += stack.amount;
				}
			}
		}
		if(nbt.hasKey("gas"))
		{
			NBTTagList list = nbt.getTagList("gas", NBT.TAG_COMPOUND);
			for(int i = 0; i < list.tagCount(); ++i)
			{
				FluidStack stack = FluidStack.loadFluidStackFromNBT(list.getCompoundTagAt(i));
				if(stack != null)
				{
					this.gas.add(stack);
					this.totalGasAmount += stack.amount;
				}
			}
		}
		if(nbt.hasKey("output"))
		{
			NBTTagList list = nbt.getTagList("output", NBT.TAG_COMPOUND);
			this.output = new FluidStack[list.tagCount()];
			for(int i = 0; i < list.tagCount(); ++i)
			{
				this.output[i] = FluidStack.loadFluidStackFromNBT(list.getCompoundTagAt(i));
			}
		}
		this.cooldown = nbt.getLong("cd");
	}
	
	@Override
	public void writeToDescription(NBTTagCompound nbt)
	{
		super.writeToDescription(nbt);
		if(this.tankList != null)
		{
			nbt.setByte("ts", (byte) this.tankList.length);
		}
		nbt.setInteger("c", this.totalCapacity);
		if(!this.liquid.isEmpty())
		{
			NBTTagList list = new NBTTagList();
			nbt.setTag("liquid", list);
			for(FluidStack stack : this.liquid)
			{
				list.appendTag(stack.writeToNBT(new NBTTagCompound()));
			}
		}
		if(!this.gas.isEmpty())
		{
			NBTTagList list = new NBTTagList();
			nbt.setTag("gas", list);
			for(FluidStack stack : this.gas)
			{
				list.appendTag(stack.writeToNBT(new NBTTagCompound()));
			}
		}
		if(this.output != null)
		{
			NBTTagList list = new NBTTagList();
			nbt.setTag("output", list);
			for(FluidStack stack : this.output)
			{
				if(stack != null)
				{
					list.appendTag(stack.writeToNBT(new NBTTagCompound()));
				}
			}
		}
		nbt.setLong("cd", this.cooldown);
	}
	
	@Override
	public void readFromDescription(NBTTagCompound nbt)
	{
		super.readFromDescription(nbt);
		this.tankSize = nbt.getByte("ts");
		this.totalCapacity = nbt.getInteger("c");
		synchronized (this.liquid)
		{
			this.liquid.clear();
			if(nbt.hasKey("liquid"))
			{
				NBTTagList list = nbt.getTagList("liquid", NBT.TAG_COMPOUND);
				for(int i = 0; i < list.tagCount(); ++i)
				{
					FluidStack stack = FluidStack.loadFluidStackFromNBT(list.getCompoundTagAt(i));
					if(stack != null)
					{
						this.liquid.add(stack);
					}
				}
			}
		}
		synchronized (this.gas)
		{
			this.gas.clear();
			if(nbt.hasKey("gas"))
			{
				NBTTagList list = nbt.getTagList("gas", NBT.TAG_COMPOUND);
				for(int i = 0; i < list.tagCount(); ++i)
				{
					FluidStack stack = FluidStack.loadFluidStackFromNBT(list.getCompoundTagAt(i));
					if(stack != null)
					{
						this.gas.add(stack);
					}
				}
			}
		}
		if(nbt.hasKey("output"))
		{
			NBTTagList list = nbt.getTagList("output", NBT.TAG_COMPOUND);
			this.output = new FluidStack[list.tagCount()];
			for(int i = 0; i < list.tagCount(); ++i)
			{
				this.output[i] = FluidStack.loadFluidStackFromNBT(list.getCompoundTagAt(i));
			}
		}
		this.cooldown = nbt.getLong("cd");
	}
	
	void markStructureUpdated()
	{
		this.marked = true;
	}
	
	@Override
	protected void updateServer()
	{
		super.updateServer();
		refreshStructure(this.init);
		if (this.tankList != null)
		{
			fillFluidToBottom();
		}
		syncToNearby();
		if(this.init)
		{
			this.init = false;
		}
	}
	
	private void fillFluidToBottom()
	{
		if (this.totalGasAmount == 0 && this.totalLiquidAmount == 0) return;
		TileEntity te = getTile(EnumFacing.DOWN);
		if (te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP))
		{
			IFluidHandler handler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP);
			FluidStack stack = drain(0, 1000, false);
			if (stack != null)
			{
				int amount = handler.fill(stack, true);
				if (amount > 0)
				{
					stack = stack.copy();
					stack.amount = amount;
					this.drain(0, stack, true);
				}
			}
		}
	}
	
	protected void refreshStructure(boolean isInit)
	{
		if(!this.worldObj.isBlockLoaded(this.pos))
		{
			if(this.tankList != null)
			{
				for(TETank tank : this.tankList)
				{
					if(tank == this) continue;
					tank.tankList = null;
					tank.tankID = -1;
				}
				this.tankList = null;
				this.capacityCache = new int[]{this.material.tankCapacity};
				this.totalCapacity = this.material.tankCapacity;
			}
		}
		else if(this.marked)
		{
			boolean flag = this.tankList == null;
			if(!flag)
			{
				for(int i = 0; i < this.tankList.length; ++i)
				{
					if(this.worldObj.getTileEntity(this.pos.add(0, i, 0)) != this.tankList[i])
					{
						flag = true;
						break;
					}
				}
				if(!flag && !(this.tankList[this.tankList.length - 1] instanceof TETankTop))
				{
					if(this.worldObj.getTileEntity(this.pos.add(0, this.tankList.length, 0)) instanceof TETank)
					{
						flag = true;
					}
				}
			}
			if(flag)
			{
				if(!isInit)
				{
					this.liquid.clear();
					this.gas.clear();
					this.totalGasAmount = 0;
					this.totalLiquidAmount = 0;
					for(TETank tank : this.tankList)
					{
						if(tank == this) continue;
						tank.tankList = null;
						tank.tankID = -1;
					}
				}
				List<TETank> list = new ArrayList();
				list.add(this);
				BlockPos pos1 = this.pos;
				TileEntity tile;
				while((tile = this.worldObj.getTileEntity(pos1 = pos1.up())) instanceof TETank)
				{
					if(tile instanceof TETankBottom) break;
					list.add((TETank) tile);
					if(tile instanceof TETankTop) break;
				}
				this.tankList = list.toArray(new TETank[list.size()]);
				this.capacityCache = new int[this.tankList.length];
				int capacity1 = 0;
				for(int i = 0; i < this.tankList.length; ++i)
				{
					capacity1 += this.tankList[i].material.tankCapacity;
					this.tankList[i].tankID = i;
					this.tankList[i].tankList = this.tankList;
					this.capacityCache[i] = capacity1;
				}
				this.totalCapacity = capacity1;
			}
			this.marked = false;
		}
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties()
	{
		return this.tankList != null ? new IFluidTankProperties[]{ new FluidTankProperties(null, this.totalCapacity, true, true) } :
			new IFluidTankProperties[0];
	}
	
	public int fill(int tankID, FluidStack resource, boolean doFill)
	{
		int result = resource.amount;
		if(result > this.totalCapacity - this.totalLiquidAmount - this.totalGasAmount)
		{
			result = this.totalCapacity - this.totalLiquidAmount - this.totalGasAmount;
		}
		if(doFill)
		{
			if(resource.getFluid().isGaseous(resource))
			{
				this.totalGasAmount += result;
			}
			else
			{
				this.totalLiquidAmount += result;
			}
		}
		resource = Util.copyAmount(resource, result);
		if(doFill)
		{
			if(resource.getFluid().isGaseous(resource))
			{
				if(!this.gas.isEmpty())
				{
					for(FluidStack stack : this.gas)
					{
						if(stack.isFluidEqual(resource))
						{
							stack.amount += resource.amount;
							return result;
						}
					}
				}
				this.gas.add(resource);
			}
			else
			{
				if(!this.liquid.isEmpty())
				{
					int idx = 0;
					int dence = resource.getFluid().getDensity(resource);
					for(FluidStack stack : this.liquid)
					{
						if(stack.isFluidEqual(resource))
						{
							stack.amount += resource.amount;
							return result;
						}
						int dence1 = stack.getFluid().getDensity(stack);
						if(dence1 < dence || (dence1 == dence && stack.getFluid().getName().compareTo(resource.getFluid().getName()) < 0))
						{
							this.liquid.add(idx, resource);
							return result;
						}
						idx++;
					}
				}
				this.liquid.addLast(resource);
			}
		}
		return result;
	}
	
	public FluidStack drain(int tankID, int maxDrain, boolean doDrain)
	{
		if(tankID == 0 ? this.totalLiquidAmount == 0 : this.totalLiquidAmount < this.capacityCache[tankID - 1])
		{
			if(this.gas.isEmpty()) return null;
			int id = this.random.nextInt(this.gas.size());//Tank will output gas randomly.
			FluidStack stack = this.gas.get(id);
			int amount = Math.min(stack.amount, maxDrain);
			if(doDrain)
			{
				if(amount == stack.amount)
				{
					this.gas.remove(id);
				}
				else
				{
					stack.amount -= amount;
				}
				this.totalGasAmount -= amount;
			}
			stack = Util.copyAmount(stack, amount);
			return stack;
		}
		else
		{
			if(this.liquid.isEmpty()) return null;
			if(tankID == 0)
			{
				FluidStack stack = this.liquid.getFirst();
				int amount = Math.min(stack.amount, maxDrain);
				if(doDrain)
				{
					if(amount == stack.amount)
					{
						this.liquid.removeFirst();
					}
					else
					{
						stack.amount -= amount;
					}
					this.totalLiquidAmount -= amount;
				}
				stack = Util.copyAmount(stack, amount);
				return stack;
			}
			else
			{
				int amount = this.capacityCache[tankID - 1];
				int idx = 0;
				for(FluidStack stack : this.liquid)
				{
					amount -= stack.amount;
					if(amount < 0)
					{
						amount = Math.min(stack.amount + amount, maxDrain);
						if(doDrain)
						{
							if(amount == stack.amount)
							{
								this.liquid.remove(idx);
							}
							else
							{
								stack.amount -= amount;
							}
							this.totalLiquidAmount -= amount;
						}
						stack = Util.copyAmount(stack, amount);
						return stack;
					}
					++idx;
				}
			}
		}
		return null;
	}
	
	public FluidStack drain(int tankID, FluidStack resource, boolean doDrain)
	{
		if(resource.getFluid().isGaseous(resource))
		{
			if(this.gas.isEmpty()) return null;
			if(this.totalLiquidAmount >= this.capacityCache[tankID]) return null;
			Iterator<FluidStack> iterator = this.gas.iterator();
			while(iterator.hasNext())
			{
				FluidStack stack = iterator.next();
				if(resource.isFluidEqual(stack))
				{
					int max = Math.min(resource.amount, stack.amount);
					if(doDrain)
					{
						if(max == stack.amount)
						{
							iterator.remove();
						}
						else
						{
							stack.amount -= max;
						}
						this.totalGasAmount -= max;
					}
					stack = Util.copyAmount(resource, max);
					return stack;
				}
			}
			return null;
		}
		else
		{
			if(this.totalLiquidAmount == 0 || tankID > 0 && this.totalLiquidAmount < this.capacityCache[tankID]) return null;
			if(this.liquid.isEmpty()) return null;
			Iterator<FluidStack> iterator = this.liquid.iterator();
			while(iterator.hasNext())
			{
				FluidStack stack = iterator.next();
				if(resource.isFluidEqual(stack))
				{
					int max = Math.min(resource.amount, stack.amount);
					if(doDrain)
					{
						if(max == stack.amount)
						{
							iterator.remove();
						}
						else
						{
							stack.amount -= max;
						}
						this.totalLiquidAmount -= max;
					}
					stack = Util.copyAmount(resource, max);
					return stack;
				}
			}
			return null;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return new AxisAlignedBB(-1, -1, -1, 1, this.tankSize + 1, 1).offset(this.pos);
	}
}