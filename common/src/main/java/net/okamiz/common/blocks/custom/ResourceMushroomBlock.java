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
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.okamiz.common.compats.jade.ClientMutationRecipes;
import net.okamiz.common.modded_registries.RecipesRegistry;
import net.okamiz.common.modded_registries.TagsRegistry;
import net.okamiz.common.recipe.mushroom_mutation.MushroomMutationRecipe;
import net.okamiz.common.recipe.mushroom_mutation.MushroomMutationRecipeInput;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ResourceMushroomBlock extends CropBlock {
    public static final int SUB_STEPS = 3;
    public static final int MAX_AGE = 3 * SUB_STEPS - 1;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, MAX_AGE);
    public Supplier<Item> drop;
    public Supplier<Item> essenceDrop;
    public Supplier<Item> extraDrop;
    private static final int MUTATION_RADIUS = 2;

    private final int growthTicks;      // GROWS TICKS PER AGE (*3 to have full time) (2400 ticks = 2 min)
    private static final int BLOCKED_RETRY_TICKS = 100; // LIGHT CHECK RETRY TICKS

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0),
            Block.box(4.0, 0.0, 4.0, 12.0, 8.0, 12.0),
            Block.box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0),
    };

    public enum MutationStatus { NONE, READY, TOO_BRIGHT, NO_SPACE }
    public record MutationInfo(MutationStatus status, @Nullable Block result, float chance) {
        public static final MutationInfo NONE = new MutationInfo(MutationStatus.NONE, null, 0f);
    }

    public ResourceMushroomBlock(Properties properties, Supplier<Item> drop, Supplier<Item> essenceDrop, Supplier<Item> extraDrop, int growthTicks) {
        super(properties);
        this.drop = drop;
        this.essenceDrop = essenceDrop;
        this.growthTicks = growthTicks;
        this.extraDrop = extraDrop;
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
        return state.is(TagsRegistry.SPORE_SOILS);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (isBlockedByLight(context.getLevel(), context.getClickedPos())) {
            return null;
        }
        return super.getStateForPlacement(context);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

        if (state.getValue(AGE) != MAX_AGE) {
            return InteractionResult.PASS;
        }

        if (level instanceof ServerLevel serverLevel) {
            for (ItemStack stack : harvest(serverLevel, pos)) {
                level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack));
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        return this.mayPlaceOn(level.getBlockState(below), level, below);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, BonemealSource source) {
        return false;
    }

    /** Reset the mushroom and return what it gave. Server side only. */
    public List<ItemStack> harvest(ServerLevel level, BlockPos pos) {
        resetAge(level, pos);
        level.scheduleTick(pos, this, nextDelay(level, pos, level.getRandom()));
        level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                5, 0.25, 0.25, 0.25, 0.0);
        return rollDrops(level.getRandom());
    }

    public List<ItemStack> rollDrops(RandomSource random) {
        List<ItemStack> result = new ArrayList<>();
        for (HarvestDrop drop : getHarvestDrops()) {
            if (random.nextInt(100) < drop.chancePercent()) {
                int count = drop.min() + random.nextInt(drop.max() - drop.min() + 1);
                ItemStack stack = new ItemStack(drop.item().get(), count);
                if (!stack.isEmpty()) {
                    result.add(stack);
                }
            }
        }
        return result;
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        List<ItemStack> drops = super.getDrops(state, params);
        return drops.isEmpty() ? List.of(new ItemStack(this)) : drops;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return this.asBlock();
    }

    public void resetAge(Level level, BlockPos pos){
        level.setBlock(pos, this.defaultBlockState().setValue(AGE, 0), 3);
    }

    public double getSoilMultiplier(Level level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        if (below.getBlock() instanceof SporeSoilBlock sporeSoilBlock) {
            return sporeSoilBlock.getSpeedMultiplier();
        }
        return 1.0;
    }

    public double getTotalSpeed(Level level, BlockPos pos) {
        double speed = getSoilMultiplier(level, pos);
        GrowthAcceleratorBlock.Best best = GrowthAcceleratorBlock.findBestFor(level, pos);
        if (best != null) {
            speed *= best.block().getSpeedMultiplier();
        }
        return speed;
    }

    protected int getGrowthDelay(Level level, BlockPos pos) {
        return Math.max(1, (int) (growthTicks / getTotalSpeed(level, pos)));
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

    public MutationInfo getMutationInfo(Level level, BlockPos pos) {
        MutationInfo best = MutationInfo.NONE;
        for (BlockPos other : BlockPos.betweenClosed(pos.offset(-MUTATION_RADIUS, 0, -MUTATION_RADIUS),
                pos.offset(MUTATION_RADIUS, 0, MUTATION_RADIUS))) {
            if (other.equals(pos) || !level.hasChunk(other.getX(), other.getZ())) {
                continue;
            }
            if (!(level.getBlockState(other).getBlock() instanceof ResourceMushroomBlock otherMushroom)) {
                continue;
            }

            MushroomMutationRecipeInput input = new MushroomMutationRecipeInput(new ItemStack(this), new ItemStack(otherMushroom));
            var recipe = ClientMutationRecipes.find(input, level);
            if (recipe.isEmpty()) {
                continue;
            }
            if (!(recipe.get().output().create().getItem() instanceof BlockItem blockItem)) {
                continue;
            }

            MutationStatus status;
            if (findMutationSpot(level, pos, other, true) != null) {
                status = MutationStatus.READY;
            } else if (findMutationSpot(level, pos, other, false) != null) {
                status = MutationStatus.TOO_BRIGHT;
            } else {
                status = MutationStatus.NO_SPACE;
            }
            if (best.status() == MutationStatus.NONE || status.ordinal() < best.status().ordinal()) {
                best = new MutationInfo(status, blockItem.getBlock(), recipe.get().chance());
            }
        }
        return best;
    }

    private void tryMutate(ServerLevel level, BlockPos pos, RandomSource random) {
        for (BlockPos other : BlockPos.betweenClosed(pos.offset(-MUTATION_RADIUS, 0, -MUTATION_RADIUS),
                pos.offset(MUTATION_RADIUS, 0, MUTATION_RADIUS))) {
            if (other.equals(pos) || !level.isLoaded(other)) {
                continue;
            }

            BlockState otherState = level.getBlockState(other);
            if (!(otherState.getBlock() instanceof ResourceMushroomBlock otherMushroom) || otherMushroom.getAge(otherState) < otherMushroom.getMaxAge()) {
                continue;
            }

            MushroomMutationRecipeInput input = new MushroomMutationRecipeInput(new ItemStack(this), new ItemStack(otherMushroom));
            var holder = level.recipeAccess().getRecipeFor(RecipesRegistry.MUSHROOM_MUTATION_RECIPE_TYPE.get(), input, level);
            if (holder.isEmpty()){
                continue;
            }

            MushroomMutationRecipe recipe = holder.get().value();
            ItemStack result = recipe.assemble(input);
            if (!(result.getItem() instanceof BlockItem blockItem)){
                continue;
            }

            BlockPos spot = findMutationSpot(level, pos, other, true);
            if (spot == null){
                continue;
            }

            if (random.nextFloat() >= recipe.chance()){
                continue;
            }

            level.setBlock(spot, blockItem.getBlock().defaultBlockState(), 3);
            level.sendParticles(ParticleTypes.MYCELIUM, spot.getX() + 0.5, spot.getY() + 0.4, spot.getZ() + 0.5,
                    12, 0.3, 0.2, 0.3, 0.0);

            return;
        }
    }

    private @Nullable BlockPos findMutationSpot(LevelReader level, BlockPos a, BlockPos b, boolean checkLight) {
        for (BlockPos q : BlockPos.betweenClosed(a.offset(-1, 0, -1), a.offset(1, 0, 1))) {
            if (q.equals(a) || q.equals(b)){
                continue;
            }
            if (Math.abs(q.getX() - b.getX()) > 1 || Math.abs(q.getZ() - b.getZ()) > 1){
                continue;
            }
            if (!level.getBlockState(q).isAir()){
                continue;
            }
            if (!level.getBlockState(q.below()).is(TagsRegistry.SPORE_SOILS)){
                continue;
            }
            if (checkLight && level.getRawBrightness(q, 0) > 7) {
                continue;
            }
            return q.immutable();
        }
        return null;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int age = getAge(state);
        if (age >= getMaxAge()) {
            return;
        }
        if (isBlockedByLight(level, pos)) {
            // TOO MUCH LIGHT -> WAITING STATE
            level.scheduleTick(pos, this, BLOCKED_RETRY_TICKS);
            return;
        }
        age++;
        level.setBlock(pos, getStateForAge(age), 2);
        if (age < getMaxAge()) {
            level.scheduleTick(pos, this, nextDelay(level, pos, random));
        } else {
            tryMutate(level, pos, random);
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // SECURITY FOR OLD MUSHROOMS
        if (getAge(state) < getMaxAge()) {
            level.scheduleTick(pos, this, nextDelay(level, pos, random));
        }
    }

    public int getEstimatedRemainingTicks(Level level, BlockPos pos, BlockState state) {
        int stepsLeft = MAX_AGE - getAge(state);
        int perStep = getGrowthDelay(level, pos) * 2 / MAX_AGE;
        return stepsLeft * perStep;
    }

    public boolean isBlockedByLight(Level level, BlockPos pos) {
        return level.getRawBrightness(pos, 0) >= 11;
    }



    public record HarvestDrop(Supplier<Item> item, int min, int max, int chancePercent) {}

    public List<HarvestDrop> getHarvestDrops() {
        List<HarvestDrop> list = new ArrayList<>();
        if (drop != null) {
            list.add(new HarvestDrop(drop, 1, 3, 100));                                   // fragments
        }
        if (essenceDrop != null) {
            list.add(new HarvestDrop(essenceDrop, 1, 2, 45));                           // essence
        }
        if (extraDrop != null) {
            list.add(new HarvestDrop(extraDrop, 1, 1, 15));                           // extra
        }
        return list;
    }

}
