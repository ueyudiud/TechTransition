package ttr.api.gui;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IFluidSyncableContainer
{
	@SideOnly(Side.CLIENT)
	void setFluid(int idx, FluidStack stack);
}