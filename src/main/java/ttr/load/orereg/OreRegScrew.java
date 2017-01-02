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
 * Created at 2016年12月21日 下午8:27:31
 * @author ueyudiud
 */
public class OreRegScrew implements IOreDictRegister
{
	@Override
	public void registerOreRecipes(ItemStack stack, EnumOrePrefix prefix, EnumMaterial material)
	{
		if(material.contain(SubTag.METAL))
		{
			TTrRecipeHandler.addShapedRecipe(Util.copyAmount(stack, 1), " x", "xf", 'x', EnumOrePrefix.bolt.getDictName(material), 'f', EnumTools.file.orename());
			TTrRecipeAdder.addLatheRecipe(new OreStack(EnumOrePrefix.bolt.getDictName(material)), 80, 40 * material.woughtHardness / 1000L, 1, new BaseStack(stack, 1));
		}
	}
}