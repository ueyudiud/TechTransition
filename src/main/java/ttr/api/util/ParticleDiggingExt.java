/*
 * copyright© 2016-2016 ueyudiud
 */
package ttr.api.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.world.World;

/**
 * Created at 2016年12月21日 上午8:26:31
 * @author ueyudiud
 */
public class ParticleDiggingExt extends ParticleDigging
{
	public ParticleDiggingExt(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
			double ySpeedIn, double zSpeedIn, IBlockState state)
	{
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, state);
	}
}