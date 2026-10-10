package net.okamiz.neoforge.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.okamiz.SporeNexus;
import net.okamiz.common.worldgen.PlacedFeatures;

public class BiomeModifiersNeoForge {

    public static final ResourceKey<BiomeModifier> ADD_BANDED_AGARIC = registerKey("add_banded_agaric");
    public static final ResourceKey<BiomeModifier> ADD_ETERNAL_LIGHT_MUSHROOM = registerKey("add_eternal_light_mushroom");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_BANDED_AGARIC, new BiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(PlacedFeatures.BANDED_AGARIC_PLACED_KEY)), GenerationStep.Decoration.UNDERGROUND_DECORATION));

        context.register(ADD_ETERNAL_LIGHT_MUSHROOM, new BiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biomes.getOrThrow(Biomes.LUSH_CAVES)),
                HolderSet.direct(placedFeatures.getOrThrow(PlacedFeatures.ETERNAL_LIGHT_MUSHROOM_PLACED_KEY)), GenerationStep.Decoration.UNDERGROUND_DECORATION));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, name));
    }
}
