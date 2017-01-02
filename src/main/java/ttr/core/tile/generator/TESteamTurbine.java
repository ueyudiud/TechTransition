/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.tile.generator;

import java.util.HashSet;
import java.util.Set;

import ic2.api.energy.tile.IKineticSource;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import ttr.api.tile.ISteamInputHatch;
import ttr.api.util.EnergyTrans;
import ttr.core.block.BlockBrick;
import ttr.core.tile.TEMachineBase;

/**
 * Created at 2016年12月23日 下午6:31:50
 * @author ueyudiud
 */
public class TESteamTurbine extends TEMachineBase implements IKineticSource
{
	private static final int MAX_CAPACITY = 65536;
	
	private int energyInput;
	private Set<ISteamInputHatch> hatchs = new HashSet(5);
	
	@Override
	protected void updateServer()
	{
		super.updateServer();
		if(checkStructure())
		{
			if(this.energyInput > 0)
			{
				--this.energyInput;
			}
			for(ISteamInputHatch hatch : this.hatchs)
			{
				this.energyInput += hatch.inputSteam(hatch.getSteamAmount(), true) * EnergyTrans.STEAM_TO_J;
			}
			if(this.energyInput > MAX_CAPACITY)
			{
				this.energyInput = MAX_CAPACITY;
			}
		}
		else
		{
			this.energyInput = 0;
		}
	}
	
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
					if(state != BlockBrick.BASIC)
					{
						return false;
					}
				}
			}
		}
		return !this.hatchs.isEmpty();
	}
	
	@Override
	public int maxrequestkineticenergyTick(EnumFacing directionFrom)
	{
		return directionFrom == this.facing ? this.energyInput : 0;
	}
	
	@Override
	public int requestkineticenergy(EnumFacing directionFrom, int requestkineticenergy)
	{
		if(directionFrom != this.facing) return 0;
		if(requestkineticenergy > this.energyInput)
		{
			requestkineticenergy = this.energyInput;
		}
		this.energyInput -= requestkineticenergy;
		return requestkineticenergy;
	}
}