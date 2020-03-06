package com.github.commoble.weirderbiomes.surfacebuilders;

import java.util.Random;
import java.util.function.Function;

import com.github.commoble.weirderbiomes.WeirderBiomes;
import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class CalderaSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig>
{
	public CalderaSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> data)
	{
		super(data);
	}

	// from shattered savanna
	@Override
	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid,
		int seaLevel, long seed, SurfaceBuilderConfig config)
	{
		if (noise > 1.75D)
		{
			this.buildSurfaceWithConfig(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed,
				SurfaceBuilder.STONE_STONE_GRAVEL_CONFIG);
		}
		else if (noise > -0.5D)
		{
			this.buildSurfaceWithConfig(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed,
				SurfaceBuilder.CORASE_DIRT_DIRT_GRAVEL_CONFIG);
		}
		else
		{
			this.buildSurfaceWithConfig(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed,
				SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG);
		}
	}

	protected void buildSurfaceWithConfig(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock,
		BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config)
	{
		this.buildActualSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, config.getTop(), config.getUnder(), config.getUnderWaterMaterial(),
			seaLevel);
	}

	// from default surface builder
	protected void buildActualSurface(Random random, IChunk chunk, Biome biome, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid,
		BlockState top, BlockState middle, BlockState bottom, int sealevel)
	{
		BlockState topState = top;
		BlockState middleState = middle;
		BlockPos.Mutable posInChunk = new BlockPos.Mutable();
		int sandstoneFlag = -1;
		int noiseCount = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
		int localX = x & 15;
		int localZ = z & 15;
		BlockState lava = Blocks.LAVA.getDefaultState();
		BlockState stone = Blocks.STONE.getDefaultState();

		for (int y = startHeight; y >= 0; --y)
		{
			posInChunk.setPos(localX, y, localZ);
			BlockState stateFromMutaPos = chunk.getBlockState(posInChunk);
			if (noise > -0.5D && y == (sealevel-1) && stateFromMutaPos.getBlock() == Blocks.WATER)
			{

				BlockState newState = this.isNearBiomeEdge(chunk, posInChunk, random, noise) ? stone : lava;
				while (posInChunk.getY() > 0 && chunk.getBlockState(posInChunk).getBlock() == Blocks.WATER)
				{
					chunk.setBlockState(posInChunk, newState, false);
					posInChunk.move(Direction.DOWN);
				}
				
				break;
			}
			if (stateFromMutaPos.isAir())
			{
				sandstoneFlag = -1;
			}
			else if (stateFromMutaPos.getBlock() == defaultBlock.getBlock())
			{
				if (sandstoneFlag == -1)
				{
					if (noiseCount <= 0)
					{
						topState = Blocks.AIR.getDefaultState();
						middleState = defaultBlock;
					}
					else if (y >= sealevel - 4 && y <= sealevel + 1)
					{
						topState = top;
						middleState = middle;
					}

					if (y < sealevel && (topState == null || topState.isAir()))
					{
						if (biome.getTemperature(posInChunk.setPos(x, y, z)) < 0.15F)
						{
							topState = Blocks.ICE.getDefaultState();
						}
						else
						{
							topState = defaultFluid;
						}

						posInChunk.setPos(localX, y, localZ);
					}

					sandstoneFlag = noiseCount;
					if (y >= sealevel - 1)
					{
						chunk.setBlockState(posInChunk, topState, false);
					}
					else if (y < sealevel - 7 - noiseCount)
					{
						topState = Blocks.AIR.getDefaultState();
						middleState = defaultBlock;
						chunk.setBlockState(posInChunk, bottom, false);
					}
					else
					{
						chunk.setBlockState(posInChunk, middleState, false);
					}
				}
				else if (sandstoneFlag > 0)
				{
					--sandstoneFlag;
					chunk.setBlockState(posInChunk, middleState, false);
					if (sandstoneFlag == 0 && middleState.getBlock() == Blocks.SAND && noiseCount > 1)
					{
						sandstoneFlag = random.nextInt(4) + Math.max(0, y - 63);
						middleState = middleState.getBlock() == Blocks.RED_SAND ? Blocks.RED_SANDSTONE.getDefaultState() : Blocks.SANDSTONE.getDefaultState();
					}
				}
			}
		}

	}
	
	public boolean isNearBiomeEdge(IChunk chunk, BlockPos pos, Random rand, double noise)
	{
		if (noise < 0.0D)
		{
			return true;
		}
		int y = pos.getY();
		int radius = 1;
		int centerX = pos.getX();
		int centerZ = pos.getZ();
		int minX = Math.max(0, centerX - radius);
		int maxX = Math.min(centerX + radius, 15);
		int minZ = Math.max(0, centerZ - radius);
		int maxZ = Math.min(centerZ + radius, 15);
		for (int x = minX; x <= maxX; x++)
		{
			for (int z = minZ; z <= maxZ; z++)
			{
				if (chunk.getBiomes().getNoiseBiome(x, y, z) != WeirderBiomes.BIOMES.CALDERA.get())
				{
					return true;
				}
			}
		}
		return false;
	}
}
