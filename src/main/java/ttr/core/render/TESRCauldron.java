package ttr.core.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.core.tile.machine.TECauldron;

@SideOnly(Side.CLIENT)
public class TESRCauldron extends TileEntitySpecialRenderer<TECauldron>
{
	@Override
	public void renderTileEntityAt(TECauldron tile, double x, double y, double z, float partialTicks, int destroyStage)
	{
		Fluid fluid = tile.getFluidType();
		if(fluid != null && tile.level > 0)
		{
			GL11.glPushMatrix();
			GlStateManager.disableLighting();
			GlStateManager.enableBlend();
			renderFace(x, y, z, fluid, tile.level);
			GlStateManager.disableBlend();
			GlStateManager.enableLighting();
			GL11.glPopMatrix();
		}
	}

	protected void renderFace(double x, double y, double z, Fluid fluid, int level)
	{
		int color = fluid.getColor();
		float r = ((color >> 24) & 0xFF) / 255F;
		float g = ((color >> 16) & 0xFF) / 255F;
		float b = ((color >> 8) & 0xFF) / 255F;
		float a = ((color & 0xFF)) / 255F;
		GL11.glTranslatef((float)x, (float)y, (float)z);
		float start = 2F / 16F;
		float end = 14F / 16F;
		TextureAtlasSprite icon = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getStill().toString());
		float u1 = icon.getInterpolatedU(2F);
		float u2 = icon.getInterpolatedU(14F);
		float v1 = icon.getInterpolatedV(2F);
		float v2 = icon.getInterpolatedV(14F);
		float height = (6 + 3 * level) / 16F;
		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer buffer = tessellator.getBuffer();
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		vertex(buffer, start, height, start, u1, v1, r, g, b, a);
		vertex(buffer, start, height, end, u1, v2, r, g, b, a);
		vertex(buffer, end, height, end, u2, v2, r, g, b, a);
		vertex(buffer, end, height, start, u2, v1, r, g, b, a);
		tessellator.draw();
	}

	protected void vertex(VertexBuffer buffer, double x, double y, double z, double u, double v, float r, float g, float b, float a)
	{
		buffer.pos(x, y, z).tex(u, v).color(r, g, b, a).endVertex();
	}
}