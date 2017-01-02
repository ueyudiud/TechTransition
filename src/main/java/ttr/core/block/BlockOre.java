/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.core.block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyHelper;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;
import ttr.api.enums.EnumTools;
import ttr.api.enums.TextureSet;
import ttr.api.item.ItemBlockBase;
import ttr.api.recipe.MaterialInstance;
import ttr.api.recipe.TTrRecipeAdder;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.stack.BaseStack;
import ttr.api.util.ParticleDiggingExt;
import ttr.api.util.SubTag;
import ttr.core.TTrMaterialHandler;
import ttr.core.block.BlockRock.RockType;
import ttr.core.tile.TEOre;
import ttr.load.TTrIBF;

/**
 * Created at 2016年12月20日 下午8:46:30
 * @author ueyudiud
 */
public class BlockOre extends Block implements ITileEntityProvider
{
	private static final ThreadLocal<TEOre> THREAD = new ThreadLocal();
	
	public static enum RockBase implements IStringSerializable
	{
		STONE("stone", EnumMaterial.Stone),
		NETHER_STONE("netherrack", EnumMaterial.Netherrack),
		//		ENDSTONE("endstone"),
		MARBLE("marble", EnumMaterial.Marble);
		
		final String name;
		final EnumMaterial material;
		IBlockState base;
		
		RockBase(String name, EnumMaterial material)
		{
			this.name = name;
			this.material = material;
		}
		
		@Override
		public String getName()
		{
			return this.name;
		}
	}
	
	public static final IProperty<String> ORE_COVER = new PropertyHelper<String>("type", String.class)
	{
		ImmutableList<String> values;
		
		@Override
		public Collection<String> getAllowedValues()
		{
			if(this.values == null)
			{
				Set<String> set = new HashSet();
				for(TextureSet s : TextureSet.TEXTURE_SETS)
				{
					String tag = s.getLocation(EnumOrePrefix.ore);
					if(tag != null)
					{
						set.add(tag);
					}
				}
				set.add("default");
				this.values = ImmutableList.copyOf(set);
			}
			return this.values;
		}
		
		@Override
		public String getName(String value)
		{
			return value;
		}
		
		
		@Override
		public Optional<String> parseValue(String value)
		{
			return Optional.of(value);
		}
	};
	
	public static final PropertyEnum<RockBase> ROCK_BASE = PropertyEnum.create("rock", RockBase.class);
	
	public final Item[] itemList;
	
	public BlockOre()
	{
		super(Material.ROCK);
		setUnlocalizedName("ttr.ore");
		setRegistryName("ore");
		setDefaultState(getDefaultState().withProperty(ROCK_BASE, RockBase.STONE).withProperty(ORE_COVER, "default"));
		GameRegistry.register(this);
		this.itemList = new Item[RockBase.values().length];
		for(int i = 0; i < this.itemList.length; this.itemList[i++] = new ItemBlockBase(this, i == 0, "ore" + i).setHasSubtypes(true));
	}
	
	public void postinit()
	{
		RockBase.STONE.base = Blocks.STONE.getDefaultState();
		RockBase.NETHER_STONE.base = Blocks.NETHERRACK.getDefaultState();
		RockBase.MARBLE.base = TTrIBF.marble.getDefaultState().withProperty(BlockRock.ROCK_TYPE, RockType.resource);
		for(EnumMaterial material : EnumMaterial.getMaterials())
		{
			if(material != null && EnumOrePrefix.ore.access(material))
			{
				for(RockBase rock : BlockOre.RockBase.values())
				{
					ItemStack stack = new ItemStack(this.itemList[rock.ordinal()], 1, material.id);
					if(!EnumOrePrefix.gem.access(material))
					{
						TTrRecipeAdder.addGrindingRecipe(new BaseStack(stack),
								new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.crushed, material, 2L)),
								new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.crushed, material, 1L)),
								new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dustSmall, rock.material, 1L)), new int[]{8000, 2000, 500},
								new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dustOreTiny, material.byproduct1, 1L)), new int[]{2000, 1000, 500, 250},
								300, 12);
					}
					else
					{
						TTrRecipeAdder.addGrindingRecipe(new BaseStack(stack),
								new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.crushed, material, 2L)),
								new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.crushed, material, 1L)),
								new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dustSmall, rock.material, 1L)), new int[]{8000, 2000, 500},
								new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.gemChip, material, 1L)), new int[]{800, 400, 200},
								300, 12);
					}
					if(material.contain(SubTag.METAL) && !material.contain(SubTag.BLAST_REQUIRED) && material.contain(SubTag.NORMAL_ORE_PROCESSING))
					{
						TemplateRecipeMap.SMELTING.addRecipe(new BaseStack(stack), 200, 20 * material.heatCapability / 1000L, new BaseStack(TTrMaterialHandler.getItemStack(EnumOrePrefix.dust, material, 1L)));
					}
				}
				ItemStack instance = new ItemStack(this, 1, material.id);
				if(!MaterialInstance.hasItemStack(EnumOrePrefix.ore, material))
				{
					MaterialInstance.registerInstanceItem(EnumOrePrefix.ore, material, instance, true);
				}
				TTrMaterialHandler.registerOre(instance, material, EnumOrePrefix.ore);
			}
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, ORE_COVER, ROCK_BASE);
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile instanceof TEOre)
		{
			state = state.withProperty(ORE_COVER, ((TEOre) tile).material.textureSet.getLocation(EnumOrePrefix.ore));
		}
		return state;
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(ROCK_BASE).ordinal();
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, ItemStack stack)
	{
		IBlockState state = getDefaultState();
		for(int i = 0; i < this.itemList.length; ++i)
		{
			if(stack.getItem() == this.itemList[i])
			{
				state.withProperty(ROCK_BASE, RockBase.values()[i]);
			}
		}
		return state;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack)
	{
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile instanceof TEOre)
		{
			((TEOre) tile).material = EnumMaterial.getMaterial(stack.getItemDamage());
		}
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(ROCK_BASE, RockBase.values()[meta]);
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos,
			IBlockState state, TileEntity tile, ItemStack stack)
	{
		if(tile instanceof TEOre)
		{
			THREAD.set((TEOre) tile);
		}
		super.harvestBlock(world, player, pos, state, tile, stack);
		THREAD.set(null);
	}
	
	@Override
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
	{
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TEOre)
		{
			THREAD.set((TEOre) tile);
		}
		boolean flag = super.canHarvestBlock(world, pos, player);
		THREAD.remove();
		return flag;
	}
	
	@Override
	public int getHarvestLevel(IBlockState state)
	{
		if(THREAD.get() != null)
		{
			return Math.max(state.getValue(ROCK_BASE).material.blockHarvestLevel, THREAD.get().material.blockHarvestLevel);
		}
		return super.getHarvestLevel(state);
	}
	
	@Override
	public String getHarvestTool(IBlockState state)
	{
		return EnumTools.pickaxe.name();
	}
	
	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos)
	{
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile instanceof TEOre)
		{
			return Math.max(((TEOre) tile).material.blockHardness, blockState.getValue(ROCK_BASE).material.blockHardness);
		}
		return blockState.getValue(ROCK_BASE).material.blockHardness;
	}
	
	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion)
	{
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TEOre)
		{
			return Math.max(((TEOre) tile).material.blockExplosionResistance, world.getBlockState(pos).getValue(ROCK_BASE).material.blockExplosionResistance);
		}
		return world.getBlockState(pos).getValue(ROCK_BASE).material.blockExplosionResistance;
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		TEOre ore = THREAD.get();
		if(ore == null)
		{
			TileEntity tile = world.getTileEntity(pos);
			if(tile instanceof TEOre)
			{
				ore = (TEOre) tile;
			}
		}
		if(ore == null) return ImmutableList.of();
		ArrayList<ItemStack> list = new ArrayList();
		list.add(new ItemStack(this.itemList[state.getValue(ROCK_BASE).ordinal()], 1, ore.material.id));
		return list;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
	{
		for(EnumMaterial material : EnumMaterial.getMaterials())
		{
			if(material != null && EnumOrePrefix.ore.access(material))
			{
				list.add(new ItemStack(itemIn, 1, material.id));
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TEOre();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager)
	{
		IBlockState state = world.getBlockState(pos).getValue(ROCK_BASE).base;
		int i = 4;
		
		for (int j = 0; j < 4; ++j)
		{
			for (int k = 0; k < 4; ++k)
			{
				for (int l = 0; l < 4; ++l)
				{
					double d0 = pos.getX() + (j + 0.5D) / 4.0D;
					double d1 = pos.getY() + (k + 0.5D) / 4.0D;
					double d2 = pos.getZ() + (l + 0.5D) / 4.0D;
					manager.addEffect((new ParticleDiggingExt(world, d0, d1, d2, d0 - pos.getX() - 0.5D, d1 - pos.getY() - 0.5D, d2 - pos.getZ() - 0.5D, state)).setBlockPos(pos));
				}
			}
		}
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager)
	{
		state = worldObj.getBlockState(target.getBlockPos()).getValue(ROCK_BASE).base;
		BlockPos pos = target.getBlockPos();
		EnumFacing side = target.sideHit;
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();
		float f = 0.1F;
		AxisAlignedBB axisalignedbb = state.getBoundingBox(worldObj, pos);
		double d0 = i + RANDOM.nextDouble() * (axisalignedbb.maxX - axisalignedbb.minX - 0.2) + 0.1 + axisalignedbb.minX;
		double d1 = j + RANDOM.nextDouble() * (axisalignedbb.maxY - axisalignedbb.minY - 0.2) + 0.1 + axisalignedbb.minY;
		double d2 = k + RANDOM.nextDouble() * (axisalignedbb.maxZ - axisalignedbb.minZ - 0.2) + 0.1 + axisalignedbb.minZ;
		
		switch (side)
		{
		case DOWN :
			d1 = j + axisalignedbb.minY - 0.1;
			break;
		case UP :
			d1 = j + axisalignedbb.maxY + 0.1;
			break;
		case NORTH :
			d2 = k + axisalignedbb.minZ - 0.1;
			break;
		case SOUTH :
			d2 = k + axisalignedbb.maxZ + 0.1;
			break;
		case WEST :
			d0 = i + axisalignedbb.minX - 0.1;
			break;
		case EAST :
			d0 = i + axisalignedbb.maxX + 0.1;
			break;
		default:
			break;
		}
		manager.addEffect((new ParticleDiggingExt(worldObj, d0, d1, d2, 0.0D, 0.0D, 0.0D, state)).setBlockPos(pos).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
		return true;
	}
}