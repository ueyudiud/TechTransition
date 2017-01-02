/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.load.orereg;

import net.minecraft.item.ItemStack;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;
import ttr.api.recipe.IOreDictRegister;
import ttr.api.util.Util;
import ttr.core.TTrRecipeHandler;

/**
 * Created at 2016年12月20日 下午4:53:55
 * @author ueyudiud
 */
public class OreRegDustPowder implements IOreDictRegister
{
	@Override
	public void registerOreRecipes(ItemStack stack, EnumOrePrefix prefix, EnumMaterial material)
	{
		TTrRecipeHandler.addShapelessRecipe(Util.copyAmount(stack, 4), EnumOrePrefix.dustTiny.getDictName(material));
		TTrRecipeHandler.addShapelessRecipe(Util.copyAmount(stack, 9), EnumOrePrefix.dustSmall.getDictName(material));
	}
}