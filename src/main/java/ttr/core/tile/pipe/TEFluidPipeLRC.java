package ttr.core.tile.pipe;

public class TEFluidPipeLRC extends TEFluidPipe
{
	public TEFluidPipeLRC(int capacity, int size, long maxTemperature, boolean canCurrentGas, int flowSpeed)
	{
		super(capacity, size, maxTemperature, canCurrentGas, flowSpeed);
	}
	
	public static class TEFluidPipeCopperSmall extends TEFluidPipeLRC
	{
		public TEFluidPipeCopperSmall()
		{
			super(100, 3, 800, true, 25);
		}
	}
	public static class TEFluidPipeCopperMiddle extends TEFluidPipeLRC
	{
		public TEFluidPipeCopperMiddle()
		{
			super(200, 4, 800, true, 50);
		}
	}
	public static class TEFluidPipeCopperBig extends TEFluidPipeLRC
	{
		public TEFluidPipeCopperBig()
		{
			super(300, 5, 800, true, 75);
		}
	}
	
	public static class TEFluidPipeBronzeSmall extends TEFluidPipeLRC
	{
		public TEFluidPipeBronzeSmall()
		{
			super(160, 3, 1400, true, 35);
		}
	}
	public static class TEFluidPipeBronzeMiddle extends TEFluidPipeLRC
	{
		public TEFluidPipeBronzeMiddle()
		{
			super(320, 4, 1400, true, 70);
		}
	}
	public static class TEFluidPipeBronzeBig extends TEFluidPipeLRC
	{
		public TEFluidPipeBronzeBig()
		{
			super(480, 5, 1400, true, 105);
		}
	}
	
	public static class TEFluidPipeSteelSmall extends TEFluidPipeLRC
	{
		public TEFluidPipeSteelSmall()
		{
			super(300, 3, 1700, true, 50);
		}
	}
	public static class TEFluidPipeSteelMiddle extends TEFluidPipeLRC
	{
		public TEFluidPipeSteelMiddle()
		{
			super(600, 4, 1700, true, 100);
		}
	}
	public static class TEFluidPipeSteelBig extends TEFluidPipeLRC
	{
		public TEFluidPipeSteelBig()
		{
			super(900, 5, 1700, true, 150);
		}
	}
	
	public static class TEFluidPipeTungstenSmall extends TEFluidPipeLRC
	{
		public TEFluidPipeTungstenSmall()
		{
			super(600, 3, 3500, true, 100);
		}
	}
	public static class TEFluidPipeTungstenMiddle extends TEFluidPipeLRC
	{
		public TEFluidPipeTungstenMiddle()
		{
			super(1200, 4, 3500, true, 200);
		}
	}
	public static class TEFluidPipeTungstenBig extends TEFluidPipeLRC
	{
		public TEFluidPipeTungstenBig()
		{
			super(1800, 5, 3500, true, 300);
		}
	}
}