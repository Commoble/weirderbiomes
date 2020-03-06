package com.github.commoble.weirderbiomes.features;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
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
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config)
	{
		BlockPos.Mutable posInChunk = new BlockPos.Mutable();
		BlockPos.Mutable posInWorld = new BlockPos.Mutable();
		BlockState lava = Blocks.LAVA.getDefaultState();
		BlockState stone = Blocks.STONE.getDefaultState();
		IChunk chunk = world.getChunk(pos);
		int seaStart = world.getSeaLevel() - 1;
		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				int y = seaStart;
				posInChunk.setPos(x, y, z);
				posInWorld.setPos(x + pos.getX(), y, z + pos.getZ());
				if (chunk.getBlockState(posInChunk).getBlock() == Blocks.WATER)
				{
					BlockState newState = this.isNearBiomeEdge(world, posInWorld) ? stone : lava;
					while (posInChunk.getY() > 0 && chunk.getBlockState(posInChunk).getBlock() == Blocks.WATER)
					{
						chunk.setBlockState(posInChunk, newState, false);
						posInChunk.move(Direction.DOWN);
					}
				}
			}
		}

		return true;
	}
	
	public boolean isNearBiomeEdge(IWorld world, BlockPos pos)
	{
		int y = pos.getY();
		int radius = world.getRandom().nextInt(3) + 1;
		int centerX = pos.getX();
		int centerZ = pos.getZ();
		int minX = centerX - radius;
		int maxX = centerX + radius;
		int minZ = centerZ - radius;
		int maxZ = centerZ + radius;
		BlockPos.Mutable searchPos = new BlockPos.Mutable();
		Biome lastBiome = null;
		for (int x = minX; x <= maxX; x++)
		{
			for (int z = minZ; z <= maxZ; z++)
			{
				searchPos.setPos(x,y,z);
				Biome nextBiome = world.getBiome(searchPos);
				if (lastBiome == null)
				{
					lastBiome = nextBiome;
				}
				else if (nextBiome != lastBiome)
				{
					return true;
				}
			}
		}
		return false;
	}
	
}
