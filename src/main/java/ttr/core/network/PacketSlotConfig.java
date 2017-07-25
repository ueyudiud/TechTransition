/*
 * copyright© 2016-2017 ueyudiud
 */
package ttr.core.network;

import java.io.IOException;

import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ttr.api.net.PacketBlockCoord;
import ttr.api.network.IPacket;
import ttr.api.network.Network;
import ttr.core.tile.TEMachineRecipeMap;

/**
 * @author ueyudiud
 */
public class PacketSlotConfig extends PacketBlockCoord
{
	int type;
	
	public PacketSlotConfig()
	{
	}
	public PacketSlotConfig(World world, BlockPos pos, int type)
	{
		super(world, pos);
		this.type = type;
	}
	
	@Override
	public IPacket process(Network network)
	{
		TileEntity te = world().getTileEntity(this.pos);
		if (te instanceof TEMachineRecipeMap)
		{
			TEMachineRecipeMap tile = (TEMachineRecipeMap) te;
			switch (this.type)
			{
			case 0 :
				tile.allowAutoOutputFluid = !tile.allowAutoOutputFluid;
				break;
			case 1 :
				tile.allowAutoOutputItem = !tile.allowAutoOutputItem;
				break;
			case 2 :
				int o = tile.autoOutputFace == null ? -1 : tile.autoOutputFace.ordinal();
				o++;
				if (tile.facing.ordinal() == o) o++;
				if (o == 6)
				{
					tile.autoOutputFace = null;
				}
				else
				{
					tile.autoOutputFace = EnumFacing.VALUES[o];
				}
				break;
			case 3 :
				tile.moveFully = !tile.moveFully;
				break;
			case 4 :
				tile.throwItemOut = !tile.throwItemOut;
				break;
			default:
				break;
			}
			tile.syncToPlayer(getPlayer());
		}
		return null;
	}
	
	@Override
	protected void encode(PacketBuffer output) throws IOException
	{
		super.encode(output);
		output.writeInt(this.type);
	}
	
	@Override
	protected void decode(PacketBuffer input) throws IOException
	{
		super.decode(input);
		this.type = input.readInt();
	}
}