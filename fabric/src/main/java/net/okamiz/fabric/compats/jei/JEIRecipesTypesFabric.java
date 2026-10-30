package net.okamiz.fabric.compats.jei;

import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.okamiz.SporeNexus;
import net.okamiz.common.recipe.mycelian_core.MycelianCoreRecipe;
import net.okamiz.common.recipe.spore_nexus_craft.SporeNexusCraftRecipe;

public class JEIRecipesTypesFabric {
    public static final RecipeType<RecipeHolder<MycelianCoreRecipe>> MYCELIAN_CORE =
            RecipeType.createRecipeHolderType(ResourceLocation.fromNamespaceAndPath(SporeNexus.MOD_ID, "mycelian_core"));
    public static final RecipeType<RecipeHolder<SporeNexusCraftRecipe>> SPORE_NEXUS_CRAFT =
            RecipeType.createRecipeHolderType(ResourceLocation.fromNamespaceAndPath(SporeNexus.MOD_ID, "spore_nexus_craft"));
}
