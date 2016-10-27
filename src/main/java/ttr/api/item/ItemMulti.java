package ttr.api.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.TTrAPI;
import ttr.api.data.M;
import ttr.api.material.Mat;
import ttr.api.material.MatCondition;
import ttr.api.util.LanguageManager;
import ttr.api.util.SubTag;
import ttr.api.util.UnlocalizedList;

public class ItemMulti extends ItemBase
{
	private static Map<MatCondition, ItemMulti> items = new HashMap();
	
	public final MatCondition condition;

	public static void postInitalized()
	{
		if(items == null) throw new RuntimeException("The items has already post initalized!");
		for(MatCondition condition : MatCondition.register)
		{
			if(!items.containsKey(condition) && !condition.contain(SubTag.NO_ITEM))
			{
				new ItemMulti(condition);
			}
		}
		items = null;
	}
	
	public ItemMulti(MatCondition mc)
	{
		this("ttr", mc);
		hasSubtypes = true;
	}
	public ItemMulti(String modid, MatCondition mc)
	{
		super(modid, "multi." + mc.orePrefix.toLowerCase());
		setCreativeTab(CreativeTabs.MATERIALS);
		condition = mc;
		hasSubtypes = true;
		items.put(mc, this);
	}
	
	@Override
	public void postInitalizedItems()
	{
		for(Mat material : Mat.register())
		{
			if(condition.isBelongTo(material))
			{
				if(material.itemProp == null)
				{
					ItemStack templete = new ItemStack(this, 1, material.id);
					TTrAPI.registerItemModel(this, material.id, material.modid, condition.orePrefix.toLowerCase() + "/" + material.name);
					condition.registerOre(material, templete);
					LanguageManager.registerLocal(getTranslateName(templete), condition.getLocal(material));
				}
				else
				{
					int l = material.itemProp.getOffsetMetaCount();
					for(int i = 0; i < l; ++i)
					{
						ItemStack templete = new ItemStack(this, 1, material.id);
						material.itemProp.setInstanceFromMeta(templete, i, material, condition);
						String name = condition.orePrefix.toLowerCase() + "/" + material.name;
						String str1 = material.itemProp.getSubName(i);
						if(str1 != null)
						{
							name += "_" + str1;
						}
						TTrAPI.registerItemModel(this, material.id | i << 15, material.modid, str1);
						condition.registerOre(material, templete);
						LanguageManager.registerLocal(getTranslateName(templete), condition.getLocal(material));
					}
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
	{
		for(Mat material : Mat.register())
		{
			if(condition.isBelongTo(material))
			{
				subItems.add(new ItemStack(itemIn, 1, material.id));
			}
		}
	}

	protected Mat getMaterialFromItem(ItemStack stack)
	{
		return Mat.register().get(getBaseDamage(stack), M.VOID);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		ItemStack stack2 = stack;
		Mat material = getMaterialFromItem(stack);
		if(!entityIn.worldObj.isRemote && material.itemProp != null)
		{
			stack = material.itemProp.onUpdate(stack, worldIn, new BlockPos(entityIn));
			if(entityIn instanceof EntityPlayer)
			{
				if(stack == null)
				{
					((EntityPlayer) entityIn).inventory.removeStackFromSlot(itemSlot);
					return;
				}
			}
			if(stack.getItem() != this)
				return;
		}
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem)
	{
		Mat material = getMaterialFromItem(entityItem.getEntityItem());
		if(!entityItem.worldObj.isRemote && material.itemProp != null)
		{
			ItemStack stack = material.itemProp.onUpdate(entityItem.getEntityItem(), entityItem.worldObj, new BlockPos(entityItem));
			if(stack == null)
			{
				entityItem.setDead();
				return false;
			}
			else if(stack != entityItem.getEntityItem())
			{
				entityItem.setEntityItemStack(stack);
			}
			if(stack.getItem() != this)
				return true;
		}
		return false;
	}

	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return condition.stackLimit;
	}
	
	@Override
	public int getStackMetaOffset(ItemStack stack)
	{
		Mat material = getMaterialFromItem(stack);
		if(material.itemProp != null)
			return material.itemProp.getMetaOffset(stack, material, condition);
		return 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected void addInformation(ItemStack stack, EntityPlayer playerIn, UnlocalizedList unlocalizedList,
			boolean advanced)
	{
		super.addInformation(stack, playerIn, unlocalizedList, advanced);
		Mat material = getMaterialFromItem(stack);
		if(material.itemProp != null)
		{
			material.itemProp.addInformation(stack, material, condition, unlocalizedList);
		}
	}

	@Override
	public void setDamage(ItemStack stack, int damage)
	{
		super.setDamage(stack, damage & 0x7FFF);
		Mat material = getMaterialFromItem(stack);
		if(material.itemProp != null)
		{
			material.itemProp.setInstanceFromMeta(stack, damage >> 15, material, condition);
		}
	}
}