package ttr.load;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import ttr.api.util.LanguageManager;
import ttr.core.block.metal.BlockMetal1;

public class TTrLangs
{
	public static final String forge = "inventory.forge";

	public static final String boilerCoalBronze = "inventory.boiler.coal.bronze";
	public static final String boilerCoalInvar = "inventory.boiler.coal.invar";
	public static final String boilerCoalSteel = "inventory.boiler.coal.steel";

	public static final String steamExtractorBronze = "inventory.extractor.bronze";
	public static final String steamExtractorSteel = "inventory.extractor.steel";
	public static final String steamForgeHammerBronze = "inventory.forgehammer.bronze";
	public static final String steamForgeHammerSteel = "inventory.forgehammer.steel";
	public static final String steamGrinderBronze = "inventory.grinder.bronze";
	public static final String steamGrinderSteel = "inventory.grinder.steel";
	public static final String steamPressorBronze = "inventory.pressor.bronze";
	public static final String steamPressorSteel = "inventory.pressor.steel";
	public static final String steamCutterBronze = "inventory.cutter.bronze";
	public static final String steamCutterSteel = "inventory.cutter.steel";
	public static final String steamAlloyFurnaceBronze = "inventory.alloyfurnace.bronze";
	public static final String steamAlloyFurnaceSteel = "inventory.alloyfurnace.steel";
	public static final String steamFurnaceBronze = "inventory.furnace.bronze";
	public static final String steamFurnaceSteel = "inventory.furnace.steel";
	public static final String steamCompressor = "inventory.compressor";
	public static final String steamCrystalizerBronze = "inventory.crystailzer.bronze";
	
	public static void init()
	{
		for(BlockMetal1.EnumType type : BlockMetal1.EnumType.values())
		{
			b(TTrBlocks.metalblock1, type.ordinal(), type.localName());
		}

		b(TTrBlocks.casing, 0, "Brass Machine Casing");
		b(TTrBlocks.casing, 1, "Bronze Machine Casing");
		b(TTrBlocks.casing, 2, "Iron Machine Casing");
		b(TTrBlocks.casing, 3, "Aluminium Machine Casing");
		b(TTrBlocks.casing, 4, "Steel Machine Casing");
		b(TTrBlocks.casing, 5, "Stainless Steel Machine Casing");
		b(TTrBlocks.casing, 6, "Titanium Machine Casing");
		b(TTrBlocks.casing, 7, "Tungsten Steel Machine Casing");
		b(TTrBlocks.casing, 8, "Advanced Machine Casing");
		b(TTrBlocks.casing, 9, "Highly Advanced Machine Casing");
		b(TTrBlocks.casing, 10, "Bronze Brick Casing");
		b(TTrBlocks.casing, 11, "Steel Brick Casing");
		
		b(TTrBlocks.forge, 0, "Forge");

		b(TTrBlocks.furnace, 0, "Furnace");
		b(TTrBlocks.cauldron, 0, "Cauldron");
		b(TTrBlocks.forge, 0, "Forge");
		
		b(TTrBlocks.boiler1, 0, "Bronze Coal Boiler");
		b(TTrBlocks.boiler1, 1, "Invar Coal Boiler");
		b(TTrBlocks.boiler1, 2, "Steel Coal Boiler");
		
		b(TTrBlocks.steamMachine1a, 0, "Bronze Steam Furnace");
		b(TTrBlocks.steamMachine1b, 0, "Steel Steam Furnace");
		b(TTrBlocks.steamMachine1a, 1, "Bronze Steam Grinder");
		b(TTrBlocks.steamMachine1b, 1, "Steel Steam Grinder");
		b(TTrBlocks.steamMachine1a, 2, "Bronze Steam Extractor");
		b(TTrBlocks.steamMachine1b, 2, "Steel Steam Extractor");
		b(TTrBlocks.steamMachine1a, 3, "Bronze Steam Forge Hammer");
		b(TTrBlocks.steamMachine1b, 3, "Steel Steam Forge Hammer");
		b(TTrBlocks.steamMachine1a, 4, "Bronze Steam Cutter");
		b(TTrBlocks.steamMachine1b, 4, "Steel Steam Cutter");
		b(TTrBlocks.steamMachine1a, 5, "Bronze Steam Pressor");
		b(TTrBlocks.steamMachine1b, 5, "Steel Steam Pressor");
		b(TTrBlocks.steamMachine1a, 6, "Bronze Steam Alloy Furnace");
		b(TTrBlocks.steamMachine1b, 6, "Steel Steam Alloy Furnace");
		b(TTrBlocks.steamMachine1a, 7, "Bronze Steam Crystalizer");
		
		b(TTrBlocks.steamHatch, 0, "Bronze Steam Input Hatch");
		b(TTrBlocks.steamHatch, 1, "Steel Steam Input Hatch");

		b(TTrBlocks.bronzeMulti, 0, "Bronze Compressor");
		
		LanguageManager.registerLocal(forge, "Forge");
		
		LanguageManager.registerLocal(boilerCoalBronze, "Bronze Coal Boiler");
		LanguageManager.registerLocal(boilerCoalInvar, "Invar Coal Boiler");
		LanguageManager.registerLocal(boilerCoalSteel, "Steel Coal Boiler");
		
		LanguageManager.registerLocal(steamExtractorBronze, "Bronze Steam Extractor");
		LanguageManager.registerLocal(steamExtractorSteel, "Steel Steam Extractor");
		LanguageManager.registerLocal(steamForgeHammerBronze, "Bronze Steam Forge Hammer");
		LanguageManager.registerLocal(steamForgeHammerSteel, "Steel Steam Forge Hammer");
		LanguageManager.registerLocal(steamGrinderBronze, "Bronze Steam Grinder");
		LanguageManager.registerLocal(steamGrinderSteel, "Steel Steam Grinder");
		LanguageManager.registerLocal(steamPressorBronze, "Bronze Steam Pressor");
		LanguageManager.registerLocal(steamPressorSteel, "Steel Steam Pressor");
		LanguageManager.registerLocal(steamCutterBronze, "Bronze Steam Cutter");
		LanguageManager.registerLocal(steamCutterSteel, "Steel Steam Cutter");
		LanguageManager.registerLocal(steamAlloyFurnaceBronze, "Bronze Steam Alloy Furnace");
		LanguageManager.registerLocal(steamAlloyFurnaceSteel, "Steel Steam Alloy Furnace");
		LanguageManager.registerLocal(steamFurnaceBronze, "Bronze Steam Furnace");
		LanguageManager.registerLocal(steamFurnaceSteel, "Steel Steam Furnace");
		LanguageManager.registerLocal(steamCompressor, "Bronze Compressor");
		LanguageManager.registerLocal(steamCrystalizerBronze, "Bronze Crystalier");
	}
	
	private static void b(Block block, int meta, String localized)
	{
		b(new ItemStack(block, 1, meta), localized);
	}
	
	private static void b(ItemStack stack, String localized)
	{
		LanguageManager.registerLocal(stack.getUnlocalizedName() + ".name", localized);
	}
}