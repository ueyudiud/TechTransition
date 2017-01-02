package ttr.core.handler;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import ttr.api.enums.EnumOrePrefix;
import ttr.api.fuel.ITTrFuelHandler;
import ttr.api.stack.BaseStack;
import ttr.api.stack.OreStack;
import ttr.api.util.IDataChecker;
import ttr.api.util.ISubTagContainer;
import ttr.api.util.MaterialStack;
import ttr.api.util.SubTag;
import ttr.core.TTrMaterialHandler;

public class FuelHandler implements ITTrFuelHandler
{
	private final OreStack LOG_check = new OreStack("logWood");
	private final OreStack TREE_SAPLING_check = new OreStack("treeSapling");
	private final OreStack WOOD_SLAB_check = new OreStack("slabWood");
	private final OreStack POWERED_LAVA_check = new OreStack("lavaPowered");
	private final OreStack SULFUR_check = new OreStack("dustSulfur");
	private final OreStack ASH = new OreStack("dustTinyAsh");
	
	private final FuelInfo COAL			= new FuelInfo(1000, 20, 1600).setOutput(this.ASH);
	private final FuelInfo COAL_BLOCK	= new FuelInfo(1000, 20, 16000).setOutput(new OreStack("dustAsh"));
	private final FuelInfo WOODEN		= new FuelInfo(600 , 16, 200).setOutput(this.ASH);
	private final FuelInfo LOG			= new FuelInfo(750 , 20, 200).setOutput(this.ASH);
	public static final FuelInfo LAVA	= new FuelInfo(1200, 10, 20000);
	private final FuelInfo POWERED_LAVA = new FuelInfo(2000, 100,3000);
	private final FuelInfo BLAZE		= new FuelInfo(1500, 80, 500);
	private final FuelInfo BLAZE_POWDER	= new FuelInfo(1500, 80, 125);
	private final FuelInfo TREE_SAPLING = new FuelInfo(700,  16, 100);
	//	private final FuelInfo SULFUR       = new FuelInfo(750,  40, 400).setEffect(new PotionEffect(MobEffects.POISON, 5, 1));
	
	private static final IDataChecker<ISubTagContainer> CHECKER = new IDataChecker.Not(SubTag.NOT_BURNABLE);
	
	@Override
	public FuelInfo getFuelValue(ItemStack stack, boolean highQualityReq)
	{
		MaterialStack stack1 = TTrMaterialHandler.getMaterialStack(stack, CHECKER);
		if(stack1 != null)
		{
			if(stack1.material.fuelValue > 0 && (!highQualityReq || stack1.material.highQuality))
			{
				int amount = (int) (stack1.material.fuelValue * stack1.amount / EnumOrePrefix.dust.amount);
				if(amount > 0)
				{
					FuelInfo info = new FuelInfo(stack1.material.fuelTemperature, stack1.material.fuelPower, amount);
					if(stack1.material.fuelOutput != null)
					{
						long a1 = stack1.amount / 9 / EnumOrePrefix.dustPowder.amount;
						if(a1 > 0)
						{
							if((a1 % 4) == 0)
							{
								info.setOutput(new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dustTiny, stack1.material.fuelOutput, a1 / 4)));
							}
							else
							{
								info.setOutput(new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dustPowder, stack1.material.fuelOutput, a1)));
							}
						}
					}
					if(stack1.material.fuelEffect != null)
					{
						info.setEffect(new PotionEffect(stack1.material.fuelEffect.getPotion(), 40, stack1.material.fuelEffect.getAmplifier(), stack1.material.fuelEffect.getIsAmbient(), stack1.material.fuelEffect.doesShowParticles()));
					}
					return info;
				}
			}
		}
		if(this.TREE_SAPLING_check.similar(stack))
			return this.TREE_SAPLING;
		Item item = stack.getItem();
		if(item instanceof ItemBlock)
		{
			Block block = Block.getBlockFromItem(stack.getItem());
			if(block.getDefaultState().getMaterial() == Material.WOOD)
			{
				if(this.LOG_check.similar(stack))
					return this.LOG;
				if(!highQualityReq)
					return this.WOODEN;
			}
			if(!highQualityReq && this.WOOD_SLAB_check.similar(stack))
				return this.WOODEN;
			if(block == Blocks.COAL_BLOCK)
				return this.COAL_BLOCK;
		}
		if(!highQualityReq)
		{
			if(item == Items.BLAZE_POWDER)
				return this.BLAZE_POWDER;
			else if(item == Items.BLAZE_ROD)
				return this.BLAZE;
			else if(item == Items.LAVA_BUCKET)
				return LAVA;
			else if(this.POWERED_LAVA_check.contain(stack))
				return this.POWERED_LAVA;
		}
		if(item == Items.COAL)
			return this.COAL;
		return null;
	}
}