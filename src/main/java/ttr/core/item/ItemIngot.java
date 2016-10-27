package ttr.core.item;

import ttr.api.item.ItemMulti;
import ttr.api.material.MatCondition;
import ttr.core.TTr;

public class ItemIngot extends ItemMulti
{
	public ItemIngot(MatCondition mc)
	{
		super(TTr.MODID, mc);
	}

}
