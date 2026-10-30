package net.okamiz.fabric.compats.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.okamiz.SporeNexus;
import net.okamiz.common.compats.jei.MushroomMutation;
import net.okamiz.common.compats.jei.category.MushroomMutationCategory;
import net.okamiz.common.modded_registries.BlocksRegistry;
import net.okamiz.common.modded_registries.MenusRegistry;
import net.okamiz.common.modded_registries.RecipesRegistry;
import net.okamiz.common.blocks.custom.ResourceMushroomBlock;
import net.okamiz.common.compats.jei.MushroomHarvest;
import net.okamiz.common.compats.jei.MushroomHarvestCategory;
import net.okamiz.common.menus.custom.MycelianCoreMenu;
import net.okamiz.common.menus.custom.MycelianCoreScreen;
import net.okamiz.common.menus.custom.SporeNexusCraftMenu;
import net.okamiz.common.menus.custom.SporeNexusCraftScreen;
import net.okamiz.fabric.compats.jei.category.MycelianCoreRecipeCategory;
import net.okamiz.fabric.compats.jei.category.SporeNexusCraftRecipeCategory;

import java.util.List;

@JeiPlugin
public class JEIPluginFabric implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(SporeNexus.MOD_ID, "jei_plugin");
    }

    private <I extends RecipeInput, T extends Recipe<I>> List<RecipeHolder<T>> getRecipes(RecipeType<T> type) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return List.of();
        }
        return level.getRecipeManager().getAllRecipesFor(type);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new MycelianCoreRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new SporeNexusCraftRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MushroomHarvestCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MushroomMutationCategory(registration.getJeiHelpers().getGuiHelper()));

    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(JEIRecipesTypesFabric.MYCELIAN_CORE, this.getRecipes(RecipesRegistry.MYCELIAN_CORE_RECIPE_TYPE.get()));
        registration.addRecipes(JEIRecipesTypesFabric.SPORE_NEXUS_CRAFT, this.getRecipes(RecipesRegistry.SPORE_NEXUS_CRAFT_RECIPE_TYPE.get()));
        registration.addRecipes(MushroomHarvestCategory.TYPE, BuiltInRegistries.BLOCK.stream().filter(b -> b instanceof ResourceMushroomBlock)
                .map(b -> new MushroomHarvest(b, ((ResourceMushroomBlock) b).getHarvestDrops())).toList());
        registration.addRecipes(MushroomMutationCategory.TYPE, this.getRecipes(RecipesRegistry.MUSHROOM_MUTATION_RECIPE_TYPE.get())
                        .stream().map(holder -> MushroomMutation.from(holder.value())).toList());
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(MycelianCoreScreen.class, 91, 36, 24, 16, JEIRecipesTypesFabric.MYCELIAN_CORE);
        registration.addRecipeClickArea(SporeNexusCraftScreen.class, 103, 83, 26, 13, JEIRecipesTypesFabric.SPORE_NEXUS_CRAFT);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BlocksRegistry.MYCELIAN_CORE.get()), JEIRecipesTypesFabric.MYCELIAN_CORE);
        registration.addRecipeCatalyst(new ItemStack(BlocksRegistry.SPORE_NEXUS_CRAFT_BLOCK.get()), JEIRecipesTypesFabric.SPORE_NEXUS_CRAFT);

    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(
                MycelianCoreMenu.class, MenusRegistry.MYCELIAN_CORE_MENU.get(), JEIRecipesTypesFabric.MYCELIAN_CORE,
                36, 5,
                0, 36);

        registration.addRecipeTransferHandler(
                SporeNexusCraftMenu.class, MenusRegistry.SPORE_NEXUS_CRAFT_MENU.get(), JEIRecipesTypesFabric.SPORE_NEXUS_CRAFT,
                36, 9,
                0, 36);
    }
}