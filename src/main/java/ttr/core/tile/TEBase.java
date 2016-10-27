package ttr.core.tile;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import ttr.api.TTrAPI;
import ttr.api.network.IPacket;
import ttr.api.tile.IActivableTile;
import ttr.api.util.Util;
import ttr.core.TTr;

public class TEBase extends TileEntity implements IActivableTile
{
	public Random random = new Random();
	public boolean isUpdating;

	public TEBase()
	{

	}

	public boolean isInitialized()
	{
		return true;
	}

	public boolean isUpdating()
	{
		return isUpdating;
	}

	@Override
	public boolean isInvalid()
	{
		return super.isInvalid();
	}

	public boolean isClient()
	{
		return worldObj == null ? !TTrAPI.isSimulating() : worldObj.isRemote;
	}

	public boolean isServer()
	{
		return worldObj == null ? TTrAPI.isSimulating() : !worldObj.isRemote;
	}

	public void sendToAll(IPacket player)
	{
		if(worldObj != null)
		{
			TTr.network.sendToAll(player);
		}
	}

	public void sendToServer(IPacket packet)
	{
		if(worldObj != null)
		{
			TTr.network.sendToServer(packet);
		}
	}

	public void sendToPlayer(IPacket packet, EntityPlayer player)
	{
		if(worldObj != null)
		{
			TTr.network.sendToPlayer(packet, player);
		}
	}

	public void sendLargeToPlayer(IPacket packet, EntityPlayer player)
	{
		if(worldObj != null)
		{
			TTr.network.sendLargeToPlayer(packet, player);
		}
	}

	public void sendToNearby(IPacket packet, float range)
	{
		if(worldObj != null)
		{
			TTr.network.sendToNearBy(packet, this, range);
		}
	}

	public void sendToDim(IPacket packet)
	{
		if(worldObj != null)
		{
			sendToDim(packet, worldObj.provider.getDimension());
		}
	}

	public void sendToDim(IPacket packet, int dim)
	{
		if(worldObj != null)
		{
			TTr.network.sendToDim(packet, dim);
		}
	}

	public void syncToAll()
	{

	}

	public void syncToDim()
	{

	}

	public void syncToNearby()
	{

	}

	public void syncToPlayer(EntityPlayer player)
	{

	}

	public void markBlockUpdate()
	{
		worldObj.notifyBlockOfStateChange(pos, getBlockType());
	}

	public void markBlockRenderUpdate()
	{
		worldObj.markBlockRangeForRenderUpdate(pos.add(-1, -1, -1), pos.add(1, 1, 1));
	}
	
	public void markLightUpdate()
	{
		worldObj.checkLight(pos);
	}

	@Override
	public double getDistanceSq(double x, double y, double z)
	{
		return super.getDistanceSq(x, y, z);
	}

	public double getDistanceFrom(double x, double y, double z)
	{
		return Math.sqrt(getDistanceSq(x, y, z));
	}

	public double getDistanceSq(Entity entity)
	{
		return getDistanceSq(entity.posX, entity.posY, entity.posZ);
	}

	public double getDistanceFrom(Entity entity)
	{
		return getDistanceFrom(entity.posX, entity.posY, entity.posZ);
	}

	public void initRotation(EnumFacing frontFacing)
	{
		
	}

	public EnumFacing getRotation()
	{
		return null;
	}
	
	public boolean setRotation(EnumFacing facing)
	{
		return false;
	}
	
	public boolean setRotation(EnumFacing firstDir, EnumFacing secondDir)
	{
		return false;
	}

	public void onNeighbourBlockChange()
	{

	}

	/**
	 * The rotate for block check,
	 * Info : The direction must be 2D rotation!
	 * @param frontOffset
	 * @param lrOffset
	 * @param udOffset
	 * @param direction
	 * @param block
	 * @param meta
	 * @param ignoreUnloadChunk
	 * @return
	 */
	public boolean matchBlock(int frontOffset, int lrOffset, int udOffset, EnumFacing direction, Block block, int meta, boolean ignoreUnloadChunk)
	{
		if(worldObj == null) return false;
		int x = frontOffset * direction.getFrontOffsetX() + lrOffset * direction.getFrontOffsetZ();
		int y = udOffset;
		int z = frontOffset * direction.getFrontOffsetZ() + lrOffset * direction.getFrontOffsetX();
		return matchBlock(x, y, z, block, meta, ignoreUnloadChunk);
	}

	public boolean matchBlock(int offsetX, int offsetY, int offsetZ, Block block, int meta, boolean ignoreUnloadChunk)
	{
		return worldObj == null ? false :
			Util.isBlock(worldObj, pos.add(offsetX, offsetY, offsetZ), block, meta, ignoreUnloadChunk);
	}

	public boolean matchBlockNearby(int offsetX, int offsetY, int offsetZ, Block block, int meta, boolean ignoreUnloadChunk)
	{
		return worldObj == null ? false :
			Util.isBlockNearby(worldObj, pos.add(offsetX, offsetY, offsetZ), block, meta, ignoreUnloadChunk);
	}

	public void removeBlock()
	{
		removeBlock(0, 0, 0);
	}

	public void removeBlock(int xOffset, int yOffset, int zOffset)
	{
		worldObj.removeTileEntity(pos.add(xOffset, yOffset, zOffset));
		worldObj.setBlockToAir(pos.add(xOffset, yOffset, zOffset));
	}

	public void onBlockBreak(IBlockState state)
	{

	}

	@Override
	public boolean onActived(EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing facing, float hitX,
			float hitY, float hitZ)
	{
		return false;
	}

	public boolean onBlockClicked(EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		return false;
	}

	public boolean canBlockStay()
	{
		return worldObj == null ? true :
			getBlockType().canPlaceBlockAt(worldObj, pos);
	}

	public IBlockState getBlock(int xOffset, int yOffset, int zOffset)
	{
		return worldObj.getBlockState(pos.add(xOffset, yOffset, zOffset));
	}

	public TileEntity getTile(int xOffset, int yOffset, int zOffset)
	{
		return worldObj.getTileEntity(pos.add(xOffset, yOffset, zOffset));
	}

	public TileEntity getTile(EnumFacing facing)
	{
		return worldObj.getTileEntity(pos.offset(facing));
	}

	public boolean isAirNearby(boolean ignoreUnloadChunk)
	{
		return Util.isAirNearby(worldObj, pos, ignoreUnloadChunk);
	}

	public boolean isCatchRain(boolean checkNeayby)
	{
		return Util.isCatchingRain(worldObj, pos, checkNeayby);
	}
	
	public void onBlockPlacedBy(IBlockState state, EntityLivingBase placer, ItemStack stack)
	{

	}

	public void getWrenchDrops(IBlockState state, int meta, ArrayList<ItemStack> drops)
	{
		drops.add(new ItemStack(state.getBlock(), 1, meta));
	}
	
	public boolean isActived()
	{
		return false;
	}

	public EnumFacing getFacing(String name)
	{
		return EnumFacing.SOUTH;
	}
}