package ttr.api.data;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import ttr.api.inventory.IItemHandlerIO;
import ttr.api.inventory.StandardItemHandlerIO;

public class Capabilities
{
	//Pre-pre initialization
	static
	{
		CapabilityManager.INSTANCE.register(IItemHandlerIO.class, new IItemHandlerIO.ItemHandlerIOStorage(),
				StandardItemHandlerIO.class);
	}
	
	@CapabilityInject(IItemHandlerIO.class)
	public static Capability<IItemHandlerIO> ITEM_HANDLER_IO;
}