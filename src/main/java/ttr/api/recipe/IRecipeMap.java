package ttr.api.recipe;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import ttr.api.recipe.IRecipeMap.IRecipe;

public interface IRecipeMap<R extends IRecipe>
{
	public static interface IRecipe
	{
	}
	
	Map<String, IRecipeMap> RECIPEMAPS = new HashMap();
	
	void addRecipe(R recipe);
	
	Collection<R> getRecipes();
	
	void reloadRecipes();
	
	R findRecipe(World world, BlockPos pos, long power, FluidStack[] fluidInputs, ItemStack...itemInputs);
}