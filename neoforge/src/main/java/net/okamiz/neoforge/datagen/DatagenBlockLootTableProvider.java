package net.okamiz.neoforge.datagen;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.okamiz.common.modded_registries.BlocksRegistry;

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
        dropSelf(BlocksRegistry.FERTILIZED_MYCELIUM.get());
        dropSelf(BlocksRegistry.FUNGAL_MYCELIUM.get());
        dropSelf(BlocksRegistry.INFERNAL_MYCELIUM.get());
        dropSelf(BlocksRegistry.ETHEREAL_MYCELIUM.get());


        dropSelf(BlocksRegistry.BANDED_AGARIC.get());
        dropSelf(BlocksRegistry.ETERNAL_LIGHT_MUSHROOM.get());

        add(BlocksRegistry.BANDED_AGARIC_STEM.get(),
                createMushroomBlockDrop(BlocksRegistry.BANDED_AGARIC_STEM.get(), BlocksRegistry.BANDED_AGARIC.get().asItem()));
        add(BlocksRegistry.BANDED_AGARIC_BLOCK.get(),
                createMushroomBlockDrop(BlocksRegistry.BANDED_AGARIC_BLOCK.get(), BlocksRegistry.BANDED_AGARIC.get().asItem()));
        add(BlocksRegistry.BANDED_AGARIC_FLUFFY_STEM.get(),
                createMushroomBlockDrop(BlocksRegistry.BANDED_AGARIC_FLUFFY_STEM.get(), BlocksRegistry.BANDED_AGARIC.get().asItem()));

        add(BlocksRegistry.ETERNAL_LIGHT_MUSHROOM_STEM.get(),
                createMushroomBlockDrop(BlocksRegistry.ETERNAL_LIGHT_MUSHROOM_STEM.get(), BlocksRegistry.ETERNAL_LIGHT_MUSHROOM.get().asItem()));
        add(BlocksRegistry.ETERNAL_LIGHT_MUSHROOM_BLOCK.get(),
                createMushroomBlockDrop(BlocksRegistry.ETERNAL_LIGHT_MUSHROOM_BLOCK.get(), BlocksRegistry.ETERNAL_LIGHT_MUSHROOM.get().asItem()));

        BlocksRegistry.RESOURCE_MUSHROOMS.values().forEach(block -> dropSelf(block.get()));

        dropSelf(BlocksRegistry.MYCELIAN_CORE.get());
        dropSelf(BlocksRegistry.SPORE_NEXUS_CRAFT_BLOCK.get());
        dropSelf(BlocksRegistry.GROWTH_ACCELERATOR.get());
        dropSelf(BlocksRegistry.FUNGAL_GROWTH_ACCELERATOR.get());
        dropSelf(BlocksRegistry.INFERNAL_GROWTH_ACCELERATOR.get());
        dropSelf(BlocksRegistry.ETHEREAL_GROWTH_ACCELERATOR.get());
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
