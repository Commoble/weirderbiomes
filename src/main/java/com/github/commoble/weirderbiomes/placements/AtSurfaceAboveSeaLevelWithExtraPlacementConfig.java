package com.github.commoble.weirderbiomes.placements;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.world.gen.placement.IPlacementConfig;

public class AtSurfaceAboveSeaLevelWithExtraPlacementConfig implements IPlacementConfig
{
	public final int minHeightAboveSeaLevel;
	public final int count;
	public final float extraChance;
	public final int extraCount;

	public AtSurfaceAboveSeaLevelWithExtraPlacementConfig(int minHeightAboveSeaLevel, int count, float extraChanceIn, int extraCountIn)
	{
		this.minHeightAboveSeaLevel = minHeightAboveSeaLevel;
		this.count = count;
		this.extraChance = extraChanceIn;
		this.extraCount = extraCountIn;
	}

	@Override
	public <T> Dynamic<T> serialize(DynamicOps<T> ops)
	{
		return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(
			ops.createString("min_height_above_sea_level"),
			ops.createInt(this.minHeightAboveSeaLevel),
			ops.createString("count"),
			ops.createInt(this.count),
			ops.createString("extra_chance"),
			ops.createFloat(this.extraChance),
			ops.createString("extra_count"),
			ops.createInt(this.extraCount))));
	}

	public static AtSurfaceAboveSeaLevelWithExtraPlacementConfig deserialize(Dynamic<?> data)
	{
		int minHeightAboveSeaLevel = data.get("min_height_above_sea_level").asInt(0);
		int count = data.get("count").asInt(0);
		float extraChance = data.get("extra_chance").asFloat(0.0F);
		int extraCount = data.get("extra_count").asInt(0);
		return new AtSurfaceAboveSeaLevelWithExtraPlacementConfig(minHeightAboveSeaLevel, count, extraChance, extraCount);
	}
}