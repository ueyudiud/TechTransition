package ttr.api.inventory;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class WaterTank extends FluidTank
{
	public static final String DISTILLED_WATER = "ic2distilledwater";

	public WaterTank(int capacity)
	{
		super(capacity);
	}

	@Override
	public boolean canFillFluidType(FluidStack fluid)
	{
		return (fluid == null) ? false :
			(fluid.getFluid() == FluidRegistry.WATER) || (DISTILLED_WATER.equals(fluid.getFluid().getName()));
	}

	public boolean isDistilledWater()
	{
		return fluid != null && DISTILLED_WATER.equals(fluid.getFluid().getName());
	}
}