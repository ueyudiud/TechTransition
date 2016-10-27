package ttr.api.fuel;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import ttr.api.stack.AbstractStack;

public interface ITTrFuelHandler
{
	FuelInfo getFuelValue(ItemStack stack, boolean highQualityReq);

	public static class FuelInfo
	{
		public final int maxTemp;
		public final int fuelPower;
		public final int burnTick;

		public FuelInfo(int maxT, int power, int tick)
		{
			maxTemp = maxT;
			fuelPower = power;
			burnTick = tick;
		}

		public FuelInfo setOutput(AbstractStack output)
		{
			this.output = output;
			return this;
		}
		
		public FuelInfo setEffect(PotionEffect effect)
		{
			this.effect = effect;
			return this;
		}

		public PotionEffect effect;
		public AbstractStack output;
	}
}