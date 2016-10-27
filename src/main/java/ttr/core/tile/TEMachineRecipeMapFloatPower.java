package ttr.core.tile;

import ttr.api.recipe.TemplateRecipeMap.TemplateRecipe;

public abstract class TEMachineRecipeMapFloatPower extends TEMachineRecipeMap
{
	public TEMachineRecipeMapFloatPower(int itemInputSize, int itemOutputSize, int fluidInputSize, int fluidOutputSize)
	{
		super(itemInputSize, itemOutputSize, fluidInputSize, fluidOutputSize);
	}
	
	@Override
	protected void initRecipeInput(TemplateRecipe recipe)
	{
		super.initRecipeInput(recipe);
		maxDuration = recipe.minPower * recipe.duration;
		minPower = recipe.minPower;
	}
	
	@Override
	protected void initRecipeOutput()
	{
		super.initRecipeOutput();
		maxDuration = 0;
		minPower = 0;
	}
	
	@Override
	protected void onWorking()
	{
		if(useEnergy())
		{
			if(is(Working))
			{
				duration += getPower();
			}
		}
	}
}