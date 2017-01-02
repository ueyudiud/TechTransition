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
import ttr.core.TTrRecipeHandler;

/**
 * Created at 2016年12月20日 上午12:35:12
 * @author ueyudiud
 */
public class OreRegDustTiny implements IOreDictRegister
{
	@Override
	public void registerOreRecipes(ItemStack stack, EnumOrePrefix prefix, EnumMaterial material)
	{
		String dust = EnumOrePrefix.dust.getDictName(material);
		String dustPowder = EnumOrePrefix.dustPowder.getDictName(material);
		TTrRecipeHandler.addShapedRecipe(Util.copyAmount(stack, 9), " x", 'x', dust);
		TTrRecipeHandler.addShapelessRecipe(Util.copyAmount(stack, 1), dustPowder, dustPowder, dustPowder, dustPowder);
		if(EnumOrePrefix.crushed.access(material) && material.contain(SubTag.NORMAL_ORE_PROCESSING))
		{
			TemplateRecipeMap.SMELTING.addRecipe(new OreStack(EnumOrePrefix.crushed.getDictName(material)), 300 * material.heatCapability / 1000L, 20, new BaseStack(Util.copyAmount(stack, 10)));
			TemplateRecipeMap.SMELTING.addRecipe(new OreStack(EnumOrePrefix.crushedPurified.getDictName(material)), 300 * material.heatCapability / 1000L, 20, new BaseStack(Util.copyAmount(stack, 10)));
			TemplateRecipeMap.SMELTING.addRecipe(new OreStack(EnumOrePrefix.crushedCentrifuged.getDictName(material)), 300 * material.heatCapability / 1000L, 20, new BaseStack(Util.copyAmount(stack, 10)));
		}
		if(EnumOrePrefix.nugget.access(material))
		{
			TTrRecipeAdder.addGrindingRecipe(new OreStack(EnumOrePrefix.nugget.getDictName(material)), new BaseStack(Util.copyAmount(stack, 1)), 12, 8 * material.woughtHardness / 1000L);
		}
		if(EnumOrePrefix.bolt.access(material))
		{
			TTrRecipeAdder.addGrindingRecipe(new OreStack(EnumOrePrefix.bolt.getDictName(material)), new BaseStack(stack, 1), 12, 8 * material.woughtHardness / 1000L);
		}
		if(EnumOrePrefix.screw.access(material))
		{
			TTrRecipeAdder.addGrindingRecipe(new OreStack(EnumOrePrefix.screw.getDictName(material)), new BaseStack(stack, 1), 12, 8 * material.woughtHardness / 1000L);
		}
	}
}