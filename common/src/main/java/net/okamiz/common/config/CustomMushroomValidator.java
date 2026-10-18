package net.okamiz.common.config;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.okamiz.SporeNexus;
import net.okamiz.common.modded_registries.ItemsRegistry;
import net.okamiz.common.records.AltarDefinition;
import net.okamiz.common.records.CustomMushroomDefinition;
import net.okamiz.common.records.MutationDefinition;
import org.jetbrains.annotations.Nullable;

public final class CustomMushroomValidator {

    private CustomMushroomValidator() {}

    public static void validate() {
        for (CustomMushroomDefinition def : ItemsRegistry.CUSTOM_MUSHROOMS) {
            checkItem(def, "fragment_result", def.fragmentResult());
            checkItem(def, "essence", def.essence());
            checkItem(def, "extra_drop", def.extraDrop());
            AltarDefinition altar = def.altar();
            if (altar != null) {
                checkItem(def, "altar.mushroom_input", altar.mushroomInput());
                altar.resourceInputs().forEach(id -> checkItem(def, "altar.resource_input", id));
                altar.secondaryInputs().forEach(id -> checkItem(def, "altar.secondary_input", id));
            }
            for (MutationDefinition mutation : def.mutations()) {
                checkBlock(def, "mutations.parent_a", mutation.parentA());
                checkBlock(def, "mutations.parent_b", mutation.parentB());
            }
        }
    }

    private static void checkItem(CustomMushroomDefinition def, String field, @Nullable Identifier id) {
        if (id != null && BuiltInRegistries.ITEM.getOptional(id).isEmpty()) {
            SporeNexus.LOGGER.error("Custom mushroom '{}': '{}' points to the item '{}' which does not exist "
                    + "(typo, or the mod is not installed?)", def.id(), field, id);
        }
    }

    private static void checkBlock(CustomMushroomDefinition def, String field, Identifier id) {
        if (BuiltInRegistries.BLOCK.getOptional(id).isEmpty()) {
            SporeNexus.LOGGER.error("Custom mushroom '{}': '{}' points to the block '{}' which does not exist "
                    + "(typo, or the mod is not installed?)", def.id(), field, id);
        }
    }
}
