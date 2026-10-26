package net.okamiz.common.records;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record CustomMushroomDefinition(String id, @Nullable String name, String color, int growthTicks, ResourceLocation fragmentResult, int fragmentCount,
                                       @Nullable ResourceLocation essence, @Nullable ResourceLocation extraDrop, @Nullable AltarDefinition altar,
                                       @Nullable List<MutationDefinition> mutations) {
    public String blockName() {
        return id + "_resource_mushroom";
    }

    public String fragmentName() {
        return id + "_fragments";
    }
}
