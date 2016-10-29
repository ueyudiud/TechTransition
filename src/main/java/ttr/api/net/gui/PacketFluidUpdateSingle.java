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

public class PacketFluidUpdateSingle extends PacketContainer
{
	private int[] id;
	private FluidStack[] stacks;
	
	public PacketFluidUpdateSingle()
	{
	}
	public PacketFluidUpdateSingle(int windowID, ITankSyncable tank, int...is)
	{
		super(windowID);
		id = is;
		stacks = new FluidStack[id.length];
		for(int i = 0; i < is.length; ++i)
		{
			stacks[i] = tank.getStackInTank(i);
		}
	}

	@Override
	protected void encode(PacketBuffer output) throws IOException
	{
		super.encode(output);
		output.writeByte(stacks.length);
		output.writeVarIntArray(id);
		for (int i = 0; i < id.length; ++i)
		{
			FluidStack stack = stacks[i];
			if(stack != null)
			{
				output.writeBoolean(true);
				output.writeShort(FluidRegistry.getFluidID(stack.getFluid()));
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
		stacks = new FluidStack[input.readByte()];
		id = input.readVarIntArray();
		for(int id : this.id)
		{
			if(input.readBoolean())
			{
				int key = input.readShort();
				Fluid fluid = FluidRegistry.getFluid(key);
				if(fluid == null)
					throw new IOException();
				int amt = input.readInt();
				NBTTagCompound nbt = input.readNBTTagCompoundFromBuffer();
				stacks[id] = new FluidStack(fluid, amt, nbt);
			}
		}
	}

	@Override
	public IPacket process(Network network)
	{
		Container container = getPlayer().openContainer;
		if(container.windowId == windowID && container instanceof IFluidSyncableContainer)
		{
			for(int i = 0; i < id.length; ++i)
			{
				((IFluidSyncableContainer) container).setFluid(id[i], stacks[i]);
			}
		}
		return null;
	}
}