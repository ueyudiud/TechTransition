package ttr.core.render;

import javax.vecmath.Vector2f;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class TESRBase<T extends TileEntity> extends TileEntitySpecialRenderer<T>
{
	protected static final Vector2f STILL = new Vector2f(0.0F, 0.0F);
	
	//	private static final int[] X = { 0, 0, 1, 1 };
	//	private static final int[] Z = { 0, 1, 1, 0 };
	
	protected boolean renderUp = true;
	protected boolean renderDown = true;
	protected boolean renderNorth = true;
	protected boolean renderSouth = true;
	protected boolean renderEast = true;
	protected boolean renderWest = true;
	protected boolean enableColor = true;
	protected boolean diffuseLight = true;
	protected boolean flip = false;
	protected RenderHelper helper = RenderHelper.instance;
	protected float red = 1.0F;
	protected float green = 1.0F;
	protected float blue = 1.0F;
	protected float alpha = 1.0F;
	protected RenderEntityItem renderEntityItem;
	protected EntityItem entityItem;
	
	@Override
	public abstract void renderTileEntityAt(T tile, double x, double y, double z,
			float partialTicks, int destroyStage);
	
	protected TextureAtlasSprite getTexture(IBlockState state)
	{
		return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getBlockModelShapes().getTexture(state);
	}
	
	protected TextureAtlasSprite getStillTexture(FluidStack stack)
	{
		return getTexture(stack.getFluid().getStill(stack));
	}
	
	protected TextureAtlasSprite getTexture(ResourceLocation location)
	{
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
	}
	
	protected void setColor(FluidStack stack)
	{
		if(stack == null)
		{
			resetColor();
		}
		else
		{
			int color = stack.getFluid().getColor(stack);
			colorf(
					((color >> 16) & 0xFF) / 255F,
					((color >>  8) & 0xFF) / 255F,
					( color        & 0xFF) / 255F, 1.0F);
		}
	}
	
	protected void resetColor()
	{
		colorf(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	protected void colori(int rgb)
	{
		colorf(
				(rgb >> 16 & 0xFF) / 255.0F,
				(rgb >>  8 & 0xFF) / 255.0F,
				(rgb       & 0xFF) / 255.0F,
				1.0F);
	}
	
	protected void colorf(float r, float g, float b, float a)
	{
		this.red = r;
		this.green = g;
		this.blue = b;
		this.alpha = a;
	}
	
	protected float getU(TextureAtlasSprite icon, float x)
	{
		return icon.getInterpolatedU(x * 16);
	}
	
	protected float getV(TextureAtlasSprite icon, float x)
	{
		return icon.getInterpolatedV(x * 16);
	}
	
	protected void renderFluidFace(
			double xMin, double zMin, double xMax, double zMax,
			double y1, double y2, double y3, double y4,
			FluidStack stack, Vector2f flow)
	{
		if(stack != null)
		{
			setColor(stack);
			TextureAtlasSprite icon;
			float u1, u2, v1, v2;
			//			if(L.similar(flow.x, 0F) && L.similar(flow.y, 0F))
			{
				icon = getStillTexture(stack);
				u1 = getU(icon, (float) xMin);
				u2 = getU(icon, (float) xMax);
				v1 = getV(icon, (float) zMin);
				v2 = getV(icon, (float) zMax);
				this.helper
				.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR)
				.face(this.red, this.green, this.blue, this.alpha,
						xMin, y1, zMin, u1, v1,
						xMin, y2, zMax, u1, v2,
						xMax, y3, zMax, u2, v2,
						xMax, y4, zMin, u2, v1)
				.draw();
			}
			//FIXME
			//			else
			//			{
			//				float c1 = (float) Math.atan2(flow.y, flow.x);
			//				float a = MathHelper.sin(c1);
			//				float b = MathHelper.cos(c1);
			//				int dir = (int) (a / 90);
			//				float xCen = (float) ((xMin + xMax) / 2F);
			//				float zCen = (float) ((zMin + zMax) / 2F);
			//				float xScale = (float) (xMax - xMin) * b;
			//				float zScale = (float) (zMax - zMin) * b;
			//				float u3, v3, u4, v4;
			//				icon = getTexture(stack.getFluid().getFlowing(stack));
			//				u1 = icon.getInterpolatedU(16 * xCen + (b * (X[(dir    ) & 0x3] * 2 - 1) + a * (Z[(dir    ) & 0x3] * 2 - 1)) * xScale * 8);
			//				v1 = icon.getInterpolatedV(16 * zCen + (b * (X[(dir + 1) & 0x3] * 2 - 1) + a * (Z[(dir + 1) & 0x3] * 2 - 1)) * zScale * 8);
			//				u2 = icon.getInterpolatedU(16 * xCen + (b * (X[(dir + 1) & 0x3] * 2 - 1) + a * (Z[(dir + 1) & 0x3] * 2 - 1)) * xScale * 8);
			//				v2 = icon.getInterpolatedV(16 * zCen + (b * (X[(dir + 2) & 0x3] * 2 - 1) + a * (Z[(dir + 2) & 0x3] * 2 - 1)) * zScale * 8);
			//				u3 = icon.getInterpolatedU(16 * xCen + (b * (X[(dir + 2) & 0x3] * 2 - 1) + a * (Z[(dir + 2) & 0x3] * 2 - 1)) * xScale * 8);
			//				v3 = icon.getInterpolatedV(16 * zCen + (b * (X[(dir + 3) & 0x3] * 2 - 1) + a * (Z[(dir + 3) & 0x3] * 2 - 1)) * zScale * 8);
			//				u4 = icon.getInterpolatedU(16 * xCen + (b * (X[(dir + 3) & 0x3] * 2 - 1) + a * (Z[(dir + 3) & 0x3] * 2 - 1)) * xScale * 8);
			//				v4 = icon.getInterpolatedV(16 * zCen + (b * (X[(dir    ) & 0x3] * 2 - 1) + a * (Z[(dir    ) & 0x3] * 2 - 1)) * zScale * 8);
			//				this.helper.face(this.red, this.green, this.blue, this.alpha,
			//						xMin, y1, zMin, u1, v1,
			//						xMin, y2, zMax, u2, v2,
			//						xMax, y3, zMax, u3, v3,
			//						xMax, y4, zMin, u4, v4).draw();
			//			}
		}
	}
	
	protected void disableAllFace()
	{
		this.renderDown = this.renderUp = this.renderEast = this.renderWest = this.renderNorth = this.renderSouth = false;
	}
	
	protected void enableAllFace()
	{
		this.renderDown = this.renderUp = this.renderEast = this.renderWest = this.renderNorth = this.renderSouth = true;
	}
	
	protected void renderYPos(
			double x1, double y1, double z1,
			double x2, double y2, double z2,
			TextureAtlasSprite icon)
	{
		face(icon,
				x2, y2, z2, (float) x2, (float) z2,
				x2, y2, z1, (float) x2, (float) z1,
				x1, y2, z1, (float) x1, (float) z1,
				x1, y2, z2, (float) x1, (float) z2,
				0, 1, 0);
	}
	
	protected void renderYNeg(
			double x1, double y1, double z1,
			double x2, double y2, double z2,
			TextureAtlasSprite icon)
	{
		face(icon,
				x2, y1, z1, (float) x2, 1F - (float) z1,
				x2, y1, z2, (float) x2, 1F - (float) z2,
				x1, y1, z2, (float) x1, 1F - (float) z2,
				x1, y1, z1, (float) x1, 1F - (float) z1,
				0, -1, 0);
	}
	
	protected void renderZPos(
			double x1, double y1, double z1,
			double x2, double y2, double z2,
			TextureAtlasSprite icon)
	{
		face(icon,
				x1, y2, z2, (float) x1, 1F - (float) y2,
				x1, y1, z2, (float) x1, 1F - (float) y1,
				x2, y1, z2, (float) x2, 1F - (float) y1,
				x2, y2, z2, (float) x2, 1F - (float) y2,
				0, 0, 1);
	}
	
	protected void renderZNeg(
			double x1, double y1, double z1,
			double x2, double y2, double z2,
			TextureAtlasSprite icon)
	{
		face(icon,
				x2, y2, z1, 1F - (float) x2, 1F - (float) y2,
				x2, y1, z1, 1F - (float) x2, 1F - (float) y1,
				x1, y1, z1, 1F - (float) x1, 1F - (float) y1,
				x1, y2, z1, 1F - (float) x1, 1F - (float) y2,
				0, 0, -1);
	}
	
	protected void renderXPos(
			double x1, double y1, double z1,
			double x2, double y2, double z2,
			TextureAtlasSprite icon)
	{
		face(icon,
				x2, y2, z2, 1F - (float) z2, 1F - (float) y2,
				x2, y1, z2, 1F - (float) z2, 1F - (float) y1,
				x2, y1, z1, 1F - (float) z1, 1F - (float) y1,
				x2, y2, z1, 1F - (float) z1, 1F - (float) y2,
				1, 0, 0);
	}
	
	protected void renderXNeg(
			double x1, double y1, double z1,
			double x2, double y2, double z2,
			TextureAtlasSprite icon)
	{
		face(icon,
				x1, y2, z1, (float) z1, 1F - (float) y2,
				x1, y1, z1, (float) z1, 1F - (float) y1,
				x1, y1, z2, (float) z2, 1F - (float) y1,
				x1, y2, z2, (float) z2, 1F - (float) y2,
				-1, 0, 0);
	}
	
	protected void renderCube(
			double x1, double y1, double z1,
			double x2, double y2, double z2,
			TextureAtlasSprite[] icons)
	{
		this.helper.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
		if(this.renderDown)	renderYNeg(x1, y1, z1, x2, y2, z2, icons[0]);
		if(this.renderUp)	renderYPos(x1, y1, z1, x2, y2, z2, icons[1]);
		if(this.renderNorth)renderZNeg(x1, y1, z1, x2, y2, z2, icons[2]);
		if(this.renderSouth)renderZPos(x1, y1, z1, x2, y2, z2, icons[3]);
		if(this.renderWest)	renderXNeg(x1, y1, z1, x2, y2, z2, icons[4]);
		if(this.renderEast)	renderXPos(x1, y1, z1, x2, y2, z2, icons[5]);
		this.helper.draw();
	}
	
	protected void renderCube(
			double x1, double y1, double z1,
			double x2, double y2, double z2,
			TextureAtlasSprite icon)
	{
		this.helper.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
		if(this.renderUp) renderYPos(x1, y1, z1, x2, y2, z2, icon);
		if(this.renderDown) renderYNeg(x1, y1, z1, x2, y2, z2, icon);
		if(this.renderSouth) renderZPos(x1, y1, z1, x2, y2, z2, icon);
		if(this.renderNorth) renderZNeg(x1, y1, z1, x2, y2, z2, icon);
		if(this.renderEast) renderXPos(x1, y1, z1, x2, y2, z2, icon);
		if(this.renderWest) renderXNeg(x1, y1, z1, x2, y2, z2, icon);
		this.helper.draw();
	}
	
	private void face(TextureAtlasSprite icon,
			double x1, double y1, double z1, float u1, float v1,
			double x2, double y2, double z2, float u2, float v2,
			double x3, double y3, double z3, float u3, float v3,
			double x4, double y4, double z4, float u4, float v4,
			float nx, float ny, float nz)
	{
		if (!this.flip)
		{
			this.helper.face(icon, x1, y1, z1, u1, v1, x2, y2, z2, u2, v2, x3, y3, z3, u3, v3, x4, y4, z4, u4, v4, nx, ny, nz);
		}
		else
		{
			this.helper.face(icon, x1, y1, z1, u1, v1, x4, y4, z4, u4, v4, x3, y3, z3, u3, v3, x2, y2, z2, u2, v2, -nx, -ny, -nz);
		}
	}
	
	protected void face6Dir(
			double x, double y, double z,
			double uOfX, double uOfY, double uOfZ,
			double vOfX, double vOfY, double vOfZ,
			double u1, double v1, double u2, double v2)
	{
		this.helper.begin(GL11.GL_QUADS, this.enableColor ?
				DefaultVertexFormats.POSITION_TEX_COLOR :
					DefaultVertexFormats.POSITION_TEX);
		double d0 = u1;
		double d1 = v1;
		double d2 = u2;
		double d3 = v2;
		double d5 = x;
		double d6 = x + uOfX;
		double d7 = x + uOfX + vOfX;
		double d8 = x + vOfX;
		double d9 = z;
		double d10 = z + uOfZ;
		double d11 = z + uOfZ + vOfZ;
		double d12 = z + vOfZ;
		double d13 = y;
		double d14 = y + uOfY;
		double d15 = y + uOfY + vOfY;
		double d16 = y + vOfY;
		if (this.enableColor)
		{
			this.helper
			.vertex_ptc(d5, d13, d9, d2, d1, this.red, this.green, this.blue, this.alpha)
			.vertex_ptc(d6, d14, d10, d2, d3, this.red, this.green, this.blue, this.alpha)
			.vertex_ptc(d7, d15, d11, d0, d3, this.red, this.green, this.blue, this.alpha)
			.vertex_ptc(d8, d16, d12, d0, d1, this.red, this.green, this.blue, this.alpha)
			.vertex_ptc(d8, d16, d12, d0, d1, this.red, this.green, this.blue, this.alpha)
			.vertex_ptc(d7, d15, d11, d0, d3, this.red, this.green, this.blue, this.alpha)
			.vertex_ptc(d6, d14, d10, d2, d3, this.red, this.green, this.blue, this.alpha)
			.vertex_ptc(d5, d13, d9, d2, d1, this.red, this.green, this.blue, this.alpha);
		}
		else
		{
			this.helper
			.vertex_pt(d5, d13, d9, d2, d1)
			.vertex_pt(d6, d14, d10, d2, d3)
			.vertex_pt(d7, d15, d11, d0, d3)
			.vertex_pt(d8, d16, d12, d0, d1)
			.vertex_pt(d8, d16, d12, d0, d1)
			.vertex_pt(d7, d15, d11, d0, d3)
			.vertex_pt(d6, d14, d10, d2, d3)
			.vertex_pt(d5, d13, d9, d2, d1);
		}
		this.helper.draw();
	}
	
	public void renderItem(ItemStack stack, double x, double y, double z)
	{
		if (this.renderEntityItem == null || this.entityItem == null)
		{
			this.renderEntityItem = new RenderEntityItem(Minecraft.getMinecraft().getRenderManager(), Minecraft.getMinecraft().getRenderItem());
			this.entityItem = new EntityItem(getWorld());
		}
		this.entityItem.hoverStart = 0.0F;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		if (this.renderEntityItem.getRenderManager().options.fancyGraphics)
		{
			GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
		}
		this.entityItem.setEntityItemStack(stack);
		this.renderEntityItem.doRender(this.entityItem, 0, 0, 0, 0, 0);
		GL11.glPopMatrix();
	}
}