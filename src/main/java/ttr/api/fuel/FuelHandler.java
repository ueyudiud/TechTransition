package ttr.api.fuel;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ttr.api.fuel.ITTrFuelHandler.FuelInfo;

public class FuelHandler
{
	public static boolean canClassicFuelBurn;
	private static final List<ITTrFuelHandler> list = new ArrayList();
	
	public static void addFuelHandler(ITTrFuelHandler fuelHandler)
	{
		list.add(fuelHandler);
	}
	
	public static FuelInfo getFuelInfo(ItemStack stack)
	{
		return getFuelInfo(stack, false);
	}
	public static FuelInfo getFuelInfo(ItemStack stack, boolean highQualityReq)
	{
		for(ITTrFuelHandler handler : list)
		{
			FuelInfo info = handler.getFuelValue(stack, highQualityReq);
			if(info != null)
				return info;
		}
		if(!highQualityReq && canClassicFuelBurn)
		{
			int time = GameRegistry.getFuelValue(stack);
			return new FuelInfo(time > 3000 ? 1200 : time > 1000 ? 1000 : 600, 10, time);
		}
		return null;
	}
	
	public static boolean isItemFuel(ItemStack stack)
	{
		return isItemFuel(stack, false);
	}
	
	public static boolean isItemFuel(ItemStack stack, boolean highQualityReq)
	{
		return getFuelInfo(stack, highQualityReq) != null;
	}
}