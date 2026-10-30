package net.okamiz.fabric;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.okamiz.SporeNexus;
import net.okamiz.common.modded_registries.BlockEntitiesRegistry;
import net.okamiz.fabric.worldgen.BiomeModifiersFabric;

public final class SporeNexusFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        SporeNexus.init();
        registerCapabilities();
        BiomeModifiersFabric.generateModWorldGen();
    }



    private void registerCapabilities(){
        ItemStorage.SIDED.registerForBlockEntity((be, direction) -> InventoryStorage.of(be, direction),
                BlockEntitiesRegistry.MUSHROOM_HARVESTER_BE.get());
        ItemStorage.SIDED.registerForBlockEntity((be, direction) -> InventoryStorage.of(be, direction),
                BlockEntitiesRegistry.SPORE_NEXUS_CRAFT_BE.get());
        ItemStorage.SIDED.registerForBlockEntity((be, direction) -> InventoryStorage.of(be, direction),
                BlockEntitiesRegistry.MYCELIAN_CORE_BE.get());
    }
}
