package com.exopteron.exotestmod.items.wand;

import com.exopteron.exotestmod.entities.EntityIceBall;
import com.exopteron.exotestmod.items.ItemMagicWand;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;

public class PoisonCloudSpell implements IWandSpell {

    @Override
    public int cast(Level level, Player player, InteractionHand hand, ItemStack wand) {
        ItemStack i = player.getItemInHand(hand);
        //ItemMagicWand.damageWand(i, 1, player, hand);
        ThrownPotion snowball = new ThrownPotion(level, player);
        snowball.setItem(PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.POISON));
        snowball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
        level.addFreshEntity(snowball);
        return 25;
    }
    
}
