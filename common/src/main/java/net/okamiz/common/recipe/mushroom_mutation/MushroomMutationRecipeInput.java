package net.okamiz.common.recipe.mushroom_mutation;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record MushroomMutationRecipeInput(ItemStack first, ItemStack second) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        return index == 0 ? first : second;
    }

    @Override
    public int size() {
        return 2;
    }
}
