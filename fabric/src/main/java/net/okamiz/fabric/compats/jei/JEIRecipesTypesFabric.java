package net.okamiz.fabric.compats.jei;

import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.okamiz.SporeNexus;
import net.okamiz.common.recipe.mycelian_core.MycelianCoreRecipe;
import net.okamiz.common.recipe.spore_nexus_craft.SporeNexusCraftRecipe;

public class JEIRecipesTypesFabric {
    public static final IRecipeType<RecipeHolder<MycelianCoreRecipe>> MYCELIAN_CORE =
            create(SporeNexus.MOD_ID, "mycelian_core", MycelianCoreRecipe.class);
    public static final IRecipeType<RecipeHolder<SporeNexusCraftRecipe>> SPORE_NEXUS_CRAFT =
            create(SporeNexus.MOD_ID, "spore_nexus_craft", SporeNexusCraftRecipe.class);

    // From Occultism: https://github.com/klikli-dev/occultism/blob/version/26.1.2/src/main/java/com/klikli_dev/occultism/integration/jei/impl/JeiRecipeTypes.java
    // Under MIT-License
    public static <R extends Recipe<?>> IRecipeType<RecipeHolder<R>> create(String modid, String name, Class<? extends R> recipeClass) {
        Identifier uid = Identifier.fromNamespaceAndPath(modid, name);
        @SuppressWarnings({"unchecked", "RedundantCast"})
        Class<? extends RecipeHolder<R>> holderClass = (Class<? extends RecipeHolder<R>>) (Object) RecipeHolder.class;
        return IRecipeType.create(uid, holderClass);
    }
}
