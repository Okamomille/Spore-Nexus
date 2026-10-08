package net.okamiz.common.blocks.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.okamiz.common.blocks.MultiblockProxyBlock;
import net.okamiz.common.blocks.entity.custom.MycelianCoreBlockEntity;
import org.jetbrains.annotations.NotNull;

public class MycelianCoreProxy extends Block implements MultiblockProxyBlock {

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    public MycelianCoreProxy(Properties properties) {
        super(properties);

        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected @NotNull MapCodec<? extends Block> codec() {
        return simpleCodec(MycelianCoreProxy::new);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
    }

    @Override
    protected @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer && level.getBlockEntity(getMainPos(pos)) instanceof MycelianCoreBlockEntity core) {
            MycelianCoreBlock.openMenu(serverPlayer, core, getMainPos(pos));
        }
        return InteractionResult.SUCCESS;
    }


    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide()) {
            BlockPos mainPos = getMainPos(pos);

            if (!MycelianCoreBlock.isCleaningUpMain(mainPos)
                    && level.getBlockState(mainPos).getBlock() instanceof MycelianCoreBlock) {

                if (level.getBlockEntity(mainPos) instanceof MycelianCoreBlockEntity core) {
                    core.drops();
                }

                level.destroyBlock(mainPos, !player.isCreative());
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    /**
     * Runs for every removal of the proxy, not just player breaks: explosions, {@code /setblock}, other
     * mods. {@link #playerWillDestroy} only covers the player case, so without this the proxy could
     * disappear and leave the Machine standing.
     */
    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean movedByPiston) {
        BlockPos mainPos = getMainPos(pos);

        if (!MycelianCoreBlock.isCleaningUpMain(mainPos) && level.getBlockState(mainPos).getBlock() instanceof MycelianCoreBlock) {
            level.destroyBlock(mainPos, true);
        }

        super.affectNeighborsAfterRemoval(state, level, pos, movedByPiston);
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getMachineTopBox(state);
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getMachineTopBox(state);
    }

    @Override
    protected @NotNull VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return getMachineTopBox(state);
    }

    @Override
    protected @NotNull VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected @NotNull VoxelShape getOcclusionShape(BlockState state) {
        return Shapes.empty();
    }


    protected VoxelShape getMachineTopBox(BlockState state) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);
    }

    public static BlockPos getMainPos(BlockPos proxyPos) {
        return proxyPos.below();
    }

    @Override
    public BlockPos getControllerPos(BlockPos proxyPos, BlockState proxyState) {
        return getMainPos(proxyPos);
    }
}
