package ttr.core.tile.pipe;

public class TEFluidPipeMRC extends TEFluidPipe
{
	public TEFluidPipeMRC(int capacity, int size, long maxTemperature, boolean canCurrentGas, int flowSpeed)
	{
		super(capacity, size, maxTemperature, canCurrentGas, flowSpeed);
	}
	
	public static class TEFluidPipeStainlessSteelSmall extends TEFluidPipeMRC
	{
		public TEFluidPipeStainlessSteelSmall()
		{
			super(400, 3, 2000, true, 60);
		}
	}
	public static class TEFluidPipeStainlessSteelMiddle extends TEFluidPipeMRC
	{
		public TEFluidPipeStainlessSteelMiddle()
		{
			super(800, 4, 2000, true, 120);
		}
	}
	public static class TEFluidPipeStainlessSteelBig extends TEFluidPipeMRC
	{
		public TEFluidPipeStainlessSteelBig()
		{
			super(1200, 5, 2000, true, 180);
		}
	}
	
	public static class TEFluidPipeTitaniumSmall extends TEFluidPipeMRC
	{
		public TEFluidPipeTitaniumSmall()
		{
			super(600, 3, 2400, true, 100);
		}
	}
	public static class TEFluidPipeTitaniumMiddle extends TEFluidPipeMRC
	{
		public TEFluidPipeTitaniumMiddle()
		{
			super(1200, 4, 2400, true, 200);
		}
	}
	public static class TEFluidPipeTitaniumBig extends TEFluidPipeMRC
	{
		public TEFluidPipeTitaniumBig()
		{
			super(2400, 5, 2400, true, 300);
		}
	}
	
	public static class TEFluidPipeTungstenSteelSmall extends TEFluidPipeMRC
	{
		public TEFluidPipeTungstenSteelSmall()
		{
			super(1000, 3, 2400, true, 180);
		}
	}
	public static class TEFluidPipeTungstenSteelMiddle extends TEFluidPipeMRC
	{
		public TEFluidPipeTungstenSteelMiddle()
		{
			super(2000, 4, 2400, true, 360);
		}
	}
	public static class TEFluidPipeTungstenSteelBig extends TEFluidPipeMRC
	{
		public TEFluidPipeTungstenSteelBig()
		{
			super(3000, 5, 2400, true, 540);
		}
	}
}