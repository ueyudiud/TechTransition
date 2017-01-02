package ttr.core.tile;

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
		if(this.allowfacing.containsKey(tag))
		{
			Log.warn("The " + getClass() + " tag: " + tag + " has already set.");
		}
		else if(this.defaultfacings[defaultFacing.ordinal()] != null)
		{
			Log.warn("The " + getClass() + " facing : " + defaultFacing.name() + " has already set. Please select another facing for use.");
		}
		else
		{
			this.defaultfacings[defaultFacing.ordinal()] = tag;
			this.facings[defaultFacing.ordinal()] = tag;
			this.allowfacing.put(tag, allowedFacings);
		}
	}
	
	public EnumFacing facing = EnumFacing.NORTH;
	
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
		nbt.setByte("facing", (byte) this.facing.ordinal());
		nbt.setLong("currentState", this.currentState);
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < this.facings.length; ++i)
		{
			if(this.facings[i] != null)
			{
				list.appendTag(new NBTTagString(i + this.facings[i]));
			}
		}
		nbt.setTag("faces", list);
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.facing = EnumFacing.VALUES[nbt.getByte("facing")];
		this.currentState = nbt.getLong("currentState");
		NBTTagList list = nbt.getTagList("faces", NBT.TAG_STRING);
		for (int i = 0; i < list.tagCount(); ++i)
		{
			String key = list.getStringTagAt(i);
			int id = Integer.valueOf(new String(new char[]{key.charAt(0)}));
			if(id < EnumFacing.VALUES.length && id >= 0)
			{
				key = key.substring(1);
				this.facings[id] = key;
			}
		}
	}
	
	@Override
	public void writeToDescription(NBTTagCompound nbt)
	{
		super.writeToDescription(nbt);
		nbt.setByte("f", (byte) this.facing.ordinal());
		nbt.setLong("s", this.currentState);
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < this.facings.length; ++i)
		{
			if(this.facings[i] != null)
			{
				list.appendTag(new NBTTagString(i + this.facings[i]));
			}
		}
		nbt.setTag("fs", list);
	}
	
	@Override
	public void readFromDescription(NBTTagCompound nbt)
	{
		boolean flag = false;
		super.readFromDescription(nbt);
		this.facing = EnumFacing.VALUES[nbt.getByte("f")];
		long state = nbt.getLong("s");
		if(state != this.currentState)
		{
			this.currentState = state;
			flag = true;
		}
		NBTTagList list = nbt.getTagList("fs", NBT.TAG_STRING);
		for (int i = 0; i < list.tagCount(); ++i)
		{
			String key = list.getStringTagAt(i);
			int id = Integer.valueOf(new String(new char[]{key.charAt(0)}));
			if(id < EnumFacing.VALUES.length && id >= 0)
			{
				key = key.substring(1);
				this.facings[id] = key;
			}
		}
		if(flag)
		{
			markBlockRenderUpdate();
		}
	}
	
	@Override
	public boolean isActived()
	{
		return false;
	}
	
	@Override
	protected void preUpdateEntity()
	{
		this.lastCurrentState = this.currentState;
	}
	
	@Override
	protected void postUpdateEntity()
	{
		byte lightValue = (byte) this.worldObj.getBlockState(this.pos).getLightValue(this.worldObj, this.pos);
		if(lightValue != this.lastLightValue)
		{
			markLightUpdate();
			this.lastLightValue = lightValue;
		}
		if(this.lastCurrentState != this.currentState)
		{
			syncToNearby();
		}
	}
	
	protected boolean is(int flag)
	{
		return (this.currentState & (1 << flag)) != 0;
	}
	
	protected void swit(int flag)
	{
		this.currentState ^= 1 << flag;
	}
	
	protected void enable(int flag)
	{
		this.currentState |= 1 << flag;
	}
	
	protected void disable(int flag)
	{
		this.currentState &= ~(1 << flag);
	}
	
	protected boolean swiandget(int flag)
	{
		swit(flag);
		return is(flag);
	}
	
	@Override
	public void initRotation(EnumFacing frontFacing)
	{
		Arrays.fill(this.facings, null);
		this.facing = frontFacing;
		int[] off = MACHINE_ROTATION[this.facing.ordinal()];
		for(int i = 0; i < this.defaultfacings.length; ++i)
		{
			this.facings[off[i]] = this.defaultfacings[i];
		}
	}
	
	@Override
	public EnumFacing getRotation()
	{
		return this.facing;
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
		if(this.facings[firstDir.ordinal()] == null && this.facings[secondDir.ordinal()] == null)
			return false;
		String key1 = this.facings[firstDir.ordinal()];
		String key2 = this.facings[secondDir.ordinal()];
		if(key1 != key2 && rotateAllowed(key1, secondDir) && rotateAllowed(key2, firstDir))
		{
			if(this.facing == firstDir)
			{
				this.facing = secondDir;
			}
			else if(this.facing == secondDir)
			{
				this.facing = firstDir;
			}
			this.facings[secondDir.ordinal()] = key1;
			this.facings[firstDir.ordinal()] = key2;
			syncToNearby();
			return true;
		}
		return false;
	}
	
	protected boolean rotateAllowed(String tag, EnumFacing side)
	{
		if(tag == null) return true;
		if(!this.allowfacing.containsKey(tag)) return false;
		for(EnumFacing facing : this.allowfacing.get(tag))
		{
			if(facing == side) return true;
		}
		return false;
	}
	
	public int getFacingID(String key)
	{
		for(int i = 0; i < this.facings.length; ++i)
		{
			if(key.equals(this.facings[i]))
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
		if(!this.plugins.isEmpty())
		{
			NBTTagList list = new NBTTagList();
			for(Entry<String, Integer> plugin : this.plugins.entrySet())
			{
				list.appendTag(new NBTTagString(plugin.getKey() + ":" + plugin.getValue()));
			}
			nbt.setTag(key, list);
		}
	}
	
	protected void readPluginFromNBT(String key, NBTTagCompound nbt)
	{
		this.plugins.clear();
		if(nbt.hasKey(key, NBT.TAG_LIST))
		{
			NBTTagList list = nbt.getTagList(key, NBT.TAG_STRING);
			for(int i = 0; i < list.tagCount(); ++i)
			{
				String value = list.getStringTagAt(i);
				String[] split = value.split(":");
				this.plugins.put(split[0], Integer.valueOf(split[1]));
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
		return this.plugins.getOrDefault(key, 0);
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
			this.plugins.put(key, level);
			return true;
		}
		return false;
	}
	
	@Override
	public void removePlugin(String key)
	{
		this.plugins.remove(key);
	}
}