package com.github.commoble.weirderbiomes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

	public WeirderBiomes()
	{
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		
		modBus.addListener(WeirderBiomes::onCommonSetup);
	}
	
	private static void onCommonSetup(FMLCommonSetupEvent event)
	{

	}
}
