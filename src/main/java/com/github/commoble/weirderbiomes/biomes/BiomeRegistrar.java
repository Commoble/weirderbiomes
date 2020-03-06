package com.github.commoble.weirderbiomes.biomes;

import com.github.commoble.weirderbiomes.Registrar;
import com.github.commoble.weirderbiomes.RegistryNames;
import com.github.commoble.weirderbiomes.surfacebuilders.SurfaceBuilders;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeRegistrar extends Registrar<Biome>
{
	public final RegistryObject<CalderaBiome> CALDERA;
	
	public BiomeRegistrar()
	{
		super(ForgeRegistries.BIOMES);
		this.CALDERA = this.register(RegistryNames.CALDERA, () -> new CalderaBiome(new Biome.Builder()
			.surfaceBuilder(SurfaceBuilders.CALDERA, SurfaceBuilder.DIRT_DIRT_GRAVEL_CONFIG)
			.category(Category.EXTREME_HILLS)
			.precipitation(Biome.RainType.RAIN)
			.downfall(0.05F)
			.depth(-0.08F)
			.scale(0.15F)
			//.scale(0.05F)
			.temperature(1.75F)
			.waterColor(10786514)
			.waterFogColor(4654392)
			));
	}

}
