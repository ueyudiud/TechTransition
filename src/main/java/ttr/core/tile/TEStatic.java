package ttr.core.tile;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
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
	public void readFromDescription(PacketBuffer buffer) throws IOException
	{
		readFromDescription1(buffer);
	}
	
	public void readFromDescription1(PacketBuffer buffer) throws IOException
	{
		
	}
	
	@Override
	public void writeToDescription(PacketBuffer buffer) throws IOException
	{
		
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writeInitalizeTag(nbt);
		nbt.merge(super.getUpdateTag());
		return nbt;
	}
	
	protected void writeInitalizeTag(NBTTagCompound nbt)
	{
		
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag)
	{
		super.handleUpdateTag(tag);
		readInitalizeTag(tag);
	}

	protected void readInitalizeTag(NBTTagCompound nbt)
	{

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