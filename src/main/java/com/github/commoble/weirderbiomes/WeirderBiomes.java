package com.github.commoble.weirderbiomes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.commoble.weirderbiomes.biomes.BiomeRegistrar;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WeirderBiomes.MODID)
public class WeirderBiomes
{
	public static final String MODID = "weirderbiomes";
	public static final Logger LOGGER = LogManager.getLogger();
	
	public static BiomeRegistrar BIOMES;

	public WeirderBiomes()
	{
		IEventBus MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus FORGE_BUS = MinecraftForge.EVENT_BUS;
		
		BIOMES = new BiomeRegistrar();
		
		MOD_BUS.addListener(WeirderBiomes::onCommonSetup);
	}
	
	private static void onCommonSetup(FMLCommonSetupEvent event)
	{
		BiomeDictionary.addTypes(ForgeRegistries.BIOMES.getValue(ResourceLocations.CALDERA), BiomeDictionary.Type.HOT, BiomeDictionary.Type.DRY, BiomeDictionary.Type.CONIFEROUS);
		BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(WeirderBiomes.BIOMES.CALDERA.get(), 400));
	}
}
