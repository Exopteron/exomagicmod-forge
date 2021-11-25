package com.exopteron.exotestmod.keybinds;

import com.exopteron.exotestmod.network.PacketChangeSpell;
import com.exopteron.exotestmod.network.PacketHandler;
import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class KeyBinds {
    // TODO translatabletext
    public static final KeyMapping switchSpell = new KeyMapping("Switch spell", 45, "Exo's Test Mod");
    public static boolean isSwitchSpellDown = false;

    public static void setup(FMLClientSetupEvent e) {
        ClientRegistry.registerKeyBinding(switchSpell);
    }

    @SubscribeEvent
    public static void onTick(ClientTickEvent e) {
        Minecraft mc = Minecraft.getInstance();
        if (KeyBinds.switchSpell.isDown()) {
            if (!KeyBinds.isSwitchSpellDown && mc.options.keyShift.isDown()) {
                PacketHandler.INSTANCE.sendToServer(new PacketChangeSpell());
                // KeyBinds.isSwitchSpellDown = true;
            }
            KeyBinds.isSwitchSpellDown = true;
        } else {
            KeyBinds.isSwitchSpellDown = false;
        }
    }
}
