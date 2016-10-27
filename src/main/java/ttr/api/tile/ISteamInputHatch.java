package ttr.api.tile;

public interface ISteamInputHatch
{
	int getCapacity();
	
	int getSteamAmount();

	int inputSteam(int amount, boolean process);
}