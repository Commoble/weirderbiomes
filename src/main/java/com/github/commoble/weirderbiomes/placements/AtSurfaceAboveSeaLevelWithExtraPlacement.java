package com.github.commoble.weirderbiomes.placements;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.placement.Placement;

public class AtSurfaceAboveSeaLevelWithExtraPlacement extends Placement<AtSurfaceAboveSeaLevelWithExtraPlacementConfig>
{
	public AtSurfaceAboveSeaLevelWithExtraPlacement(Function<Dynamic<?>, ? extends AtSurfaceAboveSeaLevelWithExtraPlacementConfig> config)
	{
		super(config);
	}

	@Override
	public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> generatorIn, Random random, AtSurfaceAboveSeaLevelWithExtraPlacementConfig config, BlockPos pos)
	{
		int i = config.count;
		if (random.nextFloat() < config.extraChance)
		{
			i += config.extraCount;
		}

		return IntStream.range(0, i).mapToObj((p_227444_3_) -> {
			int j = random.nextInt(16) + pos.getX();
			int k = random.nextInt(16) + pos.getZ();
			int l = world.getHeight(Heightmap.Type.MOTION_BLOCKING, j, k);
			return new BlockPos(j, l, k);
		})
			.filter(placePos -> placePos.getY() >= world.getSeaLevel() + config.minHeightAboveSeaLevel);
	}
}