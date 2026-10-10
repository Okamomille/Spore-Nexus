package net.okamiz.common.worldgen;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.okamiz.SporeNexus;

import java.util.List;

public class PlacedFeatures {

    public static final ResourceKey<PlacedFeature> BANDED_AGARIC_PLACED_KEY = registerKey("banded_agaric_placed");
    public static final ResourceKey<PlacedFeature> ETERNAL_LIGHT_MUSHROOM_PLACED_KEY = registerKey("eternal_light_mushroom_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, BANDED_AGARIC_PLACED_KEY,
                configuredFeatures.getOrThrow(ConfiguredFeatures.BANDED_AGARIC_KEY),
                List.of(CountPlacement.of(4), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-54), VerticalAnchor.absolute(50)),
                        EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                        RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome()));

        register(context, ETERNAL_LIGHT_MUSHROOM_PLACED_KEY,
                configuredFeatures.getOrThrow(ConfiguredFeatures.ETERNAL_LIGHT_MUSHROOM_KEY),
                List.of(CountPlacement.of(6), InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-54), VerticalAnchor.absolute(50)),
                        EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                        RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome()));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
                                 Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }


}
