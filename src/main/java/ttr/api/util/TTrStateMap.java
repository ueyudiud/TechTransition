package ttr.api.util;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TTrStateMap extends StateMapperBase
{
	private String locate;
	private IProperty property;
	private List<IProperty> ignored;
	
	public TTrStateMap(String locate, IProperty property, IProperty...ignored)
	{
		this.locate = locate;
		this.property = property;
		this.ignored = ImmutableList.copyOf(ignored);
	}
	
	@Override
	public ModelResourceLocation getModelResourceLocation(IBlockState state)
	{
		Map<IProperty<?>, Comparable<?>> map = Maps.<IProperty<?>, Comparable<?>>newLinkedHashMap(state.getProperties());
		String domain = state.getBlock().getRegistryName().getResourceDomain();
		String locate;
		if(property != null)
		{
			locate = this.locate + "/" + removeAndGetName(property, map);
		}
		else
		{
			locate = this.locate;
		}
		for(IProperty property : ignored)
		{
			map.remove(property);
		}
		return new ModelResourceLocation(domain + ":" + locate, getPropertyString(map));
	}
	
	private <T extends Comparable<T>> String removeAndGetName(IProperty<T> property, Map<IProperty<?>, Comparable<?>> map)
	{
		return property.getName((T) map.remove(this.property));
	}
}