package ttr.load.recipe;

import ic2.api.item.IC2Items;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;
import ttr.api.enums.EnumTools;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.stack.OreStack;
import ttr.api.util.Util;
import ttr.core.TTrRecipeHandler;
import ttr.load.TTrIBF;

public class TTrMachineRecipes
{
	public static void init()
	{
		OreDictionary.registerOre("machineCasingTier1", new ItemStack(TTrIBF.casing, 1, 0));
		OreDictionary.registerOre("machineCasingTier1", new ItemStack(TTrIBF.casing, 1, 1));
		OreDictionary.registerOre("machineCasingTier2", new ItemStack(TTrIBF.casing, 1, 2));
		OreDictionary.registerOre("machineCasingTier2", new ItemStack(TTrIBF.casing, 1, 3));
		OreDictionary.registerOre("machineCasingTier2", new ItemStack(TTrIBF.casing, 1, 4));
		OreDictionary.registerOre("machineCasingTier3", new ItemStack(TTrIBF.casing, 1, 4));
		OreDictionary.registerOre("machineCasingTier3", new ItemStack(TTrIBF.casing, 1, 5));
		OreDictionary.registerOre("machineCasingTier4", new ItemStack(TTrIBF.casing, 1, 5));
		OreDictionary.registerOre("machineCasingTier4", new ItemStack(TTrIBF.casing, 1, 6));
		OreDictionary.registerOre("machineCasingTier5", new ItemStack(TTrIBF.casing, 1, 6));
		OreDictionary.registerOre("machineCasingTier5", new ItemStack(TTrIBF.casing, 1, 7));
		OreDictionary.registerOre("machineCasingTier6", new ItemStack(TTrIBF.casing, 1, 8));
		OreDictionary.registerOre("machineCasingTier7", new ItemStack(TTrIBF.casing, 1, 9));
		OreDictionary.registerOre("machineBrickCasingBronze", new ItemStack(TTrIBF.casing, 1, 10));
		OreDictionary.registerOre("machineBrickCasingSteel", new ItemStack(TTrIBF.casing, 1, 11));
		OreDictionary.registerOre("craftingMachineFurnace", TTrIBF.furnace);
		OreDictionary.registerOre("craftingMachineFurnace", Blocks.FURNACE);
		OreDictionary.registerOre("craftingMachineFurnace", Blocks.LIT_FURNACE);
		OreDictionary.registerOre("craftingMachineCauldron", Blocks.CAULDRON);
		OreDictionary.registerOre("craftingMachineCauldron", TTrIBF.cauldron);
		OreDictionary.registerOre("craftingMachinePiston", Blocks.PISTON);
		OreDictionary.registerOre("craftingMachinePiston", Blocks.PISTON_EXTENSION);
		OreDictionary.registerOre("circuitTier0", IC2Items.getItem("crafting", "circuit"));
		OreDictionary.registerOre("circuitTier2", IC2Items.getItem("crafting", "advanced_circuit"));
		OreDictionary.registerOre("cableCopper", IC2Items.getItem("cable", "type:copper,insulation:0"));
		OreDictionary.registerOre("cableCopperInsulated", IC2Items.getItem("cable", "type:copper,insulation:1"));
		OreDictionary.registerOre("cableTin", IC2Items.getItem("cable", "type:tin,insulation:0"));
		OreDictionary.registerOre("cableTinInsulated", IC2Items.getItem("cable", "type:tin,insulation:1"));
		OreDictionary.registerOre("cableGold", IC2Items.getItem("cable", "type:gold,insulation:0"));
		OreDictionary.registerOre("cableGoldInsulated", IC2Items.getItem("cable", "type:gold,insulation:1"));
		OreDictionary.registerOre("cableIron", IC2Items.getItem("cable", "type:iron,insulation:0"));
		OreDictionary.registerOre("cableIronInsulated", IC2Items.getItem("cable", "type:iron,insulation:1"));
		
		TemplateRecipeMap.WIREMILL.addRecipe(new OreStack("ingotTin"), 400, 30, new OreStack("cableTin", 3));
		TemplateRecipeMap.WIREMILL.addRecipe(new OreStack("ingotCopper"), 400, 30, new OreStack("cableCopper", 3));
		TemplateRecipeMap.WIREMILL.addRecipe(new OreStack("ingotGold"), 400, 30, new OreStack("cableGold", 3));
		TemplateRecipeMap.WIREMILL.addRecipe(new OreStack("ingotIron"), 400, 30, new OreStack("cableIron", 3));
		
		TTrRecipeHandler.addShapelessRecipe(Util.copyAmount(IC2Items.getItem("cable", "type:copper,insulation:0"), 1), "plateCopper", EnumTools.cutter.orename());
		TTrRecipeHandler.addShapelessRecipe(Util.copyAmount(IC2Items.getItem("cable", "type:tin,insulation:0"), 1), "plateTin", EnumTools.cutter.orename());
		TTrRecipeHandler.addShapelessRecipe(Util.copyAmount(IC2Items.getItem("cable", "type:gold,insulation:0"), 1), "plateGold", EnumTools.cutter.orename());
		TTrRecipeHandler.addShapelessRecipe(Util.copyAmount(IC2Items.getItem("cable", "type:iron,insulation:0"), 1), "plateIron", EnumTools.cutter.orename());
		
		TTrRecipeHandler.addShapedRecipe(IC2Items.getItem("energy_crystal"), "xxx", "xox", "xxx", 'x', "dustRedstone", 'o', "gemDiamond");
		TTrRecipeHandler.addShapedRecipe(IC2Items.getItem("energy_crystal"), "xxx", "xox", "xxx", 'x', "dustRedstone", 'o', "gemRuby");
		TTrRecipeHandler.addShapedRecipe(IC2Items.getItem("lapotron_crystal"), "xcx", "xox", "xcx", 'x', "gemLapis", 'o', "gemSapphire", 'c', "circuitTier2");
		
		TTrRecipeHandler.addShapedRecipe(new ItemStack(TTrIBF.brick, 2, 0),
				"ppp",
				"bhb",
				"ppp", 'p', "plateBronze", 'b', Blocks.BRICK_BLOCK, 'h', EnumTools.hammer.orename());
		TTrRecipeHandler.addShapedRecipe(new ItemStack(TTrIBF.brick, 1, 1),
				"ppp",
				"cbc",
				"ppp", 'p', "plateIron", 'b', "machineCasingTier2", 'c', "circuitTier0");
		TTrRecipeHandler.addShapedRecipe(new ItemStack(TTrIBF.brick, 1, 2),
				"ppp",
				"cbc",
				"ppp", 'p', "plateSteel", 'b', "machineCasingTier4", 'c', "circuitTier2");
		TTrRecipeHandler.addShapedRecipe(new ItemStack(TTrIBF.brick, 1, 3),
				"ppp",
				"cbc",
				"ppp", 'p', "plateChromium", 'b', "machineCasingTier5", 'c', "circuitTier4");
		
		Object[] machinecasingInputs = {"plateBrass", "plateBronze", "plateIron", "plateAluminium", "plateSteel", "plateStainlessSteel", "plateTitanium", "plateTungstensteel"};
		for(int i = 0; i < machinecasingInputs.length; ++i)
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.casing, 1, i), "xxx", "xwx", "xxx", 'x', machinecasingInputs[i], 'w', EnumTools.wrench.orename()));
		}
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.casing, 1, 8), "pxp", "omo", "pxp", 'p', "plateStainlessSteel", 'x', "plateAdvancedCarbon", 'o', "plateAdvancedAlloy", 'm', "machineCasingTier4"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.casing, 1, 8), "pop", "xmx", "pop", 'p', "plateStainlessSteel", 'x', "plateAdvancedCarbon", 'o', "plateAdvancedAlloy", 'm', "machineCasingTier4"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.casing, 1, 9), "121", "2m2", "121", '2', "plateTitanium", '1', "plateChromium", 'm', "machineCasingTier6"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.casing, 1, 10), "ppp", "php", "bbb", 'p', "plateBronze", 'h', EnumTools.hammer.orename(), 'b', Blocks.BRICK_BLOCK));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.casing, 1, 11), "ppp", "php", "bbb", 'p', "plateSteel", 'h', EnumTools.hammer.orename(), 'b', Blocks.BRICK_BLOCK));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.furnace, 1, 0),
				"xxx",
				"x x",
				"xxx",
				'x', "cobblestone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.furnace, 1, 1),
				"xxx",
				"x x",
				"xxx",
				'x', Blocks.BRICK_BLOCK));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.furnace, 1, 2),
				"xxx",
				"x x",
				"xxx",
				'x', Blocks.OBSIDIAN));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.forge, 1, 0),
				"bbb",
				"fff",
				"hcw",
				'b', Blocks.BRICK_BLOCK, 'f', new ItemStack(TTrIBF.furnace, 1, 1),
				'h', EnumTools.hammer.orename(), 'w', EnumTools.wrench.orename(),
				'c', "blockCopper"));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.cauldron, 1, 0),
				"x x",
				"xhx",
				"xxx",
				'x', "plateIron", 'h', EnumTools.hammer.orename()));
		
		for(EnumMaterial material : EnumMaterial.getMaterials())
		{
			if(material != null && material.tankCapacity > 0)
			{
				ItemStack stack = new ItemStack(TTrIBF.tank, 1, 1);
				stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setShort("material", material.id);
				TTrRecipeHandler.addShapedRecipe(stack, "xgx", "gwg", "xgx", 'x', EnumOrePrefix.stickLong.getDictName(material), 'g', "blockGlass", 'w', EnumTools.wrench.orename());
				stack.setItemDamage(0);
				TTrRecipeHandler.addShapedRecipe(stack, "xgx", "gwg", "xpx", 'p', EnumOrePrefix.plate.getDictName(material), 'x', EnumOrePrefix.stickLong.getDictName(material), 'g', "blockGlass", 'w', EnumTools.wrench.orename());
				stack.setItemDamage(2);
				TTrRecipeHandler.addShapedRecipe(stack, "xpx", "gwg", "xgx", 'p', EnumOrePrefix.plate.getDictName(material), 'x', EnumOrePrefix.stickLong.getDictName(material), 'g', "blockGlass", 'w', EnumTools.wrench.orename());
			}
		}
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.boiler1, 1, 0),
				"xxx",
				"xwx",
				"bcb",
				'x', "plateBronze", 'w', EnumTools.wrench.orename(), 'b', Blocks.BRICK_BLOCK,
				'b', Blocks.BRICK_BLOCK, 'c', "craftingMachineCauldron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.boiler1, 1, 1),
				"xxx",
				"xwx",
				"bcb",
				'x', "plateInvar", 'w', EnumTools.wrench.orename(),
				'b', Blocks.BRICK_BLOCK, 'c', "craftingMachineCauldron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.boiler1, 1, 2),
				"xxx",
				"xwx",
				"bcb",
				'x', "plateSteel", 'w', EnumTools.wrench.orename(),
				'b', Blocks.BRICK_BLOCK, 'c', "craftingMachineCauldron"));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.steamMachine1a, 1, 0),
				"pwp",
				"pfp",
				"bmb",
				'p', "plateBronze", 'w', EnumTools.wrench.orename(), 'b', Blocks.BRICK_BLOCK,
				'f', "craftingMachineFurnace", 'm', "machineBrickCasingBronze"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.steamMachine1a, 1, 1),
				"wdh",
				"gmg",
				"pip",
				'g', "gearIron", 'd', "gemDiamond",
				'p', "plateBronze", 'w', EnumTools.wrench.orename(), 'h', EnumTools.hammer.orename(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier1"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.steamMachine1a, 1, 1),
				"wdh",
				"gmg",
				"pip",
				'g', "gearBronze", 'd', "gemDiamond",
				'p', "plateBronze", 'w', EnumTools.wrench.orename(), 'h', EnumTools.hammer.orename(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier1"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.steamMachine1a, 1, 2),
				"ppp",
				"iwi",
				"pmp",
				'p', "plateBronze", 'w', EnumTools.wrench.orename(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier1"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.steamMachine1a, 1, 3),
				"gig",
				"pwp",
				"pmp",
				'g', "gearIron", 'p', "plateBronze", 'w', EnumTools.wrench.orename(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier1"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.steamMachine1a, 1, 4),
				"pgp",
				"iwd",
				"pmp",
				'g', "gearIron",
				'd', "gemDiamond", 'p', "plateBronze", 'w', EnumTools.wrench.orename(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier1"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.steamMachine1a, 1, 4),
				"pgp",
				"iwd",
				"pmp",
				'g', "gearBronze",
				'd', "gemDiamond", 'p', "plateBronze", 'w', EnumTools.wrench.orename(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier1"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.steamMachine1a, 1, 5),
				"pip",
				"gwg",
				"pmp",
				'g', "gearIron", 'p', "plateBronze", 'w', EnumTools.wrench.orename(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier1"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.steamMachine1a, 1, 5),
				"pip",
				"gwg",
				"pmp",
				'g', "gearBronze", 'p', "plateBronze", 'w', EnumTools.wrench.orename(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier1"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.steamMachine1a, 1, 6),
				"ppp",
				"fwf",
				"bbb",
				'p', "plateBronze", 'w', EnumTools.wrench.orename(), 'b', Blocks.BRICK_BLOCK,
				'f', new ItemStack(TTrIBF.steamMachine1a, 1, 0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.steamMachine1a, 1, 7),
				"pwp",
				"gcg",
				"bfb",
				'c', "craftingMachineCauldron", 'g', "blockGlass",
				'p', "plateBronze", 'w', EnumTools.wrench.orename(), 'b', Blocks.BRICK_BLOCK,
				'f', new ItemStack(TTrIBF.steamMachine1a, 1, 0)));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.steamMachine1b, 1, 0),
				"pwp",
				"pfp",
				"bmb",
				'p', "plateSteel", 'w', EnumTools.wrench.orename(), 'b', Blocks.BRICK_BLOCK,
				'f', "craftingMachineFurnace", 'm', "machineBrickCasingSteel"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.steamMachine1b, 1, 1),
				"wdh",
				"gmg",
				"pip",
				'g', "gearSteel", 'd', "gemDiamond",
				'p', "plateSteel", 'w', EnumTools.wrench.orename(), 'h', EnumTools.hammer.orename(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier2"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.steamMachine1b, 1, 2),
				"ppp",
				"iwi",
				"pmp",
				'p', "plateSteel", 'w', EnumTools.wrench.orename(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier2"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.steamMachine1b, 1, 3),
				"gig",
				"pwp",
				"pmp",
				'g', "gearSteel", 'p', "plateSteel", 'w', EnumTools.wrench.orename(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier2"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.steamMachine1b, 1, 4),
				"pgp",
				"iwd",
				"pmp",
				'g', "gearSteel", 'd', "gemDiamond", 'p', "plateSteel", 'w', EnumTools.wrench.orename(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier2"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.steamMachine1b, 1, 5),
				"pip",
				"gwg",
				"pmp",
				'g', "gearSteel", 'p', "plateSteel", 'w', EnumTools.wrench.orename(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier2"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrIBF.steamMachine1b, 1, 6),
				"ppp",
				"fwf",
				"bbb",
				'p', "plateSteel", 'w', EnumTools.wrench.orename(), 'b', Blocks.BRICK_BLOCK,
				'f', new ItemStack(TTrIBF.steamMachine1b, 1, 0)));
		
		TTrRecipeHandler.addShapedRecipe(new ItemStack(TTrIBF.steamHatch, 1, 0),
				"pgp",
				"gwg",
				"pmp",
				'p', "plateBronze", 'w', EnumTools.wrench.orename(),
				'g', "gearBronze", 'm', "machineCasingTier1");
		TTrRecipeHandler.addShapedRecipe(new ItemStack(TTrIBF.steamHatch, 1, 1),
				"pgp",
				"gwg",
				"pmp",
				'p', "plateSteel", 'w', EnumTools.wrench.orename(),
				'g', "gearSteel", 'm', "machineCasingTier2");
		
		TTrRecipeHandler.addShapedRecipe(new ItemStack(TTrIBF.bronzeMulti, 1, 0),
				"pip",
				"iwi",
				"pip", 'p', "plateBronze", 'i', "craftingMachinePiston", 'w', EnumTools.wrench.orename());
		TTrRecipeHandler.addShapedRecipe(new ItemStack(TTrIBF.bronzeMulti, 1, 1),
				"pip",
				"iwi",
				"pip", 'p', "plateBronze", 'i', new ItemStack(TTrIBF.furnace, 1, 1), 'w', EnumTools.wrench.orename());
		
		TTrRecipeHandler.addShapedRecipe(IC2Items.getItem("crafting", "circuit"),
				"lll",
				"xpx",
				"lll",
				'l', "cableCopperInsulated", 'x', TTrIBF.sub.get("circuitChipBasicHandmade"), 'p', TTrIBF.sub.get("circuitBoardBasicHandmade"));
	}
}