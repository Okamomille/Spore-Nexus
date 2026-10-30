package net.okamiz.neoforge.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.okamiz.SporeNexus;
import net.okamiz.common.modded_registries.ItemsRegistry;


public class DatagenItemModelProvider extends ItemModelProvider {

    public DatagenItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SporeNexus.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        flat(ItemsRegistry.FUNGALSTEEL_INGOT.get());
        flat(ItemsRegistry.FUNGALSTEEL_NUGGET.get());

        flat(ItemsRegistry.FUNGAL_POWDER.get());
        flat(ItemsRegistry.MUSHROOM_SPORES.get());

        handheldItem(ItemsRegistry.FUNGALSTEEL_SWORD.get());
        handheldItem(ItemsRegistry.FUNGALSTEEL_AXE.get());
        handheldItem(ItemsRegistry.FUNGALSTEEL_PICKAXE.get());
        handheldItem(ItemsRegistry.FUNGALSTEEL_SHOVEL.get());
        handheldItem(ItemsRegistry.FUNGALSTEEL_HOE.get());

        flat(ItemsRegistry.LUMINESCENT_FIBERS.get());
        flat(ItemsRegistry.MINERAL_QUARTZ.get());
        flat(ItemsRegistry.CONDUCTIVE_INGOT.get());
        flat(ItemsRegistry.REINFORCED_FUNGAL_TISSUE.get());

        flat(ItemsRegistry.NEXUS_FUNGUS.get());
        flat(ItemsRegistry.GREAT_FUNGUS.get());
        flat(ItemsRegistry.INFERNAL_FUNGUS.get());
        flat(ItemsRegistry.ETHEREAL_FUNGUS.get());

        flat(ItemsRegistry.EMBER_SPORES.get());
        flat(ItemsRegistry.PRISMATIC_DUST.get());
        flat(ItemsRegistry.HUMUS_CLUMP.get());
        flat(ItemsRegistry.MINERAL_FUNGUS.get());
        flat(ItemsRegistry.CONDUCTIVE_FUNGUS.get());
        flat(ItemsRegistry.CRYSTALLIZED_FUNGUS.get());
        flat(ItemsRegistry.GOLDEN_FUNGUS.get());

        /* ESSENCES */
        flat(ItemsRegistry.FUNGAL_ESSENCE.get());
        flat(ItemsRegistry.INFERNAL_ESSENCE.get());
        flat(ItemsRegistry.ETHEREAL_ESSENCE.get());

        /* FRAGMENTS */
        ItemsRegistry.FRAGMENTS.values().forEach(item -> flat(item.get()));
    }

    private void flat(Item item) {
        basicItem(item);
    }
}
