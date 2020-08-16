package com.github.commoble.weirderbiomes.features;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class PileFeature extends Feature<BlockStateProvidingFeatureConfig>
{
	public PileFeature(Codec<BlockStateProvidingFeatureConfig> codec)
	{
		super(codec);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, BlockStateProvidingFeatureConfig config)
	{
		if (pos.getY() < 5)
		{
			return false;
		}
		else
		{
			int xRadius = 2 + rand.nextInt(2);
			int zRadius = 2 + rand.nextInt(2);

			for (BlockPos mutaPos : BlockPos.getAllInBoxMutable(pos.add(-xRadius, 0, -zRadius), pos.add(xRadius, 1, zRadius)))
			{
				int xOffsetToCenter = pos.getX() - mutaPos.getX();
				int zOffsetToCenter = pos.getZ() - mutaPos.getZ();
				if (xOffsetToCenter * xOffsetToCenter + zOffsetToCenter * zOffsetToCenter <= rand.nextFloat() * 10.0F - rand.nextFloat() * 6.0F)
				{
					this.addPileBlock(world, mutaPos, rand, config);
				}
				else if (rand.nextFloat() < 0.031D)
				{
					this.addPileBlock(world, mutaPos, rand, config);
				}
			}

			return true;
		}
	}

	private void addPileBlock(IWorld world, BlockPos pos, Random rand, BlockStateProvidingFeatureConfig config)
	{
		if (this.canPlaceAt(world, pos, rand))
		{
			world.setBlockState(pos, config.stateProvider.getBlockState(rand, pos), 4);
		}
	}

	private boolean canPlaceAt(IWorld world, BlockPos pos, Random rand)
	{
		BlockPos belowPos = pos.down();
		BlockState belowState = world.getBlockState(belowPos);
		BlockState state = world.getBlockState(pos);
		return state.getMaterial().isReplaceable() && belowState.isSideSolidFullSquare(world, belowPos, Direction.UP);
	}
}
