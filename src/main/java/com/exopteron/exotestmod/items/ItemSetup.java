package com.exopteron.exotestmod.items;

import com.exopteron.exotestmod.TestMod;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.fmllegacy.RegistryObject;

public class ItemSetup {
    public static RegistryObject<ItemMagicWand> MAGIC_WAND = null;
    public static RegistryObject<ItemEnergyCrystal> ENERGY_CRYSTAL = null;
    public ItemSetup(TestMod main) {
        new Magicium(main);
        Properties magicWand = new Item.Properties();
        magicWand.durability(150);
        magicWand.tab(TestMod.CREATIVE_TAB);
        MAGIC_WAND = TestMod.ITEMS.register("magic_wand", () -> new ItemMagicWand(magicWand));

        Properties energyCrystal = new Item.Properties();
        energyCrystal.stacksTo(1);
        energyCrystal.tab(TestMod.CREATIVE_TAB);
        ENERGY_CRYSTAL = TestMod.ITEMS.register("energy_crystal", () -> new ItemEnergyCrystal(energyCrystal));
    }
}
