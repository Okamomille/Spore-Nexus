package net.okamiz.neoforge.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import net.okamiz.SporeNexus;
import net.okamiz.common.modded_registries.BlocksRegistry;
import net.okamiz.common.modded_registries.ItemsRegistry;
import net.okamiz.common.modded_registries.TagsRegistry;

import java.util.concurrent.CompletableFuture;

public class DatagenItemsTagsProvider extends ItemTagsProvider {
    // 1.21.1: the item tags provider needs the block tags (for copy()) and the ExistingFileHelper.
    public DatagenItemsTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                    CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, SporeNexus.MOD_ID, existingFileHelper);
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
        // No spear in 1.21.1 (spears were added by Minecraft 1.21.11).


    }
}
