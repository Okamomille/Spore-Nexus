package net.okamiz.common.blocks.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CustomMushroomBlock extends VegetationBlock {

    private static final VoxelShape SHAPE = Block.column((double)11.0F, (double)0.0F, (double)11.0F);
    public static final MapCodec<CustomMushroomBlock> CODEC = simpleCodec(CustomMushroomBlock::new);

    public CustomMushroomBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends VegetationBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    protected void randomTick(final BlockState state, final ServerLevel level, BlockPos pos, final RandomSource random) {
        if (random.nextInt(25) == 0) {
            int max = 5;
            int r = 4;

            for(BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-4, -1, -4), pos.offset(4, 1, 4))) {
                if (level.getBlockState(blockPos).is(this)) {
                    --max;
                    if (max <= 0) {
                        return;
                    }
                }
            }

            BlockPos offset = pos.offset(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);

            for(int i = 0; i < 4; ++i) {
                if (level.isEmptyBlock(offset) && state.canSurvive(level, offset)) {
                    pos = offset;
                }

                offset = pos.offset(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
            }

            if (level.isEmptyBlock(offset) && state.canSurvive(level, offset)) {
                level.setBlock(offset, state, 2);
            }
        }

    }

    protected boolean mayPlaceOn(final BlockState state, final BlockGetter level, final BlockPos pos) {
        return state.isSolidRender();
    }

    protected boolean canSurvive(final BlockState state, final LevelReader level, final BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState below = level.getBlockState(belowPos);
        if (below.is(BlockTags.OVERRIDES_MUSHROOM_LIGHT_REQUIREMENT)) {
            return true;
        } else {
            return level.getRawBrightness(pos, 0) < 13 && this.mayPlaceOn(below, level, belowPos);
        }
    }
}
