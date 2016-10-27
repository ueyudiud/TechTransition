package ttr.api.item;

import java.util.List;

import net.minecraft.item.ItemStack;
import ttr.api.data.EnumToolType;

public interface ITool
{
	List<EnumToolType> getToolTypes(ItemStack stack);
	
	void onToolUse(ItemStack stack, EnumToolType toolType, float amount);
}