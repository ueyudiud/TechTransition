package farcore.debug;

import ttr.api.data.M;
import ttr.api.data.MC;
import ttr.api.material.MatCondition;

@Deprecated
public class Debug
{
	public static void main(String[] args)
	{
		MC.init();
		M.init();
		String sourceLocate = "D:/Program Files/minecraft/f/forge-1.10.2-12.18.1.2011-mdk-other/src/main/resources/assets";
		//		String srcDirName = "";
		//		String destDirName = "";
		//		String formatName = "chiseled.png";
		//		TextureCopier.copyTarget(srcDirName, destDirName, formatName);
		for(MatCondition condition : MatCondition.register)
		{
			ModelFileCreator.provideGroupItemInfo(sourceLocate, condition);
		}
	}
}