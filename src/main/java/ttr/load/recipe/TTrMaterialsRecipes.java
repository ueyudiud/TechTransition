package ttr.load.recipe;

import ic2.api.item.IC2Items;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;
import ttr.api.recipe.TTrRecipeAdder;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.stack.AbstractStack;
import ttr.api.stack.BaseStack;
import ttr.api.stack.OreStack;
import ttr.core.TTrMaterialHandler;

public class TTrMaterialsRecipes
{
	private static final AbstractStack[] EMPTY_ITEM_STACK = {};
	private static final FluidStack[] EMPTY_FLUID_STACK = {};
	
	public static void init()
	{
		OreDictionary.registerOre("plateAdvancedCarbon", IC2Items.getItem("crafting", "carbon_plate"));
		OreDictionary.registerOre("plateAdvancedAlloy", IC2Items.getItem("crafting", "alloy"));
		
		BaseStack blastAsh = new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dust, EnumMaterial.BlastAsh, 4));
		TemplateRecipeMap.BRONZE_BLAST.addRecipe(new AbstractStack[]{new OreStack("ingotIron"), new OreStack("gemCharcoal", 4)}, 3600, 1, new AbstractStack[]{new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.ingot, EnumMaterial.Steel, 1)), blastAsh});
		TemplateRecipeMap.BRONZE_BLAST.addRecipe(new AbstractStack[]{new OreStack("ingotIron"), new OreStack("dustCharcoal", 4)}, 3600, 1, new AbstractStack[]{new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.ingot, EnumMaterial.Steel, 1)), blastAsh});
		TemplateRecipeMap.BRONZE_BLAST.addRecipe(new AbstractStack[]{new OreStack("ingotIron"), new OreStack("gemCoal", 4)}, 3600, 1, new AbstractStack[]{new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.ingot, EnumMaterial.Steel, 1)), blastAsh});
		TemplateRecipeMap.BRONZE_BLAST.addRecipe(new AbstractStack[]{new OreStack("ingotIron"), new OreStack("dustCoal", 4)}, 3600, 1, new AbstractStack[]{new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.ingot, EnumMaterial.Steel, 1)), blastAsh});
		
		TTrRecipeAdder.addExtractRecipe(new BaseStack(IC2Items.getItem("misc_resource", "resin")), new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dust, EnumMaterial.RawRubber, 3)), new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dust, EnumMaterial.RawRubber, 1)), 300, 16);
		TemplateRecipeMap.ALLOY_SMELTING.addRecipe(new AbstractStack[]{new OreStack(EnumOrePrefix.dust.getDictName(EnumMaterial.RawRubber), 3), new OreStack(EnumOrePrefix.dust.getDictName(EnumMaterial.Sulfur), 1)}, new AbstractStack[]{new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.ingot, EnumMaterial.Rubber, 1))}, 200L, 30L, 600);
		
		TemplateRecipeMap.FORGE.addRecipe(new AbstractStack[]{new OreStack("dustMagnetite", 7), new OreStack("dustCoal", 2), new OreStack("dustMarble")}, new AbstractStack[]{new OreStack("dustIron", 4)}, 4000, 20, 1000);
		TemplateRecipeMap.FORGE.addRecipe(new AbstractStack[]{new OreStack("dustMagnetite", 7), new OreStack("dustCoal", 2), new OreStack("dustCalcite")}, new AbstractStack[]{new OreStack("dustIron", 4)}, 4000, 20, 1000);
		TemplateRecipeMap.FORGE.addRecipe(new AbstractStack[]{new OreStack("dustLimonite", 8), new OreStack("dustCoal"), new OreStack("dustMarble")}, new AbstractStack[]{new OreStack("dustIron", 2)}, 2000, 20, 1000);
		TemplateRecipeMap.FORGE.addRecipe(new AbstractStack[]{new OreStack("dustLimonite", 8), new OreStack("dustCoal"), new OreStack("dustCalcite")}, new AbstractStack[]{new OreStack("dustIron", 2)}, 2000, 20, 1000);
	}
}