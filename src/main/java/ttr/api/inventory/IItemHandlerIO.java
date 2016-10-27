package ttr.api.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public interface IItemHandlerIO
{
	boolean canExtractItem();

	boolean canInsertItem();

	ItemStack extractItem(int size, EnumFacing direction, boolean simulate);

	int tryInsertItem(ItemStack stack, EnumFacing direction, boolean simulate);
	
	public static class ItemHandlerIOStorage implements IStorage<IItemHandlerIO>
	{
		@Override
		public NBTBase writeNBT(Capability<IItemHandlerIO> capability, IItemHandlerIO instance, EnumFacing side)
		{
			return new NBTTagString("");
		}

		@Override
		public void readNBT(Capability<IItemHandlerIO> capability, IItemHandlerIO instance, EnumFacing side,
				NBTBase nbt)
		{

		}
	}
}
