package com.github.commoble.weirderbiomes;

import net.minecraft.util.ResourceLocation;

public class ResourceLocations
{
	// biomes
	public static final ResourceLocation CALDERA = getModRL(RegistryNames.CALDERA);
		
	public static ResourceLocation getModRL(String name)
	{
		return new ResourceLocation(WeirderBiomesMod.MODID, name);
	}
}
