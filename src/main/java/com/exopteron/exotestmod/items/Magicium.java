package com.exopteron.exotestmod.items;

import com.exopteron.exotestmod.TestMod;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fmllegacy.RegistryObject;

public class Magicium {
    public static final RegistryObject<Item> MAGICIUM_GEM = TestMod.ITEMS.register("magicium_gem", () -> {
        Item.Properties properties = new Item.Properties();
        properties.stacksTo(64);
        properties.tab(TestMod.CREATIVE_TAB);
        return new Item(properties);
    });

    public static final RegistryObject<Block> MAGICIUM_BLOCK = TestMod.BLOCKS.register("magicium_block", () -> {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK);
        return new Block(properties);
    });

    public static final RegistryObject<Item> MAGICIUM_BLOCK_ITEM = TestMod.ITEMS.register("magicium_block", () -> {
        Item.Properties properties = new Item.Properties();
        properties.stacksTo(64);
        properties.tab(TestMod.CREATIVE_TAB);
        return new BlockItem(MAGICIUM_BLOCK.get(), properties);
    });


    public static final RegistryObject<Block> MAGICIUM_ORE = TestMod.BLOCKS.register("magicium_ore", () -> {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE);
        return new OreBlock(properties);
    });

    public static final RegistryObject<Item> MAGICIUM_ORE_ITEM = TestMod.ITEMS.register("magicium_ore", () -> {
        Item.Properties properties = new Item.Properties();
        properties.stacksTo(64);
        properties.tab(TestMod.CREATIVE_TAB);
        return new BlockItem(MAGICIUM_ORE.get(), properties);
    });
    public Magicium(TestMod main) {

    }
}
