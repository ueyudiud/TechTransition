package ttr.api.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.material.Mat;
import ttr.api.material.MatCondition;
import ttr.api.util.UnlocalizedList;

public interface IItemMatProp
{
	String getSubName(int offset);
	
	int getOffsetMetaCount();

	int getMetaOffset(ItemStack stack, Mat material, MatCondition condition);
	
	void setInstanceFromMeta(ItemStack stack, int offset, Mat material, MatCondition condition);

	ItemStack onUpdate(ItemStack stack, World world, BlockPos pos);

	@SideOnly(Side.CLIENT)
	void addInformation(ItemStack stack, Mat material, MatCondition condition, UnlocalizedList list);
}