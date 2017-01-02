/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import ttr.api.enums.EnumTools;
import ttr.api.recipe.FakeCraftingInventory;
import ttr.api.stack.AbstractStack;
import ttr.api.stack.BaseStack;
import ttr.api.stack.OreStack;

/**
 * Created at 2016年12月20日 上午12:15:06
 * @author ueyudiud
 */
public class TTrRecipeHandler
{
	public static List<AbstractStack> CRAFTING_OUTPUT_REMOVE_TARGETS = new ArrayList();
	public static List<FakeCraftingInventory> CRAFTING_INPUTS_REMOVE_TARGETS = new ArrayList();
	public static List<IRecipe> CRAFTING_ADD_TARGETS = new ArrayList();
	
	public static void addHammerCrushable(ItemStack output, Object input)
	{
		if(output != null)
		{
			addRecipe(new ShapelessOreRecipe(output, input, EnumTools.hammer.orename()));
		}
	}
	
	public static void addShapedRecipe(ItemStack output, Object...inputs)
	{
		if(output != null)
		{
			addRecipe(new ShapedOreRecipe(output, inputs));
		}
	}
	
	public static void addShapelessRecipe(ItemStack output, Object...inputs)
	{
		if(output != null)
		{
			addRecipe(new ShapelessOreRecipe(output, inputs));
		}
	}
	
	public static void addRecipe(IRecipe recipe)
	{
		CRAFTING_ADD_TARGETS.add(recipe);
	}
	
	public static void markRemoveCraftingOutput(Object...stacks)
	{
		AbstractStack stack;
		for(Object object : stacks)
		{
			if(object instanceof Item)
			{
				stack = new BaseStack((Item) object);
			}
			else if(object instanceof Block)
			{
				stack = new BaseStack((Block) object);
			}
			else if(object instanceof ItemStack)
			{
				stack = new BaseStack((ItemStack) object);
			}
			else if(object instanceof String)
			{
				stack = new OreStack((String) object);
			}
			else if(object instanceof AbstractStack)
			{
				stack = (AbstractStack) object;
			}
			else throw new IllegalArgumentException();
			markRemoveCraftingOutput(stack);
		}
	}
	
	public static void markRemoveCraftingOutput(AbstractStack stack)
	{
		CRAFTING_OUTPUT_REMOVE_TARGETS.add(stack);
	}
	
	public static void markRemoveCraftingShapelessInputs(Object...objects)
	{
		CRAFTING_INPUTS_REMOVE_TARGETS.add(FakeCraftingInventory.createShapeless(objects));
	}
	
	public static void markRemoveCraftingShapedInputs(Object...objects)
	{
		CRAFTING_INPUTS_REMOVE_TARGETS.add(FakeCraftingInventory.createShaped(objects));
	}
	
	public static void markRemoveCraftingInput(Object object)
	{
		CRAFTING_INPUTS_REMOVE_TARGETS.add(FakeCraftingInventory.createWithSingle(object));
	}
	
	public static void valiadateRemoveRecipeInventory()
	{
		List<FakeCraftingInventory> list = new ArrayList(CRAFTING_INPUTS_REMOVE_TARGETS);
		CRAFTING_INPUTS_REMOVE_TARGETS.clear();
		for(FakeCraftingInventory inventory : list)
		{
			if(inventory.isValid())
			{
				CRAFTING_INPUTS_REMOVE_TARGETS.add(inventory);
			}
		}
	}
}