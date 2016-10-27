package ttr.api.net;

import java.io.IOException;

import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import ttr.api.TTrAPI;
import ttr.api.network.PacketAbstract;

public abstract class PacketWorld extends PacketAbstract
{
	private World world;
	protected int dimID;

	public PacketWorld()
	{

	}
	public PacketWorld(World world)
	{
		this.world = world;
		dimID = world.provider.getDimension();
	}

	@Override
	protected void encode(PacketBuffer output) throws IOException
	{
		output.writeShort(dimID);
	}

	@Override
	protected void decode(PacketBuffer input) throws IOException
	{
		dimID = input.readShort();
	}

	public World world()
	{
		return world != null ? world : (world = TTrAPI.world(dimID));
	}
}