package ttr.core.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.core.tile.TEMachineBase;

@SideOnly(Side.CLIENT)
public class TESRMachine<T extends TEMachineBase> extends TileEntitySpecialRenderer<T>
{
	@Override
	public void renderTileEntityAt(T tile, double x, double y, double z, float partialTicks, int destroyStage)
	{
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		renderSelectBoundMatrix(tile, partialTicks);
		GL11.glPopMatrix();
	}

	private static final float NE = 0.25F;
	private static final float PO = 0.75F;
	private static final float OFF = 1E-3F;
	
	protected boolean shouldDisplayMatrix(T tile, EnumFacing facing, EntityPlayer player)
	{
		return tile.shouldDisplayMatrixWhenSelected(facing, player);
	}
	
	protected void renderSelectBoundMatrix(T tile, float partialTicks)
	{
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if(player == null) return;
		RayTraceResult result = player.rayTrace(4.0F, partialTicks);
		if(result != null && result.typeOfHit == Type.BLOCK &&
				tile.getPos().equals(result.getBlockPos()) && shouldDisplayMatrix(tile, result.sideHit, player))
		{
			Tessellator tessellator = Tessellator.getInstance();
			VertexBuffer buffer = tessellator.getBuffer();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
			GL11.glLineWidth(1.0F);
			buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
			switch (result.sideHit)
			{
			case UP :
				drawLine(buffer, NE, 1 + OFF, 0, NE, 1 + OFF, 1, 0F, 0F, 0F, 1F);
				drawLine(buffer, PO, 1 + OFF, 0, PO, 1 + OFF, 1, 0F, 0F, 0F, 1F);
				drawLine(buffer, 0, 1 + OFF, NE, 1, 1 + OFF, NE, 0F, 0F, 0F, 1F);
				drawLine(buffer, 0, 1 + OFF, PO, 1, 1 + OFF, PO, 0F, 0F, 0F, 1F);
				break;
			case DOWN :
				drawLine(buffer, NE, 0 - OFF, 0, NE, 0 - OFF, 0, 0F, 0F, 0F, 1F);
				drawLine(buffer, PO, 0 - OFF, 0, PO, 0 - OFF, 1, 0F, 0F, 0F, 1F);
				drawLine(buffer, 0, 0 - OFF, NE, 1, 0 - OFF, NE, 0F, 0F, 0F, 1F);
				drawLine(buffer, 0, 0 - OFF, PO, 1, 0 - OFF, PO, 0F, 0F, 0F, 1F);
				break;
			case SOUTH :
				drawLine(buffer, NE, 0, 1 + OFF, NE, 1, 1 + OFF, 0F, 0F, 0F, 1F);
				drawLine(buffer, PO, 0, 1 + OFF, PO, 1, 1 + OFF, 0F, 0F, 0F, 1F);
				drawLine(buffer, 0, NE, 1 + OFF, 1, NE, 1 + OFF, 0F, 0F, 0F, 1F);
				drawLine(buffer, 0, PO, 1 + OFF, 1, PO, 1 + OFF, 0F, 0F, 0F, 1F);
				break;
			case NORTH :
				drawLine(buffer, NE, 0, 0 - OFF, NE, 1, 0 - OFF, 0F, 0F, 0F, 1F);
				drawLine(buffer, PO, 0, 0 - OFF, PO, 1, 0 - OFF, 0F, 0F, 0F, 1F);
				drawLine(buffer, 0, NE, 0 - OFF, 1, NE, 0 - OFF, 0F, 0F, 0F, 1F);
				drawLine(buffer, 0, PO, 0 - OFF, 1, PO, 0 - OFF, 0F, 0F, 0F, 1F);
				break;
			case EAST :
				drawLine(buffer, 1 + OFF, NE, 0, 1 + OFF, NE, 1, 0F, 0F, 0F, 1F);
				drawLine(buffer, 1 + OFF, PO, 0, 1 + OFF, PO, 1, 0F, 0F, 0F, 1F);
				drawLine(buffer, 1 + OFF, 0, NE, 1 + OFF, 1, NE, 0F, 0F, 0F, 1F);
				drawLine(buffer, 1 + OFF, 0, PO, 1 + OFF, 1, PO, 0F, 0F, 0F, 1F);
				break;
			case WEST :
				drawLine(buffer, 0 - OFF, NE, 0, 0 - OFF, NE, 1, 0F, 0F, 0F, 1F);
				drawLine(buffer, 0 - OFF, PO, 0, 0 - OFF, PO, 1, 0F, 0F, 0F, 1F);
				drawLine(buffer, 0 - OFF, 0, NE, 0 - OFF, 1, NE, 0F, 0F, 0F, 1F);
				drawLine(buffer, 0 - OFF, 0, PO, 0 - OFF, 1, PO, 0F, 0F, 0F, 1F);
				break;
			}
			tessellator.draw();
			GL11.glDisable(GL11.GL_LINE_SMOOTH);
			GL11.glDisable(GL11.GL_BLEND);
		}
	}
	
	protected void drawLine(VertexBuffer buffer, float x1, float y1, float z1, float x2, float y2, float z2, float red, float green, float blue, float alpha)
	{
		buffer.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
		buffer.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
	}
}