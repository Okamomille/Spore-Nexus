package net.okamiz.neoforge.datagen;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.okamiz.common.Registries.BlocksRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DatagenBlockLootTableProvider extends BlockLootSubProvider {
    public DatagenBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(BlocksRegistry.FUNGALSTEEL_BLOCK.get());


        dropSelf(BlocksRegistry.BANDED_AGARIC.get());
        dropSelf(BlocksRegistry.ETERNAL_LIGHT_MUSHROOM.get());


        dropSelf(BlocksRegistry.COAL_RESOURCE_MUSHROOM.get());
        dropSelf(BlocksRegistry.COPPER_RESOURCE_MUSHROOM.get());
        dropSelf(BlocksRegistry.DIAMOND_RESOURCE_MUSHROOM.get());
        dropSelf(BlocksRegistry.EMERALD_RESOURCE_MUSHROOM.get());
        dropSelf(BlocksRegistry.FUNGALSTEEL_RESOURCE_MUSHROOM.get());
        dropSelf(BlocksRegistry.LAPIS_LAZULI_RESOURCE_MUSHROOM.get());
        dropSelf(BlocksRegistry.IRON_RESOURCE_MUSHROOM.get());
        dropSelf(BlocksRegistry.GOLD_RESOURCE_MUSHROOM.get());
        dropSelf(BlocksRegistry.NETHERITE_RESOURCE_MUSHROOM.get());
        dropSelf(BlocksRegistry.QUARTZ_RESOURCE_MUSHROOM.get());
        dropSelf(BlocksRegistry.REDSTONE_RESOURCE_MUSHROOM.get());

        dropSelf(BlocksRegistry.MYCELIAN_CORE.get());
        dropSelf(BlocksRegistry.SPORE_NEXUS_CRAFT_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (RegistrySupplier<Block> supplier : BlocksRegistry.BLOCKS) {
            blocks.add(supplier.get());
        }
        return blocks;
    }
}
