package com.exopteron.exotestmod.items;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;

public class ItemEnergyCrystal extends Item {

    public ItemEnergyCrystal(Properties p_41383_) {
        super(p_41383_);

    }

    public static void damageCrystal(ItemStack crystal, int damage, Player player, InteractionHand hand) {
        crystal.hurtAndBreak(damage, player, (item) -> {
            player.broadcastBreakEvent(hand);
        });
    }

    @Override
    public void appendHoverText(ItemStack item, Level level, List<Component> components, TooltipFlag p_41424_) {
        components.add(new TextComponent("Crystal Damage: " + ItemMagicWand.getCrystalDamage(item)).withStyle(ChatFormatting.GRAY));
        super.appendHoverText(item, level, components, p_41424_);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack us = super.getDefaultInstance();
        CompoundTag tag = us.getOrCreateTag();
        tag.putInt("CrystalDamage", 0);
        return us;
    }

    public static void setCrystalDamage(ItemStack crystal, int damage) {
        CompoundTag tag = crystal.getOrCreateTag();
        tag.putInt("CrystalDamage", damage);
    }
}
