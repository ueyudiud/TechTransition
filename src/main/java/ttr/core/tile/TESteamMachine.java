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
import ttr.load.TTrFluids;

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
		inventory = new Inventory(1, name, 64);
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
		steamAmount = nbt.getInteger("steam");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("steam", steamAmount);
		return super.writeToNBT(nbt);
	}

	@Override
	protected void initRecipeInput(TemplateRecipe recipe)
	{
		super.initRecipeInput(recipe);
		duration = (long) Math.ceil(duration * durationMultipler);
		minPower = (long) Math.ceil(minPower / durationMultipler);
	}
	
	@Override
	protected boolean matchRecipeOutput()
	{
		return worldObj.isAirBlock(pos.offset(getFacing("exhaust")));
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
				worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL,
						dir.getFrontOffsetX() * .5 + pos.getX() + .5,
						dir.getFrontOffsetY() * .5 + pos.getY() + .5,
						dir.getFrontOffsetZ() * .5 + pos.getZ() + .5,
						dir.getFrontOffsetX() * 0.2 + (random.nextFloat() - random.nextFloat()) * 0.02,
						dir.getFrontOffsetY() * 0.2 + (random.nextFloat() - random.nextFloat()) * 0.02,
						dir.getFrontOffsetZ() * 0.2 + (random.nextFloat() - random.nextFloat()) * 0.02);
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
		for(EntityLivingBase entity : worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos.offset(dir))))
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
		return maxPower;
	}
	
	@Override
	protected boolean useEnergy()
	{
		if(steamAmount >= minPower)
		{
			disable(SteamNotEnough);
			steamAmount -= Math.ceil(minPower * EnergyTrans.J_TO_STEAM / efficiency);
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
		return steamInput;
	}
	
	public int getSteamAmount()
	{
		return steamAmount;
	}

	@Override
	public IFluidTankProperties[] getTankProperties()
	{
		return new IFluidTankProperties[]{new FluidTankProperties(new FluidStack(TTrFluids.steam, steamAmount), maxSteamCap, true, false)};
	}
	
	@Override
	public int fill(FluidStack resource, boolean doFill)
	{
		if(resource != null && resource.getFluid() == TTrFluids.steam)
		{
			steamAmount += resource.amount;
			steamInput += resource.amount;
			return resource.amount;
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