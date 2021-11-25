package com.exopteron.exotestmod.world;

import com.exopteron.exotestmod.items.Magicium;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.VeryBiasedToBottomHeight;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class OreGen {
    private static RuleTest ENDSTONE = new BlockMatchTest(Blocks.END_STONE);

    @SubscribeEvent
    public static void genOres(final BiomeLoadingEvent e) {
        if (e.getCategory() == BiomeCategory.THEEND) {
            e.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Feature.ORE
                    .configured(new OreConfiguration(ENDSTONE, Magicium.MAGICIUM_ORE.get().defaultBlockState(), 5))
                    .range(new RangeDecoratorConfiguration(
                            VeryBiasedToBottomHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.absolute(80), 15)))
                    .squared().count(5));
        }
    }
}