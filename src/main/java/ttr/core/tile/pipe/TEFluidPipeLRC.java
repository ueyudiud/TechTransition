package ttr.core.tile.pipe;

public class TEFluidPipeLRC extends TEFluidPipe
{
	public TEFluidPipeLRC(int capacity, long maxTemperature, boolean canCurrentGas, int flowSpeed)
	{
		super(capacity, maxTemperature, canCurrentGas, flowSpeed);
	}

	public static class TEFluidPipeCopperSmall extends TEFluidPipeLRC
	{
		public TEFluidPipeCopperSmall()
		{
			super(100, 800, true, 25);
		}
	}
	public static class TEFluidPipeCopperMiddle extends TEFluidPipeLRC
	{
		public TEFluidPipeCopperMiddle()
		{
			super(200, 800, true, 50);
		}
	}
	public static class TEFluidPipeCopperBig extends TEFluidPipeLRC
	{
		public TEFluidPipeCopperBig()
		{
			super(300, 800, true, 75);
		}
	}

	public static class TEFluidPipeBronzeSmall extends TEFluidPipeLRC
	{
		public TEFluidPipeBronzeSmall()
		{
			super(160, 1400, true, 35);
		}
	}
	public static class TEFluidPipeBronzeMiddle extends TEFluidPipeLRC
	{
		public TEFluidPipeBronzeMiddle()
		{
			super(320, 1400, true, 70);
		}
	}
	public static class TEFluidPipeBronzeBig extends TEFluidPipeLRC
	{
		public TEFluidPipeBronzeBig()
		{
			super(480, 1400, true, 105);
		}
	}

	public static class TEFluidPipeSteelSmall extends TEFluidPipeLRC
	{
		public TEFluidPipeSteelSmall()
		{
			super(300, 1700, true, 50);
		}
	}
	public static class TEFluidPipeSteelMiddle extends TEFluidPipeLRC
	{
		public TEFluidPipeSteelMiddle()
		{
			super(600, 1700, true, 100);
		}
	}
	public static class TEFluidPipeSteelBig extends TEFluidPipeLRC
	{
		public TEFluidPipeSteelBig()
		{
			super(900, 1700, true, 150);
		}
	}

	public static class TEFluidPipeTungstenSmall extends TEFluidPipeLRC
	{
		public TEFluidPipeTungstenSmall()
		{
			super(600, 3500, true, 100);
		}
	}
	public static class TEFluidPipeTungstenMiddle extends TEFluidPipeLRC
	{
		public TEFluidPipeTungstenMiddle()
		{
			super(1200, 3500, true, 200);
		}
	}
	public static class TEFluidPipeTungstenBig extends TEFluidPipeLRC
	{
		public TEFluidPipeTungstenBig()
		{
			super(1800, 3500, true, 300);
		}
	}
}