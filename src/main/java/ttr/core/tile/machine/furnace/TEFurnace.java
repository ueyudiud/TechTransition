package ttr.core.tile.machine.furnace;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import ttr.api.fuel.FuelHandler;
import ttr.api.fuel.ITTrFuelHandler.FuelInfo;
import ttr.api.inventory.Inventory;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.recipe.TemplateRecipeMap.TemplateRecipe;
import ttr.api.tile.IContainerableTile;
import ttr.core.gui.machine.furnace.ContainerFurnace;
import ttr.core.gui.machine.furnace.GuiFurnace;
import ttr.core.tile.TEMachineRecipeMapFloatPower;

public class TEFurnace extends TEMachineRecipeMapFloatPower implements IContainerableTile
{
	private static final int Burning = 16;
	private static final int[] FUEL = {2};

	public int temperature = 298;
	public int maxTemperature = 298;
	public int currentBurnTime;
	public int burnTime;
	public int burnPower;
	public int requireTemp;
	
	public TEFurnace()
	{
		super(1, 1, 0, 0);
		inventory = new Inventory(1, "Smelting", 64);
		inventory.setTile(this);
		addFacing("burn", EnumFacing.NORTH);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		requireTemp = nbt.getInteger("tempReq");
		temperature = nbt.getInteger("temperature");
		if(burnTime > 0)
		{
			maxTemperature = nbt.getInteger("maxTemp");
			currentBurnTime = nbt.getInteger("current");
			burnTime = nbt.getInteger("burn");
			burnPower = nbt.getInteger("power");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("tempReq", requireTemp);
		nbt.setInteger("temperature", temperature);
		if(nbt.hasKey("burn"))
		{
			nbt.setInteger("current", currentBurnTime);
			nbt.setInteger("burn", burnTime);
			nbt.setInteger("power", burnPower);
			nbt.setInteger("maxTemp", maxTemperature);
		}
		return nbt;
	}
	
	@Override
	public boolean isActived()
	{
		return is(Burning);
	}
	
	@Override
	protected void updateClient()
	{
		super.updateClient();
	}
	
	@Override
	protected void updateServer()
	{
		int t = Math.min(getMaxTemperature(), maxTemperature);
		if(temperature < t)
		{
			++temperature;
		}
		else if(temperature > t)
		{
			--temperature;
		}
		if(burnTime > 0)
		{
			if(--burnTime == 0)
			{
				maxTemperature = 298;
				burnPower = 0;
				disable(Burning);
			}
		}
		if(burnTime == 0)
		{
			if(burnFuel())
			{
				enable(Burning);
			}
		}
		super.updateServer();
		int temp = getMaxTemperature();
		if(temperature > temp * 2)
		{
			worldObj.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 2.0F + temperature / 500F, false);
			worldObj.setBlockToAir(pos);
			worldObj.removeTileEntity(pos);
			return;
		}
	}

	@Override
	protected void initRecipeInput(TemplateRecipe recipe)
	{
		super.initRecipeInput(recipe);
		requireTemp = (int) recipe.customValue;
	}
	
	@Override
	protected void initRecipeOutput()
	{
		super.initRecipeOutput();
		requireTemp = 0;
	}
	
	@Override
	protected void onWorking()
	{
		if(useEnergy())
		{
			if(is(Working) && temperature >= requireTemp)
			{
				++duration;
			}
		}
	}

	protected int getMaxTemperature()
	{
		return 1000;
	}

	private boolean burnFuel()
	{
		if(inventory.stacks[0] == null) return false;
		FuelInfo info = FuelHandler.getFuelInfo(inventory.stacks[0]);
		if(info != null)
		{
			currentBurnTime = burnTime = info.burnTick;
			burnPower = info.fuelPower;
			maxTemperature = info.maxTemp;
			if(--inventory.stacks[0].stackSize == 0)
			{
				inventory.stacks[0] = inventory.stacks[0]
						.getItem().getContainerItem(inventory.stacks[0]);
			}
			if(info.effect != null)
			{
				EntityAreaEffectCloud entity = new EntityAreaEffectCloud(worldObj, pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5);
				entity.setParticle(EnumParticleTypes.SMOKE_NORMAL);
				entity.addEffect(info.effect);
				entity.setDuration(currentBurnTime);
				worldObj.spawnEntityInWorld(entity);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
		return super.getCapability(capability, facing);
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == getFacing("burn") ? FUEL : super.getSlotsForFace(side);
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction)
	{
		return super.canInsertItem(index, stack, direction) ? true : index == 2 && FuelHandler.isItemFuel(stack);
	}
	
	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		return index == 2 ? !FuelHandler.isItemFuel(stack) : super.canExtractItem(index, stack, direction);
	}
	
	@Override
	public Container getContainer(EnumFacing side, EntityPlayer player)
	{
		return new ContainerFurnace(player, this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiContainer getGui(EnumFacing side, EntityPlayer player)
	{
		return new GuiFurnace(player, this);
	}
	
	@SideOnly(Side.CLIENT)
	public boolean isBurning()
	{
		return isActived();
	}

	@SideOnly(Side.CLIENT)
	public boolean isSmelting()
	{
		return is(Working);
	}

	@Override
	public int getFieldCount()
	{
		return 7;
	}

	@Override
	public int getField(int id)
	{
		switch (id)
		{
		case 0 : return (int) (duration & 0xFFFFFFFF);
		case 1 : return (int) ((duration >> 32) & 0xFFFFFFFF);
		case 2 : return (int) (maxDuration & 0xFFFFFFFF);
		case 3 : return (int) ((maxDuration >> 32) & 0xFFFFFFFF);
		case 4 : return burnTime;
		case 5 : return currentBurnTime;
		case 6 : return temperature;
		default: return 0;
		}
	}

	@Override
	public void setField(int id, int value)
	{
		switch (id)
		{
		case 0 : duration &= 0xFFFFFFFF00000000L; duration |= value; break;
		case 1 : duration &= 0xFFFFFFFFL; duration |= value << 32; break;
		case 2 : maxDuration &= 0xFFFFFFFF00000000L; maxDuration |= value; break;
		case 3 : maxDuration &= 0xFFFFFFFFL; maxDuration |= value << 32; break;
		case 4 : burnTime = value; break;
		case 5 : currentBurnTime = value; break;
		case 6 : temperature = value; break;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getBurnProgress(int scale)
	{
		return (int) (scale * (float) burnTime / currentBurnTime);
	}

	@Override
	protected TemplateRecipeMap getRecipeMap()
	{
		return TemplateRecipeMap.SMELTING;
	}

	@Override
	protected boolean useEnergy()
	{
		return burnPower >= minPower;
	}

	@Override
	protected long getEnergyInput()
	{
		return burnPower;
	}

	@Override
	protected long getPower()
	{
		return burnPower;
	}

	@Override
	protected boolean matchRecipeSpecial(TemplateRecipe recipe)
	{
		return temperature >= recipe.customValue;
	}
}