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
		this.stacks = new FluidStack[tanks.getTankSize()];
		for(int i = 0; i < this.stacks.length; ++i)
		{
			this.stacks[i] = tanks.getStackInTank(i);
		}
	}
	
	@Override
	protected void encode(PacketBuffer output) throws IOException
	{
		super.encode(output);
		output.writeShort((short) this.stacks.length);
		for (FluidStack stack : this.stacks)
		{
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
		this.stacks = new FluidStack[input.readShort()];
		for(int i = 0; i < this.stacks.length; ++i)
		{
			if(input.readBoolean())
			{
				short key = input.readShort();
				Fluid fluid = FluidRegistry.getFluid(key);
				if(fluid == null)
					throw new IOException();
				int amt = input.readInt();
				NBTTagCompound nbt = input.readNBTTagCompoundFromBuffer();
				this.stacks[i] = new FluidStack(fluid, amt, nbt);
			}
		}
	}
	
	@Override
	public IPacket process(Network network)
	{
		Container container = getPlayer().openContainer;
		if(container.windowId == this.windowID && container instanceof IFluidSyncableContainer)
		{
			for(int idx = 0; idx < this.stacks.length; ++idx)
			{
				((IFluidSyncableContainer) container).setFluid(idx, this.stacks[idx]);
			}
		}
		return null;
	}
}