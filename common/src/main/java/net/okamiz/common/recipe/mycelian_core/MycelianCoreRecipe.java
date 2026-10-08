package net.okamiz.common.recipe.mycelian_core;

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

public record MycelianCoreRecipe(Ingredient mushroomInputItem, Ingredient substractInputItem, List<Ingredient> nutrientInputs
        , ItemStackTemplate output, Optional<ItemStackTemplate> secondaryOutput)
        implements Recipe<MycelianCoreRecipeInput> {

    public static final MapCodec<MycelianCoreRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Ingredient.CODEC.fieldOf("mushroom_input").forGetter(MycelianCoreRecipe::mushroomInputItem),
                    Ingredient.CODEC.fieldOf("substract_input").forGetter(MycelianCoreRecipe::substractInputItem),
                    Ingredient.CODEC.listOf().optionalFieldOf("nutrients", List.of()).forGetter(MycelianCoreRecipe::nutrientInputs),
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(MycelianCoreRecipe::output),
                    ItemStackTemplate.CODEC.optionalFieldOf("secondary_output").forGetter(MycelianCoreRecipe::secondaryOutput)
            ).apply(instance, MycelianCoreRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MycelianCoreRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, MycelianCoreRecipe::mushroomInputItem,
                    Ingredient.CONTENTS_STREAM_CODEC, MycelianCoreRecipe::substractInputItem,
                    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), MycelianCoreRecipe::nutrientInputs,
                    ItemStackTemplate.STREAM_CODEC, MycelianCoreRecipe::output,
                    ItemStackTemplate.STREAM_CODEC.apply(ByteBufCodecs::optional), MycelianCoreRecipe::secondaryOutput,
                    MycelianCoreRecipe::new);

    /** send slot index for nutrients(0-2) used, or empty. */
    public Optional<int[]> findNutrientSlots(MycelianCoreRecipeInput input) {
        int[] assignment = new int[nutrientInputs.size()];
        return assign(0, input, assignment, new boolean[3]) ? Optional.of(assignment) : Optional.empty();
    }

    private boolean assign(int index, MycelianCoreRecipeInput input, int[] assignment, boolean[] used) {
        if (index == nutrientInputs.size()) {
            return true;
        }
        for (int slot = 0; slot < 3; slot++) {
            if (!used[slot] && nutrientInputs.get(index).test(input.getItem(2 + slot))) {
                used[slot] = true;
                assignment[index] = slot;
                if (assign(index + 1, input, assignment, used)) {
                    return true;
                }
                used[slot] = false;
            }
        }
        return false;
    }

    @Override
    public boolean matches(MycelianCoreRecipeInput input, Level level) {
        if (level.isClientSide()) {
            return false;
        }
        return mushroomInputItem.test(input.getItem(0))
                && substractInputItem.test(input.getItem(1))
                && findNutrientSlots(input).isPresent();
    }

    @Override
    public ItemStack assemble(MycelianCoreRecipeInput input) {
        return output.create().copy();
    }
    public Optional<ItemStack> assembleSecondary() {
        return secondaryOutput.map(ItemStackTemplate::create);
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String group() {
        return "Mycelian Core";
    }

    @Override
    public RecipeSerializer<? extends Recipe<MycelianCoreRecipeInput>> getSerializer() {
        return RecipesRegistry.MYCELIAN_CORE_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<MycelianCoreRecipeInput>> getType() {
        return RecipesRegistry.MYCELIAN_CORE_RECIPE_TYPE.get();
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
