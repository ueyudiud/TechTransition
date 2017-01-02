/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.item;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import ic2.api.item.ICustomDamageItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagInt;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.item.IBehavior;
import ttr.api.item.ITurbine;
import ttr.api.item.ItemSub;
import ttr.api.util.UnlocalizedList;
import ttr.load.TTrLangs;

/**
 * Created at 2016年12月28日 下午9:23:59
 * @author ueyudiud
 */
public class ItemTurbine extends ItemSub implements ITurbine, ICustomDamageItem
{
	private final Map<Integer, TurbineInfoIn> turbineInfoMap = new HashMap();
	
	private static class TurbineInfoIn extends TurbineInfo
	{
		private final int maxDamage;
		
		public TurbineInfoIn(int maxDamage, int maxTransferLimit, float efficiency, int maxOutput)
		{
			super(maxTransferLimit, efficiency, maxOutput);
			this.maxDamage = maxDamage;
		}
	}
	
	public ItemTurbine()
	{
		super("turbine_item");
		addSubItems();
	}
	
	private void addSubItems()
	{
		addSubItem(1, "bronze", "Bronze Turbine", new TurbineInfoIn(36000, 160, 0.9F, 800));
		addSubItem(2, "steel", "Steel Turbine", new TurbineInfoIn(36000, 360, 0.85F, 2000));
		addSubItem(3, "bronze", "Bronze Turbine", new TurbineInfoIn(36000, 640, 0.8F, 4000));
		addSubItem(4, "magnalium", "Magnalium Turbine", new TurbineInfoIn(36000, 1280, 0.8F, 8000));
		addSubItem(5, "carbon", "Carbon Turbine", new TurbineInfoIn(36000, 720, 1.0F, 800));
	}
	
	protected void addSubItem(int meta, String name, String localized, TurbineInfoIn info, IBehavior... behaviors)
	{
		super.addSubItem(meta, name, localized, behaviors);
		this.turbineInfoMap.put(meta, info);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return 1;
	}
	
	@Override
	public @Nullable TurbineInfoIn getTurbineInfo(ItemStack stack)
	{
		return this.turbineInfoMap.get(getBaseDamage(stack));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected void addInformation(ItemStack stack, EntityPlayer playerIn, UnlocalizedList unlocalizedList,
			boolean advanced)
	{
		super.addInformation(stack, playerIn, unlocalizedList, advanced);
		TurbineInfoIn info = getTurbineInfo(stack);
		if(info != null)
		{
			unlocalizedList.add(TTrLangs.infoTurbineDamage, info.maxDamage - getCustomDamage(stack), info.maxDamage);
			unlocalizedList.add(TTrLangs.infoTurbineMaxOutput, info.maxOutput);
			unlocalizedList.add(TTrLangs.infoTurbineTransferLimit, info.maxTransferLimit);
			unlocalizedList.add(TTrLangs.infoTurbineEfficiency, info.efficiency * 100);
		}
	}
	
	@Override
	public int getCustomDamage(ItemStack stack)
	{
		return stack.hasTagCompound() ? stack.getTagCompound().getInteger("damage") : 0;
	}
	
	@Override
	public int getMaxCustomDamage(ItemStack stack)
	{
		TurbineInfoIn info = getTurbineInfo(stack);
		return info == null ? 0 : info.maxDamage;
	}
	
	@Override
	public void setCustomDamage(ItemStack stack, int damage)
	{
		stack.setTagInfo("damage", new NBTTagInt(damage));
	}
	
	@Override
	public boolean applyCustomDamage(ItemStack stack, int damage, EntityLivingBase src)
	{
		int d1 = getCustomDamage(stack);
		int md = getMaxCustomDamage(stack);
		if(md < damage + d1)
		{
			stack.stackSize --;
			setCustomDamage(stack, 0);
		}
		else
		{
			setCustomDamage(stack, damage + d1);
		}
		return true;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		return (float) getCustomDamage(stack) / (float) getMaxCustomDamage(stack);
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return getCustomDamage(stack) != 0;
	}
}