package com.exopteron.exotestmod.server;

import com.exopteron.exotestmod.network.PacketHandler;
import com.exopteron.exotestmod.network.PacketSpellRegistry;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

@Mod.EventBusSubscriber()
public class EventListener {
    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinWorldEvent e) {
        if (e.getEntity() instanceof ServerPlayer) {
            PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) e.getEntity()), PacketSpellRegistry.fromSpellRegistry());
        }
    }
}
