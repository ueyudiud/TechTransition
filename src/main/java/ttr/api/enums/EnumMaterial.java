/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.api.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fluids.Fluid;
import ttr.api.util.ISubTagContainer;
import ttr.api.util.MaterialStack;
import ttr.api.util.SubTag;

/**
 * Created at 2016年12月19日 下午11:52:13
 * @author ueyudiud
 */
public class EnumMaterial implements ISubTagContainer
{
	private static final EnumMaterial[] MATERIALS = new EnumMaterial[Short.MAX_VALUE];
	private static final Map<String, EnumMaterial> MATERIAL_MAP = new HashMap();
	
	public static final EnumMaterial
	_NULL 				= new EnumMaterial(-1  , TextureSet.NONE     , 0|0|0|0| 0| 0| 0|  0|  0|  0|   0, "null", "NULL", "Null", 0xFFFFFFFF),
	
	Neutron				= new EnumMaterial(1   , TextureSet.DULL     ,     4     |32                    , EnumElement.Nt, 0xFFFFFFFF),
	HydrogenRadicals	= new EnumMaterial(11  , TextureSet.NONE     , 0                                , EnumElement.H , 0x0000FFFF),
	MetallicHydrogen	= new EnumMaterial(12  , TextureSet.SHINY    , 1  |4                            , "MetallicHydrogen", "MetallicHydrogen", "Metallic Hydrogen", 0x9999FFFF),
	Hydrogen			= new EnumMaterial(13  , TextureSet.FLUIDY1  ,            32                    , "Hydrogen", "Hydrogen", "Hydrogen", 0x0000FF),
	Helium				= new EnumMaterial(21  , TextureSet.FLUIDY2  ,            32                    , EnumElement.He, 0xC4C400FF),
	Helium_3			= new EnumMaterial(22  , TextureSet.FLUIDY1  ,            32                    , EnumElement.He_3, 0xFFFF00FF),
	Helium_4			= new EnumMaterial(23  , TextureSet.FLUIDY2  ,            32                    , EnumElement.He_4, 0xADAD08FF),
	Lithium				= new EnumMaterial(31  , TextureSet.METALLIC ,            32                    , EnumElement.Li, 0xA6C7FFFF),
	Beryllium			= new EnumMaterial(41  , TextureSet.DULL     , 1                                , EnumElement.Be, 0x055E00FF),
	Boron				= new EnumMaterial(51  , TextureSet.NONMETAL2, 1|2                              , EnumElement.B , 0x592C00FF),
	Carbon				= new EnumMaterial(61  , TextureSet.NONMETAL1, 1         |32                    , EnumElement.C , 0x070707FF),
	Diamond				= new EnumMaterial(63  , TextureSet.DIAMOND  , 1|2          |64                 , "Diamond", "Diamond", "Diamond", 0xA2F6E7FF),
	NitrogenRadicals	= new EnumMaterial(71  , TextureSet.NONE     , 0                                , EnumElement.N , 0x5983E5FF),
	Nitrogen			= new EnumMaterial(72  , TextureSet.FLUIDY2  ,            32                    , "Nitrogen", "Nitrogen", "Nitrogen", 0x5983E5FF),
	OxygenRadicals		= new EnumMaterial(81  , TextureSet.NONE     , 0                                , EnumElement.O , 0xAECDE8FF),
	Oxygen				= new EnumMaterial(82  , TextureSet.FLUIDY2  ,            32                    , "Oxygen", "Oxygen", "Oxygen", 0xAECDE8FF),
	Ozone				= new EnumMaterial(83  , TextureSet.FLUIDY1  ,            32                    , "Ozone", "Ozone", "Ozone", 0xEAD3D4FF),
	FluorineRadicals	= new EnumMaterial(91  , TextureSet.NONE     , 0                                , EnumElement.F , 0xEAE8C9FF),
	Fluorine			= new EnumMaterial(92  , TextureSet.FLUIDY2  ,            32                    , "Fluorine", "Fluorine", "Fluorine", 0xEAE8C9FF),
	Neon				= new EnumMaterial(101 , TextureSet.FLUIDY1  ,            32                    , EnumElement.Ne, 0xF13E11FF),
	Sodium				= new EnumMaterial(111 , TextureSet.METALLIC ,            32                    , EnumElement.Na, 0x344A9DFF),
	Magnesium			= new EnumMaterial(121 , TextureSet.METALLIC , 1  |4                            , EnumElement.Mg, 0xF4CDCDFF),
	Aluminium			= new EnumMaterial(131 , TextureSet.DULL     , 1  |4|8      |64                 , EnumElement.Al, 0xB6D1FFFF),
	Silicon				= new EnumMaterial(141 , TextureSet.NONMETAL4, 1|2          |64                 , EnumElement.Si, 0x303034FF),
	Phosphorus			= new EnumMaterial(151 , TextureSet.NONMETAL2, 1|2                              , EnumElement.P , 0x88525DFF),
	Sulfur				= new EnumMaterial(161 , TextureSet.NONMETAL1, 1|2          |64                 , EnumElement.S , 0xE4E000FF),
	ChlorineRadicals	= new EnumMaterial(171 , TextureSet.NONE     , 0                                , EnumElement.Cl, 0xA7BD58FF),
	Chlorine			= new EnumMaterial(172 , TextureSet.FLUIDY2  ,            32                    , "Chlorine", "Chlorine", "Chlorine", 0xA7BD58FF),
	Argon				= new EnumMaterial(181 , TextureSet.FLUIDY1  ,            32                    , EnumElement.Ar, 0x1AA3E9FF),
	Potassium			= new EnumMaterial(191 , TextureSet.METALLIC ,            32                    , EnumElement.K , 0x349D9DFF),
	Calcium				= new EnumMaterial(201 , TextureSet.METALLIC ,            32                    , EnumElement.Ca, 0xD34D5FFF),
	Scandium			= new EnumMaterial(211 , TextureSet.METALLIC , 1  |4                            , EnumElement.Sc, 0xF4EEE2FF),
	Titanium			= new EnumMaterial(221 , TextureSet.METALLIC , 1  |4|8      |64                 , EnumElement.Ti, 0xADB0E6FF),
	Vanadium			= new EnumMaterial(231 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.V , 0xCAC9CBFF),
	Chromium			= new EnumMaterial(241 , TextureSet.SHINY    , 1  |4|8      |64                 , EnumElement.Cr, 0xF9E2EEFF),
	Manganese			= new EnumMaterial(251 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Mn, 0x9A9A9AFF),
	Iron				= new EnumMaterial(261 , TextureSet.METALLIC , 1  |4|8      |64                 , EnumElement.Fe, 0xC6C6C6FF),
	Cobalt				= new EnumMaterial(271 , TextureSet.METALLIC , 1  |4|8      |64                 , EnumElement.Co, 0x4146C1FF),
	Nickel				= new EnumMaterial(281 , TextureSet.METALLIC , 1  |4|8      |64                 , EnumElement.Ni, 0xB5BDE0FF),
	Copper				= new EnumMaterial(291 , TextureSet.SHINY    , 1  |4|8      |64                 , EnumElement.Cu, 0xEF5023FF),
	Zinc				= new EnumMaterial(301 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Zn, 0xE4DEEBFF),
	Gallium				= new EnumMaterial(311 , TextureSet.DULL     , 1  |4                            , EnumElement.Ga, 0xC2C2C6FF),
	Germanium			= new EnumMaterial(321 , TextureSet.SHINY    , 1  |4                            , EnumElement.Ge, 0xD2D3CFFF),
	Arsenic				= new EnumMaterial(331 , TextureSet.NONMETAL2, 1|2                              , EnumElement.As, 0x555555FF),
	Selenium			= new EnumMaterial(341 , TextureSet.NONMETAL2, 1|2                              , EnumElement.Se, 0x9F9FA4FF),
	BromineRadicals		= new EnumMaterial(351 , TextureSet.NONE     , 0                                , EnumElement.Br, 0xA5563BFF),
	Bromine				= new EnumMaterial(352 , TextureSet.FLUIDY1  ,            32                    , "Bromine", "Bromine", "Bromine", 0xA5563BFF),
	Krypton				= new EnumMaterial(361 , TextureSet.FLUIDY1  ,            32                    , EnumElement.Kr, 0xCDCDCDFF),
	Rubidium			= new EnumMaterial(371 , TextureSet.METALLIC ,            32                    , EnumElement.Rb, 0xBE9A9AFF),
	Strontium			= new EnumMaterial(381 , TextureSet.METALLIC ,            32                    , EnumElement.Sr, 0xE2DBC0FF),
	Yttrium				= new EnumMaterial(391 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Y , 0xC5CEC5FF),
	Zirconium			= new EnumMaterial(401 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Zr, 0xEBEBEAFF),
	Niobium				= new EnumMaterial(411 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Nb, 0x808AB2FF),
	Molybdenum			= new EnumMaterial(421 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Mo, 0xA4A4A4FF),
	Technetium			= new EnumMaterial(431 , TextureSet.METALLIC , 1  |4                            , EnumElement.Tc, 0x81817EFF),
	Ruthenium			= new EnumMaterial(441 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Ru, 0xA7A7A4FF),
	Rhodium				= new EnumMaterial(451 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Rh, 0xF0E8E8FF),
	Palladium			= new EnumMaterial(461 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Pd, 0xECF7F8FF),
	Silver				= new EnumMaterial(471 , TextureSet.SHINY    , 1  |4        |64                 , EnumElement.Ag, 0xB3CFE5FF),
	Cadmium				= new EnumMaterial(481 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Cd, 0xCFCFCDFF),
	Indium				= new EnumMaterial(491 , TextureSet.DULL     , 1  |4        |64                 , EnumElement.In, 0xA0A0A1FF),
	Tin					= new EnumMaterial(501 , TextureSet.DULL     , 1  |4|8      |64                 , EnumElement.Sn, 0xE8E8E8FF),
	Antimony			= new EnumMaterial(511 , TextureSet.SHINY    , 1  |4        |64                 , EnumElement.Sb, 0x9299E4FF),
	Tellurium			= new EnumMaterial(521 , TextureSet.SHINY    , 1|2                              , EnumElement.Te, 0xC8CACDFF),
	IodineRadicals		= new EnumMaterial(531 , TextureSet.NONE     , 0                                , EnumElement.I , 0x524067FF),
	Iodine				= new EnumMaterial(532 , TextureSet.NONMETAL4, 1|2       |32                    , "Iodine", "Iodine", "Iodine" , 0x524067FF),
	Xenon				= new EnumMaterial(541 , TextureSet.FLUIDY1  ,            32                     , EnumElement.Xe, 0x11E4F1FF),
	Caesium				= new EnumMaterial(551 , TextureSet.METALLIC , 1  |4                            , EnumElement.Cs, 0xFFFFCFFF),
	Barium				= new EnumMaterial(561 , TextureSet.METALLIC , 1  |4                            , EnumElement.Ba, 0xC1B6A7FF),
	Lantanium			= new EnumMaterial(571 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.La, 0xEBEBE6FF),
	Cerium				= new EnumMaterial(581 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Ce, 0xB1ACA5FF),
	Praseodymium		= new EnumMaterial(591 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Pr, 0xCBCDC9FF),
	Neodymium			= new EnumMaterial(601 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Nd, 0xC6C8C7FF),
	Promethium			= new EnumMaterial(611 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Pm, 0x9895AAFF),
	Samarium			= new EnumMaterial(621 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Sm, 0xD7D7C9FF),
	Europium			= new EnumMaterial(631 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Eu, 0xC2AEC1FF),
	Gadolinium			= new EnumMaterial(641 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Gd, 0xD9DBD8FF),
	Terbium				= new EnumMaterial(651 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Tb, 0xE1E2E1FF),
	Dysprosium			= new EnumMaterial(661 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Dy, 0xC1C1B7FF),
	Holmium				= new EnumMaterial(671 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Ho, 0xE1E1E1FF),
	Erbium				= new EnumMaterial(681 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Er, 0xE6E2E3FF),
	Thulium				= new EnumMaterial(691 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Tm, 0xA2A2A1FF),
	Ytterbium			= new EnumMaterial(701 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Yb, 0xA7BDC5FF),
	Lutetium			= new EnumMaterial(711 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Lu, 0xE0E1DBFF),
	Hafnium				= new EnumMaterial(721 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Hf, 0xC4C6C5FF),
	Tantalum			= new EnumMaterial(731 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Ta, 0xE1E5E7FF),
	Tungsten			= new EnumMaterial(741 , TextureSet.METALLIC , 1  |4|8      |64                 , EnumElement.W , 0x4D4D4DFF),
	Rhenium				= new EnumMaterial(751 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Re, 0x92908EFF),
	Osmium				= new EnumMaterial(761 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Os, 0x5884D7FF),
	Iridium				= new EnumMaterial(771 , TextureSet.METALLIC , 1|2|4|8      |64                 , EnumElement.Ir, 0xECECECFF),
	Platinum			= new EnumMaterial(781 , TextureSet.SHINY    , 1  |4        |64                 , EnumElement.Pt, 0xDCCBB3FF),
	Gold				= new EnumMaterial(791 , TextureSet.SHINY    , 1  |4        |64                 , EnumElement.Au, 0xFFFF0BFF),
	Mercury				= new EnumMaterial(801 , TextureSet.FLUIDY2  , 1  |4     |32                    , EnumElement.Hg, 0xEACECEFF),
	Thallium			= new EnumMaterial(811 , TextureSet.METALLIC , 1  |4                            , EnumElement.Tl, 0xF8F8F9FF),
	Lead				= new EnumMaterial(821 , TextureSet.DULL     , 1  |4|8      |64                 , EnumElement.Pb, 0x271746FF),
	Bismuth				= new EnumMaterial(831 , TextureSet.METALLIC , 1  |4|8      |64                 , EnumElement.Bi, 0x1D422DFF),
	Polonium			= new EnumMaterial(841 , TextureSet.DULL     , 0                                , EnumElement.Po, 0xE7E6E1FF),
	Astatine			= new EnumMaterial(851 , TextureSet.NONMETAL4, 0                                , EnumElement.At, 0xFFFFFFFF),
	Radon				= new EnumMaterial(861 , TextureSet.FLUIDY1  ,            32                    , EnumElement.Rn, 0x11F185FF),
	Francium			= new EnumMaterial(871 , TextureSet.METALLIC , 1  |4                            , EnumElement.Fr, 0xAFAFAFFF),
	Radium				= new EnumMaterial(881 , TextureSet.METALLIC , 1  |4                            , EnumElement.Ra, 0xA5AF8DFF),
	Actinium			= new EnumMaterial(891 , TextureSet.METALLIC , 1  |4                            , EnumElement.Ac, 0xCFCFCFFF),
	Thorium				= new EnumMaterial(901 , TextureSet.METALLIC , 1  |4        |64                 , EnumElement.Th, 0x00251AFF),
	Protactinium		= new EnumMaterial(911 , TextureSet.METALLIC , 1  |4                            , EnumElement.Pa, 0xCBCACAFF),
	Uranium				= new EnumMaterial(921 , TextureSet.METALLIC , 1|2|4        |64                 , EnumElement.U , 0xACFE91FF),
	Neptunium			= new EnumMaterial(931 , TextureSet.METALLIC , 1  |4                            , EnumElement.Np, 0x9B978BFF),
	Plutonium			= new EnumMaterial(941 , TextureSet.METALLIC , 1  |4                            , EnumElement.Pu, 0x616161FF),
	//Compound
	//Oxide
	Water				= new EnumMaterial(2001, TextureSet.FLUIDY2  ,            32                    , "Water", "Water", "Water", 0x1F55FF8F),
	Ice					= new EnumMaterial(2002, TextureSet.CUBE_SHINY,1|2       |32                    , "Ice", "Ice", "Ice", 0x77A9FFAF),
	LithiumOxide		= new EnumMaterial(2003, TextureSet.CUBE     , 1|2                              , "LithiumOxide", "LithiumOxide", "Lithium Oxide", 0xE6ECF7FF),
	SodiumOxide			= new EnumMaterial(2004, TextureSet.DULL     , 1                                , "SodiumOxide", "SodiumOxide", "Sodium Oxide", 0xEFEEE8FF),
	PotassiumOxide		= new EnumMaterial(2005, TextureSet.DULL     , 1                                , "PotassiumOxide", "PotassiumOxide", "Potassium Oxide", 0xF2EFDAFF),
	RubidiumOxide		= new EnumMaterial(2006, TextureSet.DULL     , 1                                , "RubidiumOxide", "RubidiumOxide", "Rubidium Oxide", 0xFFEC99FF),
	CaesiumOxide		= new EnumMaterial(2007, TextureSet.DULL     , 1                                , "CaesiumOxide", "CaesiumOxide", "Caesium Oxide", 0xFFBC42FF),
	SodiumPeroxide		= new EnumMaterial(2008, TextureSet.DULL     , 1                                , "SodiumPeroxide", "SodiumPeroxide", "Sodium Peroxide", 0xFFFF87FF),
	PotassiumPeroxide	= new EnumMaterial(2009, TextureSet.DULL     , 1                                , "PotassiumPeroxide", "PotassiumPeroxide", "Potassium Peroxide", 0xF2F25EFF),
	BerylliumOxide		= new EnumMaterial(2021, TextureSet.CUBE     , 1|2                              , "BerylliumOxide", "BerylliumOxide", "Beryllium Oxide", 0xE6F4E8FF),
	MagnesiumOxide		= new EnumMaterial(2022, TextureSet.WHITE    , 1                                , "Magnesium Oxide", "Magnesium Oxide", "Magnesium Oxide", 0xF6F6F6FF),
	CalciumOxide		= new EnumMaterial(2023, TextureSet.WHITE    , 1                                , "CalciumOxide", "CalciumOxide", "Calcium Oxide", 0xF4F4F4FF),
	StrontiumOxide		= new EnumMaterial(2024, TextureSet.WHITE    , 1                                , "StrontiumOxide", "StrontiumOxide", "Strontium Oxide", 0xF4F4F4FF),
	BariumOxide			= new EnumMaterial(2025, TextureSet.WHITE    , 1                                , "BariumOxide", "BariumOxide", "Barium Oxide", 0xF0F0F0FF),
	BoronOxide			= new EnumMaterial(2041, TextureSet.DULL     , 1                                , "BoronOxide", "BoronOxide", "Boron Oxide", 0xF2E8E6FF),
	AluminiumOxide		= new EnumMaterial(2042, TextureSet.CUBE     , 1|2                              , "AluminiumOxide", "AluminiumOxide", "Aluminium Oxide", 0xE8E8E8FF),
	GalliumOxide		= new EnumMaterial(2043, TextureSet.CUBE_SHINY,1|2                              , "GalliumOxide", "GalliumOxide", "Gallium Oxide", 0xEAEAEAFF),
	IndiumOxide			= new EnumMaterial(2044, TextureSet.CUBE     , 1                                , "IndiumOxide", "IndiumOxide", "Indium Oxide", 0xB4E84EFF),
	ThalliumTrioxide	= new EnumMaterial(2045, TextureSet.CUBE     , 1                                , "ThalliumTrioxide", "ThalliumTrioxide", "Thallium Trioxide", 0x22261CFF),
	ThallousOxide		= new EnumMaterial(2046, TextureSet.CUBE_SHINY,1                                , "ThallousOxide", "ThallousOxide", "Thallous Oxide", 0x333530FF),
	CarbonMonoxide		= new EnumMaterial(2061, TextureSet.FLUIDY1  ,            32                    , "CarbonMonoxide", "CarbonMonoxide", "Carbon Monoxide", 0xF9F9CC3F),
	CarbonDioxide		= new EnumMaterial(2062, TextureSet.FLUIDY1  , 1         |32                    , "CarbonDioxide", "CarbonDioxide", "Carbon Dioxide", 0xDDDDCC3F),
	SiliconDioxide		= new EnumMaterial(2063, TextureSet.DULL     , 1                                , "SiliconDioxide", "SiliconDioxide", "Silicon Dioxide", 0xF7F7EFFF),
	GermaniumDioxide	= new EnumMaterial(2064, TextureSet.DULL     , 1                                , "GermaniumDioxide", "GermaniumDioxide", "Germanium Dioxide", 0xF7F7F7FF),
	StannousOxide		= new EnumMaterial(2065, TextureSet.DULL     , 1                                , "StannousOxide", "StannousOxide", "Stannous Oxide", 0x111111FF),
	StannicOxide		= new EnumMaterial(2066, TextureSet.DULL     , 1                                , "StannicOxide", "StannicOxide", "Stannic Dioxide", 0xF7F7EFFF),
	LeadMonoxide		= new EnumMaterial(2067, TextureSet.DULL     , 1                                , "LeadMonoxide", "LeadMonoxide", "Lead Monoxide", 0xE5BF90FF),
	LeadDioxide			= new EnumMaterial(2068, TextureSet.DULL     , 1                                , "LeadDioxide", "LeadDioxide", "Lead Dioxide", 0x160B06FF),
	NitrousOxide		= new EnumMaterial(2081, TextureSet.FLUIDY2  ,            32                    , "NitrousOxide", "NitrousOxide", "Nitrous Oxide", 0xE5E5E522),
	NitricOxide			= new EnumMaterial(2082, TextureSet.FLUIDY2  ,            32                    , "NitricOxide", "NitricOxide", "Nitric Oxide", 0xE5E5E522),
	NitrogenDioxide		= new EnumMaterial(2083, TextureSet.FLUIDY2  ,            32                    , "NitrogenDioxide", "NitrogenDioxide", "Nitrogen Dioxide", 0xD1813CAF),
	PhosphorusTrioxide	= new EnumMaterial(2084, TextureSet.WHITE    , 1         |32                    , "PhosphorusTrioxide", "PhosphorusTrioxide", "Phosphorus Trioxide", 0xE2E2E2FF),
	PhosphorusPentoxide	= new EnumMaterial(2085, TextureSet.WHITE    , 1                                , "PhosphorusPentoxide", "PhosphorusPentoxide", "Phosphorus Pentoxide", 0xE6E6E6FF),
	ArsenicTrioxide		= new EnumMaterial(2086, TextureSet.WHITE    , 1                                , "ArsenicTrioxide", "ArsenicTrioxide", "Arsenic Trioxide", 0xF2F2F2FF),
	AntimonyTrioxide	= new EnumMaterial(2087, TextureSet.WHITE    , 1                                , "AntimonyTrioxide", "AntimonyTrioxide", "Antimony Trioxide", 0xF2F2F2FF),
	AntimonyPentoxide	= new EnumMaterial(2088, TextureSet.DULL     , 1                                , "AntimonyPentoxide", "AntimonyPentoxide", "Antimony Pentoxide", 0xEFA940FF),
	BismuthTrioxide		= new EnumMaterial(2089, TextureSet.CUBE     , 1|2                              , "BismuthTrioxide", "BismuthTrioxide", "Bismuth Trioxide", 0xEDE4AFFF),
	SulfurDioxide		= new EnumMaterial(2101, TextureSet.FLUIDY2  ,            32                    , "SulfurDioxide", "SulfurDioxide", "Sulfur Dioxide", 0xEAE4D022),
	SulfurTrioxide		= new EnumMaterial(2102, TextureSet.FLUIDY1  ,            32                    , "SulfurTrioxide", "SulfurTrioxide", "Sulfur Trioxide", 0xE8B17422),
	SeleniumDioxide		= new EnumMaterial(2103, TextureSet.CUBE     , 1                                , "SeleniumDioxide", "SeleniumDioxide", "Selenium Dioxide", 0xE5CEAEFF),
	SeleniumTrioxide	= new EnumMaterial(2104, TextureSet.CUBE     , 1                                , "SeleniumTrioxide", "SeleniumTrioxide", "Selenium Trioxide", 0xF2EEE8FF),
	TelluriumDioxide	= new EnumMaterial(2105, TextureSet.DULL     , 1                                , "TelluriumDioxide", "TelluriumDioxide", "Tellurium Dioxide", 0xE8E1C7FF),
	DichlorineMonoxide	= new EnumMaterial(2121, TextureSet.FLUIDY2  ,            32                    , "DichlorineMonoxide", "DichlorineMonoxide", "Dichlorine Monoxide", 0xD8B656FF),
	ScandiumOxide		= new EnumMaterial(2201, TextureSet.DULL     , 1                                , "ScandiumOxide", "ScandiumOxide", "Scandium Oxide", 0xE5E5DEFF),
	TitaniumDioxide		= new EnumMaterial(2202, TextureSet.WHITE    , 1                                , "TitaniumDioxide", "TitaniumDioxide", "Titanium Dioxide", 0xFAFAFAFF),
	DivanadiumPentaoxide= new EnumMaterial(2203, TextureSet.CUBE     , 1|2                              , "DivanadiumPentaoxide", "DivanadiumPentaoxide", "Divanadium Pentaoxide", 0xF4D755FF),
	ChromiumSesquioxide	= new EnumMaterial(2204, TextureSet.CUBE     , 1                                , "ChromiumSesquioxide", "ChromiumSesquioxide", "Chromium Sesquioxide", 0x8DCC82FF),
	ChromiumTrioxide	= new EnumMaterial(2205, TextureSet.CUBE     , 1|2                              , "ChromiumTrioxide", "ChromiumTrioxide", "Chromium Trioxide", 0x9B311FFF),
	ManganeseOxide		= new EnumMaterial(2206, TextureSet.DULL     , 1                                , "ManganeseOxide", "ManganeseOxide", "Manganese Oxide", 0x261202FF),
	CuprousOxide		= new EnumMaterial(2401, TextureSet.RUBY     , 1|2                              , "CuprousOxide", "CuprousOxide", "Cuprous Oxide", 0xAF2424FF),
	CupricOxide			= new EnumMaterial(2402, TextureSet.DULL     , 1                                , "CupricOxide", "CupricOxide", "Cupric Oxide", 0x110303FF),
	//Chloride
	//Carbonate
	LithiumCarbonate	= new EnumMaterial(4001, TextureSet.DULL     , 1                                , "LithiumCarbonate", "LithiumCarbonate", "Lithium Carbonate", 0xEFEFEFFF),
	SodiumCarbonate		= new EnumMaterial(4002, TextureSet.DULL     , 1                                , "SodiumCarbonate", "SodiumCarbonate", "Sodium Carbonate", 0xEFEFEFFF),
	//Alloy
	Bronze				= new EnumMaterial(6001, TextureSet.METALLIC , 1  |4|8                          , "Bronze", "Bronze", "Bronze", 0xE5651BFF),
	Brass				= new EnumMaterial(6002, TextureSet.METALLIC , 1  |4|8                          , "Brass", "Brass", "Brass", 0xE5B91BFF),
	LeadBronze			= new EnumMaterial(6003, TextureSet.METALLIC , 1  |4|8                          , "LeadBronze", "LeadBronze", "Lead Bronze", 0xCE8218FF),
	Steel				= new EnumMaterial(6004, TextureSet.METALLIC , 1  |4|8                          , "Steel", "Steel", "Steel", 0x616378FF),
	WoughtIron			= new EnumMaterial(6005, TextureSet.METALLIC , 1  |4|8                          , "WoughtIron", "WoughtIron", "Wought Iron", 0xBFAFAFFF),
	
	Ash					= new EnumMaterial(7001, TextureSet.DULL     , 1                                , "Ash", "Ash", "Ash", 0xC4C4C4FF),
	BlastAsh			= new EnumMaterial(7002, TextureSet.DULL     , 1                                , "BlastAsh", "BlastAsh", "Blast Ash", 0xC1B7B0FF),
	RawRubber			= new EnumMaterial(7003, TextureSet.DULL     , 1                                , "RawRubber", "RawRubber", "Raw Rubber", 0xB9871FFF),
	
	Wood				= new EnumMaterial(8001, TextureSet.WOOD     , 1    |8                          , "Wood", "Wood", "Wood", 0x684E1E8F),
	Flint				= new EnumMaterial(8002, TextureSet.FLINT    , 1|2                              , "Flint", "Flint", "Flint", 0x232335FF),
	Coal				= new EnumMaterial(8003, TextureSet.NONMETAL1, 1|2          |64                 , "Coal", "Coal", "Coal", 0x262626FF),
	Charcoal			= new EnumMaterial(8004, TextureSet.NONMETAL1, 1|2                              , "Charcoal", "Charcoal", "Charcoal", 0x3A3735FF),
	Rubber				= new EnumMaterial(8005, TextureSet.RUBBER   , 1  |4|8                          , "Rubber", "Rubber", "Rubber", 0x1E1E1EFF),
	SBR					= new EnumMaterial(8006, TextureSet.RUBBER   , 1  |4|8                          , "SBR", "SBR", "SBR", 0xE5E2DAFF),
	Coke				= new EnumMaterial(8007, TextureSet.NONMETAL2, 1|2                              , "Coke", "Coke", "Coke", 0xA39A94FF),
	Glass				= new EnumMaterial(8008, TextureSet.RUBY     , 1|2                              , "Glass", "Glass", "Glass", 0xFFFFFFFF),
	
	Marble				= new EnumMaterial(9001, TextureSet.ROCKY    , 1                                , "Marble", "Marble", "Marble", 0xE2E6F0FF).setChemicalFormula("CaCO3").setBlockProperties(0, 1.0F, 8.0F),
	Stone				= new EnumMaterial(9002, TextureSet.ROCKY    , 1                                , "Stone", "Stone", "Stone", 0xCECECEFF).setBlockProperties(0, 3.0F, 10.0F),
	Netherrack			= new EnumMaterial(9003, TextureSet.ROCKY    , 1                                , "Netherrack", "Netherrack", "Netherrack", 0x931313FF).setBlockProperties(0, 0.5F, 5.0F),
	
	Ruby				= new EnumMaterial(10001,TextureSet.RUBY     , 1|2    |16                       , "Ruby", "Ruby", "Ruby", 0xE21B39FF),
	Sapphire			= new EnumMaterial(10002,TextureSet.VERTICAL , 1|2    |16                       , "Sappire", "Sappire", "Sappire", 0x0029DEFF),
	Emerald				= new EnumMaterial(10003,TextureSet.EMERALD  , 1|2    |16                       , "Emerald", "Emerald", "Emerald", 0xFFFFFFFF);
	
	static
	{
		MetallicHydrogen.setContain(new MaterialStack(HydrogenRadicals, 1));
		Hydrogen.setContain(new MaterialStack(HydrogenRadicals, 2));
		Nitrogen.setContain(new MaterialStack(NitrogenRadicals, 2));
		Oxygen.setContain(new MaterialStack(OxygenRadicals, 2));
		Ozone.setContain(new MaterialStack(OxygenRadicals, 3));
		Fluorine.setContain(new MaterialStack(FluorineRadicals, 2));
		Chlorine.setContain(new MaterialStack(ChlorineRadicals, 2));
		Bromine.setContain(new MaterialStack(BromineRadicals, 2));
		Iodine.setContain(new MaterialStack(IodineRadicals, 2));
		Diamond.setContain(new MaterialStack(Carbon, 128));
		
		WoughtIron.setContain(new MaterialStack(Iron, 1));
		
		WoughtIron.setChemicalFormula("Fe");
		
		RawRubber.setChemicalFormula("C5H8");
		Rubber.setChemicalFormula("(C5H8)nS");
		SBR.setChemicalFormula("(C12H14)n");
		
		Coal.setFuel(1600, 1000, 20, Ash, null, true);
		Coke.setFuel(3200, 1200, 20, Ash, null, true);
		Charcoal.setFuel(1600, 1000, 20, Ash, null, true);
		Wood.setFuel(200, 600, 16, Ash, null, false);
		Sulfur.setFuel(400, 750, 40, null, new PotionEffect(MobEffects.POISON, 5, 1), false);
		Lithium.setFuel(80, 800, 200, LithiumOxide, null, false);
		Sodium.setFuel(100, 800, 120, SodiumPeroxide, null, false);
		Potassium.setFuel(100, 800, 140, PotassiumPeroxide, null, false);
		Rubidium.setFuel(100, 800, 160, RubidiumOxide, null, false);
		Caesium.setFuel(100, 800, 180, CaesiumOxide, null, false);
		
		_NULL.tankCapacity = 1;
		Wood.tankCapacity = 1000;
		Bronze.tankCapacity = 1500;
		Iron.tankCapacity = 2000;
		WoughtIron.tankCapacity = 2500;
		Aluminium.tankCapacity = 2000;
		Steel.tankCapacity = 3000;
		Titanium.tankCapacity = 4000;
		Copper.tankCapacity = 1500;
		Silver.tankCapacity = 2000;
		Gold.tankCapacity = 2000;
		Stone.tankCapacity = 1200;
		Marble.tankCapacity = 1500;
		
		final EnumMaterial
		Peroxide = new EnumMaterial(-1, TextureSet.NONE, 0, "Peroxide", "Peroxide", "Peroxide", 0xFFFFFFFF),
		Carbonate = new EnumMaterial(-1, TextureSet.NONE, 0, "Carbonate", "Carbonate", "Carbonate", 0xFFFFFFFF);
		Peroxide.setContain(new MaterialStack(OxygenRadicals, 2));
		Carbonate.setContain(new MaterialStack(Carbon, 1), new MaterialStack(OxygenRadicals, 3));
		
		Water.setContain(new MaterialStack(HydrogenRadicals, 2), new MaterialStack(OxygenRadicals, 1));
		Ice.setContain(new MaterialStack(Water, 1));
		LithiumOxide.setContain(new MaterialStack(Lithium, 2), new MaterialStack(OxygenRadicals, 1));
		SodiumOxide.setContain(new MaterialStack(Sodium, 2), new MaterialStack(OxygenRadicals, 1));
		PotassiumOxide.setContain(new MaterialStack(Potassium, 2), new MaterialStack(OxygenRadicals, 1));
		RubidiumOxide.setContain(new MaterialStack(Rubidium, 2), new MaterialStack(OxygenRadicals, 1));
		CaesiumOxide.setContain(new MaterialStack(Caesium, 2), new MaterialStack(OxygenRadicals, 1));
		SodiumPeroxide.setContain(new MaterialStack(Sodium, 2), new MaterialStack(Peroxide, 1));
		PotassiumPeroxide.setContain(new MaterialStack(Potassium, 2), new MaterialStack(Peroxide, 1));
		BerylliumOxide.setContain(new MaterialStack(Beryllium, 1), new MaterialStack(OxygenRadicals, 1));
		MagnesiumOxide.setContain(new MaterialStack(Magnesium, 1), new MaterialStack(OxygenRadicals, 1));
		CalciumOxide.setContain(new MaterialStack(Calcium, 1), new MaterialStack(OxygenRadicals, 1));
		StrontiumOxide.setContain(new MaterialStack(Strontium, 1), new MaterialStack(OxygenRadicals, 1));
		BariumOxide.setContain(new MaterialStack(Barium, 1), new MaterialStack(OxygenRadicals, 1));
		BoronOxide.setContain(new MaterialStack(Boron, 2), new MaterialStack(OxygenRadicals, 3));
		AluminiumOxide.setContain(new MaterialStack(Aluminium, 2), new MaterialStack(OxygenRadicals, 3));
		GalliumOxide.setContain(new MaterialStack(Gallium, 2), new MaterialStack(OxygenRadicals, 3));
		IndiumOxide.setContain(new MaterialStack(Indium, 2), new MaterialStack(OxygenRadicals, 3));
		ThalliumTrioxide.setContain(new MaterialStack(Thallium, 2), new MaterialStack(OxygenRadicals, 3));
		ThallousOxide.setContain(new MaterialStack(Thallium, 2), new MaterialStack(OxygenRadicals, 1));
		CarbonMonoxide.setContain(new MaterialStack(Carbon, 1), new MaterialStack(OxygenRadicals, 1));
		CarbonDioxide.setContain(new MaterialStack(Carbon, 1), new MaterialStack(OxygenRadicals, 2));
		SiliconDioxide.setContain(new MaterialStack(Silicon, 1), new MaterialStack(OxygenRadicals, 2));
		GermaniumDioxide.setContain(new MaterialStack(Germanium, 1), new MaterialStack(OxygenRadicals, 2));
		StannousOxide.setContain(new MaterialStack(Tin, 1), new MaterialStack(OxygenRadicals, 1));
		StannicOxide.setContain(new MaterialStack(Tin, 1), new MaterialStack(OxygenRadicals, 2));
		LeadMonoxide.setContain(new MaterialStack(Lead, 1), new MaterialStack(OxygenRadicals, 1));
		LeadDioxide.setContain(new MaterialStack(Lead, 1), new MaterialStack(OxygenRadicals, 2));
		NitrousOxide.setContain(new MaterialStack(NitrogenRadicals, 2), new MaterialStack(OxygenRadicals, 1));
		NitricOxide.setContain(new MaterialStack(NitrogenRadicals, 1), new MaterialStack(OxygenRadicals, 1));
		NitrogenDioxide.setContain(new MaterialStack(NitrogenRadicals, 1), new MaterialStack(OxygenRadicals, 2));
		PhosphorusTrioxide.setContain(new MaterialStack(Phosphorus, 4), new MaterialStack(OxygenRadicals, 6));
		PhosphorusPentoxide.setContain(new MaterialStack(Phosphorus, 4), new MaterialStack(OxygenRadicals, 10));
		ArsenicTrioxide.setContain(new MaterialStack(Arsenic, 2), new MaterialStack(OxygenRadicals, 3));
		AntimonyPentoxide.setContain(new MaterialStack(Antimony, 2), new MaterialStack(OxygenRadicals, 3));
		AntimonyTrioxide.setContain(new MaterialStack(Antimony, 2), new MaterialStack(OxygenRadicals, 5));
		BismuthTrioxide.setContain(new MaterialStack(Bismuth, 2), new MaterialStack(OxygenRadicals, 3));
		SulfurDioxide.setContain(new MaterialStack(Sulfur, 1), new MaterialStack(OxygenRadicals, 2));
		SulfurTrioxide.setContain(new MaterialStack(Sulfur, 1), new MaterialStack(OxygenRadicals, 3));
		SeleniumDioxide.setContain(new MaterialStack(Selenium, 1), new MaterialStack(OxygenRadicals, 2));
		SeleniumTrioxide.setContain(new MaterialStack(Selenium, 1), new MaterialStack(OxygenRadicals, 3));
		ScandiumOxide.setContain(new MaterialStack(Scandium, 2), new MaterialStack(OxygenRadicals, 3));
		TitaniumDioxide.setContain(new MaterialStack(Titanium, 1), new MaterialStack(OxygenRadicals, 2));
		ChromiumSesquioxide.setContain(new MaterialStack(Chromium, 2), new MaterialStack(OxygenRadicals, 3));
		ChromiumTrioxide.setContain(new MaterialStack(Chromium, 1), new MaterialStack(OxygenRadicals, 3));
		ManganeseOxide.setContain(new MaterialStack(Manganese, 1), new MaterialStack(OxygenRadicals, 2));
		
		CuprousOxide.setContain(new MaterialStack(Copper, 2), new MaterialStack(OxygenRadicals, 1));
		CupricOxide.setContain(new MaterialStack(Copper, 1), new MaterialStack(OxygenRadicals, 1));
		
		DichlorineMonoxide.setContain(new MaterialStack(ChlorineRadicals, 2), new MaterialStack(OxygenRadicals, 1));
		
		LithiumCarbonate.setContain(new MaterialStack(Lithium, 2), new MaterialStack(Carbonate, 1));
		SodiumCarbonate.setContain(new MaterialStack(Sodium, 2), new MaterialStack(Carbonate, 1));
		
		Ruby.setContain(new MaterialStack(AluminiumOxide, 3), new MaterialStack(Chromium, 1));
		Sapphire.setContain(new MaterialStack(AluminiumOxide, 3), new MaterialStack(Titanium, 1));
		
		Ice.meltingPoint = Water.meltingPoint = 273;
		Ice.boilingPoint = Water.boilingPoint = 373;
		DichlorineMonoxide.meltingPoint = 152;
		DichlorineMonoxide.boilingPoint = 275;
		CarbonMonoxide.meltingPoint = 68;
		CarbonMonoxide.boilingPoint = 82;
		CarbonDioxide.meltingPoint = CarbonDioxide.boilingPoint = 216;
		SiliconDioxide.meltingPoint = 1713;
		SiliconDioxide.boilingPoint = 2950;
		GermaniumDioxide.meltingPoint = 1115;
		NitrousOxide.meltingPoint = 182;
		NitrousOxide.boilingPoint = 185;
		NitricOxide.meltingPoint = 109;
		NitricOxide.boilingPoint = 121;
		NitrogenDioxide.meltingPoint = 262;
		NitrogenDioxide.boilingPoint = 294;
		SulfurDioxide.meltingPoint = 201;
		SulfurTrioxide.boilingPoint = 263;
		SulfurTrioxide.meltingPoint = 290;
		SulfurTrioxide.boilingPoint = 318;
		
		Bronze.setContain(new MaterialStack(Copper, 3), new MaterialStack(Tin, 1));
		Brass.setContain(new MaterialStack(Copper, 3), new MaterialStack(Zinc, 1));
		LeadBronze.setContain(new MaterialStack(Copper, 4), new MaterialStack(Tin, 1), new MaterialStack(Lead, 1));
		Steel.setContain(new MaterialStack(Iron, 8), new MaterialStack(Carbon, 1));
		
		SubTag.METAL.addTo(MetallicHydrogen, Lithium, Beryllium, Sodium, Magnesium, Aluminium, Potassium, Calcium, Scandium, Titanium, Vanadium, Chromium, Manganese, Iron, Cobalt, Nickel, Copper, Zinc,
				Gallium, Germanium, Rubidium, Strontium, Yttrium, Zirconium, Niobium, Molybdenum, Terbium, Ruthenium, Rhodium, Palladium, Silver, Cadmium, Indium, Tin, Antimony, Caesium,
				Barium, Lantanium, Cerium, Praseodymium, Neodymium, Promethium, Samarium, Europium, Gadolinium, Terbium, Dysprosium, Holmium, Erbium, Thulium, Ytterbium, Lutetium, Hafnium,
				Tantalum, Tungsten, Rhenium, Osmium, Iridium, Platinum, Gold, Mercury, Thallium, Lead, Bismuth, Polonium, Francium, Radium, Actinium, Thorium, Protactinium, Uranium, Neptunium,
				Plutonium, Bronze, Brass, LeadBronze, Steel, WoughtIron);
		SubTag.NORMAL_ORE_PROCESSING.addTo(Aluminium, Titanium, Vanadium, Chromium, Magnesium, Iron, Cobalt, Nickel, Copper, Zinc, Zirconium, Niobium, Molybdenum, Ruthenium, Rhodium, Palladium, Cadmium,
				Indium, Tin, Antimony, Tungsten, Lead, Bismuth, Coal, Diamond);
		SubTag.ROCKY.addTo(Flint, Marble, Stone);
		SubTag.ALLOY_SIMPLE.addTo(Bronze, Brass, LeadBronze);
		SubTag.ALLOY_ADV.addTo(Steel);
		SubTag.BLAST_REQUIRED.addTo(Aluminium, Silicon, Titanium, Iridium, Osmium, Platinum, Tungsten, Steel, Yttrium, Chromium);
		SubTag.SOFT_TOOL.addTo(Wood, Rubber, SBR);
		SubTag.WOOD.addTo(Wood);
		SubTag.POLYMER.addTo(Rubber, SBR);
		
		MetallicHydrogen.setRecipeProperties(80L, 800L);
		Lithium.setRecipeProperties(350L, 600L);
		Sodium.setRecipeProperties(400L, 500L);
		Potassium.setRecipeProperties(425L, 470L);
		Rubidium.setRecipeProperties(440L, 455L);
		Cerium.setRecipeProperties(450L, 450L);
		Antimony.setRecipeProperties(1000L, 800L);
		Tin.setRecipeProperties(800L, 750L);
		Lead.setRecipeProperties(900L, 800L);
		Bronze.setRecipeProperties(1100L, 950L);
		LeadBronze.setRecipeProperties(1100L, 950L);
		Steel.setRecipeProperties(1200L, 1200L);
		Titanium.setRecipeProperties(1500L, 1100L);
		Tungsten.setRecipeProperties(1800L, 1200L);
		Iridium.setRecipeProperties(2800L, 1800L);
		Osmium.setRecipeProperties(3500L, 2000L);
		
		_NULL.setToolProperties(0, -1, 0F, 0F, _NULL);
		Diamond.setToolProperties(3, 1024, 8.0F, 4.0F, Wood);
		Aluminium.setToolProperties(1, 120, 4.5F, 2.0F, Wood);
		Titanium.setToolProperties(2, 820, 7.5F, 3.5F, Iron);
		Iron.setToolProperties(2, 256, 6.0F, 3.0F, Wood);
		WoughtIron.setToolProperties(2, 384, 6.5F, 3.0F, Wood);
		Cobalt.setToolProperties(2, 176, 6.0F, 3.0F, Wood);
		Nickel.setToolProperties(2, 128, 5.0F, 4.5F, Wood);
		Copper.setToolProperties(1, 72, 4.5F, 2.0F, Wood);
		Osmium.setToolProperties(4, 4096, 8.0F, 6.0F, Titanium);
		Iridium.setToolProperties(4, 5120, 7.5F, 5.0F, Titanium);
		Lead.setToolProperties(1, 80, 5.0F, 3.0F, Wood);
		Bismuth.setToolProperties(2, 150, 5.6F, 3.0F, Wood);
		Bronze.setToolProperties(2, 196, 5.5F, 2.5F, Wood);
		Brass.setToolProperties(2, 96, 5.0F, 2.5F, Wood);
		LeadBronze.setToolProperties(2, 216, 5.5F, 3.0F, Wood);
		Steel.setToolProperties(2, 512, 7.5F, 4.0F, Iron);
		Stone.setToolProperties(1, 48, 4.0F, 2.0F, Wood);
		Wood.setToolProperties(0, 32, 2.0F, 1.0F, Wood);
		Rubber.setToolProperties(0, 64, 1.0F, 0.0F, Wood);
		SBR.setToolProperties(1, 512, 1.5F, 0.0F, Iron);
		Flint.setToolProperties(1, 64, 3.5F, 2.5F, Wood);
	}
	
	public static void postinit()
	{
		for(EnumMaterial material : MATERIALS)
		{
			if(material != null)
			{
				material.resetMaterialProperties();
			}
		}
	}
	
	public static EnumMaterial[] getMaterials()
	{
		return MATERIALS;
	}
	
	public static EnumMaterial getMaterial(int i)
	{
		return MATERIALS[i];
	}
	
	public static EnumMaterial getMaterialNonNull(int i)
	{
		return MATERIALS[i] == null ? _NULL : MATERIALS[i];
	}
	
	public static EnumMaterial getMaterialFromDictName(String name)
	{
		return MATERIAL_MAP.get(name);
	}
	
	public final short id;
	public final String name;
	public final String localName;
	public final String oreDictName;
	public final TextureSet textureSet;
	
	/**
	 * 1 for dust, small dust, tiny dust, powder
	 * 2 for crystal, gem
	 * 4 for ingot, plate, stick, etc
	 * 8 for machine part, gear, etc
	 * 16 for lens, etc
	 * 32 for cell, etc
	 * 64 for ore, crushed, etc
	 */
	public final long materialState;
	private final ArrayList<SubTag> tags = new ArrayList();
	
	/**
	 * 1 for water
	 * 2 for ammonia
	 * 4 for ethanol(C2H5OH)
	 * 8 for ether((C2H5)2O)
	 */
	public long solubility;
	
	public EnumMaterial(int id, TextureSet set, long state, EnumElement element, int RGBa)
	{
		this(id, set, state, element.name.toLowerCase(), element.name, element.name, RGBa);
		this.molarMass = element.mass * 1000L;
		this.chemicalFormula = element.name();
		this.requireBracketsInCF = false;
	}
	public EnumMaterial(int id, TextureSet set, long state, String name, String oreName, String localName, int RGBa)
	{
		this.id = (short) id;
		this.textureSet = set;
		this.materialState = state;
		this.name = name;
		this.localName = localName;
		this.oreDictName = oreName;
		this.RGBa = RGBa;
		if(id != -1)
		{
			MATERIALS[id] = this;
			MATERIAL_MAP.put(this.oreDictName, this);
		}
	}
	
	public int fuelValue = -1;
	public int fuelPower = 20;
	public int fuelTemperature;
	public boolean highQuality;
	public EnumMaterial fuelOutput;
	public PotionEffect fuelEffect;
	
	public int RGBa;
	public long meltingPoint;
	public long boilingPoint;
	public Fluid solid;
	public Fluid liquid;
	public Fluid gas;
	
	/**
	 * 2 for recipe properties.
	 * 3 for chemical formula.
	 */
	private long autoGeneratedProperties = 0;
	private boolean autoGenerated = false;
	//For recipe uses.
	public long woughtHardness = 1000L;
	public long heatCapability = 1000L;
	public long molarMass;
	//For tool uses.
	public boolean isTool = false;
	public int toolQuality = -1;
	public long toolDurability = 1;
	public float toolHardness;
	public float toolDamageVsEntity;
	//For block uses
	public String blockHarvestTool = "pickaxe";
	public int blockHarvestLevel = 0;
	public float blockHardness;
	public float blockExplosionResistance;
	
	public int tankCapacity = -1;
	
	public EnumMaterial toolHandle = this;
	
	public List<MaterialStack> contain;
	private boolean requireBracketsInCF = true;
	public String chemicalFormula;
	
	public EnumMaterial byproduct1 = this;
	public EnumMaterial byproduct2 = this;
	public EnumMaterial byproduct3 = this;
	public EnumMaterial byproduct4 = this;
	
	public EnumMaterial setRecipeProperties(long woughtHardness, long heatCapability)
	{
		this.woughtHardness = woughtHardness;
		this.heatCapability = heatCapability;
		return this;
	}
	
	public EnumMaterial setContain(MaterialStack...stacks)
	{
		this.contain = Arrays.asList(stacks);
		return this;
	}
	
	public EnumMaterial setChemicalFormula(String chemicalFormula)
	{
		markNonAutoGenerated(3);
		this.chemicalFormula = chemicalFormula;
		return this;
	}
	
	public EnumMaterial setFuel(int burnTime, int maxTemperature, int burnPower, @Nullable EnumMaterial output, @Nullable PotionEffect effect, boolean highQuality)
	{
		this.fuelValue = burnTime;
		this.fuelTemperature = maxTemperature;
		this.fuelPower = burnPower;
		this.fuelEffect = effect;
		this.fuelOutput = output;
		this.highQuality = highQuality;
		return this;
	}
	
	public EnumMaterial setToolProperties(int quality, long durability, float hardness, float damageVsEntity)
	{
		return setToolProperties(quality, durability, hardness, damageVsEntity, Aluminium);
	}
	public EnumMaterial setToolProperties(int quality, long durability, float hardness, float damageVsEntity, EnumMaterial handle)
	{
		this.isTool = true;
		this.toolQuality = quality;
		this.toolDurability = durability;
		this.toolHardness = hardness;
		this.toolDamageVsEntity = damageVsEntity;
		this.toolHandle = handle;
		return this;
	}
	
	public EnumMaterial setBlockProperties(int harvestLevel, float hardness, float resistance)
	{
		return setBlockProperties("pickaxe", harvestLevel, hardness, resistance);
	}
	public EnumMaterial setBlockProperties(String harvestTool, int harvestLevel, float hardness, float resistance)
	{
		this.blockHarvestTool = harvestTool;
		this.blockHarvestLevel = harvestLevel;
		this.blockHardness = hardness;
		this.blockExplosionResistance = resistance;
		return this;
	}
	
	private void resetMaterialProperties()
	{
		if(this.contain != null)
		{
			for(MaterialStack stack : this.contain)
			{
				if(!stack.material.autoGenerated)
				{
					stack.material.resetMaterialProperties();
				}
			}
		}
		if(shouldAutoGenerated(2) && this.contain != null)
		{
			long count = 0;
			this.woughtHardness = 0;
			this.heatCapability = 0;
			for(MaterialStack stack : this.contain)
			{
				count += stack.amount;
				this.woughtHardness += stack.material.woughtHardness * stack.amount;
				this.heatCapability += stack.material.heatCapability * stack.amount;
			}
			this.woughtHardness /= count;
			this.heatCapability /= count;
		}
		if(shouldAutoGenerated(3) && this.contain != null)
		{
			StringBuilder builder = new StringBuilder();
			label:
			{
				for(MaterialStack stack : this.contain)
				{
					if(stack.material.chemicalFormula == null)
					{
						break label;
					}
					if(stack.material.requireBracketsInCF && stack.amount != 1)
					{
						builder.append('(');
					}
					builder.append(stack.material.chemicalFormula);
					if(stack.material.requireBracketsInCF && stack.amount != 1)
					{
						builder.append(')');
					}
					if(stack.amount != 1)
					{
						builder.append(stack.amount);
					}
				}
				this.chemicalFormula = builder.toString();
			}
		}
		this.autoGenerated = true;
	}
	
	private void markNonAutoGenerated(int mark)
	{
		this.autoGeneratedProperties |= (1L << mark);
	}
	
	private boolean shouldAutoGenerated(int mark)
	{
		return (this.autoGeneratedProperties & (1L << mark)) == 0;
	}
	
	@Override
	public void add(SubTag... tags)
	{
		for(SubTag tag : tags)
		{
			this.tags.add(tag);
		}
	}
	
	@Override
	public boolean contain(SubTag tag)
	{
		return this.tags.contains(tag);
	}
}