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
import ttr.api.util.Util;
import ttr.core.tile.ITankSyncable;
import ttr.core.tile.TEMachineInventory;
import ttr.load.TTrIBF;

public abstract class TEBoiler extends TEMachineInventory implements ITankSyncable
{
	private static final int STEAM_PER_WATER = 100;
	
	public class SteamTank implements IFluidHandler
	{
		public final BoilerTankProperties tankSteamProperties = new BoilerTankProperties();
		
		@Override
		public IFluidTankProperties[] getTankProperties()
		{
			return new IFluidTankProperties[]{this.tankSteamProperties};
		}
		
		@Override
		public int fill(FluidStack resource, boolean doFill)
		{
			return 0;
		}
		
		@Override
		public FluidStack drain(FluidStack resource, boolean doDrain)
		{
			return resource.getFluid() == TTrIBF.steam ? drain(resource.amount, doDrain) : null;
		}
		
		@Override
		public FluidStack drain(int maxDrain, boolean doDrain)
		{
			int value = Math.min(maxDrain, TEBoiler.this.amount);
			if(value == 0) return null;
			if(doDrain)
			{
				TEBoiler.this.amount -= value;
			}
			return new FluidStack(TTrIBF.steam, value);
		}
	}
	
	public class BoilerTankProperties implements IFluidTankProperties
	{
		@Override
		public FluidStack getContents()
		{
			return TEBoiler.this.amount <= 0 ? new FluidStack(TTrIBF.steam, TEBoiler.this.amount) : null;
		}
		
		@Override
		public int getCapacity()
		{
			return TEBoiler.this.realSteamCap;
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
			return fluidStack.getFluid() == TTrIBF.steam;
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
		this.tank1 = new WaterTank(waterCap)
		{
			@Override
			public int fill(FluidStack resource, boolean doFill)
			{
				if ((doFill) && (resource != null) && (resource.amount > 0) && (TEBoiler.this.temperature > 373) && ((this.fluid == null) || (this.fluid.amount == 0)))
				{
					TEBoiler.this.removeBlock();
					TEBoiler.this.worldObj.createExplosion(null, TEBoiler.this.pos.getX() + 0.5D, TEBoiler.this.pos.getY() + 0.5D, TEBoiler.this.pos.getZ() + 0.5D, 2.0F + (TEBoiler.this.temperature - 300) * 0.01F, false);
					return 0;
				}
				return super.fill(resource, doFill);
			}
		};
		this.realSteamCap = steamCap;
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
		this.tank1.readFromNBT(nbt.getCompoundTag("tank1"));
		this.amount = nbt.getInteger("amount");
		this.temperature = nbt.getInteger("temp");
		this.calcification = nbt.getFloat("calcify");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt.setTag("tank1", this.tank1.writeToNBT(new NBTTagCompound()));
		nbt.setInteger("amount", this.amount);
		nbt.setInteger("temp", this.temperature);
		nbt.setFloat("calcify", this.calcification);
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
		return (1 + getPluginLevel(IPluginAccess.tankCapacity)) * this.realSteamCap;
	}
	
	protected boolean checkCasing()
	{
		if (this.maxTemperature < this.temperature)
		{
			removeBlock();
			this.worldObj.createExplosion(null, this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, 2.0F, true);
			return false;
		}
		if (this.amount > getRealSteamCap())
		{
			if(this.worldObj.isAirBlock(this.pos.up()))
			{
				this.amount = getRealSteamCap() * 2 / 3;
				//Display sound.
				this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.pos.getX() + 0.5 + (this.random.nextFloat() - this.random.nextFloat()) * 0.5, this.pos.getY() + 1, this.pos.getZ() + 0.5 + (this.random.nextFloat() - this.random.nextFloat()) * 0.5, 0, 0.2, 0);
			}
			else
			{
				removeBlock();
				this.worldObj.createExplosion(null, this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, 2.0F, true);
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
		if (this.temperature >= bp)
		{
			int speed = (int) (getPower() * EnergyTrans.J_TO_STEAM * this.efficiency * (1.0F - this.calcification));
			int produce = useWater(speed);
			this.amount += produce;
		}
		else
		{
			int speed = (int) (Math.log1p(bp - this.temperature) * this.amount / (this.tank1.getFluidAmount() + 1));
			this.amount -= speed;
			if (this.amount < 0)
			{
				this.amount = 0;
			}
		}
	}
	
	
	protected int useWater(int speed)
	{
		boolean flag = !this.tank1.isDistilledWater();
		int ret;
		if (speed > this.buf)
		{
			int ex = speed / 100 + 1;
			FluidStack stack = this.tank1.drain(ex, true);
			if (stack == null)
			{
				ret = this.buf;
			}
			else
			{
				this.buf += stack.amount * 100;
				ret = Math.min(this.buf, speed);
				this.buf -= ret;
			}
		}
		else
		{
			this.buf -= speed;
			ret = speed;
		}
		if ((flag) && (this.random.nextInt(6) == 0))
		{
			this.calcification += 1.0E-008F * ret;
		}
		return ret;
	}
	
	protected void tryFlowSteam()
	{
		float pressAmount = getPressure();
		if (pressAmount > this.outputPressure)
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
			TileEntity tile = this.worldObj.getTileEntity(this.pos.offset(face));
			if (tile != null)
			{
				if(tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, face.getOpposite()))
				{
					IFluidHandler handler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, face.getOpposite());
					FluidStack stack = Util.getSteam(Math.min(getMaxOutput(), this.amount));
					if (handler.fill(stack, false) != 0)
					{
						this.amount -= handler.fill(stack, true);
					}
				}
				else if(tile instanceof net.minecraftforge.fluids.IFluidHandler)
				{
					net.minecraftforge.fluids.IFluidHandler handler = (net.minecraftforge.fluids.IFluidHandler) tile;
					FluidStack stack = Util.getSteam(Math.min(getMaxOutput(), this.amount));
					if (handler.fill(face.getOpposite(), stack, false) != 0)
					{
						this.amount -= handler.fill(face.getOpposite(), stack, true);
					}
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
		return (float) this.amount / (float) getRealSteamCap();
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
		case 0 : return this.amount;
		case 1 : return this.temperature;
		default: return 0;
		}
	}
	
	@Override
	public void setField(int id, int value)
	{
		switch(id)
		{
		case 0 : this.amount = value; break;
		case 1 : this.temperature = value; break;
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
			return (T) (facing == getFacing("steam") ? this.tank2 : this.tank1);
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
		return this.tank1.getInfo();
	}
	
	@Override
	public FluidStack getStackInTank(int id)
	{
		return this.tank1.getFluid();
	}
	
	@Override
	public void setFluidStackToTank(int id, FluidStack stack)
	{
		this.tank1.setFluid(stack);
	}
	
	@SideOnly(Side.CLIENT)
	public int getWaterProgress(int scale)
	{
		return (int) ((float) (this.tank1.getFluidAmount() * scale) / (float) this.tank1.getCapacity());
	}
	
	@SideOnly(Side.CLIENT)
	public int getSteamProgress(int scale)
	{
		return (int) ((float) (this.amount * scale) / (float) getRealSteamCap());
	}
	
	@SideOnly(Side.CLIENT)
	public int getTemperatureProgress(int scale)
	{
		return (int) ((float) ((this.temperature - 298) * scale) / (float) (this.maxTemperature - 298));
	}
	
	@Override
	protected NBTTagCompound writeNBTToStack()
	{
		if(this.calcification != 0)
		{
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setFloat("calcification", this.calcification);
			return nbt;
		}
		return super.writeNBTToStack();
	}
	
	@Override
	protected void readFromNBTAtStack(NBTTagCompound nbt)
	{
		super.readFromNBTAtStack(nbt);
		this.calcification = nbt.getFloat("calcification");
	}
	
	@Override
	protected boolean isPluginAllowed(String key)
	{
		return key.startsWith(IPluginAccess.tankCapacity);
	}
}