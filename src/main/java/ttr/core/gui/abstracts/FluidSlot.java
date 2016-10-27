package ttr.core.gui.abstracts;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import ttr.core.tile.ITankSyncable;

public class FluidSlot
{
	protected final int index;
	public final ITankSyncable tank;
	public int x;
	public int y;
	public int w;
	public int h;
	public int slotNumber;

	public FluidSlot(ITankSyncable tank, int id, int x, int y)
	{
		this(tank, id, x, y, 16, 16);
	}
	public FluidSlot(ITankSyncable tank, int id, int x, int y, int w, int h)
	{
		index = id;
		this.tank = tank;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	/**
	 * if par2 has more items than par1, onCrafting(fluid,countIncrease) is called
	 */
	public void onSlotChange(FluidStack fluid, FluidStack change)
	{
		if (fluid != null && change != null)
		{
			if (fluid.getFluid() == change.getFluid())
			{
				int i = change.amount - fluid.amount;
				
				if (i > 0)
				{
					onCrafting(fluid, i);
				}
			}
		}
	}

	public void onCrafting(FluidStack fluid, int i) {	}
	
	public void setStack(FluidStack stack)
	{
		tank.setFluidStackToTank(getSlotIndex(), stack);
	}
	
	/**
	 * Helper fnct to get the stack in the slot.
	 */
	public FluidStack getStack()
	{
		return tank.getStackInTank(slotNumber);
	}

	/**
	 * Helper fnct to get the stack in the slot.
	 */
	public FluidTankInfo getTankInfo()
	{
		return tank.getInfomation(getSlotIndex());
	}
	
	/**
	 * Returns if this slot contains a stack.
	 */
	public boolean getHasStack()
	{
		return getStack() != null;
	}

	public void renderFluidInSlot(GuiContainerBase gui)
	{
		if(getHasStack() && shouldRender)
		{
			gui.drawFluid(x, y, tank.getInfomation(getSlotIndex()), w, h, drawLay());
		}
	}

	private boolean shouldRender = true;

	public FluidSlot setNoRender()
	{
		shouldRender = false;
		return this;
	}

	protected boolean shouldRender()
	{
		return shouldRender;
	}

	protected boolean drawLay()
	{
		return false;
	}
	
	/**
	 * Retrieves the index in the inventory for this slot, this value should typically not
	 * be used, but can be useful for some occasions.
	 *
	 * @return Index in associated inventory for this slot.
	 */
	public int getSlotIndex()
	{
		return index;
	}
}