package ttr.core.tile.pipe;

public class TEFluidPipeMRC extends TEFluidPipe
{
	public TEFluidPipeMRC(int capacity, long maxTemperature, boolean canCurrentGas, int flowSpeed)
	{
		super(capacity, maxTemperature, canCurrentGas, flowSpeed);
	}

	public static class TEFluidPipeStainlessSteelSmall extends TEFluidPipeMRC
	{
		public TEFluidPipeStainlessSteelSmall()
		{
			super(400, 2000, true, 60);
		}
	}
	public static class TEFluidPipeStainlessSteelMiddle extends TEFluidPipeMRC
	{
		public TEFluidPipeStainlessSteelMiddle()
		{
			super(800, 2000, true, 120);
		}
	}
	public static class TEFluidPipeStainlessSteelBig extends TEFluidPipeMRC
	{
		public TEFluidPipeStainlessSteelBig()
		{
			super(1200, 2000, true, 180);
		}
	}
	
	public static class TEFluidPipeTitaniumSmall extends TEFluidPipeMRC
	{
		public TEFluidPipeTitaniumSmall()
		{
			super(600, 2400, true, 100);
		}
	}
	public static class TEFluidPipeTitaniumMiddle extends TEFluidPipeMRC
	{
		public TEFluidPipeTitaniumMiddle()
		{
			super(1200, 2400, true, 200);
		}
	}
	public static class TEFluidPipeTitaniumBig extends TEFluidPipeMRC
	{
		public TEFluidPipeTitaniumBig()
		{
			super(2400, 2400, true, 300);
		}
	}
	
	public static class TEFluidPipeTungstenSteelSmall extends TEFluidPipeMRC
	{
		public TEFluidPipeTungstenSteelSmall()
		{
			super(1000, 2400, true, 180);
		}
	}
	public static class TEFluidPipeTungstenSteelMiddle extends TEFluidPipeMRC
	{
		public TEFluidPipeTungstenSteelMiddle()
		{
			super(2000, 2400, true, 360);
		}
	}
	public static class TEFluidPipeTungstenSteelBig extends TEFluidPipeMRC
	{
		public TEFluidPipeTungstenSteelBig()
		{
			super(3000, 2400, true, 540);
		}
	}
}