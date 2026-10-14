package net.okamiz.neoforge.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.okamiz.SporeNexus;
import net.okamiz.common.Registries.BlocksRegistry;
import net.okamiz.common.Registries.TagsRegistry;

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
                .add(BlocksRegistry.SPORE_NEXUS_CRAFT_BLOCK.getKey())
                .add(BlocksRegistry.GROWTH_ACCELERATOR.getKey())
                .add(BlocksRegistry.FUNGAL_GROWTH_ACCELERATOR.getKey())
                .add(BlocksRegistry.INFERNAL_GROWTH_ACCELERATOR.getKey())
                .add(BlocksRegistry.ETHEREAL_GROWTH_ACCELERATOR.getKey())
        ;

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(BlocksRegistry.BANDED_AGARIC_FLUFFY_STEM.getKey())
                .add(BlocksRegistry.BANDED_AGARIC_STEM.getKey())
                .add(BlocksRegistry.BANDED_AGARIC_BLOCK.getKey())
                .add(BlocksRegistry.ETERNAL_LIGHT_MUSHROOM_STEM.getKey())
                .add(BlocksRegistry.ETERNAL_LIGHT_MUSHROOM_BLOCK.getKey())
        ;

        BlocksRegistry.RESOURCE_MUSHROOMS.values().forEach(block ->tag(BlockTags.MINEABLE_WITH_HOE).add(block.getKey()));

        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(BlocksRegistry.BANDED_AGARIC_FLUFFY_STEM.getKey())
                .add(BlocksRegistry.BANDED_AGARIC_STEM.getKey())
                .add(BlocksRegistry.BANDED_AGARIC_BLOCK.getKey())
                .add(BlocksRegistry.ETERNAL_LIGHT_MUSHROOM_STEM.getKey())
                .add(BlocksRegistry.ETERNAL_LIGHT_MUSHROOM_BLOCK.getKey())

        ;

        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(BlocksRegistry.FERTILIZED_MYCELIUM.getKey())
                .add(BlocksRegistry.FUNGAL_MYCELIUM.getKey())
                .add(BlocksRegistry.INFERNAL_MYCELIUM.getKey())
                .add(BlocksRegistry.ETHEREAL_MYCELIUM.getKey())
        ;



        //tag(TagsRegistry.NEEDS_FUNGALSTEEL_TOOL)
               // .remove(TagsRegistry.);


        tag(TagsRegistry.INCORRECT_FUNGALSTEEL_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
               // .remove(TagsRegistry.NEEDS_FUNGALSTEEL_TOOL)
         ;


    }
}
