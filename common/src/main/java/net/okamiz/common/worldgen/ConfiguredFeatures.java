package net.okamiz.common.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.okamiz.SporeNexus;
import net.okamiz.common.modded_registries.BlocksRegistry;

public class ConfiguredFeatures {

    public static final ResourceKey<Feature> BANDED_AGARIC_KEY = registerKey("banded_agaric_key");
    public static final ResourceKey<Feature> ETERNAL_LIGHT_MUSHROOM_KEY = registerKey("eternal_light_mushroom_key");


    public static void bootstrap(BootstrapContext<Feature> context) {

        context.register(BANDED_AGARIC_KEY, new SimpleBlockFeature(BlockStateProvider.holderOf(BlocksRegistry.BANDED_AGARIC.get())));
        context.register(ETERNAL_LIGHT_MUSHROOM_KEY, new SimpleBlockFeature(BlockStateProvider.holderOf(BlocksRegistry.ETERNAL_LIGHT_MUSHROOM.get())));
    }


    public static ResourceKey<Feature> registerKey(String name) {
        return ResourceKey.create(Registries.FEATURE, ResourceLocation.fromNamespaceAndPath(SporeNexus.MOD_ID, name));
    }
}