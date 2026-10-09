package net.okamiz.fabric.client;

import dev.architectury.registry.client.gui.MenuScreenRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.recipe.v1.sync.RecipeSynchronization;
import net.okamiz.common.Registries.MenusRegistry;
import net.okamiz.common.Registries.RecipesRegistry;
import net.okamiz.common.menus.custom.MycelianCoreScreen;
import net.okamiz.common.menus.custom.SporeNexusCraftScreen;

public final class SporeNexusFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerScreens();
        registerJEIRecipeTypes();
    }

    private void registerJEIRecipeTypes() {
        RecipeSynchronization.synchronizeRecipeSerializer(RecipesRegistry.MYCELIAN_CORE_RECIPE_SERIALIZER.get());
        RecipeSynchronization.synchronizeRecipeSerializer(RecipesRegistry.SPORE_NEXUS_CRAFT_RECIPE_SERIALIZER.get());
    }

    private void registerScreens() {
        MenuScreenRegistry.registerScreenFactory(MenusRegistry.MYCELIAN_CORE_MENU.get(), MycelianCoreScreen::new);
        MenuScreenRegistry.registerScreenFactory(MenusRegistry.SPORE_NEXUS_CRAFT_MENU.get(), SporeNexusCraftScreen::new);
    }


}
