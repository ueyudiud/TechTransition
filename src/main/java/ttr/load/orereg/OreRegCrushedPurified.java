/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.load.orereg;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;
import ttr.api.recipe.IOreDictRegister;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.stack.AbstractStack;
import ttr.api.stack.BaseStack;
import ttr.api.stack.OreStack;
import ttr.api.util.SubTag;
import ttr.api.util.Util;
import ttr.core.TTrMaterialHandler;
import ttr.load.TTrIBF;

/**
 * Created at 2016年12月21日 上午10:22:43
 * @author ueyudiud
 */
public class OreRegCrushedPurified implements IOreDictRegister
{
	private static final BaseStack STONE_SMALL = new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dustSmall, EnumMaterial.Stone, 1));
	
	@Override
	public void registerOreRecipes(ItemStack stack, EnumOrePrefix prefix, EnumMaterial material)
	{
		if(material.contain(SubTag.NORMAL_ORE_PROCESSING))
		{
			TemplateRecipeMap.CAULDRON_WASHING.addRecipe(
					new AbstractStack[]{new OreStack(EnumOrePrefix.crushed.getDictName(material))}, new FluidStack[]{new FluidStack(FluidRegistry.WATER, 333)},
					new AbstractStack[]{new BaseStack(Util.copyAmount(stack, 1)), STONE_SMALL, new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dustOreTiny, material.byproduct1, 1))},
					new int[]{10000, 6000, 625}, null, 1, 1, 0, null);
			TemplateRecipeMap.ORE_WASHING.addRecipe(
					new AbstractStack[]{new OreStack(EnumOrePrefix.crushed.getDictName(material))},
					new FluidStack[]{new FluidStack(FluidRegistry.WATER, 1000)},
					new AbstractStack[]{new BaseStack(stack, 1), new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dustOreTiny, material.byproduct1, 1))},
					new FluidStack[]{new FluidStack(TTrIBF.ore_washed_water, 1000)}, 400, 48);
		}
	}
}