package net.okamiz.common.config;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.okamiz.SporeNexus;
import net.okamiz.common.modded_registries.ItemsRegistry;
import net.okamiz.common.records.AltarDefinition;
import net.okamiz.common.records.CustomMushroomDefinition;
import net.okamiz.common.records.IngredientRef;
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
                checkIngredient(def, "altar.mushroom_input", altar.mushroomInput());
                altar.resourceInputs().stream().distinct().forEach(ref -> checkIngredient(def, "altar.resource_input", ref));
                altar.secondaryInputs().stream().distinct().forEach(ref -> checkIngredient(def, "altar.secondary_input", ref));
            }
            for (MutationDefinition mutation : def.mutations()) {
                checkBlock(def, "mutations.parent_a", mutation.parentA());
                checkBlock(def, "mutations.parent_b", mutation.parentB());
            }
        }
    }

    private static void checkIngredient(CustomMushroomDefinition def, String field, IngredientRef ref) {
        if (!ref.tag()) {
            checkItem(def, field, ref.id());
            return;
        }
        TagKey<Item> key = TagKey.create(Registries.ITEM, ref.id());
        boolean empty = BuiltInRegistries.ITEM.get(key).map(set -> set.size() == 0).orElse(true);
        if (empty) {
            SporeNexus.LOGGER.error("Custom mushroom '{}': '{}' points to the item tag '#{}' which does not exist "
                    + "or is empty (block tags don't work here, it must be an item tag)", def.id(), field, ref.id());
        }
    }

    private static void checkItem(CustomMushroomDefinition def, String field, @Nullable ResourceLocation id) {
        if (id != null && BuiltInRegistries.ITEM.getOptional(id).isEmpty()) {
            SporeNexus.LOGGER.error("Custom mushroom '{}': '{}' points to the item '{}' which does not exist "
                    + "(typo, or the mod is not installed?)", def.id(), field, id);
        }
    }

    private static void checkBlock(CustomMushroomDefinition def, String field, ResourceLocation id) {
        if (BuiltInRegistries.BLOCK.getOptional(id).isEmpty()) {
            SporeNexus.LOGGER.error("Custom mushroom '{}': '{}' points to the block '{}' which does not exist "
                    + "(typo, or the mod is not installed?)", def.id(), field, id);
        }
    }
}
