package com.exopteron.exotestmod;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class Utils {
    public static void awardAdvancement(ResourceLocation advancement, ServerPlayer serverPlayer) {
        Advancement adv = serverPlayer.server.getAdvancements()
                .getAdvancement(advancement);
        for (String str : serverPlayer.getAdvancements().getOrStartProgress(adv).getRemainingCriteria()) {
            serverPlayer.getAdvancements().award(adv, str);
        }
    }
}
