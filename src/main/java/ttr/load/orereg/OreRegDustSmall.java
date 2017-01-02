/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.load.orereg;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;
import ttr.api.recipe.IOreDictRegister;
import ttr.api.recipe.TTrRecipeAdder;
import ttr.api.stack.BaseStack;
import ttr.api.stack.OreStack;
import ttr.api.util.MaterialStack;
import ttr.api.util.SubTag;
import ttr.api.util.Util;
import ttr.core.TTrRecipeHandler;

/**
 * Created at 2016年12月20日 上午12:34:33
 * @author ueyudiud
 */
public class OreRegDustSmall implements IOreDictRegister
{
	@Override
	public void registerOreRecipes(ItemStack stack, EnumOrePrefix prefix, EnumMaterial material)
	{
		String dust = EnumOrePrefix.dust.getDictName(material);
		String dustPowder = EnumOrePrefix.dustPowder.getDictName(material);
		TTrRecipeHandler.addShapedRecipe(Util.copyAmount(stack, 4), "x ", 'x', dust);
		TTrRecipeHandler.addShapelessRecipe(Util.copyAmount(stack, 1), dustPowder, dustPowder, dustPowder, dustPowder, dustPowder, dustPowder, dustPowder, dustPowder, dustPowder);
		if(EnumOrePrefix.stick.access(material))
		{
			TTrRecipeAdder.addGrindingRecipe(new OreStack(EnumOrePrefix.stick.getDictName(material)), new BaseStack(Util.copyAmount(stack, 2)), 50, 8 * material.woughtHardness / 1000L);
		}
		if(EnumOrePrefix.chunk.access(material))
		{
			TTrRecipeAdder.addGrindingRecipe(new OreStack(EnumOrePrefix.chunk.getDictName(material)), new BaseStack(Util.copyAmount(stack, 1)), 25, 8 * material.woughtHardness / 1000L);
		}
		if(EnumOrePrefix.gemChip.access(material))
		{
			TTrRecipeAdder.addGrindingRecipe(new OreStack(EnumOrePrefix.gemChip.getDictName(material)), new BaseStack(Util.copyAmount(stack, 1)), 25, 8 * material.woughtHardness / 1000L);
		}
		if(material.contain(SubTag.ALLOY_SIMPLE))
		{
			if(material.contain != null)
			{
				int size = 0;
				List<Object> list1 = new ArrayList();
				for (MaterialStack stack1 : material.contain)
				{
					size += stack1.amount;
					for(int i = 0; i < stack1.amount; ++i)
					{
						list1.add(EnumOrePrefix.dust.getDictName(stack1.material));
					}
				}
				if(list1.size() <= 9)
				{
					if(size % 4 != 0)
					{
						TTrRecipeHandler.addShapelessRecipe(Util.copyAmount(stack, size * 3), list1.toArray());
					}
				}
			}
		}
	}
}