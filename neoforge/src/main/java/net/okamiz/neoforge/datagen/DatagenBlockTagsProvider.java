package net.okamiz.neoforge.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.okamiz.SporeNexus;
import net.okamiz.common.Registries.BlocksRegistry;

import java.util.concurrent.CompletableFuture;

public class DatagenBlockTagsProvider extends BlockTagsProvider {
    public DatagenBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, SporeNexus.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(BlocksRegistry.FUNGALSTEEL_BLOCK.getKey())
                .add(BlocksRegistry.MYCELIAN_CORE.getKey())
                .add(BlocksRegistry.MYCELIAN_CORE_PROXY.getKey())
                .add(BlocksRegistry.SPORE_NEXUS_CRAFT_BLOCK.getKey());

        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(BlocksRegistry.COAL_RESOURCE_MUSHROOM.getKey())
                .add(BlocksRegistry.COPPER_RESOURCE_MUSHROOM.getKey())
                .add(BlocksRegistry.DIAMOND_RESOURCE_MUSHROOM.getKey())
                .add(BlocksRegistry.EMERALD_RESOURCE_MUSHROOM.getKey())
                .add(BlocksRegistry.FUNGALSTEEL_RESOURCE_MUSHROOM.getKey())
                .add(BlocksRegistry.LAPIS_LAZULI_RESOURCE_MUSHROOM.getKey())
                .add(BlocksRegistry.IRON_RESOURCE_MUSHROOM.getKey())
                .add(BlocksRegistry.GOLD_RESOURCE_MUSHROOM.getKey())
                .add(BlocksRegistry.QUARTZ_RESOURCE_MUSHROOM.getKey())
                .add(BlocksRegistry.REDSTONE_RESOURCE_MUSHROOM.getKey())
                .add(BlocksRegistry.NETHERITE_RESOURCE_MUSHROOM.getKey());
    }
}
