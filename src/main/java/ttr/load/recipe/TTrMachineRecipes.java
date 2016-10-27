package ttr.load.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ttr.api.data.EnumToolType;
import ttr.load.TTrBlocks;

public class TTrMachineRecipes
{
	public static void init()
	{
		OreDictionary.registerOre("machineCasingTier1", new ItemStack(TTrBlocks.casing, 1, 0));
		OreDictionary.registerOre("machineCasingTier1", new ItemStack(TTrBlocks.casing, 1, 1));
		OreDictionary.registerOre("machineCasingTier2", new ItemStack(TTrBlocks.casing, 1, 2));
		OreDictionary.registerOre("machineCasingTier2", new ItemStack(TTrBlocks.casing, 1, 3));
		OreDictionary.registerOre("machineCasingTier2", new ItemStack(TTrBlocks.casing, 1, 4));
		OreDictionary.registerOre("machineCasingTier3", new ItemStack(TTrBlocks.casing, 1, 4));
		OreDictionary.registerOre("machineCasingTier3", new ItemStack(TTrBlocks.casing, 1, 5));
		OreDictionary.registerOre("machineCasingTier4", new ItemStack(TTrBlocks.casing, 1, 5));
		OreDictionary.registerOre("machineCasingTier4", new ItemStack(TTrBlocks.casing, 1, 6));
		OreDictionary.registerOre("machineCasingTier5", new ItemStack(TTrBlocks.casing, 1, 6));
		OreDictionary.registerOre("machineCasingTier5", new ItemStack(TTrBlocks.casing, 1, 7));
		OreDictionary.registerOre("machineCasingTier6", new ItemStack(TTrBlocks.casing, 1, 8));
		OreDictionary.registerOre("machineCasingTier7", new ItemStack(TTrBlocks.casing, 1, 9));
		OreDictionary.registerOre("machineBrickCasingBronze", new ItemStack(TTrBlocks.casing, 1, 10));
		OreDictionary.registerOre("machineBrickCasingSteel", new ItemStack(TTrBlocks.casing, 1, 11));
		OreDictionary.registerOre("craftingMachineFurnace", TTrBlocks.furnace);
		OreDictionary.registerOre("craftingMachineFurnace", Blocks.FURNACE);
		OreDictionary.registerOre("craftingMachineFurnace", Blocks.LIT_FURNACE);
		OreDictionary.registerOre("craftingMachineCauldron", Blocks.CAULDRON);
		OreDictionary.registerOre("craftingMachineCauldron", TTrBlocks.cauldron);
		OreDictionary.registerOre("craftingMachinePiston", Blocks.PISTON);
		OreDictionary.registerOre("craftingMachinePiston", Blocks.PISTON_EXTENSION);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.brick, 2, 0),
				"ppp",
				"bhb",
				"ppp", 'p', "plateBronze", 'b', Blocks.BRICK_BLOCK, 'h', EnumToolType.hard_hammer.ore()));
		
		Object[] machinecasingInputs = {"plateBrass", "plateBronze", "plateIron", "plateAluminium", "plateSteel", "plateStainlessSteel", "plateTitanium", "plateTungstensteel"};
		for(int i = 0; i < machinecasingInputs.length; ++i)
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.casing, 1, i), "xxx", "xwx", "xxx", 'x', machinecasingInputs[i], 'w', EnumToolType.wrench.ore()));
		}
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.casing, 1, 8), "pxp", "omo", "pxp", 'p', "plateStainlessSteel", 'x', "plateAdvancedCarbon", 'o', "plateAdvancedAlloy", 'm', "machineCasingTier4"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.casing, 1, 8), "pop", "xmx", "pop", 'p', "plateStainlessSteel", 'x', "plateAdvancedCarbon", 'o', "plateAdvancedAlloy", 'm', "machineCasingTier4"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.casing, 1, 9), "121", "2m2", "121", '2', "plateTitanium", '1', "plateChromium", 'm', "machineCasingTier6"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.casing, 1, 10), "ppp", "php", "bbb", 'p', "plateBronze", 'h', EnumToolType.hard_hammer.ore(), 'b', Blocks.BRICK_BLOCK));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.casing, 1, 11), "ppp", "php", "bbb", 'p', "plateSteel", 'h', EnumToolType.hard_hammer.ore(), 'b', Blocks.BRICK_BLOCK));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.furnace, 1, 0),
				"xxx",
				"x x",
				"xxx",
				'x', "cobblestone"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.furnace, 1, 1),
				"xxx",
				"x x",
				"xxx",
				'x', Blocks.BRICK_BLOCK));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.furnace, 1, 2),
				"xxx",
				"x x",
				"xxx",
				'x', Blocks.OBSIDIAN));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.forge, 1, 0),
				"bbb",
				"fff",
				"hcw",
				'b', Blocks.BRICK_BLOCK, 'f', new ItemStack(TTrBlocks.furnace, 1, 1),
				'h', EnumToolType.hard_hammer.ore(), 'w', EnumToolType.wrench.ore(),
				'c', "blockCopper"));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.cauldron, 1, 0),
				"x x",
				"xhx",
				"xxx",
				'x', "plateIron", 'h', EnumToolType.hard_hammer.ore()));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.boiler1, 1, 0),
				"xxx",
				"xwx",
				"bcb",
				'x', "plateBronze", 'w', EnumToolType.wrench.ore(), 'b', Blocks.BRICK_BLOCK,
				'b', Blocks.BRICK_BLOCK, 'c', "craftingMachineCauldron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.boiler1, 1, 1),
				"xxx",
				"xwx",
				"bcb",
				'x', "plateInvar", 'w', EnumToolType.wrench.ore(),
				'b', Blocks.BRICK_BLOCK, 'c', "craftingMachineCauldron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.boiler1, 1, 2),
				"xxx",
				"xwx",
				"bcb",
				'x', "plateSteel", 'w', EnumToolType.wrench.ore(),
				'b', Blocks.BRICK_BLOCK, 'c', "craftingMachineCauldron"));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.steamMachine1a, 1, 0),
				"pwp",
				"pfp",
				"bmb",
				'p', "plateBronze", 'w', EnumToolType.wrench.ore(), 'b', Blocks.BRICK_BLOCK,
				'f', "craftingMachineFurnace", 'm', "machineBrickCasingBronze"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.steamMachine1a, 1, 1),
				"wdh",
				"gmg",
				"pip",
				'g', "gearIron", 'd', "gemDiamond",
				'p', "plateBronze", 'w', EnumToolType.wrench.ore(), 'h', EnumToolType.hard_hammer.ore(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier1"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.steamMachine1a, 1, 1),
				"wdh",
				"gmg",
				"pip",
				'g', "gearBronze", 'd', "gemDiamond",
				'p', "plateBronze", 'w', EnumToolType.wrench.ore(), 'h', EnumToolType.hard_hammer.ore(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier1"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.steamMachine1a, 1, 2),
				"ppp",
				"iwi",
				"pmp",
				'p', "plateBronze", 'w', EnumToolType.wrench.ore(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier1"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.steamMachine1a, 1, 3),
				"gig",
				"pwp",
				"pmp",
				'g', "gearIron", 'p', "plateBronze", 'w', EnumToolType.wrench.ore(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier1"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.steamMachine1a, 1, 4),
				"pgp",
				"iwd",
				"pmp",
				'g', "gearIron",
				'd', "gemDiamond", 'p', "plateBronze", 'w', EnumToolType.wrench.ore(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier1"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.steamMachine1a, 1, 4),
				"pgp",
				"iwd",
				"pmp",
				'g', "gearBronze",
				'd', "gemDiamond", 'p', "plateBronze", 'w', EnumToolType.wrench.ore(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier1"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.steamMachine1a, 1, 5),
				"pip",
				"gwg",
				"pmp",
				'g', "gearIron", 'p', "plateBronze", 'w', EnumToolType.wrench.ore(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier1"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.steamMachine1a, 1, 5),
				"pip",
				"gwg",
				"pmp",
				'g', "gearBronze", 'p', "plateBronze", 'w', EnumToolType.wrench.ore(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier1"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.steamMachine1a, 1, 6),
				"ppp",
				"fwf",
				"bbb",
				'p', "plateBronze", 'w', EnumToolType.wrench.ore(), 'b', Blocks.BRICK_BLOCK,
				'f', new ItemStack(TTrBlocks.steamMachine1a, 1, 0)));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.steamMachine1b, 1, 0),
				"pwp",
				"pfp",
				"bmb",
				'p', "plateSteel", 'w', EnumToolType.wrench.ore(),
				'f', "craftingMachineFurnace", 'm', "machineBrickCasingSteel"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.steamMachine1b, 1, 1),
				"wdh",
				"gmg",
				"pip",
				'g', "gearSteel", 'd', "gemDiamond",
				'p', "plateSteel", 'w', EnumToolType.wrench.ore(), 'h', EnumToolType.hard_hammer.ore(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier2"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.steamMachine1b, 1, 2),
				"ppp",
				"iwi",
				"pmp",
				'p', "plateSteel", 'w', EnumToolType.wrench.ore(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier2"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.steamMachine1b, 1, 3),
				"gig",
				"pwp",
				"pmp",
				'g', "gearSteel", 'p', "plateSteel", 'w', EnumToolType.wrench.ore(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier2"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.steamMachine1b, 1, 4),
				"pgp",
				"iwd",
				"pmp",
				'g', "gearSteel", 'd', "gemDiamond", 'p', "plateSteel", 'w', EnumToolType.wrench.ore(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier2"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.steamMachine1b, 1, 5),
				"pip",
				"gwg",
				"pmp",
				'g', "gearSteel", 'p', "plateSteel", 'w', EnumToolType.wrench.ore(),
				'i', "craftingMachinePiston", 'm', "machineCasingTier2"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.steamMachine1b, 1, 6),
				"ppp",
				"fwf",
				"bbb",
				'p', "plateSteel", 'w', EnumToolType.wrench.ore(), 'b', Blocks.BRICK_BLOCK,
				'f', new ItemStack(TTrBlocks.steamMachine1b, 1, 0)));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.steamHatch, 1, 0),
				"pgp",
				"gwg",
				"pmp",
				'p', "plateBronze", 'w', EnumToolType.wrench.ore(),
				'g', "gearBronze", 'm', "machineCasingTier1"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.steamHatch, 1, 1),
				"pgp",
				"gwg",
				"pmp",
				'p', "plateSteel", 'w', EnumToolType.wrench.ore(),
				'g', "gearSteel", 'm', "machineCasingTier2"));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TTrBlocks.bronzeMulti, 1, 0),
				"pip",
				"iwi",
				"pip", 'p', "plateBronze", 'i', "craftingMachinePiston", 'w', EnumToolType.wrench.ore()));
	}
}