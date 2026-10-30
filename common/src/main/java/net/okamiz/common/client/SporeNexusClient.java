package net.okamiz.common.client;

import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.okamiz.common.modded_registries.BlocksRegistry;

import java.util.ArrayList;
import java.util.List;

/** Client only setup shared by Fabric and NeoForge. */
public final class SporeNexusClient {

    private SporeNexusClient() {}

    public static void initRenderTypes() {
        List<Block> cutout = new ArrayList<>();
        cutout.add(BlocksRegistry.BANDED_AGARIC.get());
        cutout.add(BlocksRegistry.ETERNAL_LIGHT_MUSHROOM.get());
        cutout.add(BlocksRegistry.MYCELIAN_CORE.get());
        cutout.add(BlocksRegistry.MYCELIAN_CORE_PROXY.get());
        cutout.add(BlocksRegistry.SPORE_NEXUS_CRAFT_BLOCK.get());
        BlocksRegistry.RESOURCE_MUSHROOMS.values().forEach(block -> cutout.add(block.get()));

        RenderTypeRegistry.register(RenderType.cutout(), cutout.toArray(Block[]::new));
    }
}
