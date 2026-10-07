package net.okamiz.fabric.client;

import dev.architectury.registry.client.gui.MenuScreenRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.okamiz.common.Registries.MenusRegistry;
import net.okamiz.common.menus.custom.MycelianCoreScreen;

public final class SporeNexusFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerScreens();
    }

    private void registerScreens() {
        MenuScreenRegistry.registerScreenFactory(MenusRegistry.MYCELIAN_CORE_MENU.get(), MycelianCoreScreen::new);
    }


}
