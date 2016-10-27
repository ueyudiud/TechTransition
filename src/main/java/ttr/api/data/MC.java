package ttr.api.data;

import ttr.api.material.MatCondition;
import ttr.api.util.IDataChecker;
import ttr.api.util.SubTag;

public class MC
{
	public static final MatCondition base = new MatCondition(false, "base", "Base", "%s Base");
	
	public static final MatCondition ingot = new MatCondition("ingot", "Ingot", "%s Ingot").setSize(1296L).setFilter(new IDataChecker.Or(SubTag.METAL));
	public static final MatCondition plate = new MatCondition("plate", "Plate", "%s Plate").setSize(1296L, 648L).setFilter(new IDataChecker.Or(SubTag.METAL, SubTag.GEN_COAL, SubTag.ROCK));
	public static final MatCondition stick = new MatCondition("stick", "Stick", "%s Stick").setSize(648L, 648L, 4.0F).setFilter(new IDataChecker.Or(SubTag.METAL));
	public static final MatCondition screw = new MatCondition("screw", "Screw", "%s Screw").setSize(162L, 162L, 4.0F).setFilter(new IDataChecker.Or(SubTag.METAL));
	public static final MatCondition gem = new MatCondition("gem", "Gem", "%s").setSize(1296L).setFilter(new IDataChecker.Or(SubTag.GEM, SubTag.GEN_COAL));

	public static final MatCondition dust = new MatCondition("dust", "Dust", "%s Dust").setSize(1296L, 72L).setFilter(SubTag.TRUE);
	public static final MatCondition dustSmall = new MatCondition("dustSmall", "Small Dust", "Small %s Dust").setSize(1296L, 72L).setFilter(SubTag.TRUE);
	public static final MatCondition dustTiny = new MatCondition("dustTiny", "Tiny Dust", "Tiny %s Dust").setSize(1296L, 72L).setFilter(SubTag.TRUE);

	public static final MatCondition ore = new MatCondition("ore", "Ore", "%s Ore").setSize(5184L, 1296L, 0.25F).setFilter(new IDataChecker.Or(SubTag.ORE_CRYSTAL, SubTag.ORE_METAL, SubTag.ORE_NONMETAL, SubTag.ORE_SALT, SubTag.ORE_SIMPLE));
	public static final MatCondition oreCrushed = new MatCondition("crushed", "Crushed Ore", "Crushed %s Ore").setSize(1728L, 324L).setFilter(new IDataChecker.Or(SubTag.ORE_CRYSTAL, SubTag.ORE_METAL, SubTag.ORE_NONMETAL, SubTag.ORE_SALT, SubTag.ORE_SIMPLE));
	public static final MatCondition orePurifiedCrushed = new MatCondition("crushedPurified", "Purified Crushed Ore", "Purified Crushed %s Ore").setSize(1728L, 324L).setFilter(new IDataChecker.Or(SubTag.ORE_CRYSTAL, SubTag.ORE_METAL, SubTag.ORE_NONMETAL, SubTag.ORE_SIMPLE));
	public static final MatCondition oreCentrifugedCrushed = new MatCondition("crushedCentrifuged", "Centrifuged Crushed Ore", "Centrifuged Crushed %s Ore").setSize(1728L, 324L).setFilter(new IDataChecker.Or(SubTag.ORE_CRYSTAL, SubTag.ORE_METAL, SubTag.ORE_NONMETAL, SubTag.ORE_SALT, SubTag.ORE_SIMPLE));

	static
	{
		ore.add(SubTag.NO_ITEM);
	}

	public static void init(){	}
}