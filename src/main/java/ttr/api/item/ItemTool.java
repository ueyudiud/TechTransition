package ttr.api.item;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Multimap;
import com.mojang.realmsclient.gui.ChatFormatting;

import ic2.api.item.ElectricItem;
import ic2.api.item.IBoxable;
import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumTools;
import ttr.api.util.Util;

public class ItemTool extends ItemSub implements IBoxable, ISpecialElectricItem, IElectricItemManager
{
	protected Map<Integer, ToolStat> toolstats = new HashMap();
	
	public ItemTool(String name)
	{
		super("ttr", name);
		this.hasSubtypes = true;
		this.registerSimpleItemModel = false;
		setNoRepair();
	}
	
	public Collection<String> getNames()
	{
		return this.nameMap.keySet();
	}
	
	public ItemStack makeTool(String key, EnumMaterial material)
	{
		Integer id = this.nameMap.get(key);
		if(id == null) return null;
		ToolStat stat = this.toolstats.get(id);
		if(stat == null) return null;
		ItemStack result = new ItemStack(this, 1, stat.unusableID);
		result.setTagInfo("material", new NBTTagShort(material.id));
		return result;
	}
	
	public String getName(ItemStack stack)
	{
		return this.metas[getBaseDamage(stack)];
	}
	
	public void addSubItem(int id, EnumTools tools, String localName, ToolStat stat, IBehavior... behaviors)
	{
		addSubItem(id, tools.name(), localName, stat.setToolClasses(tools), behaviors);
		OreDictionary.registerOre(tools.orename(), new ItemStack(this, 1, id));
	}
	public void addSubItem(int id, String name, String localName, ToolStat stat, IBehavior... behaviors)
	{
		super.addSubItem(id, name, localName, behaviors);
		this.toolstats.put(id, stat);
		stat.unusableID = stat.usableID = (short) id;
	}
	public void addSubItem(int idCharged, int idUncharged, String name, String localName, ToolStat stat, IBehavior... behaviors)
	{
		super.addSubItem(idCharged, name, localName, behaviors);
		super.addSubItem(idUncharged, name + "_uncharged", localName, new IBehavior[0]);
		this.toolstats.put(idCharged, stat);
		if(idCharged != idUncharged)
		{
			this.toolstats.put(idUncharged, stat);
		}
		stat.usableID = (short) idCharged;
		stat.unusableID = (short) idUncharged;
	}
	
	@Override
	protected boolean isItemUsable(ItemStack stack)
	{
		return canUse(stack, getToolStat(stack).dischargePerUse);
	}
	
	@Override
	public Set<String> getToolClasses(ItemStack stack)
	{
		return getToolStat(stack).toolClasses;
	}
	
	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass)
	{
		ToolStat stat = getToolStat(stack);
		if(stat.toolClasses.contains(toolClass))
		{
			return getMaterial(stack).toolQuality;
		}
		return super.getHarvestLevel(stack, toolClass);
	}
	
	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state)
	{
		ToolStat stat = getToolStat(stack);
		EnumMaterial material = getMaterial(stack);
		return isItemUsable(stack) ?
				stat.harvestSpeedBase + material.toolHardness * stat.harvestSpeedMultiple : 1.0F;
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		if(slot == EntityEquipmentSlot.MAINHAND)
		{
			ToolStat stat = getToolStat(stack);
			EnumMaterial material = getMaterial(stack);
			Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(),
					new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier",
							stat.damageVsEntityBase + stat.damageVsEntityMultiple * material.toolDamageVsEntity, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(),
					new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier",
							stat.attackSpeed, 0));
		}
		return super.getAttributeModifiers(slot, stack);
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving)
	{
		ToolStat stat = getToolStat(stack);
		super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
		dischargeOrDamageItem(stack, stat.dischargePerUse, stat.damagePerDestory, entityLiving);
		return true;
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		ToolStat stat = getToolStat(stack);
		dischargeOrDamageItem(stack, stat.dischargePerUse, stat.damagePerAttack, attacker);
		return true;
	}
	
	protected ToolStat getToolStat(ItemStack stack)
	{
		return this.toolstats.get(super.getDamage(stack));
	}
	
	@Override
	public int getDamage(ItemStack stack)
	{
		int damage = super.getDamage(stack);
		ToolStat stat = this.toolstats.get(damage);
		return stat == null ? damage :
			canUse(stack, stat.dischargePerUse) ? stat.usableID : stat.unusableID;
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return 1;
	}
	
	public long getCustomDamage(ItemStack stack)
	{
		return !stack.hasTagCompound() ? 0 : stack.getTagCompound().getLong("damage");
	}
	
	public EnumMaterial getMaterial(ItemStack stack)
	{
		EnumMaterial material = stack.hasTagCompound() ? EnumMaterial.getMaterial(stack.getTagCompound().getShort("material")) : null;
		return material == null ? EnumMaterial._NULL : material;
	}
	
	public long getMaxCustomDamage(ItemStack stack)
	{
		EnumMaterial material = getMaterial(stack);
		return material != null ? material.toolDurability : 0L;
	}
	
	public void setCustomDamage(ItemStack stack, long damage)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt == null)
		{
			stack.setTagCompound(nbt = new NBTTagCompound());
		}
		
		if(damage == 0)
		{
			nbt.removeTag("damage");
			if(nbt.hasNoTags())
			{
				stack.setTagCompound(null);
			}
		}
		else
		{
			nbt.setLong("damage", damage);
		}
	}
	
	public boolean dischargeOrDamageItem(ItemStack stack, long discharge, long minDamage, @Nullable EntityLivingBase src)
	{
		if(src instanceof EntityPlayer && ((EntityPlayer) src).capabilities.isCreativeMode) return false;
		if(discharge <= 0 || canUse(stack, discharge))
		{
			if(discharge > 0)
			{
				discharge(stack, discharge, 999, true, true, false);
			}
			return applyCustomDamage(stack, minDamage, src);
		}
		else
		{
			return applyCustomDamage(stack, discharge / 100 + minDamage, src);
		}
	}
	
	public boolean applyCustomDamage(ItemStack stack, long damage, @Nullable EntityLivingBase src)
	{
		if(src instanceof EntityPlayer && ((EntityPlayer) src).capabilities.isCreativeMode) return false;
		long amount = getCustomDamage(stack);
		long max = getMaxCustomDamage(stack);
		if(amount + damage >= max)
		{
			stack.stackSize--;
			setCustomDamage(stack, 0L);
			return true;
		}
		setCustomDamage(stack, damage + amount);
		return true;
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return getCustomDamage(stack) != 0;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		return (double) getCustomDamage(stack) / (double) getMaxCustomDamage(stack);
	}
	
	@Override
	public boolean canBeStoredInToolbox(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book)
	{
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}
	
	@Override
	public boolean isItemTool(ItemStack stack)
	{
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
		ToolStat stat = getToolStat(stack);
		if(stat == null) return stack;
		dischargeOrDamageItem(stack, stat.damagePerUse, stat.dischargePerUse, null);
		return stack.stackSize <= 0 ? null : stack;
	}
	
	@Override
	public double charge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean simulate)
	{
		ToolStat stat = getToolStat(stack);
		if(stat == null || stat.tier > tier) return 0;
		double charged = getCharge(stack);
		long max = stat.maxCharge;
		if(!ignoreTransferLimit && amount > stat.transferLimit)
		{
			amount = stat.transferLimit;
		}
		if(amount + charged > max)
		{
			amount = max - charged;
		}
		if(!simulate)
		{
			setCharge(stack, amount + charged);
		}
		return amount;
	}
	
	@Override
	public double discharge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean externally,
			boolean simulate)
	{
		ToolStat stat = getToolStat(stack);
		if(stat == null || (!externally && stat.tier < tier)) return 0;
		double charged = getCharge(stack);
		if(!ignoreTransferLimit && amount > stat.transferLimit)
		{
			amount = stat.transferLimit;
		}
		if(amount > charged)
		{
			amount = charged;
		}
		if(!simulate)
		{
			setCharge(stack, amount - charged);
		}
		return amount;
	}
	
	private void setCharge(ItemStack stack, double charge)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt == null)
		{
			stack.setTagCompound(nbt = new NBTTagCompound());
		}
		
		if(charge == 0)
		{
			nbt.removeTag("charge");
			if(nbt.hasNoTags())
			{
				stack.setTagCompound(null);
			}
		}
		else
		{
			nbt.setDouble("charge", charge);
		}
	}
	
	@Override
	public double getCharge(ItemStack stack)
	{
		return stack.hasTagCompound() ? stack.getTagCompound().getDouble("charge") : 0;
	}
	
	@Override
	public double getMaxCharge(ItemStack stack)
	{
		ToolStat stat;
		return (stat = this.toolstats.get(getDamage(stack))) != null ? stat.maxCharge : -1;
	}
	
	@Override
	public boolean canUse(ItemStack stack, double amount)
	{
		if(amount <= 0) return true;
		ToolStat stat = getToolStat(stack);
		return stat == null ? true : getCharge(stack) > stat.dischargePerUse;
	}
	
	@Override
	public boolean use(ItemStack stack, double amount, EntityLivingBase entity)
	{
		if(entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode) return true;
		discharge(stack, amount, -1, true, true, false);
		return true;
	}
	
	@Override
	public void chargeFromArmor(ItemStack stack, EntityLivingBase entity)
	{
		ToolStat stat = getToolStat(stack);
		if(stat == null) return;
		boolean changed = false;
		for(ItemStack stack1 : entity.getArmorInventoryList())
		{
			if(stack1 != null)
			{
				IElectricItemManager manager = ElectricItem.manager;
				if(manager.canUse(stack1, 0D))//To check if item can use.
				{
					double amount = manager.discharge(stack1, stat.transferLimit, 999, true, true, true);
					if(amount != 0)
					{
						manager.discharge(stack1, charge(stack, amount, 999, true, false), 999, true, true, false);
						changed = true;
					}
				}
			}
		}
		if(changed && entity instanceof EntityPlayer && Util.isSimulating())
		{
			((EntityPlayer) entity).openContainer.detectAndSendChanges();
		}
	}
	
	@Override
	public String getToolTip(ItemStack stack)
	{
		ToolStat stat;
		return (stat = getToolStat(stack)) == null ? null :
			getCharge(stack) + "/" + stat.maxCharge;
	}
	
	@Override
	public int getTier(ItemStack stack)
	{
		ToolStat stat;
		return (stat = this.toolstats.get(getDamage(stack))) != null ? stat.tier : -1;
	}
	
	@Override
	public final IElectricItemManager getManager(ItemStack stack)
	{
		return this;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		super.addInformation(stack, playerIn, tooltip, advanced);
		ToolStat stat = getToolStat(stack);
		if(stat == null) return;
		EnumMaterial material = getMaterial(stack);
		if(material != EnumMaterial._NULL)
		{
			tooltip.add(String.format("%s%s lv%d", ChatFormatting.YELLOW.toString(), material.name, material.toolQuality));
			tooltip.add(String.format("Damage : %s%d/%d", ChatFormatting.AQUA.toString(), material.toolDurability - getCustomDamage(stack), material.toolDurability));
			tooltip.add(String.format("Hardness : %s%s", ChatFormatting.GREEN.toString(), Util.FORMAT_CUTOUT_AP2.format(stat.harvestSpeedBase + stat.harvestSpeedMultiple * material.toolHardness)));
			tooltip.add(String.format("Attack : %s%s", ChatFormatting.DARK_RED.toString(), Util.FORMAT_CUTOUT_AP2.format(stat.damageVsEntityBase + stat.damageVsEntityMultiple * material.toolDamageVsEntity)));
		}
	}
}