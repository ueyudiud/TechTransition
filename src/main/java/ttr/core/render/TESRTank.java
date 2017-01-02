/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.render;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fluids.FluidStack;
import ttr.core.tile.machine.TETankBottom;

/**
 * Created at 2016年12月22日 下午10:20:29
 * @author ueyudiud
 */
public class TESRTank extends TileEntitySpecialRenderer<TETankBottom>
{
	private float alpha = 1.0F;
	
	@Override
	public void renderTileEntityAt(TETankBottom tile, double x, double y, double z, float partialTicks, int destroyStage)
	{
		if(tile.tankSize == 0) return;
		List<FluidStack> gas;
		List<FluidStack> liquid;
		synchronized (tile.gas)
		{
			gas = ImmutableList.copyOf(tile.gas);
		}
		synchronized (tile.liquid)
		{
			liquid = ImmutableList.copyOf(tile.liquid);
		}
		int totalCapacity = tile.totalCapacity;
		int tankSize = tile.tankSize;
		if(!gas.isEmpty() || !liquid.isEmpty())
		{
			GL11.glPushMatrix();
			GlStateManager.disableLighting();
			GlStateManager.enableBlend();
			GL11.glTranslatef((float)x, (float)y, (float)z);
			float a = 0F, b = 0F;
			int amount = 0;
			if(!liquid.isEmpty())
			{
				for(FluidStack stack : liquid)
				{
					a = b;
					b = ((float) (amount += stack.amount) / (float) totalCapacity * tankSize) - 1E-2F;
					renderFace(x, y, z, stack, a, b);
				}
			}
			if(!gas.isEmpty())
			{
				FluidStack stack = null;
				for(int i = 0; i < gas.size(); ++i)
				{
					if(stack == null || stack.amount < gas.get(i).amount)
					{
						stack = gas.get(i);
						amount = stack.amount;
					}
				}
				this.alpha = (float) amount / (float) totalCapacity;
				renderFace(x, y, z, stack, b, tankSize - 1E-2F);
				this.alpha = 1.0F;
			}
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
			GL11.glPopMatrix();
		}
	}
	
	protected void renderFace(double x, double y, double z, FluidStack fluid, float height1, float height2)
	{
		int color = fluid.getFluid().getColor(fluid);
		float r = ((color >> 24) & 0xFF) / 255F;
		float g = ((color >> 16) & 0xFF) / 255F;
		float b = ((color >> 8) & 0xFF) / 255F;
		float a = ((color & 0xFF)) / 255F * this.alpha;
		float start = 1F / 16F + 1E-3F;
		float end = 15F / 16F - 1E-3F;
		TextureAtlasSprite icon = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getFluid().getStill(fluid).toString());
		float u1 = icon.getInterpolatedU(2F);
		float u2 = icon.getInterpolatedU(14F);
		float v1 = icon.getInterpolatedV(2F);
		float v2 = icon.getInterpolatedV(14F);
		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer buffer = tessellator.getBuffer();
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		
		vertex(buffer, start, height2, start, u1, v1, r, g, b, a);
		vertex(buffer, start, height2, end, u1, v2, r, g, b, a);
		vertex(buffer, end, height2, end, u2, v2, r, g, b, a);
		vertex(buffer, end, height2, start, u2, v1, r, g, b, a);
		
		vertex(buffer, end, height1, start, u2, v1, r, g, b, a);
		vertex(buffer, end, height1, end, u2, v2, r, g, b, a);
		vertex(buffer, start, height1, end, u1, v2, r, g, b, a);
		vertex(buffer, start, height1, start, u1, v1, r, g, b, a);
		
		for(int i = (int) height1; i < height2; ++i)
		{
			float heighta = i < height1 ? height1 : i;
			float heightb = i + 1 > height2 ? height2 : i + 1;
			v2 = icon.getInterpolatedV(16 * (1F - (heighta - i)));
			v1 = icon.getInterpolatedV(16 * (1F - (heightb - i)));
			
			vertex(buffer, end, heightb, start, u1, v1, r, g, b, a);
			vertex(buffer, end, heightb, end  , u2, v1, r, g, b, a);
			vertex(buffer, end, heighta, end  , u2, v2, r, g, b, a);
			vertex(buffer, end, heighta, start, u1, v2, r, g, b, a);
			
			vertex(buffer, start, heightb, end  , u1, v1, r, g, b, a);
			vertex(buffer, start, heightb, start, u2, v1, r, g, b, a);
			vertex(buffer, start, heighta, start, u2, v2, r, g, b, a);
			vertex(buffer, start, heighta, end  , u1, v2, r, g, b, a);
			
			vertex(buffer, end  , heightb, end, u1, v1, r, g, b, a);
			vertex(buffer, start, heightb, end, u2, v1, r, g, b, a);
			vertex(buffer, start, heighta, end, u2, v2, r, g, b, a);
			vertex(buffer, end  , heighta, end, u1, v2, r, g, b, a);
			
			vertex(buffer, start, heightb, start, u1, v1, r, g, b, a);
			vertex(buffer, end  , heightb, start, u2, v1, r, g, b, a);
			vertex(buffer, end  , heighta, start, u2, v2, r, g, b, a);
			vertex(buffer, start, heighta, start, u1, v2, r, g, b, a);
		}
		tessellator.draw();
	}
	
	protected void vertex(VertexBuffer buffer, double x, double y, double z, double u, double v, float r, float g, float b, float a)
	{
		buffer.pos(x, y, z).tex(u, v).color(r, g, b, a).endVertex();
	}
}