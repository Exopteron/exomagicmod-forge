package com.exopteron.exotestmod.items.wand;

import com.exopteron.exotestmod.entities.EntityIceBall;
import com.exopteron.exotestmod.items.ItemMagicWand;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class IceballSpell implements IWandSpell {

    @Override
    public int cast(Level level, Player player, InteractionHand hand, ItemStack wand) {
        ItemStack i = player.getItemInHand(hand);
        //ItemMagicWand.damageWand(i, 1, player, hand);
        EntityIceBall snowball = new EntityIceBall(level, player);
        snowball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
        level.addFreshEntity(snowball);
        return 5;
    }
    @Override
    public void rebound(Level level, Player player, InteractionHand hand) {
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 15 * 20, 255));
        player.hurt(DamageSource.MAGIC, 6.0F);
    }
}
