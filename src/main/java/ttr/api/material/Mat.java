package ttr.api.material;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.minecraftforge.fml.common.Loader;
import ttr.api.collection.Register;
import ttr.api.item.IItemMatProp;
import ttr.api.util.ISubTagContainer;
import ttr.api.util.LanguageManager;
import ttr.api.util.SubTag;

public class Mat implements Comparable<Mat>, ISubTagContainer
{
	private static final Register<Mat> REGISTER = new Register(32768);

	public static Register<Mat> register()
	{
		return REGISTER;
	}
	
	public final String modid;
	public final String name;
	public final String oreDictName;
	public final String localName;
	public final String displayTooltip;
	public final int id;
	public short[] RGBa = {255, 255, 255, 255};
	public int RGB = 0xFFFFFF;
	
	public int meltingPoint = -1;
	public int boilingPoint = -1;
	
	public float heatCap;
	public float thermalConduct;
	public float maxSpeed;
	public float maxTorque;
	public float dielectricConstant;
	public float electrialResistance;
	public float redstoneResistance;
	
	public int fuelTemperature;
	public int fuelPower;
	public float fuelValuePerUnit;
	//Tool configuration.
	public boolean canMakeTool = false;
	public int toolMaxUse = 1;
	public int toolHarvestLevel;
	public float toolHardness = 1.0F;
	public float toolDamageToEntity;
	public int toolEnchantability;
	public float toolBrittleness;
	public float toolAttackSpeed;
	//Block configuration.
	public boolean hasBlock = false;
	public String blockHarvestTool;
	public int blockHarvestLevel;
	public float blockExplosionResistance;
	public float blockHardness;
	//Multi item configuration.
	public IItemMatProp itemProp;

	public int saltColor = 0xFFFFFFFF;
	
	public Mat handleMaterial;

	public Mat byproduct1 = this;
	public Mat byproduct2 = this;
	public Mat byproduct3 = this;
	public Mat byproduct4 = this;
	
	private Set<SubTag> subTags = new HashSet();
	
	public Mat(int id, String name, String oreDict, String localized)
	{
		this(id, Loader.instance().activeModContainer().getModId(), name, oreDict, localized, "?");
	}
	public Mat(int id, String name, String oreDict, String localized, String displayTooltip, Object...objects)
	{
		this(id, Loader.instance().activeModContainer().getModId(), name, oreDict, localized, displayTooltip, objects);
	}
	public Mat(int id, String modid, String name, String oreDict, String localized, String displayTooltip, Object...objects)
	{
		this(id, true, modid, name, oreDict, localized, displayTooltip, objects);
	}
	public Mat(int id, boolean register, String modid, String name, String oreDict, String localized, String displayTooltip, Object...objects)
	{
		this.id = id;
		this.modid = modid;
		this.name = name;
		this.displayTooltip = String.format(displayTooltip, objects);
		oreDictName = oreDict;
		localName = localized;
		LanguageManager.registerLocal("material." + name + ".name", localized);
		if(register)
		{
			Mat.REGISTER.register(id, name, this);
		}
	}
	
	public String getLocalName()
	{
		return LanguageManager.translateToLocal("material." + name + ".name");
	}
	
	public Mat setRGBa(int colorIndex)
	{
		RGBa[0] = (short) ((colorIndex >> 24)       );
		RGBa[1] = (short) ((colorIndex >> 16) & 0xFF);
		RGBa[2] = (short) ((colorIndex >> 8 ) & 0xFF);
		RGBa[3] = (short) ((colorIndex      ) & 0xFF);
		RGB = colorIndex >> 8;
		return this;
	}
	
	public Mat setRGBa(short[] colorIndex)
	{
		RGBa = colorIndex;
		RGB = colorIndex[0] << 16 | colorIndex[1] << 8 | colorIndex[2];
		return this;
	}

	public Mat setPoint(int melting, int boiling)
	{
		meltingPoint = melting;
		boilingPoint = boiling;
		return this;
	}
	
	public Mat setGeneralProp(float heatCap, float thermalConduct, float maxSpeed, float maxTorque, float dielectricConstant, float electrialResistance, float redstoneResistance)
	{
		this.heatCap = heatCap;
		this.thermalConduct = thermalConduct;
		this.dielectricConstant = dielectricConstant;
		this.maxSpeed = maxSpeed;
		this.maxTorque = maxTorque;
		this.electrialResistance = electrialResistance;
		this.redstoneResistance = redstoneResistance;
		return this;
	}
	
	public Mat setBlockable(String harvestTool, int harvestLevel, float hardness, float resistance)
	{
		hasBlock = true;
		blockHarvestTool = harvestTool;
		blockHarvestLevel = harvestLevel;
		blockHardness = hardness;
		blockExplosionResistance = resistance;
		return this;
	}
	
	public Mat setToolable(int harvestLevel, int maxUse, float hardness, float brittleness, float dVE, float attackSpeed, int enchantability)
	{
		canMakeTool = true;
		toolHarvestLevel = harvestLevel;
		toolMaxUse = maxUse;
		toolHardness = hardness;
		toolBrittleness = brittleness;
		toolDamageToEntity = dVE;
		toolEnchantability = enchantability;
		toolAttackSpeed = attackSpeed;
		return this;
	}
	
	public Mat setSaltable(int saltColor, SubTag...enableSaltType)
	{
		this.saltColor = saltColor;
		add(enableSaltType);
		return this;
	}

	public Mat setItemProp(IItemMatProp itemProp)
	{
		this.itemProp = itemProp;
		return this;
	}

	public Mat setFuelInfo(int temperature, int power, float tickPerUnit)
	{
		fuelPower = power;
		fuelTemperature = temperature;
		fuelValuePerUnit = tickPerUnit;
		return this;
	}
	
	public Mat setTag(SubTag...tags)
	{
		add(tags);
		return this;
	}
	
	@Override
	public void add(SubTag... tags)
	{
		subTags.addAll(Arrays.asList(tags));
	}
	
	@Override
	public boolean contain(SubTag tag)
	{
		return subTags.contains(tag);
	}
	
	@Override
	public int compareTo(Mat o)
	{
		return name.compareTo(o.name);
	}
}