package ttr.core.tile.machine;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.fuel.FuelHandler;
import ttr.api.fuel.ITTrFuelHandler.FuelInfo;
import ttr.api.inventory.Inventory;
import ttr.api.recipe.IRecipeMap;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.recipe.TemplateRecipeMap.TemplateRecipe;
import ttr.api.tile.IContainerableTile;
import ttr.core.gui.machine.ContainerForge;
import ttr.core.gui.machine.GuiForge;
import ttr.core.tile.TEMultiBlockStructureRecipeMapFloatPower;
import ttr.load.TTrLangs;

public class TEForge extends TEMultiBlockStructureRecipeMapFloatPower implements IContainerableTile
{
	private static final int Burning = 16;
	
	public int temperature = 298;
	public int maxTemperature = 298;
	public int currentBurnTime;
	public int burnTime;
	public int burnPower;
	
	public TEForge()
	{
		super(3, 3, 0, 0);
		inventory = new Inventory(1, TTrLangs.forge, 64);
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
	protected void updateServer()
	{
		int t = maxTemperature;
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
	}

	@Override
	protected void onMissingStructure()
	{
		super.onMissingStructure();
		initRecipeOutput();
	}

	@Override
	protected boolean matchRecipeSpecial(TemplateRecipe recipe)
	{
		return temperature >= recipe.customValue;
	}
	
	@Override
	protected IRecipeMap<TemplateRecipe> getRecipeMap()
	{
		return TemplateRecipeMap.FORGE;
	}

	private boolean burnFuel()
	{
		if(inventory.stacks[0] == null) return false;
		FuelInfo info = FuelHandler.getFuelInfo(inventory.stacks[0], true);
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
	protected boolean useEnergy()
	{
		return burnPower >= minPower;
	}

	@Override
	protected boolean checkStructure()
	{
		IBlockState BRICK = Blocks.BRICK_BLOCK.getDefaultState();
		MutableBlockPos pos1 = new MutableBlockPos();
		for(int i = -1; i <= 1; ++i)
		{
			for(int k = -1; k <= 1; ++k)
			{
				for(int j = -2; j <= 0; ++j)
				{
					pos1.setPos(pos.getX() + i, pos.getY() + j, pos.getZ() + k);
					if(i == 0 && k == 0)
					{
						if(j == -1)
						{
							if(!worldObj.isAirBlock(pos1)) return false;
							continue;
						}
						else if(j != -2)
						{
							continue;
						}
					}
					if(worldObj.getBlockState(pos1) != BRICK)
						return false;
				}
			}
		}
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean isBurning()
	{
		return burnTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public boolean isSmelting()
	{
		return duration > 0;
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
	public Container getContainer(EnumFacing side, EntityPlayer player)
	{
		return new ContainerForge(player, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiContainer getGui(EnumFacing side, EntityPlayer player)
	{
		return new GuiForge(player, this);
	}
}