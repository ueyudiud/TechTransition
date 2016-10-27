package ttr.core.tile.machine.steam;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import ttr.api.tile.ISteamInputHatch;
import ttr.core.tile.TEMachineBase;
import ttr.load.TTrFluids;

public class TESteamInputHatch extends TEMachineBase
implements ISteamInputHatch, IFluidHandler
{
	public static class TESteamInputHatchBronze extends TESteamInputHatch
	{
		public TESteamInputHatchBronze()
		{
			super(4000, 50);
		}
	}
	public static class TESteamInputHatchSteel extends TESteamInputHatch
	{
		public TESteamInputHatchSteel()
		{
			super(16000, 200);
		}
	}

	protected final int capacity;
	protected final int maxInputSpeed;
	protected int input;
	protected int amount;
	
	public TESteamInputHatch(int capacity, int maxInputSpeed)
	{
		this.capacity = capacity;
		this.maxInputSpeed = maxInputSpeed;
	}
	
	@Override
	protected EnumFacing[] getMainAllowedFacing()
	{
		return EnumFacing.VALUES;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("amount", amount);
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		amount = nbt.getInteger("amount");
		super.readFromNBT(nbt);
	}
	
	@Override
	protected void preUpdateEntity()
	{
		super.preUpdateEntity();
		input = 0;
	}

	@Override
	public boolean isActived()
	{
		return false;
	}

	@Override
	public final int getCapacity()
	{
		return capacity;
	}
	
	@Override
	public int getSteamAmount()
	{
		return amount;
	}
	
	@Override
	public int inputSteam(int amount, boolean process)
	{
		int ret = Math.min(amount, maxInputSpeed - input);
		if(ret > this.amount)
		{
			ret = this.amount;
		}
		if(process)
		{
			this.amount -= ret;
			input += ret;
		}
		return ret;
	}

	@Override
	public IFluidTankProperties[] getTankProperties()
	{
		return new IFluidTankProperties[]{
				new IFluidTankProperties()
				{
					@Override
					public FluidStack getContents()
					{
						return amount != 0 ? new FluidStack(TTrFluids.steam, amount) : null;
					}
					
					@Override
					public int getCapacity()
					{
						return capacity;
					}
					
					@Override
					public boolean canFillFluidType(FluidStack fluidStack)
					{
						return fluidStack.getFluid() == TTrFluids.steam;
					}
					
					@Override
					public boolean canFill()
					{
						return true;
					}
					
					@Override
					public boolean canDrainFluidType(FluidStack fluidStack)
					{
						return false;
					}
					
					@Override
					public boolean canDrain()
					{
						return false;
					}
				}
		};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill)
	{
		if(resource.getFluid() == TTrFluids.steam)
		{
			int ret = Math.min(resource.amount, capacity - amount);
			if(doFill)
			{
				amount += ret;
			}
			return ret;
		}
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain)
	{
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		return null;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && facing == getRotation() ? true : super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && facing == getRotation())
			return (T) this;
		return super.getCapability(capability, facing);
	}
}