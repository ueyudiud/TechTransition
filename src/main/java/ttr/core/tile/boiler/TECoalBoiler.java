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
			super(TTrLangs.boilerCoalBronze, 1200, 0.2F, 8000, 4000, 1.0F);
		}

		@Override
		protected int getMaxOutput()
		{
			return 12;
		}

		@Override
		protected float powerMultiper()
		{
			return 1.2F;
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
			super(TTrLangs.boilerCoalInvar, 1600, 0.16F, 8000, 6000, 0.95F);
		}

		@Override
		protected int getMaxOutput()
		{
			return 20;
		}
		
		@Override
		protected float powerMultiper()
		{
			return 4.0F;
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
			super(TTrLangs.boilerCoalSteel, 2000, 0.125F, 8000, 8000, 0.9F);
		}

		@Override
		protected int getMaxOutput()
		{
			return 30;
		}

		@Override
		protected float powerMultiper()
		{
			return 7.5F;
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
	protected int power;
	
	public TECoalBoiler(String name, int maxTemperature, float outputPressure, int waterCap, int steamCap, float efficiency)
	{
		super(maxTemperature, outputPressure, waterCap, steamCap, efficiency);
		inventory = new Inventory(4, name, 64);
		addFacing("fuel", EnumFacing.NORTH);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if ((nbt.hasKey("burnTime")) && (nbt.getInteger("burnTime") > 0))
		{
			currentItemTemp = nbt.getInteger("currentItemTemperature");
			currentMaxBurnTime = nbt.getInteger("currentMaxBurnTime");
			burnTime = nbt.getInteger("burnTime");
			power = nbt.getInteger("power");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		if (burnTime > 0)
		{
			nbt.setInteger("currentItemTemperature", currentItemTemp);
			nbt.setInteger("currentMaxBurnTime", currentMaxBurnTime);
			nbt.setFloat("burnTime", burnTime);
			nbt.setInteger("power", power);
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
		inventory.fillOrDrainInventoryTank(tank1, 0, 1, FDType.D);
	}

	protected void updateBurnState()
	{
		if (temperature < currentItemTemp)
		{
			temperature += 1;
		}
		else if (temperature > currentItemTemp)
		{
			temperature -= 1;
		}
		if (burnTime > 0.0F)
		{
			burnTime -= powerMultiper();
			if (burnTime <= 0.0F)
			{
				currentItemTemp = 298;
				disable(Burning);
				syncToNearby();
			}
		}
		if (burnTime <= 0.0F)
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
		if (inventory.stacks[2] == null)
			return false;
		ITTrFuelHandler.FuelInfo info = FuelHandler.getFuelInfo(inventory.stacks[2], true);
		if (info != null)
		{
			burnTime += (currentMaxBurnTime = (int) (info.burnTick / powerMultiper()));
			power = (int) (info.fuelPower * powerMultiper() * efficiency);
			currentItemTemp = info.maxTemp;
			if (--inventory.stacks[2].stackSize == 0)
			{
				inventory.stacks[2] = inventory.stacks[2].getItem().getContainerItem(inventory.stacks[2]);
			}
			inventory.addStack(3, info.output, true);
			if(info.effect != null)
			{
				EntityAreaEffectCloud entity = new EntityAreaEffectCloud(worldObj, pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5);
				entity.setParticle(EnumParticleTypes.SMOKE_NORMAL);
				entity.addEffect(info.effect);
				entity.setDuration(currentMaxBurnTime);
				worldObj.spawnEntityInWorld(entity);
			}
			return true;
		}
		return false;
	}
	
	@Override
	protected int getPower()
	{
		return power;
	}
	
	protected float powerMultiper()
	{
		return 1.0F;
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
		case 0 : return amount;
		case 1 : return temperature;
		case 2 : return currentMaxBurnTime;
		case 3 : return burnTime;
		default: return 0;
		}
	}

	@Override
	public void setField(int id, int value)
	{
		switch(id)
		{
		case 0 : amount = value; break;
		case 1 : temperature = value; break;
		case 2 : currentMaxBurnTime = value; break;
		case 3 : burnTime = value; break;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getBurnProgress(int scale)
	{
		return currentMaxBurnTime == 0 ? 0 : (int) ((float) (burnTime * scale) / (float) currentMaxBurnTime);
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