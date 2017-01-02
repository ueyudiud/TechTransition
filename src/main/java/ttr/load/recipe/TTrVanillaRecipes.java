package ttr.load.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;
import ttr.api.enums.EnumTools;
import ttr.api.recipe.TTrRecipeAdder;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.stack.BaseStack;
import ttr.core.TTrMaterialHandler;
import ttr.core.TTrRecipeHandler;
import ttr.core.block.metal.BlockMetal1;
import ttr.load.TTrIBF;

public class TTrVanillaRecipes
{
	public static void init()
	{
		for(BlockMetal1.EnumType type : BlockMetal1.EnumType.values())
		{
			OreDictionary.registerOre(type.oreName(), new ItemStack(TTrIBF.metalblock1, 1, type.ordinal()));
		}
		
		TTrRecipeHandler.addShapedRecipe(new ItemStack(Blocks.PISTON), "xxx", "cic", "crc", 'x', "plankWood", 'c', "cobblestone", 'i', EnumOrePrefix.ingot.getDictName(EnumMaterial.Aluminium), 'r', "dustRedstone");
		TTrRecipeHandler.addShapedRecipe(new ItemStack(Blocks.PISTON), "xxx", "cic", "crc", 'x', "plankWood", 'c', "cobblestone", 'i', EnumOrePrefix.ingot.getDictName(EnumMaterial.Iron), 'r', "dustRedstone");
		TTrRecipeHandler.addShapedRecipe(new ItemStack(Blocks.PISTON), "xxx", "cic", "crc", 'x', "plankWood", 'c', "cobblestone", 'i', EnumOrePrefix.ingot.getDictName(EnumMaterial.WoughtIron), 'r', "dustRedstone");
		TTrRecipeHandler.addShapedRecipe(new ItemStack(Blocks.PISTON), "xxx", "cic", "crc", 'x', "plankWood", 'c', "cobblestone", 'i', EnumOrePrefix.ingot.getDictName(EnumMaterial.Bronze), 'r', "dustRedstone");
		TTrRecipeHandler.addShapedRecipe(new ItemStack(Blocks.PISTON), "xxx", "cic", "crc", 'x', "plankWood", 'c', "cobblestone", 'i', EnumOrePrefix.ingot.getDictName(EnumMaterial.LeadBronze), 'r', "dustRedstone");
		TTrRecipeHandler.addShapedRecipe(new ItemStack(Blocks.PISTON), "xxx", "cic", "crc", 'x', "plankWood", 'c', "cobblestone", 'i', EnumOrePrefix.ingot.getDictName(EnumMaterial.Steel), 'r', "dustRedstone");
		TTrRecipeHandler.addShapedRecipe(new ItemStack(Blocks.PISTON), "xxx", "cic", "crc", 'x', "plankWood", 'c', "cobblestone", 'i', EnumOrePrefix.ingot.getDictName(EnumMaterial.Titanium), 'r', "dustRedstone");
		
		TTrRecipeHandler.addShapedRecipe(new ItemStack(Blocks.CHEST), "xxx", "xsx", "xxx", 'x', "plankWood", 's', EnumTools.saw.orename());
		
		TTrRecipeHandler.addShapedRecipe(new ItemStack(Items.STICK, 2), "x", "x", 'x', "stickWood");
		TTrRecipeHandler.addShapedRecipe(new ItemStack(Items.STICK, 4), "s", "x", "x", 'x', "stickWood", 's', EnumTools.saw.orename());
		TTrRecipeHandler.addShapedRecipe(new ItemStack(Blocks.TORCH), "x", "s", 'x', EnumOrePrefix.gemChip.getDictName(EnumMaterial.Coal), 's', "stickWood");
		
		TemplateRecipeMap.SMELTING.addRecipe(new BaseStack(Items.PORKCHOP), 200, 10, new BaseStack(Items.COOKED_PORKCHOP), 400);
		TemplateRecipeMap.SMELTING.addRecipe(new BaseStack(Items.BEEF), 200, 10, new BaseStack(Items.COOKED_BEEF), 400);
		TemplateRecipeMap.SMELTING.addRecipe(new BaseStack(Items.CHICKEN), 200, 10, new BaseStack(Items.COOKED_CHICKEN), 400);
		for(ItemFishFood.FishType type : ItemFishFood.FishType.values())
		{
			if(type.canCook())
			{
				TemplateRecipeMap.SMELTING.addRecipe(new BaseStack(Items.FISH, 1, type.ordinal()), 200, 10, new BaseStack(Items.COOKED_FISH, 1, type.ordinal()), 400);
			}
		}
		TemplateRecipeMap.SMELTING.addRecipe(new BaseStack(Items.MUTTON), 200, 10, new BaseStack(Items.COOKED_MUTTON), 400);
		TemplateRecipeMap.SMELTING.addRecipe(new BaseStack(Items.RABBIT), 200, 10, new BaseStack(Items.COOKED_RABBIT), 400);
		TemplateRecipeMap.SMELTING.addRecipe(new BaseStack(Items.POTATO), 200, 10, new BaseStack(Items.BAKED_POTATO), 400);
		
		TemplateRecipeMap.SMELTING.addRecipe(new BaseStack(Blocks.COBBLESTONE), 200, 20, new BaseStack(Blocks.STONE), 1000);
		TemplateRecipeMap.SMELTING.addRecipe(new BaseStack(Blocks.NETHERRACK), 800, 40, new BaseStack(Items.NETHERBRICK), 1300);
		TemplateRecipeMap.SMELTING.addRecipe(new BaseStack(Blocks.CLAY), 800, 20, new BaseStack(Blocks.HARDENED_CLAY), 900);
		TemplateRecipeMap.SMELTING.addRecipe(new BaseStack(Items.CLAY_BALL), 200, 20, new BaseStack(Items.BRICK), 900);
		TemplateRecipeMap.SMELTING.addRecipe(new BaseStack(Blocks.CACTUS), 100, 16, new BaseStack(Items.DYE, 1, EnumDyeColor.GREEN.getDyeDamage()), 400);
		TemplateRecipeMap.SMELTING.addRecipe(new BaseStack(Blocks.SPONGE, 1, 1), 200, 10, new BaseStack(Blocks.SPONGE, 1, 0), 370);
		TemplateRecipeMap.SMELTING.addRecipe(new BaseStack(Items.CHORUS_FRUIT), 200, 20, new BaseStack(Items.CHORUS_FRUIT_POPPED), 500);
		
		TemplateRecipeMap.SMELTING.addRecipe(new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dust, EnumMaterial.Glass, 1)), 500, 20, new BaseStack(Blocks.GLASS));
		TemplateRecipeMap.COMPRESS.addRecipe(new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dust, EnumMaterial.Ice, 1)), 500, 20, new BaseStack(Blocks.ICE));
		TTrRecipeAdder.addGrindingRecipe(new BaseStack(Blocks.GLASS), new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dust, EnumMaterial.Glass, 1)), 240, 12);
		TTrRecipeAdder.addGrindingRecipe(new BaseStack(Blocks.ICE), new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dust, EnumMaterial.Ice, 1)), 160, 12);
		
		TemplateRecipeMap.CAULDRON_WASHING.addRecipe(new BaseStack(Blocks.SPONGE, 1, 0), new FluidStack(FluidRegistry.WATER, 1000), 0, 0, new BaseStack(Blocks.SPONGE, 1, 1));
	}
}