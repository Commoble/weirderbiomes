package com.github.commoble.weirderbiomes.features;

import net.minecraft.world.gen.feature.NoFeatureConfig;

public class Features
{
	// have to static init Features as they are used in biomes which are registered before features
	public static final WaterIsLavaFeature WATER_IS_LAVA = new WaterIsLavaFeature(NoFeatureConfig::deserialize);

}
