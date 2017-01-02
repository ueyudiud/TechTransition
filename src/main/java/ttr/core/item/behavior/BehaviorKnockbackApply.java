/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.item.behavior;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import ttr.api.item.BehaviorBase;

/**
 * Created at 2016年12月20日 上午12:11:15
 * @author ueyudiud
 */
public class BehaviorKnockbackApply extends BehaviorBase
{
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if(entity instanceof EntityLivingBase)
		{
			((EntityLivingBase) entity).knockBack(player, 0.5F, MathHelper.sin(player.rotationYaw * 0.017453292F), (-MathHelper.cos(player.rotationYaw * 0.017453292F)));
		}
		return super.onLeftClickEntity(stack, player, entity);
	}
}