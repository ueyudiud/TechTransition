package ttr.api.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IDescribable
{
	NBTTagCompound writeDescriptionsToNBT(NBTTagCompound nbt);
	
	void readDescriptionsFromNBT(NBTTagCompound nbt);

	void markNBTSync(EntityPlayer player);
}