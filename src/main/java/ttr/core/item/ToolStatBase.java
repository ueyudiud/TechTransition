package ttr.core.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ttr.api.item.IToolStat;
import ttr.api.material.Mat;

public class ToolStatBase implements IToolStat
{
	protected int a = 1;
	protected int b = 1;
	protected int c = 1;
	protected float damageMultiplier = 1.0F;
	protected float baseAttackDamage;
	protected float attackSpeed = 0;
	protected boolean canBlock = false;
	protected List<Material> effectMaterials = new ArrayList();
	protected List<IBlockState> effectiveStates = new ArrayList();
	
	public ToolStatBase()
	{
	}

	public ToolStatBase setCanBlock()
	{
		canBlock = true;
		return this;
	}
	
	public ToolStatBase setDamageMultiplier(float damageMultiplier)
	{
		this.damageMultiplier = damageMultiplier;
		return this;
	}
	
	public ToolStatBase setBaseAttackDamage(float baseAttackDamage)
	{
		this.baseAttackDamage = baseAttackDamage;
		return this;
	}
	
	public ToolStatBase setAttackSpeed(float attackSpeed)
	{
		this.attackSpeed = attackSpeed;
		return this;
	}

	public ToolStatBase setEffective(Material...material)
	{
		effectMaterials.addAll(Arrays.asList(material));
		return this;
	}

	public ToolStatBase setEffective(IBlockState...states)
	{
		effectiveStates.addAll(Arrays.asList(states));
		return this;
	}

	public ToolStatBase setEffective(Block...blocks)
	{
		for(Block block : blocks)
		{
			effectiveStates.addAll(block.getBlockState().getValidStates());
		}
		return this;
	}
	
	public ToolStatBase setDamage(int perBreak, int perAttack, int perCraft)
	{
		a = perAttack;
		b = perBreak;
		c = perCraft;
		return this;
	}
	
	@Override
	public void onToolCrafted(ItemStack stack, EntityPlayer player)
	{
	}

	@Override
	public int getToolDamagePerBreak(ItemStack stack, EntityLivingBase user, World world, BlockPos pos,
			IBlockState block)
	{
		return b;
	}

	@Override
	public int getToolDamagePerAttack(ItemStack stack, EntityLivingBase user, Entity target)
	{
		return a;
	}

	@Override
	public int getToolDamagePerCraft(ItemStack stack)
	{
		return c;
	}

	@Override
	public boolean isToolEffective(ItemStack stack, IBlockState state)
	{
		return effectMaterials.contains(state.getMaterial()) || effectiveStates.contains(state);
	}

	@Override
	public float getDamageVsEntity(ItemStack stack, Mat material)
	{
		return baseAttackDamage + material.toolDamageToEntity;
	}

	@Override
	public float getAttackSpeed(ItemStack stack)
	{
		return attackSpeed;
	}

	@Override
	public float getAttackSpeed(ItemStack stack, Mat material)
	{
		return getAttackSpeed(stack) + material.toolAttackSpeed;
	}

	@Override
	public float getMaxDurabilityMultiplier()
	{
		return damageMultiplier;
	}

	@Override
	public int getToolHarvestLevel(ItemStack stack, String toolClass, Mat baseMaterial)
	{
		return baseMaterial.toolHarvestLevel;
	}

	@Override
	public boolean canBlock()
	{
		return canBlock;
	}
}