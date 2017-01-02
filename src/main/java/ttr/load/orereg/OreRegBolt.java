/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.load.orereg;

import net.minecraft.item.ItemStack;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;
import ttr.api.enums.EnumTools;
import ttr.api.recipe.IOreDictRegister;
import ttr.api.recipe.TTrRecipeAdder;
import ttr.api.stack.BaseStack;
import ttr.api.stack.OreStack;
import ttr.api.util.SubTag;
import ttr.api.util.Util;
import ttr.core.TTrRecipeHandler;

/**
 * Created at 2016年12月21日 下午8:23:22
 * @author ueyudiud
 */
public class OreRegBolt implements IOreDictRegister
{
	@Override
	public void registerOreRecipes(ItemStack stack, EnumOrePrefix prefix, EnumMaterial material)
	{
		if(material.contain(SubTag.METAL))
		{
			TTrRecipeHandler.addShapedRecipe(Util.copyAmount(stack, 2), " s", "i ", 's', EnumTools.saw.orename(), 'i', EnumOrePrefix.stick.getDictName(material));
			TTrRecipeAdder.addCuttingRecipe(new OreStack(EnumOrePrefix.stick.getDictName(material)), 120 * material.woughtHardness / 1000L, 40, 1, new BaseStack(stack, 4), new BaseStack(stack, 2));
		}
	}
}