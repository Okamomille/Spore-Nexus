package net.okamiz.common.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.okamiz.SporeNexus;
import net.okamiz.common.Registries.BlocksRegistry;

public class ConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?,?>> BANDED_AGARIC_KEY = registerKey("banded_agaric_key");
    public static final ResourceKey<ConfiguredFeature<?,?>> ETERNAL_LIGHT_MUSHROOM_KEY = registerKey("eternal_light_mushroom_key");


    public static void bootstrap(BootstrapContext<ConfiguredFeature<?,?>> context){

        register(context, BANDED_AGARIC_KEY, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(BlocksRegistry.BANDED_AGARIC.get())));
        register(context, ETERNAL_LIGHT_MUSHROOM_KEY, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(BlocksRegistry.ETERNAL_LIGHT_MUSHROOM.get())));

    }


    public static ResourceKey<ConfiguredFeature<?,?>> registerKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?,?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?,?>> key, F feature, FC configuration){
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
