package ttr.api.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.TTrAPI;
import ttr.api.util.LanguageManager;
import ttr.api.util.UnlocalizedList;

public class ItemSub extends ItemBase
{
	protected String[] metas = new String[32768];
	protected Map<String, Integer> nameMap = new HashMap();
	protected Map<String, List<IBehavior>> behaviors = new HashMap();
	
	protected ItemSub(String name)
	{
		super(name);
		hasSubtypes = true;
	}
	protected ItemSub(String modid, String name)
	{
		super(modid, name);
		hasSubtypes = true;
	}

	protected void addSubItem(int meta, String name, String localized, IBehavior...behaviors)
	{
		if (metas[meta] != null)
			throw new RuntimeException("The name " + name + " has already registered!");
		metas[meta] = name;
		nameMap.put(name, meta);
		this.behaviors.put(name, ImmutableList.copyOf(behaviors));
		LanguageManager.registerLocal(getUnlocalizedName(new ItemStack(this, 1, meta)), localized);
		TTrAPI.proxy.registerItemModel(this, meta, modid, this.name + "/" + name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
	{
		for(int i = 0; i < metas.length; ++i)
		{
			if(metas[i] != null)
			{
				subItems.add(new ItemStack(itemIn, 1, i));
			}
		}
	}

	protected List<IBehavior> getBehavior(ItemStack stack)
	{
		return behaviors.getOrDefault(metas[getBaseDamage(stack)], IBehavior.NONE);
	}
	
	protected boolean isItemUsable(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving)
	{
		if(!isItemUsable(stack))
			return false;
		try
		{
			boolean flag = false;
			for(IBehavior behavior : getBehavior(stack))
				if(behavior.onBlockDestroyed(stack, worldIn, state, pos, entityLiving))
				{
					flag = true;
				}
			return flag;
		}
		catch(Exception exception)
		{
			return false;
		}
	}
	
	@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player)
	{
		if(!isItemUsable(item))
			return true;
		try
		{
			boolean flag = true;
			for(IBehavior behavior : getBehavior(item))
				if(!behavior.onDroppedByPlayer(item, player))
				{
					flag = false;
				}
			return flag;
		}
		catch(Exception exception)
		{
			return true;
		}
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem)
	{
		if(!isItemUsable(entityItem.getEntityItem()))
			return false;
		try
		{
			boolean flag = false;
			for(IBehavior behavior : getBehavior(entityItem.getEntityItem()))
			{
				if(behavior.onEntityItemUpdate(entityItem))
				{
					flag = true;
				}
				if(entityItem.isDead) return false;
			}
			return flag;
		}
		catch(Exception exception)
		{
			return false;
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
			EnumHand hand)
	{
		if(!isItemUsable(itemStackIn))
			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
		try
		{
			ActionResult<ItemStack> result = new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
			for(IBehavior behavior : getBehavior(itemStackIn))
			{
				ActionResult<ItemStack> result2;
				if((result2 = behavior.onItemRightClick(result.getResult(), worldIn, playerIn, hand)) != null &&
						result2.getType() != EnumActionResult.PASS)
				{
					EnumActionResult result3;
					if(result2.getType() == EnumActionResult.SUCCESS || result.getType() == EnumActionResult.SUCCESS)
					{
						result3 = EnumActionResult.SUCCESS;
					}
					else
					{
						result3 = EnumActionResult.FAIL;
					}
					result = new ActionResult<ItemStack>(result3, result2.getResult());
				}
			}
			return result;
		}
		catch(Exception exception)
		{
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
		}
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!isItemUsable(stack))
			return EnumActionResult.PASS;
		try
		{
			EnumActionResult result = EnumActionResult.PASS;
			for(IBehavior behavior : getBehavior(stack))
			{
				EnumActionResult result2;
				if((result2 = behavior.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ)) != EnumActionResult.PASS)
				{
					if(result == EnumActionResult.SUCCESS)
					{
						continue;
					}
					result = result2;
				}
			}
			return result;
		}
		catch(Exception exception)
		{
			return EnumActionResult.FAIL;
		}
	}
	
	@Override
	public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos,
			EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		if(!isItemUsable(stack))
			return EnumActionResult.PASS;
		try
		{
			EnumActionResult result = EnumActionResult.PASS;
			for(IBehavior behavior : getBehavior(stack))
			{
				EnumActionResult result2;
				if((result2 = behavior.onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ, hand)) != EnumActionResult.PASS)
				{
					if(result == EnumActionResult.SUCCESS)
					{
						continue;
					}
					result = result2;
				}
			}
			return result;
		}
		catch(Exception exception)
		{
			return EnumActionResult.FAIL;
		}
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if(!isItemUsable(stack))
			return false;
		try
		{
			boolean flag = false;
			for(IBehavior behavior : getBehavior(stack))
			{
				if(behavior.onLeftClickEntity(stack, player, entity))
				{
					flag = true;
				}
			}
			return flag;
		}
		catch(Exception exception)
		{
			return false;
		}
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
	{
		if(!isItemUsable(stack))
			return;
		for(IBehavior behavior : getBehavior(stack))
		{
			behavior.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if(!isItemUsable(stack))
			return;
		ItemStack stack2 = stack;
		for(IBehavior behavior : getBehavior(stack))
		{
			stack = behavior.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
			if(entityIn instanceof EntityPlayer)
			{
				if(stack == null)
				{
					((EntityPlayer) entityIn).inventory.removeStackFromSlot(itemSlot);
					return;
				}
				else if(stack != stack2)
				{
					((EntityPlayer) entityIn).inventory.setInventorySlotContents(itemSlot, stack);
				}
				if(stack.getItem() != this)
					return;
			}
		}
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
	{
		if(!isItemUsable(stack))
			return;
		try
		{
			for(IBehavior behavior : getBehavior(stack))
			{
				behavior.onUsingTick(stack, player, count);
			}
		}
		catch(Exception exception)
		{
			;
		}
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target,
			EnumHand hand)
	{
		if(!isItemUsable(stack))
			return false;
		try
		{
			boolean flag = false;
			for(IBehavior behavior : getBehavior(stack))
			{
				if(behavior.onRightClickEntity(stack, playerIn, target, hand))
				{
					flag = true;
				}
			}
			return flag;
		}
		catch(Exception exception)
		{
			return false;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected void addInformation(ItemStack stack, EntityPlayer playerIn, UnlocalizedList unlocalizedList,
			boolean advanced)
	{
		if(!isItemUsable(stack))
		{
			unlocalizedList.add("info.invalid");
			return;
		}
		for(IBehavior behavior : getBehavior(stack))
		{
			behavior.addInformation(stack, playerIn, unlocalizedList, advanced);
		}
	}
}