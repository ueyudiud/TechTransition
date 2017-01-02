/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.load.recipe;

import java.util.Iterator;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import ttr.api.recipe.FakeCraftingInventory;
import ttr.api.recipe.IRecipeMap;
import ttr.api.stack.AbstractStack;
import ttr.core.TTrRecipeHandler;

/**
 * Created at 2016年12月20日 上午12:31:31
 * @author ueyudiud
 */
public class TTrRecipeModify
{
	public static void completeload()
	{
		TTrRecipeHandler.valiadateRemoveRecipeInventory();
		Iterator<IRecipe> iterator = CraftingManager.getInstance().getRecipeList().iterator();
		while(iterator.hasNext())
		{
			IRecipe recipe = iterator.next();
			if(matchRecipeMarkedRemoved(recipe))
			{
				iterator.remove();
				continue;
			}
		}
		CraftingManager.getInstance().getRecipeList().addAll(TTrRecipeHandler.CRAFTING_ADD_TARGETS);
		TTrRecipeHandler.CRAFTING_INPUTS_REMOVE_TARGETS = null;
		TTrRecipeHandler.CRAFTING_OUTPUT_REMOVE_TARGETS = null;
		TTrRecipeHandler.CRAFTING_ADD_TARGETS = null;
		
		for(IRecipeMap map : IRecipeMap.RECIPEMAPS.values())
		{
			map.reloadRecipes();
		}
	}
	
	private static boolean matchRecipeMarkedRemoved(IRecipe recipe)
	{
		if(recipe.getRecipeOutput() != null)
		{
			ItemStack output = recipe.getRecipeOutput();
			for(AbstractStack stack : TTrRecipeHandler.CRAFTING_OUTPUT_REMOVE_TARGETS)
			{
				if(stack.similar(output)) return true;
			}
		}
		for(FakeCraftingInventory inventory : TTrRecipeHandler.CRAFTING_INPUTS_REMOVE_TARGETS)
		{
			try
			{
				if(recipe.matches(inventory, null)) return true;
			}
			catch(Exception exception)
			{
				;
			}
		}
		return false;
	}
}