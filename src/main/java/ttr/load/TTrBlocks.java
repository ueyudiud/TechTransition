package ttr.load;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ttr.api.TTrAPI;
import ttr.api.block.ItemBlockExt;
import ttr.api.data.M;
import ttr.core.TTr;
import ttr.core.block.BlockBoiler;
import ttr.core.block.BlockBrick;
import ttr.core.block.BlockFluidPipe1a;
import ttr.core.block.BlockFluidPipe1b;
import ttr.core.block.BlockFluidPipe1c;
import ttr.core.block.BlockForge;
import ttr.core.block.BlockMachine;
import ttr.core.block.BlockMachineCasing;
import ttr.core.block.BlockRock;
import ttr.core.block.machine.BlockBronzeMulti;
import ttr.core.block.machine.BlockSteamHatch;
import ttr.core.block.machine.BlockSteamMachine1a;
import ttr.core.block.machine.BlockSteamMachine1b;
import ttr.core.block.machine.cauldron.BlockCauldronExt;
import ttr.core.block.machine.furnace.BlockFurnaceExt;
import ttr.core.block.metal.BlockMetal1;
import ttr.core.tile.boiler.TECoalBoiler;
import ttr.core.tile.machine.TECauldron;
import ttr.core.tile.machine.TEForge;
import ttr.core.tile.machine.furnace.TEFurnace;
import ttr.core.tile.machine.furnace.TEFurnaceBrick;
import ttr.core.tile.machine.furnace.TEFurnaceObsidian;
import ttr.core.tile.machine.steam.TEBronzeCompressor;
import ttr.core.tile.machine.steam.TESteamAlloyFurnace.TESteamAlloyFurnaceBronze;
import ttr.core.tile.machine.steam.TESteamAlloyFurnace.TESteamAlloyFurnaceSteel;
import ttr.core.tile.machine.steam.TESteamCutter.TESteamCutterBronze;
import ttr.core.tile.machine.steam.TESteamCutter.TESteamCutterSteel;
import ttr.core.tile.machine.steam.TESteamExtractor.TESteamExtractorBronze;
import ttr.core.tile.machine.steam.TESteamExtractor.TESteamExtractorSteel;
import ttr.core.tile.machine.steam.TESteamForgeHammer.TESteamForgeHammerBronze;
import ttr.core.tile.machine.steam.TESteamForgeHammer.TESteamForgeHammerSteel;
import ttr.core.tile.machine.steam.TESteamFurnace.TESteamFurnaceBronze;
import ttr.core.tile.machine.steam.TESteamFurnace.TESteamFurnaceSteel;
import ttr.core.tile.machine.steam.TESteamGrinder.TESteamGrinderBronze;
import ttr.core.tile.machine.steam.TESteamGrinder.TESteamGrinderSteel;
import ttr.core.tile.machine.steam.TESteamInputHatch.TESteamInputHatchBronze;
import ttr.core.tile.machine.steam.TESteamInputHatch.TESteamInputHatchSteel;
import ttr.core.tile.machine.steam.TESteamPressor.TESteamPressorBronze;
import ttr.core.tile.machine.steam.TESteamPressor.TESteamPressorSteel;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeBronzeBig;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeBronzeMiddle;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeBronzeSmall;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeCopperBig;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeCopperMiddle;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeCopperSmall;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeSteelBig;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeSteelMiddle;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeSteelSmall;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeTungstenBig;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeTungstenMiddle;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeTungstenSmall;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeStainlessSteelBig;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeStainlessSteelMiddle;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeStainlessSteelSmall;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeTitaniumBig;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeTitaniumMiddle;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeTitaniumSmall;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeTungstenSteelBig;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeTungstenSteelMiddle;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeTungstenSteelSmall;

public class TTrBlocks
{
	public static Block marble;
	
	public static Block metalblock1;

	public static Block brick;
	public static Block casing;

	public static Block furnace;
	public static Block cauldron;
	public static Block forge;
	
	public static Block pipeFluid1a;
	public static Block pipeFluid1b;
	public static Block pipeFluid1c;

	public static Block boiler1;
	
	public static Block steamHatch;
	public static Block steamMachine1a;
	public static Block steamMachine1b;
	public static Block bronzeMulti;
	
	public static void init()
	{
		marble = new BlockRock(M.marble);
		
		register(metalblock1 = new BlockMetal1().setRegistryName("metal1").setUnlocalizedName("metal.1").setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
		TTrAPI.proxy.registerForgeModel(metalblock1, "metala", BlockMetal1.TYPE, false);
		
		register(brick = new BlockBrick().setRegistryName("brick").setUnlocalizedName("brick").setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
		TTrAPI.proxy.registerForgeModel(brick, "brick", BlockBrick.BRICK_TYPE, false);
		register(casing = new BlockMachineCasing().setRegistryName("casing").setUnlocalizedName("casing").setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
		registerItemModel(casing, 0, "ttr", "casing/brass");
		registerItemModel(casing, 1, "ttr", "casing/bronze");
		registerItemModel(casing, 2, "ttr", "casing/iron");
		registerItemModel(casing, 3, "ttr", "casing/aluminium");
		registerItemModel(casing, 4, "ttr", "casing/steel");
		registerItemModel(casing, 5, "ttr", "casing/stainless_steel");
		registerItemModel(casing, 6, "ttr", "casing/titanium");
		registerItemModel(casing, 7, "ttr", "casing/tungstensteel");
		registerItemModel(casing, 8, "ttr", "casing/advanced");
		registerItemModel(casing, 9, "ttr", "casing/highly_advanced");
		registerItemModel(casing, 10, "ttr", "casing/bronze_brick");
		registerItemModel(casing, 11, "ttr", "casing/steel_brick");
		register(furnace = new BlockFurnaceExt().setRegistryName("furnace").setUnlocalizedName("furnace.ext").setCreativeTab(CreativeTabs.DECORATIONS));
		GameRegistry.registerTileEntity(TEFurnace.class, "furnace.stone.simple");
		GameRegistry.registerTileEntity(TEFurnaceBrick.class, "furnace.brick.simple");
		GameRegistry.registerTileEntity(TEFurnaceObsidian.class, "furnace.obsidian.simple");
		registerItemModel(furnace, 0, "minecraft", "furnace");
		registerItemModel(furnace, 1, "ttr", "furnace/brick");
		registerItemModel(furnace, 2, "ttr", "furnace/obsidian");
		register(cauldron = new BlockCauldronExt().setRegistryName("cauldron").setUnlocalizedName("cauldron.ext").setCreativeTab(CreativeTabs.DECORATIONS));
		GameRegistry.registerTileEntity(TECauldron.class, "cauldron.simple");
		registerItemModel(cauldron, 0, "minecraft", "cauldron");
		register(forge = new BlockForge().setRegistryName("forge").setUnlocalizedName("forge").setCreativeTab(CreativeTabs.DECORATIONS));
		GameRegistry.registerTileEntity(TEForge.class, "forge");
		TTrAPI.proxy.registerForgeModel(forge, "forge", null, false);
		register(boiler1 = new BlockBoiler().setRegistryName("boiler.1").setUnlocalizedName("boiler.1").setCreativeTab(CreativeTabs.DECORATIONS));
		TTrAPI.proxy.registerForgeModel(boiler1, "boiler1", BlockBoiler.BOILER_TYPE);
		registerMachineTile(TECoalBoiler.TEBronzeCoalBoiler.class, "boilder.simple.bronze");
		registerMachineTile(TECoalBoiler.TEInvarCoalBoiler.class, "boilder.simple.invar");
		registerMachineTile(TECoalBoiler.TESteelCoalBoiler.class, "boilder.simple.steel");
		register(steamMachine1a = new BlockSteamMachine1a().setRegistryName("steam.machine.1a").setUnlocalizedName("steam.machine.1a").setCreativeTab(CreativeTabs.DECORATIONS));
		register(steamMachine1b = new BlockSteamMachine1b().setRegistryName("steam.machine.1b").setUnlocalizedName("steam.machine.1b").setCreativeTab(CreativeTabs.DECORATIONS));
		TTrAPI.proxy.registerForgeModel(steamMachine1a, "steam_bronze", BlockSteamMachine1a.MACHINE_TYPE1, BlockMachine.FACING_OUTPUT);
		TTrAPI.proxy.registerForgeModel(steamMachine1b, "steam_steel", BlockSteamMachine1a.MACHINE_TYPE1, BlockMachine.FACING_OUTPUT);
		registerMachineTile(TESteamGrinderBronze.class, "steam.grinder.bronze");
		registerMachineTile(TESteamGrinderSteel.class, "steam.grinder.steel");
		registerMachineTile(TESteamExtractorBronze.class, "steam.extractor.bronze");
		registerMachineTile(TESteamExtractorSteel.class, "steam.extractor.steel");
		registerMachineTile(TESteamFurnaceBronze.class, "steam.furnace.bronze");
		registerMachineTile(TESteamFurnaceSteel.class, "steam.furnace.steel");
		registerMachineTile(TESteamAlloyFurnaceBronze.class, "steam.alloyfurnace.bronze");
		registerMachineTile(TESteamAlloyFurnaceSteel.class, "steam.alloyfurnace.steel");
		registerMachineTile(TESteamCutterBronze.class, "steam.cutter.bronze");
		registerMachineTile(TESteamCutterSteel.class, "steam.cutter.steel");
		registerMachineTile(TESteamForgeHammerBronze.class, "steam.forgehammer.bronze");
		registerMachineTile(TESteamForgeHammerSteel.class, "steam.forgehammer.steel");
		registerMachineTile(TESteamPressorBronze.class, "steam.pressor.bronze");
		registerMachineTile(TESteamPressorSteel.class, "steam.pressor.steel");
		register(steamHatch = new BlockSteamHatch().setRegistryName("steam.hatch").setUnlocalizedName("steam.hatch").setCreativeTab(CreativeTabs.DECORATIONS));
		TTrAPI.proxy.registerForgeModel(steamHatch, "steam_hatch", BlockSteamHatch.MACHINE_TYPE1);
		registerMachineTile(TESteamInputHatchBronze.class, "steam.ihatch.bronze");
		registerMachineTile(TESteamInputHatchSteel.class, "steam.ihatch.steel");
		register(bronzeMulti = new BlockBronzeMulti().setRegistryName("steam_bronze_multi").setUnlocalizedName("steam.bronze.multi").setCreativeTab(CreativeTabs.DECORATIONS));
		registerMachineTile(TEBronzeCompressor.class, "bronze.compressor");
		TTrAPI.proxy.registerForgeModel(bronzeMulti, "steam_multi", BlockBronzeMulti.MACHINE_TYPE);
		register(pipeFluid1a = new BlockFluidPipe1a().setRegistryName("pipe.fluid.1a").setUnlocalizedName("pipe.fluid.1a").setCreativeTab(CreativeTabs.TRANSPORTATION));
		register(pipeFluid1b = new BlockFluidPipe1b().setRegistryName("pipe.fluid.1b").setUnlocalizedName("pipe.fluid.1b").setCreativeTab(CreativeTabs.TRANSPORTATION));
		register(pipeFluid1c = new BlockFluidPipe1c().setRegistryName("pipe.fluid.1c").setUnlocalizedName("pipe.fluid.1c").setCreativeTab(CreativeTabs.TRANSPORTATION));
		GameRegistry.registerTileEntity(TEFluidPipeCopperSmall.class, "pipe.fluid.copper.small");
		GameRegistry.registerTileEntity(TEFluidPipeCopperMiddle.class, "pipe.fluid.copper.middle");
		GameRegistry.registerTileEntity(TEFluidPipeCopperBig.class, "pipe.fluid.copper.big");
		GameRegistry.registerTileEntity(TEFluidPipeBronzeSmall.class, "pipe.fluid.bronze.small");
		GameRegistry.registerTileEntity(TEFluidPipeBronzeMiddle.class, "pipe.fluid.bronze.middle");
		GameRegistry.registerTileEntity(TEFluidPipeBronzeBig.class, "pipe.fluid.bronze.big");
		GameRegistry.registerTileEntity(TEFluidPipeSteelSmall.class, "pipe.fluid.steel.small");
		GameRegistry.registerTileEntity(TEFluidPipeSteelMiddle.class, "pipe.fluid.steel.middle");
		GameRegistry.registerTileEntity(TEFluidPipeSteelBig.class, "pipe.fluid.steel.big");
		GameRegistry.registerTileEntity(TEFluidPipeStainlessSteelSmall.class, "pipe.fluid.stainlesssteel.small");
		GameRegistry.registerTileEntity(TEFluidPipeStainlessSteelMiddle.class, "pipe.fluid.stainlesssteel.middle");
		GameRegistry.registerTileEntity(TEFluidPipeStainlessSteelBig.class, "pipe.fluid.stainlesssteel.big");
		GameRegistry.registerTileEntity(TEFluidPipeTitaniumSmall.class, "pipe.fluid.titanium.small");
		GameRegistry.registerTileEntity(TEFluidPipeTitaniumMiddle.class, "pipe.fluid.titanium.middle");
		GameRegistry.registerTileEntity(TEFluidPipeTitaniumBig.class, "pipe.fluid.titanium.big");
		GameRegistry.registerTileEntity(TEFluidPipeTungstenSmall.class, "pipe.fluid.tungsten.small");
		GameRegistry.registerTileEntity(TEFluidPipeTungstenMiddle.class, "pipe.fluid.tungsten.middle");
		GameRegistry.registerTileEntity(TEFluidPipeTungstenBig.class, "pipe.fluid.tungsten.big");
		GameRegistry.registerTileEntity(TEFluidPipeTungstenSteelSmall.class, "pipe.fluid.tungstensteel.small");
		GameRegistry.registerTileEntity(TEFluidPipeTungstenSteelMiddle.class, "pipe.fluid.tungstensteel.middle");
		GameRegistry.registerTileEntity(TEFluidPipeTungstenSteelBig.class, "pipe.fluid.tungstensteel.big");
	}
	
	private static void registerMachineTile(Class<? extends TileEntity> tileEntityClass, String name)
	{
		GameRegistry.registerTileEntity(tileEntityClass, name);
		TTr.proxy.registerMachineRender(tileEntityClass);
	}

	private static void register(Block block)
	{
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlockExt(block).setRegistryName(block.getRegistryName()));
	}

	private static void registerItemModel(Block block, int meta, String modid, String locate)
	{
		TTrAPI.proxy.registerItemModel(Item.getItemFromBlock(block), meta, modid, locate);
	}
}