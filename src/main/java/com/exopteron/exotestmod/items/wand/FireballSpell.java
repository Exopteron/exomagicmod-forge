package com.exopteron.exotestmod.items.wand;

import com.exopteron.exotestmod.entities.EntityMagicFireball;
import com.exopteron.exotestmod.items.ItemMagicWand;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion;

public class FireballSpell implements IWandSpell {

    @Override
    public int cast(Level level, Player player, InteractionHand hand, ItemStack wand) {
        ItemStack i = player.getItemInHand(hand);
        //ItemMagicWand.damageWand(i, 1, player, hand);
        EntityMagicFireball snowball = new EntityMagicFireball(level, player);
        snowball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
        level.addFreshEntity(snowball);
        return 15;
    }
    @Override
    public void rebound(Level level, Player player, InteractionHand hand) {
        player.setSecondsOnFire(15);
        player.hurt(DamageSource.MAGIC, 6.0F);
        level.explode(player, player.position().x, player.position().y, player.position().z, 1.5F, false, Explosion.BlockInteraction.NONE);
    }
}
