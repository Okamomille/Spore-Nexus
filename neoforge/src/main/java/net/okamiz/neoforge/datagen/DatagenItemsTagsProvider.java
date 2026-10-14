package net.okamiz.neoforge.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.okamiz.SporeNexus;
import net.okamiz.common.modded_registries.BlocksRegistry;
import net.okamiz.common.modded_registries.ItemsRegistry;
import net.okamiz.common.modded_registries.TagsRegistry;

import java.util.concurrent.CompletableFuture;

public class DatagenItemsTagsProvider extends ItemTagsProvider {
    public DatagenItemsTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, SporeNexus.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(TagsRegistry.FUNGALSTEEL_REPAIRABLE)
                .add(ItemsRegistry.FUNGALSTEEL_INGOT.getKey())
        ;

        tag(ItemTags.SWORDS)
                .add(ItemsRegistry.FUNGALSTEEL_SWORD.getKey())
        ;
        tag(ItemTags.PICKAXES)
                .add(ItemsRegistry.FUNGALSTEEL_PICKAXE.getKey())
        ;
        tag(ItemTags.AXES)
                .add(ItemsRegistry.FUNGALSTEEL_AXE.getKey())
        ;
        tag(ItemTags.SHOVELS)
                .add(ItemsRegistry.FUNGALSTEEL_SHOVEL.getKey())
        ;
        tag(ItemTags.HOES)
                .add(ItemsRegistry.FUNGALSTEEL_HOE.getKey())
        ;
        tag(ItemTags.SPEARS)
                .add(ItemsRegistry.FUNGALSTEEL_SPEAR.getKey())
        ;


    }
}
