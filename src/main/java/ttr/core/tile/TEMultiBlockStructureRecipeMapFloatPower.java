package ttr.core.tile;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class TEMultiBlockStructureRecipeMapFloatPower extends TEMachineRecipeMapFloatPower
{
	public static final int StructureFine = 0x12;
	@SideOnly(Side.CLIENT)
	private int clientStructureBuf;
	
	public TEMultiBlockStructureRecipeMapFloatPower(int itemInputSize, int itemOutputSize, int fluidInputSize,
			int fluidOutputSize)
	{
		super(itemInputSize, itemOutputSize, fluidInputSize, fluidOutputSize);
	}

	@Override
	protected void updateClient()
	{
		super.updateClient();
		if(((++clientStructureBuf) & 0xF) == 0)
		{
			if(checkStructure())
			{
				enable(StructureFine);
			}
			else
			{
				disable(StructureFine);
			}
			clientStructureBuf = 0;
		}
	}
	
	@Override
	protected void updateServer()
	{
		lastCurrentState = currentState;
		lastEnergyInput = getEnergyInput();
		if (canUseMachine())
		{
			if(is(MachineEnabled))
			{
				checkRecipe();
				onWorking();
			}
		}
		else
		{
			onMissingStructure();
		}
		super.updateServer();
		if(currentState != lastCurrentState)
		{
			syncToNearby();
		}
	}

	@Override
	protected boolean canUseMachine()
	{
		return checkStructure();
	}

	protected void onMissingStructure()
	{

	}

	@SideOnly(Side.CLIENT)
	protected boolean isStructureFine()
	{
		return is(StructureFine);
	}

	protected abstract boolean checkStructure();
}