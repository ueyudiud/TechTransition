package ttr.api.tile;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

public interface ISynchronizableTile
{
	void writeToDescription(PacketBuffer buffer) throws IOException;

	void readFromDescription(PacketBuffer buffer) throws IOException;
	
	void markBlockRenderUpdate();
	
	void syncToPlayer(EntityPlayer player);
}