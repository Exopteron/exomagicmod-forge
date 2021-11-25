package com.exopteron.exotestmod.items;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.exopteron.exotestmod.TestMod;
import com.exopteron.exotestmod.Utils;
import com.exopteron.exotestmod.entities.EntityIceBall;
import com.exopteron.exotestmod.items.wand.IWandSpell;
import com.exopteron.exotestmod.items.wand.MagicWandSpells;
import com.exopteron.exotestmod.items.wand.MagicWandSpells.SpellRegistry;
import com.exopteron.exotestmod.keybinds.KeyBinds;
import com.exopteron.exotestmod.network.PacketChangeSpell;
import com.exopteron.exotestmod.network.PacketHandler;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ItemMagicWand extends Item {
    public ItemMagicWand(Properties properties) {
        super(properties);

    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }

    public static void damageWand(ItemStack wand, int damage, Player player, InteractionHand hand) {
        int crystalDamage = getCrystalDamage(wand);
        ItemStack crystal = ItemSetup.ENERGY_CRYSTAL.get().getDefaultInstance();
        wand.hurtAndBreak(damage, player, (item) -> {
            Utils.awardAdvancement(new ResourceLocation("exotestmod", "wand_break"), (ServerPlayer) player);
            if ((crystalDamage + 1) < 11) {
                player.level.explode(player, player.position().x, player.position().y, player.position().z, 1.5F, true,
                        BlockInteraction.NONE);
                ItemEnergyCrystal.setCrystalDamage(crystal, crystalDamage + 1);
                player.level.addFreshEntity(new ItemEntity(player.level, player.position().x, player.position().y + 1,
                        player.position().z, crystal, 0D, 0D, 0D));
            } else {
                Utils.awardAdvancement(new ResourceLocation("exotestmod", "wand_explosion"), (ServerPlayer) player);
                player.level.explode(player, player.position().x, player.position().y, player.position().z, 4.0F, true,
                        BlockInteraction.BREAK);
            }
            player.level.addFreshEntity(new ItemEntity(player.level, player.position().x, player.position().y + 1,
                    player.position().z, new ItemStack(TestMod.NETHERITE_STICK.get()), 0D, 0D, 0D));
            player.broadcastBreakEvent(hand);
        });
    }

    public static int getCrystalDamage(ItemStack wand) {
        CompoundTag tag = wand.getOrCreateTag();
        int crystalDamage = tag.getInt("CrystalDamage");
        return crystalDamage;
    }

    @Override
    public void onCraftedBy(ItemStack p_41447_, Level p_41448_, Player p_41449_) {
        // TODO Auto-generated method stub
        super.onCraftedBy(p_41447_, p_41448_, p_41449_);
    }

    @Override
    public void appendHoverText(ItemStack item, Level level, List<Component> components, TooltipFlag p_41424_) {
        components.add(new TextComponent("Crystal Damage: " + getCrystalDamage(item)).withStyle(ChatFormatting.GRAY));
        super.appendHoverText(item, level, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack i = player.getItemInHand(hand);
        CompoundTag tag = i.getOrCreateTag();
        String spell = tag.getString("CurrentSpell");
        if (spell == null || spell == "") {
            tag.putString("CurrentSpell", "FIREBALL");
        }
        spell = tag.getString("CurrentSpell");
        boolean doRebound = shouldRebound(i);
        if (!level.isClientSide) {
            IWandSpell s = SpellRegistry.INSTANCE.spellRegistry.get(spell);
            if (s == null) {
                System.out.println("Is null from " + spell);
                for (String spel : SpellRegistry.INSTANCE.spellRegistry.keySet()) {
                    System.out.println("Spell " + spel);
                }
                return super.use(level, player, hand);
            }
            if (doRebound) {
                Utils.awardAdvancement(new ResourceLocation("exotestmod", "wand_rebound"), (ServerPlayer) player);
                player.sendMessage(new TranslatableComponent("exotestmod.spellrebound").withStyle(ChatFormatting.ITALIC)
                        .withStyle(ChatFormatting.GRAY), Util.NIL_UUID);
                damageWand(i, 1, player, hand);
                s.rebound(level, player, hand);
            } else {
                Utils.awardAdvancement(new ResourceLocation("exotestmod", "cast_spell"), (ServerPlayer) player);
                int cooldownTicks = s.cast(level, player, hand, i);
                damageWand(i, s.getSpellDurabilityCost(), player, hand);
                player.getCooldowns().addCooldown(this, cooldownTicks);
            }
        } else {
            player.swing(hand);
            // spell.spell.castClient(level, player, hand);
        }
        return super.use(level, player, hand);
    }

    public static boolean shouldRebound(ItemStack wand) {
        int crystalDamage = getCrystalDamage(wand);
        if (crystalDamage == 0) {
            return false;
        }
        Random random = ThreadLocalRandom.current();
        return (random.nextInt(100) <= 10 * crystalDamage);
    }
}
