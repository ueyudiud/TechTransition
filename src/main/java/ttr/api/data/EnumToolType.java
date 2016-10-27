package ttr.api.data;

import net.minecraft.item.ItemStack;
import ttr.api.stack.OreStack;

public enum EnumToolType
{
	hand("Hand"),//The player hand current, do not register tool with this.
	axe("Axe"),
	hard_hammer("HardHammer"),
	soft_hammer("SoftHammer"),
	pickaxe("Pickaxe"),
	shovel("Shovel"),
	sword("Sword"),
	knife("Knife"),
	hoe("Hoe"),
	file("File"),
	saw("Saw"),
	wrench("Wrench");

	String name;
	OreStack stack;

	EnumToolType(String name)
	{
		stack = new OreStack(this.name = ("craftingTool" + name));
	}
	
	public OreStack stack()
	{
		return stack;
	}

	public String ore()
	{
		return name;
	}

	public boolean match(ItemStack stack)
	{
		return stack != null && stack().similar(stack);
	}
}