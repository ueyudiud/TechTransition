package ttr.api.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import ttr.api.enums.EnumMaterial;
import ttr.api.enums.EnumOrePrefix;
import ttr.api.recipe.IOreDictRegister;
import ttr.api.recipe.MaterialInstance;
import ttr.api.util.LanguageManager;

public class ItemMulti extends Item
{
	protected final EnumOrePrefix prefix;
	protected List<IOreDictRegister> registers = new ArrayList();
	
	public ItemMulti(EnumOrePrefix prefix)
	{
		this.prefix = prefix;
		this.hasSubtypes = true;
		setCreativeTab(CreativeTabs.MATERIALS);
		setRegistryName(prefix.name);
		setUnlocalizedName(prefix.name);
		GameRegistry.register(this);
	}
	
	public final void registerOreDictRegister(IOreDictRegister register)
	{
		if(this.registers == null)
			throw new RuntimeException("Please register listener before item is post-initalized.");
		this.registers.add(register);
	}
	
	public void postinitalize()
	{
		for(EnumMaterial material : EnumMaterial.getMaterials())
		{
			if(material != null && access(material))
			{
				ItemStack stack = MaterialInstance.findItemStack(this.prefix, material);
				if(stack == null)
				{
					stack = new ItemStack(this, 1, material.id);
					LanguageManager.registerLocal(getTranslateName(stack), this.prefix.getTranslatedName(material));
				}
				OreDictionary.registerOre(this.prefix.oreDictName + material.oreDictName, stack);
				for(IOreDictRegister register : this.registers)
				{
					register.registerOreRecipes(stack, this.prefix, material);
				}
			}
		}
	}
	
	public final EnumOrePrefix getPrefix()
	{
		return this.prefix;
	}
	
	protected boolean access(EnumMaterial material)
	{
		return this.prefix.access(material);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return getUnlocalizedName() + "@" + getDamage(stack);
	}
	
	protected String getTranslateName(ItemStack stack)
	{
		return getUnlocalizedName(stack) + ".name";
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return LanguageManager.translateToLocal(getTranslateName(stack));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
	{
		for(EnumMaterial material : EnumMaterial.getMaterials())
		{
			if(material != null && !MaterialInstance.hasItemStack(this.prefix, material) && access(material))
			{
				subItems.add(new ItemStack(itemIn, 1, material.id));
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		super.addInformation(stack, playerIn, tooltip, advanced);
		EnumMaterial material = EnumMaterial.getMaterial(getDamage(stack));
		if(material.chemicalFormula != null)
		{
			tooltip.add(material.chemicalFormula);
		}
	}
}