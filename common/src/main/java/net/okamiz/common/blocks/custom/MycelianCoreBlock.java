package net.okamiz.common.blocks.custom;

import com.mojang.serialization.MapCodec;
import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.okamiz.common.Registries.BlockEntitiesRegistry;
import net.okamiz.common.Registries.BlocksRegistry;
import net.okamiz.common.blocks.entity.custom.MycelianCoreBlockEntity;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class MycelianCoreBlock extends BaseEntityBlock {

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    public static final MapCodec<MycelianCoreBlock> CODEC = simpleCodec(MycelianCoreBlock::new);
    private static final Set<BlockPos> CLEANING_UP_MAINS = new HashSet<>();

    public MycelianCoreBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }


    /* FACING */


    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        BlockPos proxyPos = pos.above();
        Level level = context.getLevel();

        if (level.isOutsideBuildHeight(proxyPos)) {
            return null;
        }

        if (!level.getBlockState(proxyPos).canBeReplaced(context)) {
            return null;
        }

        Direction facing = context.getHorizontalDirection().getOpposite();

        return defaultBlockState().setValue(FACING, facing);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        if (!level.isClientSide()) {
            BlockPos proxyPos = pos.above();

            System.out.println("========== MYCELIAN CORE ==========");
            System.out.println("CORE  : " + pos);
            System.out.println("PROXY : " + proxyPos);
            System.out.println("BLOCK AT PROXY BEFORE : " + level.getBlockState(proxyPos));

            level.setBlock(proxyPos,
                    BlocksRegistry.MYCELIAN_CORE_PROXY.get()
                            .defaultBlockState()
                            .setValue(MycelianCoreProxy.FACING, state.getValue(FACING)),
                    3);

            System.out.println("BLOCK AT PROXY AFTER  : " + level.getBlockState(proxyPos));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    /*BLOCK ENTITY*/
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MycelianCoreBlockEntity(pos, state);
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean movedByPiston) {
        removeProxyBlock(level, pos);

        level.updateNeighbourForOutputSignal(pos, this);
        state.updateNeighbourShapes(level, pos, UPDATE_NEIGHBORS);

        super.affectNeighborsAfterRemoval(state, level, pos, movedByPiston);
    }

    static boolean isCleaningUpMain(BlockPos mainPos) {
        return CLEANING_UP_MAINS.contains(mainPos);
    }

    /**
     * Removes the proxy if it still points back at {@code mainPos}. Guarded by
     * {@link #CLEANING_UP_MAINS} so the proxy removed here doesn't try to tear the blender down again.
     */
    static void removeProxyBlock(Level level, BlockPos mainPos) {
        BlockPos immutableMain = mainPos.immutable();
        CLEANING_UP_MAINS.add(immutableMain);

        try {
            BlockPos proxyPos = immutableMain.above();
            BlockState proxyState = level.getBlockState(proxyPos);

            if (proxyState.getBlock() instanceof MycelianCoreProxy
                    && MycelianCoreProxy.getMainPos(proxyPos).equals(immutableMain)) {
                level.removeBlock(proxyPos, false);
            }
        } finally {
            CLEANING_UP_MAINS.remove(immutableMain);
        }
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack destroyedWith) {
        if (blockEntity instanceof MycelianCoreBlockEntity mycelianCoreBlockEntity) {
            mycelianCoreBlockEntity.drops();
        }
        removeProxyBlock(level, pos);

        super.playerDestroy(level, player, pos, state, blockEntity, destroyedWith);
    }


    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer && level.getBlockEntity(pos) instanceof MycelianCoreBlockEntity core) {
            openMenu(serverPlayer, core, pos);
        }
        return InteractionResult.SUCCESS;
    }

    public static void openMenu(ServerPlayer serverPlayer, MycelianCoreBlockEntity core, BlockPos pos){
        MenuRegistry.openExtendedMenu(serverPlayer, core, buffer -> buffer.writeBlockPos(pos));
    }


    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        if(level.isClientSide()){
            return null;
        }

        return createTickerHelper(type, BlockEntitiesRegistry.MYCELIAN_CORE_BE.get(), (level1, pos, state, entity) ->
                entity.tick(level1, pos, state));
    }
}
