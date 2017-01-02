/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.api.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created at 2016年12月19日 下午11:52:38
 * @author ueyudiud
 */
public class TextureSet
{
	public static final List<TextureSet> TEXTURE_SETS = new ArrayList();
	
	public static final TextureSet NONE = new TextureSet("none");
	public static final TextureSet WOOD = new TextureSet("wood");
	public static final TextureSet FLINT = new TextureSet("flint");
	public static final TextureSet DULL = new TextureSet("dull");
	public static final TextureSet WHITE = new TextureSet("white");
	public static final TextureSet ROCKY = new TextureSet("rocky");
	public static final TextureSet METALLIC = new TextureSet("metallic");
	public static final TextureSet SHINY = new TextureSet("shiny");
	public static final TextureSet DIAMOND = new TextureSet("diamond");
	public static final TextureSet RUBY = new TextureSet("ruby");
	public static final TextureSet FLUIDY1 = new TextureSet("fluidy1");//Such as H2
	public static final TextureSet FLUIDY2 = new TextureSet("fluidy2");//Such as He
	public static final TextureSet NONMETAL1 = new TextureSet("nonemetal1");//Such as coal
	public static final TextureSet NONMETAL2 = new TextureSet("nonemetal2");//Such as boron
	//	public static final TextureSet NONMETAL3 = new TextureSet("nonemetal3");//Such as phosphorus
	public static final TextureSet NONMETAL4 = new TextureSet("nonemetal4");//Such as silicon
	public static final TextureSet QUARTZ = new TextureSet("quartz");
	public static final TextureSet CUBE = new TextureSet("cube");
	public static final TextureSet CUBE_SHINY = new TextureSet("cube_shiny");
	public static final TextureSet RUBBER = new TextureSet("rubber");
	
	static
	{
		EnumOrePrefix[] prefixs;
		putModelLocations(EnumOrePrefix.ingot, "rubber", RUBBER);
		putModelLocations(EnumOrePrefix.gem, "nonmetal1", NONMETAL1);
		putModelLocations(EnumOrePrefix.gem, "nonmetal2", NONMETAL2);
		putModelLocations(EnumOrePrefix.gem, "nonmetal4", NONMETAL4);
		putModelLocations(EnumOrePrefix.gem, "flint", FLINT);
		putModelLocations(EnumOrePrefix.gem, "metallic", METALLIC);
		putModelLocations(EnumOrePrefix.gem, "diamond", DIAMOND);
		putModelLocations(EnumOrePrefix.gem, "ruby", RUBY);
		putModelLocations(EnumOrePrefix.gem, "quartz", QUARTZ);
		putModelLocations(EnumOrePrefix.gem, "cube", CUBE);
		putModelLocations(EnumOrePrefix.gem, "cube_shiny", CUBE_SHINY);
		putModelLocations(EnumOrePrefix.gemChip, "nonmetal", NONMETAL1, NONMETAL2, NONMETAL4);
		putModelLocations(EnumOrePrefix.gemChip, "flint", FLINT);
		putModelLocations(EnumOrePrefix.gemChip, "cube", CUBE);
		putModelLocations(EnumOrePrefix.gemChip, "cube_shiny", CUBE_SHINY);
		putModelLocations(EnumOrePrefix.gear, "shiny", SHINY, RUBBER);
		putModelLocations(EnumOrePrefix.gear, "rocky", ROCKY);
		putModelLocations(EnumOrePrefix.gear, "wooden", WOOD);
		
		putModelLocations(EnumOrePrefix.ore, "normal", METALLIC, SHINY, DULL);
		putModelLocations(EnumOrePrefix.ore, "gem", NONMETAL2, NONMETAL4, DIAMOND, RUBY);
		putModelLocations(EnumOrePrefix.ore, "ven", NONMETAL1, ROCKY, QUARTZ, CUBE, CUBE_SHINY, WHITE);
		putModelLocations(EnumOrePrefix.plate, "nonmetal", NONMETAL1, NONMETAL2);
		prefixs = new EnumOrePrefix[]
				{EnumOrePrefix.ingot, EnumOrePrefix.plate, EnumOrePrefix.chunk, EnumOrePrefix.nugget, EnumOrePrefix.stick, EnumOrePrefix.stickLong};
		for(EnumOrePrefix prefix : prefixs)
		{
			if(prefix != EnumOrePrefix.ingot)
				putModelLocations(prefix, "shiny", RUBBER);
			putModelLocations(prefix, "dull", DULL, QUARTZ, CUBE);
			putModelLocations(prefix, "shiny", SHINY, NONMETAL4, DIAMOND, RUBY, CUBE_SHINY);
			putModelLocations(prefix, "metallic", METALLIC, WHITE);
			putModelLocations(prefix, "rocky", ROCKY);
		}
		prefixs = new EnumOrePrefix[]
				{EnumOrePrefix.dust, EnumOrePrefix.dustSmall, EnumOrePrefix.dustTiny, EnumOrePrefix.dustPowder};
		for(EnumOrePrefix prefix : prefixs)
		{
			putModelLocations(prefix, "nonmetal1", NONMETAL1, NONMETAL2, FLINT, ROCKY);
			putModelLocations(prefix, "nonmetal2", NONMETAL4);
			putModelLocations(prefix, "metallic", METALLIC);
			putModelLocations(prefix, "dull", DULL, QUARTZ, CUBE);
			putModelLocations(prefix, "shiny", SHINY, RUBBER, DIAMOND, RUBY, CUBE_SHINY);
			putModelLocations(prefix, "wood", WOOD);
			putModelLocations(prefix, "white", WHITE);
		}
		putModelLocations(EnumOrePrefix.cell, "1", FLUIDY1, METALLIC, CUBE, CUBE_SHINY);
		putModelLocations(EnumOrePrefix.cell, "2", FLUIDY2, WHITE);
	}
	
	public final String name;
	private Map<EnumOrePrefix, String> locations = new HashMap();
	
	public TextureSet(String name)
	{
		this.name = name;
		this.TEXTURE_SETS.add(this);
	}
	
	public String getLocation(EnumOrePrefix prefix)
	{
		return this.locations.getOrDefault(prefix, "default");
	}
	
	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModelLocation(EnumOrePrefix prefix)
	{
		return new ModelResourceLocation("ttr:" + prefix.name, "type=" + getLocation(prefix));
	}
	
	@SideOnly(Side.CLIENT)
	public ResourceLocation[] getResources()
	{
		List<ResourceLocation> locations = new ArrayList();
		for(EnumOrePrefix prefix : EnumOrePrefix.getPrefixs())
		{
			if(prefix != null)
			{
				locations.add(getModelLocation(prefix));
			}
		}
		return locations.toArray(new ResourceLocation[locations.size()]);
	}
	
	public static void putModelLocations(EnumOrePrefix prefix, String location, TextureSet...sets)
	{
		for(TextureSet set : sets)
		{
			set.locations.put(prefix, location);
		}
	}
}