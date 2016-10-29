package ttr.load;

import net.minecraft.item.Item;
import ttr.api.data.EnumToolType;
import ttr.api.item.ItemMulti;
import ttr.api.item.ItemTool;
import ttr.api.util.IDataChecker;
import ttr.api.util.ISubTagContainer;
import ttr.api.util.SubTag;
import ttr.core.item.ItemSubTTr;
import ttr.core.item.behavior.BehaviorHoe;
import ttr.core.item.behavior.BehaviorShovel;
import ttr.core.item.behavior.BehaviorWrench;

public class TTrItems
{
	public static ItemSubTTr sub;
	
	public static Item pickaxe;
	public static Item axe;
	public static Item shovel;
	public static Item hoe;
	public static Item sword;
	public static Item hard_hammer;
	public static Item file;
	public static Item saw;
	public static Item wrench;
	
	public static void init()
	{
		ItemMulti.postInitalized();
		IDataChecker<ISubTagContainer> NEEDED0 = new IDataChecker.Or(SubTag.GEM, SubTag.METAL, SubTag.ROCK, SubTag.WOOD, SubTag.ALLOY);
		IDataChecker<ISubTagContainer> NEEDED1 = new IDataChecker.Or(SubTag.GEM, SubTag.METAL);
		axe = new ItemTool(EnumToolType.axe, TTrToolStats.AXE, NEEDED0);
		shovel = new ItemTool(EnumToolType.shovel, TTrToolStats.SHOVEL, NEEDED0, new BehaviorShovel());
		pickaxe = new ItemTool(EnumToolType.pickaxe, TTrToolStats.PICKAXE, NEEDED0);
		hoe = new ItemTool(EnumToolType.hoe, TTrToolStats.HOE, NEEDED0, new BehaviorHoe());
		sword = new ItemTool(EnumToolType.sword, TTrToolStats.SWORD, NEEDED0);
		hard_hammer = new ItemTool(EnumToolType.hard_hammer, TTrToolStats.SWORD, NEEDED1);
		file = new ItemTool(EnumToolType.file, TTrToolStats.FILE, NEEDED1);
		saw = new ItemTool(EnumToolType.saw, TTrToolStats.SAW, NEEDED1);
		wrench = new ItemTool(EnumToolType.wrench, TTrToolStats.WRENCH, NEEDED1, new BehaviorWrench());
		
		sub = new ItemSubTTr();
	}
	
	public static void initSubItem(ItemSubTTr item)
	{
		item.addSubItem(701, "circuitPartBasic", "Basic Circuit Part", "circuitPartTier0");
		item.addSubItem(721, "circuitBoardBasic", "Basic Circuit Board", "circuitBoardTier0");
		item.addSubItem(731, "circuitBoardBasicHandmade", "Handmade Basic Circuit Board");
		item.addSubItem(741, "circuitChipBasic", "Basic Logical Chip", "circuitChipTier0");
		item.addSubItem(751, "circuitChipBasicHandmade", "Handmade Basic Logical Chip");
		item.addSubItem(761, "circuitBasic", "Basic Circuit", "circuitTier0");
		
		item.addSubItem(901, "bronzeGear", "Bronze Gear", "gearBronze");
		item.addSubItem(902, "ironGear", "Iron Gear", "gearIron");
		item.addSubItem(903, "steelGear", "Steel Gear", "gearSteel");
		item.addSubItem(904, "tungstensteelGear", "Tungsten Steel Gear", "gearTungstensteel");
		item.addSubItem(905, "stainlessSteelGear", "Stainless Steel Gear", "gearStainlessSteel");
		item.addSubItem(906, "titaniumGear", "Titanium Gear", "gearTitanium");
	}
}