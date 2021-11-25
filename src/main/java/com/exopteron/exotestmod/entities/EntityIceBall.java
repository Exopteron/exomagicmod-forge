package com.exopteron.exotestmod.entities;

import java.util.ArrayList;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner.BlockType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.IFluidBlock;

public class EntityIceBall extends ThrowableItemProjectile {
    public EntityIceBall(Level p_37399_, LivingEntity p_37400_) {
        super(EntityType.SNOWBALL, p_37400_, p_37399_);
    }

    public EntityIceBall(EntityType<? extends EntityIceBall> p_37438_, Level p_37440_) {
        super(p_37438_, p_37440_);
    }

    public EntityIceBall(EntityType<? extends EntityIceBall> p_37438_, LivingEntity p_37439_, Level p_37440_) {
        super(p_37438_, p_37439_, p_37440_);
    }

    public EntityIceBall(Level p_37394_, double p_37395_, double p_37396_, double p_37397_) {
        super(EntityType.SNOWBALL, p_37395_, p_37396_, p_37397_, p_37394_);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SNOWBALL;
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        Entity entity = hitResult.getEntity();
        if (entity instanceof LivingEntity) {
            LivingEntity e = (LivingEntity) entity;
            e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 15 * 20, 255));
            entity.hurt(DamageSource.MAGIC, 6.0F);
        }
        super.onHitEntity(hitResult);
    }
    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        BlockPos pos = hitResult.getBlockPos().relative(hitResult.getDirection());
        this.hitBlock(pos);
        ArrayList<BlockPos> allPositions = new ArrayList<BlockPos>();
        allPositions.add(pos.north());
        allPositions.add(pos.south());
        allPositions.add(pos.east());
        allPositions.add(pos.west());
        for (BlockPos p : allPositions) {
            this.hitBlock(this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, p));
        }
        //this.kill();
        super.onHitBlock(hitResult);
    }
    private void hitBlock(BlockPos pos) {
        if (this.level.getBlockState(pos).is(Blocks.WATER)) {
            this.level.setBlock(pos, Blocks.ICE.defaultBlockState(), Constants.BlockFlags.DEFAULT);
        } else if (this.level.getBlockState(pos).canBeReplaced(Fluids.EMPTY) && !(this.level.getBlockState(pos.below()).getBlock() instanceof IFluidBlock || this.level.getBlockState(pos.below()).getBlock() instanceof LiquidBlock) ) {
            this.level.setBlock(pos, Blocks.SNOW.defaultBlockState(), Constants.BlockFlags.DEFAULT);
        }
    }
    @Override
    public void tick() {

        BlockPos pos = this.blockPosition();
        super.tick();
        if (this.level.getBlockState(pos).is(Blocks.WATER)) {
            this.level.setBlock(pos, Blocks.ICE.defaultBlockState(), Constants.BlockFlags.DEFAULT);
        }
    }
}
