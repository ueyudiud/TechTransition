/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.api.util;

import ttr.api.enums.EnumMaterial;

/**
 * Created at 2016年12月19日 下午11:54:01
 * @author ueyudiud
 */
public class MaterialStack
{
	public EnumMaterial material;
	public long amount;
	
	public MaterialStack(EnumMaterial material, long amount)
	{
		this.material = material;
		this.amount = amount;
	}
}