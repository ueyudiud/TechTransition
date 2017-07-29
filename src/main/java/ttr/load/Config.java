package ttr.load;

import net.minecraftforge.common.config.Configuration;
import ttr.api.TTrAPI;

public class Config
{
	public static boolean removeErroredTile;
	public static boolean logHalfOutput;
	public static boolean disableIC2Tools;
	
	public static void init(Configuration config)
	{
		removeErroredTile = config.getBoolean("removeErroredTile", "general", false, "");
		logHalfOutput = config.getBoolean("logHalfOutput", "recipe", true, "Enable this option the log to plank recipe will only have half output without saw");
		disableIC2Tools = config.getBoolean("disableIC2Tools", "recipe", true, "");
		TTrAPI.enableAutoInputAndOutputItemAndFluid = config.getBoolean("enableAutoInputAndOutputItemAndFluid", "general", false,
				"You can input item and output item by right click machine if you enable this option, but this function"
						+ "may cause wrong operation.");
	}
}