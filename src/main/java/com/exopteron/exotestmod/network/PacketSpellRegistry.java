package com.exopteron.exotestmod.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

import com.exopteron.exotestmod.TestMod;
import com.exopteron.exotestmod.items.ItemMagicWand;
import com.exopteron.exotestmod.items.wand.IWandSpell;
import com.exopteron.exotestmod.items.wand.MagicWandSpells;
import com.exopteron.exotestmod.items.wand.MagicWandSpells.SpellRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class PacketSpellRegistry {
    public ArrayList<String> spellList;
    public PacketSpellRegistry(ArrayList<String> spellList) {
        this.spellList = spellList;
    }
    public static PacketSpellRegistry fromSpellRegistry() {
        return new PacketSpellRegistry(new ArrayList<String>(SpellRegistry.INSTANCE.spellRegistry.keySet()));
    }
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            SpellRegistry.INSTANCE.clientSpellRegistry = new HashMap<String, IWandSpell>();
            for (String spell : this.spellList) {
                SpellRegistry.INSTANCE.clientSpellRegistry.put(spell, null);
            }
            TestMod.LOGGER.info("[Exo's Magic Mod] Recieved " + SpellRegistry.INSTANCE.clientSpellRegistry.size() + " spells from server");
        });
        ctx.get().setPacketHandled(true);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeVarInt(this.spellList.size());
        for (String spell : this.spellList) {
            buf.writeUtf(spell);
        }
    }

    public static PacketSpellRegistry decode(FriendlyByteBuf buf) {
        ArrayList<String> spells = new ArrayList<String>();
        int spellCount = buf.readVarInt();
        int i;
        for (i = 0; i < spellCount; i++) {
            spells.add(buf.readUtf());
        }
        return new PacketSpellRegistry(spells);
    }
}
