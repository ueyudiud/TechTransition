package ttr.load.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import ic2.api.item.IC2Items;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ttr.api.material.Mat;
import ttr.api.recipe.FakeCraftingInventory;
import ttr.api.stack.AbstractStack;
import ttr.api.stack.BaseStack;
import ttr.api.stack.OreStack;
import ttr.api.util.SubTag;
import ttr.load.Config;

public class TTrRecipeRemove
{
	private static List<AbstractStack> outputRemoveList = new ArrayList();
	private static List<FakeCraftingInventory> removeList = new ArrayList();
	/**
	 * The ttr cut half output of log crafting recipe.
	 */
	private static List<FakeCraftingInventory> logRemoveList = new ArrayList();
	private static List<AbstractStack> smeltingInputRemoveList = new ArrayList();

	public static void init()
	{
		addRemoveRecipe("xxx", "x x", "xxx", 'x', Blocks.COBBLESTONE);
		if(Config.disableIC2Tools)
		{
			addRemoves(IC2Items.getItem("forge_hammer"), IC2Items.getItem("cutter"));
			addRemoveRecipe("p p", " p ", "s s", 'p', "plateIron", 's', Items.IRON_INGOT);
			addRemoveRecipe(" x ", "xxx", "x  ", 'x', Blocks.PLANKS);
		}
		//		if(Config.overrideMagnetizer)
		//		{
		//			addRemoves(Util.ItemStacks.getIC2("magnetizer"));
		//		}
		addRemoveRecipe("xxx", " s ", " s ", 'x', Items.QUARTZ, 's', Items.STICK);//AE2
		addRemoveRecipe("x", "x", 'x', Blocks.PLANKS);//Sticks
		addRemoveRecipe("xxx", "o  ", 'x', "dustCopper", 'o', "dustTin");
		addRemoveRecipe("xxx", "o  ", 'x', "dustCopper", 'o', "dustZinc");
		addRemoveRecipe("xxo", 'x', "dustIron", 'o', "dustNickel");
		addRemoveRecipe("xo", 'x', "dustGold", 'o', "dustSilver");
		addRemoveRecipe("ccc", "sr ", 'c', "dustCopper", 's', "dustSilver", 'r', "bucketRedstone");
		addRemoveRecipe("ttt", "sr ", 't', "dustTin", 's', "dustSilver", 'r', "bucketGlowstone");
		addRemoveRecipe("ttp", "sr ", 't', "dustTin", 'p', "dustPlatinum", 's', "dustSilver", 'r', "bucketEnder");
		addRemoveRecipe("ttt", "ttx", 'x', "dustElectrum", 't', "dustRedstone");
		addRemoveRecipe("xxx", "o  ", 'x', "ingotCopper", 'o', "ingotTin");
		addRemoveRecipe("xxx", "x x", "xxx", 'x', "plankWood");//Chest

		for(Mat material : Mat.register())
		{
			if(material.contain(SubTag.METAL) && material.hasBlock)
			{
				addRemoveRecipe("x", 'x', "block" + material.oreDictName);
				addRemoveRecipe("xxx", "xxx", "xxx", 'x', "ingot" + material.oreDictName);
				//				addRemoveRecipe("xxx", "xxx", "xxx", 'x', "nugget" + material.oreDictName);
			}
		}
		
		addRemoves(
				Items.SHEARS, Blocks.PISTON,
				Items.GOLDEN_HORSE_ARMOR, Items.BUCKET, Items.COOKIE,
				IC2Items.getItem("crafting", "circuit"), IC2Items.getItem("crafting", "advanced_circuit"),
				IC2Items.getItem("crafting", "coil"), IC2Items.getItem("crafting", "carbon_fibre"),
				IC2Items.getItem("crafting", "carbon_mesh"), IC2Items.getItem("crafting", "coal_ball"),
				IC2Items.getItem("crafting", "coal_chunk"), IC2Items.getItem("crafting", "iridium"),
				IC2Items.getItem("crafting", "raw_crystal_memory"),
				IC2Items.getItem("re_battery"), IC2Items.getItem("advanced_re_battery"),
				IC2Items.getItem("te", "generator"), IC2Items.getItem("te", "electrolyzer"),
				IC2Items.getItem("te", "fertilizer"), IC2Items.getItem("te", "extractor"),
				IC2Items.getItem("te", "canner"), IC2Items.getItem("te", "centrifuge"),
				IC2Items.getItem("te", "macerator"), IC2Items.getItem("te", "compressor"),
				IC2Items.getItem("te", "blast_furnace"), IC2Items.getItem("te", "electric_furnace"),
				IC2Items.getItem("te", "ore_washing_plant"), IC2Items.getItem("te", "metal_former"),
				IC2Items.getItem("te", "itnt"), IC2Items.getItem("fence", "iron"),
				Blocks.IRON_BARS, Blocks.IRON_BLOCK, Blocks.GOLD_BLOCK, Blocks.HOPPER, Items.CAULDRON,
				Blocks.GLASS_PANE, Blocks.REDSTONE_BLOCK, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE,
				Items.IRON_DOOR,
				"gearIron", "gearCopper", "gearGold", "gearBronze", "gearInvar", "gearSteel");
		addRemoveSmelting(IC2Items.getItem("misc_resource", "resin"), Blocks.SAND, Blocks.HARDENED_CLAY,
				"ingotAluminium", "ingotTitanium", "ingotChromium",
				"ingotTungsten", "ingotOsmium", "ingotIridium",
				"ingotPlatinum",
				"ingotSignalum", "ingotLumium", "ingotEnderium");
	}
	
	public static void addRemoves(Object...objects)
	{
		for(Object object : objects)
		{
			addRemove(object);
		}
	}
	
	public static void addRemove(Object object)
	{
		if(object instanceof Item)
		{
			addRemove(new BaseStack((Item) object));
		}
		else if(object instanceof Block)
		{
			addRemove(new BaseStack((Block) object));
		}
		else if(object instanceof ItemStack)
		{
			addRemove(new BaseStack((ItemStack) object));
		}
		else if(object instanceof String)
		{
			addRemove(new OreStack((String) object));
		}
		else if(object instanceof AbstractStack)
		{
			outputRemoveList.add((AbstractStack) object);
		}
	}
	
	public static void addRemoveRecipe(Object...objects)
	{
		FakeCraftingInventory inventory = FakeCraftingInventory.init(objects);
		if(inventory.isValid())
		{
			removeList.add(inventory);
		}
	}
	
	public static void addLogRemoveRecipe(Object...objects)
	{
		FakeCraftingInventory inventory = FakeCraftingInventory.init(objects);
		if(inventory.isValid())
		{
			logRemoveList.add(inventory);
		}
	}

	public static void addRemoveSmelting(Object...objects)
	{
		for(Object object : objects)
		{
			addRemoveSmelting(object);
		}
	}
	private static void addRemoveSmelting(Object object)
	{
		if(object instanceof Item)
		{
			addRemoveSmelting(new BaseStack((Item) object));
		}
		else if(object instanceof Block)
		{
			addRemoveSmelting(new BaseStack((Block) object));
		}
		else if(object instanceof ItemStack)
		{
			addRemoveSmelting(new BaseStack((ItemStack) object));
		}
		else if(object instanceof String)
		{
			addRemoveSmelting(new OreStack((String) object));
		}
		else if(object instanceof AbstractStack)
		{
			smeltingInputRemoveList.add((AbstractStack) object);
		}
	}
	
	public static void removeAll()
	{
		for(Entry<ItemStack, ItemStack> entry : new ArrayList<Entry<ItemStack, ItemStack>>(FurnaceRecipes.instance().getSmeltingList().entrySet()))
		{
			ItemStack input = entry.getKey();
			for(AbstractStack stack : smeltingInputRemoveList)
			{
				if(stack.similar(input))
				{
					FurnaceRecipes.instance().getSmeltingList().remove(input);
					break;
				}
			}
		}
		if(Config.logHalfOutput)
		{
			for(ItemStack stack : OreDictionary.getOres("logWood"))
			{
				if(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
				{
					for(int i = 0; i < 16; ++i)
					{
						addLogRemoveRecipe("x", 'x', new ItemStack(stack.getItem(), 1, i));
					}
				}
				else
				{
					addLogRemoveRecipe("x", 'x', stack);
				}
			}
		}
		label:
			for(Object rawRecipe : new ArrayList(CraftingManager.getInstance().getRecipeList()))
			{
				IRecipe recipe = (IRecipe) rawRecipe;
				ItemStack output = recipe.getRecipeOutput();
				if(output != null)
				{
					for(AbstractStack stack1 : outputRemoveList)
					{
						if(stack1.similar(output))
						{
							CraftingManager.getInstance().getRecipeList().remove(rawRecipe);
							continue label;
						}
					}
				}
				for(FakeCraftingInventory inventory : removeList)
				{
					try
					{
						if(recipe.matches(inventory, null))
						{
							CraftingManager.getInstance().getRecipeList().remove(rawRecipe);
							continue label;
						}
					}
					catch(Exception exception)
					{
						;
					}
				}
				if(Config.logHalfOutput)
				{
					for(FakeCraftingInventory inventory : logRemoveList)
					{
						try
						{
							if(recipe.matches(inventory, null))
							{
								CraftingManager.getInstance().getRecipeList().remove(rawRecipe);
								ItemStack log = inventory.getStackInSlot(0);
								GameRegistry.addRecipe(new ShapedOreRecipe(output.copy(), "s", "x", 's', "craftingToolSaw", 'x', log));
								output.stackSize /= 2;
								if(output.stackSize == 0)
								{
									output.stackSize = 1;
								}
								GameRegistry.addRecipe(new ShapedOreRecipe(output, "x", 'x', log));
								if(log.getItem() instanceof ItemBlock)
								{
									//								PlateCuttingRecipe.addCuttingRecipe("minecraft:log" +
									//										GameData.getBlockRegistry().getNameForObject(
									//												Block.getBlockFromItem(log.getItem())),
									//										new CuttingInfo(new BaseStack(log), 3200, 30, new BaseStack(output), new BaseStack(output, output.stackSize * 3)));
								}
							}
						}
						catch(Exception exception)
						{
							;
						}
					}
				}
			}
	}
}