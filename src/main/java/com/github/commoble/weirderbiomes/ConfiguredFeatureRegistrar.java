package com.github.commoble.weirderbiomes;

import com.github.commoble.weirderbiomes.blocks.FaceBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraft.world.gen.placement.TopSolidWithNoiseConfig;

public class ConfiguredFeatureRegistrar
{
	public static final ConfiguredFeature<?, ?> EXTRA_IRON = register("extra_iron_ore",
		Feature.ORE.configure(
				new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.IRON_ORE.getDefaultState(), 9))
			.decorate(Placement.field_242907_l.configure(new TopSolidRangeConfig(32, 32, 128)))
			.repeat(16));
	
	public static final ConfiguredFeature<?,?> STONE_COLUMN = register("stone_column",
		FeatureRegistrar.COLUMN.configure(new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(BlockRegistrar.STONE_VILLAGER_HEAD.getDefaultState()))))
			.decorate(Features.Placements.TOP_SOLID_HEIGHTMAP)
			.spreadHorizontally()
			.decorate(Placement.field_242901_e.configure(new TopSolidWithNoiseConfig(160, 80.0D, 0.3D)));

	public static final ConfiguredFeature<?, ?> FACE_PILE = register("head_pile",
		FeatureRegistrar.PILE.configure(new BlockStateProvidingFeatureConfig(makeHeadProvider()))
			.decorate(Features.Placements.TOP_SOLID_HEIGHTMAP)
			.spreadHorizontally()
			.decorate(Placement.field_242901_e.configure(new TopSolidWithNoiseConfig(160, 80.0D, 0.3D))));
		

	private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> feature)
	{
		return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(WeirderBiomes.MODID, name), feature);
	}
	
	private static WeightedBlockStateProvider makeHeadProvider()
	{
		WeightedBlockStateProvider provider = new WeightedBlockStateProvider();
		Block[] blocks = {
			BlockRegistrar.STONE_VILLAGER_HEAD
		};
		Direction[] horizontals = {Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
		for (Block block : blocks)
		{
			BlockState state = block.getDefaultState();
			for (Direction dir : horizontals)
			{
				provider = provider.addState(state.with(FaceBlock.FACING, dir), 1);
			}
		}
		
		return provider;
	}
}
