package ttr.core.tile.machine;

import net.minecraft.nbt.NBTTagCompound;
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
	public void readFromDescription(NBTTagCompound nbt)
	{
		super.readFromDescription(nbt);
		if(nbt.hasKey("f"))
		{
			fluid = FluidRegistry.getFluid(nbt.getString("f"));
			level = nbt.getByte("l");
		}
		markBlockRenderUpdate();
	}

	@Override
	public void writeToDescription(NBTTagCompound nbt)
	{
		super.writeToDescription(nbt);
		if(fluid != null && level > 0)
		{
			nbt.setString("f", fluid.getName());
			nbt.setByte("l", (byte) level);
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