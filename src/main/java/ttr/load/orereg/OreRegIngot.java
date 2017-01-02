/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.load.orereg;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;
import ttr.api.recipe.IOreDictRegister;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.stack.AbstractStack;
import ttr.api.stack.BaseStack;
import ttr.api.stack.OreStack;
import ttr.api.stack.SourceStack;
import ttr.api.util.MaterialStack;
import ttr.api.util.SubTag;
import ttr.api.util.Util;
import ttr.core.TTrMaterialHandler;
import ttr.core.TTrRecipeHandler;

/**
 * Created at 2016年12月20日 上午12:35:34
 * @author ueyudiud
 */
public class OreRegIngot implements IOreDictRegister
{
	@Override
	public void registerOreRecipes(ItemStack stack, EnumOrePrefix prefix, EnumMaterial material)
	{
		if(material.contain(SubTag.METAL))
		{
			String nugget = EnumOrePrefix.nugget.getDictName(material);
			TTrRecipeHandler.addShapelessRecipe(Util.copyAmount(stack, 1), nugget, nugget, nugget, nugget, nugget, nugget, nugget, nugget, nugget);
			FurnaceRecipes.instance().addSmeltingRecipe(TTrMaterialHandler.getItemStack(EnumOrePrefix.dust, material, 1), Util.copyAmount(stack, 1), 0.0F);
			if(!material.contain(SubTag.BLAST_REQUIRED))
			{
				TemplateRecipeMap.SMELTING.addRecipe(new OreStack(EnumOrePrefix.dust.getDictName(material)), 200 * material.heatCapability / 1000L, 20, new BaseStack(Util.copyAmount(stack, 1)), material.meltingPoint);
			}
			if(EnumOrePrefix.gem.access(material))
			{
				TemplateRecipeMap.COMPRESS.addRecipe(new OreStack(EnumOrePrefix.gem.getDictName(material)), 200 * material.woughtHardness / 1000L, 18, new BaseStack(Util.copyAmount(stack, 1)));
				TemplateRecipeMap.COMPRESS.addRecipe(new OreStack(EnumOrePrefix.gemChip.getDictName(material), 4), 200 * material.woughtHardness / 1000L, 18, new BaseStack(Util.copyAmount(stack, 1)));
				TemplateRecipeMap.BRONZE_COMPRESS.addRecipe(new OreStack(EnumOrePrefix.gem.getDictName(material)), 100, 36 * material.woughtHardness / 1000L, new BaseStack(Util.copyAmount(stack, 1)));
				TemplateRecipeMap.BRONZE_COMPRESS.addRecipe(new OreStack(EnumOrePrefix.gemChip.getDictName(material), 4), 100, 36 * material.woughtHardness / 1000L, new BaseStack(Util.copyAmount(stack, 1)));
			}
		}
		else if(material.contain(SubTag.POLYMER))
		{
			TemplateRecipeMap.SMELTING.addRecipe(new OreStack(EnumOrePrefix.dust.getDictName(material), 2), 200, 20, new BaseStack(Util.copyAmount(stack, 1)));
		}
		if(material.contain(SubTag.ALLOY_SIMPLE))
		{
			if(material.contain != null && material.contain.size() <= 3)
			{
				int size = 0;
				List<AbstractStack> list = new ArrayList();
				for (MaterialStack stack1 : material.contain)
				{
					size += stack1.amount;
					list.add(new SourceStack(stack1.material, (int) stack1.amount));
				}
				TemplateRecipeMap.ALLOY_SMELTING.addRecipe(list.toArray(new AbstractStack[list.size()]), new AbstractStack[]{new BaseStack(stack, size)}, 200 * size, 16 * material.heatCapability / 1000L, material.meltingPoint);
			}
		}
	}
}