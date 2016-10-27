package ttr.api.tile;

public interface IPluginAccess
{
	String tankCapacity = "C";
	String energyCapacity = "E";
	String overclock = "O";
	String lock = "L";
	String voltageUpgrade = "V";
	String fuse = "F";
	
	int getPluginLevel(String key);
	
	void removePlugin(String key);

	boolean addPlugin(String key, int level);
}