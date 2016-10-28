package ttr.load;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ttr.api.fluid.FluidTTr;
import ttr.api.material.Mat;
import ttr.api.util.SubTag;

public class TTrFluids
{
	public static Fluid distilledwater;
	public static Fluid steam;
	public static Fluid sulphuric_acid;
	public static Fluid nitric_acid;
	public static Fluid redalloy;

	public static Map<Mat, Fluid> sulfate_impure;
	public static Map<Mat, Fluid> chlorhydric_impure;
	public static Map<Mat, Fluid> nitric_impure;
	
	public static Map<Mat, Fluid> sulfate;
	public static Map<Mat, Fluid> chlorhydric;
	public static Map<Mat, Fluid> nitric;

	public static void init()
	{
		ImmutableMap.Builder<Mat, Fluid> builder1 = ImmutableMap.builder();
		ImmutableMap.Builder<Mat, Fluid> builder2 = ImmutableMap.builder();
		ImmutableMap.Builder<Mat, Fluid> builder3 = ImmutableMap.builder();
		ImmutableMap.Builder<Mat, Fluid> builder4 = ImmutableMap.builder();
		ImmutableMap.Builder<Mat, Fluid> builder5 = ImmutableMap.builder();
		ImmutableMap.Builder<Mat, Fluid> builder6 = ImmutableMap.builder();
		for(Mat material : Mat.register())
		{
			if(material.contain(SubTag.SULFATE_SOLUTABLE))
			{
				Fluid fluid = FluidRegistry.getFluid("sulfate." + material.name);
				if(fluid == null)
				{
					fluid = new FluidTTr("sulfate." + material.name, "sulfate").setColor(material.saltColor).setDensity(1800).setViscosity(1050);
				}
				builder1.put(material, fluid);
				fluid = FluidRegistry.getFluid("sulfate.impure." + material.name);
				if(fluid == null)
				{
					fluid = new FluidTTr("sulfate.impure." + material.name, "sulfate_impure").setColor(material.saltColor).setDensity(1800).setViscosity(1200);
				}
				builder4.put(material, fluid);
			}
			if(material.contain(SubTag.CHLORHYDRIC_SOLUTABLE))
			{
				Fluid fluid = FluidRegistry.getFluid("chlorhydric." + material.name);
				if(fluid == null)
				{
					fluid = new FluidTTr("chlorhydric." + material.name, "chlorhydric").setColor(material.saltColor).setDensity(1800).setViscosity(1050);
				}
				builder2.put(material, fluid);
				fluid = FluidRegistry.getFluid("chlorhydric.impure." + material.name);
				if(fluid == null)
				{
					fluid = new FluidTTr("chlorhydric.impure." + material.name, "chlorhydric_impure").setColor(material.saltColor).setDensity(1800).setViscosity(1200);
				}
				builder5.put(material, fluid);
			}
			if(material.contain(SubTag.NITRIC_SOLUTABLE))
			{
				Fluid fluid = FluidRegistry.getFluid("nitric." + material.name);
				if(fluid == null)
				{
					fluid = new FluidTTr("nitric." + material.name, "nitric").setColor(material.saltColor).setDensity(1800).setViscosity(1050);
				}
				builder3.put(material, fluid);
				fluid = FluidRegistry.getFluid("nitric.impure." + material.name);
				if(fluid == null)
				{
					fluid = new FluidTTr("nitric.impure." + material.name, "nitric_impure").setColor(material.saltColor).setDensity(1800).setViscosity(1200);
				}
				builder6.put(material, fluid);
			}
		}
		sulfate = builder1.build();
		chlorhydric = builder2.build();
		nitric = builder3.build();
		sulfate_impure = builder4.build();
		chlorhydric_impure = builder5.build();
		nitric_impure = builder6.build();
		distilledwater = FluidRegistry.getFluid("ic2distilledwater");
		steam = FluidRegistry.getFluid("steam");
		if(steam == null)
		{
			steam = new FluidTTr("steam").setDensity(-300).setViscosity(60).setTemperature(500);
		}
		sulphuric_acid = FluidRegistry.getFluid("sulfuricacid");
		if(sulphuric_acid == null)
		{
			sulphuric_acid = new FluidTTr("sulfuricacid").setDensity(1890).setViscosity(2100);
		}
		nitric_acid = FluidRegistry.getFluid("nitricacid");
		if(nitric_acid == null)
		{
			nitric_acid = new FluidTTr("nitricacid").setDensity(1420).setViscosity(1600);
		}
		redalloy = FluidRegistry.getFluid("redalloy");
		if(redalloy == null)
		{
			redalloy = new FluidTTr("redalloy").setDensity(3200).setViscosity(3500).setTemperature(850);
		}
		MinecraftForge.EVENT_BUS.register(new TTrFluids());
	}
	
	@SubscribeEvent
	public void onIconMapReload(TextureStitchEvent.Pre event)
	{
		FluidTTr.registerTextures(event.getMap());
	}
}