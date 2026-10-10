package net.okamiz.neoforge;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.okamiz.SporeNexus;
import net.okamiz.neoforge.datagen.*;


import java.util.Collections;
import java.util.List;

@EventBusSubscriber(modid = SporeNexus.MOD_ID)
public class SporeNexusNeoForgeDataGen {
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event){
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();

        var lookupProvider = event.getLookupProvider();



        generator.addProvider(true, new DatagenModelProvider(packOutput));
        generator.addProvider(true, new DatagenBlockTagsProvider(packOutput, lookupProvider));
        generator.addProvider(true, new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(DatagenBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));


        generator.addProvider(true, new DatagenRecipeProvider.Runner(packOutput, lookupProvider));
        generator.addProvider(true, new DatapackProvider(packOutput, lookupProvider));
    }
}
