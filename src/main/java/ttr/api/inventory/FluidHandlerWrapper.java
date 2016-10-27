package ttr.api.inventory;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class FluidHandlerWrapper implements IFluidHandler
{
	public final EnumFacing facing;
	protected final IFluidHandler handler;

	public FluidHandlerWrapper(EnumFacing facing, IFluidHandler handler)
	{
		this.facing = facing;
		this.handler = handler;
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties()
	{
		return handler.getTankProperties();
	}
	
	@Override
	public int fill(FluidStack resource, boolean doFill)
	{
		return handler.fill(resource, doFill);
	}
	
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain)
	{
		return handler.drain(resource, doDrain);
	}
	
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		return handler.drain(maxDrain, doDrain);
	}
}