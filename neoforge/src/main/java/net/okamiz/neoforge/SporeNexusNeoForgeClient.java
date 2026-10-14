package net.okamiz.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.okamiz.SporeNexus;
import net.okamiz.common.modded_registries.BlockEntitiesRegistry;
import net.okamiz.common.modded_registries.MenusRegistry;
import net.okamiz.common.blocks.renderer.MycelianCoreBlockEntityRenderer;
import net.okamiz.common.blocks.renderer.SporeNexusCraftBlockEntityRenderer;
import net.okamiz.common.menus.custom.MycelianCoreScreen;
import net.okamiz.common.menus.custom.SporeNexusCraftScreen;

@EventBusSubscriber(modid = SporeNexus.MOD_ID, value = Dist.CLIENT)
public class SporeNexusNeoForgeClient {
    @SubscribeEvent
    public static void registerScreen(RegisterMenuScreensEvent event){
        event.register(MenusRegistry.MYCELIAN_CORE_MENU.get(), MycelianCoreScreen::new);
        event.register(MenusRegistry.SPORE_NEXUS_CRAFT_MENU.get(), SporeNexusCraftScreen::new);
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(BlockEntitiesRegistry.MYCELIAN_CORE_BE.get(), MycelianCoreBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(BlockEntitiesRegistry.SPORE_NEXUS_CRAFT_BE.get(), SporeNexusCraftBlockEntityRenderer::new);
    }
}
