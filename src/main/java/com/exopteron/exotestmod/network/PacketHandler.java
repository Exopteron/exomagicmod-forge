package com.exopteron.exotestmod.network;

import java.util.Optional;

import com.exopteron.exotestmod.TestMod;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public class PacketHandler {
        private static int packetID = 0;
        private static final String PROTOCOL_VERSION = "1.1";
        public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
                        new ResourceLocation(TestMod.MODID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals,
                        PROTOCOL_VERSION::equals);

        public PacketHandler(TestMod main) {
                INSTANCE.<PacketLightningSpell>registerMessage(packetID++, PacketLightningSpell.class,
                                PacketLightningSpell::encode, PacketLightningSpell::decode,
                                PacketLightningSpell::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));

                INSTANCE.<PacketChangeSpell>registerMessage(packetID++, PacketChangeSpell.class,
                                PacketChangeSpell::encode, PacketChangeSpell::decode, PacketChangeSpell::handle,
                                Optional.of(NetworkDirection.PLAY_TO_SERVER));

                INSTANCE.<PacketSpellRegistry>registerMessage(packetID++, PacketSpellRegistry.class,
                                PacketSpellRegistry::encode, PacketSpellRegistry::decode, PacketSpellRegistry::handle,
                                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        }

}
