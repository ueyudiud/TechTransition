/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.api.recipe;

import net.minecraft.item.ItemStack;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;

/**
 * Created at 2016年12月19日 下午11:56:55
 * @author ueyudiud
 */
public interface IOreDictRegister
{
	void registerOreRecipes(ItemStack stack, EnumOrePrefix prefix, EnumMaterial material);
}