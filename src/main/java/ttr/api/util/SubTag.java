package ttr.api.util;

import java.util.Collection;
import java.util.HashSet;

import ttr.api.collection.Register;

public class SubTag implements IDataChecker<ISubTagContainer>
{
	private static final Register<SubTag> subTags = new Register();
	
	public static final SubTag WOOD = getNewSubTag("WOOD");
	public static final SubTag POLYMER = getNewSubTag("POLYMER");
	@Deprecated
	public static final SubTag ORE = getNewSubTag("ORE");
	public static final SubTag NORMAL_ORE_PROCESSING = getNewSubTag("NORMAL_ORE_PROCESSING");
	public static final SubTag METAL = getNewSubTag("METAL");
	public static final SubTag NONMETAL = getNewSubTag("NONMETAL");
	public static final SubTag RADIOACTIVITY = getNewSubTag("RADIOACTIVITY");
	public static final SubTag GEN_COAL = getNewSubTag("GEN_COAL");
	public static final SubTag GEM = getNewSubTag("GEM");
	public static final SubTag MONCRYSTAL = getNewSubTag("MONCRYSTAL");
	public static final SubTag ALLOY = getNewSubTag("ALLOY");
	public static final SubTag NO_ITEM = getNewSubTag("NO_ITEM");
	public static final SubTag BLAST_REQUIRED = getNewSubTag("BLAST_REQUIRED");
	
	public static final SubTag ROCKY = getNewSubTag("ROCKY");
	public static final SubTag ALLOY_SIMPLE = getNewSubTag("ALLOY_SIMPLE");
	public static final SubTag ALLOY_ADV = getNewSubTag("ALLOY_ADV");
	
	public static final SubTag NOT_BURNABLE = getNewSubTag("NOT_BURNABLE");
	
	public static final SubTag SOFT_TOOL = getNewSubTag("SOFT_TOOL");
	
	public static void addTagsTo(SubTag[] tags, ISubTagContainer...containers)
	{
		for(ISubTagContainer container : containers)
			if(container != null)
			{
				container.add(tags);
			}
	}
	
	public static SubTag getNewSubTag(String name)
	{
		if(subTags.contain(name))
			return subTags.get(name);
		return new SubTag(name);
	}
	
	private final String name;
	public final Collection<ISubTagContainer> relevantTaggedItems = new HashSet(1);
	
	private SubTag(String name)
	{
		this.name = name;
		subTags.register(name, this);
	}
	
	public String name()
	{
		return this.name;
	}
	
	@Override
	public String toString()
	{
		return this.name;
	}
	
	public SubTag addContainerToList(ISubTagContainer... containers)
	{
		if (containers != null)
		{
			for (ISubTagContainer container : containers)
				if ((container != null) && (!this.relevantTaggedItems.contains(container)))
				{
					this.relevantTaggedItems.add(container);
				}
		}
		return this;
	}
	
	public SubTag addTo(ISubTagContainer... containers)
	{
		if (containers != null)
		{
			for (ISubTagContainer container : containers)
				if (container != null)
				{
					container.add(new SubTag[] { this });
				}
		}
		return this;
	}
	
	@Override
	public boolean isTrue(ISubTagContainer container)
	{
		return container.contain(this);
	}
}