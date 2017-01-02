/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.tile.generator;

import ic2.api.energy.tile.IKineticSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import ttr.api.data.Capabilities;
import ttr.api.inventory.IItemHandlerIO;
import ttr.api.item.ITurbine;
import ttr.api.item.ITurbine.TurbineInfo;
import ttr.api.util.EnergyTrans;
import ttr.api.util.Util;
import ttr.core.tile.TEMachineBase;

/**
 * Created at 2016年12月28日 上午12:05:03
 * @author ueyudiud
 */
public class TESmallSteamTurbine extends TEMachineBase implements IKineticSource, IItemHandlerIO
{
	private static final int MAX_CAPACITY = 1024;
	private static final int Working = 0x3;
	
	private int inputSteam;
	private long energyAmount;
	private int puredWaterLevel = 0;
	private int currentTransfer;
	private int lastTickTransfer;
	private ItemStack turbine;
	
	private IFluidHandler handler1 = new IFluidHandler()
	{
		@Override
		public IFluidTankProperties[] getTankProperties()
		{
			return new IFluidTankProperties[]{new FluidTankProperties(Util.getDistilledWater(TESmallSteamTurbine.this.puredWaterLevel), 1000, false, true)};
		}
		
		@Override
		public int fill(FluidStack resource, boolean doFill)
		{
			return 0;
		}
		
		@Override
		public FluidStack drain(int maxDrain, boolean doDrain)
		{
			int amount = Math.min(maxDrain, TESmallSteamTurbine.this.puredWaterLevel);
			FluidStack result = Util.getDistilledWater(amount);
			if(doDrain)
			{
				TESmallSteamTurbine.this.puredWaterLevel -= amount;
			}
			return result;
		}
		
		@Override
		public FluidStack drain(FluidStack resource, boolean doDrain)
		{
			return Util.isDistilledWater(resource) ? drain(resource.amount, doDrain) : null;
		}
	};
	
	private IFluidHandler handler2 = new IFluidHandler()
	{
		@Override
		public IFluidTankProperties[] getTankProperties()
		{
			return new IFluidTankProperties[]{new FluidTankProperties(Util.getSteam(TESmallSteamTurbine.this.inputSteam), 1000, true, false)};
		}
		
		@Override
		public int fill(FluidStack resource, boolean doFill)
		{
			if(Util.isSteam(resource))
			{
				int amount = Math.min(1000 - TESmallSteamTurbine.this.inputSteam, resource.amount);
				if(doFill)
				{
					TESmallSteamTurbine.this.inputSteam += amount;
				}
				return amount;
			}
			return 0;
		}
		
		@Override
		public FluidStack drain(int maxDrain, boolean doDrain)
		{
			return null;
		}
		
		@Override
		public FluidStack drain(FluidStack resource, boolean doDrain)
		{
			return null;
		}
	};
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("inputSteam", this.inputSteam);
		nbt.setLong("energyAmount", this.energyAmount);
		nbt.setInteger("puredWaterLevel", this.puredWaterLevel);
		if(this.turbine != null)
		{
			nbt.setTag("turbine", this.turbine.writeToNBT(new NBTTagCompound()));
		}
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.inputSteam = nbt.getInteger("inputSteam");
		this.energyAmount = nbt.getLong("energyAmount");
		this.puredWaterLevel = nbt.getInteger("puredWaterLevel");
		if(nbt.hasKey("turbine"))
		{
			this.turbine = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("turbine"));
		}
	}
	
	@Override
	protected EnumFacing[] getMainAllowedFacing()
	{
		return EnumFacing.VALUES;
	}
	
	@Override
	protected void updateServer()
	{
		this.lastTickTransfer = this.currentTransfer;
		this.currentTransfer = 0;
		if(this.lastTickTransfer > 0)
		{
			enable(Working);
		}
		else
		{
			disable(Working);
		}
		super.updateServer();
		if(this.inputSteam > 0)
		{
			TurbineInfo info = getTurbine();
			if(info == null) return;
			int transfer = Math.min(info.maxTransferLimit, this.inputSteam);
			double t1 = transfer * EnergyTrans.STEAM_TO_J;
			this.energyAmount += t1 * info.efficiency;
			if(this.energyAmount > MAX_CAPACITY)
			{
				this.energyAmount = MAX_CAPACITY;
			}
			this.inputSteam -= transfer;
			Util.damageOrDischargeItem(this.turbine, -1, 1, null);
		}
	}
	
	@Override
	public boolean isActived()
	{
		return is(Working);
	}
	
	@Override
	public int maxrequestkineticenergyTick(EnumFacing directionFrom)
	{
		if(directionFrom == this.facing)
		{
			TurbineInfo info = getTurbine();
			return info != null ? (int) (Math.min(info.maxOutput, this.energyAmount) * EnergyTrans.J_TO_RU) : 0;
		}
		return 0;
	}
	
	@Override
	public int requestkineticenergy(EnumFacing directionFrom, int requestkineticenergy)
	{
		if(directionFrom != this.facing) return 0;
		TurbineInfo info = getTurbine();
		if(info == null) return 0;
		int max = (int) (Math.min(this.energyAmount, info.maxOutput) * EnergyTrans.J_TO_RU);
		if(max == 0) return 0;
		this.currentTransfer += max * EnergyTrans.RU_TO_J;
		this.energyAmount -= max * EnergyTrans.RU_TO_J;
		this.puredWaterLevel = Math.min(this.puredWaterLevel + this.random.nextInt(max / 5 + 1), 1000);
		return max;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && facing != this.facing) ||
				capability == Capabilities.ITEM_HANDLER_IO || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && facing != this.facing)
		{
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(facing == this.facing.getOpposite() ? this.handler2 : this.handler1);
		}
		if(capability == Capabilities.ITEM_HANDLER_IO)
		{
			return Capabilities.ITEM_HANDLER_IO.cast(this);
		}
		return super.getCapability(capability, facing);
	}
	
	private TurbineInfo getTurbine()
	{
		return this.turbine == null ? null : ((ITurbine) this.turbine.getItem()).getTurbineInfo(this.turbine);
	}
	
	@Override
	public boolean canExtractItem()
	{
		return this.turbine != null;
	}
	
	@Override
	public boolean canInsertItem()
	{
		return this.turbine == null;
	}
	
	@Override
	public ItemStack extractItem(int size, EnumFacing direction, boolean simulate)
	{
		if(this.turbine == null) return null;
		ItemStack result = this.turbine.copy();
		if(!simulate)
		{
			this.turbine = null;
		}
		return result;
	}
	
	@Override
	public int tryInsertItem(ItemStack stack, EnumFacing direction, boolean simulate)
	{
		if(this.turbine != null || !(stack.getItem() instanceof ITurbine)) return 0;
		if(!simulate)
		{
			this.turbine = Util.copyAmount(stack, 1);
		}
		return 1;
	}
}