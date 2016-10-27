package ttr.core.tile;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IExplosionPowerOverride;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import ttr.api.tile.IPluginAccess;
import ttr.api.util.EnergyTrans;

public abstract class TEElectricalMachineRecipeMap extends TEMachineRecipeMap implements IEnergySink, IExplosionPowerOverride
{
	protected long energy;
	protected double untranslateEnergy;
	private byte energyAllowedSide = (byte) 0xFF;
	protected double energyInput;
	protected boolean addedInToNet = false;
	protected boolean enabledAddedInToNet = true;
	protected byte oldTier;
	protected byte newTier;
	
	public TEElectricalMachineRecipeMap(int itemInputSize, int itemOutputSize, int fluidInputSize, int fluidOutputSize)
	{
		super(itemInputSize, itemOutputSize, fluidInputSize, fluidOutputSize);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt.setLong("energy", energy);
		nbt.setByte("energyAllowedSide", energyAllowedSide);
		return super.writeToNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		energy = nbt.getLong("energy");
		energyAllowedSide = nbt.getByte("energyAllowedSide");
	}

	@Override
	protected void preUpdateEntity()
	{
		super.preUpdateEntity();
		energyInput = 0;
		oldTier = newTier;
	}

	@Override
	protected void postUpdateEntity()
	{
		super.postUpdateEntity();
		if(oldTier != newTier)
		{
			removeFromEnergyNet();
			oldTier = newTier;
			addToEnergyNet();
		}
	}
	
	protected abstract long getDefaultMaxEnergyCapacity();
	
	protected long getMaxEnergyCapacity()
	{
		long value = getDefaultMaxEnergyCapacity();
		return (getPluginLevel(IPluginAccess.energyCapacity) + 1) * value;
	}

	@Override
	public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing side)
	{
		return (energyAllowedSide & (1 << side.ordinal())) != 0;
	}

	protected void transferEnergy()
	{
		if(untranslateEnergy >= EnergyTrans.J_TO_ELE)
		{
			long value = (long) (untranslateEnergy * EnergyTrans.ELE_TO_J);
			long maxValue = getMaxEnergyCapacity();
			energy += value;
			untranslateEnergy -= value * EnergyTrans.J_TO_ELE;
			if(energy > maxValue)
			{
				energy = maxValue;
			}
		}
	}
	
	@Override
	public double getDemandedEnergy()
	{
		return getMaxEnergyCapacity() - energy;
	}

	@Override
	public int getSinkTier()
	{
		return oldTier;
	}

	protected abstract long getMaxVoltage();

	@Override
	public double injectEnergy(EnumFacing directionFrom, double amount, double voltage)
	{
		if(voltage > getMaxVoltage())
		{
			onOverpowered(amount, voltage);
			return 0;
		}
		untranslateEnergy += amount;
		energyInput += amount;
		transferEnergy();
		return amount;
	}
	
	@Override
	protected void initServer()
	{
		super.initServer();
		oldTier = newTier = (byte) getBaseTier();
		if(enabledAddedInToNet && !addedInToNet)
		{
			addToEnergyNet();
		}
	}

	protected abstract int getBaseTier();

	protected void setTier(int tier)
	{
		newTier = (byte) tier;
	}

	@Override
	public void onRemoveFromLoadedWorld()
	{
		super.onRemoveFromLoadedWorld();
		if(addedInToNet)
		{
			removeFromEnergyNet();
		}
	}
	
	protected void addToEnergyNet()
	{
		if(enabledAddedInToNet)
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			addedInToNet = true;
		}
	}

	protected void removeFromEnergyNet()
	{
		MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
		addedInToNet = false;
	}

	protected void onOverpowered(double amount, double voltage)
	{
		if(getPluginLevel(IPluginAccess.fuse) > 0)
		{
			removeFromEnergyNet();
			removePlugin(IPluginAccess.fuse);
			enabledAddedInToNet = false;
		}
		else
		{
			worldObj.createExplosion(null, pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, (float) (voltage - getMaxVoltage()) / 100F + 3, true);
		}
	}
	
	@Override
	protected long getEnergyInput()
	{
		return (long) energyInput;
	}
	
	@Override
	public boolean shouldExplode()
	{
		if(getPluginLevel(IPluginAccess.fuse) >= 0)
			return false;
		return true;
	}
	
	@Override
	public float getExplosionPower(int tier, float defaultPower)
	{
		return defaultPower;
	}
}