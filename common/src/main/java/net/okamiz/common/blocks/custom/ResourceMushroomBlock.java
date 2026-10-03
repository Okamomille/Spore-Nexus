package net.okamiz.common.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.okamiz.common.Registries.ItemsRegistry;

import java.util.Random;
import java.util.function.Supplier;

public class ResourceMushroomBlock extends CropBlock {
    public static final int MAX_AGE = 2;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 2);
    public Supplier<Item> drop;
    public Supplier<Item> secondaryDrop;
    ItemStack fragmentsDrop;
    ItemStack fungalEssenceDrop;

    private static final int GROWTH_CHANCE = 15; //1/15 per randomTick

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0),
            Block.box(4.0, 0.0, 4.0, 12.0, 8.0, 12.0),
            Block.box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0),
    };

    public ResourceMushroomBlock(Properties properties, Supplier<Item> drop, Supplier<Item> secondaryDrop) {
        super(properties);
        this.drop = drop;
        this.secondaryDrop = secondaryDrop;
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
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        this.fragmentsDrop = new ItemStack(drop.get());
        this.fungalEssenceDrop = new ItemStack(ItemsRegistry.FUNGAL_ESSENCE.get());
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {


        if (state.getValue(AGE) != MAX_AGE) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide()) {
            resetAge(level, pos);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        5, 0.25, 0.25, 0.25, 0.0);
            }

            int fragDropCount = level.getRandom().nextInt(3);
            for (int i = 0; i <= fragDropCount; i++) {
                dropFragments(level, pos);
            }
            drop(level, pos);
        }

        return InteractionResult.SUCCESS;
    }

    private void drop(Level level, BlockPos pos){
        Random random = new Random();
        int fungalEssenceDropRate = random.nextInt(2);
        int secondaryDropRate = random.nextInt(3);
        int mushroomCapDropRate = random.nextInt(3);
        int mushroomSporesDropRate = random.nextInt(5);
        int sporeRelicDropRate = random.nextInt(100);

        if(fungalEssenceDropRate == 0){
            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), fungalEssenceDrop));
        }
        if(secondaryDropRate == 0){
            if(secondaryDrop != null){
                level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(secondaryDrop.get())));
            }
        }

        /* EXTRA DROPS
        if(mushroomCapDropRate == 0){
            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), mushroomCapDrop));
        }
        if(mushroomSporesDropRate == 0){
            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), mushroomSporesDrop));
        }

        if(sporeRelicDropRate <= 1){
            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), relicDrop));
        }

         */


    }



    private void dropFragments(Level level, BlockPos pos){
        if(drop != null){
            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), fragmentsDrop));

        }
    }


    @Override
    protected ItemLike getBaseSeedId() {
        return this.asBlock();
    }

    public void resetAge(Level level, BlockPos pos){
        level.setBlock(pos, this.defaultBlockState().setValue(AGE, 0), 3);
    }



    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int age = this.getAge(state);
        if (age < this.getMaxAge() && random.nextInt(getGrowthChance(level, pos)) == 0) {
            level.setBlock(pos, this.getStateForAge(age + 1), 2);
        }
    }


    protected int getGrowthChance(Level level, BlockPos pos){
        return GROWTH_CHANCE;
    }
}
