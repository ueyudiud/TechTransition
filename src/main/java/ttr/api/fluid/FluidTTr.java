package ttr.api.fluid;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FluidTTr extends Fluid
{
	@SideOnly(Side.CLIENT)
	public static void registerTextures(TextureMap map)
	{
		for(ResourceLocation location : textures)
		{
			map.registerSprite(location);
		}
	}

	private static final List<ResourceLocation> textures = new ArrayList();

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
		textures.add(still);
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