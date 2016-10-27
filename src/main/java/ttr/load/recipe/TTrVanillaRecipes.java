package ttr.load.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ttr.api.data.EnumToolType;
import ttr.core.block.metal.BlockMetal1;
import ttr.load.TTrBlocks;

public class TTrVanillaRecipes
{
	public static void init()
	{
		for(BlockMetal1.EnumType type : BlockMetal1.EnumType.values())
		{
			OreDictionary.registerOre(type.oreName(), new ItemStack(TTrBlocks.metalblock1, 1, type.ordinal()));
		}

		GameRegistry.addRecipe(new ShapedOreRecipe(Blocks.PISTON, "xxx", "cic", "crc", 'x', "plankWood", 'c', "cobblestone", 'i', "ingotBronze", 'r', "dustRedstone"));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.CHEST), "xxx", "xsx", "xxx", 'x', "plankWood", 's', EnumToolType.saw.ore()));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.STICK, 2), "x", "x", 'x', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.STICK, 4), "s", "x", "x", 'x', "stickWood", 's', EnumToolType.saw.ore()));
	}
}