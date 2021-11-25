package com.exopteron.exotestmod.entities;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Bus.MOD)
public class RendererSetup {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void eventListen(final RegistryEvent.Register<EntityType<?>> e) {
        System.out.println("Hello!\n\n\n\n");
        EntityRenderers.register(EntitySetup.iceBall.get(), ThrownItemRenderer::new);
        EntityRenderers.register(EntitySetup.magicBall.get(), ThrownItemRenderer::new);
    }
}
