package net.okamiz.neoforge;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.okamiz.SporeNexus;
import net.okamiz.common.worldgen.ConfiguredFeatures;
import net.okamiz.common.worldgen.PlacedFeatures;
import net.okamiz.neoforge.datagen.*;
import net.okamiz.neoforge.worldgen.BiomeModifiersNeoForge;

@EventBusSubscriber(modid = SporeNexus.MOD_ID)
public class SporeNexusNeoForgeDataGen {


    @SubscribeEvent
    public static void gatherRegistries(GatherDataRegistryEntriesEvent event) {
        event.add(Registries.FEATURE, ConfiguredFeatures::bootstrap)
                .add(Registries.PLACED_FEATURE, PlacedFeatures::bootstrap)
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, BiomeModifiersNeoForge::bootstrap)
                .recipe(DatagenRecipeProvider::new)
                .lootTable(new LootTableProvider.SubProviderEntry(DatagenBlockLootTableProvider::new, LootContextParamSets.BLOCK));
    }


    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        var lookupProvider = event.getReloadableLookupProvider();

        generator.addProvider(true, new DatagenModelProvider(packOutput));
        generator.addProvider(true, new DatagenBlockTagsProvider(packOutput, lookupProvider));
        generator.addProvider(true, new DatagenItemsTagsProvider(packOutput, lookupProvider));
    }
}