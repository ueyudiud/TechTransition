/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.item.stat;

import ttr.api.enums.EnumMaterial;
import ttr.api.item.ToolStat;
import ttr.api.util.SubTag;

/**
 * Created at 2016年12月20日 上午12:10:01
 * @author ueyudiud
 */
public class ToolSoftHammer extends ToolStat
{
	public ToolSoftHammer()
	{
		super(3L, 4L, 2L, -1L);
		setAttackProperty(1.0F, 0.7F, -2.0F);
	}
	
	@Override
	public boolean isMaterialAccess(EnumMaterial material)
	{
		return material.toolQuality >= 0 && material.contain(SubTag.SOFT_TOOL);
	}
}