package ttr.core.tile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.inventory.Inventory;
import ttr.api.recipe.TemplateRecipeMap.TemplateRecipe;
import ttr.api.tile.IContainerableTile;
import ttr.api.util.DamageSources;
import ttr.api.util.EnergyTrans;
import ttr.load.TTrIBF;

public abstract class TESteamMachine extends TEMachineRecipeMap
implements IContainerableTile, IFluidHandler
{
	public static final int SteamNotEnough = 16;
	public static final int ReleaseSteam = 17;
	
	protected int steamInput;
	
	public final long maxPower;
	public final float durationMultipler;
	public final int maxSteamCap;
	protected final float efficiency;
	protected int steamAmount;
	
	public TESteamMachine(String name, int itemInputSize, int itemOutputSize, int fluidInputSize, int fluidOutputSize, int maxSteamCap, long maxPower, float efficiency, float durationMultipler)
	{
		this(itemInputSize, itemOutputSize, fluidInputSize, fluidOutputSize, maxSteamCap, maxPower, efficiency, durationMultipler);
		this.inventory = new Inventory(1, name, 64);
	}
	
	public TESteamMachine(int itemInputSize, int itemOutputSize, int fluidInputSize, int fluidOutputSize, int maxSteamCap, long maxPower, float efficiency, float durationMultipler)
	{
		super(itemInputSize, itemOutputSize, fluidInputSize, fluidOutputSize);
		this.maxSteamCap = maxSteamCap;
		this.efficiency = efficiency;
		this.durationMultipler = durationMultipler;
		this.maxPower = maxPower;
		addFacing("exhaust", EnumFacing.SOUTH);
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
		this.steamAmount = nbt.getInteger("steam");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("steam", this.steamAmount);
		return super.writeToNBT(nbt);
	}
	
	@Override
	protected void initRecipeInput(TemplateRecipe recipe)
	{
		super.initRecipeInput(recipe);
		this.duration = (long) Math.ceil(this.duration * this.durationMultipler);
		this.minPower = (long) Math.ceil(this.minPower / this.durationMultipler);
	}
	
	@Override
	protected boolean matchRecipeOutput()
	{
		return this.worldObj.isAirBlock(this.pos.offset(getFacing("exhaust")));
	}
	
	@Override
	protected void updateClient()
	{
		super.updateClient();
		if(is(ReleaseSteam))
		{
			EnumFacing dir = getFacing("exhaust");
			for(int i = 0; i < 4; ++i)
			{
				this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL,
						dir.getFrontOffsetX() * .5 + this.pos.getX() + .5,
						dir.getFrontOffsetY() * .5 + this.pos.getY() + .5,
						dir.getFrontOffsetZ() * .5 + this.pos.getZ() + .5,
						dir.getFrontOffsetX() * 0.2 + (this.random.nextFloat() - this.random.nextFloat()) * 0.02,
						dir.getFrontOffsetY() * 0.2 + (this.random.nextFloat() - this.random.nextFloat()) * 0.02,
						dir.getFrontOffsetZ() * 0.2 + (this.random.nextFloat() - this.random.nextFloat()) * 0.02);
			}
			disable(ReleaseSteam);
		}
	}
	
	@Override
	protected void preUpdateEntity()
	{
		super.preUpdateEntity();
		disable(ReleaseSteam);
	}
	
	@Override
	protected void initRecipeOutput()
	{
		super.initRecipeOutput();
		//Display sound
		EnumFacing dir = getFacing("exhaust");
		for(EntityLivingBase entity : this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.pos.offset(dir))))
		{
			if(entity.getActivePotionEffect(MobEffects.FIRE_RESISTANCE) == null)
			{
				entity.attackEntityFrom(DamageSources.STEAM_HEAT, getSteamDamage());
			}
		}
		enable(ReleaseSteam);
		syncToNearby();
	}
	
	protected float getSteamDamage()
	{
		return 6F;
	}
	
	@Override
	protected void updateServer()
	{
		super.updateServer();
	}
	
	@Override
	protected long getPower()
	{
		return this.maxPower;
	}
	
	@Override
	protected boolean useEnergy()
	{
		if(this.steamAmount >= this.minPower)
		{
			disable(SteamNotEnough);
			this.steamAmount -= Math.ceil(this.minPower * EnergyTrans.J_TO_STEAM / this.efficiency);
			return true;
		}
		else if(is(Working))
		{
			enable(SteamNotEnough);
		}
		return false;
	}
	
	@Override
	protected long getEnergyInput()
	{
		return this.steamInput;
	}
	
	public int getSteamAmount()
	{
		return this.steamAmount;
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties()
	{
		return new IFluidTankProperties[]{new FluidTankProperties(new FluidStack(TTrIBF.steam, this.steamAmount), this.maxSteamCap, true, false)};
	}
	
	@Override
	public int fill(FluidStack resource, boolean doFill)
	{
		if(resource != null && resource.getFluid() == TTrIBF.steam)
		{
			int in = Math.min(resource.amount, this.maxSteamCap - this.steamAmount);
			if(doFill)
			{
				this.steamAmount += in;
				this.steamInput += in;
			}
			return in;
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
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ||
				super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		return super.getCapability(capability, facing);
	}
	
	@SideOnly(Side.CLIENT)
	public boolean isSteamNotEnough()
	{
		return is(Working) && is(SteamNotEnough);
	}
}