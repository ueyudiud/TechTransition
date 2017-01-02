package ttr.core.tile.boiler;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.fuel.FuelHandler;
import ttr.api.fuel.ITTrFuelHandler;
import ttr.api.inventory.Inventory;
import ttr.api.inventory.Inventory.FDType;
import ttr.api.tile.IContainerableTile;
import ttr.core.gui.boiler.ContainerCoalBoiler;
import ttr.core.gui.boiler.GuiCoalBoiler;
import ttr.load.TTrLangs;

public abstract class TECoalBoiler extends TEBoiler implements IContainerableTile
{
	private static final int Burning = 16;
	private static final int[] OUT = {1, 3};
	private static final int[] IN = {0};
	private static final int[] FUEL = {2};
	
	public static class TEBronzeCoalBoiler extends TECoalBoiler
	{
		public TEBronzeCoalBoiler()
		{
			super(TTrLangs.boilerCoalBronze, 1200, 0.2F, 16000, 16000, 0.8F);
		}
		
		@Override
		protected int getMaxOutput()
		{
			return 60;
		}
		
		@Override
		protected float powerMultiper()
		{
			return 0.2F;
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public GuiContainer getGui(EnumFacing side, EntityPlayer player)
		{
			return new GuiCoalBoiler.GuiCoalBoilerBronze(player, this);
		}
	}
	
	public static class TEInvarCoalBoiler extends TECoalBoiler
	{
		public TEInvarCoalBoiler()
		{
			super(TTrLangs.boilerCoalInvar, 1600, 0.16F, 16000, 16000, 0.85F);
		}
		
		@Override
		protected int getMaxOutput()
		{
			return 80;
		}
		
		@Override
		protected float powerMultiper()
		{
			return 0.5F;
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public GuiContainer getGui(EnumFacing side, EntityPlayer player)
		{
			return new GuiCoalBoiler.GuiCoalBoilerInvar(player, this);
		}
	}
	
	public static class TESteelCoalBoiler extends TECoalBoiler
	{
		public TESteelCoalBoiler()
		{
			super(TTrLangs.boilerCoalSteel, 2000, 0.125F, 16000, 16000, 0.9F);
		}
		
		@Override
		protected int getMaxOutput()
		{
			return 120;
		}
		
		@Override
		protected float powerMultiper()
		{
			return 1.0F;
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public GuiContainer getGui(EnumFacing side, EntityPlayer player)
		{
			return new GuiCoalBoiler.GuiCoalBoilerSteel(player, this);
		}
	}
	
	protected int currentItemTemp = 298;
	public int currentMaxBurnTime;
	public int burnTime;
	protected int power = 20;
	
	public TECoalBoiler(String name, int maxTemperature, float outputPressure, int waterCap, int steamCap, float efficiency)
	{
		super(maxTemperature, outputPressure, waterCap, steamCap, efficiency);
		this.inventory = new Inventory(4, name, 64);
		addFacing("fuel", EnumFacing.SOUTH);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if ((nbt.hasKey("burnTime")) && (nbt.getInteger("burnTime") > 0))
		{
			this.currentItemTemp = nbt.getInteger("currentItemTemperature");
			this.currentMaxBurnTime = nbt.getInteger("currentMaxBurnTime");
			this.burnTime = nbt.getInteger("burnTime");
			this.power = nbt.getInteger("power");
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		if (this.burnTime > 0)
		{
			nbt.setInteger("currentItemTemperature", this.currentItemTemp);
			nbt.setInteger("currentMaxBurnTime", this.currentMaxBurnTime);
			nbt.setFloat("burnTime", this.burnTime);
			nbt.setInteger("power", this.power);
		}
		return super.writeToNBT(nbt);
	}
	
	@Override
	protected void updateServer()
	{
		updateWaterTank();
		updateBurnState();
		super.updateServer();
	}
	
	protected void updateWaterTank()
	{
		this.inventory.fillOrDrainInventoryTank(this.tank1, 0, 1, FDType.D);
	}
	
	protected void updateBurnState()
	{
		if (this.temperature < this.currentItemTemp)
		{
			this.temperature += 1;
		}
		else if (this.temperature > this.currentItemTemp)
		{
			this.temperature -= 1;
		}
		if (this.burnTime > 0)
		{
			this.burnTime --;
			if (this.burnTime <= 0)
			{
				this.currentItemTemp = 298;
				disable(Burning);
				syncToNearby();
			}
		}
		if (this.burnTime <= 0)
		{
			if(burnFuel())
			{
				enable(Burning);
				syncToNearby();
			}
		}
	}
	
	protected boolean burnFuel()
	{
		if (this.inventory.stacks[2] == null)
			return false;
		ITTrFuelHandler.FuelInfo info = FuelHandler.getFuelInfo(this.inventory.stacks[2], true);
		if (info != null)
		{
			this.power = 20 + (int) (info.fuelPower * powerMultiper());
			this.burnTime = this.currentMaxBurnTime = (int) (info.burnTick * info.fuelPower * this.efficiency / this.power);
			this.currentItemTemp = info.maxTemp;
			if (--this.inventory.stacks[2].stackSize == 0)
			{
				this.inventory.stacks[2] = this.inventory.stacks[2].getItem().getContainerItem(this.inventory.stacks[2]);
			}
			this.inventory.addStack(3, info.output, true);
			if(info.effect != null)
			{
				EntityAreaEffectCloud entity = new EntityAreaEffectCloud(this.worldObj, this.pos.getX() + .5, this.pos.getY() + .5, this.pos.getZ() + .5);
				entity.setParticle(EnumParticleTypes.SMOKE_NORMAL);
				entity.addEffect(info.effect);
				entity.setDuration(this.currentMaxBurnTime);
				this.worldObj.spawnEntityInWorld(entity);
			}
			return true;
		}
		else
		{
			this.power = 20;
		}
		return false;
	}
	
	@Override
	protected int getPower()
	{
		return this.power;
	}
	
	protected float powerMultiper()
	{
		return 0.0F;
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == getFacing("fuel") ? FUEL : super.getSlotsForFace(side);
	}
	
	@Override
	protected int[] getInputSlots()
	{
		return IN;
	}
	
	@Override
	protected int[] getOutputSlots()
	{
		return OUT;
	}
	
	@Override
	public int getFieldCount()
	{
		return 4;
	}
	
	@Override
	public int getField(int id)
	{
		switch (id)
		{
		case 0 : return this.amount;
		case 1 : return this.temperature;
		case 2 : return this.currentMaxBurnTime;
		case 3 : return this.burnTime;
		default: return 0;
		}
	}
	
	@Override
	public void setField(int id, int value)
	{
		switch(id)
		{
		case 0 : this.amount = value; break;
		case 1 : this.temperature = value; break;
		case 2 : this.currentMaxBurnTime = value; break;
		case 3 : this.burnTime = value; break;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getBurnProgress(int scale)
	{
		return this.currentMaxBurnTime == 0 ? 0 : (int) ((float) (this.burnTime * scale) / (float) this.currentMaxBurnTime);
	}
	
	@SideOnly(Side.CLIENT)
	public boolean isBurning()
	{
		return is(Burning);
	}
	
	@Override
	public boolean isActived()
	{
		return isBurning();
	}
	
	@Override
	public Container getContainer(EnumFacing side, EntityPlayer player)
	{
		return new ContainerCoalBoiler(player, this);
	}
}