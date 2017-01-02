package ttr.api.enums;

import net.minecraft.item.ItemStack;
import ttr.api.stack.OreStack;

public enum EnumTools
{
	hand("Hand"),//The player hand current, do not register tool with this.
	axe("Axe"),
	hammer("HardHammer"),
	soft_hammer("SoftHammer"),
	pickaxe("Pickaxe"),
	shovel("Shovel"),
	sword("Sword"),
	knife("Knife"),
	hoe("Hoe"),
	file("File"),
	saw("Saw"),
	wrench("Wrench"),
	screwdriver("Screwdriver"),
	cutter("Cutter");
	
	String name;
	OreStack stack;
	
	EnumTools(String name)
	{
		this.stack = new OreStack(this.name = ("craftingTool" + name));
	}
	
	public OreStack stack()
	{
		return this.stack;
	}
	
	public String orename()
	{
		return this.name;
	}
	
	public boolean match(ItemStack stack)
	{
		return stack != null && stack().similar(stack);
	}
}