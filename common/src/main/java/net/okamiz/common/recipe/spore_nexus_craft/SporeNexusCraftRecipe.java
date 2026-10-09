package net.okamiz.common.recipe.spore_nexus_craft;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.okamiz.common.Registries.RecipesRegistry;

import java.util.List;
import java.util.Optional;

public record SporeNexusCraftRecipe(Ingredient mushroomInputItem, List<Ingredient> resourceInputs, List<Ingredient> secondaryInputs
        , ItemStackTemplate output)
        implements Recipe<SporeNexusCraftRecipeInput> {

    public static final MapCodec<SporeNexusCraftRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Ingredient.CODEC.fieldOf("mushroom_input").forGetter(SporeNexusCraftRecipe::mushroomInputItem),
                    Ingredient.CODEC.listOf().optionalFieldOf("resource_input", List.of()).forGetter(SporeNexusCraftRecipe::resourceInputs),
                    Ingredient.CODEC.listOf().optionalFieldOf("secondary_input", List.of()).forGetter(SporeNexusCraftRecipe::secondaryInputs),
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(SporeNexusCraftRecipe::output)
            ).apply(instance, SporeNexusCraftRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, SporeNexusCraftRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, SporeNexusCraftRecipe::mushroomInputItem,
                    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), SporeNexusCraftRecipe::resourceInputs,
                    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), SporeNexusCraftRecipe::secondaryInputs,
                    ItemStackTemplate.STREAM_CODEC, SporeNexusCraftRecipe::output,
                    SporeNexusCraftRecipe::new);



    private static final int MUSHROOM_SLOT = 0;
    private static final int RESOURCE_START = 1;
    private static final int RESOURCE_COUNT = 4;
    private static final int SECONDARY_START = 5;
    private static final int SECONDARY_COUNT = 4;

    public Optional<int[]> findResourceSlots(SporeNexusCraftRecipeInput input) {
        return findSlots(resourceInputs, input, RESOURCE_START, RESOURCE_COUNT);
    }

    public Optional<int[]> findSecondarySlots(SporeNexusCraftRecipeInput input) {
        return findSlots(secondaryInputs, input, SECONDARY_START, SECONDARY_COUNT);
    }

    /** SEND SLOT INDEX FOR EACH INGREDIENT. */
    private static Optional<int[]> findSlots(List<Ingredient> ingredients, SporeNexusCraftRecipeInput input,
                                             int start, int count) {
        int[] assignment = new int[ingredients.size()];
        return assign(0, ingredients, input, start, count, assignment, new boolean[count])
                ? Optional.of(assignment) : Optional.empty();
    }

    private static boolean assign(int index, List<Ingredient> ingredients, SporeNexusCraftRecipeInput input,
                                  int start, int count, int[] assignment, boolean[] used) {
        if (index == ingredients.size()) {
            return true;
        }
        for (int i = 0; i < count; i++) {
            if (!used[i] && ingredients.get(index).test(input.getItem(start + i))) {
                used[i] = true;
                assignment[index] = start + i;
                if (assign(index + 1, ingredients, input, start, count, assignment, used)) {
                    return true;
                }
                used[i] = false;
            }
        }
        return false;
    }

    @Override
    public boolean matches(SporeNexusCraftRecipeInput input, Level level) {
        if (level.isClientSide()) {
            return false;
        }
        return mushroomInputItem.test(input.getItem(MUSHROOM_SLOT))
                && findResourceSlots(input).isPresent()
                && findSecondarySlots(input).isPresent();
    }

    @Override
    public ItemStack assemble(SporeNexusCraftRecipeInput input) {
        return output.create().copy();
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String group() {
        return "Spore Nexus Craft";
    }

    @Override
    public RecipeSerializer<? extends Recipe<SporeNexusCraftRecipeInput>> getSerializer() {
        return RecipesRegistry.SPORE_NEXUS_CRAFT_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<SporeNexusCraftRecipeInput>> getType() {
        return RecipesRegistry.SPORE_NEXUS_CRAFT_RECIPE_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }
}
