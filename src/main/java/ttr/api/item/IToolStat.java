package ttr.api.item;

import ic2.api.item.ICustomDamageItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ttr.api.material.Mat;

public interface IToolStat
{
	void onToolCrafted(ItemStack stack, EntityPlayer player);
	
	int getToolDamagePerBreak(ItemStack stack, EntityLivingBase user, World world, BlockPos pos, IBlockState block);

	int getToolDamagePerAttack(ItemStack stack, EntityLivingBase user, Entity target);

	int getToolDamagePerCraft(ItemStack stack);
	
	default float getStrVsBlock(ItemStack stack, Mat material, IBlockState state)
	{
		return isToolEffective(stack, state) ? material.toolHardness : 1F;
	}
	
	boolean isToolEffective(ItemStack stack, IBlockState state);

	default float getAttackSpeed(ItemStack stack, Mat material)
	{
		return (1F + getAttackSpeed(stack)) * material.toolAttackSpeed - 1F;
	}

	float getDamageVsEntity(ItemStack stack, Mat material);
	
	float getAttackSpeed(ItemStack stack);
	
	float getMaxDurabilityMultiplier();
	
	int getToolHarvestLevel(ItemStack stack, String toolClass, Mat baseMaterial);

	default boolean canHarvestDrop(ItemStack stack, IBlockState state)
	{
		return false;
	}

	boolean canBlock();
	
	/**
	 * Helper method.
	 * @param stack
	 * @param amount
	 * @param src
	 */
	default void applyDamage(ItemStack stack, int amount, EntityLivingBase src)
	{
		if(stack.getItem() instanceof ICustomDamageItem)
		{
			((ICustomDamageItem) stack.getItem()).applyCustomDamage(stack, amount, src);
		}
		else
		{
			stack.damageItem(amount, src);
		}
	}
}