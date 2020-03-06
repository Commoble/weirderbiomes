package com.github.commoble.weirderbiomes.placements;

public class Placements
{
	// have to static init placements as they are used in biomes which are registered before placements
	public static final AtSurfaceAboveSeaLevelWithExtraPlacement AT_SURFACE_ABOVE_SEA_LEVEL_WITH_EXTRA_PLACEMENT = new AtSurfaceAboveSeaLevelWithExtraPlacement(AtSurfaceAboveSeaLevelWithExtraPlacementConfig::deserialize);
	

}
