package ttr.load;

import ttr.load.recipe.TTrMachineRecipes;
import ttr.load.recipe.TTrMaterialsRecipes;
import ttr.load.recipe.TTrRecipeRemove;
import ttr.load.recipe.TTrVanillaRecipes;

public class TTrRecipes
{
	public static void preinit()
	{
		TTrRecipeRemove.init();
	}
	
	public static void init()
	{
		TTrVanillaRecipes.init();
		TTrMaterialsRecipes.init();
		TTrMachineRecipes.init();
	}
}