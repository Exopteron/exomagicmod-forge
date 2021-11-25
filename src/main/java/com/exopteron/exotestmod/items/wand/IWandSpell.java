package com.exopteron.exotestmod.items.wand;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IWandSpell {
    default int cast(Level level, Player player, InteractionHand hand, ItemStack wand) {
        return 0;
    };

    default void castClient(Level level, Player player, InteractionHand hand) {

    };

    default void rebound(Level level, Player player, InteractionHand hand) {
        
    };
    default int getSpellDurabilityCost() {
        return 1;
    };
}
