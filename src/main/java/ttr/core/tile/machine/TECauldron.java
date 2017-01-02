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
			this.fluid = FluidRegistry.getFluid(compound.getString("fluid"));
			this.level = compound.getByte("level");
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		if(this.fluid != null && this.level > 0)
		{
			compound.setString("fluid", this.fluid.getName());
			compound.setByte("level", (byte) this.level);
		}
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromDescription(NBTTagCompound nbt)
	{
		super.readFromDescription(nbt);
		//		if(nbt.hasKey("f"))
		{
			this.fluid = FluidRegistry.getFluid(nbt.getString("f"));
			this.level = nbt.getByte("l");
		}
		markBlockRenderUpdate();
	}
	
	@Override
	public void writeToDescription(NBTTagCompound nbt)
	{
		super.writeToDescription(nbt);
		if(this.fluid != null && this.level > 0)
		{
			nbt.setString("f", this.fluid.getName());
			nbt.setByte("l", (byte) this.level);
		}
	}
	
	public Fluid getFluidType()
	{
		checkClientCondition();
		return this.level == 0 ? null : this.fluid;
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
		if(this.worldObj.isRemote) return;
		amt = MathHelper.clamp_int(amt, 0, 3);
		boolean flag = amt != this.level;
		if(this.fluid != fluid)
		{
			this.fluid = fluid;
			flag = true;
		}
		this.level = amt;
		if(flag)
		{
			markBlockUpdate();
		}
	}
	
	public int getLevel()
	{
		checkClientCondition();
		return this.level;
	}
	
	public boolean useFluid(boolean process)
	{
		if(this.level > 0)
		{
			if(process)
			{
				--this.level;
				if(this.level == 0)
				{
					this.fluid = null;
				}
				updateFluidState();
			}
			return true;
		}
		return false;
	}
	
	public void checkClientCondition()
	{
		if(this.worldObj.isRemote && !this.initialized)
		{
			sendToServer(new PacketTEAsk(this.worldObj, this.pos));
		}
	}
	
	@Override
	public void markBlockRenderUpdate()
	{
		int light = getLightValue();
		if(this.prelight != light)
		{
			this.prelight = light;
			this.worldObj.checkLightFor(EnumSkyBlock.BLOCK, this.pos);
		}
		super.markBlockRenderUpdate();
	}
	
	public void updateFluidState()
	{
		syncToNearby();
	}
	
	public int getLightValue()
	{
		return this.fluid == null ? 0 :
			(int) (this.fluid.getLuminosity() * LIGHT_VALUE_COEFFICIENT[this.level]);
	}
}