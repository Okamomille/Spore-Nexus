package net.okamiz.neoforge.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.okamiz.SporeNexus;
import net.okamiz.common.modded_registries.BlocksRegistry;
import net.okamiz.common.modded_registries.TagsRegistry;

import java.util.concurrent.CompletableFuture;

public class DatagenBlockTagsProvider extends BlockTagsProvider {
    public DatagenBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, SporeNexus.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(BlockTags.OVERRIDES_MUSHROOM_LIGHT_REQUIREMENT)
                .add(BlocksRegistry.FERTILIZED_MYCELIUM.get())
                .add(BlocksRegistry.FUNGAL_MYCELIUM.get())
                .add(BlocksRegistry.INFERNAL_MYCELIUM.get())
                .add(BlocksRegistry.ETHEREAL_MYCELIUM.get())
        ;


        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(BlocksRegistry.FUNGALSTEEL_BLOCK.get())
                .add(BlocksRegistry.MYCELIAN_CORE.get())
                .add(BlocksRegistry.MYCELIAN_CORE_PROXY.get())
                .add(BlocksRegistry.SPORE_NEXUS_CRAFT_BLOCK.get())
                .add(BlocksRegistry.GROWTH_ACCELERATOR.get())
                .add(BlocksRegistry.FUNGAL_GROWTH_ACCELERATOR.get())
                .add(BlocksRegistry.INFERNAL_GROWTH_ACCELERATOR.get())
                .add(BlocksRegistry.ETHEREAL_GROWTH_ACCELERATOR.get())
                .add(BlocksRegistry.MUSHROOM_HARVESTER.get())
                .add(BlocksRegistry.FUNGAL_HARVESTER.get())
                .add(BlocksRegistry.INFERNAL_HARVESTER.get())
                .add(BlocksRegistry.ETHEREAL_HARVESTER.get())
        ;

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(BlocksRegistry.BANDED_AGARIC_FLUFFY_STEM.get())
                .add(BlocksRegistry.BANDED_AGARIC_STEM.get())
                .add(BlocksRegistry.BANDED_AGARIC_BLOCK.get())
                .add(BlocksRegistry.ETERNAL_LIGHT_MUSHROOM_STEM.get())
                .add(BlocksRegistry.ETERNAL_LIGHT_MUSHROOM_BLOCK.get())
        ;

        BlocksRegistry.RESOURCE_MUSHROOMS.values().forEach(block ->tag(BlockTags.MINEABLE_WITH_HOE).add(block.get()));

        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(BlocksRegistry.BANDED_AGARIC_FLUFFY_STEM.get())
                .add(BlocksRegistry.BANDED_AGARIC_STEM.get())
                .add(BlocksRegistry.BANDED_AGARIC_BLOCK.get())
                .add(BlocksRegistry.ETERNAL_LIGHT_MUSHROOM_STEM.get())
                .add(BlocksRegistry.ETERNAL_LIGHT_MUSHROOM_BLOCK.get())

        ;

        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(BlocksRegistry.FERTILIZED_MYCELIUM.get())
                .add(BlocksRegistry.FUNGAL_MYCELIUM.get())
                .add(BlocksRegistry.INFERNAL_MYCELIUM.get())
                .add(BlocksRegistry.ETHEREAL_MYCELIUM.get())
        ;



        //tag(TagsRegistry.NEEDS_FUNGALSTEEL_TOOL)
               // .remove(TagsRegistry.);


        tag(TagsRegistry.INCORRECT_FUNGALSTEEL_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
               // .remove(TagsRegistry.NEEDS_FUNGALSTEEL_TOOL)
         ;


    }
}
