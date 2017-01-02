package ttr.core.tile;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItemManager;
import net.minecraft.item.ItemStack;
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
	protected boolean accessConfigGUIOpen()
	{
		return true;
	}
	
	@Override
	protected void updateServer()
	{
		chargeTileFromSlot(0);
		super.updateServer();
	}
	
	protected void chargeTileFromSlot(int id)
	{
		double need = getDemandedEnergy();
		if(need > 0)
		{
			ItemStack stack = this.inventory.getStackInSlot(id);
			if(stack != null)
			{
				IElectricItemManager manager = ElectricItem.manager;
				if(manager.getTier(stack) <= getSinkTier())
				{
					double charge = manager.discharge(stack, need, getSinkTier(), false, false, false);
					if(charge > 0)
					{
						this.untranslateEnergy += charge;
						transferEnergy();
					}
				}
			}
		}
	}
	
	@Override
	protected void initServer()
	{
		super.initServer();
		long cap = getMaxEnergyCapacity();
		if(this.energy > cap)
		{
			this.energy = cap;
		}
	}
	
	@Override
	protected long getDefaultMaxEnergyCapacity()
	{
		return this.energyCapacity;
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
		return this.baseTier + getPluginLevel(IPluginAccess.voltageUpgrade);
	}
	
	@Override
	protected boolean useEnergy()
	{
		if(this.energy >= this.minPower)
		{
			this.energy -= this.minPower;
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