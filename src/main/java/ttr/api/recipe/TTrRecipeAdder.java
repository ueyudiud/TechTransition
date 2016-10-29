package ttr.api.recipe;

import ttr.api.stack.AbstractStack;

public class TTrRecipeAdder
{
	public static final int[] FULL_CHANCES = {10000};

	public static void addGrindingRecipe(AbstractStack input, AbstractStack output, long duration, long power)
	{
		TemplateRecipeMap.GRINDING      .addRecipe(new AbstractStack[]{input}, null, new AbstractStack[]{output}, (int[][]) null, null, duration, power, 0, null);
		TemplateRecipeMap.GRINDING_STEAM.addRecipe(new AbstractStack[]{input}, null, new AbstractStack[]{output}, (int[][]) null, null, duration, power, 0, null);
	}

	public static void addGrindingRecipe(AbstractStack input, AbstractStack output1, AbstractStack output2, int[] chances2, int duration, int power)
	{
		TemplateRecipeMap.GRINDING      .addRecipe(new AbstractStack[]{input}, null, new AbstractStack[]{output1, output2}, new int[][]{FULL_CHANCES, chances2}, null, duration, power, 0, null);
		TemplateRecipeMap.GRINDING_STEAM.addRecipe(new AbstractStack[]{input}, null, new AbstractStack[]{output1, output2}, new int[][]{FULL_CHANCES, chances2}, null, duration, power, 0, null);
	}

	public static void addGrindingRecipe(AbstractStack input, AbstractStack output1, AbstractStack output2, int[] chances2, AbstractStack output3, int[] chances3, int duration, int power)
	{
		TemplateRecipeMap.GRINDING      .addRecipe(new AbstractStack[]{input}, null, new AbstractStack[]{output1, output2, output3}, new int[][]{FULL_CHANCES, chances2, chances3}, null, duration, power, 0, null);
		TemplateRecipeMap.GRINDING_STEAM.addRecipe(new AbstractStack[]{input}, null, new AbstractStack[]{output1, output2}, new int[][]{FULL_CHANCES, chances2}, null, duration, power, 0, null);
	}

	public static void addGrindingRecipe(AbstractStack input, AbstractStack outputs1a, AbstractStack outputs1b, int duration, int power)
	{
		TemplateRecipeMap.GRINDING      .addRecipe(new AbstractStack[]{input}, null, new AbstractStack[]{outputs1a}, (int[][]) null, null, duration, power, 0, null);
		TemplateRecipeMap.GRINDING_STEAM.addRecipe(new AbstractStack[]{input}, null, new AbstractStack[]{outputs1b}, (int[][]) null, null, duration, power, 0, null);
	}

	public static void addGrindingRecipe(AbstractStack input, AbstractStack outputs1a, AbstractStack outputs1b, AbstractStack output2, int[] chances2, int duration, int power)
	{
		TemplateRecipeMap.GRINDING      .addRecipe(new AbstractStack[]{input}, null, new AbstractStack[]{outputs1a, output2}, new int[][]{FULL_CHANCES, chances2}, null, duration, power, 0, null);
		TemplateRecipeMap.GRINDING_STEAM.addRecipe(new AbstractStack[]{input}, null, new AbstractStack[]{outputs1b, output2}, new int[][]{FULL_CHANCES, chances2}, null, duration, power, 0, null);
	}

	public static void addGrindingRecipe(AbstractStack input, AbstractStack outputs1a, AbstractStack outputs1b, AbstractStack output2, int[] chances2, AbstractStack output3, int[] chances3, int duration, int power)
	{
		TemplateRecipeMap.GRINDING      .addRecipe(new AbstractStack[]{input}, null, new AbstractStack[]{outputs1a, output2, output3}, new int[][]{FULL_CHANCES, chances2, chances3}, null, duration, power, 0, null);
		TemplateRecipeMap.GRINDING_STEAM.addRecipe(new AbstractStack[]{input}, null, new AbstractStack[]{outputs1b, output2}, new int[][]{FULL_CHANCES, chances2}, null, duration, power, 0, null);
	}

	public static void addGrindingRecipe(AbstractStack input, AbstractStack[] outputs1, int[][] chances1, AbstractStack[] outputs2, int[][] chances2, int duration, int power)
	{
		TemplateRecipeMap.GRINDING      .addRecipe(new AbstractStack[]{input}, null, outputs1, chances1, null, duration, power, 0, null);
		TemplateRecipeMap.GRINDING_STEAM.addRecipe(new AbstractStack[]{input}, null, outputs2, chances2, null, duration, power, 0, null);
	}
	
	public static void addExtractRecipe(AbstractStack input, AbstractStack outputa, AbstractStack outputb, int duration, int power)
	{
		TemplateRecipeMap.EXTRACT      .addRecipe(input, duration, power, outputa);
		TemplateRecipeMap.EXTRACT_STEAM.addRecipe(input, duration, power, outputb);
	}
	
	public static void addExtractRecipe(AbstractStack input, AbstractStack output, int duration, int power) {addExtractRecipe(input, output, output, duration, power);}
}