package com.github.commoble.weirderbiomes;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistrar
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WeirderBiomes.MODID);
	
	public static final RegistryObject<BlockItem> STONE_VILLAGER_HEAD = registerBlockItem(Names.STONE_VILLAGER_HEAD, () -> BlockRegistrar.STONE_VILLAGER_HEAD);
	public static final RegistryObject<BlockItem> STONE_OBSERVER_HEAD = registerBlockItem(Names.STONE_OBSERVER_HEAD, () -> BlockRegistrar.STONE_OBSERVER_HEAD);
	
	public static RegistryObject<BlockItem> registerBlockItem(String name, Supplier<? extends Block> blockGetter)
	{
		return ITEMS.register(name, () -> new BlockItem(blockGetter.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
	}
}
