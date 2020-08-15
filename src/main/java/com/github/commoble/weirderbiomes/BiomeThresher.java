package com.github.commoble.weirderbiomes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntConsumer;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.layer.BiomeLayer;
import net.minecraft.world.gen.layer.LayerUtil;

public class BiomeThresher
{
	public static final int LAST_VANILLA_ID = 173;
	
	// biome layer extenders, use one as an arg for addBiome
	public static final IntConsumer HOT = i -> BiomeLayer.field_202744_r = addToLayerArray(BiomeLayer.field_202744_r, i);
	public static final IntConsumer WARM = i -> BiomeLayer.field_202745_s = addToLayerArray(BiomeLayer.field_202745_s, i);
	public static final IntConsumer COOL = i -> BiomeLayer.field_202746_t = addToLayerArray(BiomeLayer.field_202746_t, i);
	public static final IntConsumer COLD = i -> BiomeLayer.field_202747_u = addToLayerArray(BiomeLayer.field_202747_u, i);
	
	// registering biomes in mod constructor for now since there's no forge API yet
	public static Biome addBiome(RegistryKey<Biome> key, LayerUtil.Type type, IntConsumer layerExtender, Biome biome)
	{
		int id = addToBiomeRegistry(key, biome);
		addToOverworldBiomeProvider(key);
		addToLayer(id, type, layerExtender);
		return biome;
	}
	
	private static int addToBiomeRegistry(RegistryKey<Biome> key, Biome biome)
	{
		// TODO make more efficient
		int id = LAST_VANILLA_ID+1;
		while (BiomeRegistry.BY_RAW_ID.containsKey(id))
		{
			id++;
		}
		BiomeRegistry.BY_RAW_ID.put(id, key);
		WorldGenRegistries.set(WorldGenRegistries.BIOME, id, key, biome);
		return id;
	}
	
	private static void addToOverworldBiomeProvider(RegistryKey<Biome> key)
	{
		// not threadsafe, fix later + make more efficient
		List<RegistryKey<Biome>> oldList = OverworldBiomeProvider.BIOMES;
		List<RegistryKey<Biome>> newList = new ArrayList<>(oldList.size()+1);
		newList.addAll(oldList);
		newList.add(key);
		OverworldBiomeProvider.BIOMES = newList;
	}
	
	private static void addToLayer(int id, LayerUtil.Type type, IntConsumer layerExtender)
	{
		LayerUtil.field_242937_a.put(id, type.ordinal());
		layerExtender.accept(id);
	}
	
	// thanks to BYG for helping me realize BiomeLayer was a thing as well
	private static int[] addToLayerArray(int[] existingArray, int id)
	{
		int[] newArray = Arrays.copyOf(existingArray, existingArray.length + 1);
		newArray[existingArray.length - 1] = id;
		return newArray;
	}
}
