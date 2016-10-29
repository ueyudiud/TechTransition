package ttr.core.gui.abstracts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTankInfo;
import ttr.api.util.LanguageManager;

public abstract class GuiContainerBase extends GuiContainer
{
	private static final ResourceLocation voidTexture = new ResourceLocation("ttr:textures/gui/void.png");
	
	private final ResourceLocation location;

	public ContainerBase container;
	protected int xoffset;
	protected int yoffset;

	public GuiContainerBase(ContainerBase container)
	{
		this(container, 176, 166);
	}

	public GuiContainerBase(ContainerBase container, int ySize)
	{
		this(container, 176, ySize);
	}

	public GuiContainerBase(ContainerBase container, int xSize, int ySize)
	{
		super(container);
		this.container = container;
		this.ySize = ySize;
		this.xSize = xSize;
		location = getResourceLocation();
	}
	
	@Override
	protected final void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		if(shouldRenderName())
		{
			String str = getName();
			fontRendererObj.drawString(str, (xSize - fontRendererObj.getStringWidth(str)) / 2, 6, 0x404040);
		}
		fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
		drawFrontgroundOther(par1, par2);
		for(FluidSlot slot : container.fluidSlotList)
		{
			par1 -= xoffset;
			par2 -= yoffset;
			if(slot.shouldRender() && slot.x <= par1 && slot.x + slot.w >= par1 && slot.y <= par2 && slot.y + slot.h >= par2)
			{
				FluidTankInfo info = slot.getTankInfo();
				if(info.fluid != null)
				{
					List<String> texts = new ArrayList();
					texts.add("Fluid : " + info.fluid.getLocalizedName());
					texts.add(ChatFormatting.GREEN + "Amount : " + info.fluid.amount + "L");
					texts.add(ChatFormatting.RED + "Temperature : " + info.fluid.getFluid().getTemperature(info.fluid) + "K");
					drawHoveringText(texts, par1, par2);
				}
				break;
			}
		}
	}

	protected void drawFrontgroundOther(int x, int y)
	{

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(location);
		xoffset = (width - xSize) / 2;
		yoffset = (height - ySize) / 2;
		drawTexturedModalRect(xoffset, yoffset, 0, 0, xSize, ySize);
		drawUnderFluid(xoffset, yoffset);
		mc.getTextureManager().bindTexture(location);
		drawOther(xoffset, yoffset, x, y);
	}
	
	protected void drawUnderFluid(int xO, int yO)
	{
		GL11.glPushMatrix();
		for(FluidSlot slot : container.fluidSlotList)
		{
			slot.renderFluidInSlot(this);
		}
		GL11.glPopMatrix();
	}
	
	protected abstract void drawOther(int xOffset, int yOffset, int aMouseXPosition, int aMouseYPosition);

	@Deprecated
	public boolean hasCustomName()
	{
		return container instanceof ContainerBase ? container.inv.hasCustomName() : false;
	}
	
	public String getName()
	{
		return container.inv.hasCustomName() ? container.inv.getDisplayName().getFormattedText() :
			LanguageManager.translateToLocal(container.inv.getName());
	}
	
	protected boolean shouldRenderName()
	{
		return true;
	}

	public abstract ResourceLocation getResourceLocation();
	
	public void drawFluid(int x, int y, FluidTankInfo tank, int width, int height)
	{
		drawFluid(x, y, tank, width, height, false);
	}
	public void drawFluid(int x, int y, FluidTankInfo info, int width, int height, boolean lay)
	{
		xoffset = (this.width - xSize) / 2;
		yoffset = (this.height - ySize) / 2;
		
		if(info.fluid == null) return;
		if (info.fluid.amount > 0)
		{
			TextureAtlasSprite fluidIcon =
					mc.getTextureMapBlocks().getAtlasSprite(info.fluid.getFluid().getStill(info.fluid).toString());
			if (fluidIcon != null)
			{
				mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				int color = info.fluid.getFluid().getColor(info.fluid);
				drawTexturedModalRect(xoffset + x, yoffset + y, fluidIcon, width, height);
				//				if(lay)
				//				{
				//					drawRepeated(fluidIcon, xoffset + x, yoffset + y, (double) (info.fluid.amount * width) / (double)info.capacity, height, zLevel, color);
				//				}
				//				else
				//				{
				//					drawRepeated(fluidIcon, xoffset + x, yoffset + y + height - (double) (info.fluid.amount * height) / (double) info.capacity, width, (double) (info.fluid.amount * height) / (double) info.capacity, zLevel, color);
				//				}
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(location);
			}
		}
	}
	
	protected void drawRepeated(TextureAtlasSprite icon, double x, double y, double width, double height, double z, int color)
	{
		double iconWidthStep = ((double)icon.getMaxU() - (double)icon.getMinU()) / 16D;
		double iconHeightStep = ((double)icon.getMaxV() - (double)icon.getMinV()) / 16D;
		int a = color >>> 24 & 0xFF;
		int r = color >>> 16 & 0xFF;
		int g = color >>> 8 & 0xFF;
		int b = color & 0xFF;

		GL11.glColor4f(1F, 1F, 1F, 1F);
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer buffer = tessellator.getBuffer();
		buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		for (double cy = y; cy < y + height; cy += 16D)
		{
			double quadHeight = Math.min(16D, (height + y) - cy);
			double maxY = cy + quadHeight;
			double maxV = icon.getMinV() + iconHeightStep * quadHeight;
			for (double cx = x; cx < x + width; cx += 16D)
			{
				double quadWidth = Math.min(16D, (width + x) - cx);
				double maxX = cx + quadWidth;
				double maxU = icon.getMinU() + iconWidthStep * quadWidth;
				buffer.pos(cx, maxY, z).tex(icon.getMinU(), maxV).color(r, g, b, a);
				buffer.pos(maxX, maxY, z).tex(maxU, maxV).color(r, g, b, a);
				buffer.pos(maxX, cy, z).tex(maxU, icon.getMinV()).color(r, g, b, a);
				buffer.pos(cx, cy, z).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, a);
			}
		}
		tessellator.draw();
	}

	protected void drawAreaTooltip(int mouseX, int mouseY, String tooltip, int x, int y, int u, int v)
	{
		if (mouseX >= x && mouseX <= (x + u) && mouseY >= y && mouseY <= (y + v))
		{
			drawTooltip(mouseX - xoffset, mouseY - yoffset, tooltip);
		}
	}

	protected void drawTooltip(int x, int y, String tooltip)
	{
		drawTooltip(x, y, Arrays.asList(tooltip));
	}
	protected void drawTooltip(int x, int y, List<String> tooltip)
	{
		if (!tooltip.isEmpty())
		{
			FontRenderer font = Minecraft.getMinecraft().fontRendererObj;
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			int k = 0;
			Iterator iterator = tooltip.iterator();

			while (iterator.hasNext())
			{
				String s = (String)iterator.next();
				int l = font.getStringWidth(s);

				if (l > k)
				{
					k = l;
				}
			}

			int j2 = x + 12;
			int k2 = y - 12;
			int i1 = 8;

			if (tooltip.size() > 1)
			{
				i1 += 2 + (tooltip.size() - 1) * 10;
			}

			if (j2 + k > width)
			{
				j2 -= 28 + k;
			}

			if (k2 + i1 + 6 > height)
			{
				k2 = height - i1 - 6;
			}

			zLevel = 300.0F;
			itemRender.zLevel = 300.0F;
			int j1 = -267386864;
			drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
			drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
			drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
			drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
			drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
			int k1 = 1347420415;
			int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
			drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
			drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
			drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
			drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);

			for (int i2 = 0; i2 < tooltip.size(); ++i2)
			{
				String s1 = tooltip.get(i2);
				font.drawStringWithShadow(s1, j2, k2, -1);

				if (i2 == 0)
				{
					k2 += 2;
				}

				k2 += 10;
			}

			zLevel = 0.0F;
			itemRender.zLevel = 0.0F;
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		}
	}
	
	//	protected void drawRect(int x, int y, int u, int v, int color)
	//	{
	//		mc.renderEngine.bindTexture(voidTexture);
	//        float f = (float)(color >> 16 & 255) / 255.0F;
	//        float f1 = (float)(color >> 8 & 255) / 255.0F;
	//        float f2 = (float)(color & 255) / 255.0F;
	//        Tessellator tessellator = Tessellator.instance;
	//        GL11.glEnable(GL11.GL_BLEND);
	//        GL11.glDisable(GL11.GL_TEXTURE_2D);
	//        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
	//        GL11.glColor4f(f, f1, f2, 1.0F);
	//        tessellator.startDrawingQuads();
	//        tessellator.addVertex((double)x, (double)y + v, 0.0D);
	//        tessellator.addVertex((double)x + u, (double)y + v, 0.0D);
	//        tessellator.addVertex((double)x + u, (double)y, 0.0D);
	//        tessellator.addVertex((double)x, (double)y, 0.0D);
	//        tessellator.draw();
	//        GL11.glEnable(GL11.GL_TEXTURE_2D);
	//        GL11.glDisable(GL11.GL_BLEND);
	//        mc.renderEngine.bindTexture(getResourceLocation());
	//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	//	}
}