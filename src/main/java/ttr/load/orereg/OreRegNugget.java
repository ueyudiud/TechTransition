/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.load.orereg;

import net.minecraft.item.ItemStack;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;
import ttr.api.recipe.IOreDictRegister;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.stack.BaseStack;
import ttr.api.stack.OreStack;
import ttr.api.util.SubTag;
import ttr.api.util.Util;

/**
 * Created at 2016年12月20日 上午9:07:02
 * @author ueyudiud
 */
public class OreRegNugget implements IOreDictRegister
{
	@Override
	public void registerOreRecipes(ItemStack stack, EnumOrePrefix prefix, EnumMaterial material)
	{
		if(material == EnumMaterial.WoughtIron)
		{
			TemplateRecipeMap.SMELTING.addRecipe(new OreStack(prefix.getDictName(EnumMaterial.Iron)), 200, 20, new BaseStack(Util.copyAmount(stack, 1)));
		}
		if(material.contain(SubTag.METAL))
		{
			if(!material.contain(SubTag.BLAST_REQUIRED))
			{
				TemplateRecipeMap.SMELTING.addRecipe(new OreStack(EnumOrePrefix.dustTiny.getDictName(material)), 23 * material.heatCapability / 1000L, 20, new BaseStack(Util.copyAmount(stack, 1)));
			}
		}
	}
}