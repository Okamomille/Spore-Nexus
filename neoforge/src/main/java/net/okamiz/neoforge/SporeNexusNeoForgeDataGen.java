package net.okamiz.neoforge;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.okamiz.SporeNexus;
import net.okamiz.neoforge.datagen.DatagenModelProvider;

@EventBusSubscriber(modid = SporeNexus.MOD_ID)
public class SporeNexusNeoForgeDataGen {
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event){
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();

        generator.addProvider(true, new DatagenModelProvider(packOutput));
    }
}
