package ttr.core.tile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.tile.IPluginAccess;
import ttr.api.util.Log;

public class TEMachineBase extends TESynchronization implements IPluginAccess
{
	public byte lastLightValue;
	public long currentState;
	public long lastCurrentState;
	protected Map<String, Integer> plugins = new HashMap();

	public static final int[][] MACHINE_ROTATION = {
			{2, 3, 1, 0, 5, 4},
			{3, 2, 0, 1, 4, 5},
			{0, 1, 2, 3, 4, 5},
			{0, 1, 3, 2, 5, 4},
			{0, 1, 4, 5, 2, 3},
			{0, 1, 5, 4, 3, 2}};
	
	private Map<String, EnumFacing[]> allowfacing = new HashMap();
	private String[] defaultfacings = new String[EnumFacing.VALUES.length];
	protected String[] facings = new String[EnumFacing.VALUES.length];
	
	protected void addFacing(String tag, EnumFacing defaultFacing)
	{
		addFacing(tag, defaultFacing, EnumFacing.VALUES);
	}
	protected void addFacing(String tag, EnumFacing defaultFacing, EnumFacing[] allowedFacings)
	{
		if(allowfacing.containsKey(tag))
		{
			Log.warn("The " + getClass() + " tag: " + tag + " has already set.");
		}
		else if(defaultfacings[defaultFacing.ordinal()] != null)
		{
			Log.warn("The " + getClass() + " facing : " + defaultFacing.name() + " has already set. Please select another facing for use.");
		}
		else
		{
			defaultfacings[defaultFacing.ordinal()] = tag;
			facings[defaultFacing.ordinal()] = tag;
			allowfacing.put(tag, allowedFacings);
		}
	}

	public EnumFacing facing;

	public TEMachineBase()
	{
		addFacing("main", EnumFacing.NORTH, getMainAllowedFacing());
	}

	protected EnumFacing[] getMainAllowedFacing()
	{
		return EnumFacing.HORIZONTALS;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt.setByte("facing", (byte) facing.ordinal());
		nbt.setLong("currentState", currentState);
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < facings.length; ++i)
		{
			if(facings[i] != null)
			{
				list.appendTag(new NBTTagString(i + facings[i]));
			}
		}
		nbt.setTag("faces", list);
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		facing = EnumFacing.VALUES[nbt.getByte("facing")];
		currentState = nbt.getLong("currentState");
		NBTTagList list = nbt.getTagList("faces", NBT.TAG_STRING);
		for (int i = 0; i < list.tagCount(); ++i)
		{
			String key = list.getStringTagAt(i);
			int id = Integer.valueOf(new String(new char[]{key.charAt(0)}));
			if(id < EnumFacing.VALUES.length && id >= 0)
			{
				key = key.substring(1);
				facings[id] = key;
			}
		}
	}
	
	@Override
	protected void writeInitalizeTag(NBTTagCompound nbt)
	{
		nbt.setByte("f", (byte) facing.ordinal());
		nbt.setLong("s", currentState);
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < facings.length; ++i)
		{
			if(facings[i] != null)
			{
				list.appendTag(new NBTTagString(i + facings[i]));
			}
		}
		nbt.setTag("fs", list);
	}
	
	@Override
	protected void readInitalizeTag(NBTTagCompound nbt)
	{
		facing = EnumFacing.VALUES[nbt.getByte("f")];
		currentState = nbt.getLong("s");
		NBTTagList list = nbt.getTagList("fs", NBT.TAG_STRING);
		for (int i = 0; i < list.tagCount(); ++i)
		{
			String key = list.getStringTagAt(i);
			int id = Integer.valueOf(new String(new char[]{key.charAt(0)}));
			if(id < EnumFacing.VALUES.length && id >= 0)
			{
				key = key.substring(1);
				facings[id] = key;
			}
		}
	}
	
	@Override
	public boolean isActived()
	{
		return false;
	}

	@Override
	public void writeToDescription(PacketBuffer buffer) throws IOException
	{
		super.writeToDescription(buffer);
		buffer.writeLong(currentState);
		byte code = 0;
		for(int i = 0; i < 6; ++i)
		{
			if(facings[i] != null)
			{
				code |= (1 << i);
			}
		}
		buffer.writeByte(code);
		for(int i = 0; i < 6; ++i)
		{
			if(facings[i] != null)
			{
				buffer.writeString(facings[i]);
			}
		}
	}
	
	@Override
	public void readFromDescription1(PacketBuffer buffer) throws IOException
	{
		super.readFromDescription1(buffer);
		facing = EnumFacing.values()[buffer.readByte()];
		currentState = buffer.readLong();
		Arrays.fill(facings, null);
		byte code = buffer.readByte();
		for(int i = 0; i < 6; ++i)
		{
			if((code & (1 << i)) != 0)
			{
				facings[i] = buffer.readStringFromBuffer(10);
			}
		}
	}
	
	@Override
	protected void preUpdateEntity()
	{
		lastCurrentState = currentState;
	}

	@Override
	protected void postUpdateEntity()
	{
		byte lightValue = (byte) worldObj.getBlockState(pos).getLightValue(worldObj, pos);
		if(lightValue != lastLightValue)
		{
			markLightUpdate();
			lastLightValue = lightValue;
		}
		if(lastCurrentState != currentState)
		{
			syncToNearby();
		}
	}
	
	protected boolean is(int flag)
	{
		return (currentState & (1 << flag)) != 0;
	}
	
	protected void swit(int flag)
	{
		currentState ^= 1 << flag;
	}

	protected void enable(int flag)
	{
		currentState |= 1 << flag;
	}

	protected void disable(int flag)
	{
		currentState &= ~(1 << flag);
	}

	protected boolean swiandget(int flag)
	{
		swit(flag);
		return is(flag);
	}

	@Override
	public void initRotation(EnumFacing frontFacing)
	{
		Arrays.fill(facings, null);
		facing = frontFacing;
		int[] off = MACHINE_ROTATION[facing.ordinal()];
		for(int i = 0; i < defaultfacings.length; ++i)
		{
			facings[off[i]] = defaultfacings[i];
		}
	}
	
	@Override
	public EnumFacing getRotation()
	{
		return facing;
	}

	@Override
	public boolean setRotation(EnumFacing facing)
	{
		if(this.facing == facing) return false;
		initRotation(facing);
		syncToNearby();
		return true;
	}
	
	@Override
	public boolean setRotation(EnumFacing firstDir, EnumFacing secondDir)
	{
		if(facings[firstDir.ordinal()] == null && facings[secondDir.ordinal()] == null)
			return false;
		String key1 = facings[firstDir.ordinal()];
		String key2 = facings[secondDir.ordinal()];
		if(key1 != key2 && rotateAllowed(key1, secondDir) && rotateAllowed(key2, firstDir))
		{
			if(facing == firstDir)
			{
				facing = secondDir;
			}
			else if(facing == secondDir)
			{
				facing = firstDir;
			}
			facings[secondDir.ordinal()] = key1;
			facings[firstDir.ordinal()] = key2;
			syncToNearby();
			return true;
		}
		return false;
	}
	
	protected boolean rotateAllowed(String tag, EnumFacing side)
	{
		if(tag == null) return true;
		if(!allowfacing.containsKey(tag)) return false;
		for(EnumFacing facing : allowfacing.get(tag))
		{
			if(facing == side) return true;
		}
		return false;
	}

	public int getFacingID(String key)
	{
		for(int i = 0; i < facings.length; ++i)
		{
			if(key.equals(facings[i]))
				return i;
		}
		return -1;
	}

	@Override
	public EnumFacing getFacing(String key)
	{
		int v = getFacingID(key);
		return v == -1 ? null : EnumFacing.VALUES[v];
	}
	
	protected void writePluginToNBT(String key, NBTTagCompound nbt)
	{
		if(!plugins.isEmpty())
		{
			NBTTagList list = new NBTTagList();
			for(Entry<String, Integer> plugin : plugins.entrySet())
			{
				list.appendTag(new NBTTagString(plugin.getKey() + ":" + plugin.getValue()));
			}
			nbt.setTag(key, list);
		}
	}

	protected void readPluginFromNBT(String key, NBTTagCompound nbt)
	{
		plugins.clear();
		if(nbt.hasKey(key, NBT.TAG_LIST))
		{
			NBTTagList list = nbt.getTagList(key, NBT.TAG_STRING);
			for(int i = 0; i < list.tagCount(); ++i)
			{
				String value = list.getStringTagAt(i);
				String[] split = value.split(":");
				plugins.put(split[0], Integer.valueOf(split[1]));
			}
		}
	}
	
	@Override
	public void getWrenchDrops(IBlockState state, int meta, ArrayList<ItemStack> drops)
	{
		ItemStack stack = new ItemStack(state.getBlock(), 1, meta);
		NBTTagCompound nbt = writeNBTToStack();
		if(nbt != null)
		{
			stack.setTagCompound(nbt);
		}
		drops.add(stack);
	}

	@Override
	public void onBlockPlacedBy(IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		super.onBlockPlacedBy(state, placer, stack);
		if(stack.hasTagCompound())
		{
			readFromNBTAtStack(stack.getTagCompound());
		}
	}
	
	protected void readFromNBTAtStack(NBTTagCompound nbt)
	{
		readPluginFromNBT("plugins", nbt);
	}
	
	protected NBTTagCompound writeNBTToStack()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writePluginToNBT("plugins", nbt);
		return nbt;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean shouldDisplayMatrixWhenSelected(EnumFacing side, EntityPlayer player)
	{
		return false;
	}

	@Override
	public int getPluginLevel(String key)
	{
		return plugins.getOrDefault(key, 0);
	}
	
	protected boolean isPluginAllowed(String key)
	{
		return false;
	}
	
	@Override
	public boolean addPlugin(String key, int level)
	{
		if(getPluginLevel(key) < level && isPluginAllowed(key))
		{
			plugins.put(key, level);
			return true;
		}
		return false;
	}
	
	@Override
	public void removePlugin(String key)
	{
		plugins.remove(key);
	}
}