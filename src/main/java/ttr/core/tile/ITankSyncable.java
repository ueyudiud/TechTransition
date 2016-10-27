package ttr.core.tile;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public interface ITankSyncable
{
	int getTankSize();
	
	FluidTankInfo getInfomation(int id);
	
	default FluidStack getStackInTank(int id)
	{
		return getInfomation(id).fluid;
	}
	
	void setFluidStackToTank(int id, FluidStack stack);
}