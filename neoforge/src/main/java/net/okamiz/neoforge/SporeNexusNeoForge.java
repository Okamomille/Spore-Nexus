package net.okamiz.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.transfer.item.VanillaContainerWrapper;
import net.okamiz.SporeNexus;
import net.okamiz.common.modded_registries.BlockEntitiesRegistry;

@Mod(SporeNexus.MOD_ID)
public final class SporeNexusNeoForge {
    public SporeNexusNeoForge(IEventBus modBus) {
        SporeNexus.init();
        modBus.addListener(SporeNexusNeoForge::registerCapabilities);
    }

    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.Item.BLOCK, BlockEntitiesRegistry.MUSHROOM_HARVESTER_BE.get(),
                (be, side) -> VanillaContainerWrapper.of(be));
        event.registerBlockEntity(Capabilities.Item.BLOCK, BlockEntitiesRegistry.SPORE_NEXUS_CRAFT_BE.get(),
                (be, side) -> VanillaContainerWrapper.of(be));
        event.registerBlockEntity(Capabilities.Item.BLOCK, BlockEntitiesRegistry.MYCELIAN_CORE_BE.get(),
                (be, side) -> VanillaContainerWrapper.of(be));
    }
}
