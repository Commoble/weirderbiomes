package com.github.commoble.weirderbiomes.surfacebuilders;

import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class SurfaceBuilders
{
	public static final CalderaSurfaceBuilder CALDERA = new CalderaSurfaceBuilder(SurfaceBuilderConfig::deserialize);
}
