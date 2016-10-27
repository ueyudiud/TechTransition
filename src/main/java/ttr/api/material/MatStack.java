package ttr.api.material;

import net.minecraft.nbt.NBTTagCompound;
import ttr.api.data.MC;

public class MatStack
{
	public MatCondition condition;
	public Mat material;
	public long size;

	public MatStack(Mat material, long size)
	{
		this(material, MC.base, size);
	}
	public MatStack(Mat material, MatCondition condition, long size)
	{
		this.material = material;
		this.condition = condition;
		this.size = size;
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		if(nbt == null)
		{
			nbt = new NBTTagCompound();
		}
		nbt.setString("condition", condition.orePrefix);
		nbt.setString("material", material.name);
		nbt.setLong("size", size);
		return nbt;
	}
	
	public static MatStack readFromNBT(NBTTagCompound nbt)
	{
		if(!nbt.hasKey("condition") || !Mat.register().contain(nbt.getString("material"))) return null;
		return new MatStack(Mat.register().get(nbt.getString("material")), MatCondition.register.get("condition", MC.base), nbt.getLong("size"));
	}
}