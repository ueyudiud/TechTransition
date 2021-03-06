package ttr.api.net.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ttr.api.net.PacketBlockCoord;
import ttr.api.network.IPacket;
import ttr.api.network.Network;
import ttr.api.tile.ISynchronizableTile;

public class PacketTEAsk extends PacketBlockCoord
{
	public PacketTEAsk()
	{
		
	}
	public PacketTEAsk(World world, BlockPos pos)
	{
		super(world, pos);
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
				((ISynchronizableTile) tile).syncToPlayer(getPlayer());
			}
		}
		return null;
	}
}