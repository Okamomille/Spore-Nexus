package net.okamiz.common.Registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.okamiz.SporeNexus;
import net.okamiz.common.recipe.mycelian_core.MycelianCoreRecipe;

import java.lang.reflect.Type;

public class RecipesRegistry {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(SporeNexus.MOD_ID, Registries.RECIPE_TYPE);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(SporeNexus.MOD_ID, Registries.RECIPE_SERIALIZER);



    public static final RegistrySupplier<RecipeSerializer<MycelianCoreRecipe>> MYCELIAN_CORE_RECIPE_SERIALIZER =
            RECIPE_SERIALIZERS.register("mycelian_core", () -> new RecipeSerializer<>(MycelianCoreRecipe.CODEC, MycelianCoreRecipe.STREAM_CODEC));
    public static final RegistrySupplier<RecipeType<MycelianCoreRecipe>> MYCELIAN_CORE_RECIPE_TYPE =
            RECIPE_TYPES.register("mycelian_core", () -> new RecipeType<MycelianCoreRecipe>(){
                @Override
                public String toString(){
                    return "mycelian_core";
                }
            });
}
