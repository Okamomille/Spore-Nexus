package net.okamiz.neoforge.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.okamiz.SporeNexus;
import net.okamiz.common.blocks.custom.ResourceMushroomBlock;
import net.okamiz.common.modded_registries.BlocksRegistry;
import net.okamiz.common.modded_registries.ItemsRegistry;

import java.util.Optional;

public class DatagenModelProvider extends ModelProvider {
    public DatagenModelProvider(PackOutput output) {
        super(output, SporeNexus.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels){


        /* ITEMS */
        itemModels.generateFlatItem(ItemsRegistry.FUNGALSTEEL_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.FUNGALSTEEL_NUGGET.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemsRegistry.FUNGAL_POWDER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.MUSHROOM_SPORES.get(), ModelTemplates.FLAT_ITEM);


        itemModels.generateFlatItem(ItemsRegistry.FUNGALSTEEL_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateSpear(ItemsRegistry.FUNGALSTEEL_SPEAR.get());
        itemModels.generateFlatItem(ItemsRegistry.FUNGALSTEEL_AXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.FUNGALSTEEL_PICKAXE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.FUNGALSTEEL_SHOVEL.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.FUNGALSTEEL_HOE.get(), ModelTemplates.FLAT_HANDHELD_ITEM);


        itemModels.generateFlatItem(ItemsRegistry.LUMINESCENT_FIBERS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.MINERAL_QUARTZ.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.CONDUCTIVE_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.REINFORCED_FUNGAL_TISSUE.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemsRegistry.NEXUS_FUNGUS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.GREAT_FUNGUS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.INFERNAL_FUNGUS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.ETHEREAL_FUNGUS.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemsRegistry.EMBER_SPORES.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.PRISMATIC_DUST.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.HUMUS_CLUMP.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.MINERAL_FUNGUS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.CONDUCTIVE_FUNGUS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.CRYSTALLIZED_FUNGUS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.GOLDEN_FUNGUS.get(), ModelTemplates.FLAT_ITEM);

        /* ESSENCES */
        itemModels.generateFlatItem(ItemsRegistry.FUNGAL_ESSENCE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.INFERNAL_ESSENCE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemsRegistry.ETHEREAL_ESSENCE.get(), ModelTemplates.FLAT_ITEM);

        /* FRAGMENTS */
        ItemsRegistry.FRAGMENTS.values().forEach(item -> itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM));


        /* BLOCKS */

        createCustomMushroomBlock(blockModels, BlocksRegistry.BANDED_AGARIC_BLOCK.get(),"banded_agaric");
        createCustomMushroomBlock(blockModels, BlocksRegistry.BANDED_AGARIC_FLUFFY_STEM.get(),"banded_agaric");
        createCustomMushroomBlock(blockModels, BlocksRegistry.BANDED_AGARIC_STEM.get(),"banded_agaric");

        createCustomMushroomBlock(blockModels, BlocksRegistry.ETERNAL_LIGHT_MUSHROOM_BLOCK.get(),"eternal_light_mushroom");
        createCustomMushroomBlock(blockModels, BlocksRegistry.ETERNAL_LIGHT_MUSHROOM_STEM.get(),"eternal_light_mushroom");

        blockModels.createTrivialCube(BlocksRegistry.FUNGALSTEEL_BLOCK.get());

        blockModels.createTrivialCube(BlocksRegistry.FERTILIZED_MYCELIUM.get());
        blockModels.createTrivialCube(BlocksRegistry.FUNGAL_MYCELIUM.get());
        blockModels.createTrivialCube(BlocksRegistry.INFERNAL_MYCELIUM.get());
        blockModels.createTrivialCube(BlocksRegistry.ETHEREAL_MYCELIUM.get());

        blockModels.createNonTemplateHorizontalBlock(BlocksRegistry.MYCELIAN_CORE.get());
        blockModels.createNonTemplateHorizontalBlock(BlocksRegistry.MYCELIAN_CORE_PROXY.get());
        blockModels.createNonTemplateModelBlock(BlocksRegistry.SPORE_NEXUS_CRAFT_BLOCK.get());
        blockModels.createTrivialBlock(BlocksRegistry.MUSHROOM_HARVESTER.get(), TexturedModel.CUBE_TOP_BOTTOM);
        blockModels.createTrivialBlock(BlocksRegistry.FUNGAL_HARVESTER.get(), TexturedModel.CUBE_TOP_BOTTOM);
        blockModels.createTrivialBlock(BlocksRegistry.INFERNAL_HARVESTER.get(), TexturedModel.CUBE_TOP_BOTTOM);
        blockModels.createTrivialBlock(BlocksRegistry.ETHEREAL_HARVESTER.get(), TexturedModel.CUBE_TOP_BOTTOM);


        blockModels.createTrivialBlock(BlocksRegistry.GROWTH_ACCELERATOR.get(), TexturedModel.CUBE_TOP_BOTTOM);
        blockModels.createTrivialBlock(BlocksRegistry.FUNGAL_GROWTH_ACCELERATOR.get(), TexturedModel.CUBE_TOP_BOTTOM);
        blockModels.createTrivialBlock(BlocksRegistry.INFERNAL_GROWTH_ACCELERATOR.get(), TexturedModel.CUBE_TOP_BOTTOM);
        blockModels.createTrivialBlock(BlocksRegistry.ETHEREAL_GROWTH_ACCELERATOR.get(), TexturedModel.CUBE_TOP_BOTTOM);


        registerCrossBlockWithCustomItemTexture(blockModels, itemModels, BlocksRegistry.BANDED_AGARIC.get());
        registerCrossBlockWithCustomItemTexture(blockModels, itemModels, BlocksRegistry.ETERNAL_LIGHT_MUSHROOM.get());


        /* MUSHROOMS */
        BlocksRegistry.RESOURCE_MUSHROOMS.forEach((ore, block) -> registerMushroom(blockModels, block.get(), ore));
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

        for (int stage = 0; stage < 3; stage++) {
            ModelTemplate template = new ModelTemplate(
                    Optional.of(id("block/template/mushroom_stage_" + stage)),
                    Optional.of("_stage_" + stage),
                    STEM, CAP, TextureSlot.PARTICLE);

            TextureMapping mapping = new TextureMapping()
                    .put(STEM, tex("block/mushroom/stem"))
                    .put(CAP, tex("block/mushroom/" + ore + "_cap"))
                    .put(TextureSlot.PARTICLE, tex("block/mushroom/stem"));

            models[stage] = template.create(block, mapping, blockModels.modelOutput);
        }

        for (int age = 0; age <= ResourceMushroomBlock.MAX_AGE; age++) {
            int stage = age < 3 ? 0 : age == ResourceMushroomBlock.MAX_AGE ? 2 : 1;
            states.select(age, BlockModelGenerators.plainVariant(models[stage]));
        }

        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(states));
        blockModels.registerSimpleItemModel(block, models[1]);
    }


    public void createCustomMushroomBlock(BlockModelGenerators blockModels, Block block, String id) {
        MultiVariant skin = blockModels.plainVariant(ModelTemplates.SINGLE_FACE.create(block, TextureMapping.defaultTexture(block), blockModels.modelOutput));
        MultiVariant skinless = blockModels.plainVariant(id("block/"+id+"_block_inside"));
        blockModels.blockStateOutput.accept(MultiPartGenerator.multiPart(block).with(blockModels.condition().term(BlockStateProperties.NORTH, true), skin)
                .with(blockModels.condition().term(BlockStateProperties.EAST, true), skin.with(blockModels.Y_ROT_90)
                        .with(blockModels.UV_LOCK)).with(blockModels.condition().term(BlockStateProperties.SOUTH, true),
                        skin.with(blockModels.Y_ROT_180).with(blockModels.UV_LOCK))
                .with(blockModels.condition().term(BlockStateProperties.WEST, true),
                        skin.with(blockModels.Y_ROT_270).with(blockModels.UV_LOCK)).with(blockModels.condition().term(BlockStateProperties.UP, true),
                        skin.with(blockModels.X_ROT_270).with(blockModels.UV_LOCK)).with(blockModels.condition().term(BlockStateProperties.DOWN, true),
                        skin.with(blockModels.X_ROT_90).with(blockModels.UV_LOCK)).with(blockModels.condition().term(BlockStateProperties.NORTH, false), skinless)
                .with(blockModels.condition().term(BlockStateProperties.EAST, false), skinless.with(blockModels.Y_ROT_90)).with(blockModels.condition().term(BlockStateProperties.SOUTH, false),
                        skinless.with(blockModels.Y_ROT_180)).with(blockModels.condition().term(BlockStateProperties.WEST, false), skinless.with(blockModels.Y_ROT_270)).with(blockModels.condition()
                        .term(BlockStateProperties.UP, false), skinless.with(blockModels.X_ROT_270)).with(blockModels.condition().term(BlockStateProperties.DOWN, false),
                        skinless.with(blockModels.X_ROT_90)));
        blockModels.registerSimpleItemModel(block, TexturedModel.CUBE.createWithSuffix(block, "_inventory", blockModels.modelOutput));
    }
}
