package com.github.commoble.weirderbiomes.features;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class WaterIsLavaFeature extends Feature<NoFeatureConfig>
{

	public WaterIsLavaFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn)
	{
		super(configFactoryIn);
	}

	@Override
	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config)
	{
		BlockPos.Mutable mutablePos = new BlockPos.Mutable();
		BlockState lava = Blocks.LAVA.getDefaultState();
		IChunk chunk = worldIn.getChunk(pos);
		int seaStart = worldIn.getSeaLevel() - 1;
		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				int y = seaStart;
				mutablePos.setPos(x, y, z);
				while (mutablePos.getY() > 0 && chunk.getBlockState(mutablePos).getBlock() == Blocks.WATER)
				{
					chunk.setBlockState(mutablePos, lava, false);
					mutablePos.move(Direction.DOWN);
				}
			}
		}

		return true;
	}
	
}
