package ttr.api.item;

import java.util.List;

import net.minecraft.item.ItemStack;
import ttr.api.enums.EnumTools;

public interface ITool
{
	List<EnumTools> getToolTypes(ItemStack stack);
	
	void onToolUse(ItemStack stack, EnumTools toolType, float amount);
}