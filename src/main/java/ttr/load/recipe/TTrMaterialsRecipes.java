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
	//	private static final AbstractStack[] EMPTY_ITEM_STACK = {};
	//	private static final FluidStack[] EMPTY_FLUID_STACK = {};
	
	public static void init()
	{
		OreDictionary.registerOre("plateAdvancedCarbon", IC2Items.getItem("crafting", "carbon_plate"));
		OreDictionary.registerOre("plateAdvancedAlloy", IC2Items.getItem("crafting", "alloy"));
		
		TemplateRecipeMap.BRONZE_COMPRESS.addRecipe(new BaseStack(IC2Items.getItem("ingot", "alloy")), 100, 200, new OreStack("plateAdvancedAlloy"));
		TemplateRecipeMap.COMPRESS.addRecipe(new BaseStack(IC2Items.getItem("ingot", "alloy")), 200, 80, new OreStack("plateAdvancedAlloy"));
		
		BaseStack blastAsh = new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dust, EnumMaterial.BlastAsh, 4));
		TemplateRecipeMap.BRONZE_BLAST.addRecipe(new AbstractStack[]{new OreStack("ingotIron"), new OreStack("gemCharcoal", 4)}, 3600, 1, new AbstractStack[]{new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.ingot, EnumMaterial.Steel, 1)), blastAsh});
		TemplateRecipeMap.BRONZE_BLAST.addRecipe(new AbstractStack[]{new OreStack("ingotIron"), new OreStack("dustCharcoal", 4)}, 3600, 1, new AbstractStack[]{new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.ingot, EnumMaterial.Steel, 1)), blastAsh});
		TemplateRecipeMap.BRONZE_BLAST.addRecipe(new AbstractStack[]{new OreStack("ingotIron"), new OreStack("gemCoal", 4)}, 3600, 1, new AbstractStack[]{new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.ingot, EnumMaterial.Steel, 1)), blastAsh});
		TemplateRecipeMap.BRONZE_BLAST.addRecipe(new AbstractStack[]{new OreStack("ingotIron"), new OreStack("dustCoal", 4)}, 3600, 1, new AbstractStack[]{new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.ingot, EnumMaterial.Steel, 1)), blastAsh});
		
		TTrRecipeAdder.addExtractRecipe(new BaseStack(IC2Items.getItem("misc_resource", "resin")), new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dust, EnumMaterial.RawRubber, 3)), new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dust, EnumMaterial.RawRubber, 1)), 300, 16);
		TemplateRecipeMap.ALLOY_SMELTING.addRecipe(new AbstractStack[]{new OreStack(EnumOrePrefix.dust.getDictName(EnumMaterial.RawRubber), 3), new OreStack(EnumOrePrefix.dust.getDictName(EnumMaterial.Sulfur), 1)}, new AbstractStack[]{new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.ingot, EnumMaterial.Rubber, 1))}, 200L, 30L, 600);
		
		TemplateRecipeMap.FORGE.addRecipe(new AbstractStack[]{new OreStack(EnumOrePrefix.dust.getDictName(EnumMaterial.FerrosoFerricOxide), 7), new OreStack("dustCoal", 2), new OreStack("dustMarble")}, new AbstractStack[]{new OreStack("dustIron", 3), new OreStack("dustSmallBlastAsh")}, 4000, 20, 1000);
		TemplateRecipeMap.FORGE.addRecipe(new AbstractStack[]{new OreStack(EnumOrePrefix.dust.getDictName(EnumMaterial.FerrosoFerricOxide), 7), new OreStack("dustCoal", 2), new OreStack("dustCalcite")}, new AbstractStack[]{new OreStack("dustIron", 3), new OreStack("dustSmallBlastAsh")}, 4000, 20, 1000);
		TemplateRecipeMap.FORGE.addRecipe(new AbstractStack[]{new OreStack(EnumOrePrefix.dust.getDictName(EnumMaterial.FerricOxide), 10), new OreStack("dustCoal", 3), new OreStack("dustMarble")}, new AbstractStack[]{new OreStack("dustIron", 4), new OreStack("dustSmallBlastAsh")}, 2000, 20, 1000);
		TemplateRecipeMap.FORGE.addRecipe(new AbstractStack[]{new OreStack(EnumOrePrefix.dust.getDictName(EnumMaterial.FerricOxide), 10), new OreStack("dustCoal", 3), new OreStack("dustCalcite")}, new AbstractStack[]{new OreStack("dustIron", 4), new OreStack("dustSmallBlastAsh")}, 2000, 20, 1000);
		TemplateRecipeMap.FORGE.addRecipe(new AbstractStack[]{new OreStack(EnumOrePrefix.dust.getDictName(EnumMaterial.FerrosoFerricOxide), 7), new OreStack("dustCharcoal", 2), new OreStack("dustMarble")}, new AbstractStack[]{new OreStack("dustIron", 3), new OreStack("dustSmallBlastAsh")}, 4000, 20, 1000);
		TemplateRecipeMap.FORGE.addRecipe(new AbstractStack[]{new OreStack(EnumOrePrefix.dust.getDictName(EnumMaterial.FerrosoFerricOxide), 7), new OreStack("dustCharcoal", 2), new OreStack("dustCalcite")}, new AbstractStack[]{new OreStack("dustIron", 3), new OreStack("dustSmallBlastAsh")}, 4000, 20, 1000);
		TemplateRecipeMap.FORGE.addRecipe(new AbstractStack[]{new OreStack(EnumOrePrefix.dust.getDictName(EnumMaterial.FerricOxide), 10), new OreStack("dustCharcoal", 3), new OreStack("dustMarble")}, new AbstractStack[]{new OreStack("dustIron", 4), new OreStack("dustSmallBlastAsh")}, 2000, 20, 1000);
		TemplateRecipeMap.FORGE.addRecipe(new AbstractStack[]{new OreStack(EnumOrePrefix.dust.getDictName(EnumMaterial.FerricOxide), 10), new OreStack("dustCharcoal", 3), new OreStack("dustCalcite")}, new AbstractStack[]{new OreStack("dustIron", 4), new OreStack("dustSmallBlastAsh")}, 2000, 20, 1000);
		
		TemplateRecipeMap.PYROLYSIS.addRecipe(new OreStack(EnumOrePrefix.dust.getDictName(EnumMaterial.SodiumCarbonate), 2), 720, 192, new OreStack(EnumOrePrefix.dust.getDictName(EnumMaterial.SodiumOxide)), new FluidStack(EnumMaterial.CarbonDioxide.gas, 1000));
		TemplateRecipeMap.PYROLYSIS.addRecipe(new OreStack(EnumOrePrefix.dust.getDictName(EnumMaterial.LithiumCarbonate), 2), 360, 160, new OreStack(EnumOrePrefix.dust.getDictName(EnumMaterial.LithiumOxide)), new FluidStack(EnumMaterial.CarbonDioxide.gas, 1000));
	}
}