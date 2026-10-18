package net.okamiz.common.records;

import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record CustomMushroomDefinition(String id, @Nullable String name, String color, int growthTicks, Identifier fragmentResult, int fragmentCount,
                                       @Nullable Identifier essence, @Nullable Identifier extraDrop, @Nullable AltarDefinition altar,
                                       @Nullable List<MutationDefinition> mutations) {
    public String blockName() {
        return id + "_resource_mushroom";
    }

    public String fragmentName() {
        return id + "_fragments";
    }
}
