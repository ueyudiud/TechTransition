package ttr.api.fluid;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidTTr extends Fluid
{
	public int color = 0xFFFFFFFF;

	public FluidTTr(String fluidName)
	{
		this(fluidName, fluidName);
	}
	public FluidTTr(String fluidName, String locate)
	{
		this(fluidName, "ttr", "blocks/fluids/" + locate);
	}
	public FluidTTr(String fluidName, String modid, String locate)
	{
		super(fluidName, new ResourceLocation(modid, locate),
				new ResourceLocation(modid, locate));
		FluidRegistry.registerFluid(this);
	}
	
	public FluidTTr setColor(int color)
	{
		this.color = color;
		return this;
	}

	@Override
	public int getColor()
	{
		return color;
	}
}