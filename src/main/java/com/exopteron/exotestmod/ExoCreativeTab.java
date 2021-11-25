package com.exopteron.exotestmod;

import com.exopteron.exotestmod.items.ItemMagicWand;
import com.exopteron.exotestmod.items.ItemSetup;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ExoCreativeTab extends CreativeModeTab {

    public ExoCreativeTab(String label) {
        super(label);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemSetup.MAGIC_WAND.get());
    }
    
}
