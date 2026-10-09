package net.okamiz.neoforge.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.okamiz.SporeNexus;
import net.okamiz.common.blocks.custom.ResourceMushroomBlock;
import net.okamiz.common.Registries.BlocksRegistry;
import net.okamiz.common.Registries.ItemsRegistry;

import java.util.Optional;

public class DatagenModelProvider extends ModelProvider {
    public DatagenModelProvider(PackOutput output) {
        super(output, SporeNexus.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels){


        /* ITEMS */
        itemModels.generateFlatItem(ItemsRegistry.FUNGALSTEEL_INGOT.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemsRegistry.LUMINESCENT_FIBERS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.MINERAL_QUARTZ.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.CONDUCTIVE_INGOT.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemsRegistry.NEXUS_FUNGUS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.MINERAL_FUNGUS.get(), ModelTemplates.FLAT_ITEM);

        /* ESSENCES */
        itemModels.generateFlatItem(ItemsRegistry.FUNGAL_ESSENCE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.INFERNAL_ESSENCE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.ETHEREAL_ESSENCE.get(), ModelTemplates.FLAT_ITEM);

        /* FRAGMENTS */
        /* ORES */
        itemModels.generateFlatItem(ItemsRegistry.COAL_FRAGMENTS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.COPPER_FRAGMENTS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.DIAMOND_FRAGMENTS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.EMERALD_FRAGMENTS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.FUNGALSTEEL_FRAGMENTS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.GOLD_FRAGMENTS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.IRON_FRAGMENTS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.LAPIS_LAZULI_FRAGMENTS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.NETHERITE_FRAGMENTS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.QUARTZ_FRAGMENTS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.REDSTONE_FRAGMENTS.get(), ModelTemplates.FLAT_ITEM);



        /* BLOCKS */
        blockModels.createTrivialCube(BlocksRegistry.FUNGALSTEEL_BLOCK.get());

        blockModels.createNonTemplateHorizontalBlock(BlocksRegistry.MYCELIAN_CORE.get());
        blockModels.createNonTemplateHorizontalBlock(BlocksRegistry.MYCELIAN_CORE_PROXY.get());



        registerCrossBlockWithCustomItemTexture(blockModels, itemModels, BlocksRegistry.BANDED_AGARIC.get());
        registerCrossBlockWithCustomItemTexture(blockModels, itemModels, BlocksRegistry.ETERNAL_LIGHT_MUSHROOM.get());


        /* MUSHROOMS */
        registerMushroom(blockModels, BlocksRegistry.COAL_RESOURCE_MUSHROOM.get(), "coal");
        registerMushroom(blockModels, BlocksRegistry.COPPER_RESOURCE_MUSHROOM.get(), "copper");
        registerMushroom(blockModels, BlocksRegistry.DIAMOND_RESOURCE_MUSHROOM.get(), "diamond");
        registerMushroom(blockModels, BlocksRegistry.EMERALD_RESOURCE_MUSHROOM.get(), "emerald");
        registerMushroom(blockModels, BlocksRegistry.FUNGALSTEEL_RESOURCE_MUSHROOM.get(), "fungalsteel");
        registerMushroom(blockModels, BlocksRegistry.IRON_RESOURCE_MUSHROOM.get(), "iron");
        registerMushroom(blockModels, BlocksRegistry.GOLD_RESOURCE_MUSHROOM.get(), "gold");
        registerMushroom(blockModels, BlocksRegistry.LAPIS_LAZULI_RESOURCE_MUSHROOM.get(), "lapis_lazuli");
        registerMushroom(blockModels, BlocksRegistry.NETHERITE_RESOURCE_MUSHROOM.get(), "netherite");
        registerMushroom(blockModels, BlocksRegistry.QUARTZ_RESOURCE_MUSHROOM.get(), "quartz");
        registerMushroom(blockModels, BlocksRegistry.REDSTONE_RESOURCE_MUSHROOM.get(), "redstone");
    }


    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, path);
    }
    private static Material tex(String path) {
        return new Material(id(path));
    }



    private void registerCrossBlockWithCustomItemTexture(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block block) {
        Identifier blockId = BuiltInRegistries.BLOCK.getKey(block);

        // BLOCK MODEL
        TextureMapping mapping = new TextureMapping().put(TextureSlot.CROSS, tex("block/" + blockId.getPath()))
                .put(TextureSlot.PARTICLE, tex("block/" + blockId.getPath()));

        ModelTemplates.CROSS.create(block, mapping, blockModels.modelOutput);

        // BLOCKSTATE
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, BlockModelGenerators.plainVariant(id("block/" + blockId.getPath()))));

        // ITEM
        itemModels.generateFlatItem(block.asItem(), ModelTemplates.FLAT_ITEM);
    }









    private static final TextureSlot STEM = TextureSlot.create("stem");
    private static final TextureSlot CAP = TextureSlot.create("cap");


    private void registerMushroom(BlockModelGenerators blockModels, Block block, String ore) {
        var states = PropertyDispatch.initial(ResourceMushroomBlock.AGE);
        Identifier[] models = new Identifier[3];

        for (int age = 0; age < 3; age++) {

            ModelTemplate template = new ModelTemplate(
                    Optional.of(id("block/template/mushroom_stage_" + age)),
                    Optional.of("_stage_" + age),
                    STEM, CAP, TextureSlot.PARTICLE);


            TextureMapping mapping = new TextureMapping()
                    .put(STEM, tex("block/mushroom/stem"))
                    .put(CAP, tex("block/mushroom/" + ore + "_cap"))
                    .put(TextureSlot.PARTICLE, tex("block/mushroom/stem"));

            models[age] = template.create(block, mapping, blockModels.modelOutput);
            states.select(age, BlockModelGenerators.plainVariant(models[age]));
        }

        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(states));
        blockModels.registerSimpleItemModel(block, models[1]);
    }
}
