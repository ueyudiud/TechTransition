package ttr.core.handler;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import ttr.api.fuel.ITTrFuelHandler;
import ttr.api.stack.OreStack;

public class FuelHandler implements ITTrFuelHandler
{
	private final OreStack LOG_check = new OreStack("logWood");
	private final OreStack TREE_SAPLING_check = new OreStack("treeSapling");
	private final OreStack WOOD_SLAB_check = new OreStack("slabWood");
	private final OreStack POWERED_LAVA_check = new OreStack("lavaPowered");
	private final OreStack SULFUR_check = new OreStack("dustSulfur");
	private final OreStack ASH = new OreStack("dustTinyAsh");
	
	private final FuelInfo COAL			= new FuelInfo(1000, 20, 1600).setOutput(ASH);
	private final FuelInfo COAL_BLOCK	= new FuelInfo(1000, 20, 16000).setOutput(new OreStack("dustAsh"));
	private final FuelInfo WOODEN		= new FuelInfo(600 , 16, 200).setOutput(ASH);
	private final FuelInfo STICK		= new FuelInfo(600 , 16, 100);
	private final FuelInfo LOG			= new FuelInfo(750 , 20, 200).setOutput(ASH);
	public static final FuelInfo LAVA	= new FuelInfo(1200, 10, 20000);
	private final FuelInfo POWERED_LAVA = new FuelInfo(2000, 100,3000);
	private final FuelInfo BLAZE		= new FuelInfo(1500, 80, 500);
	private final FuelInfo BLAZE_POWDER	= new FuelInfo(1500, 80, 125);
	private final FuelInfo TREE_SAPLING = new FuelInfo(700,  16, 100);
	private final FuelInfo SULFUR       = new FuelInfo(750,  40, 400).setEffect(new PotionEffect(MobEffects.POISON, 5, 1));

	@Override
	public FuelInfo getFuelValue(ItemStack stack, boolean highQualityReq)
	{
		if(TREE_SAPLING_check.similar(stack))
			return TREE_SAPLING;
		if(SULFUR_check.similar(stack))
			return SULFUR;
		Item item = stack.getItem();
		if(item instanceof ItemBlock)
		{
			Block block = Block.getBlockFromItem(stack.getItem());
			if(block.getDefaultState().getMaterial() == Material.WOOD)
			{
				if(LOG_check.similar(stack))
					return LOG;
				if(!highQualityReq)
					return WOODEN;
			}
			if(!highQualityReq && WOOD_SLAB_check.similar(stack))
				return WOODEN;
			if(block == Blocks.COAL_BLOCK)
				return COAL_BLOCK;
		}
		if(!highQualityReq)
		{
			if(item == Items.STICK)
				return STICK;
			else if(item == Items.BLAZE_POWDER)
				return BLAZE_POWDER;
			else if(item == Items.BLAZE_ROD)
				return BLAZE;
			else if(item == Items.LAVA_BUCKET)
				return LAVA;
			else if(POWERED_LAVA_check.contain(stack))
				return POWERED_LAVA;
		}
		if(item == Items.COAL)
			return COAL;
		return null;
	}
}