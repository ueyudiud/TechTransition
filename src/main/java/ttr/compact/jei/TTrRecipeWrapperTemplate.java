/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.compact.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import ttr.api.recipe.TemplateRecipeMap.TemplateRecipe;
import ttr.api.stack.AbstractStack;

/**
 * Created at 2016年12月20日 上午9:44:00
 * @author ueyudiud
 */
public class TTrRecipeWrapperTemplate implements IRecipeWrapper
{
	private static final List<ItemStack> INVALID_LIST = ImmutableList.of(new ItemStack(Blocks.FIRE));
	
	TemplateRecipe recipe;
	
	public TTrRecipeWrapperTemplate(TemplateRecipe recipe)
	{
		this.recipe = recipe;
	}
	
	@Override
	public List getInputs()
	{
		List<List<ItemStack>> list = new ArrayList();
		for(AbstractStack stack : this.recipe.inputsItem)
		{
			List<ItemStack> list1 = stack.display();
			if(list1 == null || list1.isEmpty())
			{
				list1 = INVALID_LIST;
			}
			list.add(list1);
		}
		return list;
	}
	
	@Override
	public List getOutputs()
	{
		List<List<ItemStack>> list = new ArrayList();
		for(AbstractStack stack : this.recipe.outputsItem)
		{
			List<ItemStack> list1 = stack.display();
			if(list1 == null || list1.isEmpty())
			{
				list1 = INVALID_LIST;
			}
			list.add(list1);
		}
		return list;
	}
	
	@Override
	public List<FluidStack> getFluidInputs()
	{
		return Arrays.asList(this.recipe.inputsFluid);
	}
	
	@Override
	public List<FluidStack> getFluidOutputs()
	{
		return Arrays.asList(this.recipe.outputsFluid);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		minecraft.fontRendererObj.drawString("Power : " + this.recipe.minPower * 20 + "W", 20, 80, 0x00040404);
		minecraft.fontRendererObj.drawString("Duration : " + this.recipe.duration / 20 + "sec", 20, 90, 0x00040404);
		minecraft.fontRendererObj.drawString("Energy : " + this.recipe.duration * this.recipe.minPower + "J", 20, 100, 0x00040404);
		switch (this.recipe.name)
		{
		case "ttr.smelting":
		case "ttr.alloy.smelting":
			minecraft.fontRendererObj.drawString("Temperature : " + this.recipe.customValue + "K", 20, 110, 0x00040404);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight)
	{
		
	}
	
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		return ImmutableList.of();
	}
	
	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton)
	{
		return false;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients)
	{
		
	}
}