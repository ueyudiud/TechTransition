/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.api.recipe;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;

/**
 * Created at 2016年12月19日 下午11:57:26
 * @author ueyudiud
 */
public class MaterialInstance
{
	private static final Map<EnumOrePrefix, Map<EnumMaterial, ItemStack>> INSTANCEMAP = new HashMap();
	
	public static void registerInstanceItem(EnumOrePrefix prefix, EnumMaterial material, ItemStack stack)
	{
		registerInstanceItem(prefix, material, stack, false);
	}
	public static void registerInstanceItem(EnumOrePrefix prefix, EnumMaterial material, ItemStack stack, boolean registerToDict)
	{
		if(stack == null) return;
		if(registerToDict)
		{
			OreDictionary.registerOre(prefix.getDictName(material), stack);
		}
		Map<EnumMaterial, ItemStack> map = INSTANCEMAP.get(prefix);
		if(map == null)
		{
			INSTANCEMAP.put(prefix, map = new HashMap());
		}
		if(map.containsKey(material)) return;
		map.put(material, stack.copy());
	}
	
	public static ItemStack findItemStack(EnumOrePrefix prefix, EnumMaterial material)
	{
		Map<EnumMaterial, ItemStack> map;
		if(!INSTANCEMAP.containsKey(prefix) || !(map = INSTANCEMAP.get(prefix)).containsKey(material)) return null;
		return map.get(material).copy();
	}
	
	public static boolean hasItemStack(EnumOrePrefix prefix, EnumMaterial material)
	{
		return INSTANCEMAP.containsKey(prefix) && INSTANCEMAP.get(prefix).containsKey(material);
	}
}