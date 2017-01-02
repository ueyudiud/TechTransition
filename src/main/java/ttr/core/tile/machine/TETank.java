/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.tile.machine;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import ttr.core.tile.TEMaterialed;

/**
 * Created at 2016年12月22日 下午6:16:25
 * @author ueyudiud
 */
public class TETank extends TEMaterialed implements IFluidHandler
{
	int tankID;
	TETank[] tankList;
	
	@Override
	public void onNeighbourBlockChange()
	{
		super.onNeighbourBlockChange();
		if(this.tankList != null && this.tankList[0] != null)
		{
			((TETankBottom) this.tankList[0]).markStructureUpdated();
		}
	}
	
	@Override
	public void invalidate()
	{
		super.invalidate();
		if(this.tankList != null && this.tankList[0] != null)
		{
			((TETankBottom) this.tankList[0]).markStructureUpdated();
		}
	}
	
	@Override
	public void onChunkUnload()
	{
		super.onChunkUnload();
		if(this.tankList != null && this.tankList[0] != null)
		{
			((TETankBottom) this.tankList[0]).markStructureUpdated();
		}
	}
	
	@Override
	protected void updateServer()
	{
		super.updateServer();
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && this.tankList != null) || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && this.tankList != null) ? CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this) : super.getCapability(capability, facing);
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties()
	{
		return new IFluidTankProperties[0]; //Not applicable.
	}
	
	@Override
	public int fill(FluidStack resource, boolean doFill)
	{
		if(resource == null || this.tankList == null || !(this.tankList[0] instanceof TETankBottom)) return 0;
		if(resource.getFluid().isGaseous(resource) && !(this.tankList[this.tankList.length - 1] instanceof TETankTop)) return resource.amount;
		return ((TETankBottom) this.tankList[0]).fill(this.tankID, resource, doFill);
	}
	
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain)
	{
		if(resource == null || this.tankList == null || !(this.tankList[0] instanceof TETankBottom)) return null;
		if(resource.getFluid().isGaseous(resource) && !(this.tankList[this.tankList.length - 1] instanceof TETankTop)) return null;
		return ((TETankBottom) this.tankList[0]).drain(this.tankID, resource, doDrain);
	}
	
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		if(this.tankList == null || !(this.tankList[0] instanceof TETankBottom)) return null;
		return ((TETankBottom) this.tankList[0]).drain(this.tankID, maxDrain, doDrain);
	}
}