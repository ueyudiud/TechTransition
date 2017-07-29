package ttr.api;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.util.LanguageManager;
import ttr.api.util.TTrStateMap;

public class TTrAPI
{
	@SidedProxy(serverSide = "ttr.api.TTrAPI$Common", clientSide = "ttr.api.TTrAPI$Client")
	public static Common proxy;
	public static Object ttr;
	public static final String ID = "TTr";
	
	public static boolean enableAutoInputAndOutputItemAndFluid;
	
	public static EntityPlayer player()
	{
		return proxy.playerInstance();
	}
	
	public static World world(int id)
	{
		return proxy.worldInstance(id);
	}
	
	public static String locale()
	{
		return proxy.locale();
	}
	
	public static boolean isSimulating()
	{
		return FMLCommonHandler.instance().getEffectiveSide().isServer();
	}
	
	public static void registerItemModel(Item item, int meta, String modid, String name)
	{
		proxy.registerItemModel(item, meta, modid, name);
	}
	
	public static class Common
	{
		public String locale()
		{
			return LanguageManager.ENGLISH;
		}
		
		public void registerItemModel(Item item, int meta, String modid, String name)
		{
			
		}
		
		public World worldInstance(int id)
		{
			return DimensionManager.getWorld(id);
		}
		
		public EntityPlayer playerInstance()
		{
			return null;
		}
		
		public File fileDir()
		{
			return new File(".");
		}
		
		public <V extends Comparable<V>> void registerForgeModel(Block block, String modelName, IProperty<V> property, IProperty...ignores)
		{
			registerForgeModel(block, modelName, property, true, ignores);
		}
		
		public <V extends Comparable<V>> void registerForgeModel(Block block, String modelName, IProperty<V> property, boolean byMapper, IProperty...ignores)
		{
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static class Client extends Common
	{
		@Override
		public EntityPlayer playerInstance()
		{
			return Minecraft.getMinecraft().thePlayer;
		}
		
		@Override
		public void registerItemModel(Item item, int meta, String modid, String name)
		{
			ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(modid + ":" + name, "inventory"));
		}
		
		@Override
		public World worldInstance(int id)
		{
			if (isSimulating())
				return super.worldInstance(id);
			else
			{
				World world = Minecraft.getMinecraft().theWorld;
				return world == null ? null : world.provider.getDimension() != id ? null : world;
			}
		}
		
		@Override
		public String locale()
		{
			return Minecraft.getMinecraft().getLanguageManager()
					.getCurrentLanguage().getLanguageCode();
		}
		
		@Override
		public File fileDir()
		{
			return Minecraft.getMinecraft().mcDataDir;
		}
		
		@Override
		public <V extends Comparable<V>> void registerForgeModel(Block block, String modelName, IProperty<V> property, boolean byMapper, IProperty...ignores)
		{
			TTrStateMap map =new TTrStateMap(modelName, byMapper ? property : null, ignores);
			IBlockState state0 = block.getDefaultState();
			Item item = Item.getItemFromBlock(block);
			if(property != null)
			{
				for(Object value : property.getAllowedValues())
				{
					IBlockState state1 = state0.withProperty(property, (V) value);
					ModelLoader.setCustomModelResourceLocation(item, block.getMetaFromState(state1), map.getModelResourceLocation(state1));
				}
			}
			else
			{
				ModelLoader.setCustomModelResourceLocation(item, 0, map.getModelResourceLocation(state0));
			}
			ModelLoader.setCustomStateMapper(block, map);
		}
	}
}