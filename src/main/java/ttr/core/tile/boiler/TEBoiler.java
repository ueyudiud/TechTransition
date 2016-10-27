package ttr.core.tile.boiler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.inventory.WaterTank;
import ttr.api.tile.IPluginAccess;
import ttr.api.util.EnergyTrans;
import ttr.api.util.Log;
import ttr.core.tile.ITankSyncable;
import ttr.core.tile.TEMachineInventory;
import ttr.load.TTrFluids;

public abstract class TEBoiler extends TEMachineInventory implements ITankSyncable
{
	private static final int STEAM_PER_WATER = 100;
	
	public class SteamTank implements IFluidHandler
	{
		public final BoilerTankProperties tankSteamProperties = new BoilerTankProperties();
		
		@Override
		public IFluidTankProperties[] getTankProperties()
		{
			return new IFluidTankProperties[]{tankSteamProperties};
		}
		
		@Override
		public int fill(FluidStack resource, boolean doFill)
		{
			return 0;
		}
		
		@Override
		public FluidStack drain(FluidStack resource, boolean doDrain)
		{
			return resource.getFluid() == TTrFluids.steam ? drain(resource.amount, doDrain) : null;
		}
		
		@Override
		public FluidStack drain(int maxDrain, boolean doDrain)
		{
			int value = Math.min(maxDrain, amount);
			if(value == 0) return null;
			if(doDrain)
			{
				amount -= value;
			}
			return new FluidStack(TTrFluids.steam, value);
		}
	}

	public class BoilerTankProperties implements IFluidTankProperties
	{
		@Override
		public FluidStack getContents()
		{
			return amount <= 0 ? new FluidStack(TTrFluids.steam, amount) : null;
		}
		
		@Override
		public int getCapacity()
		{
			return realSteamCap;
		}
		
		@Override
		public boolean canFill()
		{
			return false;
		}
		
		@Override
		public boolean canDrain()
		{
			return true;
		}
		
		@Override
		public boolean canFillFluidType(FluidStack fluidStack)
		{
			return false;
		}
		
		@Override
		public boolean canDrainFluidType(FluidStack fluidStack)
		{
			return fluidStack.getFluid() == TTrFluids.steam;
		}
	}

	protected final float efficiency;
	public final int maxTemperature;
	public final float outputPressure;
	public final int realSteamCap;
	private int buf = 0;
	public int temperature = 298;
	public float calcification = 0.0F;
	protected WaterTank tank1;
	protected final SteamTank tank2 = new SteamTank();
	public int amount;
	
	public TEBoiler(int maxTemperature, float outputPressure, int waterCap, int steamCap, float efficiency)
	{
		tank1 = new WaterTank(waterCap)
		{
			@Override
			public int fill(FluidStack resource, boolean doFill)
			{
				if ((doFill) && (resource != null) && (resource.amount > 0) && (temperature > 373) && ((fluid == null) || (fluid.amount == 0)))
				{
					TEBoiler.this.removeBlock();
					TEBoiler.this.worldObj.createExplosion(null, TEBoiler.this.pos.getX() + 0.5D, TEBoiler.this.pos.getY() + 0.5D, TEBoiler.this.pos.getZ() + 0.5D, 2.0F + (temperature - 300) * 0.01F, false);
					return 0;
				}
				return super.fill(resource, doFill);
			}
		};
		realSteamCap = steamCap;
		this.maxTemperature = maxTemperature;
		this.outputPressure = outputPressure;
		this.efficiency = efficiency;
	}

	@Override
	protected EnumFacing[] getMainAllowedFacing()
	{
		return EnumFacing.HORIZONTALS;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		tank1.readFromNBT(nbt.getCompoundTag("tank1"));
		amount = nbt.getInteger("amount");
		temperature = nbt.getInteger("temp");
		calcification = nbt.getFloat("calcify");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt.setTag("tank1", tank1.writeToNBT(new NBTTagCompound()));
		nbt.setInteger("amount", amount);
		nbt.setInteger("temp", temperature);
		nbt.setFloat("calcify", calcification);
		return super.writeToNBT(nbt);
	}

	@Override
	protected void updateServer()
	{
		if(checkCasing())
		{
			boilWater();
		}
		super.updateServer();
		tryFlowSteam();
	}
	
	protected int getRealSteamCap()
	{
		return (1 + getPluginLevel(IPluginAccess.tankCapacity)) * realSteamCap;
	}
	
	protected boolean checkCasing()
	{
		if (maxTemperature < temperature)
		{
			removeBlock();
			worldObj.createExplosion(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 2.0F, true);
			return false;
		}
		if (amount > getRealSteamCap())
		{
			if(worldObj.isAirBlock(pos.up()))
			{
				amount = getRealSteamCap() * 2 / 3;
				//Display sound.
				worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5 + (random.nextFloat() - random.nextFloat()) * 0.5, pos.getY() + 1, pos.getZ() + 0.5 + (random.nextFloat() - random.nextFloat()) * 0.5, 0, 0.2, 0);
			}
			else
			{
				removeBlock();
				worldObj.createExplosion(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 2.0F, true);
				return false;
			}
		}
		return true;
	}

	protected int getWaterBoilPoint()
	{
		return 373;
	}
	
	protected abstract int getPower();
	
	protected void boilWater()
	{
		int bp = getWaterBoilPoint();
		if (temperature >= bp)
		{
			int speed = (int) (getPower() * EnergyTrans.J_TO_STEAM * efficiency * (1.0F - calcification));
			int produce = useWater(speed);
			amount += produce;
		}
		else
		{
			int speed = (int) (Math.log1p(bp - temperature) * amount / (tank1.getFluidAmount() + 1));
			amount -= speed;
			if (amount < 0)
			{
				amount = 0;
			}
		}
	}


	protected int useWater(int speed)
	{
		boolean flag = !tank1.isDistilledWater();
		int ret;
		if (speed > buf)
		{
			int ex = speed / 100 + 1;
			FluidStack stack = tank1.drain(ex, true);
			if (stack == null)
			{
				ret = buf;
			}
			else
			{
				buf += stack.amount * 100;
				ret = Math.min(buf, speed);
				buf -= ret;
			}
		}
		else
		{
			buf -= speed;
			ret = speed;
		}
		if ((flag) && (random.nextInt(6) == 0))
		{
			calcification += 1.0E-008F * ret;
		}
		return ret;
	}

	protected void tryFlowSteam()
	{
		float pressAmount = getPressure();
		if (pressAmount > outputPressure)
		{
			for(EnumFacing facing : EnumFacing.VALUES)
			{
				if(facing != EnumFacing.DOWN && facing != getRotation())
				{
					sendSteamTo(facing);
				}
			}
		}
	}

	protected void sendSteamTo(EnumFacing face)
	{
		try
		{
			TileEntity tile = worldObj.getTileEntity(pos.offset(face));
			if (tile != null &&
					tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, face.getOpposite()))
			{
				IFluidHandler handler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, face.getOpposite());
				FluidStack stack = new FluidStack(TTrFluids.steam, Math.min(getMaxOutput(), amount));
				if (handler.fill(stack, false) != 0)
				{
					amount -= handler.fill(stack, true);
				}
				return;
			}
		}
		catch (Exception exception)
		{
			Log.warn("Catching an exception during send steam to other tile.", exception);
		}
	}
	
	protected abstract int getMaxOutput();

	protected float getPressure()
	{
		return (float) amount / (float) getRealSteamCap();
	}

	@Override
	public int getFieldCount()
	{
		return 2;
	}

	@Override
	public int getField(int id)
	{
		switch (id)
		{
		case 0 : return amount;
		case 1 : return temperature;
		default: return 0;
		}
	}

	@Override
	public void setField(int id, int value)
	{
		switch(id)
		{
		case 0 : amount = value; break;
		case 1 : temperature = value; break;
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? true : super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return (T) (facing == getFacing("steam") ? tank2 : tank1);
		return super.getCapability(capability, facing);
	}
	
	@Override
	public int getTankSize()
	{
		return 1;
	}
	
	@Override
	public FluidTankInfo getInfomation(int id)
	{
		return tank1.getInfo();
	}

	@Override
	public FluidStack getStackInTank(int id)
	{
		return tank1.getFluid();
	}
	
	@Override
	public void setFluidStackToTank(int id, FluidStack stack)
	{
		tank1.setFluid(stack);
	}
	
	@SideOnly(Side.CLIENT)
	public int getWaterProgress(int scale)
	{
		return (int) ((float) (tank1.getFluidAmount() * scale) / (float) tank1.getCapacity());
	}
	
	@SideOnly(Side.CLIENT)
	public int getSteamProgress(int scale)
	{
		return (int) ((float) (amount * scale) / (float) getRealSteamCap());
	}
	
	@SideOnly(Side.CLIENT)
	public int getTemperatureProgress(int scale)
	{
		return (int) ((float) (temperature * scale) / (float) maxTemperature);
	}

	@Override
	protected NBTTagCompound writeNBTToStack()
	{
		if(calcification != 0)
		{
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setFloat("calcification", calcification);
			return nbt;
		}
		return super.writeNBTToStack();
	}

	@Override
	protected void readFromNBTAtStack(NBTTagCompound nbt)
	{
		super.readFromNBTAtStack(nbt);
		calcification = nbt.getFloat("calcification");
	}
	
	@Override
	protected boolean isPluginAllowed(String key)
	{
		return key.startsWith(IPluginAccess.tankCapacity);
	}
}