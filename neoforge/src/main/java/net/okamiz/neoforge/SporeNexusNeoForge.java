package net.okamiz.neoforge;

import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;
import net.okamiz.SporeNexus;
import net.okamiz.common.modded_registries.BlockEntitiesRegistry;
import org.jetbrains.annotations.Nullable;

@Mod(SporeNexus.MOD_ID)
public final class SporeNexusNeoForge {
    public SporeNexusNeoForge(IEventBus modBus) {
        SporeNexus.init();
        modBus.addListener(SporeNexusNeoForge::registerCapabilities);
    }


    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntitiesRegistry.MUSHROOM_HARVESTER_BE.get(),
                SporeNexusNeoForge::wrap);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntitiesRegistry.SPORE_NEXUS_CRAFT_BE.get(),
                SporeNexusNeoForge::wrap);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntitiesRegistry.MYCELIAN_CORE_BE.get(),
                SporeNexusNeoForge::wrap);
    }

    /** Sided access respects the WorldlyContainer rules, a null side gives the whole inventory. */
    private static IItemHandler wrap(WorldlyContainer container, @Nullable Direction side) {
        return side == null ? new InvWrapper(container) : new SidedInvWrapper(container, side);
    }
}
