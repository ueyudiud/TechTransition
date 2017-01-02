/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.api.item;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

/**
 * Created at 2016年12月28日 下午9:13:01
 * @author ueyudiud
 */
public interface ITurbine
{
	@Nullable TurbineInfo getTurbineInfo(ItemStack stack);
	
	public static class TurbineInfo
	{
		public final float efficiency;
		public final int maxTransferLimit;
		public final int maxOutput;
		
		public TurbineInfo(int maxTransferLimit, float efficiency, int maxOutput)
		{
			this.efficiency = efficiency;
			this.maxOutput = maxOutput;
			this.maxTransferLimit = maxTransferLimit;
		}
	}
}