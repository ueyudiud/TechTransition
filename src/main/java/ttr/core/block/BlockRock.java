package ttr.core.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import ttr.api.TTrAPI;
import ttr.api.block.ItemBlockExt;
import ttr.api.material.Mat;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.stack.AbstractStack;
import ttr.api.stack.BaseStack;
import ttr.api.stack.OreStack;
import ttr.api.util.LanguageManager;
import ttr.api.util.Util;

public class BlockRock extends Block
{
	public static final PropertyEnum<RockType> ROCK_TYPE = PropertyEnum.create("rock_type", RockType.class);

	public static enum RockType implements IStringSerializable
	{
		resource("%s"),
		cobble("%s Cobble"),
		smoothed("Smoothed %s"),
		mossy("Mossy %s"),
		brick("%s Brick"),
		brick_crushed("Cracked %s Brick"),
		brick_mossy("Mossy %s Brick"),
		brick_compacted("Compacted %s Brick"),
		chiseled("Chiseled %s");

		static
		{
			resource.fallBreakMeta = cobble.ordinal();
			brick.fallBreakMeta = brick_crushed.ordinal();
			cobble.displayInTab = false;
			mossy.burnable = true;
			brick_mossy.burnable = true;
		}

		int noMossy = ordinal();
		int noSilkTouchDropMeta = ordinal();
		int fallBreakMeta = ordinal();
		boolean burnable;
		boolean displayInTab = true;
		String local;

		private RockType(String local)
		{
			this.local = local;
		}

		@Override
		public String getName()
		{
			return name();
		}

		public boolean isBurnable()
		{
			return burnable;
		}

		public RockType burned()
		{
			return values()[noMossy];
		}
	}

	public final Mat material;
	public final float hardnessMultiplier;
	public final float resistanceMultiplier;
	public final int harvestLevel;

	public BlockRock(Mat material)
	{
		super(Material.ROCK);
		setRegistryName("rock." + material.name);
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlockExt(this).setRegistryName("rock." + material.name));
		this.material = material;
		harvestLevel = material.blockHarvestLevel;
		setSoundType(SoundType.STONE);
		setHardness(hardnessMultiplier = material.blockHardness);
		setResistance(resistanceMultiplier = material.blockExplosionResistance);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

		for(RockType type : RockType.values())
		{
			LanguageManager.registerLocal(new ItemStack(this, 1, type.ordinal()).getUnlocalizedName() + ".name", String.format(type.local, material.localName));
		}
		OreDictionary.registerOre("stone" + material.oreDictName, new ItemStack(this, 1, 0));
		OreDictionary.registerOre("cobble" + material.oreDictName, new ItemStack(this, 1, 1));
		OreDictionary.registerOre("brick" + material.oreDictName, new ItemStack(this, 1, 4));
		OreDictionary.registerOre("brick" + material.oreDictName, new ItemStack(this, 1, 5));
		OreDictionary.registerOre("brick" + material.oreDictName, new ItemStack(this, 1, 6));
		OreDictionary.registerOre("brick" + material.oreDictName, new ItemStack(this, 1, 7));
		OreDictionary.registerOre("brick" + material.oreDictName, new ItemStack(this, 1, 8));
		OreDictionary.registerOre("stoneSmoothed" + material.oreDictName, new ItemStack(this, 1, 3));
		
		TemplateRecipeMap.FORGE_HAMMER.addRecipe(new BaseStack(this, 1, RockType.resource.ordinal()), (long) (50 * material.blockHardness), 40, new BaseStack(this, 1, RockType.cobble.ordinal()));
		TemplateRecipeMap.FORGE_HAMMER.addRecipe(new BaseStack(this, 1, RockType.brick.ordinal()), (long) (50 * material.blockHardness), 40, new BaseStack(this, 1, RockType.brick_crushed.ordinal()));
		TemplateRecipeMap.GRINDING.addRecipe(new AbstractStack[]{new BaseStack(this)}, null, new AbstractStack[]{new OreStack("dust" + material.oreDictName), new OreStack("dustSmall" + material.byproduct1.oreDictName)}, new int[][]{{10000, 5000, 2500, 1250}, {1000}}, null, (long) (100 * material.blockHardness), 24, 0, null);
		TemplateRecipeMap.GRINDING_STEAM.addRecipe(new AbstractStack[]{new BaseStack(this)}, null, new AbstractStack[]{new OreStack("dust" + material.oreDictName), new OreStack("dustTiny" + material.byproduct1.oreDictName)}, new int[][]{{10000, 2000}, {1000}}, null, (long) (120 * material.blockHardness), 16, 0, null);
		TTrAPI.proxy.registerForgeModel(this, "rock/" + material.name, ROCK_TYPE, false);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, ROCK_TYPE);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(ROCK_TYPE).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(ROCK_TYPE, RockType.values()[meta]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		for(RockType type : RockType.values())
			if(type.displayInTab)
			{
				list.add(new ItemStack(itemIn, 1, type.ordinal()));
			}
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		return true;
	}

	@Override
	public String getHarvestTool(IBlockState state)
	{
		return "pickaxe";
	}

	@Override
	public boolean isToolEffective(String type, IBlockState state)
	{
		return getHarvestTool(state).equals(type);
	}

	@Override
	public int getHarvestLevel(IBlockState state)
	{
		RockType type = state.getValue(ROCK_TYPE);
		switch (type)
		{
		case cobble :
		case mossy :
			return harvestLevel / 2;
		default:
			return harvestLevel;
		}
	}

	@Override
	protected ItemStack createStackedBlock(IBlockState state)
	{
		return new ItemStack(this, 1, state.getValue(ROCK_TYPE).ordinal());
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		List<ItemStack> ret = new ArrayList<ItemStack>();
		Random rand = world instanceof World ? ((World) world).rand : RANDOM;
		RockType type = state.getValue(ROCK_TYPE);
		ret.add(new ItemStack(this, 1, type.noSilkTouchDropMeta));
		return ret;
	}
	
	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return world.getBlockState(pos).getValue(ROCK_TYPE).burnable;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return isFlammable(world, pos, face) ? 40 : 0;
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return 0;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if(state.getValue(ROCK_TYPE).burnable && Util.isBlockNearby(worldIn, pos, Blocks.FIRE, true))
		{
			worldIn.setBlockState(pos, state.withProperty(ROCK_TYPE, RockType.values()[state.getValue(ROCK_TYPE).noMossy]), 3);
		}
	}

	@Override
	public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos,
			Predicate<IBlockState> target)
	{
		return (state.getValue(ROCK_TYPE) == RockType.resource) && (target.apply(getDefaultState()) || target.apply(Blocks.STONE.getDefaultState()));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
}