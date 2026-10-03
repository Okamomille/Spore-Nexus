package net.okamiz.common.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ResourceMushroomBlock extends CropBlock {
    public static final int MAX_AGE = 2;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 2);

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0),
            Block.box(4.0, 0.0, 4.0, 12.0, 8.0, 12.0),
            Block.box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0),
    };

    public ResourceMushroomBlock(Properties properties) {
        super(properties);
    }



    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 2;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[this.getAge(state)];
    }
    protected int getBonemealAgeIncrease(Level level) {
        return Mth.nextInt(level.getRandom(), 0, 1);
    }
    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[this.getAge(state)];
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getBlock().defaultBlockState().is(Blocks.DIRT);
    }


    @Override
    protected ItemLike getBaseSeedId() {
        return this.asBlock();
    }

    public void resetAge(Level level, BlockPos pos){
        level.setBlock(pos, this.defaultBlockState().setValue(AGE, 0), 3);
    }


    @Override
    protected void randomTick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
        if (level.getRawBrightness(pos, 0) >= 9) {
            int age = this.getAge(state);
            if (age < this.getMaxAge()) {
                float growthSpeed = getGrowthSpeed(this, level, pos);
                if (random.nextInt((int)(25.0F / growthSpeed) + 1) == 0) {
                    level.setBlock(pos, this.getStateForAge(age + 1), 2);
                }
            }
        }

    }


    protected static float getGrowthSpeed(final Block type, final BlockGetter level, final BlockPos pos) {
        float speed = 0.5F;
        BlockPos below = pos.below();

        for(int xx = -1; xx <= 1; ++xx) {
            for(int zz = -1; zz <= 1; ++zz) {
                float blockSpeed = 0.0F;
                BlockState blockState = level.getBlockState(below.offset(xx, 0, zz));
                if (blockState.is(BlockTags.GROWS_CROPS)) {
                    blockSpeed = 1.0F;
                    if ((Integer)blockState.getValueOrElse(FarmlandBlock.MOISTURE, 0) > 0) {
                        blockSpeed = 3.0F;
                    }
                }

                if (xx != 0 || zz != 0) {
                    blockSpeed /= 4.0F;
                }

                speed += blockSpeed;
            }
        }

        BlockPos north = pos.north();
        BlockPos south = pos.south();
        BlockPos west = pos.west();
        BlockPos east = pos.east();
        boolean horizontal = level.getBlockState(west).is(type) || level.getBlockState(east).is(type);
        boolean vertical = level.getBlockState(north).is(type) || level.getBlockState(south).is(type);
        if (horizontal && vertical) {
            speed /= 2.0F;
        } else {
            boolean diagonal = level.getBlockState(west.north()).is(type) || level.getBlockState(east.north()).is(type) || level.getBlockState(east.south()).is(type) || level.getBlockState(west.south()).is(type);
            if (diagonal) {
                speed /= 2.0F;
            }
        }

        return speed;
    }
}
