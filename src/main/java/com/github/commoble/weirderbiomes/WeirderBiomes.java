package com.github.commoble.weirderbiomes;

import java.util.function.IntConsumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.layer.LayerUtil.Type;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

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
		
		IntConsumer biomeSetThreshers[] = {BiomeThresher.COLD, BiomeThresher.COOL, BiomeThresher.WARM, BiomeThresher.HOT};
		
		for (int i=0; i<100; i++)
		{
			RegistryKey<Biome> key = RegistryKey.of(Registry.BIOME_KEY,  new ResourceLocation(MODID, String.valueOf(i)));
			BiomeThresher.addBiome(key, Type.DESERT, biomeSetThreshers[i % 4], createTestBiome());
		}
//		BiomeThresher.addBiome(TEST_BIOME_KEY, Type.FOREST, TEST_BIOME);
		
		// subscribe deferred registers
		DeferredRegister<?>[] registers = {
			BlockRegistrar.BLOCKS,
			ItemRegistrar.ITEMS
		};
		
		for (DeferredRegister<?> register : registers)
		{
			register.register(modBus);
		}
		
		modBus.addListener(WeirderBiomes::onCommonSetup);
	}
	
	private static void onCommonSetup(FMLCommonSetupEvent event)
	{

	}
	
	public static final SurfaceBuilderConfig FACE_VALLEY_SURFACE_BUILDER_CONFIG = new SurfaceBuilderConfig(Blocks.WHITE_TERRACOTTA.getDefaultState(), Blocks.WHITE_TERRACOTTA.getDefaultState(), Blocks.GRAVEL.getDefaultState());
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> FACE_VALLEY_SURFACE_BUILDER = registerSurfaceBuilder("valley_of_faces", SurfaceBuilder.DEFAULT.func_242929_a(FACE_VALLEY_SURFACE_BUILDER_CONFIG));
	
	public static <CONFIG extends ISurfaceBuilderConfig> ConfiguredSurfaceBuilder<CONFIG> registerSurfaceBuilder(String name, ConfiguredSurfaceBuilder<CONFIG> builder)
	{
		return WorldGenRegistries.add(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, new ResourceLocation(MODID, name), builder);
	}
	
	public static Biome createTestBiome()
	{
	      BiomeGenerationSettings.Builder genBuilder = (new BiomeGenerationSettings.Builder())
	    	  .surfaceBuilder(FACE_VALLEY_SURFACE_BUILDER);
//	    	  .structureFeature(StructureFeatures.field_244140_f);
	      DefaultBiomeFeatures.addDefaultUndergroundStructures(genBuilder);
	      DefaultBiomeFeatures.addMineables(genBuilder);
	      DefaultBiomeFeatures.addDefaultOres(genBuilder);
	      DefaultBiomeFeatures.addDefaultDisks(genBuilder);
	      DefaultBiomeFeatures.addLandCarvers(genBuilder);
	      DefaultBiomeFeatures.addOceanCarvers(genBuilder);
	      DefaultBiomeFeatures.addDungeons(genBuilder);
	      
	      genBuilder
	      	.feature(Decoration.UNDERGROUND_ORES, ConfiguredFeatureRegistrar.EXTRA_IRON)
//	      	.feature(Decoration.VEGETAL_DECORATION, ConfiguredFeatureRegistrar.STONE_COLUMN)
	      	.feature(Decoration.VEGETAL_DECORATION, ConfiguredFeatureRegistrar.FACE_PILE);

	      MobSpawnInfo.Builder spawnBuilder = new MobSpawnInfo.Builder();
	      DefaultBiomeFeatures.addDesertMobs(spawnBuilder);
	      return (new Biome.Builder())
	    	  .precipitation(Biome.RainType.NONE)
	    	  .category(Biome.Category.DESERT)
	    	  .depth(-0.5F).scale(2F).temperature(0.5F).downfall(0.1F)
	    	  .effects(
	    		  (new BiomeAmbience.Builder())
//	    		  .waterColor(4159204)
	    		  .waterColor(packRGB(0x5F, 0x6B, 0xE4))
	    		  .waterFogColor(329011)
	    		  .fogColor(10518688)
//	    		  .fogColor(packRGB(112, 96, 153))
	    		  .skyColor(heatToSkyColor(0.2F))
//	    		  .skyColor(packRGB(112, 96, 153))
	    		  .moodSound(MoodSoundAmbience.CAVE).build())
	    	  
	    	  .spawnSettings(spawnBuilder.build())
	    	  .generationSettings(genBuilder.build()).build();
	  
	}
	
	// from BiomeMaker -- could just AT this but hard to figure out what the srg names are for new stuff in 1.16.2
	// when using yarn-over-mcp
	public static int heatToSkyColor(float heat)
	{
	      float clampedHeat = MathHelper.clamp(heat / 3F, -1.0F, 1.0F);
	      return MathHelper.hsvToRGB(0.62222224F - clampedHeat * 0.05F, 0.5F + clampedHeat * 0.1F, 1.0F);
	}
	
	public static int packRGB(int red, int green, int blue)
	{
		return (red << 16) + (green << 8) + blue;
	}
}
