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
 * Created at 2016年12月21日 上午10:40:07
 * @author ueyudiud
 */
public class OreRegDustOreTiny implements IOreDictRegister
{
	@Override
	public void registerOreRecipes(ItemStack stack, EnumOrePrefix prefix, EnumMaterial material)
	{
		String dustOre = EnumOrePrefix.dustOre.getDictName(material);
		TTrRecipeHandler.addShapedRecipe(Util.copyAmount(stack, 9), " x", 'x', dustOre);
	}
}