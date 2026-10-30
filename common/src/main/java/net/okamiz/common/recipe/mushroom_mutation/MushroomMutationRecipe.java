package net.okamiz.common.recipe.mushroom_mutation;

import com.mojang.serialization.Codec;
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

public record MushroomMutationRecipe(Ingredient parentA, Ingredient parentB, ItemStack output, float chance)
        implements Recipe<MushroomMutationRecipeInput> {

    public static final MapCodec<MushroomMutationRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    RecipeCodecs.INGREDIENT.fieldOf("parent_a").forGetter(MushroomMutationRecipe::parentA),
                    RecipeCodecs.INGREDIENT.fieldOf("parent_b").forGetter(MushroomMutationRecipe::parentB),
                    ItemStack.CODEC.fieldOf("result").forGetter(MushroomMutationRecipe::output),
                    Codec.floatRange(0f, 1f).optionalFieldOf("chance", 0.05f).forGetter(MushroomMutationRecipe::chance))
                    .apply(instance, MushroomMutationRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MushroomMutationRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, MushroomMutationRecipe::parentA,
                    Ingredient.CONTENTS_STREAM_CODEC, MushroomMutationRecipe::parentB,
                    ItemStack.STREAM_CODEC, MushroomMutationRecipe::output,
                    ByteBufCodecs.FLOAT, MushroomMutationRecipe::chance, MushroomMutationRecipe::new);

    @Override
    public boolean matches(MushroomMutationRecipeInput input, Level level) {
        // order does not matter: A+B or B+A
        return (parentA.test(input.first()) && parentB.test(input.second())) || (parentA.test(input.second()) && parentB.test(input.first()));
    }

    @Override
    public ItemStack assemble(MushroomMutationRecipeInput input, HolderLookup.Provider registries) {
        return output.copy();
    }

    /** Convenience overload, the registries are not needed to build the result. */
    public ItemStack assemble(MushroomMutationRecipeInput input) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output;
    }

    @Override public boolean showNotification() {
        return false;
    }

    @Override public String getGroup() {
        return "Spore Nexus Mutation";
    }

    @Override public RecipeSerializer<?> getSerializer() {
        return RecipesRegistry.MUSHROOM_MUTATION_RECIPE_SERIALIZER.get();
    }

    @Override public RecipeType<?> getType() {
        return RecipesRegistry.MUSHROOM_MUTATION_RECIPE_TYPE.get();
    }
    @Override
    public boolean isSpecial() {
        return true;
    }

}
