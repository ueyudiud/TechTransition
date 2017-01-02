package ttr.api.item;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumTools;
import ttr.api.util.SubTag;

public class ToolStat
{
	final long damagePerAttack;
	final long damagePerUse;
	final long damagePerDestory;
	final long dischargePerUse;
	
	float harvestSpeedBase;
	float harvestSpeedMultiple = 1.0F;
	
	float damageVsEntityBase;
	float damageVsEntityMultiple = 1.0F;
	float attackSpeed;
	
	int tier = -1;
	long transferLimit;
	long maxCharge;
	
	Set<String> toolClasses = ImmutableSet.of();
	
	public ToolStat(long damagePerAttack, long damagePerUse, long damagePerDestory, long dischargePerUse)
	{
		this.damagePerAttack = damagePerAttack;
		this.damagePerDestory = damagePerDestory;
		this.damagePerUse = damagePerUse;
		this.dischargePerUse = dischargePerUse;
	}
	
	public ToolStat setElectrical(int tier, long maxCharge, long transferLimit)
	{
		this.tier = tier;
		this.maxCharge = maxCharge;
		this.transferLimit = transferLimit;
		return this;
	}
	
	public ToolStat setToolClasses(EnumTools tools)
	{
		return setToolClasses(tools.name());
	}
	public ToolStat setToolClasses(String...tools)
	{
		this.toolClasses = ImmutableSet.copyOf(tools);
		return this;
	}
	
	public ToolStat setAttackProperty(float atkBase, float atkMul, float atkSpeed)
	{
		this.damageVsEntityBase = atkBase;
		this.damageVsEntityMultiple = atkMul;
		this.attackSpeed = atkSpeed;
		return this;
	}
	
	public ToolStat setHarvestProperty(float harBase, float hasMul)
	{
		this.harvestSpeedBase = harBase;
		this.harvestSpeedMultiple = hasMul;
		return this;
	}
	
	short usableID;
	short unusableID;
	
	public boolean isMaterialAccess(EnumMaterial material)
	{
		return material.toolQuality >= 0 && !material.contain(SubTag.SOFT_TOOL);
	}
}