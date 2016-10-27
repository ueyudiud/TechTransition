package ttr.api.item;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;

import ic2.api.item.ICustomDamageItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import ttr.api.data.EnumToolType;
import ttr.api.material.Mat;
import ttr.api.util.IDataChecker;
import ttr.api.util.ISubTagContainer;

public class ItemTool extends ItemSub implements ICustomDamageItem
{
	protected IBehavior[] behaviors;
	protected IToolStat toolStats;
	protected IDataChecker<? extends ISubTagContainer> select;
	protected Set<String> toolClasses;
	
	protected ItemTool(String name)
	{
		super(name);
		canRepair = false;
		setCreativeTab(CreativeTabs.TOOLS);
	}
	protected ItemTool(String modid, String name)
	{
		super(modid, name);
		setCreativeTab(CreativeTabs.TOOLS);
	}
	public ItemTool(String modid, String name, Set<String> toolClasses, IToolStat stat, IDataChecker<? extends ISubTagContainer> tag, IBehavior...behaviors)
	{
		this(modid, name);
		this.behaviors = behaviors;
		toolStats = stat;
		select = tag;
		this.toolClasses = toolClasses;
	}
	public ItemTool(String name, Set<String> toolClasses, IToolStat stat, IDataChecker<? extends ISubTagContainer> tag, IBehavior...behaviors)
	{
		this(name);
		this.behaviors = behaviors;
		toolStats = stat;
		select = tag;
		this.toolClasses = toolClasses;
	}
	public ItemTool(EnumToolType type, IToolStat stat, IDataChecker<? extends ISubTagContainer> tag, IBehavior...behaviors)
	{
		this("ttr", type.name(), ImmutableSet.of(type.name()), stat, tag, behaviors);
		OreDictionary.registerOre(type.ore(), new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE));
	}

	@Override
	public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player)
	{
		return true;
	}
	
	@Override
	public void postInitalizedItems()
	{
		for(Mat material : Mat.register())
		{
			if(material.canMakeTool && (select == null || ((IDataChecker<ISubTagContainer>) select).isTrue(material)))
			{
				addSubItem(material.id, material.name, localized, behaviors);
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
	{
		for(Mat material : Mat.register())
		{
			if(material.canMakeTool && (select == null || ((IDataChecker<ISubTagContainer>) select).isTrue(material)))
			{
				subItems.add(new ItemStack(itemIn, 1, material.id));
			}
		}
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return 1;
	}
	
	@Override
	public boolean isItemTool(ItemStack stack)
	{
		return true;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return toolStats.canBlock() ? EnumAction.BLOCK : EnumAction.NONE;
	}

	@Override
	public boolean canHarvestBlock(IBlockState state, ItemStack stack)
	{
		return toolStats.canHarvestDrop(stack, state);
	}
	
	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state)
	{
		return toolStats.getStrVsBlock(stack, Mat.register().get(getDamage(stack)), state);
	}

	@Override
	public Set<String> getToolClasses(ItemStack stack)
	{
		return toolClasses;
	}
	
	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass)
	{
		return toolStats.getToolHarvestLevel(stack, toolClass, Mat.register().get(getBaseDamage(stack)));
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		if(slot == EntityEquipmentSlot.MAINHAND)
		{
			Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
			Mat material = Mat.register().get(getBaseDamage(stack));
			float speed = toolStats.getAttackSpeed(stack, material);
			float dve = toolStats.getDamageVsEntity(stack, material);
			if(speed != 0)
			{
				multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", speed, 0));
			}
			if(dve != 0)
			{
				multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", dve, 0));
			}
			return multimap;
		}
		return super.getAttributeModifiers(slot, stack);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		applyCustomDamage(stack, toolStats.getToolDamagePerAttack(stack, attacker, target), attacker);
		return true;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving)
	{
		if(state.getBlockHardness(worldIn, pos) != 0)
		{
			applyCustomDamage(stack, toolStats.getToolDamagePerBreak(stack, entityLiving, worldIn, pos, state), entityLiving);
		}
		return true;
	}

	@Override
	public boolean hasContainerItem(ItemStack stack)
	{
		return getContainerItem(stack) != null;
	}

	@Override
	public ItemStack getContainerItem(ItemStack stack)
	{
		ItemStack stack1 = stack.copy();
		applyCustomDamage(stack1, toolStats.getToolDamagePerCraft(stack1), null);
		if(stack1.stackSize > 0)
			return stack1;
		else
			return null;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return getCustomDamage(stack) != 0;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		return (float) getCustomDamage(stack) / (float) getMaxCustomDamage(stack);
	}
	
	@Override
	public int getCustomDamage(ItemStack stack)
	{
		return stack.hasTagCompound() ? stack.getTagCompound().getInteger("damage") : 0;
	}

	@Override
	public int getMaxCustomDamage(ItemStack stack)
	{
		return (int) (Mat.register().get(getBaseDamage(stack)).toolMaxUse * toolStats.getMaxDurabilityMultiplier());
	}

	@Override
	public void setCustomDamage(ItemStack stack, int damage)
	{
		if(!stack.hasTagCompound())
		{
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setInteger("damage", damage);
	}

	@Override
	public boolean applyCustomDamage(ItemStack stack, int damage, EntityLivingBase src)
	{
		if(src instanceof EntityPlayer && ((EntityPlayer) src).capabilities.isCreativeMode)
			return false;
		int damage0 = getCustomDamage(stack);
		int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);
		int j = 0;
		while (i > 0 && j < damage)
		{
			if (EnchantmentDurability.negateDamage(stack, i, src.getRNG()))
			{
				--damage;
			}
			++j;
		}
		if(damage > 0)
		{
			int damage1 = damage + damage0;
			if(damage1 > getMaxCustomDamage(stack))
			{
				stack.stackSize--;
				setCustomDamage(stack, 0);
			}
			else
			{
				setCustomDamage(stack, damage1);
			}
			return true;
		}
		return true;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return toolStats.canBlock() ? 72000 : super.getMaxItemUseDuration(stack);
	}
}