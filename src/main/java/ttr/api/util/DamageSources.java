package ttr.api.util;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class DamageSources
{
	public static final DamageSource STEAM_HEAT = new DamageSource("steam.heat")
	{
		@Override
		public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn)
		{
			return new TextComponentTranslation(ChatFormatting.RED + "%s" + ChatFormatting.WHITE + " was boiled alive", entityLivingBaseIn.getDisplayName());
		}
	}.setDifficultyScaled();
}
