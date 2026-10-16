package net.okamiz.common.compats.jei;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.okamiz.common.recipe.mushroom_mutation.MushroomMutationRecipe;

public record MushroomMutation(Ingredient parentA, Ingredient parentB, ItemStack result, float chance) {
    public static MushroomMutation from(MushroomMutationRecipe r) {
        return new MushroomMutation(r.parentA(), r.parentB(), r.output().create(), r.chance());
    }
}
