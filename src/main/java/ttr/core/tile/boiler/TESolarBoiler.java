package ttr.core.tile.boiler;

public abstract class TESolarBoiler extends TEBoiler
{
	public TESolarBoiler(int maxTemperature, float outputPressure, int waterCap, int steamCap, float efficiency)
	{
		super(maxTemperature, outputPressure, waterCap, steamCap, efficiency);
	}
}