package com.exopteron.exotestmod.network;


import java.util.function.Supplier;

import com.exopteron.exotestmod.TestMod;
import com.exopteron.exotestmod.items.ItemMagicWand;
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

public class PacketChangeSpell {
    public PacketChangeSpell() {

    }
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer sender = ctx.get().getSender();
            ItemStack heldItemStack = sender.getInventory().getItem(sender.getInventory().selected);
            if (heldItemStack.getItem() instanceof ItemMagicWand) {
                String spell = MagicWandSpells.spellFromItemStack(heldItemStack);
                spell = MagicWandSpells.rotate(spell);
                SpellRegistry.setItemStack(heldItemStack, spell);
            }
        });
        ctx.get().setPacketHandled(true);
    }
    public void encode(FriendlyByteBuf buf) {

    }
    public static PacketChangeSpell decode(FriendlyByteBuf buf) {
        return new PacketChangeSpell();
    }
}
