package com.exopteron.exotestmod.gui;

import com.exopteron.exotestmod.TestMod;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
@Mod.EventBusSubscriber(modid = TestMod.MODID, value = Dist.CLIENT, bus = Bus.MOD)
public class GuiSetup {
    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event) {
        OverlayRegistry.registerOverlayTop("magic_wand", new GuiMagicWand());
    }
}
