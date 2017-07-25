/*
 * copyright© 2016-2017 ueyudiud
 */
package ttr.core.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.core.util.BlockStateWrapper;

/**
 * @author ueyudiud
 */
@SideOnly(Side.CLIENT)
public class ModelMachine implements IModel, ICustomModelLoader
{
	public static final ResourceLocation LOCATION = new ResourceLocation("ttr", "block/machine");
	public static final Map<String, ResourceLocation> TEXTURES = new HashMap<>();
	private static final ModelMachine MODEL = new ModelMachine();
	
	public static void init()
	{
		ModelLoaderRegistry.registerLoader(MODEL);
	}
	
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager)
	{
		
	}
	
	@Override
	public boolean accepts(ResourceLocation modelLocation)
	{
		return LOCATION.equals(modelLocation);
	}
	
	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception
	{
		return this;
	}
	
	@Override
	public Collection<ResourceLocation> getDependencies()
	{
		return ImmutableList.of();
	}
	
	@Override
	public Collection<ResourceLocation> getTextures()
	{
		return TEXTURES.values();
	}
	
	@Override
	public IBakedModel bake(IModelState state, VertexFormat format,
			Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
	{
		return null;
	}
	
	@Override
	public IModelState getDefaultState()
	{
		return ModelRotation.X0_Y0;
	}
	
	private static class VariantState extends BlockStateWrapper
	{
		String[] icons;
		
		VariantState(IBlockState parent)
		{
			super(parent);
		}
		
		@Override
		protected BlockStateWrapper wrap(IBlockState state)
		{
			return new VariantState(state);
		}
	}
	
	private static class BakedModel implements IBakedModel
	{
		@Override
		public List<BakedQuad> getQuads(@Nullable IBlockState state, EnumFacing side, long rand)
		{
			if (state == null)
			{
				
			}
			return null;
		}
		
		@Override
		public boolean isAmbientOcclusion()
		{
			return true;
		}
		
		@Override
		public boolean isGui3d()
		{
			return true;
		}
		
		@Override
		public boolean isBuiltInRenderer()
		{
			return false;
		}
		
		@Override
		public TextureAtlasSprite getParticleTexture()
		{
			return null;
		}
		
		@Override
		public ItemCameraTransforms getItemCameraTransforms()
		{
			return ItemCameraTransforms.DEFAULT;
		}
		
		@Override
		public ItemOverrideList getOverrides()
		{
			return ItemOverrideList.NONE;
		}
	}
}