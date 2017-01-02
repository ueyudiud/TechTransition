/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.compact;

import com.google.common.collect.ImmutableList;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.compact.jei.TTrRecipeCategoryTemplate;
import ttr.compact.jei.TTrRecipeHandlerTemplate;
import ttr.core.block.machine.BlockBronzeMulti;
import ttr.core.block.machine.BlockElectricMachine1.MachineType3;
import ttr.core.block.machine.BlockSteamMachine1a.MachineType1;
import ttr.load.TTrIBF;

/**
 * Created at 2016年12月20日 上午9:36:49
 * @author ueyudiud
 */
@JEIPlugin
public class JEIIntegration implements IModPlugin
{
	@Override
	public void registerIngredients(IModIngredientRegistration registry)
	{
		
	}
	
	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry)
	{
		
	}
	
	@Override
	public void register(IModRegistry registry)
	{
		registry.addRecipeHandlers(new TTrRecipeHandlerTemplate());
		addRecipeMapDisplay(registry, TemplateRecipeMap.SMELTING, new ResourceLocation("ttr", "textures/gui/JEI/furnace.png"), new int[][]{{56, 17}}, new int[][]{{116, 35}}, new ItemStack(TTrIBF.furnace, 1, 0), new ItemStack(TTrIBF.furnace, 1, 1), new ItemStack(TTrIBF.furnace, 1, 2));
		
		addRecipeMapDisplay(registry, TemplateRecipeMap.CAULDRON_SOLUTE, new ResourceLocation("ttr", "textures/gui/JEI/cauldron.png"), new int[][]{{35, 24}}, new int[][]{{71, 24}}, new int[][]{{53, 42}}, new int[][]{{99, 42}}, new ItemStack(TTrIBF.cauldron));
		addRecipeMapDisplay(registry, TemplateRecipeMap.CAULDRON_WASHING, new ResourceLocation("ttr", "textures/gui/JEI/cauldron.png"), new int[][]{{35, 24}}, new int[][]{{71, 24}, {89, 24}, {107, 24}}, new int[][]{{53, 42}}, new int[0][], new ItemStack(TTrIBF.cauldron));
		
		addRecipeMapDisplay(registry, TemplateRecipeMap.FORGE_HAMMER, new ResourceLocation("ttr", "textures/gui/JEI/steam_hammer.png"), new int[][]{{53, 25}}, new int[][]{{107, 25}}, new ItemStack(TTrIBF.steamMachine1a, 1, MachineType1.FORGE_HAMMER.ordinal()), new ItemStack(TTrIBF.steamMachine1b, 1, MachineType1.FORGE_HAMMER.ordinal()));
		addRecipeMapDisplay(registry, TemplateRecipeMap.ALLOY_SMELTING, new ResourceLocation("ttr", "textures/gui/JEI/alloy_smelter.png"), new int[][]{{35, 16}, {53, 16}, {35, 34}, {53, 34}}, new int[][]{{107, 25}}, new ItemStack(TTrIBF.steamMachine1a, 1, MachineType1.ALLOY_FURNACE.ordinal()), new ItemStack(TTrIBF.steamMachine1b, 1, MachineType1.ALLOY_FURNACE.ordinal()), new ItemStack(TTrIBF.electricMachine1, 1, MachineType3.ALLOY_SMELTER.ordinal()));
		addRecipeMapDisplay(registry, TemplateRecipeMap.EXTRACT, new ResourceLocation("ttr", "textures/gui/JEI/extractor.png"), new int[][]{{53, 25}}, new int[][]{{107, 25}}, new ItemStack(TTrIBF.steamMachine1a, 1, MachineType1.EXTRACTOR.ordinal()), new ItemStack(TTrIBF.steamMachine1b, 1, MachineType1.EXTRACTOR.ordinal()), new ItemStack(TTrIBF.electricMachine1, 1, MachineType3.EXTRACTOR.ordinal()));
		addRecipeMapDisplay(registry, TemplateRecipeMap.COMPRESS, new ResourceLocation("ttr", "textures/gui/JEI/compress.png"), new int[][]{{53, 25}}, new int[][]{{107, 25}}, new ItemStack(TTrIBF.bronzeMulti, 1, BlockBronzeMulti.EnumType.COMPRESSOR.ordinal()), new ItemStack(TTrIBF.electricMachine1, 1, MachineType3.COMPRESSOR.ordinal()));
		addRecipeMapDisplay(registry, TemplateRecipeMap.CUTTING, new ResourceLocation("ttr", "textures/gui/JEI/cutter.png"), new int[][]{{53, 25}}, new int[][]{{107, 25}}, new int[][]{{53, 63}}, new int[0][], new ItemStack(TTrIBF.steamMachine1a, 1, MachineType1.CUTTER.ordinal()), new ItemStack(TTrIBF.steamMachine1b, 1, MachineType1.CUTTER.ordinal()), new ItemStack(TTrIBF.electricMachine1, 1, MachineType3.CUTTER.ordinal()));
		addRecipeMapDisplay(registry, TemplateRecipeMap.GRINDING, new ResourceLocation("ttr", "textures/gui/JEI/macerator.png"), new int[][]{{53, 25}}, new int[][]{{107, 16}, {125, 16}, {107, 34}, {125, 34}}, new ItemStack(TTrIBF.steamMachine1a, 1, MachineType1.GRINDER.ordinal()), new ItemStack(TTrIBF.steamMachine1b, 1, MachineType1.GRINDER.ordinal()), new ItemStack(TTrIBF.electricMachine1, 1, MachineType3.GRINDER.ordinal()));
		addRecipeMapDisplay(registry, TemplateRecipeMap.BENDER, new ResourceLocation("ttr", "textures/gui/JEI/bender.png"), new int[][]{{35, 25}, {53, 25}}, new int[][]{{107, 25}}, new ItemStack(TTrIBF.electricMachine1, 1, MachineType3.BENDER.ordinal()));
		addRecipeMapDisplay(registry, TemplateRecipeMap.WIREMILL, new ResourceLocation("ttr", "textures/gui/JEI/wiremill.png"), new int[][]{{53, 25}}, new int[][]{{107, 25}}, new ItemStack(TTrIBF.electricMachine1, 1, MachineType3.WIREMILL.ordinal()));
		addRecipeMapDisplay(registry, TemplateRecipeMap.LATHE, new ResourceLocation("ttr", "textures/gui/JEI/lathe.png"), new int[][]{{53, 25}}, new int[][]{{107, 25}, {125, 25}}, new int[][]{{53, 63}}, new int[0][], new ItemStack(TTrIBF.electricMachine1, 1, MachineType3.LATHE.ordinal()));
		addRecipeMapDisplay(registry, TemplateRecipeMap.CANNER, new ResourceLocation("ttr", "textures/gui/JEI/canner.png"), new int[][]{{35, 25}, {53, 25}, {35, 63}}, new int[][]{{107, 25}, {125, 25}}, new int[][]{{53, 63}}, new int[][]{{107, 63}}, new ItemStack(TTrIBF.electricMachine1, 1, MachineType3.CANNER.ordinal()));
		addRecipeMapDisplay(registry, TemplateRecipeMap.PYROLYSIS, new ResourceLocation("ttr", "textures/gui/JEI/pyrolysisor.png"), new int[][]{{53, 25}}, new int[][]{{107, 25}}, new int[][]{{53, 63}}, new int[][]{{107, 63}}, new ItemStack(TTrIBF.electricMachine1, 1, MachineType3.PYROLYSISOR.ordinal()));
		addRecipeMapDisplay(registry, TemplateRecipeMap.ORE_WASHING, new ResourceLocation("ttr", "textures/gui/JEI/ore_washer.png"), new int[][]{{53, 25}}, new int[][]{{107, 25}, {125, 25}, {143, 25}}, new int[][]{{53, 63}}, new int[][]{{107, 63}}, new ItemStack(TTrIBF.electricMachine1, 1, MachineType3.ORE_WASHER.ordinal()));
		addRecipeMapDisplay(registry, TemplateRecipeMap.ASSEMBLE, new ResourceLocation("ttr", "textures/gui/JEI/assembler.png"), new int[][]{{35, 25}, {53, 25}}, new int[][]{{107, 25}}, new int[][]{{53, 63}}, new int[0][], new ItemStack(TTrIBF.electricMachine1, 1, MachineType3.ASSEMBLER.ordinal()));
	}
	
	private void addRecipeMapDisplay(IModRegistry registry, TemplateRecipeMap map, ResourceLocation guiLocation, int[][] inputSlots, int[][] outputSlots, ItemStack...machine)
	{
		addRecipeMapDisplay(registry, map, guiLocation, inputSlots, outputSlots, new int[0][], new int[0][], machine);
	}
	private void addRecipeMapDisplay(IModRegistry registry, TemplateRecipeMap map, ResourceLocation guiLocation, int[][] inputSlots, int[][] outputSlots, int[][] inputTanks, int[][] outputTanks, ItemStack...machine)
	{
		registry.addRecipeCategories(new TTrRecipeCategoryTemplate(registry.getJeiHelpers().getGuiHelper(), map, guiLocation, inputSlots, outputSlots, inputTanks, outputTanks));
		registry.addRecipes(ImmutableList.copyOf(map.getRecipes()));
		for(ItemStack stack : machine)
		{
			registry.addRecipeCategoryCraftingItem(stack, map.name);
		}
	}
	
	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) { }
}