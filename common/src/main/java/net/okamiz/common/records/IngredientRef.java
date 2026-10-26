package net.okamiz.common.records;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public record IngredientRef(ResourceLocation id, boolean tag) {

    public static @Nullable IngredientRef parse(String raw) {
        boolean isTag = raw.startsWith("#");
        ResourceLocation id = ResourceLocation.tryParse(isTag ? raw.substring(1) : raw);
        return id == null ? null : new IngredientRef(id, isTag);
    }

    @Override
    public String toString() {
        return tag ? "#" + id : id.toString();
    }
}
