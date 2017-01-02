/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.load.recipe;

import ic2.api.item.IC2Items;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;
import ttr.api.enums.EnumTools;
import ttr.api.util.SubTag;
import ttr.core.TTrRecipeHandler;
import ttr.load.TTrIBF;

/**
 * Created at 2016年12月20日 上午12:39:00
 * @author ueyudiud
 */
public class TTrToolRecipes
{
	public static void init()
	{
		TTrRecipeHandler.markRemoveCraftingOutput(IC2Items.getItem("forge_hammer"), IC2Items.getItem("cutter"));
		for(EnumMaterial material : EnumMaterial.getMaterials())
		{
			if(material != null && material.toolQuality >= 0)
			{
				addGenerateToolRecipes(material);
			}
		}
		TTrRecipeHandler.addShapedRecipe(TTrIBF.sub.get("mouldEmpty"), "xx", "xx", "fh", 'x', "plateSteel", 'f', EnumTools.file.orename(), 'h', EnumTools.hammer.orename());
		TTrRecipeHandler.addShapedRecipe(TTrIBF.sub.get("mouldPlate"), "h  ", " m ", "   ", 'm', "mouldEmpty", 'h', EnumTools.hammer.orename());
		TTrRecipeHandler.addShapedRecipe(TTrIBF.sub.get("mouldCasing"), " h ", " m ", "   ", 'm', "mouldEmpty", 'h', EnumTools.hammer.orename());
		TTrRecipeHandler.addShapedRecipe(TTrIBF.sub.get("mouldGear"), "  h", " m ", "   ", 'm', "mouldEmpty", 'h', EnumTools.hammer.orename());
	}
	
	protected static void addGenerateToolRecipes(EnumMaterial material)
	{
		EnumMaterial material2 = material.toolHandle;
		if(!material.contain(SubTag.SOFT_TOOL))
		{
			if(material.contain(SubTag.METAL))
			{
				String ingot = EnumOrePrefix.ingot.getDictName(material);
				String plate = EnumOrePrefix.plate.getDictName(material);
				String stick = EnumOrePrefix.stick.getDictName(material);
				String stick2 = EnumOrePrefix.stick.getDictName(material2);
				TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("axe", material), "pih", "ps ", "fs ", 'p', plate, 'i', ingot, 'h', EnumTools.hammer.orename(), 'f', EnumTools.file.orename(), 's', stick2);
				TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("pickaxe", material), "pii", "fsh", " s ", 'p', plate, 'i', ingot, 'h', EnumTools.hammer.orename(), 'f', EnumTools.file.orename(), 's', stick2);
				TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("shovel", material), "fph", " s ", " s ", 'p', plate, 'h', EnumTools.hammer.orename(), 'f', EnumTools.file.orename(), 's', stick2);
				TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("sword", material), " p ", "fph", " s ", 'p', plate, 'i', ingot, 'h', EnumTools.hammer.orename(), 'f', EnumTools.file.orename(), 's', stick2);
				TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("hoe", material), "pih", "fs ", " s ", 'p', plate, 'i', ingot, 'h', EnumTools.hammer.orename(), 'f', EnumTools.file.orename(), 's', stick2);
				TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("hammer", material), "iii", "iii", " s ", 'i', ingot, 'h', EnumTools.hammer.orename(), 's', stick2);
				TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("wrench", material), "ihi", "iii", " i ", 'i', ingot, 'h', EnumTools.hammer.orename());
				TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("file", material), "p", "p", "s", 'p', plate, 's', stick2);
				TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("saw", material), "sss", "pps", "fh ", 'p', plate, 'h', EnumTools.hammer.orename(), 'f', EnumTools.file.orename(), 's', stick2);
				TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("screwdriver", material), " hi", " if", "s  ", 'i', stick, 'h', EnumTools.hammer.orename(), 'f', EnumTools.file.orename(), 's', stick2);
				TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("cutter", material), "pfp", "dph", "sis", 'p', plate, 'i', EnumOrePrefix.screw.getDictName(material), 's', stick, 'f', EnumTools.file.orename(), 'h', EnumTools.hammer.orename(), 'd', EnumTools.screwdriver.orename());
			}
			else if(EnumOrePrefix.gem.access(material))
			{
				String gem = EnumOrePrefix.gem.getDictName(material);
				String stick2 = EnumOrePrefix.stick.getDictName(material2);
				if(material.contain(SubTag.ROCKY))
				{
					TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("axe", material), "gg", "gs", " s", 'g', gem, 's', stick2);
					TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("pickaxe", material), "ggg", " s ", " s ", 'g', gem, 's', stick2);
					TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("shovel", material), "g", "s", "s", 'g', gem, 's', stick2);
					TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("sword", material), "g", "g", "s", 'g', gem, 's', stick2);
					TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("hoe", material), "gg", " s", " s", 'g', gem, 's', stick2);
				}
				else
				{
					TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("axe", material), "gg", "gs", "fs", 'g', gem, 'f', EnumTools.file.orename(), 's', stick2);
					TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("pickaxe", material), "ggg", "fs ", " s ", 'g', gem, 'f', EnumTools.file.orename(), 's', stick2);
					TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("shovel", material), "fg", " s", " s", 'g', gem, 'f', EnumTools.file.orename(), 's', stick2);
					TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("sword", material), " g", "fg", " s", 'g', gem, 'f', EnumTools.file.orename(), 's', stick2);
					TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("hoe", material), "gg", "fs", " s", 'g', gem, 'f', EnumTools.file.orename(), 's', stick2);
				}
			}
		}
		else
		{
			if(material == EnumMaterial.Wood)
			{
				TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("soft_hammer", material), "iii", "iii", " s ", 'i', "plankWood", 's', "stickWood");
			}
			else if(material.contain(SubTag.POLYMER))
			{
				TTrRecipeHandler.addShapedRecipe(TTrIBF.tool.makeTool("soft_hammer", material), "iii", "iii", " s ", 'i', EnumOrePrefix.ingot.getDictName(material), 's', EnumOrePrefix.stick.getDictName(material2));
			}
		}
	}
}