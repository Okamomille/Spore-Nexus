package net.okamiz.common.blocks.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.okamiz.SporeNexus;

import java.util.Optional;

public class CustomMushroomBlock extends VegetationBlock implements BonemealableBlock {

    private static final VoxelShape SHAPE = Block.column((double)11.0F, (double)0.0F, (double)11.0F);

    private Identifier giantId = Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, "giant_banded_agaric");

    public CustomMushroomBlock(Properties properties, String id) {
        super(properties);

        giantId = Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, id);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return random.nextFloat() < 0.4F;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        Optional<StructureTemplate> optional = level.getStructureManager().get(giantId);
        if (optional.isEmpty()) {
            return;
        }
        StructureTemplate template = optional.get();
        Vec3i size = template.getSize();

        BlockPos origin = pos.offset(-size.getX() / 2, 0, -size.getZ() / 2);

        for (BlockPos p : BlockPos.betweenClosed(origin, origin.offset(size.getX() - 1, size.getY() - 1, size.getZ() - 1))) {
            if (!p.equals(pos) && !level.getBlockState(p).canBeReplaced()) {
                return;
            }
        }

        level.removeBlock(pos, false);
        template.placeInWorld(level, origin, origin, new StructurePlaceSettings(), random, 2);
    }

    @Override
    protected MapCodec<? extends VegetationBlock> codec() {
        return null;
    }
}
