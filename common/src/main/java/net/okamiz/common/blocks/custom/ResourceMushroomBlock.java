package net.okamiz.common.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ResourceMushroomBlock extends CropBlock {
    public static final int SUB_STEPS = 3;
    public static final int MAX_AGE = 3 * SUB_STEPS - 1;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, MAX_AGE);
    public Supplier<Item> drop;
    public Supplier<Item> secondaryDrop;

    private final int growthTicks;      // GROWS TICKS PER AGE (*3 to have full time) (2400 ticks = 2 min)
    private static final int BLOCKED_RETRY_TICKS = 100; // LIGHT CHECK RETRY TICKS

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0),
            Block.box(4.0, 0.0, 4.0, 12.0, 8.0, 12.0),
            Block.box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0),
    };

    public ResourceMushroomBlock(Properties properties, Supplier<Item> drop, Supplier<Item> secondaryDrop, int growthTicks) {
        super(properties);
        this.drop = drop;
        this.secondaryDrop = secondaryDrop;
        this.growthTicks = growthTicks;
    }



    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    private int getStage(BlockState state) {
        int age = getAge(state);
        if (age == MAX_AGE) {
            return 2;
        }
        return age < SUB_STEPS ? 0 : 1;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[getStage(state)];
    }
    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[getStage(state)];
    }


    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(Blocks.MYCELIUM);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getLevel().getRawBrightness(context.getClickedPos(), 0) >= 8) {
            return null;
        }
        return super.getStateForPlacement(context);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

        if (state.getValue(AGE) != MAX_AGE) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide()) {
            resetAge(level, pos);
            level.scheduleTick(pos, this, nextDelay(level, pos, level.getRandom()));
            level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        5, 0.25, 0.25, 0.25, 0.0);
            }

            dropAll(level, pos);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        return this.mayPlaceOn(level.getBlockState(below), level, below);
    }


    @Override
    public boolean isValidBonemealTarget(final LevelReader level, final BlockPos pos, final BlockState state) {
        return false;
    }


    private void dropAll(Level level, BlockPos pos) {
        RandomSource random = level.getRandom();
        for (HarvestDrop drop : getHarvestDrops()) {
            if (random.nextInt(100) < drop.chancePercent()) {
                int count = drop.min() + random.nextInt(drop.max() - drop.min() + 1);
                level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        new ItemStack(drop.item().get(), count)));
            }
        }
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return this.asBlock();
    }

    public void resetAge(Level level, BlockPos pos){
        level.setBlock(pos, this.defaultBlockState().setValue(AGE, 0), 3);
    }

    protected int getGrowthDelay(Level level, BlockPos pos) {
        double speed = 1.0;

        /*
        if (level.getBlockState(pos.below()).is(BlocksRegistry.RICH_SOIL.get())) {
            speed *= 1.25;
        }


        if (hasAcceleratorNearby(level, pos)) {
            speed *= 2.0;
        }
        */
        return Math.max(1, (int) (growthTicks / speed));
    }

    private boolean hasAcceleratorNearby(Level level, BlockPos pos) {
        int r = 3;
        /*
        for (BlockPos p : BlockPos.betweenClosed(pos.offset(-r, -r, -r), pos.offset(r, r, r))) {
            if (level.getBlockState(p).is(BlocksRegistry.GROWTH_ACCELERATOR.get())) {
                return true;
            }
        }

         */
        return false;
    }

    private int nextDelay(Level level, BlockPos pos, RandomSource random) {
        int base = getGrowthDelay(level, pos) * 2 / MAX_AGE;
        int jitter = Math.max(1, base / 5);
        return base - jitter / 2 + random.nextInt(jitter);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (!level.isClientSide() && getAge(state) < getMaxAge()) {
            level.scheduleTick(pos, this, nextDelay(level, pos, level.getRandom()));
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int age = getAge(state);
        if (age >= getMaxAge()) {
            return;
        }
        if (level.getRawBrightness(pos, 0) >= 8) {
            // TOO MUCH LIGHT -> WAITING STATE
            level.scheduleTick(pos, this, BLOCKED_RETRY_TICKS);
            return;
        }
        age++;
        level.setBlock(pos, getStateForAge(age), 2);
        if (age < getMaxAge()) {
            level.scheduleTick(pos, this, nextDelay(level, pos, random));
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // SECURITY FOR OLD MUSHROOMS
        if (getAge(state) < getMaxAge()) {
            level.scheduleTick(pos, this, nextDelay(level, pos, random));
        }
    }




    public record HarvestDrop(Supplier<Item> item, int min, int max, int chancePercent) {}

    public List<HarvestDrop> getHarvestDrops() {
        List<HarvestDrop> list = new ArrayList<>();
        if (drop != null) {
            list.add(new HarvestDrop(drop, 1, 3, 100));                                   // fragments
        }
        if (secondaryDrop != null) {
            list.add(new HarvestDrop(secondaryDrop, 1, 1, 45));                           // secondary
        }
        return list;
    }

}
