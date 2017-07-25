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
		this.inventory = new Inventory(1, TTrLangs.steamCompressor, 64);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt.setLong("pressure", this.pressure);
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.pressure = nbt.getLong("pressure");
	}
	
	@Override
	protected void updateServer()
	{
		super.updateServer();
	}
	
	@Override
	protected boolean checkStructure()
	{
		this.hatchs.clear();
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
							this.pos.getX() + facing.getFrontOffsetX() * i + facing.getFrontOffsetZ() * k,
							this.pos.getY() + j,
							this.pos.getZ() + facing.getFrontOffsetZ() * i + facing.getFrontOffsetX() * k);
					if(k == 0 && i == 1 && j == 0)
					{
						if(!this.worldObj.isAirBlock(pos1))
							return false;
						continue;
					}
					if(i == 1 || j == 0 || k == 1)
					{
						TileEntity tile = this.worldObj.getTileEntity(pos1);
						if(tile != null)
						{
							if(!(tile instanceof ISteamInputHatch))
								return false;
							this.hatchs.add((ISteamInputHatch) tile);
							continue;
						}
					}
					IBlockState state = this.worldObj.getBlockState(pos1);
					if(state != BlockBrick.BRONZE)
						return false;
				}
			}
		}
		return !this.hatchs.isEmpty();
	}
	
	@Override
	protected void checkRecipe()
	{
		int input = 0;
		if (!this.hatchs.isEmpty())
		{
			for(ISteamInputHatch hatch : this.hatchs)
			{
				input += hatch.inputSteam((int) (this.pressure / 100 + 1), true);
			}
			this.hatchs.clear();
		}
		if (this.pressure < MAX_PRESSURE && input * 100 > this.pressure)
		{
			this.pressure += (input * 100 - this.pressure) / 100 + 1;
		}
		else if(this.pressure > 0 && input * 200 < this.pressure)
		{
			this.pressure -= (this.pressure - input * 200) / 100 + 1;
		}
		super.checkRecipe();
	}
	
	@Override
	protected void onMissingStructure()
	{
		super.onMissingStructure();
		initRecipeOutput();
		this.pressure = 0;
	}
	
	@Override
	protected TemplateRecipeMap getRecipeMap()
	{
		return TemplateRecipeMap.BRONZE_COMPRESS;
	}
	
	@Override
	protected boolean useEnergy()
	{
		return this.pressure >= this.minPower * 100;
	}
	
	@Override
	protected long getEnergyInput()
	{
		return Long.MAX_VALUE;
	}
	
	@Override
	protected long getPower()
	{
		return this.pressure / 100;
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
		case 0 : return (int) (this.duration & 0xFFFFFFFF);
		case 1 : return (int) ((this.duration >> 32) & 0xFFFFFFFF);
		case 2 : return (int) (this.maxDuration & 0xFFFFFFFF);
		case 3 : return (int) ((this.maxDuration >> 32) & 0xFFFFFFFF);
		case 4 : return (int) (this.pressure & 0xFFFFFFFF);
		case 5 : return (int) ((this.pressure >> 32) & 0xFFFFFFFF);
		default: return 0;
		}
	}
	
	@Override
	public void setField(int id, int value)
	{
		switch(id)
		{
		case 0 : this.duration &= 0xFFFFFFFF00000000L; this.duration |= value; break;
		case 1 : this.duration &= 0xFFFFFFFFL; this.duration |= value << 32; break;
		case 2 : this.maxDuration &= 0xFFFFFFFF00000000L; this.maxDuration |= value; break;
		case 3 : this.maxDuration &= 0xFFFFFFFFL; this.maxDuration |= value << 32; break;
		case 4 : this.pressure &= 0xFFFFFFFF00000000L; this.pressure |= value; break;
		case 5 : this.pressure &= 0xFFFFFFFFL; this.pressure |= value << 32; break;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getPressureProgress(int scale)
	{
		return (int) ((double) (this.pressure * scale) / (double) MAX_PRESSURE);
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