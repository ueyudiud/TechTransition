/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.api.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ttr.api.util.ISubTagContainer;
import ttr.api.util.SubTag;

/**
 * Created at 2016年12月19日 下午11:53:11
 * @author ueyudiud
 */
public class EnumOrePrefix implements ISubTagContainer
{
	private static final EnumOrePrefix[] PREFIXS = new EnumOrePrefix[512];
	
	public static final EnumOrePrefix _DEFAULT = new EnumOrePrefix("", "", "", "", 1L);
	
	public static final EnumOrePrefix
	ore					= new EnumOrePrefix("ore"				, "ore"					, ""					, " Ore"	,  576L)	.setItemType(              64),
	crushed				= new EnumOrePrefix("crushed"			, "crushed"				, "Crushed "			, " Ore"	,  144L)	.setItemType(              64),
	crushedPurified		= new EnumOrePrefix("crushedPurified"	, "crushedPurified"		, "Purified Crushed "	, " Ore"	,  144L)	.setItemType(              64),
	crushedCentrifuged	= new EnumOrePrefix("crushedCentrifuged", "crushedCentrifuged"	, "Centrifuged Crushed ", " Ore"	,  144L)	.setItemType(              64),
	dustOre				= new EnumOrePrefix("dustOre"			, "dustOre"				, ""					, " Ore Dust", 144L)	.setItemType(              64),
	dustOreTiny			= new EnumOrePrefix("dustOreTiny"		, "dustOreTiny"			, "Tiny "				, " Ore Dust",  16L)	.setItemType(              64),
	dust				= new EnumOrePrefix("dust"				, "dust"				, ""					, " Dust"	,  144L)	.setItemType(1               ),
	dustSmall			= new EnumOrePrefix("dustSmall"			, "dustSmall"			, "Small Pile Of "		, " Dust"	,   36L)	.setItemType(1               ),
	dustTiny			= new EnumOrePrefix("dustTiny"			, "dustTiny"			, "Tiny Pile Of "		, " Dust"	,   16L)	.setItemType(1               ),
	dustPowder			= new EnumOrePrefix("dustPowder"		, "dustPowder"			, "1/36 Pile Of "		, " Dust"	,    4L)	.setItemType(1               ),
	gem					= new EnumOrePrefix("gem"				, "gem"					, ""					, ""		,  144L)	.setItemType(  2             ),
	gemChip				= new EnumOrePrefix("gemChip"			, "gemChip"				, ""					, " Chip"	,   36L)	.setItemType(  2             ),
	ingot				= new EnumOrePrefix("ingot"				, "ingot"				, ""					, " Ingot"	,  144L)	.setItemType(    4           ),
	plate				= new EnumOrePrefix("plate"				, "plate"				, ""					, " Plate"	,  144L){ @Override public String getDictName(EnumMaterial material) { return material == EnumMaterial.Wood ? "plankWood" : super.getDictName(material); } }.setItemType(  2|4        ),
	stick				= new EnumOrePrefix("stick"				, "stick"				, ""					, " Stick"	,   72L)	.setItemType(    4           ),
	stickLong			= new EnumOrePrefix("stickLong"			, "stickLong"			, "Long "				, " Stick"	,  144L)	.setItemType(    4           ),
	bolt				= new EnumOrePrefix("bolt"				, "bolt"				, ""					, " Bolt"	,   18L)	.setItemType(    4           ),
	screw				= new EnumOrePrefix("screw"				, "screw"				, ""					, " Screw"	,   18L)	.setItemType(    4           ),
	chunk				= new EnumOrePrefix("chunk"				, "chunk"				, ""					, " Chunk"	,   36L)	.setItemType(    4           ),
	nugget				= new EnumOrePrefix("nugget"			, "nugget"				, ""					, " Nugget"	,   16L)	.setItemType(    4           ),
	cell        	    = new EnumOrePrefix("cell"      	    , "cell"        		, ""					, " Cell"	,  144L)	.setItemType(           32   ),
	gear				= new EnumOrePrefix("gear"				, "gear"				, ""					, " Gear"	,  576L)	.setItemType(      8         );
	
	static
	{
		ore.hasItem = false;
		ore.add(SubTag.NO_ITEM, SubTag.NOT_BURNABLE);
		ore.setPrefixInBlackList("orePoor", "oreSmall", "oreLarge", "oreDouble", "oreNether", "oreEnd");
		crushed.setPrefixInBlackList("crushedPurified", "crushedCentrifuged");
		dust.setPrefixInBlackList("dustSmall", "dustTiny", "dustPowder", "dustOre");
		dustOre.setPrefixInBlackList("dustOreTiny");
		gem.setPrefixInBlackList("gemChip");
		ingot.setPrefixInBlackList("ingotDouble", "ingotTriple");
		plate.setPrefixInBlackList("plateDouble", "plateTriple", "plateDence");
		stick.setPrefixInBlackList("stickLong", "stickShort");
		gear.setPrefixInBlackList("gearSmall");
	}
	
	private static short idx;
	
	public static EnumOrePrefix[] getPrefixs()
	{
		return PREFIXS;
	}
	
	private final short id;
	public final String name;
	public final String oreDictName;
	public final long amount;
	public boolean hasItem = false;
	public long allowState;
	private String localPrefix = "";
	private String localPostfix = "";
	
	public List<String> prefixBlacklist = new ArrayList();
	
	public EnumOrePrefix(String name, String oreDictName, String prefix, String postfix, long amount)
	{
		this.name = name;
		this.oreDictName = oreDictName;
		this.localPrefix = prefix;
		this.localPostfix = postfix;
		this.amount = amount;
		PREFIXS[this.id = idx++] = this;
	}
	
	public EnumOrePrefix setPrefixInBlackList(String...strings)
	{
		for(String string : strings)
		{
			this.prefixBlacklist.add(string);
		}
		return this;
	}
	
	public EnumOrePrefix setItemType(long state)
	{
		this.hasItem = true;
		this.allowState = state;
		return this;
	}
	
	public boolean access(EnumMaterial material)
	{
		return (material.materialState & this.allowState) != 0;
	}
	
	public String getDictName(EnumMaterial material)
	{
		return getDictName(material.oreDictName);
	}
	
	public String getDictName(String postfix)
	{
		return this.oreDictName + postfix;
	}
	
	public boolean access(String name)
	{
		if(name != null && name.startsWith(this.oreDictName))
		{
			for(String n : this.prefixBlacklist)
			{
				if(name.startsWith(n)) return false;
			}
			return true;
		}
		return false;
	}
	
	private List<SubTag> subtags = new ArrayList();
	
	@Override
	public void add(SubTag... tags)
	{
		this.subtags.addAll(Arrays.asList(tags));
	}
	
	@Override
	public boolean contain(SubTag tag)
	{
		return this.subtags.contains(tag);
	}
	
	public String getTranslatedName(EnumMaterial material)
	{
		return this.localPrefix + material.localName + this.localPostfix;
	}
}