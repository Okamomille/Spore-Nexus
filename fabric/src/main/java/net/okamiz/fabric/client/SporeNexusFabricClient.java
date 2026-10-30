package net.okamiz.fabric.client;

import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.okamiz.common.client.SporeNexusClient;
import net.okamiz.common.modded_registries.BlockEntitiesRegistry;
import net.okamiz.common.modded_registries.MenusRegistry;
import net.okamiz.common.blocks.renderer.MycelianCoreBlockEntityRenderer;
import net.okamiz.common.blocks.renderer.SporeNexusCraftBlockEntityRenderer;
import net.okamiz.common.menus.custom.MycelianCoreScreen;
import net.okamiz.common.menus.custom.SporeNexusCraftScreen;

public final class SporeNexusFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerScreens();
        registerBER();
        SporeNexusClient.initRenderTypes();
    }

    private void registerScreens() {
        MenuRegistry.registerScreenFactory(MenusRegistry.MYCELIAN_CORE_MENU.get(), MycelianCoreScreen::new);
        MenuRegistry.registerScreenFactory(MenusRegistry.SPORE_NEXUS_CRAFT_MENU.get(), SporeNexusCraftScreen::new);
    }


    private void registerBER(){
        BlockEntityRenderers.register(BlockEntitiesRegistry.MYCELIAN_CORE_BE.get(), MycelianCoreBlockEntityRenderer::new);
        BlockEntityRenderers.register(BlockEntitiesRegistry.SPORE_NEXUS_CRAFT_BE.get(), SporeNexusCraftBlockEntityRenderer::new);
    }
}
