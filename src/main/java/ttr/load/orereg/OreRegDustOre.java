/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.load.orereg;

import net.minecraft.item.ItemStack;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;
import ttr.api.recipe.IOreDictRegister;
import ttr.api.recipe.TTrRecipeAdder;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.stack.BaseStack;
import ttr.api.stack.OreStack;
import ttr.api.util.SubTag;
import ttr.api.util.Util;
import ttr.core.TTrMaterialHandler;
import ttr.core.TTrRecipeHandler;

/**
 * Created at 2016年12月21日 上午10:39:46
 * @author ueyudiud
 */
public class OreRegDustOre implements IOreDictRegister
{
	@Override
	public void registerOreRecipes(ItemStack stack, EnumOrePrefix prefix, EnumMaterial material)
	{
		String dustOreTiny = EnumOrePrefix.dustOreTiny.getDictName(material);
		TTrRecipeHandler.addShapelessRecipe(Util.copyAmount(stack, 1), dustOreTiny, dustOreTiny, dustOreTiny, dustOreTiny, dustOreTiny, dustOreTiny, dustOreTiny, dustOreTiny, dustOreTiny);
		if(material.contain(SubTag.NORMAL_ORE_PROCESSING))
		{
			TTrRecipeHandler.addHammerCrushable(Util.copyAmount(stack, 1), EnumOrePrefix.crushed.getDictName(material));
			TTrRecipeHandler.addHammerCrushable(Util.copyAmount(stack, 1), EnumOrePrefix.crushedPurified.getDictName(material));
			TTrRecipeHandler.addHammerCrushable(Util.copyAmount(stack, 1), EnumOrePrefix.crushedCentrifuged.getDictName(material));
			TemplateRecipeMap.FORGE_HAMMER.addRecipe(new OreStack(EnumOrePrefix.crushed.getDictName(material)), 80, 60, new BaseStack(Util.copyAmount(stack, 1)));
			TemplateRecipeMap.FORGE_HAMMER.addRecipe(new OreStack(EnumOrePrefix.crushedPurified.getDictName(material)), 80, 60, new BaseStack(Util.copyAmount(stack, 1)));
			TemplateRecipeMap.FORGE_HAMMER.addRecipe(new OreStack(EnumOrePrefix.crushedCentrifuged.getDictName(material)), 80, 60, new BaseStack(Util.copyAmount(stack, 1)));
			TTrRecipeAdder.addGrindingRecipe(new OreStack(EnumOrePrefix.crushed.getDictName(material)), new BaseStack(Util.copyAmount(stack, 1)), new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dustOreTiny, material.byproduct2, 1)), new int[]{1000, 250}, 200, 10);
			TTrRecipeAdder.addGrindingRecipe(new OreStack(EnumOrePrefix.crushedPurified.getDictName(material)), new BaseStack(Util.copyAmount(stack, 1)), new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dustOreTiny, material.byproduct3, 1)), new int[]{1000, 250}, 200, 10);
			TTrRecipeAdder.addGrindingRecipe(new OreStack(EnumOrePrefix.crushedCentrifuged.getDictName(material)), new BaseStack(Util.copyAmount(stack, 1)), new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dustOreTiny, material.byproduct4, 1)), new int[]{1000, 250}, 200, 10);
		}
	}
}