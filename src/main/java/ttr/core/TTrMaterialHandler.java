/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;
import ttr.api.item.ItemMulti;
import ttr.api.recipe.IOreDictRegister;
import ttr.api.recipe.MaterialInstance;
import ttr.api.util.IDataChecker;
import ttr.api.util.MaterialStack;
import ttr.api.util.Util;
import ttr.load.TTrIBF;

/**
 * Created at 2016年12月20日 上午12:08:38
 * @author ueyudiud
 */
public class TTrMaterialHandler
{
	private static Map<EnumOrePrefix, List<IOreDictRegister>> registers = new HashMap();
	
	public static ItemStack getItemStack(EnumOrePrefix prefix, EnumMaterial material, long size)
	{
		ItemStack stack = MaterialInstance.findItemStack(prefix, material);
		if(stack != null) return Util.copyAmount(stack, (int) size);
		Item item = TTrIBF.itemMap.get(prefix);
		return item == null ? null : new ItemStack(item, (int) size, material.id);
	}
	
	public static void registerOreRegister(EnumOrePrefix prefix, IOreDictRegister register)
	{
		ItemMulti item = TTrIBF.itemMap.get(prefix);
		if(item != null)
		{
			item.registerOreDictRegister(register);
		}
		else
		{
			if(!registers.containsKey(prefix))
			{
				registers.put(prefix, new ArrayList());
			}
			registers.get(register).add(register);
		}
	}
	
	public static void registerOre(ItemStack stack, EnumMaterial material, EnumOrePrefix prefix)
	{
		if(registers.containsKey(prefix))
		{
			for(IOreDictRegister register : registers.get(prefix))
			{
				register.registerOreRecipes(stack, prefix, material);
			}
		}
	}
	
	public static MaterialStack getMaterialStack(ItemStack stack)
	{
		return getMaterialStack(stack, IDataChecker.TRUE);
	}
	public static MaterialStack getMaterialStack(ItemStack stack, IDataChecker<? super EnumOrePrefix> checker)
	{
		if(stack == null) return null;
		int[] ids = OreDictionary.getOreIDs(stack);
		for(int id : ids)
		{
			String name = OreDictionary.getOreName(id);
			for(EnumOrePrefix prefix : EnumOrePrefix.getPrefixs())
			{
				if(prefix == null || !checker.isTrue(prefix)) continue;
				if(prefix.access(name))
				{
					String name1 = name.substring(prefix.oreDictName.length());
					EnumMaterial material = EnumMaterial.getMaterialFromDictName(name1);
					if(material != null)
					{
						return new MaterialStack(material, prefix.amount);
					}
				}
			}
		}
		return null;
	}
}