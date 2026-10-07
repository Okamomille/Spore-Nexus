package net.okamiz.common.recipe.mycelian_core;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record MycelianCoreRecipeInput(ItemStack mushroom, ItemStack substract,
                                      ItemStack nutrient1, ItemStack nutrient2, ItemStack nutrient3) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        return switch (index) {
            case 0 -> mushroom;
            case 1 -> substract;
            case 2 -> nutrient1;
            case 3 -> nutrient2;
            case 4 -> nutrient3;
            default -> throw new IllegalArgumentException("No item for index " + index);
        };
    }

    @Override
    public int size() {
        return 5;
    }
}
