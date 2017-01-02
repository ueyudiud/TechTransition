/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.load;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ic2.api.item.IC2Items;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.TTrAPI;
import ttr.api.block.ItemBlockExt;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;
import ttr.api.enums.EnumTools;
import ttr.api.enums.TextureSet;
import ttr.api.fluid.FluidTTr;
import ttr.api.item.ItemMulti;
import ttr.api.item.ItemTool;
import ttr.api.item.ToolStat;
import ttr.api.recipe.MaterialInstance;
import ttr.core.TTr;
import ttr.core.TTrMaterialHandler;
import ttr.core.block.BlockBoiler;
import ttr.core.block.BlockBrick;
import ttr.core.block.BlockFluidPipe1a;
import ttr.core.block.BlockFluidPipe1b;
import ttr.core.block.BlockFluidPipe1c;
import ttr.core.block.BlockForge;
import ttr.core.block.BlockMachine;
import ttr.core.block.BlockMachineCasing;
import ttr.core.block.BlockOre;
import ttr.core.block.BlockRock;
import ttr.core.block.machine.BlockBronzeMulti;
import ttr.core.block.machine.BlockElectricMachine1;
import ttr.core.block.machine.BlockSteamHatch;
import ttr.core.block.machine.BlockSteamMachine1a;
import ttr.core.block.machine.BlockSteamMachine1b;
import ttr.core.block.machine.BlockTank;
import ttr.core.block.machine.BlockTurbine;
import ttr.core.block.machine.cauldron.BlockCauldronExt;
import ttr.core.block.machine.furnace.BlockFurnaceExt;
import ttr.core.block.metal.BlockMetal1;
import ttr.core.item.ItemSubTTr;
import ttr.core.item.ItemTurbine;
import ttr.core.item.behavior.BehaviorHoe;
import ttr.core.item.behavior.BehaviorKnockbackApply;
import ttr.core.item.behavior.BehaviorWrench;
import ttr.core.item.stat.ToolSoftHammer;
import ttr.core.tile.TEOre;
import ttr.core.tile.boiler.TECoalBoiler;
import ttr.core.tile.electric.TEElectricalAlloySmelter;
import ttr.core.tile.electric.TEElectricalAssembler;
import ttr.core.tile.electric.TEElectricalBender;
import ttr.core.tile.electric.TEElectricalCanner;
import ttr.core.tile.electric.TEElectricalCompressor;
import ttr.core.tile.electric.TEElectricalCutter;
import ttr.core.tile.electric.TEElectricalExtractor;
import ttr.core.tile.electric.TEElectricalFurnace;
import ttr.core.tile.electric.TEElectricalGrinder;
import ttr.core.tile.electric.TEElectricalLathe;
import ttr.core.tile.electric.TEElectricalOreWasher;
import ttr.core.tile.electric.TEElectricalPressor;
import ttr.core.tile.electric.TEElectricalPyrolysisor;
import ttr.core.tile.electric.TEElectricalWiremill;
import ttr.core.tile.generator.TESmallSteamTurbine;
import ttr.core.tile.machine.TECauldron;
import ttr.core.tile.machine.TEForge;
import ttr.core.tile.machine.TETank;
import ttr.core.tile.machine.TETankBottom;
import ttr.core.tile.machine.TETankTop;
import ttr.core.tile.machine.furnace.TEFurnace;
import ttr.core.tile.machine.furnace.TEFurnaceBrick;
import ttr.core.tile.machine.furnace.TEFurnaceObsidian;
import ttr.core.tile.machine.steam.TEBronzeBlastFurnace;
import ttr.core.tile.machine.steam.TEBronzeCompressor;
import ttr.core.tile.machine.steam.TESteamAlloyFurnace.TESteamAlloyFurnaceBronze;
import ttr.core.tile.machine.steam.TESteamAlloyFurnace.TESteamAlloyFurnaceSteel;
import ttr.core.tile.machine.steam.TESteamCrystalizer.TESteamCrystalizerBronze;
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
import ttr.load.orereg.OreRegBolt;
import ttr.load.orereg.OreRegCrushedCentrifuged;
import ttr.load.orereg.OreRegCrushedPurified;
import ttr.load.orereg.OreRegDust;
import ttr.load.orereg.OreRegDustOre;
import ttr.load.orereg.OreRegDustOreTiny;
import ttr.load.orereg.OreRegDustPowder;
import ttr.load.orereg.OreRegDustSmall;
import ttr.load.orereg.OreRegDustTiny;
import ttr.load.orereg.OreRegGear;
import ttr.load.orereg.OreRegIngot;
import ttr.load.orereg.OreRegNugget;
import ttr.load.orereg.OreRegPlate;
import ttr.load.orereg.OreRegScrew;
import ttr.load.orereg.OreRegStick;
import ttr.load.orereg.OreRegStickLong;

/**
 * Created at 2016年12月20日 上午12:41:56
 * @author ueyudiud
 */
public class TTrIBF
{
	public static void init()
	{
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
		register(tank = new BlockTank().setRegistryName("tank").setUnlocalizedName("tank").setCreativeTab(CreativeTabs.DECORATIONS));
		GameRegistry.registerTileEntity(TETank.class, "Tank");
		GameRegistry.registerTileEntity(TETankTop.class, "TankTop");
		GameRegistry.registerTileEntity(TETankBottom.class, "TankBottom");
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
		registerMachineTile(TESteamCrystalizerBronze.class, "steam.crystalizer.bronze");
		register(steamHatch = new BlockSteamHatch().setRegistryName("steam.hatch").setUnlocalizedName("steam.hatch").setCreativeTab(CreativeTabs.DECORATIONS));
		TTrAPI.proxy.registerForgeModel(steamHatch, "steam_hatch", BlockSteamHatch.MACHINE_TYPE1);
		registerMachineTile(TESteamInputHatchBronze.class, "steam.ihatch.bronze");
		registerMachineTile(TESteamInputHatchSteel.class, "steam.ihatch.steel");
		register(bronzeMulti = new BlockBronzeMulti().setRegistryName("steam_bronze_multi").setUnlocalizedName("steam.bronze.multi").setCreativeTab(CreativeTabs.DECORATIONS));
		registerMachineTile(TEBronzeCompressor.class, "bronze.compressor");
		registerMachineTile(TEBronzeBlastFurnace.class, "bronze.blast");
		TTrAPI.proxy.registerForgeModel(bronzeMulti, "steam_multi", BlockBronzeMulti.MACHINE_TYPE);
		register(turbine = new BlockTurbine().setRegistryName("turbine").setUnlocalizedName("turbine").setCreativeTab(CreativeTabs.DECORATIONS));
		registerMachineTile(TESmallSteamTurbine.class, "steam.turbine");
		TTrAPI.proxy.registerForgeModel(turbine, "turbine", BlockTurbine.MACHINE_TYPE);
		register(electricMachine1 = new BlockElectricMachine1().setRegistryName("electric_machine1").setUnlocalizedName("electric.machine.1").setCreativeTab(CreativeTabs.DECORATIONS));
		registerMachineTile(TEElectricalGrinder.class, "electrical.grinder");
		registerMachineTile(TEElectricalCompressor.class, "electrical.compressor");
		registerMachineTile(TEElectricalExtractor.class, "electrical.extractor");
		registerMachineTile(TEElectricalAlloySmelter.class, "electrical.alloy.smelter");
		registerMachineTile(TEElectricalWiremill.class, "electrical.wiremill");
		registerMachineTile(TEElectricalPressor.class, "electrical.pressor");
		registerMachineTile(TEElectricalAssembler.class, "electrical.assembler");
		registerMachineTile(TEElectricalBender.class, "electrical.bender");
		registerMachineTile(TEElectricalCanner.class, "electrical.canner");
		registerMachineTile(TEElectricalCutter.class, "electrical.cutter");
		registerMachineTile(TEElectricalFurnace.class, "electrical.furnace");
		registerMachineTile(TEElectricalLathe.class, "electrical.lathe");
		registerMachineTile(TEElectricalOreWasher.class, "electrical.orewasher");
		registerMachineTile(TEElectricalPyrolysisor.class, "electrical.pyrolysisor");
		TTrAPI.proxy.registerForgeModel(electricMachine1, "electric_machine1", BlockElectricMachine1.MACHINE_TYPE1, BlockElectricMachine1.FACING_OUTPUT);
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
		
		ore = new BlockOre().setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		GameRegistry.registerTileEntity(TEOre.class, "ttr.ore");
		
		sub = new ItemSubTTr();
		turbine_item = new ItemTurbine();
		turbine_item.setCreativeTab(CreativeTabs.TOOLS);
		tool = new ItemTool("tool");
		tool.setCreativeTab(CreativeTabs.TOOLS);
		tool.addSubItem(1 , EnumTools.axe			, "Axe"			, new ToolStat(2L, 2L, 1L, -1L).setAttackProperty(3.0F, 0.8F, -1.5F));
		tool.addSubItem(2 , EnumTools.shovel		, "Shovel"		, new ToolStat(2L, 2L, 1L, -1L).setAttackProperty(2.0F, 0.6F, -2.5F));
		tool.addSubItem(3 , EnumTools.pickaxe		, "Pickaxe"		, new ToolStat(2L, 2L, 1L, -1L).setAttackProperty(2.0F, 0.7F, -3.0F));
		tool.addSubItem(4 , EnumTools.sword			, "Sword"		, new ToolStat(1L, 2L, 2L, -1L).setAttackProperty(4.0F, 1.0F, -2.0F));
		tool.addSubItem(5 , EnumTools.hoe			, "Hoe"			, new ToolStat(1L, 1L, 1L, -1L).setAttackProperty(0.0F, 0.2F, +1.0F), new BehaviorHoe());
		tool.addSubItem(6 , EnumTools.hammer		, "Hammer"		, new ToolStat(4L, 4L, 3L, -1L).setAttackProperty(3.5F, 0.8F, -2.5F));
		tool.addSubItem(7 , EnumTools.soft_hammer	, "SoftHammer"	, new ToolSoftHammer(), new BehaviorKnockbackApply());
		tool.addSubItem(8 , EnumTools.file			, "File"		, new ToolStat(1L, 2L, 2L, -1L).setAttackProperty(4.0F, 1.0F, -2.0F));
		tool.addSubItem(9 , EnumTools.wrench		, "Wrench"		, new ToolStat(3L, 3L, 4L, -1L).setAttackProperty(2.0F, 0.8F, -1.0F), new BehaviorWrench());
		tool.addSubItem(10, EnumTools.saw			, "Saw"			, new ToolStat(2L, 3L, 2L, -1L).setAttackProperty(2.5F, 0.9F, -1.5F));
		tool.addSubItem(11, EnumTools.screwdriver	, "Screwdriver"	, new ToolStat(1L, 2L, 3L, -1L).setAttackProperty(1.0F, 0.7F, -1.0F));
		tool.addSubItem(12, EnumTools.cutter		, "Cutter"		, new ToolStat(3L, 1L, 2L, -1L).setAttackProperty(1.0F, 0.7F, -1.5F));
		for(EnumOrePrefix prefix : EnumOrePrefix.getPrefixs())
		{
			if(prefix != null && prefix.hasItem && !ingoreItemMap.contains(prefix) && !itemMap.containsKey(prefix))
			{
				itemMap.put(prefix, new ItemMulti(prefix));
			}
		}
		
		distilledwater = FluidRegistry.getFluid("ic2distilled_water");
		steam = FluidRegistry.getFluid("steam");
		if(steam == null)
		{
			steam = new FluidTTr("steam").setDensity(-300).setViscosity(60).setTemperature(500).setGaseous(true);
		}
		lubricant = FluidRegistry.getFluid("lubricant");
		if(lubricant == null)
		{
			lubricant = new FluidTTr("lubricant").setDensity(800).setViscosity(1200);
		}
		sulphuric_acid = FluidRegistry.getFluid("sulfuricacid");
		if(sulphuric_acid == null)
		{
			sulphuric_acid = new FluidTTr("sulfuricacid").setDensity(1890).setViscosity(2100);
		}
		nitric_acid = FluidRegistry.getFluid("nitricacid");
		if(nitric_acid == null)
		{
			nitric_acid = new FluidTTr("nitricacid").setDensity(1420).setViscosity(1600);
		}
		ore_washed_water = FluidRegistry.getFluid("ore_washed_water");
		if(ore_washed_water == null)
		{
			ore_washed_water = new FluidTTr("ore_washed_water").setDensity(1200).setViscosity(1100).setTemperature(350);
		}
		
		marble = new BlockRock(EnumMaterial.Marble);
		
		if(FMLCommonHandler.instance().getSide().isClient())
		{
			MinecraftForge.EVENT_BUS.register(new TTrIBF());
		}
		
		TTrMaterialHandler.registerOreRegister(EnumOrePrefix.dust, new OreRegDust());
		TTrMaterialHandler.registerOreRegister(EnumOrePrefix.dustSmall, new OreRegDustSmall());
		TTrMaterialHandler.registerOreRegister(EnumOrePrefix.dustTiny, new OreRegDustTiny());
		TTrMaterialHandler.registerOreRegister(EnumOrePrefix.dustPowder, new OreRegDustPowder());
		TTrMaterialHandler.registerOreRegister(EnumOrePrefix.ingot, new OreRegIngot());
		TTrMaterialHandler.registerOreRegister(EnumOrePrefix.plate, new OreRegPlate());
		TTrMaterialHandler.registerOreRegister(EnumOrePrefix.stick, new OreRegStick());
		TTrMaterialHandler.registerOreRegister(EnumOrePrefix.stickLong, new OreRegStickLong());
		TTrMaterialHandler.registerOreRegister(EnumOrePrefix.nugget, new OreRegNugget());
		TTrMaterialHandler.registerOreRegister(EnumOrePrefix.gear, new OreRegGear());
		TTrMaterialHandler.registerOreRegister(EnumOrePrefix.dustOre, new OreRegDustOre());
		TTrMaterialHandler.registerOreRegister(EnumOrePrefix.dustOreTiny, new OreRegDustOreTiny());
		TTrMaterialHandler.registerOreRegister(EnumOrePrefix.crushedCentrifuged, new OreRegCrushedCentrifuged());
		TTrMaterialHandler.registerOreRegister(EnumOrePrefix.crushedPurified, new OreRegCrushedPurified());
		TTrMaterialHandler.registerOreRegister(EnumOrePrefix.bolt, new OreRegBolt());
		TTrMaterialHandler.registerOreRegister(EnumOrePrefix.screw, new OreRegScrew());
	}
	
	public static void postinit()
	{
		MaterialInstance.registerInstanceItem(EnumOrePrefix.ingot, EnumMaterial.Iron, new ItemStack(Items.IRON_INGOT));
		MaterialInstance.registerInstanceItem(EnumOrePrefix.ingot, EnumMaterial.Gold, new ItemStack(Items.GOLD_INGOT));
		MaterialInstance.registerInstanceItem(EnumOrePrefix.nugget, EnumMaterial.Gold, new ItemStack(Items.GOLD_NUGGET));
		MaterialInstance.registerInstanceItem(EnumOrePrefix.stick, EnumMaterial.Wood, new ItemStack(Items.STICK));
		MaterialInstance.registerInstanceItem(EnumOrePrefix.gem, EnumMaterial.Flint, new ItemStack(Items.FLINT), true);
		MaterialInstance.registerInstanceItem(EnumOrePrefix.gem, EnumMaterial.Coal, new ItemStack(Items.COAL, 1, 0), true);
		MaterialInstance.registerInstanceItem(EnumOrePrefix.gem, EnumMaterial.Charcoal, new ItemStack(Items.COAL, 1, 1), true);
		MaterialInstance.registerInstanceItem(EnumOrePrefix.ingot, EnumMaterial.Rubber, IC2Items.getItem("crafting", "rubber"), true);
		MaterialInstance.registerInstanceItem(EnumOrePrefix.gem, EnumMaterial.Iridium, IC2Items.getItem("misc_resource", "iridium_ore"), true);
		MaterialInstance.registerInstanceItem(EnumOrePrefix.gem, EnumMaterial.Diamond, new ItemStack(Items.DIAMOND));
		for(ItemMulti item : itemMap.values())
		{
			item.postinitalize();
		}
		
		((BlockOre) ore).postinit();
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRneder()
	{
		ItemMeshDefinition definition = (ItemStack stack) ->
		new ModelResourceLocation("ttr:tool", "type=" + tool.getName(stack));
		ModelLoader.setCustomMeshDefinition(tool, definition);
		for(String name : tool.getNames())
		{
			ModelLoader.registerItemVariants(tool, new ModelResourceLocation("ttr:tool", "type=" + name));
		}
		definition = (ItemStack stack) ->
		new ModelResourceLocation("ttr:tank", "type=" + BlockTank.EnumType.values()[stack.getItemDamage()]);
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(tank), definition);
		ModelLoader.registerItemVariants(Item.getItemFromBlock(tank), new ResourceLocation("ttr", "tank"));
		for(int i = 0; i < ((BlockOre) ore).itemList.length; ++i)
		{
			Item item = ((BlockOre) ore).itemList[i];
			final int j = i;
			definition = stack -> new ModelResourceLocation("ttr:ore", String.format("rock=%s,type=%s", BlockOre.RockBase.values()[j].getName(), EnumMaterial.getMaterialNonNull(stack.getItemDamage()).textureSet.getLocation(EnumOrePrefix.ore)));
			ModelLoader.setCustomMeshDefinition(item, definition);
			ModelLoader.registerItemVariants(item, new ResourceLocation("ttr", "ore"));
		}
		for(ItemMulti item : itemMap.values())
		{
			definition = stack ->
			EnumMaterial.getMaterialNonNull(stack.getItemDamage()).textureSet.getModelLocation(item.getPrefix());
			ModelLoader.setCustomMeshDefinition(item, definition);
			for(TextureSet set : TextureSet.TEXTURE_SETS)
			{
				ModelLoader.registerItemVariants(item, set.getResources());
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerBlockColor(BlockColors colors)
	{
		colors.registerBlockColorHandler((state, worldIn, pos, tintIndex) ->
		{
			try
			{
				return tintIndex != 1 || worldIn == null || pos == null ? 0xFFFFFFFF : ((TETank) worldIn.getTileEntity(pos)).material.RGBa >> 8;
			}
			catch(Exception exception)
			{
				return 0xFFFFFFFF;
			}
		}, tank);
		colors.registerBlockColorHandler((state, worldIn, pos, tintIndex) ->
		{
			try
			{
				return tintIndex != 1 || worldIn == null || pos == null ? 0xFFFFFFFF : ((TEOre) worldIn.getTileEntity(pos)).material.RGBa >> 8;
			}
			catch(Exception exception)
			{
				return 0xFFFFFFFF;
			}
		}, ore);
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
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onIconMapReload(TextureStitchEvent.Pre event)
	{
		FluidTTr.registerTextures(event.getMap());
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerItemColor(ItemColors colors)
	{
		IItemColor color = (stack, tintIndex) ->
		tintIndex != 1 ? 0xFFFFFFFF : EnumMaterial.getMaterialNonNull(stack.getItemDamage()).RGBa >> 8;
		colors.registerItemColorHandler(color, TTrIBF.itemMap.values().toArray(new Item[0]));
		colors.registerItemColorHandler(color, ((BlockOre) ore).itemList);
		color = (stack, tintIndex) ->
		tintIndex != 1 || !stack.hasTagCompound() ? 0xFFFFFFFF : EnumMaterial.getMaterialNonNull(stack.getTagCompound().getShort("material")).RGBa >> 8;
		colors.registerItemColorHandler(color, tank);
		color = (stack, tintIndex) ->
		tintIndex == 0 ? TTrIBF.tool.getMaterial(stack).RGBa >> 8 : tintIndex == 1 ? TTrIBF.tool.getMaterial(stack).toolHandle.RGBa >> 8 : 0xFFFFFFFF;
		colors.registerItemColorHandler(color, TTrIBF.tool);
	}
	
	public static void initSubItem(ItemSubTTr item)
	{
		item.addSubItem(201, "mouldEmpty", "Empty Mould", "mouldEmpty");
		item.addSubItem(202, "mouldPlate", "Plate Mould", "mouldPlate");
		item.addSubItem(203, "mouldCasing", "Casing Mould", "mouldCasing");
		item.addSubItem(204, "mouldGear", "Gear Mould", "mouldGear");
		
		item.addSubItem(701, "circuitPartBasic", "Basic Circuit Part", "circuitPartTier0");
		item.addSubItem(721, "circuitBoardBasic", "Basic Circuit Board", "circuitBoardTier0");
		item.addSubItem(731, "circuitBoardBasicHandmade", "Handmade Basic Circuit Board");
		item.addSubItem(741, "circuitChipBasic", "Basic Logical Chip", "circuitChipTier0");
		item.addSubItem(751, "circuitChipBasicHandmade", "Handmade Basic Logical Chip");
		item.addSubItem(761, "circuitBasic", "Basic Circuit", "circuitTier0");
	}
	
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
	public static Block turbine;
	public static Block electricMachine1;
	public static Block ore;
	public static Block tank;
	
	private static List<EnumOrePrefix> ingoreItemMap = new ArrayList();
	public static Map<EnumOrePrefix, ItemMulti> itemMap = new HashMap();
	public static ItemTool tool;
	public static ItemTurbine turbine_item;
	public static ItemSubTTr sub;
	
	public static Fluid distilledwater;
	public static Fluid ore_washed_water;
	public static Fluid steam;
	public static Fluid sulphuric_acid;
	public static Fluid nitric_acid;
	public static Fluid lubricant;
}