package net.okamiz.neoforge.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.okamiz.SporeNexus;
import net.okamiz.common.Registries.BlocksRegistry;
import net.okamiz.common.Registries.ItemsRegistry;

public class DatagenModelProvider extends ModelProvider {
    public DatagenModelProvider(PackOutput output) {
        super(output, SporeNexus.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels){


        /* ITEMS */
        itemModels.generateFlatItem(ItemsRegistry.FUNGALSTEEL_INGOT.get(), ModelTemplates.FLAT_ITEM);

        /* ESSENCES */
        itemModels.generateFlatItem(ItemsRegistry.FUNGAL_ESSENCE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.INFERNAL_ESSENCE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.ETHEREAL_ESSENCE.get(), ModelTemplates.FLAT_ITEM);







        /* BLOCKS */
        blockModels.createTrivialCube(BlocksRegistry.FUNGALSTEEL_BLOCK.get());
    }
}
