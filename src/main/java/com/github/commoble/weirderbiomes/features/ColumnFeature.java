package com.github.commoble.weirderbiomes.features;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class ColumnFeature extends Feature<BlockStateProvidingFeatureConfig>
{

	public ColumnFeature(Codec<BlockStateProvidingFeatureConfig> codec)
	{
		super(codec);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos pos, BlockStateProvidingFeatureConfig config)
	{
		int posY = pos.getY();
		if (posY < 5 || posY > (chunkGenerator.getMaxY() - 10))
		{
			return false;
		}
		else
		{
			for (int i=0; i<5; i++)
			{
				this.addPileBlock(world, pos.up(i), rand, config);
			}
			return true;
		}
	}

	private void addPileBlock(IWorld world, BlockPos pos, Random rand, BlockStateProvidingFeatureConfig config)
	{
		BlockState state = world.getBlockState(pos);
		if (state.isAir(world, pos) || state.getMaterial() == Material.WATER)
		{
			world.setBlockState(pos, config.stateProvider.getBlockState(rand, pos), 4);
		}

	}
	
//	private boolean canPlaceOn(IWorld world, BlockPos pos, Random rand)
//	{
//		BlockPos belowPos = pos.down();
//		BlockState belowState = world.getBlockState(belowPos);
//		return belowState.isSideSolidFullSquare(world, belowPos, Direction.UP);
//	}

}
