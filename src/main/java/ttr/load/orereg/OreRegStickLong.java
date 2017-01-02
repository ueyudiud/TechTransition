/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.load.orereg;

import net.minecraft.item.ItemStack;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;
import ttr.api.enums.EnumTools;
import ttr.api.recipe.IOreDictRegister;
import ttr.api.util.SubTag;
import ttr.api.util.Util;
import ttr.core.TTrRecipeHandler;

/**
 * Created at 2016年12月20日 上午9:04:36
 * @author ueyudiud
 */
public class OreRegStickLong implements IOreDictRegister
{
	@Override
	public void registerOreRecipes(ItemStack stack, EnumOrePrefix prefix, EnumMaterial material)
	{
		if(material.contain(SubTag.METAL))
		{
			TTrRecipeHandler.addShapedRecipe(Util.copyAmount(stack, 1), "xhx", 'x', EnumOrePrefix.stick.getDictName(material), 'h', EnumTools.hammer.orename());
		}
	}
}