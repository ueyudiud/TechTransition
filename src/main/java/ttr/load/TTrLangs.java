package ttr.load;

import com.mojang.realmsclient.gui.ChatFormatting;

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
	
	public static final String eleGrinder = "inventory.grinder.ele";
	public static final String eleCompressor = "inventory.compressor.ele";
	public static final String eleExtractor = "inventory.extractor.ele";
	public static final String eleAlloySmelter = "inventory.alloy.smelter.ele";
	public static final String eleWiremill = "inventory.wiremill.ele";
	public static final String elePressor = "inventory.pressor.ele";
	public static final String eleAssembler = "inventory.assembler.ele";
	public static final String eleBender = "inventory.bender.ele";
	public static final String eleCanner = "inventory.canner.ele";
	public static final String eleCutter = "inventory.cutter.ele";
	public static final String eleFurnace = "inventory.furnace.ele";
	public static final String eleLathe = "inventory.lathe.ele";
	public static final String eleOreWasher = "inventory.ore.washer.ele";
	public static final String elePyrolysisor = "inventory.pyrolysisor.ele";
	
	public static final String bronzeBlastFurnace = "inventory.bronze.blast.furnace";
	
	public static final String infoTurbineDamage = "info.turbine.damage";
	public static final String infoTurbineMaxOutput = "info.turbine.output";
	public static final String infoTurbineTransferLimit = "info.turbine.transfer";
	public static final String infoTurbineEfficiency = "info.turbine.efficiency";
	
	public static void init()
	{
		for(BlockMetal1.EnumType type : BlockMetal1.EnumType.values())
		{
			b(TTrIBF.metalblock1, type.ordinal(), type.localName());
		}
		
		b(TTrIBF.casing, 0, "Brass Machine Casing");
		b(TTrIBF.casing, 1, "Bronze Machine Casing");
		b(TTrIBF.casing, 2, "Iron Machine Casing");
		b(TTrIBF.casing, 3, "Aluminium Machine Casing");
		b(TTrIBF.casing, 4, "Steel Machine Casing");
		b(TTrIBF.casing, 5, "Stainless Steel Machine Casing");
		b(TTrIBF.casing, 6, "Titanium Machine Casing");
		b(TTrIBF.casing, 7, "Tungsten Steel Machine Casing");
		b(TTrIBF.casing, 8, "Advanced Machine Casing");
		b(TTrIBF.casing, 9, "Highly Advanced Machine Casing");
		b(TTrIBF.casing, 10, "Bronze Brick Casing");
		b(TTrIBF.casing, 11, "Steel Brick Casing");
		
		b(TTrIBF.forge, 0, "Forge");
		
		b(TTrIBF.furnace, 0, "Furnace");
		b(TTrIBF.cauldron, 0, "Cauldron");
		b(TTrIBF.forge, 0, "Forge");
		
		b(TTrIBF.boiler1, 0, "Bronze Coal Boiler");
		b(TTrIBF.boiler1, 1, "Invar Coal Boiler");
		b(TTrIBF.boiler1, 2, "Steel Coal Boiler");
		
		b(TTrIBF.steamMachine1a, 0, "Bronze Steam Furnace");
		b(TTrIBF.steamMachine1b, 0, "Steel Steam Furnace");
		b(TTrIBF.steamMachine1a, 1, "Bronze Steam Grinder");
		b(TTrIBF.steamMachine1b, 1, "Steel Steam Grinder");
		b(TTrIBF.steamMachine1a, 2, "Bronze Steam Extractor");
		b(TTrIBF.steamMachine1b, 2, "Steel Steam Extractor");
		b(TTrIBF.steamMachine1a, 3, "Bronze Steam Forge Hammer");
		b(TTrIBF.steamMachine1b, 3, "Steel Steam Forge Hammer");
		b(TTrIBF.steamMachine1a, 4, "Bronze Steam Cutter");
		b(TTrIBF.steamMachine1b, 4, "Steel Steam Cutter");
		b(TTrIBF.steamMachine1a, 5, "Bronze Steam Pressor");
		b(TTrIBF.steamMachine1b, 5, "Steel Steam Pressor");
		b(TTrIBF.steamMachine1a, 6, "Bronze Steam Alloy Furnace");
		b(TTrIBF.steamMachine1b, 6, "Steel Steam Alloy Furnace");
		b(TTrIBF.steamMachine1a, 7, "Bronze Steam Crystalizer");
		
		b(TTrIBF.steamHatch, 0, "Bronze Steam Input Hatch");
		b(TTrIBF.steamHatch, 1, "Steel Steam Input Hatch");
		
		b(TTrIBF.bronzeMulti, 0, "Bronze Compressor");
		b(TTrIBF.bronzeMulti, 1, "Bronze Blast Furnace");
		
		b(TTrIBF.electricMachine1, 0, "Electrical Grinder");
		b(TTrIBF.electricMachine1, 1, "Electrical Compressor");
		b(TTrIBF.electricMachine1, 2, "Electrical Extractor");
		b(TTrIBF.electricMachine1, 3, "Electrical Alloy Smelter");
		b(TTrIBF.electricMachine1, 4, "Electrical Wiremill");
		b(TTrIBF.electricMachine1, 5, "Electrical Pressor");
		b(TTrIBF.electricMachine1, 6, "Electrical Assembler");
		b(TTrIBF.electricMachine1, 7, "Electrical Bender");
		b(TTrIBF.electricMachine1, 8, "Electrical Canner");
		b(TTrIBF.electricMachine1, 9, "Electrical Cutter");
		b(TTrIBF.electricMachine1, 10, "Electrical Furnace");
		b(TTrIBF.electricMachine1, 11, "Electrical Lathe");
		b(TTrIBF.electricMachine1, 12, "Electrical Ore Washer");
		b(TTrIBF.electricMachine1, 13, "Electrical Pyrolysisor");
		
		b(TTrIBF.turbine, 0, "Small Steam Turbine");
		
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
		LanguageManager.registerLocal(bronzeBlastFurnace, "Bronze Blast Furnace");
		LanguageManager.registerLocal(steamCrystalizerBronze, "Bronze Crystalier");
		
		LanguageManager.registerLocal(eleGrinder, "Eletrical Grinder");
		LanguageManager.registerLocal(eleCompressor, "Eletrical Compressor");
		LanguageManager.registerLocal(eleExtractor, "Eletrical Extractor");
		LanguageManager.registerLocal(eleAlloySmelter, "Eletrical Alloy Smelter");
		LanguageManager.registerLocal(eleWiremill, "Eletrical Wiremill");
		LanguageManager.registerLocal(elePressor, "Eletrical Pressor");
		LanguageManager.registerLocal(eleAssembler, "Eletrical Assembler");
		LanguageManager.registerLocal(eleBender, "Eletrical Bender");
		LanguageManager.registerLocal(eleCanner, "Eletrical Canner");
		LanguageManager.registerLocal(eleCutter, "Eletrical Cutter");
		LanguageManager.registerLocal(eleFurnace, "Eletrical Furnace");
		LanguageManager.registerLocal(eleLathe, "Eletrical Lathe");
		LanguageManager.registerLocal(eleOreWasher, "Eletrical Ore Washer");
		LanguageManager.registerLocal(elePyrolysisor, "Eletrical Pyrolysisor");
		
		LanguageManager.registerLocal(infoTurbineDamage, "Damage : " + ChatFormatting.AQUA + "%d / %d");
		LanguageManager.registerLocal(infoTurbineMaxOutput, "Max Output : " + ChatFormatting.YELLOW + "%dJ");
		LanguageManager.registerLocal(infoTurbineEfficiency, "Efficiency : " + ChatFormatting.GREEN + "%g%%");
		LanguageManager.registerLocal(infoTurbineTransferLimit, "Transfer Limit : " + ChatFormatting.WHITE + "%dL/t");
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