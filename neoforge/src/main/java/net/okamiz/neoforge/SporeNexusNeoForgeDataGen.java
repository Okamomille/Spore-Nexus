package net.okamiz.neoforge;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.okamiz.SporeNexus;
import net.okamiz.neoforge.datagen.*;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@EventBusSubscriber(modid = SporeNexus.MOD_ID)
public class SporeNexusNeoForgeDataGen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        /* ASSETS */
        generator.addProvider(event.includeClient(), new DatagenBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new DatagenItemModelProvider(packOutput, existingFileHelper));

        /* DATA */
        BlockTagsProvider blockTags = new DatagenBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new DatagenItemsTagsProvider(packOutput, lookupProvider,
                blockTags.contentsGetter(), existingFileHelper));

        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(DatagenBlockLootTableProvider::new, LootContextParamSets.BLOCK)),
                lookupProvider));

        generator.addProvider(event.includeServer(), new DatagenRecipeProvider(packOutput, lookupProvider));

        // Worldgen (configured / placed features) and NeoForge biome modifiers
        generator.addProvider(event.includeServer(), new DatapackProvider(packOutput, lookupProvider));
    }
}
