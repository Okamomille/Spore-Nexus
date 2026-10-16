package net.okamiz.common.compats.jade;

import net.minecraft.world.level.Level;
import net.okamiz.common.recipe.mushroom_mutation.MushroomMutationRecipe;
import net.okamiz.common.recipe.mushroom_mutation.MushroomMutationRecipeInput;

import java.util.List;
import java.util.Optional;

public final class ClientMutationRecipes {
    private static List<MushroomMutationRecipe> recipes = List.of();

    private ClientMutationRecipes() {}

    public static void set(List<MushroomMutationRecipe> list) {
        recipes = List.copyOf(list);
    }

    public static Optional<MushroomMutationRecipe> find(MushroomMutationRecipeInput input, Level level) {
        for (MushroomMutationRecipe recipe : recipes) {
            if (recipe.matches(input, level)) {
                return Optional.of(recipe);
            }
        }
        return Optional.empty();
    }
}
