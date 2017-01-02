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
import ttr.core.TTrMaterialHandler;
import ttr.core.TTrRecipeHandler;

/**
 * Created at 2016年12月20日 上午9:01:06
 * @author ueyudiud
 */
public class OreRegStick implements IOreDictRegister
{
	@Override
	public void registerOreRecipes(ItemStack stack, EnumOrePrefix prefix, EnumMaterial material)
	{
		if(material.contain(SubTag.METAL) && EnumOrePrefix.ingot.access(material))
		{
			TTrRecipeHandler.addShapedRecipe(Util.copyAmount(stack, 1), " f", "x ", 'f', EnumTools.file.orename(), 'x', EnumOrePrefix.ingot.getDictName(material));
			TTrRecipeAdder.addLatheRecipe(new OreStack(EnumOrePrefix.ingot.getDictName(material)), 480, 24 * material.woughtHardness / 1000L, 2, new BaseStack(stack, 1), new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dustSmall, material, 2)));
		}
	}
}