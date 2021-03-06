package ttr.api.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.EnumMap;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.MessageToMessageCodec;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import ttr.api.TTrAPI;
import ttr.api.collection.Register;

@ChannelHandler.Sharable
public class Network extends MessageToMessageCodec<FMLProxyPacket, IPacket>
{
	static final Register<Network> register = new Register();
	
	public static Network network(String name)
	{
		if(!register.contain(name))
		{
			Network network = new Network(name);
			register.register(name, network);
			return network;
		}
		return register.get(name);
	}
	
	public void registerPacket(Class<? extends IPacket> packet, Side side)
	{
		if(packetTypes.contain(packet.getName())) throw new IllegalArgumentException("Duplicate Packet! " + id);
		if(id >= 256) throw new ArrayIndexOutOfBoundsException(id);
		packetTypes.register(id, packet.getName(), packet);
		++id;
	}
	
	private final EnumMap<Side, FMLEmbeddedChannel> channel;
	private final String channelName;
	private Register<Class<? extends IPacket>> packetTypes = new Register(256);
	private int id = 0;
	
	protected Network(String name)
	{
		channelName = name;
		channel = NetworkRegistry.INSTANCE.newChannel(name, new ChannelHandler[]{this, new HandlerClient(this), new HandlerServer(this)});
		registerPacket(PacketLarge.class, Side.CLIENT);//This is a important packet, used in the method in basic network.
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, IPacket msg,
			List<Object> out) throws Exception
	{
		out.add(new FMLProxyPacket(new PacketBuffer(
				msg.encode(Unpooled.buffer().writeByte(packetTypes.id(msg.getClass()))).copy()), ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get()));
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, FMLProxyPacket msg,
			List<Object> out) throws Exception
	{
		ByteBuf buf = msg.payload();
		int id = buf.readByte();
		if (!packetTypes.contain(id))
		{
			FMLLog.warning("Your Version of '" + channelName + "' definetly does not match the Version installed on the Server you joined! Do not report this as a Bug! You failed to install the proper Version of '" + channelName + "' all by yourself!");
		}
		else
		{
			IPacket packet = processPacket(id, buf, msg.getTarget(), msg.handler());
			if(packet != null)
			{
				out.add(packet);
			}
		}
	}
	
	@ChannelHandler.Sharable
	static final class HandlerClient extends SimpleChannelInboundHandler<IPacket>
	{
		public final Network network;
		
		public HandlerClient(Network network)
		{
			this.network = network;
		}
		
		@Override
		protected void channelRead0(ChannelHandlerContext ctx, IPacket packet)
				throws Exception
		{
			IPacket obj = packet.process(network);
			if(obj != null)
			{
				Channel tChannel = ctx.channel();
				IPacket pkt = obj;
				tChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
				tChannel.writeAndFlush(pkt);
			}
		}
		
		@Override
		public boolean acceptInboundMessage(Object msg) throws Exception
		{
			return super.acceptInboundMessage(msg) && ((IPacket) msg).getSide().isClient();
		}
	}
	
	@ChannelHandler.Sharable
	static final class HandlerServer extends SimpleChannelInboundHandler<IPacket>
	{
		public final Network network;
		
		public HandlerServer(Network network)
		{
			this.network = network;
		}
		
		@Override
		protected void channelRead0(ChannelHandlerContext ctx, IPacket packet)
				throws Exception
		{
			IPacket retPacket = packet.process(network);
			if(retPacket != null)
			{
				Channel tChannel = ctx.channel();
				if(packet.getPlayer() != null)
				{
					tChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
					tChannel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(packet.getPlayer());
				}
				else
				{
					tChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
				}
				tChannel.writeAndFlush(retPacket);
			}
		}
		
		@Override
		public boolean acceptInboundMessage(Object msg) throws Exception
		{
			return super.acceptInboundMessage(msg) && ((IPacket) msg).getSide().isServer();
		}
	}
	
	public FMLEmbeddedChannel getChannel(Side side)
	{
		return channel.get(side);
	}
	
	/**
	 * To check is packet needed to send.
	 * @param packet
	 * @return
	 */
	private boolean needToSend(IPacket packet)
	{
		return packet != null && packet.needToSend();
	}
	
	public void sendTo(IPacket packet)
	{
		if(!needToSend(packet)) return;
		if(TTrAPI.isSimulating())
		{
			if(TTrAPI.player() instanceof EntityPlayerMP)
			{
				sendToPlayer(packet, TTrAPI.player());
			}
		}
		else
		{
			sendToServer(packet);
		}
	}
	
	public void sendToAll(IPacket packet)
	{
		if(!needToSend(packet)) return;
		FMLEmbeddedChannel tChannel = getChannel(Side.SERVER);
		tChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
		tChannel.writeAndFlush(packet);
	}
	
	public void sendToPlayer(IPacket packet, EntityPlayer player)
	{
		if(!(player instanceof EntityPlayerMP))
			return;
		if(!needToSend(packet)) return;
		FMLEmbeddedChannel tChannel = getChannel(Side.SERVER);
		tChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
		tChannel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
		tChannel.writeAndFlush(packet);
	}
	
	public void sendToDim(IPacket packet, int dim)
	{
		if(!needToSend(packet)) return;
		FMLEmbeddedChannel tChannel = getChannel(Side.SERVER);
		tChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
		tChannel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dim);
		tChannel.writeAndFlush(packet);
	}
	
	public void sendToServer(IPacket packet)
	{
		if(!needToSend(packet)) return;
		FMLEmbeddedChannel tChannel = getChannel(Side.CLIENT);
		tChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
		tChannel.writeAndFlush(packet);
	}
	
	public void sendToNearBy(IPacket packet, int dim, int x, int y,
			int z, float range)
	{
		if(!needToSend(packet)) return;
		FMLEmbeddedChannel tChannel = getChannel(Side.SERVER);
		tChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
		tChannel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(new TargetPoint(dim, x + 0.5D, y + 0.5D, z + 0.5D, range));
		tChannel.writeAndFlush(packet);
	}
	
	public void sendToNearBy(IPacket packet, TileEntity coord, float range)
	{
		if(!needToSend(packet)) return;
		FMLEmbeddedChannel tChannel = getChannel(Side.SERVER);
		tChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
		BlockPos pos = coord.getPos();
		tChannel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(new TargetPoint(coord.getWorld().provider.getDimension(),
				pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, range));
		tChannel.writeAndFlush(packet);
	}
	
	public void sendToNearBy(IPacket packet, TargetPoint point)
	{
		if(!needToSend(packet)) return;
		FMLEmbeddedChannel tChannel = getChannel(Side.SERVER);
		tChannel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
		tChannel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
		tChannel.writeAndFlush(packet);
	}
	
	public void sendLargeToPlayer(IPacket packet, EntityPlayer player)
	{
		if(!needToSend(packet)) return;
		try
		{
			ByteBuf buffer = Unpooled.buffer();
			byte[] data = packet.encode(buffer).array();
			ByteArrayInputStream input = new ByteArrayInputStream(data);
			ByteArrayOutputStream buf = new ByteArrayOutputStream(16384);
			int len;
			byte[] cache = new byte[4096];
			while ((len = input.read(cache)) != -1)
			{
				buf.write(cache, 0, len);
			}
			data = buf.toByteArray();
			
			int maxSize = Short.MAX_VALUE - 5;
			for (int offset = 0; offset < data.length; offset += maxSize)
			{
				ByteArrayOutputStream osRaw = new ByteArrayOutputStream();
				DataOutputStream os = new DataOutputStream(osRaw);
				int state = 0;
				if (offset == 0)
				{
					state |= 0x1;
				}
				
				if (offset + maxSize > data.length)
				{
					state |= 0x2;
				}
				int id = packetTypes.id(packet.getClass());
				state |= id << 2;
				os.writeInt(state);
				os.write(data, offset, Math.min(maxSize, data.length - offset));
				sendToPlayer(new PacketLarge(osRaw.toByteArray()), player);
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}
	
	public void sendLargeToAll(IPacket packet)
	{
		if(!needToSend(packet)) return;
		try
		{
			ByteBuf buffer = Unpooled.buffer();
			byte[] data = packet.encode(buffer).array();
			ByteArrayInputStream input = new ByteArrayInputStream(data);
			ByteArrayOutputStream buf = new ByteArrayOutputStream(16384);
			int len;
			byte[] cache = new byte[4096];
			while ((len = input.read(cache)) != -1)
			{
				buf.write(cache, 0, len);
			}
			data = buf.toByteArray();
			
			int maxSize = Short.MAX_VALUE - 5;
			for (int offset = 0; offset < data.length; offset += maxSize)
			{
				ByteArrayOutputStream osRaw = new ByteArrayOutputStream();
				DataOutputStream os = new DataOutputStream(osRaw);
				int state = 0;
				if (offset == 0)
				{
					state |= 0x1;
				}
				
				if (offset + maxSize > data.length)
				{
					state |= 0x2;
				}
				int id = packetTypes.id(packet.getClass());
				state |= id << 2;
				os.writeInt(state);
				os.write(data, offset, Math.min(maxSize, data.length - offset));
				sendToAll(new PacketLarge(osRaw.toByteArray()));
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}
	
	public IPacket processPacket(int id, ByteBuf buf, Side side, INetHandler handler) throws Exception
	{
		IPacket packet;
		try
		{
			packet = packetTypes.get(id).newInstance();
			packet.decode(buf);
			packet.side(side);
			packet.handler(handler);
			return packet.process(this);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
}