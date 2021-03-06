package ttr.api.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.fml.relauncher.Side;
import ttr.api.TTrAPI;
import ttr.api.util.Log;

public class PacketLarge implements IPacket
{
	protected Side side;
	protected INetHandler handler;
	
	private byte[] bs;
	private static volatile ByteBuf largePacketCache;
	
	private int id = 0;
	private boolean flag = false;
	
	public PacketLarge()
	{
		
	}
	public PacketLarge(byte[] arrays)
	{
		bs = arrays;
	}
	
	@Override
	public Side getSide()
	{
		return side;
	}
	
	@Override
	public void side(Side side)
	{
		this.side = side;
	}
	
	@Override
	public void handler(INetHandler handler)
	{
		this.handler = handler;
	}
	
	@Override
	public EntityPlayer getPlayer()
	{
		return (handler instanceof NetHandlerPlayServer) ?
				((NetHandlerPlayServer) handler).playerEntity :
					TTrAPI.player();
	}
	
	@Override
	public ByteBuf encode(ByteBuf buf) throws IOException
	{
		buf.writeShort(bs.length);
		buf.writeBytes(bs);
		return buf;
	}
	
	@Override
	public void decode(ByteBuf buf) throws IOException
	{
		byte[] b = new byte[buf.readShort()];
		buf.readBytes(b);
		DataInputStream stream = new DataInputStream(new ByteArrayInputStream(b));
		int type = stream.readInt();
		id = type >> 2;
		if((type & 0x1) != 0)
		{
			largePacketCache = Unpooled.buffer();
		}
		byte[] buffer = new byte[4096];
		int len;
		while ((len = stream.read(buffer)) != -1)
		{
			largePacketCache.writeBytes(buffer, 0, len);
		}
		if((type & 0x2) != 0)
		{
			flag = true;
		}
	}
	
	@Override
	public IPacket process(Network network)
	{
		if(!flag) return null;
		try
		{
			network.processPacket(id, largePacketCache, side, handler);
			largePacketCache = null;
		}
		catch(Throwable e)
		{
			Log.warn("Fail to process packet.", e);
		}
		return null;
	}
	
	@Override
	public boolean needToSend()
	{
		return true;
	}
}