/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.load.orereg;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;
import ttr.api.enums.EnumTools;
import ttr.api.recipe.IOreDictRegister;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.stack.AbstractStack;
import ttr.api.stack.BaseStack;
import ttr.api.stack.OreStack;
import ttr.api.util.SubTag;
import ttr.api.util.Util;
import ttr.core.TTrRecipeHandler;

/**
 * Created at 2016年12月20日 上午9:29:45
 * @author ueyudiud
 */
public class OreRegGear implements IOreDictRegister
{
	@Override
	public void registerOreRecipes(ItemStack stack, EnumOrePrefix prefix, EnumMaterial material)
	{
		TTrRecipeHandler.markRemoveCraftingShapedInputs(" i ", "isi", " i ", 'i', EnumOrePrefix.ingot.getDictName(material), 's', new ItemStack(Items.IRON_INGOT));//TE gear
		if(material.contain(SubTag.METAL))
		{
			TTrRecipeHandler.addShapedRecipe(Util.copyAmount(stack, 1), "sps", "pwp", "sps", 's', EnumOrePrefix.stick.getDictName(material), 'p', EnumOrePrefix.plate.getDictName(material), 'w', EnumTools.wrench.orename());
			TemplateRecipeMap.ALLOY_SMELTING.addRecipe(new AbstractStack[]{new OreStack(EnumOrePrefix.ingot.getDictName(material), 8), new OreStack("mouldGear", 0)}, new AbstractStack[]{new BaseStack(Util.copyAmount(stack, 1))}, 1600 * material.heatCapability / 1000L, 64, material.meltingPoint);
		}
		else if(material.contain(SubTag.WOOD))
		{
			TTrRecipeHandler.addShapedRecipe(Util.copyAmount(stack, 1), "sps", "pwp", "sps", 's', EnumOrePrefix.stick.getDictName(material), 'p', EnumOrePrefix.plate.getDictName(material), 'w', EnumTools.saw.orename());
		}
		else if(material.contain(SubTag.POLYMER))
		{
			TemplateRecipeMap.ALLOY_SMELTING.addRecipe(new AbstractStack[]{new OreStack(EnumOrePrefix.dust.getDictName(material), 8), new OreStack("mouldGear", 0)}, new AbstractStack[]{new BaseStack(Util.copyAmount(stack, 1))}, 1600 * material.heatCapability / 1000L, 64, material.meltingPoint);
		}
	}
}