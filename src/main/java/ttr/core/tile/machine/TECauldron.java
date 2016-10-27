package ttr.core.tile.machine;

import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import ttr.api.net.tile.PacketTEAsk;
import ttr.core.tile.TEStatic;

public class TECauldron extends TEStatic
{
	public int washingBuf;

	private static final float[] LIGHT_VALUE_COEFFICIENT = {0F, 0.64F, 0.8F, 1.0F};
	protected Fluid fluid = null;
	private int prelight;
	public int level;

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		if(compound.hasKey("fluid"))
		{
			fluid = FluidRegistry.getFluid(compound.getString("fluid"));
			level = compound.getByte("level");
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		if(fluid != null && level > 0)
		{
			compound.setString("fluid", fluid.getName());
			compound.setByte("level", (byte) level);
		}
		return super.writeToNBT(compound);
	}
	
	@Override
	protected void readInitalizeTag(NBTTagCompound tag)
	{
		super.readInitalizeTag(tag);
		if(tag.hasKey("f"))
		{
			fluid = FluidRegistry.getFluid(tag.getString("f"));
			level = tag.getByte("l");
		}
	}
	
	@Override
	protected void writeInitalizeTag(NBTTagCompound nbt)
	{
		super.writeInitalizeTag(nbt);
		if(fluid != null && level > 0)
		{
			nbt.setString("f", fluid.getName());
			nbt.setByte("l", (byte) level);
		}
	}
	
	@Override
	public void readFromDescription1(PacketBuffer buffer) throws IOException
	{
		super.readFromDescription1(buffer);
		level = buffer.readByte();
		if(level > 0)
		{
			fluid = FluidRegistry.getFluid(buffer.readStringFromBuffer(99));
		}
	}

	@Override
	public void writeToDescription(PacketBuffer buffer) throws IOException
	{
		super.writeToDescription(buffer);
		buffer.writeByte(level);
		if(level > 0)
		{
			buffer.writeString(fluid.getName());
		}
	}

	public Fluid getFluidType()
	{
		checkClientCondition();
		return level == 0 ? null : fluid;
	}

	public void setFluidType(Fluid fluid)
	{
		if(this.fluid != fluid)
		{
			this.fluid = fluid;
			updateFluidState();
		}
	}
	
	public void setFluidType(Fluid fluid, int amt)
	{
		if(worldObj.isRemote) return;
		amt = MathHelper.clamp_int(amt, 0, 3);
		boolean flag = amt != level;
		if(this.fluid != fluid)
		{
			this.fluid = fluid;
			flag = true;
		}
		level = amt;
		if(flag)
		{
			markBlockUpdate();
		}
	}

	public int getLevel()
	{
		checkClientCondition();
		return level;
	}

	public boolean useFluid(boolean process)
	{
		if(level > 0)
		{
			if(process)
			{
				--level;
				if(level == 0)
				{
					fluid = null;
				}
				updateFluidState();
			}
			return true;
		}
		return false;
	}

	public void checkClientCondition()
	{
		if(worldObj.isRemote && !initialized)
		{
			sendToServer(new PacketTEAsk(worldObj, pos));
		}
	}
	
	@Override
	public void markBlockRenderUpdate()
	{
		int light = getLightValue();
		if(prelight != light)
		{
			prelight = light;
			worldObj.checkLightFor(EnumSkyBlock.BLOCK, pos);
		}
		super.markBlockRenderUpdate();
	}

	public void updateFluidState()
	{
		syncToNearby();
	}

	public int getLightValue()
	{
		return fluid == null ? 0 :
			(int) (fluid.getLuminosity() * LIGHT_VALUE_COEFFICIENT[level]);
	}
}