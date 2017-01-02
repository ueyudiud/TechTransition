/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.load.orereg;

import net.minecraft.item.ItemStack;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;
import ttr.api.enums.EnumTools;
import ttr.api.recipe.IOreDictRegister;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.stack.AbstractStack;
import ttr.api.stack.BaseStack;
import ttr.api.stack.OreStack;
import ttr.api.util.SubTag;
import ttr.api.util.Util;
import ttr.core.TTrRecipeHandler;

/**
 * Created at 2016年12月20日 上午12:36:36
 * @author ueyudiud
 */
public class OreRegPlate implements IOreDictRegister
{
	@Override
	public void registerOreRecipes(ItemStack stack, EnumOrePrefix prefix, EnumMaterial material)
	{
		if(material.contain(SubTag.METAL) && EnumOrePrefix.ingot.access(material))
		{
			TTrRecipeHandler.addShapedRecipe(Util.copyAmount(stack, 1), "h", "x", "x", 'h', EnumTools.hammer.orename(), 'x', EnumOrePrefix.ingot.getDictName(material));
			TemplateRecipeMap.FORGE_HAMMER.addRecipe(new OreStack(EnumOrePrefix.ingot.getDictName(material), 3), 100 * material.woughtHardness / 1000L, 48L, new BaseStack(stack, 2));
			TemplateRecipeMap.ALLOY_SMELTING.addRecipe(new AbstractStack[]{new OreStack(EnumOrePrefix.ingot.getDictName(material), 2), new OreStack("mouldPlate", 0)}, new AbstractStack[]{new BaseStack(stack, 1)}, 200 * material.heatCapability / 1000L, 64, material.meltingPoint);
			TemplateRecipeMap.BENDER.addRecipe(new OreStack(EnumOrePrefix.ingot.getDictName(material)), 100, 50 * material.woughtHardness / 1000L, new BaseStack(stack, 1));
		}
		if(material.contain(SubTag.POLYMER))
		{
			TemplateRecipeMap.ALLOY_SMELTING.addRecipe(new AbstractStack[]{new OreStack(EnumOrePrefix.dust.getDictName(material), 2), new OreStack("mouldPlate", 0)}, new AbstractStack[]{new BaseStack(stack, 1)}, 200 * material.heatCapability / 1000L, 64, material.meltingPoint);
		}
	}
}