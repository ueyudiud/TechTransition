package ttr.api.net;

import java.io.IOException;

import net.minecraft.network.PacketBuffer;
import ttr.api.network.PacketAbstract;

public abstract class PacketContainer extends PacketAbstract
{
	protected int windowID;

	public PacketContainer()
	{
	}
	public PacketContainer(int windowID)
	{
		this.windowID = windowID;
	}
	
	@Override
	protected void encode(PacketBuffer output) throws IOException
	{
		output.writeShort((short) windowID);
	}
	
	@Override
	protected void decode(PacketBuffer input) throws IOException
	{
		windowID = input.readShort();
	}
}