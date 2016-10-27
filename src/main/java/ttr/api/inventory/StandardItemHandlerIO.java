package ttr.api.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class StandardItemHandlerIO implements IItemHandlerIO
{
	@Override
	public boolean canExtractItem()
	{
		return false;
	}
	
	@Override
	public boolean canInsertItem()
	{
		return false;
	}
	
	@Override
	public ItemStack extractItem(int size, EnumFacing direction, boolean simulate)
	{
		return null;
	}
	
	@Override
	public int tryInsertItem(ItemStack stack, EnumFacing direction, boolean simulate)
	{
		return 0;
	}
}