/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.compact.jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import ttr.api.recipe.IRecipeMap;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.recipe.TemplateRecipeMap.TemplateRecipe;

/**
 * Created at 2016年12月20日 上午10:18:55
 * @author ueyudiud
 */
public class TTrRecipeHandlerTemplate implements IRecipeHandler<TemplateRecipe>
{
	public TTrRecipeHandlerTemplate()
	{
	}
	
	@Override
	public Class<TemplateRecipe> getRecipeClass()
	{
		return TemplateRecipe.class;
	}
	
	@Override
	public String getRecipeCategoryUid()
	{
		return "ttr.template";
	}
	
	@Override
	public String getRecipeCategoryUid(TemplateRecipe recipe)
	{
		return recipe.name;
	}
	
	@Override
	public IRecipeWrapper getRecipeWrapper(TemplateRecipe recipe)
	{
		return new TTrRecipeWrapperTemplate(recipe);
	}
	
	@Override
	public boolean isRecipeValid(TemplateRecipe recipe)
	{
		IRecipeMap map = IRecipeMap.RECIPEMAPS.get(recipe.name);
		return map instanceof TemplateRecipeMap && ((TemplateRecipeMap) map).isRecipeValid(recipe);
	}
}