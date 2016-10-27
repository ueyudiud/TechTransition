package ttr.load;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import ttr.api.data.V;
import ttr.api.item.IToolStat;
import ttr.api.material.Mat;
import ttr.core.item.ToolStatBase;

public class TTrToolStats
{
	public static final IToolStat AXE = new ToolStatBase().setAttackSpeed(-3.0F).setBaseAttackDamage(2.0F).setDamage(1, 2, 2).setEffective(Material.WOOD, Material.LEAVES, Material.CACTUS);
	public static final IToolStat SHOVEL = new ToolStatBase()
	{
		@Override
		public boolean canHarvestDrop(ItemStack stack, IBlockState state)
		{
			Block block = state.getBlock();
			return block == Blocks.SNOW || block == Blocks.SNOW_LAYER;
		}
	}.setAttackSpeed(-3.0F).setBaseAttackDamage(1.5F).setDamage(1, 2, 2).setEffective(Material.SAND, Material.CLAY, Material.GROUND, Material.SNOW, Material.GRASS);
	public static final IToolStat PICKAXE = new ToolStatBase()
	{
		@Override
		public float getStrVsBlock(ItemStack stack, Mat material, IBlockState state)
		{
			int level = material.toolHarvestLevel;
			Material material1 = state.getMaterial();
			return (state.getBlock() == Blocks.OBSIDIAN && material.toolHarvestLevel >= 3) || ((material1 == Material.IRON || material1 == Material.ANVIL) && level > 1) ? material.toolHardness : super.getStrVsBlock(stack, material, state);
		}
	}.setAttackSpeed(-2.8F).setBaseAttackDamage(1.0F).setDamage(1, 2, 2).setEffective(Material.ROCK);
	public static final IToolStat HOE = new ToolStatBase().setAttackSpeed(-2.0F).setDamage(1, 1, 1);
	public static final IToolStat SWORD = new ToolStatBase().setAttackSpeed(-2.4F).setBaseAttackDamage(3.0F).setDamage(2, 1, 2).setEffective(Material.WEB, Material.LEAVES, Material.CLOTH, Material.PLANTS, Material.GOURD).setCanBlock();
	public static final IToolStat HARD_HAMMER = new ToolStatBase().setAttackSpeed(-4.0F).setBaseAttackDamage(4.0F).setDamageMultiplier(1.2F).setDamage(3, 2, 4).setEffective(Material.ROCK, Material.IRON);
	public static final IToolStat SAW = new ToolStatBase().setAttackSpeed(-1.2F).setBaseAttackDamage(1.5F).setDamage(2, 4, 3).setEffective(Material.WOOD);
	public static final IToolStat FILE = new ToolStatBase().setAttackSpeed(-1.0F).setBaseAttackDamage(1.0F).setDamage(3, 3, 2).setEffective(Material.IRON);
	public static final IToolStat SCREW_DRIVER = new ToolStatBase().setAttackSpeed(-1.0F).setBaseAttackDamage(1.0F).setDamage(4, 2, 2);
	public static final IToolStat WRENCH = new ToolStatBase().setAttackSpeed(-2.0F).setBaseAttackDamage(1.5F).setDamageMultiplier(1.2F).setDamage(4, 3, 4).setEffective(V.MATERIAL_MACHINE);
}