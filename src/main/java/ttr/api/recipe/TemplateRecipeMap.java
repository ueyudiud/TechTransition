package ttr.api.recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import ttr.api.recipe.TemplateRecipeMap.TemplateRecipe;
import ttr.api.stack.AbstractStack;
import ttr.api.util.Log;

public class TemplateRecipeMap implements IRecipeMap<TemplateRecipe>
{
	public static class TemplateRecipe implements IRecipeMap.IRecipe
	{
		public final String name;
		
		public AbstractStack[] inputsItem;
		public AbstractStack[] outputsItem;
		public FluidStack[] inputsFluid;
		public FluidStack[] outputsFluid;
		/**
		 * The chances of each item output chance.
		 */
		public int[][] chancesOutputItem;
		public Object customData;
		public long duration;
		public long minPower;
		public long customValue;
		
		public TemplateRecipe(String name)
		{
			this.name = name;
		}
	}
	
	public static final TemplateRecipeMap SMELTING = new TemplateRecipeMap("ttr.smelting", true, 1, 1);
	public static final TemplateRecipeMap CAULDRON_WASHING = new TemplateRecipeMap("ttr.cauldron_washing", true, 1, 3, 1, 0);
	public static final TemplateRecipeMap CAULDRON_SOLUTE = new TemplateRecipeMap("ttr.cauldron_soulte", true, 1, 1, 1, 1);
	
	public static final TemplateRecipeMap FORGE = new TemplateRecipeMap("ttr.forge", true, 3, 3);
	public static final TemplateRecipeMap CRYSTALIZER = new TemplateRecipeMap("ttr.crystalizer", true, 1, 1, 1, 0);
	
	public static final TemplateRecipeMap BRONZE_COMPRESS = new TemplateRecipeMap("ttr.compress.bronze", true, 1, 1);
	public static final TemplateRecipeMap BRONZE_BLAST = new TemplateRecipeMap("ttr.blast.bronze", true, 2, 2);
	public static final TemplateRecipeMap GRINDING_STEAM = new TemplateRecipeMap("ttr.grinding.steam", true, 1, 2);
	public static final TemplateRecipeMap GRINDING = new TemplateRecipeMap("ttr.grinding", true, 1, 4);
	public static final TemplateRecipeMap COMPRESS = new TemplateRecipeMap("ttr.compressor", true, 1, 1);
	public static final TemplateRecipeMap EXTRACT_STEAM = new TemplateRecipeMap("ttr.extract.steam", true, 1, 1);
	public static final TemplateRecipeMap EXTRACT = new TemplateRecipeMap("ttr.extract", true, 1, 1);
	public static final TemplateRecipeMap FORGE_HAMMER = new TemplateRecipeMap("ttr.forging", true, 1, 1);
	public static final TemplateRecipeMap PRESS = new TemplateRecipeMap("ttr.press", true, 2, 1);
	public static final TemplateRecipeMap CUTTING = new TemplateRecipeMap("ttr.cutting", true, 1, 1, 1, 0);
	public static final TemplateRecipeMap CUTTING_STEAM = new TemplateRecipeMap("ttr.cutting.steam", true, 1, 1);
	public static final TemplateRecipeMap ALLOY_SMELTING = new TemplateRecipeMap("ttr.alloy.smelting", false, 4, 1);
	public static final TemplateRecipeMap WIREMILL = new TemplateRecipeMap("ttr.wiremill", true, 1, 1);
	public static final TemplateRecipeMap BENDER = new TemplateRecipeMap("ttr.bender", true, 2, 1);
	public static final TemplateRecipeMap ASSEMBLE = new TemplateRecipeMap("ttr.assemble", false, 2, 1, 1, 0);
	public static final TemplateRecipeMap CANNER = new TemplateRecipeMap("ttr.canner", true, 3, 2, 1, 1);
	public static final TemplateRecipeMap LATHE = new TemplateRecipeMap("ttr.lathe", true, 1, 2, 1, 0);
	public static final TemplateRecipeMap ORE_WASHING = new TemplateRecipeMap("ttr.ore.washing", true, 1, 3, 1, 1);
	public static final TemplateRecipeMap PYROLYSIS = new TemplateRecipeMap("ttr.pyrolysis", true, 1, 1, 1, 1);
	
	public static void reloadRecipeMaps()
	{
		for(IRecipeMap map : IRecipeMap.RECIPEMAPS.values())
		{
			map.reloadRecipes();
		}
	}
	
	private List<TemplateRecipe> list = new ArrayList();
	private List<TemplateRecipe> validatedlist;
	private List<TemplateRecipe> listUnmodified = Collections.unmodifiableList(this.list);
	
	public final String name;
	public final int sizeItemInput;
	public final int sizeItemOutput;
	public final int sizeFluidInput;
	public final int sizeFluidOutput;
	public final boolean shapedItemInput;
	
	public TemplateRecipeMap(String name, boolean shapedItemInput, int sizeItemInput, int sizeItemOutput)
	{
		this(name, shapedItemInput, sizeItemInput, sizeItemOutput, 0, 0);
	}
	public TemplateRecipeMap(String name, boolean shapedItemInput, int sizeItemInput, int sizeItemOutput, int sizeFluidInput, int sizeFluidOutput)
	{
		this.name = name;
		this.shapedItemInput = shapedItemInput;
		this.sizeItemInput = sizeItemInput;
		this.sizeItemOutput = sizeItemOutput;
		this.sizeFluidInput = sizeFluidInput;
		this.sizeFluidOutput = sizeFluidOutput;
		RECIPEMAPS.put(name, this);
	}
	
	@Override
	public Collection<TemplateRecipe> getRecipes()
	{
		return this.listUnmodified;
	}
	
	public void addRecipe(AbstractStack input, long duration, long power, AbstractStack output)
	{
		addRecipe(new AbstractStack[]{input}, duration, power, new AbstractStack[]{output});
	}
	
	public void addRecipe(AbstractStack input1, FluidStack input2, long duration, long power, AbstractStack output1)
	{
		addRecipe(new AbstractStack[]{input1}, new FluidStack[]{input2}, new AbstractStack[]{output1} , new FluidStack[0], duration, power);
	}
	
	public void addRecipe(AbstractStack input1, FluidStack input2, long duration, long power, AbstractStack output1, FluidStack output2)
	{
		addRecipe(new AbstractStack[]{input1}, new FluidStack[]{input2}, new AbstractStack[]{output1}, new FluidStack[]{output2}, duration, power);
	}
	
	public void addRecipe(AbstractStack input, long duration, long power, AbstractStack output1, FluidStack output2)
	{
		addRecipe(new AbstractStack[]{input}, new FluidStack[0], new AbstractStack[]{output1}, new FluidStack[]{output2}, duration, power);
	}
	
	public void addRecipe(AbstractStack input, long duration, long power, AbstractStack output, long customValue)
	{
		addRecipe(new AbstractStack[]{input}, new AbstractStack[]{output}, duration, power, customValue);
	}
	
	public void addRecipe(AbstractStack[] input, long duration, long power, AbstractStack[] output)
	{
		addRecipe(input, null, output, duration, power);
	}
	
	public void addRecipe(AbstractStack[] input1, AbstractStack[] output1, long duration, long power, long customValue)
	{
		addRecipe(input1, null, output1, null, duration, power, customValue, null);
	}
	
	public void addRecipe(AbstractStack[] input1, FluidStack[] input2, AbstractStack[] output1, long duration, long power)
	{
		addRecipe(input1, input2, output1, null, duration, power);
	}
	
	public void addRecipe(AbstractStack[] input1, AbstractStack[] output1, FluidStack[] output2, long duration, long power)
	{
		addRecipe(input1, null, output1, output2, duration, power);
	}
	
	public void addRecipe(AbstractStack[] input1, FluidStack[] input2, AbstractStack[] output1, FluidStack[] output2, long duration, long power)
	{
		addRecipe(input1, input2, output1, output2, duration, power, 0, null);
	}
	
	public void addRecipe(AbstractStack[] input1, FluidStack[] input2, AbstractStack[] output1, FluidStack[] output2, long duration, long power, long customValue, Object customData)
	{
		addRecipe(input1, input2, output1, (int[][]) null, output2, duration, power, customValue, customData);
	}
	
	public void addRecipe(AbstractStack[] input1, FluidStack[] input2, AbstractStack[] output1, int[] outputChances, FluidStack[] output2, long duration, long power, long customValue, Object customData)
	{
		if(outputChances.length != output1.length) throw new RuntimeException("The output chances length and output length are not same!");
		int[][] chances = new int[outputChances.length][1];
		for(int i = 0; i < outputChances.length; ++i)
		{
			chances[i][0] = outputChances[i];
		}
		addRecipe(input1, input2, output1, chances, output2, duration, power, customValue, customData);
	}
	
	public void addRecipe(AbstractStack[] input1, FluidStack[] input2, AbstractStack[] output1, int[][] outputChances, FluidStack[] output2, long duration, long power, long customValue, Object customData)
	{
		if(input1 == null)
		{
			input1 = new AbstractStack[0];
		}
		else if(input1.length > this.sizeItemInput)
			throw new RuntimeException("Input item size more than limit!");
		if(input2 == null)
		{
			input2 = new FluidStack[0];
		}
		else if(input2.length > this.sizeFluidInput)
			throw new RuntimeException("Input fluid size more than limit!");
		if(output1 == null)
		{
			output1 = new AbstractStack[0];
		}
		else if(output1.length > this.sizeItemOutput)
			throw new RuntimeException("Output item size more than limit!");
		if(output2 == null)
		{
			output2 = new FluidStack[0];
		}
		else if(output2.length > this.sizeFluidOutput)
			throw new RuntimeException("Output fluid size more than limit!");
		if(duration == 0)
		{
			Log.warn("The duration of recipe is 0!");
		}
		if(power == 0)
		{
			Log.warn("The power of recipe is 0!");
		}
		if(outputChances != null && outputChances.length != output1.length)
			throw new RuntimeException("The chances length and output length is not same!");
		TemplateRecipe recipe = new TemplateRecipe(this.name);
		recipe.inputsItem = input1;
		recipe.inputsFluid = input2;
		recipe.outputsItem = output1;
		recipe.outputsFluid = output2;
		recipe.chancesOutputItem = outputChances;
		recipe.duration = duration;
		recipe.minPower = power;
		recipe.customValue = customValue;
		recipe.customData = customData;
		addRecipe(recipe);
	}
	
	@Override
	public void addRecipe(TemplateRecipe recipe)
	{
		this.list.add(recipe);
	}
	
	@Override
	public TemplateRecipe findRecipe(World world, BlockPos pos, long power, FluidStack[] fluidInputs, ItemStack... itemInputs)
	{
		if(this.validatedlist == null)
			throw new RuntimeException("The recipe map should reload once before finding recipe.");
		for(TemplateRecipe recipe : this.validatedlist)
		{
			if(matchRecipe(recipe, world, pos, power, fluidInputs, itemInputs))
				return recipe;
		}
		return null;
	}
	
	protected boolean matchRecipe(TemplateRecipe recipe, World world, BlockPos pos, long power, FluidStack[] fluidInputs, ItemStack[] itemInputs)
	{
		int i;
		if(recipe.minPower > power) return false;
		if(recipe.inputsItem.length > itemInputs.length) return false;
		if(recipe.inputsFluid.length > fluidInputs.length) return false;
		for(i = 0; i < recipe.inputsFluid.length; ++i)
		{
			if(recipe.inputsFluid[i] == null)
			{
				if(fluidInputs[i] != null) return false;
			}
			else
			{
				if(fluidInputs[i] == null ||
						!fluidInputs[i].containsFluid(recipe.inputsFluid[i])) return false;
			}
		}
		if(this.shapedItemInput)
		{
			for(i = 0; i < recipe.inputsItem.length; ++i)
			{
				if(recipe.inputsItem[i] == null)
				{
					if(itemInputs[i] != null) return false;
				}
				else
				{
					if(itemInputs[i] == null ||
							!recipe.inputsItem[i].contain(itemInputs[i])) return false;
				}
			}
			return true;
		}
		else
		{
			List<AbstractStack> inputs = new ArrayList(Arrays.asList(recipe.inputsItem));
			label:
				for(i = 0; i < itemInputs.length; ++i)
				{
					if(itemInputs[i] != null)
					{
						Iterator<AbstractStack> itr = inputs.iterator();
						while(itr.hasNext())
						{
							AbstractStack stack = itr.next();
							if(stack.contain(itemInputs[i]))
							{
								itr.remove();
								continue label;
							}
						}
						return false;
					}
				}
			return inputs.isEmpty();
		}
	}
	
	@Override
	public void reloadRecipes()
	{
		ImmutableList.Builder<TemplateRecipe> recipes = ImmutableList.builder();
		for(TemplateRecipe recipe : this.list)
		{
			if(isRecipeValid(recipe))
			{
				recipes.add(recipe);
			}
		}
		this.validatedlist = recipes.build();
	}
	
	public boolean isRecipeValid(TemplateRecipe recipe)
	{
		if(recipe.chancesOutputItem != null && recipe.chancesOutputItem.length != recipe.outputsItem.length) return false;
		if(recipe.inputsFluid.length > this.sizeFluidInput) return false;
		if(recipe.outputsFluid.length > this.sizeFluidOutput) return false;
		if(recipe.inputsItem.length > this.sizeItemInput) return false;
		if(recipe.outputsItem.length > this.sizeItemOutput) return false;
		for(AbstractStack stack : recipe.inputsItem)
		{
			if(!stack.valid()) return false;
		}
		for(AbstractStack stack : recipe.outputsItem)
		{
			if(!stack.valid()) return false;
		}
		return true;
	}
	
	public boolean containInput(ItemStack target)
	{
		for(TemplateRecipe recipe : this.validatedlist)
		{
			for(AbstractStack stack : recipe.inputsItem)
			{
				if(stack.similar(target))
					return true;
			}
		}
		return false;
	}
	
	public boolean containInput(FluidStack target)
	{
		for(TemplateRecipe recipe : this.validatedlist)
		{
			for(FluidStack stack : recipe.inputsFluid)
			{
				if(stack.isFluidEqual(target))
					return true;
			}
		}
		return false;
	}
	
	public boolean containOutput(ItemStack target)
	{
		for(TemplateRecipe recipe : this.validatedlist)
		{
			for(AbstractStack stack : recipe.outputsItem)
			{
				if(stack.similar(target))
					return true;
			}
		}
		return false;
	}
	
	public boolean containOutput(FluidStack target)
	{
		for(TemplateRecipe recipe : this.validatedlist)
		{
			for(FluidStack stack : recipe.outputsFluid)
			{
				if(stack.isFluidEqual(target))
					return true;
			}
		}
		return false;
	}
}