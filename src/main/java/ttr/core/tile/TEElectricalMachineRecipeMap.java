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
		nbt.setLong("energy", this.energy);
		nbt.setByte("energyAllowedSide", this.energyAllowedSide);
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.energy = nbt.getLong("energy");
		this.energyAllowedSide = nbt.getByte("energyAllowedSide");
	}
	
	@Override
	protected void preUpdateEntity()
	{
		super.preUpdateEntity();
		this.energyInput = 0;
		this.oldTier = this.newTier;
		if(!this.addedInToNet)
		{
			addToEnergyNet();
		}
	}
	
	@Override
	protected void postUpdateEntity()
	{
		super.postUpdateEntity();
		if(this.oldTier != this.newTier)
		{
			removeFromEnergyNet();
			this.oldTier = this.newTier;
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
		return (this.energyAllowedSide & (1 << side.ordinal())) != 0;
	}
	
	protected void transferEnergy()
	{
		if(this.untranslateEnergy >= EnergyTrans.J_TO_ELE)
		{
			long value = (long) (this.untranslateEnergy * EnergyTrans.ELE_TO_J);
			long maxValue = getMaxEnergyCapacity();
			long translated = Math.min(maxValue - this.energy, value);
			this.energy += translated;
			this.untranslateEnergy -= translated * EnergyTrans.J_TO_ELE;
		}
	}
	
	@Override
	public double getDemandedEnergy()
	{
		return (getMaxEnergyCapacity() - this.energy) * EnergyTrans.J_TO_ELE - this.untranslateEnergy;
	}
	
	@Override
	public int getSinkTier()
	{
		return this.oldTier;
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
		double amount1 = Math.min(amount, getMaxEnergyCapacity() - this.energy - this.untranslateEnergy);
		this.untranslateEnergy += amount1;
		this.energyInput += amount1;
		transferEnergy();
		return amount - amount1;
	}
	
	@Override
	protected void initServer()
	{
		super.initServer();
		this.oldTier = this.newTier = (byte) getBaseTier();
		if(this.enabledAddedInToNet && !this.addedInToNet &&
				this.worldObj.isBlockLoaded(this.pos))//Prevent looped loaded.
		{
			addToEnergyNet();
		}
	}
	
	protected abstract int getBaseTier();
	
	protected void setTier(int tier)
	{
		this.newTier = (byte) tier;
	}
	
	@Override
	public void onRemoveFromLoadedWorld()
	{
		super.onRemoveFromLoadedWorld();
		if(this.addedInToNet)
		{
			removeFromEnergyNet();
		}
	}
	
	protected void addToEnergyNet()
	{
		if(this.enabledAddedInToNet)
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			this.addedInToNet = true;
		}
	}
	
	protected void removeFromEnergyNet()
	{
		MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
		this.addedInToNet = false;
	}
	
	protected void onOverpowered(double amount, double voltage)
	{
		if(getPluginLevel(IPluginAccess.fuse) > 0)
		{
			removeFromEnergyNet();
			removePlugin(IPluginAccess.fuse);
			this.enabledAddedInToNet = false;
		}
		else
		{
			this.worldObj.createExplosion(null, this.pos.getX() + .5, this.pos.getY() + .5, this.pos.getZ() + .5, (float) (voltage * voltage - getMaxVoltage() * getMaxVoltage()) / 100F + 3, true);
		}
	}
	
	@Override
	protected long getEnergyInput()
	{
		return (long) this.energyInput;
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