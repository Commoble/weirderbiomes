package com.github.commoble.weirderbiomes;

import com.github.commoble.weirderbiomes.features.ColumnFeature;
import com.github.commoble.weirderbiomes.features.PileFeature;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class FeatureRegistrar
{
	public static final Feature<BlockStateProvidingFeatureConfig> COLUMN = register("column", new ColumnFeature(BlockStateProvidingFeatureConfig.CODEC));
	public static final Feature<BlockStateProvidingFeatureConfig> PILE = register("pile", new PileFeature(BlockStateProvidingFeatureConfig.CODEC));	
	
	private static <C extends IFeatureConfig, F extends Feature<C>> F register(String name, F feature)
	{
		return Registry.register(Registry.FEATURE, name, feature);
	}
}
