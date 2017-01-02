/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.load.orereg;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;
import ttr.api.recipe.IOreDictRegister;
import ttr.api.recipe.TTrRecipeAdder;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.stack.AbstractStack;
import ttr.api.stack.BaseStack;
import ttr.api.stack.OreStack;
import ttr.api.util.MaterialStack;
import ttr.api.util.SubTag;
import ttr.api.util.Util;
import ttr.core.TTrMaterialHandler;
import ttr.core.TTrRecipeHandler;

/**
 * Created at 2016年12月20日 上午12:14:11
 * @author ueyudiud
 */
public class OreRegDust implements IOreDictRegister
{
	private static final FluidStack[] WASHING_WATER_INPUTS = new FluidStack[]{new FluidStack(FluidRegistry.WATER, 333)};
	
	@Override
	public void registerOreRecipes(ItemStack stack, EnumOrePrefix prefix, EnumMaterial material)
	{
		String small = EnumOrePrefix.dustSmall.getDictName(material);
		String tiny = EnumOrePrefix.dustTiny.getDictName(material);
		TTrRecipeHandler.addShapelessRecipe(Util.copyAmount(stack, 1), small, small, small, small);
		TTrRecipeHandler.addShapelessRecipe(Util.copyAmount(stack, 1), tiny, tiny, tiny, tiny, tiny, tiny, tiny, tiny, tiny);
		if(EnumOrePrefix.ingot.access(material))
		{
			TTrRecipeAdder.addGrindingRecipe(new OreStack(EnumOrePrefix.ingot.getDictName(material)), new BaseStack(stack, 1), 100, 8 * material.woughtHardness / 1000L);
		}
		if(EnumOrePrefix.stickLong.access(material))
		{
			TTrRecipeAdder.addGrindingRecipe(new OreStack(EnumOrePrefix.stickLong.getDictName(material)), new BaseStack(stack, 1), 100, 8 * material.woughtHardness / 1000L);
		}
		if(EnumOrePrefix.gear.access(material))
		{
			TTrRecipeAdder.addGrindingRecipe(new OreStack(EnumOrePrefix.gear.getDictName(material)), new BaseStack(stack, 4), 400, 8 * material.woughtHardness / 1000L);
		}
		if(EnumOrePrefix.plate.access(material))
		{
			TTrRecipeAdder.addGrindingRecipe(new OreStack(EnumOrePrefix.plate.getDictName(material)), new BaseStack(stack, 1), 100, 8 * material.woughtHardness / 1000L);
		}
		if(EnumOrePrefix.gem.access(material))
		{
			TTrRecipeAdder.addGrindingRecipe(new OreStack(EnumOrePrefix.gem.getDictName(material)), new BaseStack(stack, 1), 100, 8 * material.woughtHardness / 1000L);
		}
		if(EnumOrePrefix.dustOre.access(material) && material.contain(SubTag.NORMAL_ORE_PROCESSING))
		{
			TemplateRecipeMap.CAULDRON_WASHING.addRecipe(
					new AbstractStack[]{new OreStack(EnumOrePrefix.dustOre.getDictName(material))}, WASHING_WATER_INPUTS,
					new AbstractStack[]{new BaseStack(Util.copyAmount(stack, 1)), new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dustPowder, material.byproduct4, 1))},
					new int[][]{{10000}, {1250, 625, 313}}, null, 0, 0, 0, null);
		}
		if(material.contain(SubTag.ALLOY_SIMPLE))
		{
			if(material.contain != null)
			{
				int size = 0;
				List<AbstractStack> list = new ArrayList();
				List<Object> list1 = new ArrayList();
				for (MaterialStack stack1 : material.contain)
				{
					size += stack1.amount;
					list.add(new OreStack(prefix.getDictName(stack1.material), (int) stack1.amount));
					for(int i = 0; i < stack1.amount; ++i)
					{
						list1.add(prefix.getDictName(stack1.material));
					}
				}
				if(list1.size() <= 9)
				{
					TTrRecipeHandler.markRemoveCraftingShapelessInputs(list1.toArray());
					if(size % 4 == 0)
					{
						TTrRecipeHandler.addShapelessRecipe(Util.copyAmount(stack, size * 3 / 4), list1.toArray());
					}
				}
			}
		}
	}
}