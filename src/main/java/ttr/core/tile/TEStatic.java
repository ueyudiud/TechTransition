package ttr.core.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import ttr.api.net.tile.PacketTESync;
import ttr.api.tile.ISynchronizableTile;

public class TEStatic extends TEBase implements ISynchronizableTile
{
	public boolean initialized = false;

	public TEStatic()
	{

	}

	@Override
	public boolean isInitialized()
	{
		return initialized;
	}
	
	@Override
	public void readFromDescription(NBTTagCompound nbt)
	{

	}
	
	@Override
	public void writeToDescription(NBTTagCompound nbt)
	{

	}
	
	@Override
	public NBTTagCompound getUpdateTag()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writeToDescription(nbt);
		nbt.merge(super.getUpdateTag());
		return nbt;
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag)
	{
		super.handleUpdateTag(tag);
		readFromDescription(tag);
	}

	@Override
	public void syncToAll()
	{
		sendToAll(new PacketTESync(worldObj, pos));
	}

	@Override
	public void syncToDim()
	{
		sendToDim(new PacketTESync(worldObj, pos));
	}

	@Override
	public void syncToNearby()
	{
		sendToNearby(new PacketTESync(worldObj, pos), getSyncRange());
	}

	protected float getSyncRange()
	{
		return 32F;
	}

	@Override
	public void syncToPlayer(EntityPlayer player)
	{
		sendToPlayer(new PacketTESync(worldObj, pos), player);
	}

	@Override
	public void markDirty()
	{
		worldObj.notifyBlockOfStateChange(pos, getBlockType());
	}
}