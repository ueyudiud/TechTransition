/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.tile;

import java.util.ArrayList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ttr.api.enums.EnumMaterial;

/**
 * Created at 2016年12月23日 下午6:27:21
 * @author ueyudiud
 */
public class TEMaterialed extends TEMachineBase
{
	public EnumMaterial material = EnumMaterial._NULL;
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setShort("material", this.material.id);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.material = EnumMaterial.getMaterialNonNull(nbt.getShort("material"));
	}
	
	@Override
	public void writeToDescription(NBTTagCompound nbt)
	{
		super.writeToDescription(nbt);
		nbt.setShort("m", this.material.id);
	}
	
	@Override
	public void readFromDescription(NBTTagCompound nbt)
	{
		super.readFromDescription(nbt);
		this.material = EnumMaterial.getMaterialNonNull(nbt.getShort("m"));
	}
	
	@Override
	protected void readFromNBTAtStack(NBTTagCompound nbt)
	{
		super.readFromNBTAtStack(nbt);
		if(this.saveByNBT)
		{
			this.material = EnumMaterial.getMaterialNonNull(nbt.getShort("material"));
		}
	}
	
	@Override
	protected NBTTagCompound writeNBTToStack()
	{
		NBTTagCompound nbt = super.writeNBTToStack();
		if(this.saveByNBT)
		{
			nbt.setShort("material", this.material.id);
		}
		return nbt;
	}
	
	@Override
	public void onBlockPlacedBy(IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		super.onBlockPlacedBy(state, placer, stack);
		if(!this.saveByNBT)
		{
			this.material = EnumMaterial.getMaterialNonNull(stack.getItemDamage());
		}
	}
	
	@Override
	public void getWrenchDrops(IBlockState state, int meta, ArrayList<ItemStack> drops)
	{
		ItemStack stack = new ItemStack(state.getBlock(), 1, this.saveByNBT ? meta : this.material.id);
		NBTTagCompound nbt = writeNBTToStack();
		if(nbt != null)
		{
			stack.setTagCompound(nbt);
		}
		drops.add(stack);
	}
	
	protected boolean saveByNBT = true;
}