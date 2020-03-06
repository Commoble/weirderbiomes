package com.github.commoble.weirderbiomes;

import java.util.function.Supplier;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Registrar<RegistryType extends IForgeRegistryEntry<RegistryType>>
{
	private final DeferredRegister<RegistryType> deferredRegister;
	
	public Registrar(IForgeRegistry<RegistryType> registry)
	{
		this.deferredRegister = new DeferredRegister<RegistryType>(registry, WeirderBiomes.MODID);
		this.deferredRegister.register(FMLJavaModLoadingContext.get().getModEventBus());
		// init registry objects in subclass constructor
	}
	
	public <EntryType extends RegistryType> RegistryObject<EntryType> register(String id, Supplier<EntryType> entry)
	{
		return this.deferredRegister.register(id, entry);
	}
}
