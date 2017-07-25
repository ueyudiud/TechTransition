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
			super(facing, TEFluidPipe.this.tank);
		}
		
		@Override
		public int fill(FluidStack resource, boolean doFill)
		{
			if(resource == null) return 0;
			resource = resource.copy();
			resource.amount = Math.min(resource.amount, TEFluidPipe.this.flowSpeed - TEFluidPipe.this.fluidIOAmount);
			int amt = super.fill(resource, doFill);
			if(doFill)
			{
				TEFluidPipe.this.flowAmount[this.facing.ordinal()] -= amt;
				//				fluidIOAmount += amt;
			}
			return amt;
		}
		
		@Override
		public FluidStack drain(FluidStack resource, boolean doDrain)
		{
			if(resource == null) return null;
			resource = resource.copy();
			resource.amount = Math.min(resource.amount, TEFluidPipe.this.flowSpeed - TEFluidPipe.this.fluidIOAmount);
			FluidStack stack = super.drain(resource, doDrain);
			if(doDrain && stack != null && this.facing != null)
			{
				TEFluidPipe.this.flowAmount[this.facing.ordinal()] += stack.amount;
				TEFluidPipe.this.fluidIOAmount += stack.amount;
			}
			return stack;
		}
		
		@Override
		public FluidStack drain(int maxDrain, boolean doDrain)
		{
			if(maxDrain == 0) return null;
			maxDrain = Math.min(maxDrain, TEFluidPipe.this.flowSpeed - TEFluidPipe.this.fluidIOAmount);
			FluidStack stack = super.drain(maxDrain, doDrain);
			if(doDrain && stack != null && this.facing != null)
			{
				TEFluidPipe.this.flowAmount[this.facing.ordinal()] += stack.amount;
				TEFluidPipe.this.fluidIOAmount += stack.amount;
			}
			return stack;
		}
	}
	
	private final IFluidHandler[] handlers;
	
	protected final long maxTemperature;
	protected final boolean canCurrentGas;
	protected final int flowSpeed;
	public final short size;
	protected FluidTank tank;
	protected byte sideLink = (byte) 0x3F;
	protected int fluidIOAmount;
	protected int[] lastFlowAmount = new int[6];
	protected int[] flowAmount = new int[6];
	
	public TEFluidPipe(int capacity, int size, long maxTemperature, boolean canCurrentGas, int flowSpeed)
	{
		this.size = (short) size;
		this.tank = new FluidTank(capacity);
		this.maxTemperature = maxTemperature;
		this.canCurrentGas = canCurrentGas;
		this.flowSpeed = flowSpeed;
		this.handlers = new IFluidHandler[EnumFacing.VALUES.length];
		for(int i = 0; i < this.handlers.length; ++i)
		{
			this.handlers[i] = new FluidPipeHanlerWrapper(EnumFacing.VALUES[i]);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.tank.readFromNBT(nbt.getCompoundTag("tank"));
		this.sideLink = nbt.getByte("sideLink");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt.setTag("tank", this.tank.writeToNBT(new NBTTagCompound()));
		nbt.setByte("sideLink", this.sideLink);
		return super.writeToNBT(nbt);
	}
	
	protected boolean isFluidCanStayInPipe(Fluid fluid, FluidStack stack)
	{
		return (this.canCurrentGas || !fluid.isGaseous(stack));
	}
	
	protected boolean isFluidCanDestoryPipe(Fluid fluid, FluidStack stack)
	{
		return false;
	}
	
	public boolean isLink(EnumFacing facing)
	{
		if(isSideLinkable(facing))
		{
			TileEntity tile = this.worldObj.getTileEntity(this.pos.offset(facing));
			if(tile != null && tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite()))
				return true;
		}
		return false;
	}
	
	protected boolean isSideLinkable(EnumFacing facing)
	{
		return (this.sideLink & (1 << facing.ordinal())) != 0;
	}
	
	public void switchLink(EnumFacing facing)
	{
		this.sideLink ^= (1 << facing.ordinal());
		syncToNearby();
	}
	
	@Override
	protected void updateServer()
	{
		System.arraycopy(this.flowAmount, 0, this.lastFlowAmount, 0, 6);
		Arrays.fill(this.flowAmount, 0);
		this.fluidIOAmount = 0;
		super.updateServer();
		if(checkPipe())
		{
			flowFluidToNearby();
		}
	}
	
	protected boolean checkPipe()
	{
		FluidStack stack = this.tank.getFluid();
		if(stack != null)
		{
			if(isFluidCanDestoryPipe(stack.getFluid(), stack))
			{
				removeBlock();
				return false;
			}
			else if(stack.getFluid().getTemperature(stack) > this.maxTemperature)
			{
				if(Blocks.FIRE.canPlaceBlockAt(this.worldObj, this.pos))
				{
					this.worldObj.setBlockState(this.pos, Blocks.FIRE.getDefaultState());
				}
				else
				{
					removeBlock();
				}
				return false;
			}
			else if(!isFluidCanStayInPipe(stack.getFluid(), stack))
			{
				this.tank.setFluid(null);
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
			if(isLink(facing) && this.lastFlowAmount[facing.ordinal()] >= 0)
			{
				allowed.add(facing);
			}
		}
		if(allowed.size() == 0) return;
		int[] suggestedFlow = new int[allowed.size()];
		int average = (this.flowSpeed - this.fluidIOAmount) / allowed.size();
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
			if(this.lastFlowAmount[id] > 0)
			{
				suggestedFlow[now] -= this.lastFlowAmount[id];
				suggestedFlow[now] += this.lastFlowAmount[allowed.get(last).ordinal()];
			}
			suggestedFlow[now] += average;
			total += suggestedFlow[now];
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
		if (this.tank.getFluid() == null || amount <= 0) return 0;
		TileEntity tile = getTile(facing);
		if(tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite()))
		{
			IFluidHandler handler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite());
			FluidStack stack = this.tank.drain(amount, false);
			if (stack == null) return 0;
			if(handler.fill(stack, false) != 0)
			{
				int amt = handler.fill(stack, true);
				this.handlers[facing.ordinal()].drain(amt, true);
				return amount - amt;
			}
		}
		return amount;
	}
	
	@Override
	public void readFromDescription(NBTTagCompound nbt)
	{
		super.readFromDescription(nbt);
		this.sideLink = nbt.getByte("sl");
	}
	
	@Override
	public void writeToDescription(NBTTagCompound nbt)
	{
		super.writeToDescription(nbt);
		nbt.setByte("sl", this.sideLink);
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
			return (T) this.handlers[facing.ordinal()];
		return super.getCapability(capability, facing);
	}
}