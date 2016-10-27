package ttr.api.net.gui;

import java.io.IOException;

import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import ttr.api.gui.IFluidSyncableContainer;
import ttr.api.net.PacketContainer;
import ttr.api.network.IPacket;
import ttr.api.network.Network;
import ttr.core.tile.ITankSyncable;

public class PacketFluidUpdateAll extends PacketContainer
{
	private FluidStack[] stacks;
	
	public PacketFluidUpdateAll()
	{
	}
	public PacketFluidUpdateAll(int windowID, ITankSyncable tanks)
	{
		super(windowID);
		stacks = new FluidStack[tanks.getTankSize()];
		for(int i = 0; i < stacks.length; ++i)
		{
			stacks[i] = tanks.getStackInTank(i);
		}
	}

	@Override
	protected void encode(PacketBuffer output) throws IOException
	{
		super.encode(output);
		output.writeShort((short) stacks.length);
		for (FluidStack stack : stacks)
		{
			if(stack != null)
			{
				output.writeBoolean(true);
				output.writeString(stack.getFluid().getName());
				output.writeInt(stack.amount);
				output.writeNBTTagCompoundToBuffer(stack.tag);
			}
			else
			{
				output.writeBoolean(false);
			}
		}
	}

	@Override
	protected void decode(PacketBuffer input) throws IOException
	{
		super.decode(input);
		stacks = new FluidStack[input.readShort()];
		for(int i = 0; i < stacks.length; ++i)
		{
			if(input.readBoolean())
			{
				String key = input.readStringFromBuffer(999);
				if(!FluidRegistry.isFluidRegistered(key))
					throw new IOException();
				Fluid fluid = FluidRegistry.getFluid(key);
				int amt = input.readInt();
				NBTTagCompound nbt = input.readNBTTagCompoundFromBuffer();
				stacks[i] = new FluidStack(fluid, amt, nbt);
			}
		}
	}

	@Override
	public IPacket process(Network network)
	{
		Container container = getPlayer().openContainer;
		if(container.windowId == windowID && container instanceof IFluidSyncableContainer)
		{
			for(int idx = 0; idx < stacks.length; ++idx)
			{
				((IFluidSyncableContainer) container).setFluid(idx, stacks[idx]);
			}
		}
		return null;
	}
}