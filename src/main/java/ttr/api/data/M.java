package ttr.api.data;

import ttr.api.material.Mat;
import ttr.api.util.SubTag;

public class M
{
	public static final Mat VOID = new Mat(-1, false, "ttr", "void", "Void", "VOID", "?");
	
	public static final Mat carbon = new Mat(61, "ttr", "carbon", "Carbon", "Carbon", "C").setPoint(3720, 4485).setFuelInfo(1000, 200, 1.2F).setRGBa(0x030303).setTag(SubTag.NONMETAL);
	public static final Mat carbon14 = new Mat(62, "ttr", "carbon14", "Carbon14", "Carbon 14", "C-14").setPoint(3720, 3720).setFuelInfo(1000, 200, 1.2F).setRGBa(0x030303).setTag(SubTag.NONMETAL);
	public static final Mat magnesium = new Mat(121, "ttr", "magnesium", "Magnesium", "Magnesium", "Mg").setSaltable(0xFFFFFF22, SubTag.SOLUTABLE_ALL).setPoint(836, 836).setFuelInfo(1900, 800, 0.2F).setTag(SubTag.METAL);
	public static final Mat aluminium = new Mat(131, "ttr", "aluminium", "Aluminium", "Aluminium", "Al").setSaltable(0xFFFFFF22, SubTag.SOLUTABLE_ALL).setPoint(637, 637).setBlockable("pickaxe", 1, 2.4F, 12.0F).setTag(SubTag.METAL);
	public static final Mat sulfur = new Mat(161, "ttr", "sulfur", "Sulfur", "Sulfur", "S").setPoint(372, 372).setFuelInfo(700, 160, 0.8F).setTag(SubTag.NONMETAL, SubTag.ORE_NONMETAL);
	public static final Mat titanium = new Mat(221, "ttr", "titanium", "Titanium", "Titanium", "Ti").setPoint(1273, 2739).setToolable(2, 637, 6.5F, 0F, 3.0F, 0.5F, 8).setBlockable("pickaxe", 2, 4.5F, 23.0F).setTag(SubTag.METAL);
	public static final Mat vanadium = new Mat(231, "ttr", "vanadium", "Vanadium", "Vanadium", "V").setPoint(1471, 2873).setBlockable("pickaxe", 2, 6.0F, 19.0F).setTag(SubTag.METAL);
	public static final Mat chromium = new Mat(241, "ttr", "chromium", "Chromium", "Chromium", "Cr").setPoint(1463, 2248).setBlockable("pickaxe", 3, 9.0F, 48.0F).setTag(SubTag.METAL);
	public static final Mat manganese = new Mat(251, "ttr", "manganese", "Manganese", "Manganese", "Mn").setPoint(1382, 2930).setBlockable("pickaxe", 2, 1.9F, 9.0F).setTag(SubTag.METAL);
	public static final Mat iron = new Mat(261, "ttr", "iron", "Iron", "Iron", "Fe").setSaltable(0x56C12C22, SubTag.SOLUTABLE_ALL).setPoint(1182, 1829).setBlockable("pickaxe", 2, 5.0F, 10.0F).setToolable(2, 372, 6.0F, 0F, 4.0F, 0.2F, 7).setTag(SubTag.METAL);
	public static final Mat nickel = new Mat(271, "ttr", "nickel", "Nickel", "Nickel", "Ni").setSaltable(0x27AD4F22, SubTag.SOLUTABLE_ALL).setBlockable("pickaxe", 1, 4.8F, 9.0F).setPoint(1093, 2103);
	public static final Mat copper = new Mat(291, "ttr", "copper", "Copper", "Copper", "Cu").setSaltable(0x536BF422, SubTag.SOLUTABLE_ALL).setPoint(938, 3028).setBlockable("pickaxe", 1, 3.6F, 8.0F).setTag(SubTag.METAL);
	public static final Mat zinc = new Mat(301, "ttr", "zinc", "Zinc", "Zinc", "Zn").setSaltable(0xFFFFFF22, SubTag.SOLUTABLE_ALL).setPoint(720, 2531).setBlockable("pickaxe", 1, 3.2F, 8.5F).setTag(SubTag.METAL);
	public static final Mat gallium = new Mat(311, "ttr", "gallium", "Gallium", "Gallium", "Ga").setPoint(2017, 3948).setTag(SubTag.METAL);
	public static final Mat arsenium = new Mat(331, "ttr", "arsenium", "Arsenium", "Arsenium", "As").setPoint(2183, 4038).setTag(SubTag.NONMETAL);
	public static final Mat niobium = new Mat(411, "ttr", "niobium", "Niobium", "Niobium", "Nb").setPoint(1938, 4271).setTag(SubTag.METAL);
	public static final Mat silver = new Mat(471, "ttr", "silver", "Silver", "Silver", "Ag").setSaltable(0x6E74EF22, SubTag.SOLUTABLE_ALL).setPoint(1028, 3824).setBlockable("pickaxe", 1, 3.8F, 9.6F).setTag(SubTag.METAL);
	public static final Mat tin = new Mat(501, "ttr", "tin", "Tin", "Tin", "Sn").setPoint(628, 2783).setTag(SubTag.METAL);
	public static final Mat antimony = new Mat(511, "ttr", "antimony", "Antimony", "Antimony", "Sb").setPoint(738, 3017).setTag(SubTag.METAL);
	public static final Mat tungsten = new Mat(741, "ttr", "tungsten", "Tungsten", "Tungsten", "W").setPoint(3192, 5820).setBlockable("pickaxe", 2, 6.5F, 11.2F).setTag(SubTag.METAL);
	public static final Mat osmium = new Mat(761, "ttr", "osmium", "Osmium", "Osmium", "Os").setPoint(2738, 4672).setBlockable("pickaxe", 3, 11.5F, 60.0F).setTag(SubTag.METAL);
	public static final Mat iridium = new Mat(771, "ttr", "iridium", "Iridium", "Iridium", "Ir").setPoint(2518, 4428).setBlockable("pickaxe", 3, 9.5F, 36.0F).setTag(SubTag.METAL);
	public static final Mat platinum = new Mat(781, "ttr", "platinum", "Platinum", "Platinum", "Pt").setPoint(2471, 4251).setBlockable("pickaxe", 2, 4.8F, 12.0F).setTag(SubTag.METAL);
	public static final Mat gold = new Mat(791, "ttr", "gold", "Gold", "Gold", "Au").setPoint(1427, 3827).setBlockable("pickaxe", 1, 3.0F, 10.0F).setTag(SubTag.METAL);
	public static final Mat lead = new Mat(821, "ttr", "lead", "Lead", "Lead", "Pb").setPoint(627, 2714).setBlockable("pickaxe", 1, 2.0F, 8.0F).setTag(SubTag.METAL);
	public static final Mat thorium = new Mat(901, "ttr", "thorium", "Thorium", "Thorium", "Th").setPoint(1028, 3748).setTag(SubTag.METAL, SubTag.RADIOACTIVITY);
	public static final Mat uranium = new Mat(921, "ttr", "uranium", "Uranium", "Uranium", "U").setPoint(2173, 4837).setTag(SubTag.METAL, SubTag.RADIOACTIVITY);
	public static final Mat uranium235 = new Mat(922, "ttr", "uranium235", "Uranium235", "Uranium 235", "U-235").setPoint(2172, 4837).setTag(SubTag.METAL, SubTag.RADIOACTIVITY);
	public static final Mat uranium238 = new Mat(923, "ttr", "uranium238", "Uranium238", "Uranium 238", "U-238").setPoint(2177, 4837).setTag(SubTag.METAL, SubTag.RADIOACTIVITY);
	public static final Mat plutonium = new Mat(941, "ttr", "plutonium", "Plutonium", "Plutonium", "Pu").setPoint(2372, 3723).setTag(SubTag.METAL, SubTag.RADIOACTIVITY);
	public static final Mat plutonium239 = new Mat(942, "ttr", "plutonium239", "Plutonium239", "Plutonium 239", "Pu-239").setPoint(2371, 3722).setTag(SubTag.METAL, SubTag.RADIOACTIVITY);
	public static final Mat plutonium244 = new Mat(943, "ttr", "plutonium244", "Plutonium244", "Plutonium 244", "Pu-244").setPoint(2378, 3725).setTag(SubTag.METAL, SubTag.RADIOACTIVITY);

	public static final Mat salt = new Mat(3001, "ttr", "salt", "Salt", "Salt", "NaCl").setTag(SubTag.ORE_SALT);
	public static final Mat soda = new Mat(3002, "ttr", "soda", "Soda", "Soda", "Na2CO3");
	public static final Mat trona = new Mat(3003, "ttr", "trona", "Trona", "Trona", "Na2CO3").setTag(SubTag.ORE_SALT);
	public static final Mat saltpeter = new Mat(3004, "ttr", "saltpeter", "Saltpeter", "Saltpeter", "KNO3").setTag(SubTag.ORE_SALT);
	
	public static final Mat fluorapatite = new Mat(3101, "ttr", "fluorapatite", "Fluorapatite", "Fluorapatite", "Ca5(PO4)3").setTag(SubTag.ORE_SIMPLE);
	
	public static final Mat ilmenite = new Mat(3201, "ttr", "ilmenite", "Ilmenite", "Ilmenite", "FeTiO3").setTag(SubTag.ORE_SIMPLE);
	public static final Mat magnetite = new Mat(3202, "ttr", "magnetite", "Magnetite", "Magnetite", "Fe3O4").setTag(SubTag.ORE_SIMPLE);
	public static final Mat pyrite = new Mat(3203, "ttr", "pyrite", "Pyrite", "Pyrite", "FeS2").setTag(SubTag.ORE_SIMPLE);
	public static final Mat halobolite = new Mat(3204, "ttr", "halobolite", "Halobolite", "Halobolite", "[Mn,Fe,Ni,Cu,Co]O").setTag(SubTag.ORE_SIMPLE);
	public static final Mat sphalerite = new Mat(3205, "ttr", "sphalerite", "Sphalerite", "Sphalerite", "ZnS").setTag(SubTag.ORE_SIMPLE);
	public static final Mat cinnabar = new Mat(3206, "ttr", "cinnabar", "Cinnabar", "Cinnabar", "HgS").setTag(SubTag.ORE_SIMPLE);
	public static final Mat galena = new Mat(3207, "ttr", "galena", "Galena", "Galena", "PbS").setTag(SubTag.ORE_SIMPLE);
	public static final Mat limonite = new Mat(3208, "ttr", "limonite", "Limonite", "Limonite", "FeO(OH)").setTag(SubTag.ORE_SIMPLE);
	
	public static final Mat bauxite = new Mat(3301, "ttr", "bauxite", "Bauxite", "Bauxite", "Al2O3").setTag(SubTag.ORE_SIMPLE);
	public static final Mat realgar = new Mat(3302, "ttr", "realgar", "Realgar", "Realgar", "As4S4").setTag(SubTag.ORE_SIMPLE);
	public static final Mat orpiment = new Mat(3303, "ttr", "orpiment", "Orpiment", "Orpiment", "As4S6").setTag(SubTag.ORE_SIMPLE);
	
	public static final Mat diamond = new Mat(4001, "ttr", "diamond", "Diamond", "Diamond", "C128").setTag(SubTag.ORE_CRYSTAL, SubTag.GEM);
	public static final Mat emerald = new Mat(4002, "ttr", "emerald", "Emerald", "Emerald", "Be3Al2Si6O18").setTag(SubTag.ORE_CRYSTAL, SubTag.GEM);
	public static final Mat ruby = new Mat(4003, "ttr", "ruby", "Ruby", "Ruby", "(Al2O3)15Cr").setTag(SubTag.ORE_CRYSTAL, SubTag.GEM);
	public static final Mat sapphire = new Mat(4004, "ttr", "sapphire", "Sapphire", "Sapphire", "(Al2O3)15Ti").setTag(SubTag.ORE_CRYSTAL, SubTag.GEM);
	public static final Mat redGarnet = new Mat(4005, "ttr", "redGarnet", "GarnetRed", "Red Garnet", "[Mg,Fe,Mn]3Al2(SiO4)3").setTag(SubTag.ORE_CRYSTAL, SubTag.GEM);
	public static final Mat yellowGarnet = new Mat(4006, "ttr", "yellowGarnet", "GarnetYellow", "Yellow Garnet", "Ca3[Fe,Al,Cr]2(SiO4)3").setTag(SubTag.ORE_CRYSTAL, SubTag.GEM);
	public static final Mat lapis = new Mat(4007, "ttr", "lapis", "Lapis", "Lapis", "(Al6Si6Ca8Na8)12(Al3Si3Na4Cl)2FeS2CaCO3").setTag(SubTag.ORE_CRYSTAL, SubTag.GEM);
	public static final Mat sodalite = new Mat(4008, "ttr", "sodalite", "Sodalite", "Sodalite", "Al3Si3Na4Cl").setTag(SubTag.GEM);
	public static final Mat lazurite = new Mat(4009, "ttr", "lazurite", "Lazurite", "Lazurite", "Al6Si6Ca8Na8").setTag(SubTag.GEM);
	public static final Mat monazite = new Mat(4010, "ttr", "monazite", "Monazite", "Monazite", "Ce2La(PO4)2").setTag(SubTag.ORE_CRYSTAL, SubTag.GEM);
	public static final Mat coal = new Mat(4011, "ttr", "coal", "Coal", "Coal", "C[N,O,S]").setTag(SubTag.ORE_CRYSTAL, SubTag.GEM);
	public static final Mat redstone = new Mat(4012, "ttr", "redstone", "Redstone", "Redstone", "?");
	
	public static final Mat bronze = new Mat(5001, "ttr", "bronze", "Bronze", "Bronze", "Cu4SnPb").setToolable(2, 298, 5.0F, 0F, 3.0F, 0.1F, 9).setBlockable("pickaxe", 1, 4.5F, 9.6F).setTag(SubTag.METAL, SubTag.ALLOY);
	public static final Mat brass = new Mat(5002, "ttr", "brass", "Brass", "Brass", "Cu3Zn").setBlockable("pickaxe", 1, 4.2F, 9.2F).setTag(SubTag.METAL, SubTag.ALLOY);
	public static final Mat steel = new Mat(5003, "ttr", "steel", "Steel", "Steel", "Fe50C").setToolable(2, 418, 7.0F, 0F, 5.0F, 0.3F, 3).setBlockable("pickaxe", 1, 7.2F, 15.0F).setTag(SubTag.METAL, SubTag.ALLOY);
	public static final Mat pig_iron = new Mat(5004, "ttr", "pigIron", "PigIron", "Pig Iron", "Fe3C").setTag(SubTag.METAL, SubTag.ALLOY);
	public static final Mat electrum = new Mat(5005, "ttr", "electrum", "Electrum", "Electrum", "AuAg").setBlockable("pickaxe", 1, 3.2F, 9.0F).setTag(SubTag.METAL, SubTag.ALLOY);
	public static final Mat invar = new Mat(5006, "ttr", "invar", "Invar", "Invar", "Fe2Ni").setToolable(2, 390, 6.5F, 0F, 4.0F, -0.1F, 11).setBlockable("pickaxe", 1, 6.4F, 12.5F).setTag(SubTag.METAL, SubTag.ALLOY);
	public static final Mat cupronickel = new Mat(5007, "ttr", "cupronickel", "Cupronickel", "Cupronickel", "CuNi").setBlockable("pickaxe", 1, 4.5F, 9.3F).setTag(SubTag.METAL, SubTag.ALLOY);
	public static final Mat kanthal = new Mat(5008, "ttr", "kanthal", "Kanthal", "Kanthal", "FeAlCr").setTag(SubTag.METAL, SubTag.ALLOY);
	public static final Mat nichrome = new Mat(5009, "ttr", "nichrome", "Nichrome", "Nichrome", "Ni4Cr").setTag(SubTag.METAL, SubTag.ALLOY);
	public static final Mat stainlessSteel = new Mat(5010, "ttr", "stainlessSteel", "StainlessSteel", "Stainless Steel", "Fe6NiCrMn").setTag(SubTag.METAL, SubTag.ALLOY);
	public static final Mat magnalium = new Mat(5011, "ttr", "magnalium", "Magnalium", "Magnalium", "Al2Mg").setTag(SubTag.METAL, SubTag.ALLOY);
	public static final Mat yellowgold = new Mat(5012, "ttr", "yellowgold", "YellowGold", "Yellow Gold", "Au2Ag3Cu4").setTag(SubTag.ALLOY);
	public static final Mat tungstensteel = new Mat(5013, "ttr", "tungstensteel", "Tungstensteel", "Tungsten Steel", "Fe50CW").setBlockable("pickaxe", 3, 10.8F, 51.0F).setTag(SubTag.METAL, SubTag.ALLOY).setToolable(3, 4820, 9.0F, 0F, 4.0F, -0.8F, 11);
	public static final Mat redalloy = new Mat(5014, "ttr", "redalloy", "RedAlloy", "Red Alloy", "?").setTag(SubTag.ALLOY, SubTag.MONCRYSTAL);
	//	addSubItem(2006, "galliumArsenide", "Gallium Arsenide", "GalliumArsenide", "GaAs", "gallium_arsenide", new Sub());
	//	addSubItem(7001, "bronzeRough", "Rough Bronze", "RoughBronze", "Cu4SnPb", "bronze_rough", new Sub());

	public static final Mat stone = new Mat(6001, "ttr", "stone", "Stone", "Stone", "?").setBlockable("pickaxe", 0, 1.5F, 10.0F).setToolable(1, 131, 4.0F, 0.5F, 1.0F, -0.2F, 5).setTag(SubTag.ROCK);
	public static final Mat marble = new Mat(6007, "ttr", "marble", "Marble", "Marble", "CaCO3").setBlockable("pickaxe", 0, 0.8F, 5.0F).setTag(SubTag.ROCK);

	public static final Mat wood = new Mat(10001, "ttr", "wood", "Wood", "Wood", "?").setTag(SubTag.WOOD);
	//	addSubGem(10002, "moissaniteArtificial", "Artificial Moissanite", "Moissanite", "SiC", "moissanite_artificial", new Sub());
	//	addSubItem(10003, "rubberRaw", "Raw Rubber", "RubberRaw", "[C5H8]n", "raw_rubber", new Sub());
	public static final Mat ash = new Mat(10004, "ttr", "ash", "Ash", "Ash", "?");
	public static final Mat ashBlast = new Mat(10005, "ttr", "ashBlast", "BlastAsh", "Blast Ash", "?");
	public static final Mat ashBlastEnriched = new Mat(10006, "ttr", "ashBlastEnriched", "BlastAshEnriched", "Enriched Blast Ash", "?");
	//	addSubItem(10008, "netherrack", "Netherrack", "Netherrack", "nether", new Sub());
	//	addSubItem(10009, "endstone", "End Stone", "Endstone", "end", new Sub());
	//	addSubItem(10010, "clay", "Clay", "Clay", "clay", new Sub());
	//	addSubItem(10011, "enderPearl", "Ender Pearl", "EnderPearl", "ender_pearl", new Sub());
	//	addSubItem(10012, "enderEye", "Ender Eye", "EnderEye", "ender_eye", new Sub());
	//	addSubItem(10013, "calcite", "Calcite", "Calcite", "CaCO3", "calcite", new Sub());
	//	addSubItem(10014, "apatite", "Apatite", "Apatite", "Ca3(PO4)2", "apatite", new SubFertilizer(30));
	//	addSubItem(10015, "fluorite", "Fluorite", "Fluorite", "CaF2", "fluorite", new Sub());
	//	addSubItem(10016, "flint", "Flint", "Flint", "flint", new Sub());
	//	addSubItem(10017, "obsidian", "Obsidian", "Obsidian", "obsidian", new Sub());
	//	addSubItem(10018, "quickLime", "Quick Lime", "QuickLime", "CaO", "quick_lime", new Sub());
	//	addSubItem(11001, "ferrousSulfide", "Ferrous Sulfide", "FerrousSulfide", "FeS", "ferrous_sulfide", new Sub());
	//	addSubGem(11002, "copperSulphate", "Copper Sulphate", "CopperSulphate", "CuSO4", "copper_sulphate", new Sub());
	//	addSubGem(11003, "ferrousSulfate", "Ferrous Sulphate", "FerrousSulfate", "FeSO4", "ferrous_sulfate", new Sub());
	//	addSubItem(11004, "manganeseBioxide", "Manganese Bioxide", "ManganeseBioxide", "MnO2", "manganese_bioxide", new Sub());
	//	addSubItem(11005, "nickelousOxide", "Nickelous Oxide", "NickelousOxide", "NiO", "nickelous_oxide", new Sub());
	//	addSubGem(11006, "nickelousSulfate", "Nickelous Sulphate", "NickelousSulfate", "NiSO4", "nickelous_sulfate", new Sub());
	//	addSubItem(11007, "vanadicAnhydride", "Vanadic Anhydride", "VanadicAnhydride", "V2O5", "vanadic_anhydride", new Sub());
	//	addSubItem(11008, "titaniumDioxide", "Titanium White", "TitaniumDioxide", "TiO2", "titanium_dioxide", new Sub());
	//	addSubItem(11009, "aluminiumOxide", "Aluminium Oxide", "AluminiumOxide", "Al2O3", "aluminium_oxide", new Sub());
	//	addSubItem(11010, "ferricOxide", "Ferric Oxide", "FerricOxide", "Fe2O3", "ferric_oxide", new Sub());
	//	addSubItem(11011, "gesso", "Gesso", "Gesso", "CaSO4", "gesso", new Sub());
	//	addSubItem(11012, "magnesiumChloride", "Magnesium Chloride", "MagnesiumChloride", "MgCl2", "magnesium_chloride", new Sub());
	//	addSubItem(11013, "calciumChloride", "Calcium Chloride", "CalciumChloride", "CaCl2", "calcium_chloride", new Sub());
	//	addSubItem(11014, "siliconDioxide", "Silicon Dioxide", "SiliconDioxide", "SiO2", "silicondioxide", new Sub());
	//	addSubItem(11015, "chromiumTrioxide", "Chromium Trioxide", "ChromiumTrioxide", "CrO3", "chromiumtrioxide", new Sub());
	//	addSubItem(11016, "chromicOxide", "Chromic Oxide", "ChromicOxide", "Cr2O3", "chromicoxide", new Sub());
	//	addSubGem(11023, "almandine", "Almandine", "Almandine", "Fe3Al2(SiO4)3", "almandine", new Sub());
	//	addSubGem(11024, "pyrope", "Pyrope", "Pyrope", "Mg3Al2(SiO4)3", "pyrope", new Sub());
	//	addSubGem(11025, "spessartite", "Spessartite", "Spessartite", "Mn3Al2(SiO4)3", "spessartite", new Sub());
	//	addSubGem(11026, "andradite", "Andradite", "Andradite", "Ca3Fe2(SiO4)3", "andradite", new Sub());
	//	addSubGem(11027, "grossular", "Grossular", "Grossular", "Ca3Al2(SiO4)3", "grossular", new Sub());
	//	addSubGem(11028, "uvarovite", "Uvarovite", "Uvarovite", "Ca3Cr2(SiO4)3", "uvarovite", new Sub());
	//	addSubGem(11029, "spodumene", "Spodumene", "Spodumene", "Li2(AlSi2O8)2", "spodumene", new Sub());
	//	addSubItem(11301, "platinumGroup", "Platinum Group", "PlatinumGroup", "Pt6Ir2Os", "platinum_group", new Sub());
	//	addSubItem(11302, "ironGroup", "Iron Group", "IronGroup", "Fe5Ni3Ti", "iron_group", new Sub());
	//	addSubItem(11303, "ironGroupOxidized", "Oxidized Iron Group", "IronGroupOxidized", "[Fe,Ni,Ti]O", "iron_group_oxide", new Sub());
	
	static
	{
		lapis.byproduct1 = pyrite;
		lapis.byproduct2 = lazurite;
		lapis.byproduct3 = sodalite;
		sphalerite.byproduct1 = zinc;
		salt.byproduct1 = soda;
		redGarnet.byproduct1 = yellowGarnet;
		yellowGarnet.byproduct1 = redGarnet;
		orpiment.byproduct1 = realgar;
		realgar.byproduct1 = orpiment;
		galena.byproduct1 = silver;
		tungstensteel.handleMaterial = steel;
		steel.handleMaterial = iron;
		titanium.handleMaterial = iron;
		iron.handleMaterial = wood;
		bronze.handleMaterial = wood;
		invar.handleMaterial = wood;
		stone.handleMaterial = wood;
		diamond.handleMaterial = iron;
		ruby.handleMaterial = wood;
		sapphire.handleMaterial = wood;
	}
	
	public static void init(){	}
}