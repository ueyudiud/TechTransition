/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.tile.machine.steam;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import ttr.api.inventory.Inventory;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.tile.IContainerableTile;
import ttr.core.block.BlockBrick;
import ttr.core.gui.machine.steam.ContainerBronzeBlastFurnace;
import ttr.core.gui.machine.steam.GuiBronzeBlastFurnace;
import ttr.core.tile.TEMultiBlockStructureRecipeMapFloatPower;
import ttr.load.TTrLangs;

/**
 * Created at 2016年12月20日 下午1:34:04
 * @author ueyudiud
 */
public class TEBronzeBlastFurnace extends TEMultiBlockStructureRecipeMapFloatPower implements IContainerableTile
{
	private static final IBlockState LAVA = Blocks.LAVA.getDefaultState().withProperty(BlockLiquid.LEVEL, 1);
	
	public TEBronzeBlastFurnace()
	{
		super(2, 2, 0, 0);
		this.inventory = new Inventory(0, TTrLangs.bronzeBlastFurnace, 64);
	}
	
	@Override
	protected boolean checkStructure()
	{
		EnumFacing facing = getRotation().getOpposite();
		MutableBlockPos pos1 = new MutableBlockPos();
		for(int i = 0; i <= 2; ++i)
		{
			for(int j = -1; j <= 2; ++j)
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
					if(k == 0 && i == 1 && j != -1)
					{
						if(!this.worldObj.isAirBlock(pos1) && this.worldObj.getBlockState(pos1).getMaterial() != Material.LAVA)
							return false;
						continue;
					}
					IBlockState state = this.worldObj.getBlockState(pos1);
					if(state != BlockBrick.BRONZE)
						return false;
				}
			}
		}
		return true;
	}
	
	@Override
	protected void onMissingStructure()
	{
		super.onMissingStructure();
		initRecipeOutput();
	}
	
	@Override
	protected TemplateRecipeMap getRecipeMap()
	{
		return TemplateRecipeMap.BRONZE_BLAST;
	}
	
	@Override
	protected boolean useEnergy()
	{
		return true;
	}
	
	@Override
	protected void onWorking()
	{
		if(useEnergy())
		{
			if(is(Working))
			{
				BlockPos pos = this.pos.offset(this.facing.getOpposite());
				if(this.worldObj.isAirBlock(pos))
				{
					this.worldObj.setBlockState(pos, LAVA);
				}
				if(this.worldObj.isAirBlock(pos.up()))
				{
					this.worldObj.setBlockState(pos.offset(EnumFacing.UP), LAVA);
				}
				for(int i = 0; i < 4; ++i)
				{
					this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + this.random.nextDouble(), pos.getY() + this.random.nextDouble(), pos.getZ() + this.random.nextDouble(), 0.0, 0.2, 0.0);
				}
				this.duration += getPower();
			}
		}
	}
	
	@Override
	protected long getEnergyInput()
	{
		return 1;
	}
	
	@Override
	protected long getPower()
	{
		return 1;
	}
	
	@Override
	public Container getContainer(EnumFacing side, EntityPlayer player)
	{
		return new ContainerBronzeBlastFurnace(player, this);
	}
	
	@Override
	public GuiContainer getGui(EnumFacing side, EntityPlayer player)
	{
		return new GuiBronzeBlastFurnace(player, this);
	}
}