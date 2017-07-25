/*
 * copyright© 2016-2017 ueyudiud
 */
package ttr.core.render;

import java.util.Map;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.ImmutableMap;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.core.tile.pipe.TEFluidPipe;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeBronzeBig;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeBronzeMiddle;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeBronzeSmall;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeCopperBig;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeCopperMiddle;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeCopperSmall;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeSteelBig;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeSteelMiddle;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeSteelSmall;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeTungstenBig;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeTungstenMiddle;
import ttr.core.tile.pipe.TEFluidPipeLRC.TEFluidPipeTungstenSmall;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeStainlessSteelBig;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeStainlessSteelMiddle;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeStainlessSteelSmall;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeTitaniumBig;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeTitaniumMiddle;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeTitaniumSmall;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeTungstenSteelBig;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeTungstenSteelMiddle;
import ttr.core.tile.pipe.TEFluidPipeMRC.TEFluidPipeTungstenSteelSmall;

/**
 * @author ueyudiud
 */
@SideOnly(Side.CLIENT)
public class TESRFluidPipe extends TESRBase<TEFluidPipe>
{
	static Map<Class<? extends TEFluidPipe>, TextureAtlasSprite[]> textures;
	
	@Override
	public void renderTileEntityAt(TEFluidPipe te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		this.helper.setIconCoordScale(16.0F);
		TextureAtlasSprite[] sprites = textures.get(te.getClass());
		if (sprites != null)
		{
			bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			final float a = (8 - te.size) / 16.0F, b = (8 + te.size) / 16.0F;
			GL11.glPushMatrix();
			GL11.glTranslated(x, y, z);
			this.helper.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
			if (te.isLink(EnumFacing.DOWN))
			{
				renderYNeg(a, 0, a, b, a, b, sprites[1]);
				renderXNeg(a, 0, a, b, a, b, sprites[0]);
				renderXPos(a, 0, a, b, a, b, sprites[0]);
				renderZNeg(a, 0, a, b, a, b, sprites[0]);
				renderZPos(a, 0, a, b, a, b, sprites[0]);
				this.renderDown = false;
			}
			if (te.isLink(EnumFacing.UP))
			{
				renderYPos(a, b, a, b, 1, b, sprites[1]);
				renderXNeg(a, b, a, b, 1, b, sprites[0]);
				renderXPos(a, b, a, b, 1, b, sprites[0]);
				renderZNeg(a, b, a, b, 1, b, sprites[0]);
				renderZPos(a, b, a, b, 1, b, sprites[0]);
				this.renderUp = false;
			}
			if (te.isLink(EnumFacing.NORTH))
			{
				renderZNeg(a, a, 0, b, b, a, sprites[1]);
				renderXNeg(a, a, 0, b, b, a, sprites[0]);
				renderXPos(a, a, 0, b, b, a, sprites[0]);
				renderYNeg(a, a, 0, b, b, a, sprites[0]);
				renderYPos(a, a, 0, b, b, a, sprites[0]);
				this.renderNorth = false;
			}
			if (te.isLink(EnumFacing.SOUTH))
			{
				renderZPos(a, a, b, b, b, 1, sprites[1]);
				renderXNeg(a, a, b, b, b, 1, sprites[0]);
				renderXPos(a, a, b, b, b, 1, sprites[0]);
				renderYNeg(a, a, b, b, b, 1, sprites[0]);
				renderYPos(a, a, b, b, b, 1, sprites[0]);
				this.renderSouth = false;
			}
			if (te.isLink(EnumFacing.WEST))
			{
				renderXNeg(0, a, a, a, b, b, sprites[1]);
				renderYNeg(0, a, a, a, b, b, sprites[0]);
				renderYPos(0, a, a, a, b, b, sprites[0]);
				renderZNeg(0, a, a, a, b, b, sprites[0]);
				renderZPos(0, a, a, a, b, b, sprites[0]);
				this.renderWest = false;
			}
			if (te.isLink(EnumFacing.EAST))
			{
				renderXPos(b, a, a, 1, b, b, sprites[1]);
				renderYNeg(b, a, a, 1, b, b, sprites[0]);
				renderYPos(b, a, a, 1, b, b, sprites[0]);
				renderZNeg(b, a, a, 1, b, b, sprites[0]);
				renderZPos(b, a, a, 1, b, b, sprites[0]);
				this.renderEast = false;
			}
			this.helper.draw();
			renderCube(a, a, a, b, b, b, sprites[0]);
			this.renderDown = this.renderEast = this.renderNorth = this.renderSouth = this.renderUp = this.renderWest = true;
			GL11.glPopMatrix();
		}
	}
	
	public static void registerTextures(@Nullable TextureMap map)
	{
		ImmutableMap.Builder<Class<? extends TEFluidPipe>, TextureAtlasSprite[]> builder = ImmutableMap.builder();
		putTexures(builder, TEFluidPipeCopperSmall.class, map, "small/copper");
		putTexures(builder, TEFluidPipeBronzeSmall.class, map, "small/bronze");
		putTexures(builder, TEFluidPipeSteelSmall.class, map, "small/steel");
		putTexures(builder, TEFluidPipeStainlessSteelSmall.class, map, "small/steel");
		putTexures(builder, TEFluidPipeTungstenSmall.class, map, "small/tungsten");
		putTexures(builder, TEFluidPipeTungstenSteelSmall.class, map, "small/tungstensteel");
		putTexures(builder, TEFluidPipeTitaniumSmall.class, map, "small/titanium");
		putTexures(builder, TEFluidPipeCopperMiddle.class, map, "middle/copper");
		putTexures(builder, TEFluidPipeBronzeMiddle.class, map, "middle/bronze");
		putTexures(builder, TEFluidPipeSteelMiddle.class, map, "middle/steel");
		putTexures(builder, TEFluidPipeStainlessSteelMiddle.class, map, "middle/steel");
		putTexures(builder, TEFluidPipeTungstenMiddle.class, map, "middle/tungsten");
		putTexures(builder, TEFluidPipeTungstenSteelMiddle.class, map, "middle/tungstensteel");
		putTexures(builder, TEFluidPipeTitaniumMiddle.class, map, "middle/titanium");
		putTexures(builder, TEFluidPipeCopperBig.class, map, "large/copper");
		putTexures(builder, TEFluidPipeBronzeBig.class, map, "large/bronze");
		putTexures(builder, TEFluidPipeSteelBig.class, map, "large/steel");
		putTexures(builder, TEFluidPipeStainlessSteelBig.class, map, "large/steel");
		putTexures(builder, TEFluidPipeTungstenBig.class, map, "large/tungsten");
		putTexures(builder, TEFluidPipeTungstenSteelBig.class, map, "large/tungstensteel");
		putTexures(builder, TEFluidPipeTitaniumBig.class, map, "large/titanium");
		textures = builder.build();
	}
	
	private static void putTexures(ImmutableMap.Builder<Class<? extends TEFluidPipe>, TextureAtlasSprite[]> builder, Class<? extends TEFluidPipe> clazz, TextureMap map, String name)
	{
		if (map == null)
			builder.put(clazz, new TextureAtlasSprite[0]);
		else
			builder.put(clazz, new TextureAtlasSprite[] {
					map.registerSprite(new ResourceLocation("ttr", "blocks/pipe/fluid/" + name)),
					map.registerSprite(new ResourceLocation("ttr", "blocks/pipe/fluid/" + name + "_connected"))});
	}
	
	public static void registerTESR()
	{
		registerTextures(null);
		TESRFluidPipe tesr = new TESRFluidPipe();
		for (Class<? extends TEFluidPipe> clazz : textures.keySet())
		{
			ClientRegistry.bindTileEntitySpecialRenderer(clazz, tesr);
		}
	}
}