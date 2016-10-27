package ttr.load.recipe;

import static ttr.api.recipe.TTrRecipeAdder.FULL_CHANCES;

import ic2.api.item.IC2Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import ttr.api.data.EnumToolType;
import ttr.api.data.MC;
import ttr.api.material.Mat;
import ttr.api.recipe.TTrRecipeAdder;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.stack.AbstractStack;
import ttr.api.stack.OreStack;
import ttr.api.util.SubTag;
import ttr.load.TTrFluids;
import ttr.load.TTrItems;

public class TTrMaterialsRecipes
{
	public static void init()
	{
		OreDictionary.registerOre("plateAdvancedCarbon", IC2Items.getItem("crafting", "carbon_plate"));
		OreDictionary.registerOre("plateAdvancedAlloy", IC2Items.getItem("crafting", "alloy"));
		AbstractStack stone1 = new OreStack("dustStone");
		for(Mat material : Mat.register())
		{
			boolean sulfate = TTrFluids.sulfate.containsKey(material);
			boolean nitric = TTrFluids.nitric.containsKey(material);
			boolean chlorhydric = TTrFluids.chlorhydric.containsKey(material);
			boolean sulfate_impure = TTrFluids.sulfate_impure.containsKey(material);
			boolean nitric_impure = TTrFluids.nitric_impure.containsKey(material);
			boolean chlorhydric_impure = TTrFluids.chlorhydric_impure.containsKey(material);
			AbstractStack dust1 = new OreStack("dust" + material.oreDictName);
			GameRegistry.addRecipe(new ShapelessOreRecipe(dust1.instance(), "dustTiny" + material.oreDictName, "dustTiny" + material.oreDictName, "dustTiny" + material.oreDictName, "dustTiny" + material.oreDictName, "dustTiny" + material.oreDictName, "dustTiny" + material.oreDictName, "dustTiny" + material.oreDictName, "dustTiny" + material.oreDictName, "dustTiny" + material.oreDictName));
			GameRegistry.addRecipe(new ShapelessOreRecipe(dust1.instance(), "dustSmall" + material.oreDictName, "dustSmall" + material.oreDictName, "dustSmall" + material.oreDictName, "dustSmall" + material.oreDictName));
			GameRegistry.addRecipe(new ShapedOreRecipe(new OreStack("dustTiny" + material.oreDictName, 9).instance(), " x ", "   ", "   ", 'x', "dust" + material.oreDictName));
			GameRegistry.addRecipe(new ShapedOreRecipe(new OreStack("dustSmall" + material.oreDictName, 4).instance(), "x  ", "   ", "   ", 'x', "dust" + material.oreDictName));
			if(material.contain(SubTag.METAL))
			{
				AbstractStack ingot1 = new OreStack("ingot" + material.oreDictName);
				AbstractStack plate1 = new OreStack("plate" + material.oreDictName);
				AbstractStack stick1 = new OreStack("stick" + material.oreDictName);
				if(material.canMakeTool)
				{
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrItems.axe, 1, material.id), true, "pih", "ps ", "fs ", 'p', "plate" + material.oreDictName, 'i', "ingot" + material.oreDictName, 's', "stick" + material.handleMaterial.oreDictName, 'f', EnumToolType.file.ore(), 'h', EnumToolType.hard_hammer.ore()));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrItems.pickaxe, 1, material.id), true, "pii", "fsh", " s ", 'p', "plate" + material.oreDictName, 'i', "ingot" + material.oreDictName, 's', "stick" + material.handleMaterial.oreDictName, 'f', EnumToolType.file.ore(), 'h', EnumToolType.hard_hammer.ore()));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrItems.shovel, 1, material.id), true, "fph", " s ", " s ", 'p', "plate" + material.oreDictName, 'i', "ingot" + material.oreDictName, 's', "stick" + material.handleMaterial.oreDictName, 'f', EnumToolType.file.ore(), 'h', EnumToolType.hard_hammer.ore()));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrItems.hoe, 1, material.id), true, "pih", "fs ", " s ", 'p', "plate" + material.oreDictName, 'i', "ingot" + material.oreDictName, 's', "stick" + material.handleMaterial.oreDictName, 'f', EnumToolType.file.ore(), 'h', EnumToolType.hard_hammer.ore()));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrItems.sword, 1, material.id), true, " p ", "fph", " s ", 'p', "plate" + material.oreDictName, 'i', "ingot" + material.oreDictName, 's', "stick" + material.handleMaterial.oreDictName, 'f', EnumToolType.file.ore(), 'h', EnumToolType.hard_hammer.ore()));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrItems.hard_hammer, 1, material.id), true, "ii ", "iis", "ii ", 'p', "plate" + material.oreDictName, 'i', "ingot" + material.oreDictName, 's', "stick" + material.handleMaterial.oreDictName, 'f', EnumToolType.file.ore(), 'h', EnumToolType.hard_hammer.ore()));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrItems.file, 1, material.id), true, " p ", " p ", " s ", 'p', "plate" + material.oreDictName, 'i', "ingot" + material.oreDictName, 's', "stick" + material.handleMaterial.oreDictName, 'f', EnumToolType.file.ore(), 'h', EnumToolType.hard_hammer.ore()));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrItems.saw, 1, material.id), true, "sss", "pps", "hf ", 'p', "plate" + material.oreDictName, 'i', "ingot" + material.oreDictName, 's', "stick" + material.handleMaterial.oreDictName, 'f', EnumToolType.file.ore(), 'h', EnumToolType.hard_hammer.ore()));
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrItems.wrench, 1, material.id), true, "ihi", "iii", " i ", 'p', "plate" + material.oreDictName, 'i', "ingot" + material.oreDictName, 's', "stick" + material.handleMaterial.oreDictName, 'f', EnumToolType.file.ore(), 'h', EnumToolType.hard_hammer.ore()));
				}
				GameRegistry.addRecipe(new ShapedOreRecipe(plate1.instance(), "h", "x", "x", 'h', EnumToolType.hard_hammer.ore(), 'x', "ingot" + material.oreDictName));
				GameRegistry.addRecipe(new ShapedOreRecipe(stick1.instance(), "f", "x", 'f', EnumToolType.file.ore(), 'x', "ingot" + material.oreDictName));
				TemplateRecipeMap.FORGE_HAMMER.addRecipe(new OreStack("ingot" + material.oreDictName, 3), 600, 30, new OreStack("plate" + material.oreDictName, 2));
				if(material.hasBlock)
				{
					TemplateRecipeMap.COMPRESS.addRecipe(new OreStack("ingot" + material.oreDictName, 9), 300, 32, new OreStack("block" + material.oreDictName));
					TemplateRecipeMap.BRONZE_COMPRESS.addRecipe(new OreStack("ingot" + material.oreDictName, 9), 300, (long) (300 * material.blockHardness), new OreStack("block" + material.oreDictName));
					TemplateRecipeMap.SMELTING.addRecipe(new OreStack("block" + material.oreDictName), 1800, 16, new OreStack("ingot" + material.oreDictName, 9), material.meltingPoint);
				}
				if(material.meltingPoint <= 1800 && material.meltingPoint >= 300)
				{
					TemplateRecipeMap.SMELTING.addRecipe(dust1, 200, 20, ingot1, material.meltingPoint);
					TemplateRecipeMap.SMELTING.addRecipe(plate1, 200, 32, ingot1);
				}
				if(sulfate)
				{
					TemplateRecipeMap.CAULDRON_SOLUTE.addRecipe(new AbstractStack[]{dust1}, new FluidStack[]{new FluidStack(TTrFluids.sulphuric_acid, 1000)}, new AbstractStack[]{stone1}, new FluidStack[]{new FluidStack(TTrFluids.sulfate.get(material), 1000)}, 1, 1);
					TemplateRecipeMap.CAULDRON_SOLUTE.addRecipe(new AbstractStack[]{ingot1}, new FluidStack[]{new FluidStack(TTrFluids.sulphuric_acid, 1000)}, new AbstractStack[]{stone1}, new FluidStack[]{new FluidStack(TTrFluids.sulfate.get(material), 1000)}, 1, 1);
				}
				if(nitric)
				{
					TemplateRecipeMap.CAULDRON_SOLUTE.addRecipe(new AbstractStack[]{dust1}, new FluidStack[]{new FluidStack(TTrFluids.nitric_acid, 1000)}, new AbstractStack[]{stone1}, new FluidStack[]{new FluidStack(TTrFluids.nitric.get(material), 1000)}, 1, 1);
					TemplateRecipeMap.CAULDRON_SOLUTE.addRecipe(new AbstractStack[]{ingot1}, new FluidStack[]{new FluidStack(TTrFluids.nitric_acid, 1000)}, new AbstractStack[]{stone1}, new FluidStack[]{new FluidStack(TTrFluids.nitric.get(material), 1000)}, 1, 1);
				}
				//			if(chlorhydric)
				//			{
				//				CauldronWashingRecipe.instance.addRecipe(new UnbakedCauldronWashing("crushed_solute_in_sulfate", TTrFluids.sulphuric_acid, new OreStack("crushed" + material.oreDictName), TTrFluids.sulfate.get(material), 0.0625F, new OreStack("dustTiny" + material.byproduct1.oreDictName)));
				//			}
			}
			if(MC.orePurifiedCrushed.isBelongTo(material))
			{
				if(sulfate_impure)
				{
					TemplateRecipeMap.CAULDRON_SOLUTE.addRecipe(new AbstractStack[]{new OreStack("crushed" + material.oreDictName)}, new FluidStack[]{new FluidStack(TTrFluids.sulphuric_acid, 1000)}, new AbstractStack[]{stone1, new OreStack("dustTiny" + material.byproduct1.oreDictName)}, new int[]{8000, 625}, new FluidStack[]{new FluidStack(TTrFluids.sulfate_impure.get(material), 1000)}, 1, 1, 0, null);
				}
				if(nitric_impure)
				{
					TemplateRecipeMap.CAULDRON_SOLUTE.addRecipe(new AbstractStack[]{new OreStack("crushed" + material.oreDictName)}, new FluidStack[]{new FluidStack(TTrFluids.nitric_acid, 1000)}, new AbstractStack[]{stone1, new OreStack("dustTiny" + material.byproduct1.oreDictName)}, new int[]{8000, 625}, new FluidStack[]{new FluidStack(TTrFluids.nitric_impure.get(material), 1000)}, 1, 1, 0, null);
				}
				//			if(chlorhydric_impure)
				//			{
				//				CauldronWashingRecipe.instance.addRecipe(new UnbakedCauldronWashing("crushed_solute_in_sulfate", TTrFluids.sulphuric_acid, new OreStack("crushed" + material.oreDictName), TTrFluids.sulfate.get(material), 0.0625F, new OreStack("dustTiny" + material.byproduct1.oreDictName)));
				//			}
				TemplateRecipeMap.CAULDRON_WASHING.addRecipe(new AbstractStack[]{new OreStack("crushed" + material.oreDictName)}, new FluidStack[]{new FluidStack(FluidRegistry.WATER, 333)}, new AbstractStack[]{new OreStack("crushedPurified" + material.oreDictName), stone1, new OreStack("dustTiny" + material.byproduct1.oreDictName)}, new int[]{10000, 6000, 625}, null, 1, 1, 0, null);
				TTrRecipeAdder.addGrindingRecipe(new OreStack("crushed" + material.oreDictName), dust1, stone1, new int[]{6000}, new OreStack("dustSmall" + material.byproduct2.oreDictName), new int[]{4000, 2000, 1000, 500}, 300, 16);
				TTrRecipeAdder.addGrindingRecipe(new OreStack("crushedPurified" + material.oreDictName), dust1, stone1, new int[]{6000}, new OreStack("dustSmall" + material.byproduct3.oreDictName), new int[]{4000, 2000, 1000, 500}, 300, 16);
				TTrRecipeAdder.addGrindingRecipe(new OreStack("crushedCentrifuged" + material.oreDictName), dust1, stone1, new int[]{6000}, new OreStack("dustSmall" + material.byproduct4.oreDictName), new int[]{4000, 2000, 1000, 500}, 300, 16);
			}
			if(MC.ore.isBelongTo(material))
			{
				if(MC.oreCrushed.isBelongTo(material))
				{
					if(material.contain(SubTag.ORE_CRYSTAL))
					{
						int[][] chances = new int[][]{FULL_CHANCES, {6000}, {625}, {3500, 1750, 1000, 500}};
						TTrRecipeAdder.addGrindingRecipe(
								new OreStack("ore" + material.oreDictName),
								new AbstractStack[]{new OreStack("crushed" + material.oreDictName, 2), stone1, new OreStack("gem" + material.oreDictName), new OreStack("dustSmall" + material.byproduct1.oreDictName)}, chances,
								new AbstractStack[]{new OreStack("crushed" + material.oreDictName), stone1}, new int[][]{FULL_CHANCES, {6000}}, 500, 24);
					}
					else
					{
						int[][] chances = new int[][]{FULL_CHANCES, {6000}, {4000, 2000, 1000, 500}};
						TTrRecipeAdder.addGrindingRecipe(
								new OreStack("ore" + material.oreDictName),
								new AbstractStack[]{new OreStack("crushed" + material.oreDictName, 2), stone1, new OreStack("dustSmall" + material.byproduct1.oreDictName)}, chances,
								new AbstractStack[]{new OreStack("crushed" + material.oreDictName), stone1}, new int[][]{FULL_CHANCES, {6000}}, 500, 24);
					}
				}
			}
			if(material.contain(SubTag.GEM))
			{
				AbstractStack gem = new OreStack("gem" + material.oreDictName);
				TTrRecipeAdder.addGrindingRecipe(gem, dust1, 500, 40);
			}
			if(material.contain(SubTag.GEN_COAL))
			{
				AbstractStack gem = new OreStack("gem" + material.oreDictName);
				TTrRecipeAdder.addGrindingRecipe(gem, dust1, new OreStack("dustTiny" + material.byproduct1.oreDictName), new int[]{8000, 3000, 1000}, 500, 40);
			}
			if(MC.plate.isBelongTo(material))
			{
				AbstractStack plate = new OreStack("plate" + material.oreDictName);
				TTrRecipeAdder.addGrindingRecipe(plate, dust1, 400, 25);
			}
			if(MC.ingot.isBelongTo(material))
			{
				AbstractStack ingot = new OreStack("ingot" + material.oreDictName);
				TTrRecipeAdder.addGrindingRecipe(ingot, dust1, 400, 25);
			}
		}
		
		GameRegistry.addRecipe(new ShapedOreRecipe(TTrItems.sub.get("bronzeGear"), "sps", "pwp", "sps", 's', "stickBronze", 'p', "plateBronze", 'w', EnumToolType.wrench.ore()));
		GameRegistry.addRecipe(new ShapedOreRecipe(TTrItems.sub.get("ironGear"), "sps", "pwp", "sps", 's', "stickIron", 'p', "plateIron", 'w', EnumToolType.wrench.ore()));
		GameRegistry.addRecipe(new ShapedOreRecipe(TTrItems.sub.get("steelGear"), "sps", "pwp", "sps", 's', "stickSteel", 'p', "plateSteel", 'w', EnumToolType.wrench.ore()));
		GameRegistry.addRecipe(new ShapedOreRecipe(TTrItems.sub.get("tungstensteelGear"), "sps", "pwp", "sps", 's', "stickTungstensteel", 'p', "plateTungstensteel", 'w', EnumToolType.wrench.ore()));
		GameRegistry.addRecipe(new ShapedOreRecipe(TTrItems.sub.get("stainlessSteelGear"), "sps", "pwp", "sps", 's', "stickStainlessSteel", 'p', "plateStainlessSteel", 'w', EnumToolType.wrench.ore()));
		GameRegistry.addRecipe(new ShapedOreRecipe(TTrItems.sub.get("titaniumGear"), "sps", "pwp", "sps", 's', "stickTitanium", 'p', "plateTitanium", 'w', EnumToolType.wrench.ore()));

		TemplateRecipeMap.FORGE.addRecipe(new AbstractStack[]{new OreStack("dustMagnetite", 7), new OreStack("dustCoal", 2), new OreStack("dustMarble")}, new AbstractStack[]{new OreStack("dustIron", 4)}, 4000, 20, 1000);
		TemplateRecipeMap.FORGE.addRecipe(new AbstractStack[]{new OreStack("dustMagnetite", 7), new OreStack("dustCoal", 2), new OreStack("dustCalcite")}, new AbstractStack[]{new OreStack("dustIron", 4)}, 4000, 20, 1000);
		TemplateRecipeMap.FORGE.addRecipe(new AbstractStack[]{new OreStack("dustLimonite", 8), new OreStack("dustCoal"), new OreStack("dustMarble")}, new AbstractStack[]{new OreStack("dustIron", 2)}, 2000, 20, 1000);
		TemplateRecipeMap.FORGE.addRecipe(new AbstractStack[]{new OreStack("dustLimonite", 8), new OreStack("dustCoal"), new OreStack("dustCalcite")}, new AbstractStack[]{new OreStack("dustIron", 2)}, 2000, 20, 1000);
	}
}