package ttr.core.tile.pipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import ttr.api.inventory.FluidHandlerWrapper;
import ttr.core.tile.TESynchronization;

public class TEFluidPipe extends TESynchronization
{
	public class FluidPipeHanlerWrapper extends FluidHandlerWrapper
	{
		public FluidPipeHanlerWrapper(EnumFacing facing)
		{
			super(facing, tank);
		}
		
		@Override
		public int fill(FluidStack resource, boolean doFill)
		{
			if(resource == null) return 0;
			resource = resource.copy();
			resource.amount = Math.min(resource.amount, flowSpeed - fluidIOAmount);
			int amt = super.fill(resource, doFill);
			if(doFill)
			{
				flowAmount[facing.ordinal()] -= amt;
				//				fluidIOAmount += amt;
			}
			return amt;
		}
		
		@Override
		public FluidStack drain(FluidStack resource, boolean doDrain)
		{
			if(resource == null) return null;
			resource = resource.copy();
			resource.amount = Math.min(resource.amount, flowSpeed - fluidIOAmount);
			FluidStack stack = super.drain(resource, doDrain);
			if(doDrain && stack != null && facing != null)
			{
				flowAmount[facing.ordinal()] += stack.amount;
				fluidIOAmount += stack.amount;
			}
			return stack;
		}
		
		@Override
		public FluidStack drain(int maxDrain, boolean doDrain)
		{
			if(maxDrain == 0) return null;
			maxDrain = Math.min(maxDrain, flowSpeed - fluidIOAmount);
			FluidStack stack = super.drain(maxDrain, doDrain);
			if(doDrain && stack != null && facing != null)
			{
				flowAmount[facing.ordinal()] += stack.amount;
				fluidIOAmount += stack.amount;
			}
			return stack;
		}
	}

	private final IFluidHandler[] handlers;
	
	protected final long maxTemperature;
	protected final boolean canCurrentGas;
	protected final int flowSpeed;
	protected FluidTank tank;
	protected byte sideLink = (byte) 0x3F;
	protected int fluidIOAmount;
	protected int[] lastFlowAmount = new int[6];
	protected int[] flowAmount = new int[6];

	public TEFluidPipe(int capacity, long maxTemperature, boolean canCurrentGas, int flowSpeed)
	{
		tank = new FluidTank(capacity);
		this.maxTemperature = maxTemperature;
		this.canCurrentGas = canCurrentGas;
		this.flowSpeed = flowSpeed;
		handlers = new IFluidHandler[EnumFacing.VALUES.length];
		for(int i = 0; i < handlers.length; ++i)
		{
			handlers[i] = new FluidPipeHanlerWrapper(EnumFacing.VALUES[i]);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		tank.readFromNBT(nbt.getCompoundTag("tank"));
		sideLink = nbt.getByte("sideLink");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		nbt.setByte("sideLink", sideLink);
		return super.writeToNBT(nbt);
	}

	protected boolean isFluidCanStayInPipe(Fluid fluid, FluidStack stack)
	{
		return (canCurrentGas || !fluid.isGaseous(stack));
	}

	protected boolean isFluidCanDestoryPipe(Fluid fluid, FluidStack stack)
	{
		return false;
	}

	public boolean isLink(EnumFacing facing)
	{
		if(isSideLinkable(facing))
		{
			TileEntity tile = worldObj.getTileEntity(pos.offset(facing));
			if(tile != null && tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite()))
				return true;
		}
		return false;
	}
	
	protected boolean isSideLinkable(EnumFacing facing)
	{
		return (sideLink & (1 << facing.ordinal())) != 0;
	}
	
	public void switchLink(EnumFacing facing)
	{
		sideLink ^= (1 << facing.ordinal());
		syncToNearby();
	}

	@Override
	protected void updateServer()
	{
		System.arraycopy(flowAmount, 0, lastFlowAmount, 0, 6);
		Arrays.fill(flowAmount, 0);
		fluidIOAmount = 0;
		super.updateServer();
		if(checkPipe())
		{
			flowFluidToNearby();
		}
	}

	protected boolean checkPipe()
	{
		FluidStack stack = tank.getFluid();
		if(stack != null)
		{
			if(isFluidCanDestoryPipe(stack.getFluid(), stack))
			{
				removeBlock();
				return false;
			}
			else if(stack.getFluid().getTemperature(stack) > maxTemperature)
			{
				if(Blocks.FIRE.canPlaceBlockAt(worldObj, pos))
				{
					worldObj.setBlockState(pos, Blocks.FIRE.getDefaultState());
				}
				else
				{
					removeBlock();
				}
				return false;
			}
			else if(isFluidCanStayInPipe(stack.getFluid(), stack))
			{
				tank.setFluid(null);
				return false;
			}
		}
		return stack != null;
	}
	
	protected void flowFluidToNearby()
	{
		List<EnumFacing> allowed = new ArrayList(6);
		for(EnumFacing facing : EnumFacing.VALUES)
		{
			if(isLink(facing) && lastFlowAmount[facing.ordinal()] >= 0)
			{
				allowed.add(facing);
			}
		}
		if(allowed.size() == 0) return;
		int[] suggestedFlow = new int[allowed.size()];
		int average = (flowSpeed - fluidIOAmount) / allowed.size();
		int total = 0;
		int last;
		int now;
		for(now = 0; now < allowed.size(); ++now)
		{
			if(now == 0)
			{
				last = allowed.size() - 1;
			}
			else
			{
				last = now - 1;
			}
			EnumFacing facing = allowed.get(now);
			int id = facing.ordinal();
			if(lastFlowAmount[id] > 0)
			{
				suggestedFlow[id] -= lastFlowAmount[id];
				suggestedFlow[id] += lastFlowAmount[allowed.get(last).ordinal()];
			}
			suggestedFlow[id] += average;
			total += suggestedFlow[id];
		}
		average = (total + allowed.size() - 1) / allowed.size();
		for(now = 0; now < allowed.size(); ++now)
		{
			average -= tryFlowFluidTo(allowed.get(now), suggestedFlow[now] - average) / allowed.size();
		}
		if(average > 0)
		{
			total = average * allowed.size();
			for(now = 0; now < allowed.size() && total > 0; ++now)
			{
				total -= tryFlowFluidTo(allowed.get(now), total);
			}
		}
	}
	
	protected int tryFlowFluidTo(EnumFacing facing, int amount)
	{
		TileEntity tile = getTile(facing);
		if(tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite()))
		{
			IFluidHandler handler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite());
			FluidStack stack = tank.drain(amount, false);
			if(handler.fill(stack, false) != 0)
			{
				int amt = handler.fill(stack, true);
				handlers[facing.ordinal()].drain(amt, true);
				return amount - amt;
			}
		}
		return amount;
	}

	@Override
	public void readFromDescription(NBTTagCompound nbt)
	{
		super.readFromDescription(nbt);
		sideLink = nbt.getByte("sl");
	}

	@Override
	public void writeToDescription(NBTTagCompound nbt)
	{
		super.writeToDescription(nbt);
		nbt.setByte("sl", sideLink);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && isSideLinkable(facing)) ||
				super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return (T) handlers[facing.ordinal()];
		return super.getCapability(capability, facing);
	}
}