/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.tile;

import net.minecraft.nbt.NBTTagCompound;
import ttr.api.enums.EnumMaterial;

/**
 * Created at 2016年12月20日 下午8:47:52
 * @author ueyudiud
 */
public class TEOre extends TEStatic
{
	public EnumMaterial material = EnumMaterial._NULL;
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.material = EnumMaterial.getMaterialNonNull(compound.getShort("meta"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setShort("meta", this.material.id);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromDescription(NBTTagCompound nbt)
	{
		super.readFromDescription(nbt);
		this.material = EnumMaterial.getMaterialNonNull(nbt.getShort("meta"));
	}
	
	@Override
	public void writeToDescription(NBTTagCompound nbt)
	{
		super.writeToDescription(nbt);
		nbt.setShort("meta", this.material.id);
	}
}