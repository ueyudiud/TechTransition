package ttr.core.tile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import ttr.api.net.tile.PacketTESAskRender;
import ttr.api.net.tile.PacketTESync;
import ttr.api.tile.ISynchronizableTile;

public class TESynchronization extends TEBuffered
implements ISynchronizableTile
{
	private boolean initialized = false;
	
	/**
	 * The sync state.
	 * 1 for mark to all.
	 * 2 for mark to dim.
	 * 4 for mark to near by.
	 * 8 for mark block update.
	 * 16 for mark render update.
	 * 32 for mark light update.
	 */
	public long syncState = 0L;
	private volatile List<EntityPlayer> syncAskedPlayer = new ArrayList();
	
	public TESynchronization()
	{
		
	}
	
	@Override
	public void syncToAll()
	{
		syncState |= 0x1;
	}
	
	@Override
	public void syncToDim()
	{
		syncState |= 0x2;
	}
	
	@Override
	public void syncToNearby()
	{
		syncState |= 0x4;
	}
	
	@Override
	public void syncToPlayer(EntityPlayer player)
	{
		syncAskedPlayer.add(player);
	}
	
	@Override
	public void markBlockUpdate()
	{
		syncState |= 0x8;
	}
	
	@Override
	public void markBlockRenderUpdate()
	{
		syncState |= 0x10;
	}
	
	@Override
	public void markLightUpdate()
	{
		syncState |= 0x20;
	}
	
	@Override
	public boolean isInitialized()
	{
		return initialized;
	}
	
	@Override
	public final void readFromDescription(PacketBuffer buffer) throws IOException
	{
		readFromDescription1(buffer);
		markBlockUpdate();
		markBlockRenderUpdate();
		initialized = true;
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
	
	protected void readInitalizeTag(NBTTagCompound tag)
	{
		
	}

	protected void preUpdateEntity()
	{
		
	}
	
	protected void postUpdateEntity()
	{
		
	}
	
	@Override
	public void onLoad()
	{
		super.onLoad();
		if(isServer())
		{
			initServer();
		}
	}
	
	@Override
	protected final void updateEntity2()
	{
		if(isServer())
		{
			preUpdateEntity();
			updateServer();
			postUpdateEntity();
			if((syncState & 0x1) != 0)
			{
				sendToAll(new PacketTESync(worldObj, pos));
			}
			else if((syncState & 0x2) != 0)
			{
				sendToDim(new PacketTESync(worldObj, pos));
			}
			else if((syncState & 0x4) != 0)
			{
				sendToNearby(new PacketTESync(worldObj, pos), getSyncRange());
			}
			else if((syncState & 0x10) != 0)
			{
				sendToNearby(new PacketTESAskRender(worldObj, pos), getSyncRange());
			}
			if((syncState & 0x8) != 0)
			{
				worldObj.notifyNeighborsOfStateChange(pos, getBlockType());
			}
			if((syncState & 0x20) != 0)
			{
				worldObj.checkLight(pos);
			}
			syncState = 0;
			if(!syncAskedPlayer.isEmpty())
			{
				for(EntityPlayer player : ImmutableList.copyOf(syncAskedPlayer))
				{
					sendToPlayer(new PacketTESync(worldObj, pos), player);
				}
			}
			syncAskedPlayer.clear();
		}
		else
		{
			updateClient();
			if((syncState & 0x8) != 0)
			{
				worldObj.notifyBlockOfStateChange(pos, getBlockType());
			}
			if((syncState & 0x10) != 0)
			{
				int range = getRenderUpdateRange();
				worldObj.markBlockRangeForRenderUpdate(pos.add(-range, -range, -range), pos.add(range, range, range));
			}
			if((syncState & 0x20) != 0)
			{
				worldObj.checkLight(pos);
			}
			syncState = 0;
			syncAskedPlayer.clear();
		}
	}
	
	protected float getSyncRange()
	{
		return 16F;
	}
	
	protected int getRenderUpdateRange()
	{
		return 3;
	}
	
	protected void initServer()
	{
		initialized = true;
	}
	
	protected void initClient(PacketBuffer buffer)
	{
		try
		{
			readFromDescription(buffer);
		}
		catch(IOException exception)
		{
			;
		}
	}
	
	protected void updateServer()
	{
		
	}
	
	protected void updateClient()
	{
		
	}
	
	@Override
	public void onChunkUnload()
	{
		super.onChunkUnload();
		onRemoveFromLoadedWorld();
	}
	
	@Override
	public void invalidate()
	{
		super.invalidate();
		onRemoveFromLoadedWorld();
	}
	
	public void onRemoveFromLoadedWorld()
	{

	}
}