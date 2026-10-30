package net.okamiz.common.compats.jade;

import net.minecraft.world.level.Level;
import net.okamiz.common.recipe.mushroom_mutation.MushroomMutationRecipe;
import net.okamiz.common.recipe.mushroom_mutation.MushroomMutationRecipeInput;

import net.minecraft.world.item.crafting.RecipeHolder;
import net.okamiz.common.modded_registries.RecipesRegistry;
import java.util.Optional;


public final class ClientMutationRecipes {

    private ClientMutationRecipes() {}

    public static Optional<MushroomMutationRecipe> find(MushroomMutationRecipeInput input, Level level) {
        return level.getRecipeManager()
                .getRecipeFor(RecipesRegistry.MUSHROOM_MUTATION_RECIPE_TYPE.get(), input, level)
                .map(RecipeHolder::value);
    }
}
