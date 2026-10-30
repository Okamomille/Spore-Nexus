package net.okamiz.common.recipe.mycelian_core;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.okamiz.common.modded_registries.RecipesRegistry;
import net.okamiz.common.recipe.RecipeCodecs;

import java.util.List;
import java.util.Optional;

public record MycelianCoreRecipe(Ingredient mushroomInputItem, Ingredient substractInputItem, List<Ingredient> nutrientInputs
        , ItemStack output, Optional<ItemStack> secondaryOutput)
        implements Recipe<MycelianCoreRecipeInput> {

    public static final MapCodec<MycelianCoreRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    RecipeCodecs.INGREDIENT.fieldOf("mushroom_input").forGetter(MycelianCoreRecipe::mushroomInputItem),
                    RecipeCodecs.INGREDIENT.fieldOf("substract_input").forGetter(MycelianCoreRecipe::substractInputItem),
                    RecipeCodecs.INGREDIENT.listOf().optionalFieldOf("nutrients", List.of()).forGetter(MycelianCoreRecipe::nutrientInputs),
                    ItemStack.CODEC.fieldOf("result").forGetter(MycelianCoreRecipe::output),
                    ItemStack.CODEC.optionalFieldOf("secondary_output").forGetter(MycelianCoreRecipe::secondaryOutput)
            ).apply(instance, MycelianCoreRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MycelianCoreRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, MycelianCoreRecipe::mushroomInputItem,
                    Ingredient.CONTENTS_STREAM_CODEC, MycelianCoreRecipe::substractInputItem,
                    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), MycelianCoreRecipe::nutrientInputs,
                    ItemStack.STREAM_CODEC, MycelianCoreRecipe::output,
                    ItemStack.STREAM_CODEC.apply(ByteBufCodecs::optional), MycelianCoreRecipe::secondaryOutput,
                    MycelianCoreRecipe::new);

    /** send slot index for nutrients(0-2) used, or empty. */
    public Optional<int[]> findNutrientSlots(MycelianCoreRecipeInput input) {
        int[] assignment = new int[nutrientInputs.size()];
        int[] remaining = {
                input.getItem(2).getCount(),
                input.getItem(3).getCount(),
                input.getItem(4).getCount()
        };
        return assign(0, input, assignment, remaining) ? Optional.of(assignment) : Optional.empty();
    }

    private boolean assign(int index, MycelianCoreRecipeInput input, int[] assignment, int[] remaining) {
        if (index == nutrientInputs.size()) {
            return true;
        }
        for (int slot = 0; slot < 3; slot++) {
            if (remaining[slot] > 0 && nutrientInputs.get(index).test(input.getItem(2 + slot))) {
                remaining[slot]--;
                assignment[index] = slot;
                if (assign(index + 1, input, assignment, remaining)) {
                    return true;
                }
                remaining[slot]++;
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
    public ItemStack assemble(MycelianCoreRecipeInput input, HolderLookup.Provider registries) {
        return output.copy();
    }

    /** Convenience overload, the registries are not needed to build the result. */
    public ItemStack assemble(MycelianCoreRecipeInput input) {
        return output.copy();
    }

    public Optional<ItemStack> assembleSecondary() {
        return secondaryOutput.map(ItemStack::copy);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output;
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String getGroup() {
        return "Mycelian Core";
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipesRegistry.MYCELIAN_CORE_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipesRegistry.MYCELIAN_CORE_RECIPE_TYPE.get();
    }
    @Override
    public boolean isSpecial() {
        return true;
    }
}
