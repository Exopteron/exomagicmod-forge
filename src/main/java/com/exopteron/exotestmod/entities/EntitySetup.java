package com.exopteron.exotestmod.entities;

import com.exopteron.exotestmod.TestMod;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fmllegacy.RegistryObject;

public class EntitySetup {
    public static RegistryObject<EntityType<EntityIceBall>> iceBall = null;
    public static RegistryObject<EntityType<EntityMagicFireball>> magicBall = null;

    public EntitySetup(TestMod main) {
        iceBall = TestMod.ENTITIES.register("ice_ball",
                () -> EntityType.Builder.<EntityIceBall>of(EntityIceBall::new, MobCategory.MISC).build("ice_ball"));
        magicBall = TestMod.ENTITIES.register("magic_ball",
                () -> EntityType.Builder.<EntityMagicFireball>of(EntityMagicFireball::new, MobCategory.MISC)
                        .build("magic_ball"));
    }
}
