package com.github.commoble.weirderbiomes.enumextensions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;

public class FillerBlockTypes
{
	
	public static final FillerBlockType SURFACE = FillerBlockType.create("SURFACE", "surface", FillerBlockTypes::isSurfaceBlock);
	public static final FillerBlockType GRASS = FillerBlockType.create("GRASS", "grass", state -> state.getBlock() == Blocks.GRASS);
	public static final FillerBlockType DIRT = FillerBlockType.create("DIRT", "dirt", state -> state.getBlock() == Blocks.DIRT);
	
	public static boolean isSurfaceBlock(BlockState state)
	{
		Block block = state.getBlock();
		
		return block == Blocks.DIRT || block == Blocks.GRASS;
	}
}
