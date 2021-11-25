package com.exopteron.exotestmod.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner.BlockType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.util.Constants;

public class EntityMagicFireball extends ThrowableItemProjectile {
    public EntityMagicFireball(Level p_37399_, LivingEntity p_37400_) {
        super(EntityType.SNOWBALL, p_37400_, p_37399_);
    }

    public EntityMagicFireball(EntityType<? extends EntityMagicFireball> p_37438_, Level p_37440_) {
        super(p_37438_, p_37440_);
    }

    public EntityMagicFireball(EntityType<? extends EntityMagicFireball> p_37438_, LivingEntity p_37439_, Level p_37440_) {
        super(p_37438_, p_37439_, p_37440_);
    }

    public EntityMagicFireball(Level p_37394_, double p_37395_, double p_37396_, double p_37397_) {
        super(EntityType.SNOWBALL, p_37395_, p_37396_, p_37397_, p_37394_);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SNOWBALL;
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        Entity entity = hitResult.getEntity();
        entity.setSecondsOnFire(15);
        entity.hurt(DamageSource.MAGIC, 6.0F);
        this.level.explode(entity, entity.position().x, entity.position().y, entity.position().z, 1.5F, false, Explosion.BlockInteraction.NONE);
        this.kill();
        super.onHitEntity(hitResult);
    }
    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        BlockPos pos = hitResult.getBlockPos().relative(hitResult.getDirection());
        if (this.level.getBlockState(pos).canBeReplaced(Fluids.EMPTY)) {
            this.level.setBlock(pos, Blocks.FIRE.defaultBlockState(), Constants.BlockFlags.DEFAULT);
        }
        this.level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 1.5F, false, Explosion.BlockInteraction.NONE);
        this.kill();
        super.onHitBlock(hitResult);
    }
}
