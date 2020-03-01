package com.github.commoble.weirderbiomes;

import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.commoble.weirderbiomes.biomes.CalderaBiome;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WeirderBiomesMod.MODID)
public class WeirderBiomesMod
{
	public static final String MODID = "weirderbiomes";
	public static final Logger LOGGER = LogManager.getLogger();

	public WeirderBiomesMod()
	{
		IEventBus MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus FORGE_BUS = MinecraftForge.EVENT_BUS;
		
//		MOD_BUS.addGenericListener(SurfaceBuilder.class, getRegistryEventHandler(WeirderBiomesMod::registerSurfaceBuilders));
		MOD_BUS.addGenericListener(Biome.class, getRegistryEventHandler(WeirderBiomesMod::registerBiomes));
		
		MOD_BUS.addListener(WeirderBiomesMod::onCommonSetup);
	}
	
	private static <T extends IForgeRegistryEntry<T>> Consumer<RegistryEvent.Register<T>> getRegistryEventHandler(Consumer<IForgeRegistry<T>> handler)
	{
		return event -> handler.accept(event.getRegistry());
	}

	public static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, ResourceLocation registryKey, T entry)
	{
		entry.setRegistryName(registryKey);
		registry.register(entry);
		return entry;
	}
	
//	private static void registerSurfaceBuilders(IForgeRegistry<SurfaceBuilder<?>> registry)
//	{
//		register(registry, ResourceLocations.CALDERA, new CalderaSurfaceBuilder(SurfaceBuilderConfig::deserialize));
//	}
	
	private static void onCommonSetup(FMLCommonSetupEvent event)
	{
		BiomeDictionary.addTypes(ForgeRegistries.BIOMES.getValue(ResourceLocations.CALDERA), BiomeDictionary.Type.HOT, BiomeDictionary.Type.DRY, BiomeDictionary.Type.CONIFEROUS);
	}
	
	private static void registerBiomes(IForgeRegistry<Biome> registry)
	{
		register(registry, ResourceLocations.CALDERA, new CalderaBiome(new Biome.Builder()
			.surfaceBuilder(SurfaceBuilder.SHATTERED_SAVANNA, SurfaceBuilder.DIRT_DIRT_GRAVEL_CONFIG)
			.category(Category.DESERT)
			.precipitation(Biome.RainType.RAIN)
			.downfall(0.05F)
			.depth(-0.1F)
			.scale(0.15F)
			//.scale(0.05F)
			.temperature(1.75F)
			.waterColor(10786514)
			.waterFogColor(4654392)
			));
	}
}
