package ttr.core.tile.machine.steam;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.inventory.Inventory;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.tile.IContainerableTile;
import ttr.api.tile.ISteamInputHatch;
import ttr.core.block.BlockBrick;
import ttr.core.gui.machine.steam.ContainerSteamCompressor;
import ttr.core.gui.machine.steam.GuiSteamCompressor;
import ttr.core.tile.TEMultiBlockStructureRecipeMapFloatPower;
import ttr.load.TTrLangs;

public class TEBronzeCompressor extends TEMultiBlockStructureRecipeMapFloatPower
implements IContainerableTile
{
	private static final long MAX_PRESSURE = 102400;
	
	private long pressure;
	private Set<ISteamInputHatch> hatchs = new HashSet(5);
	
	public TEBronzeCompressor()
	{
		super(1, 1, 0, 0);
		inventory = new Inventory(1, TTrLangs.steamCompressor, 64);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt.setLong("pressure", pressure);
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		pressure = nbt.getLong("pressure");
	}
	
	@Override
	protected void updateServer()
	{
		super.updateServer();
	}
	
	@Override
	protected boolean checkStructure()
	{
		hatchs.clear();
		EnumFacing facing = getRotation().getOpposite();
		MutableBlockPos pos1 = new MutableBlockPos();
		for(int i = 0; i <= 2; ++i)
		{
			for(int j = -1; j <= 1; ++j)
			{
				for(int k = -1; k <= 1; ++k)
				{
					if(i == 0 && j == 0 && k == 0)
					{
						continue;
					}
					pos1.setPos(
							pos.getX() + facing.getFrontOffsetX() * i + facing.getFrontOffsetZ() * k,
							pos.getY() + j,
							pos.getZ() + facing.getFrontOffsetZ() * i + facing.getFrontOffsetX() * k);
					if(k == 0 && i == 1 && j == 0)
					{
						if(!worldObj.isAirBlock(pos1))
							return false;
						continue;
					}
					if(i == 1 || j == 0 || k == 1)
					{
						TileEntity tile = worldObj.getTileEntity(pos1);
						if(tile != null)
						{
							if(!(tile instanceof ISteamInputHatch))
								return false;
							hatchs.add((ISteamInputHatch) tile);
							continue;
						}
					}
					IBlockState state = worldObj.getBlockState(pos1);
					if(state != BlockBrick.BRONZE)
						return false;
				}
			}
		}
		return !hatchs.isEmpty();
	}

	@Override
	protected void checkRecipe()
	{
		int input = 0;
		if (!hatchs.isEmpty())
		{
			for(ISteamInputHatch hatch : hatchs)
			{
				input += hatch.inputSteam((int) (pressure / 100 + 1), true);
			}
			hatchs.clear();
		}
		if (pressure < MAX_PRESSURE &&  input * 100 > pressure)
		{
			pressure += (input * 100 - pressure) / 100 + 1;
		}
		else if(pressure > 0 && input * 200 < pressure)
		{
			pressure -= pressure / 100 + 1;
		}
		super.checkRecipe();
	}

	@Override
	protected void onMissingStructure()
	{
		super.onMissingStructure();
		initRecipeOutput();
		pressure = 0;
	}
	
	@Override
	protected TemplateRecipeMap getRecipeMap()
	{
		return TemplateRecipeMap.BRONZE_COMPRESS;
	}

	@Override
	protected boolean useEnergy()
	{
		return pressure >= minPower;
	}
	
	@Override
	protected long getEnergyInput()
	{
		return Long.MAX_VALUE;
	}
	
	@Override
	protected long getPower()
	{
		return pressure;
	}
	
	@Override
	public int getFieldCount()
	{
		return 6;
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
		case 4 : return (int) (pressure & 0xFFFFFFFF);
		case 5 : return (int) ((pressure >> 32) & 0xFFFFFFFF);
		default: return 0;
		}
	}
	
	@Override
	public void setField(int id, int value)
	{
		switch(id)
		{
		case 0 : duration &= 0xFFFFFFFF00000000L; duration |= value; break;
		case 1 : duration &= 0xFFFFFFFFL; duration |= value << 32; break;
		case 2 : maxDuration &= 0xFFFFFFFF00000000L; maxDuration |= value; break;
		case 3 : maxDuration &= 0xFFFFFFFFL; maxDuration |= value << 32; break;
		case 4 : pressure &= 0xFFFFFFFF00000000L; pressure |= value; break;
		case 5 : pressure &= 0xFFFFFFFFL; pressure |= value << 32; break;
		}
	}

	@SideOnly(Side.CLIENT)
	public int getPressureProgress(int scale)
	{
		return (int) ((double) (pressure * scale) / (double) MAX_PRESSURE);
	}
	
	@Override
	public Container getContainer(EnumFacing side, EntityPlayer player)
	{
		return new ContainerSteamCompressor(player, this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiContainer getGui(EnumFacing side, EntityPlayer player)
	{
		return new GuiSteamCompressor(player, this);
	}
}