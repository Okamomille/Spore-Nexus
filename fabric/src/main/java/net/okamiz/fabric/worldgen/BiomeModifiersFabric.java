package net.okamiz.fabric.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.okamiz.common.worldgen.PlacedFeatures;

public class BiomeModifiersFabric {
    public static void generateModWorldGen() {

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_DECORATION,
                PlacedFeatures.BANDED_AGARIC_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.LUSH_CAVES), GenerationStep.Decoration.UNDERGROUND_DECORATION,
                PlacedFeatures.ETERNAL_LIGHT_MUSHROOM_PLACED_KEY);

    }
}
