package ttr.api.util;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.mojang.realmsclient.gui.ChatFormatting;

import ic2.api.item.ICustomDamageItem;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.oredict.OreDictionary;
import ttr.api.TTrAPI;
import ttr.api.data.Capabilities;
import ttr.api.enums.EnumTools;
import ttr.api.inventory.IItemHandlerIO;
import ttr.api.item.ITool;
import ttr.api.item.ItemTool;
import ttr.api.tile.IActivableTile;
import ttr.api.tile.IContainerableTile;
import ttr.api.tile.IToolableTile;

public class Util
{
	public static final DecimalFormat FORMAT_CUTOUT_AP2 = new DecimalFormat("0.00");
	
	public static boolean equal(Object arg1, Object arg2)
	{
		return arg1 == arg2 ? true :
			(arg1 == null ^ arg2 == null) ? false :
				arg1.equals(arg2);
	}
	
	public static <E> List<E> asListExcludeNull(E...objects)
	{
		ImmutableList.Builder<E> builder = ImmutableList.builder();
		for(E element : objects)
		{
			if(element != null)
			{
				builder.add(element);
			}
		}
		return builder.build();
	}
	
	public static <T extends Comparable<T>> boolean switchProp(World world, BlockPos pos, IProperty<T> property, T value, int updateFlag)
	{
		return world.setBlockState(pos, world.getBlockState(pos).withProperty(property, value), updateFlag);
	}
	
	private static final DecimalFormat formatChance = new DecimalFormat("##0.0%");
	
	private static NBTTagCompound setupNBT(ItemStack stack)
	{
		if(!stack.hasTagCompound())
		{
			stack.setTagCompound(new NBTTagCompound());
		}
		return stack.getTagCompound();
	}
	
	public static ItemStack setChance(ItemStack stack, float chance)
	{
		NBTTagCompound nbt = setupNBT(stack);
		nbt.setFloat("chance", chance);
		return stack;
	}
	
	public static void addInformation(ItemStack stack, List<String> infos)
	{
		NBTTagCompound nbt = setupNBT(stack);
		if(nbt.hasKey("chance"))
		{
			infos.add(ChatFormatting.WHITE + "Chance : " + formatChance.format(nbt.getFloat("chance")));
		}
	}
	
	public static boolean onTileActivatedGeneral(EntityPlayer playerIn, EnumHand hand, ItemStack heldItem,
			EnumFacing facing, float hitX, float hitY, float hitZ, TileEntity tile)
	{
		if(tile == null || hand != EnumHand.MAIN_HAND) return false;
		if(!tile.getWorld().isRemote)
		{
			if(heldItem != null && heldItem.getItem() instanceof ITool &&
					tile instanceof IToolableTile)
			{
				ITool tool = (ITool) heldItem.getItem();
				ActionResult<Float> result;
				for(EnumTools toolType : tool.getToolTypes(heldItem))
				{
					if((result = ((IToolableTile) tile).onToolClick(playerIn, toolType, heldItem, facing, hitX, hitY, hitZ)).getType() != EnumActionResult.PASS)
					{
						tool.onToolUse(heldItem, toolType, result.getResult());
						return true;
					}
				}
			}
			if(tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing))
			{
				if(heldItem != null && heldItem.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null))
				{
					IFluidHandler handler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
					IFluidHandler handler2 = heldItem.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
					FluidStack input;
					FluidStack output;
					int amt;
					if((output = handler2.drain(Integer.MAX_VALUE, false)) != null)
					{
						if((amt = handler.fill(output, true)) != 0)
						{
							input = output.copy();
							input.amount = amt;
							handler2.drain(input, true);
							return true;
						}
					}
					else if((output = handler.drain(Integer.MAX_VALUE, false)) != null)
					{
						if((amt = handler2.fill(output, true)) != 0)
						{
							input = output.copy();
							input.amount = amt;
							handler.drain(input, true);
							return true;
						}
					}
				}
			}
			if(tile.hasCapability(Capabilities.ITEM_HANDLER_IO, facing))
			{
				IItemHandlerIO handler = tile.getCapability(Capabilities.ITEM_HANDLER_IO, facing);
				if(heldItem != null && heldItem.hasCapability(Capabilities.ITEM_HANDLER_IO, null))
				{
					IItemHandlerIO handler2 = heldItem.getCapability(Capabilities.ITEM_HANDLER_IO, null);
					if(handler2.canExtractItem() && handler.canInsertItem())
					{
						ItemStack stack = handler2.extractItem(Integer.MAX_VALUE, facing, true);
						if(stack != null)
						{
							int amt = handler.tryInsertItem(stack, facing, false);
							if(amt > 0)
							{
								handler2.extractItem(amt, facing, false);
								return true;
							}
						}
					}
					if(handler2.canInsertItem() && handler.canExtractItem())
					{
						ItemStack stack = handler.extractItem(Integer.MAX_VALUE, facing, true);
						if(stack != null)
						{
							int amt = handler2.tryInsertItem(stack, facing, false);
							if(amt > 0)
							{
								handler.extractItem(amt, facing, false);
								return true;
							}
						}
					}
				}
				else if(heldItem == null)
				{
					if(handler.canExtractItem())
					{
						ItemStack stack = handler.extractItem(Integer.MAX_VALUE, facing, false);
						if(stack != null)
						{
							playerIn.setHeldItem(hand, stack);
							return true;
						}
					}
				}
				else
				{
					if(handler.canExtractItem())
					{
						if(heldItem.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
						{
							IItemHandler handler2 = heldItem.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
							ItemStack stack = handler.extractItem(Integer.MAX_VALUE, facing, true);
							if(stack != null)
							{
								ItemStack stack2 = stack;
								int[] puted = new int[handler2.getSlots()];
								int point = 0;
								for(int i = 0; i < handler2.getSlots(); ++i)
								{
									if(handler2.getStackInSlot(i) == null)
									{
										if(handler2.insertItem(i, stack2, true) == null)
										{
											stack2 = handler.extractItem(stack.stackSize, facing, false);
											handler2.insertItem(i, stack, false);
											return true;
										}
										else
										{
											stack2 = stack;
											puted[point ++] = i + 1;
										}
									}
								}
								for(int i = 0; i < handler2.getSlots(); ++i)
								{
									if(!stack.isItemEqual(handler2.getStackInSlot(i)))
									{
										continue;
									}
									if((stack2 = handler2.insertItem(i, stack2, true)) == null)
									{
										break;
									}
									puted[point ++] = i + 1;
								}
								if(stack2 != null)
								{
									stack = handler.extractItem(stack.stackSize - stack2.stackSize, facing, false);
								}
								stack2 = stack;
								for(int i : puted)
								{
									if(i == 0)
									{
										break;
									}
									stack2 = handler2.insertItem(i, stack2, false);
								}
								return true;
							}
						}
						ItemStack stack = handler.extractItem(heldItem.getMaxStackSize() - heldItem.stackSize, facing, true);
						if(stack != null && stack.isItemEqual(heldItem))
						{
							heldItem.stackSize += stack.stackSize;
							handler.extractItem(stack.stackSize, facing, false);
							return true;
						}
					}
					if(handler.canInsertItem())
					{
						int size = handler.tryInsertItem(heldItem.copy(), facing, false);
						if(size > 0)
						{
							heldItem.stackSize -= size;
							return true;
						}
					}
				}
			}
		}
		if(tile instanceof IActivableTile)
		{
			if(((IActivableTile) tile).onActived(playerIn, hand, heldItem, facing, hitX, hitY, hitZ))
				return true;
		}
		if(tile instanceof IContainerableTile)
		{
			if(!tile.getWorld().isRemote)
			{
				playerIn.openGui(TTrAPI.ID, facing.ordinal(), tile.getWorld(), tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ());
			}
			return true;
		}
		return false;
	}
	
	public static void spawnDropsInWorld(World world, BlockPos pos, List<ItemStack> drops)
	{
		if(world.isRemote || drops == null) return;
		for(ItemStack stack : drops)
		{
			if(stack == null)
			{
				continue;
			}
			float f = 0.7F;
			double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			EntityItem entityitem = new EntityItem(world, pos.getX() + d0, pos.getY() + d1, pos.getZ() + d2, stack.copy());
			entityitem.setPickupDelay(10);
			world.spawnEntityInWorld(entityitem);
		}
	}
	
	public static void spawnDropsInWorld(EntityPlayer player, ItemStack drop)
	{
		if(drop == null || drop.stackSize == 0 || player.worldObj.isRemote) return;
		player.dropItem(drop, false);
	}
	
	public static void spawnDropsInWorldByPlayerOpeningContainer(EntityPlayer player, IInventory inventory)
	{
		if(player.worldObj.isRemote) return;
		for(int i = 0; i < inventory.getSizeInventory(); ++i)
		{
			spawnDropsInWorld(player, inventory.removeStackFromSlot(i));
		}
	}
	
	public static boolean isBlockNearby(World world, BlockPos pos, Block block, boolean ignoreUnloadChunk)
	{
		return isBlockNearby(world, pos, block, -1, ignoreUnloadChunk);
	}
	
	public static boolean isBlockNearby(World world, BlockPos pos, Block block, int meta, boolean ignoreUnloadChunk)
	{
		return isBlock(world, pos.up(), block, meta, ignoreUnloadChunk) ||
				isBlock(world, pos.down(), block, meta, ignoreUnloadChunk) ||
				isBlock(world, pos.east(), block, meta, ignoreUnloadChunk) ||
				isBlock(world, pos.west(), block, meta, ignoreUnloadChunk) ||
				isBlock(world, pos.north(), block, meta, ignoreUnloadChunk) ||
				isBlock(world, pos.south(), block, meta, ignoreUnloadChunk);
	}
	
	public static boolean isBlock(World world, BlockPos pos, Block block, int meta, boolean ignoreUnloadChunk)
	{
		IBlockState state;
		return (!ignoreUnloadChunk || world.isAreaLoaded(pos, 0)) &&
				(state = world.getBlockState(pos)).getBlock() == block &&
				(meta < 0 || state.getBlock().getMetaFromState(state) == meta);
	}
	
	public static boolean isAirNearby(World world, BlockPos pos, boolean ignoreUnloadChunk)
	{
		return (!ignoreUnloadChunk || world.isAreaLoaded(pos, 1)) && (
				world.isAirBlock(pos.up())  ||
				world.isAirBlock(pos.down()) ||
				world.isAirBlock(pos.west()) ||
				world.isAirBlock(pos.east()) ||
				world.isAirBlock(pos.north())||
				world.isAirBlock(pos.south()));
	}
	
	public static boolean isCatchingRain(World world, BlockPos pos)
	{
		return isCatchingRain(world, pos, false);
	}
	
	public static boolean isCatchingRain(World world, BlockPos pos, boolean checkNeayby)
	{
		if(world.isRaining())
			return world.canBlockSeeSky(pos) ||
					(checkNeayby && (
							world.canBlockSeeSky(pos.north()) ||
							world.canBlockSeeSky(pos.south()) ||
							world.canBlockSeeSky(pos.east()) ||
							world.canBlockSeeSky(pos.west())));
		return false;
	}
	
	public static ImmutableList<ItemStack> sizeOf(List<ItemStack> stacks, int size)
	{
		if(stacks == null || stacks.isEmpty()) return ImmutableList.of();
		if(size == 0) size = 1;
		ImmutableList.Builder builder = ImmutableList.builder();
		for(ItemStack stack : stacks)
			if(stack != null)
			{
				ItemStack stack2 = stack.copy();
				stack2.stackSize = size;
				if(stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE &&
						stack2.getHasSubtypes())
				{
					stack2.setItemDamage(0);
				}
				builder.add(stack2);
			}
		return builder.build();
	}
	
	private static final EnumFacing[][] rotateFix = {
			{EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST},
			{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.EAST, EnumFacing.WEST},
			{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH}};
	
	public static EnumFacing fixSide(EnumFacing side, float hitX, float hitY, float hitZ)
	{
		float u, v;
		switch (side)
		{
		case UP :
		case DOWN :
			u = hitX;
			v = hitZ;
			break;
		case NORTH :
		case SOUTH :
			u = hitX;
			v = hitY;
			break;
		case EAST :
		case WEST :
			u = hitZ;
			v = hitY;
			break;
		default:
			u = 0.5F;
			v = 0.5F;
			break;
		}
		int id;
		boolean b1 = u >= 0.25F, b2 = v >= 0.25F, b3 = u <= 0.75F, b4 = v <= 0.75F;
		return b1 && b2 && b3 && b4 ?
				side : (id = (b1 && b3 ? (!b4 ? 1 : 0) :
					(b2 && b4) ? (!b3 ? 3 : 2) : -1)) == -1 ?
							side.getOpposite() : rotateFix[side.ordinal() / 2][id];
	}
	
	public static @Nullable FluidStack copyStack(@Nullable FluidStack stack)
	{
		return stack == null ? null : stack.copy();
	}
	
	public static @Nullable ItemStack getFromOreDict(String name)
	{
		List<ItemStack> list = OreDictionary.getOres(name, false);
		return list.isEmpty() ? null : removeGeneralUseFlag(list.get(0));
	}
	
	/**
	 * INFO: this method will modify source stack.
	 * @param stack
	 */
	public static ItemStack removeGeneralUseFlag(ItemStack stack)
	{
		if(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
		{
			stack.setItemDamage(0);
		}
		return stack;
	}
	
	public static ItemStack validDisplayStack(ItemStack stack, int size)
	{
		stack = copyAmount(stack, size);
		return removeGeneralUseFlag(stack);
	}
	
	public static ItemStack copyAmount(ItemStack stack, int size)
	{
		if(size == 0) return null;
		stack = stack.copy();
		stack.stackSize = size;
		return stack;
	}
	
	public static FluidStack copyAmount(FluidStack stack, int amount)
	{
		if(amount == 0) return null;
		stack = stack.copy();
		stack.amount = amount;
		return stack;
	}
	
	public static boolean isSimulating()
	{
		return FMLCommonHandler.instance().getEffectiveSide().isServer();
	}
	
	public static boolean damageOrDischargeItem(ItemStack stack, long discharge, long minDamage, EntityLivingBase src)
	{
		if(stack.getItem() instanceof ItemTool)
		{
			return ((ItemTool) stack.getItem()).dischargeOrDamageItem(stack, discharge, minDamage, src);
		}
		else if(stack.getItem() instanceof ICustomDamageItem)
		{
			return ((ICustomDamageItem) stack.getItem()).applyCustomDamage(stack, (int) minDamage, src);
		}
		else
		{
			stack.damageItem((int) minDamage, src);
			return true;
		}
	}
	
	public static FluidTankInfo[] toTankInfos(IFluidTankProperties[] properties)
	{
		FluidTankInfo[] infos = new FluidTankInfo[properties.length];
		for(int i = 0; i < infos.length; ++i)
		{
			infos[i] = new FluidTankInfo(properties[i].getContents(), properties[i].getCapacity());
		}
		return infos;
	}
	
	public static int[] subIntList(int start, int len)
	{
		int[] ret = new int[start];
		for(int i = 0; i < len; ++i)
		{
			ret[i] = start + i;
		}
		return ret;
	}
	
	private static Fluid steam;
	private static Fluid ic2distilled_water;
	
	public static FluidStack getSteam(int amount)
	{
		if(steam == null)
		{
			steam = FluidRegistry.getFluid("steam");
		}
		return new FluidStack(steam, amount);
	}
	
	public static FluidStack getDistilledWater(int amount)
	{
		if(ic2distilled_water == null)
		{
			ic2distilled_water = FluidRegistry.getFluid("ic2distilled_water");
		}
		return new FluidStack(ic2distilled_water, amount);
	}
	
	public static boolean isDistilledWater(FluidStack stack)
	{
		return stack != null && "ic2distilled_water".equals(stack.getFluid().getName());
	}
	
	public static boolean isSteam(FluidStack stack)
	{
		return stack != null && "steam".equals(stack.getFluid().getName());
	}
}