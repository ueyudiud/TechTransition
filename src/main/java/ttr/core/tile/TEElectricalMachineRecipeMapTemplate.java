package ttr.core.tile;

import ttr.api.tile.IPluginAccess;
import ttr.api.util.EnergyTrans;

public abstract class TEElectricalMachineRecipeMapTemplate extends TEElectricalMachineRecipeMap
{
	protected final long energyCapacity;
	protected final int baseTier;

	public TEElectricalMachineRecipeMapTemplate(int itemInputSize, int itemOutputSize, int fluidInputSize,
			int fluidOutputSize, long energyCapacity, int baseTier)
	{
		super(itemInputSize, itemOutputSize, fluidInputSize, fluidOutputSize);
		this.energyCapacity = energyCapacity;
		this.baseTier = baseTier;
	}
	
	@Override
	protected void initServer()
	{
		super.initServer();
		long cap = getMaxEnergyCapacity();
		if(energy > cap)
		{
			energy = cap;
		}
	}

	@Override
	protected long getDefaultMaxEnergyCapacity()
	{
		return energyCapacity;
	}

	@Override
	protected long getMaxVoltage()
	{
		switch (getSinkTier())
		{
		case 0 :
			return 32;
		case 1 :
			return 128;
		case 2 :
			return 512;
		case 3 :
			return 2048;
		case 4 :
			return 8192;
		case 5 :
			return 32768;
		default:
			return Long.MAX_VALUE;
		}
	}

	@Override
	protected int getBaseTier()
	{
		return baseTier + getPluginLevel(IPluginAccess.voltageUpgrade);
	}

	@Override
	protected boolean useEnergy()
	{
		if(energy >= minPower)
		{
			energy -= minPower;
			return true;
		}
		return false;
	}

	@Override
	protected long getPower()
	{
		return (long) (getMaxVoltage() * EnergyTrans.ELE_TO_J);
	}
}