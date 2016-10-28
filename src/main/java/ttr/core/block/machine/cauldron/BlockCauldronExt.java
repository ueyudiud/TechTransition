package ttr.core.block.machine.cauldron;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import ttr.api.collection.Stack;
import ttr.api.recipe.TemplateRecipeMap;
import ttr.api.recipe.TemplateRecipeMap.TemplateRecipe;
import ttr.api.stack.AbstractStack;
import ttr.core.block.BlockMachine;
import ttr.core.tile.machine.TECauldron;

public class BlockCauldronExt extends BlockMachine
{
	protected static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D);
	protected static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
	protected static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);
	
	public BlockCauldronExt()
	{
		super(Material.IRON, MapColor.STONE);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this);
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn)
	{
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return FULL_BLOCK_AABB;
	}
	
	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TECauldron)
			return ((TECauldron) tile).getLightValue();
		return super.getLightValue(state, world, pos);
	}

	protected boolean onWashing(World world, BlockPos pos, TECauldron cauldron, EntityItem entityIn, TemplateRecipe recipe, boolean isSolute)
	{
		if(isSolute)
		{
			if(cauldron.getLevel() != 3)
				return false;
			cauldron.setFluidType(recipe.outputsFluid[0].getFluid());
		}
		else
		{
			cauldron.useFluid(true);
		}
		if(!world.isRemote)
		{
			int size = recipe.inputsItem[0].size(entityIn.getEntityItem());
			if(size >= entityIn.getEntityItem().stackSize)
			{
				entityIn.setDead();
			}
			else
			{
				entityIn.getEntityItem().stackSize -= size;
			}
			List<ItemStack> list = new ArrayList();
			for(int i = 0; i < recipe.outputsItem.length; ++i)
			{
				AbstractStack stack = recipe.outputsItem[i];
				int multiply = 0;
				if(recipe.chancesOutputItem != null)
				{
					for(int j = 0; j < recipe.chancesOutputItem[i].length; ++j)
					{
						int val = recipe.chancesOutputItem[i][j];
						if(val == 10000 || world.rand.nextInt(10000) < val)
						{
							multiply++;
						}
					}
				}
				else
				{
					multiply = 1;
				}
				if(multiply > 0)
				{
					ItemStack stack1 = stack.instance();
					stack1.stackSize *= multiply;
					list.add(stack1);
				}
			}
			for(ItemStack stack : list)
			{
				float f = 0.7F;
				EntityItem entityitem = new EntityItem(world, entityIn.posX, entityIn.posY, entityIn.posZ, stack.copy());
				entityitem.motionX = entityIn.motionX + RANDOM.nextFloat() * 0.04F - 0.02F;
				entityitem.motionY = 0.2F + RANDOM.nextFloat() * 0.1F;
				entityitem.motionZ = entityIn.motionZ + RANDOM.nextFloat() * 0.04F - 0.02F;
				entityitem.setPickupDelay(10);
				world.spawnEntityInWorld(entityitem);
			}
		}
		cauldron.washingBuf = 5;
		return true;
	}

	/**
	 * Called When an Entity Collided with the Block
	 */
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if(entityIn instanceof EntityItem && !worldIn.isRemote)
		{
			Stack<Fluid> stack = getFluid(worldIn, pos);
			if(stack != null)
			{
				TECauldron tile = ((TECauldron) worldIn.getTileEntity(pos));
				if(tile.washingBuf > 0)
				{
					tile.washingBuf--;
					return;
				}
				EntityItem item = (EntityItem) entityIn;
				TemplateRecipe recipe = TemplateRecipeMap.CAULDRON_WASHING.findRecipe(worldIn, pos, Integer.MAX_VALUE, new FluidStack[]{new FluidStack(tile.getFluidType(), tile.getLevel() * 334)}, item.getEntityItem());
				if(recipe != null)
				{
					if(onWashing(worldIn, pos, tile, (EntityItem) entityIn, recipe, false))
						return;
				}
				else
				{
					recipe = TemplateRecipeMap.CAULDRON_SOLUTE.findRecipe(worldIn, pos, Integer.MAX_VALUE, new FluidStack[]{new FluidStack(tile.getFluidType(), tile.getLevel() * 334)}, item.getEntityItem());
					if(recipe != null)
					{
						if(onWashing(worldIn, pos, tile, (EntityItem) entityIn, recipe, true))
							return;
					}
				}
			}
		}
		//		if(stack.element == FluidRegistry.WATER)
		//		{
		//			int i = state.getValue(LEVEL).intValue();
		//			float f = pos.getY() + (6.0F + 3 * i) / 16.0F;
		//
		//			if (!worldIn.isRemote && entityIn.isBurning() && i > 0 && entityIn.getEntityBoundingBox().minY <= f)
		//			{
		//				entityIn.extinguish();
		//				setFluid(worldIn, pos, FluidRegistry.WATER, i - 1);
		//			}
		//		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileEntity tile = worldIn.getTileEntity(pos);
		if (!(tile instanceof TECauldron) || heldItem == null)
			return true;
		else
		{
			TECauldron cauldron = (TECauldron) tile;
			Item item = heldItem.getItem();
			if (heldItem.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null))
			{
				IFluidHandler handler = heldItem.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
				FluidStack result;
				if(cauldron.level == 0)
				{
					if((result = handler.drain(1000, false)) != null &&
							!result.getFluid().isGaseous(result))
					{
						if(!worldIn.isRemote)
						{
							int l;
							cauldron.setFluidType(result.getFluid(), l = result.amount * 3 / 1000);
							if(!playerIn.capabilities.isCreativeMode)
							{
								handler.drain(l == 3 ? 1000 : l * 333 + 1, true);
							}
							playerIn.addStat(StatList.CAULDRON_FILLED);
						}
						return true;
					}
				}
				else
				{
					if(cauldron.level != 3)
					{
						if((result = handler.drain(new FluidStack(cauldron.getFluidType(), 1000 - 333 * cauldron.level), false)) != null)
						{
							if(!worldIn.isRemote)
							{
								int l;
								cauldron.setFluidType(result.getFluid(), (l = Math.min(result.amount * 3 / 1000, 3 - cauldron.level)) + cauldron.level);
								if(!playerIn.capabilities.isCreativeMode)
								{
									handler.drain(l * 333 + 1, true);
								}
								playerIn.addStat(StatList.CAULDRON_FILLED);
							}
							return true;
						}
					}
					int amt;
					if((amt = handler.fill(new FluidStack(cauldron.getFluidType(), 333 * cauldron.level + 1), false)) != 0)
					{
						if(!worldIn.isRemote)
						{
							int l = (amt + 1) * 3 / 1000;
							handler.fill(new FluidStack(cauldron.getFluidType(), amt), true);
							cauldron.setFluidType(cauldron.getFluidType(), cauldron.level - l);
							playerIn.addStat(StatList.CAULDRON_USED);
						}
						return true;
					}
				}
			}
			if(cauldron.level > 0 && cauldron.getFluidType() == FluidRegistry.WATER)
			{
				if (item == Items.GLASS_BOTTLE)
				{
					if (!worldIn.isRemote)
					{
						if (!playerIn.capabilities.isCreativeMode)
						{
							ItemStack itemstack1 = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER);
							playerIn.addStat(StatList.CAULDRON_USED);

							if (--heldItem.stackSize == 0)
							{
								playerIn.setHeldItem(hand, itemstack1);
							}
							else if (!playerIn.inventory.addItemStackToInventory(itemstack1))
							{
								playerIn.dropItem(itemstack1, false);
							}
							else if (playerIn instanceof EntityPlayerMP)
							{
								((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
							}
						}
						cauldron.useFluid(true);
					}
					return true;
				}
				else
				{
					if (item instanceof ItemArmor)
					{
						ItemArmor itemarmor = (ItemArmor)item;
						if (itemarmor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER && itemarmor.hasColor(heldItem) && !worldIn.isRemote)
						{
							itemarmor.removeColor(heldItem);
							cauldron.useFluid(true);
							playerIn.addStat(StatList.ARMOR_CLEANED);
							return true;
						}
					}
					if (item instanceof ItemBanner)
					{
						if (TileEntityBanner.getPatterns(heldItem) > 0 && !worldIn.isRemote)
						{
							ItemStack itemstack = heldItem.copy();
							itemstack.stackSize = 1;
							TileEntityBanner.removeBannerData(itemstack);
							playerIn.addStat(StatList.BANNER_CLEANED);
							if (!playerIn.capabilities.isCreativeMode)
							{
								--heldItem.stackSize;
							}
							if (heldItem.stackSize == 0)
							{
								playerIn.setHeldItem(hand, itemstack);
							}
							else if (!playerIn.inventory.addItemStackToInventory(itemstack))
							{
								playerIn.dropItem(itemstack, false);
							}
							else if (playerIn instanceof EntityPlayerMP)
							{
								((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
							}
							if (!playerIn.capabilities.isCreativeMode)
							{
								cauldron.useFluid(true);
							}
						}
						return true;
					}
				}
			}
			return false;
		}
	}

	/**
	 * Called similar to random ticks, but only when it is raining.
	 */
	@Override
	public void fillWithRain(World worldIn, BlockPos pos)
	{
		TileEntity tile = worldIn.getTileEntity(pos);
		if(!(tile instanceof TECauldron)) return;
		Fluid fluid = ((TECauldron) tile).getFluidType();
		if((fluid != null && fluid != FluidRegistry.WATER) || ((TECauldron) tile).level == 3) return;
		if (worldIn.rand.nextInt(20) == 1)
		{
			if (!worldIn.provider.canSnowAt(pos, false))
			{
				((TECauldron) tile).setFluidType(FluidRegistry.WATER, ((TECauldron) tile).level + 1);
			}
		}
	}
	
	@Override
	public boolean hasComparatorInputOverride(IBlockState state)
	{
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
	{
		Stack<Fluid> stack = getFluid(worldIn, pos);
		return stack == null ? 0 : (int) stack.size;
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}

	@Override
	public String getHarvestTool(IBlockState state)
	{
		return "pickaxe";
	}

	@Override
	public int getHarvestLevel(IBlockState state)
	{
		return 0;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TECauldron();
	}

	public void setFluid(World world, BlockPos pos, Fluid fluid, int amt)
	{
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TECauldron)
		{
			((TECauldron) tile).setFluidType(fluid, amt);
		}
	}
	
	public Stack<Fluid> getFluid(IBlockAccess world, BlockPos pos)
	{
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TECauldron && ((TECauldron) tile).getFluidType() != null)
			return new Stack(((TECauldron) tile).getFluidType(), ((TECauldron) tile).level);
		return null;
	}
}