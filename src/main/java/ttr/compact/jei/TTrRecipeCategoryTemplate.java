/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.compact.jei;

import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import ttr.api.recipe.TemplateRecipeMap;

/**
 * Created at 2016年12月20日 上午9:39:41
 * @author ueyudiud
 */
public class TTrRecipeCategoryTemplate extends BlankRecipeCategory<TTrRecipeWrapperTemplate>
{
	IDrawable background;
	TemplateRecipeMap map;
	int[][] inputSlots;
	int[][] outputSlots;
	int[][] inputTanks;
	int[][] outputTanks;
	
	public TTrRecipeCategoryTemplate(IGuiHelper helper, TemplateRecipeMap map, ResourceLocation guiLocation, int[][] inputSlots, int[][] outputSlots, int[][] inputTanks, int[][] outputTanks)
	{
		this.background = helper.createDrawable(guiLocation, 4, 4, 168, 135);
		this.map = map;
		this.inputSlots = inputSlots;
		this.outputSlots = outputSlots;
		this.inputTanks = inputTanks;
		this.outputTanks = outputTanks;
	}
	
	@Override
	public String getUid()
	{
		return this.map.name;
	}
	
	@Override
	public String getTitle()
	{
		return this.map.name;
	}
	
	@Override
	public IDrawable getBackground()
	{
		return this.background;
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, TTrRecipeWrapperTemplate recipeWrapper)
	{
		int idx = 0;
		IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
		for (int i = 0; i < recipeWrapper.recipe.inputsItem.length; ++i)
		{
			itemStackGroup.init(idx, true, this.inputSlots[i][0] - 5, this.inputSlots[i][1] - 5);
			itemStackGroup.set(idx++, recipeWrapper.recipe.inputsItem[i].instance());
		}
		for (int i = 0; i < recipeWrapper.recipe.outputsItem.length; ++i)
		{
			itemStackGroup.init(idx, false, this.outputSlots[i][0] - 5, this.outputSlots[i][1] - 5);
			itemStackGroup.set(idx++, recipeWrapper.recipe.outputsItem[i].instance());
		}
		itemStackGroup.addTooltipCallback((int slotIndex, boolean input, ItemStack stack, List<String> tooltip) ->
		{
			if(slotIndex >= recipeWrapper.recipe.inputsItem.length && recipeWrapper.recipe.chancesOutputItem != null)
			{
				addChanceTip(recipeWrapper.recipe.chancesOutputItem[slotIndex - recipeWrapper.recipe.inputsItem.length], tooltip);
			}
		});
		idx = 0;
		IGuiFluidStackGroup fluidStackGroup = recipeLayout.getFluidStacks();
		for (int i = 0; i < recipeWrapper.recipe.inputsFluid.length; ++i)
		{
			fluidStackGroup.init(idx, true, this.inputTanks[i][0] - 4, this.inputTanks[i][1] - 4, 16, 16, recipeWrapper.recipe.inputsFluid[i].amount, false, null);
			fluidStackGroup.set(idx++, recipeWrapper.recipe.inputsFluid[i]);
		}
		for (int i = 0; i < recipeWrapper.recipe.outputsFluid.length; ++i)
		{
			fluidStackGroup.init(idx, false, this.outputTanks[i][0] - 4, this.outputTanks[i][1] - 4, 16, 16, recipeWrapper.recipe.outputsFluid[i].amount, false, null);
			fluidStackGroup.set(idx++, recipeWrapper.recipe.outputsFluid[i]);
		}
	}
	
	private void addChanceTip(int[] chance, List<String> tooltip)
	{
		if(chance == null) return;
		if(chance.length == 1)
		{
			if(chance[0] != 10000)
			{
				tooltip.add("Chance :" + (chance[0] / 100) + "." + (chance[0] % 100) + "%");
			}
		}
		else for(int i = 0; i < chance.length; ++i)
		{
			tooltip.add("Chance " + i + ":" + (chance[i] / 100) + "." + (chance[i] % 100) + "%");
		}
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, TTrRecipeWrapperTemplate recipeWrapper, IIngredients ingredients)
	{
		setRecipe(recipeLayout, recipeWrapper);
	}
}