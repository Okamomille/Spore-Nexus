package net.okamiz.neoforge.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.okamiz.SporeNexus;
import net.okamiz.common.blocks.custom.ResourceMushroomBlock;
import net.okamiz.common.modded_registries.BlocksRegistry;


public class DatagenBlockStateProvider extends BlockStateProvider {

    public DatagenBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SporeNexus.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        /* HUGE MUSHROOM BLOCKS */
        hugeMushroomBlock(BlocksRegistry.BANDED_AGARIC_BLOCK.get(), "banded_agaric");
        hugeMushroomBlock(BlocksRegistry.BANDED_AGARIC_FLUFFY_STEM.get(), "banded_agaric");
        hugeMushroomBlock(BlocksRegistry.BANDED_AGARIC_STEM.get(), "banded_agaric");

        hugeMushroomBlock(BlocksRegistry.ETERNAL_LIGHT_MUSHROOM_BLOCK.get(), "eternal_light_mushroom");
        hugeMushroomBlock(BlocksRegistry.ETERNAL_LIGHT_MUSHROOM_STEM.get(), "eternal_light_mushroom");

        /* SIMPLE CUBES */
        simpleBlockWithItem(BlocksRegistry.FUNGALSTEEL_BLOCK.get(), cubeAll(BlocksRegistry.FUNGALSTEEL_BLOCK.get()));

        simpleBlockWithItem(BlocksRegistry.FERTILIZED_MYCELIUM.get(), cubeAll(BlocksRegistry.FERTILIZED_MYCELIUM.get()));
        simpleBlockWithItem(BlocksRegistry.FUNGAL_MYCELIUM.get(), cubeAll(BlocksRegistry.FUNGAL_MYCELIUM.get()));
        simpleBlockWithItem(BlocksRegistry.INFERNAL_MYCELIUM.get(), cubeAll(BlocksRegistry.INFERNAL_MYCELIUM.get()));
        simpleBlockWithItem(BlocksRegistry.ETHEREAL_MYCELIUM.get(), cubeAll(BlocksRegistry.ETHEREAL_MYCELIUM.get()));

        simpleBlockWithItem(BlocksRegistry.FUNGAL_LIGHT.get(), cubeAll(BlocksRegistry.FUNGAL_LIGHT.get()));

        /* MACHINES (hand made Blockbench models) */
        horizontalWithExistingModel(BlocksRegistry.MYCELIAN_CORE.get());
        horizontalWithExistingModel(BlocksRegistry.MYCELIAN_CORE_PROXY.get());
        ModelFile altar = existing(BlocksRegistry.SPORE_NEXUS_CRAFT_BLOCK.get());
        simpleBlockWithItem(BlocksRegistry.SPORE_NEXUS_CRAFT_BLOCK.get(), altar);

        cubeBottomTop(BlocksRegistry.MUSHROOM_HARVESTER.get());
        cubeBottomTop(BlocksRegistry.FUNGAL_HARVESTER.get());
        cubeBottomTop(BlocksRegistry.INFERNAL_HARVESTER.get());
        cubeBottomTop(BlocksRegistry.ETHEREAL_HARVESTER.get());

        cubeBottomTop(BlocksRegistry.GROWTH_ACCELERATOR.get());
        cubeBottomTop(BlocksRegistry.FUNGAL_GROWTH_ACCELERATOR.get());
        cubeBottomTop(BlocksRegistry.INFERNAL_GROWTH_ACCELERATOR.get());
        cubeBottomTop(BlocksRegistry.ETHEREAL_GROWTH_ACCELERATOR.get());

        /* SMALL MUSHROOMS (cross block, flat item with its own texture) */
        crossBlock(BlocksRegistry.BANDED_AGARIC.get());
        crossBlock(BlocksRegistry.ETERNAL_LIGHT_MUSHROOM.get());

        /* RESOURCE MUSHROOMS */
        BlocksRegistry.RESOURCE_MUSHROOMS.forEach((ore, block) -> resourceMushroom(block.get(), ore));
    }

    private String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    private ModelFile existing(Block block) {
        return models().getExistingFile(modLoc("block/" + name(block)));
    }

    /** Facing block using a hand made model; north = no rotation, like the 26.x version. */
    private void horizontalWithExistingModel(Block block) {
        ModelFile model = existing(block);
        horizontalBlock(block, model);
        simpleBlockItem(block, model);
    }

    private void cubeBottomTop(Block block) {
        String name = name(block);
        ModelFile model = models().cubeBottomTop(name,
                modLoc("block/" + name + "_side"),
                modLoc("block/" + name + "_bottom"),
                modLoc("block/" + name + "_top"));
        simpleBlockWithItem(block, model);
    }

    private void crossBlock(Block block) {
        String name = name(block);
        simpleBlock(block, models().cross(name, modLoc("block/" + name)));
        // The item uses its own flat texture (textures/item/<name>.png).
        itemModels().basicItem(block.asItem());
    }

    private void resourceMushroom(Block block, String ore) {
        String name = name(block);
        ModelFile[] stages = new ModelFile[3];
        for (int stage = 0; stage < 3; stage++) {
            stages[stage] = models().withExistingParent(name + "_stage_" + stage, modLoc("block/template/mushroom_stage_" + stage))
                    .texture("stem", modLoc("block/mushroom/stem"))
                    .texture("cap", modLoc("block/mushroom/" + ore + "_cap"))
                    .texture("particle", modLoc("block/mushroom/stem"));
        }

        getVariantBuilder(block).forAllStates(state -> {
            int age = state.getValue(ResourceMushroomBlock.AGE);
            int stage = age < ResourceMushroomBlock.SUB_STEPS ? 0 : age == ResourceMushroomBlock.MAX_AGE ? 2 : 1;
            return ConfiguredModel.builder().modelFile(stages[stage]).build();
        });

        itemModels().withExistingParent(name, modLoc("block/" + name + "_stage_1"));
    }

    /**
     * Huge mushroom block (vanilla style multipart): the skin on exposed faces, the "inside"
     * model on hidden faces. The item uses a full cube of the skin texture.
     */
    private void hugeMushroomBlock(Block block, String insideId) {
        String name = name(block);
        ModelFile skin = models().singleTexture(name, mcLoc("block/template_single_face"), "texture", blockTexture(block));
        ModelFile inside = models().getExistingFile(modLoc("block/" + insideId + "_block_inside"));

        MultiPartBlockStateBuilder builder = getMultipartBuilder(block);
        face(builder, BlockStateProperties.NORTH, skin, inside, 0, 0, false);
        face(builder, BlockStateProperties.EAST, skin, inside, 0, 90, true);
        face(builder, BlockStateProperties.SOUTH, skin, inside, 0, 180, true);
        face(builder, BlockStateProperties.WEST, skin, inside, 0, 270, true);
        face(builder, BlockStateProperties.UP, skin, inside, 270, 0, true);
        face(builder, BlockStateProperties.DOWN, skin, inside, 90, 0, true);

        ModelFile inventory = models().cubeAll(name + "_inventory", blockTexture(block));
        simpleBlockItem(block, inventory);
    }

    private void face(MultiPartBlockStateBuilder builder, BooleanProperty side, ModelFile skin, ModelFile inside,
                      int rotX, int rotY, boolean uvLockSkin) {
        builder.part().modelFile(skin).rotationX(rotX).rotationY(rotY).uvLock(uvLockSkin).addModel()
                .condition(side, true).end();
        builder.part().modelFile(inside).rotationX(rotX).rotationY(rotY).addModel()
                .condition(side, false).end();
    }
}
