package ttr.core.gui.boiler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import ttr.api.inventory.WaterTank;
import ttr.core.gui.abstracts.ContainerTPC;
import ttr.core.gui.abstracts.FluidSlot;
import ttr.core.tile.boiler.TEBoiler;

public class ContainerBoiler<T extends TEBoiler> extends ContainerTPC<T>
{
	public ContainerBoiler(EntityPlayer player, T inventory)
	{
		super(player, inventory);
		addSlotToContainer(new FluidSlot(inventory, 0, -1, -1).setNoRender());
		addSlotToContainer(new Slot(inventory, 0, 44, 26));
		addSlotToContainer(new Slot(inventory, 1, 44, 62));
		locateRecipeInput = new TransLocation("input", 36);
		locateRecipeOutput = new TransLocation("output", 37);
	}

	protected boolean matchFluid(ItemStack stack)
	{
		FluidStack current = FluidUtil.getFluidContained(stack);
		return current == null ? false :
			current.getFluid() == FluidRegistry.WATER ||
			WaterTank.DISTILLED_WATER.equals(current.getFluid().getName());
	}
	
	@Override
	public boolean transferStackInSlot(Slot slot, ItemStack baseItemStack, ItemStack itemstack, int locate)
	{
		if(locateRecipeInput.conrrect(locate) || locateRecipeOutput.conrrect(locate))
		{
			if(!locatePlayer.mergeItemStack(itemstack, true))
				return true;
		}
		else if(matchFluid(itemstack) &&
				!locateRecipeInput.mergeItemStack(itemstack, false))
			return true;
		else if(locatePlayerBag.conrrect(locate))
		{
			if(!locatePlayerHand.mergeItemStack(itemstack, false))
				return true;
		}
		else if(locatePlayerHand.conrrect(locate))
		{
			if(!locatePlayerBag.mergeItemStack(itemstack, true))
				return true;
		}
		return false;
	}
}