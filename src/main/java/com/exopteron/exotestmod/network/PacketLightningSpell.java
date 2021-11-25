package com.exopteron.exotestmod.network;


import java.util.function.Supplier;

import com.exopteron.exotestmod.items.ItemMagicWand;
import com.exopteron.exotestmod.items.wand.MagicWandSpells;

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

public class PacketLightningSpell {
    public BlockPos pos;
    public PacketLightningSpell(BlockPos pos) {
        this.pos = pos;
    }
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
/*             ServerPlayer sender = ctx.get().getSender();
            ItemStack heldItemStack = sender.getInventory().getItem(sender.getInventory().selected);
            if (heldItemStack.getItem() instanceof ItemMagicWand) {
                Spell spell = MagicWandSpells.spellFromItemStack(heldItemStack);
                if (spell == Spell.LIGHTNING) {
                    Level level = sender.level;
                    if (level.hasChunk(SectionPos.blockToSectionCoord(this.pos.getX()), SectionPos.blockToSectionCoord(this.pos.getZ()))) {
                        if (this.pos.distManhattan(sender.blockPosition()) < 25.0) {
                            ItemMagicWand.damageWand(heldItemStack, 5, sender, sender.getUsedItemHand());
                            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                            lightningBolt.setPos(Vec3.atCenterOf((Vec3i) this.pos));
                            level.addFreshEntity(lightningBolt);
                        }    
                    }
                }
            } */
        });
        ctx.get().setPacketHandled(true);
    }
    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }
    public static PacketLightningSpell decode(FriendlyByteBuf buf) {
        return new PacketLightningSpell(buf.readBlockPos());
    }
}
