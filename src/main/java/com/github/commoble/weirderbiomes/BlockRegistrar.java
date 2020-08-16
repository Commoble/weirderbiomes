package com.github.commoble.weirderbiomes;

import com.github.commoble.weirderbiomes.blocks.FaceBlock;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistrar
{
	// static-initing blocks for now due to lack of biome apis
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WeirderBiomes.MODID);
	
	public static final FaceBlock STONE_VILLAGER_HEAD = registerFaceBlock(Names.STONE_VILLAGER_HEAD);
	
	public static FaceBlock registerFaceBlock(String name)
	{
		return registerBlock(name, new FaceBlock(Properties.from(Blocks.STONE)));
	}
	
	public static <BLOCK extends Block> BLOCK registerBlock(String name, BLOCK block)
	{
		BLOCKS.register(name, () -> block);
		
		return block;
	}
}
