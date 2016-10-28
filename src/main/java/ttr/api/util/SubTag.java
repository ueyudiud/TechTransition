package ttr.api.util;

import java.util.Collection;
import java.util.HashSet;

import ttr.api.collection.Register;

public class SubTag implements IDataChecker<ISubTagContainer>
{
	private static final Register<SubTag> subTags = new Register();

	public static final SubTag WOOD = getNewSubTag("WOOD");
	public static final SubTag ORE_NONMETAL = getNewSubTag("ORE_NONMETAL");
	public static final SubTag ORE_CRYSTAL = getNewSubTag("ORE_CRYSTAL");
	public static final SubTag ORE_METAL = getNewSubTag("ORE_METAL");
	public static final SubTag ORE_SIMPLE = getNewSubTag("ORE_SIMPLE");
	public static final SubTag ORE_SALT = getNewSubTag("ORE_SALT");
	public static final SubTag METAL = getNewSubTag("METAL");
	public static final SubTag ROCK = getNewSubTag("ROCK");
	public static final SubTag NONMETAL = getNewSubTag("NONMETAL");
	public static final SubTag RADIOACTIVITY = getNewSubTag("RADIOACTIVITY");
	public static final SubTag GEN_COAL = getNewSubTag("GEN_COAL");
	public static final SubTag GEM = getNewSubTag("GEM");
	public static final SubTag ALLOY = getNewSubTag("ALLOY");
	public static final SubTag SULFATE = getNewSubTag("SULFATE");
	public static final SubTag SULFATE_SOLUTABLE = getNewSubTag("SULFATE_SOLUTABLE");
	public static final SubTag NITRIC = getNewSubTag("NITRIC");
	public static final SubTag NITRIC_SOLUTABLE = getNewSubTag("NITRIC_SOLUTABLE");
	public static final SubTag CHLORHYDRIC = getNewSubTag("CHLORHYDRIC");
	public static final SubTag CHLORHYDRIC_SOLUTABLE = getNewSubTag("CHLORHYDRIC_SOLUTABLE");
	public static final SubTag NO_ITEM = getNewSubTag("NO_ITEM");

	public static final SubTag[] SOLUTABLE_ALL = {NITRIC, NITRIC_SOLUTABLE, SULFATE, SULFATE_SOLUTABLE, CHLORHYDRIC, CHLORHYDRIC_SOLUTABLE};
	public static final SubTag[] SOLUTABLE_EXCLUDE_SULFACE = {NITRIC, NITRIC_SOLUTABLE, SULFATE, CHLORHYDRIC, CHLORHYDRIC_SOLUTABLE};
	public static final SubTag[] SOLUTABLE_EXCLUDE_CHLORHYDRIC = {NITRIC, NITRIC_SOLUTABLE, SULFATE, SULFATE_SOLUTABLE, CHLORHYDRIC};

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
		return name;
	}
	
	@Override
	public String toString()
	{
		return name;
	}

	public SubTag addContainerToList(ISubTagContainer... containers)
	{
		if (containers != null)
		{
			for (ISubTagContainer container : containers)
				if ((container != null) && (!relevantTaggedItems.contains(container)))
				{
					relevantTaggedItems.add(container);
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