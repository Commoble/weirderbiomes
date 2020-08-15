package com.github.commoble.weirderbiomes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.layer.LayerUtil.Type;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WeirderBiomes.MODID)
public class WeirderBiomes
{
	public static final String MODID = "weirderbiomes";
	public static final Logger LOGGER = LogManager.getLogger();
//	public static final String TEST_BIOME_NAME = "test_biome";
//	public static final RegistryKey<Biome> TEST_BIOME_KEY = RegistryKey.of(Registry.BIOME_KEY, new ResourceLocation(MODID, TEST_BIOME_NAME));
	public static final Biome TEST_BIOME = createTestBiome();

	public WeirderBiomes()
	{
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		
		for (int i=0; i<100; i++)
		{
			RegistryKey<Biome> key = RegistryKey.of(Registry.BIOME_KEY,  new ResourceLocation(MODID, String.valueOf(i)));
			BiomeThresher.addBiome(key, Type.DESERT, BiomeThresher.WARM, createTestBiome());
		}
//		BiomeThresher.addBiome(TEST_BIOME_KEY, Type.FOREST, TEST_BIOME);
		
		modBus.addListener(WeirderBiomes::onCommonSetup);
	}
	
	private static void onCommonSetup(FMLCommonSetupEvent event)
	{

	}
	
	public static Biome createTestBiome()
	{
	      BiomeGenerationSettings.Builder genBuilder = (new BiomeGenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.field_244173_e);

	      MobSpawnInfo.Builder spawnBuilder = new MobSpawnInfo.Builder();
	      DefaultBiomeFeatures.addDesertMobs(spawnBuilder);
	      return (new Biome.Builder())
	    	  .precipitation(Biome.RainType.NONE)
	    	  .category(Biome.Category.DESERT)
	    	  .depth(1.5F).scale(500F).temperature(0.5F).downfall(0.5F)
	    	  .effects(
	    		  (new BiomeAmbience.Builder())
	    		  .waterColor(4159204)
	    		  .waterFogColor(329011)
	    		  .fogColor(10518688)
	    		  .skyColor(0)
	    		  .moodSound(MoodSoundAmbience.CAVE).build())
	    	  
	    	  .spawnSettings(spawnBuilder.build())
	    	  .generationSettings(genBuilder.build()).build();
	  
	}
}
