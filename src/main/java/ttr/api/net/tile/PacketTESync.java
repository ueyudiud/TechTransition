package ttr.api.net.tile;

import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ttr.api.net.PacketBlockCoord;
import ttr.api.network.IPacket;
import ttr.api.network.Network;
import ttr.api.tile.ISynchronizableTile;

public class PacketTESync extends PacketBlockCoord
{
	private NBTTagCompound nbt;
	
	public PacketTESync()
	{

	}
	public PacketTESync(World world, BlockPos pos)
	{
		super(world, pos);
	}
	
	@Override
	public boolean needToSend()
	{
		return world().getTileEntity(pos) instanceof ISynchronizableTile;
	}

	@Override
	protected void encode(PacketBuffer output) throws IOException
	{
		super.encode(output);
		NBTTagCompound nbt = new NBTTagCompound();
		((ISynchronizableTile) world().getTileEntity(pos)).writeToDescription(nbt);
		output.writeNBTTagCompoundToBuffer(nbt);
	}

	@Override
	protected void decode(PacketBuffer input) throws IOException
	{
		super.decode(input);
		nbt = input.readNBTTagCompoundFromBuffer();
	}

	@Override
	public IPacket process(Network network)
	{
		World world = world();
		if(world != null)
		{
			TileEntity tile = world.getTileEntity(pos);
			if(tile instanceof ISynchronizableTile)
			{
				((ISynchronizableTile) tile).readFromDescription(nbt);
			}
		}
		return null;
	}
}