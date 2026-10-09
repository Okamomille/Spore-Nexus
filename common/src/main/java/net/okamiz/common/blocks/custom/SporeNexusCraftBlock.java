package net.okamiz.common.blocks.custom;

import com.mojang.serialization.MapCodec;
import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.okamiz.common.Registries.BlockEntitiesRegistry;
import net.okamiz.common.blocks.entity.custom.SporeNexusCraftBlockEntity;
import org.jspecify.annotations.Nullable;

public class SporeNexusCraftBlock extends BaseEntityBlock {

    public static final MapCodec<SporeNexusCraftBlock> CODEC = simpleCodec(SporeNexusCraftBlock::new);

    public static final VoxelShape SHAPE = (VoxelShape)Shapes.or(Block.column((double)14.0F, (double)0.0F, (double)8.0F),
            new VoxelShape[]{Block.column((double)10.0F, (double)8.0F, (double)12.0F), Block.column((double)16.0F, (double)12.0F, (double)16.0F)});

    public SporeNexusCraftBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
    }


    /*BLOCK ENTITY*/
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SporeNexusCraftBlockEntity(pos, state);
    }


    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack destroyedWith) {
        if (blockEntity instanceof SporeNexusCraftBlockEntity sporeNexusCraftBlockEntity) {
            sporeNexusCraftBlockEntity.drops();
        }
        super.playerDestroy(level, player, pos, state, blockEntity, destroyedWith);
    }


    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer && level.getBlockEntity(pos) instanceof SporeNexusCraftBlockEntity core) {
            openMenu(serverPlayer, core, pos);
        }
        return InteractionResult.SUCCESS;
    }

    public static void openMenu(ServerPlayer serverPlayer, SporeNexusCraftBlockEntity core, BlockPos pos){
        MenuRegistry.openExtendedMenu(serverPlayer, core, buffer -> buffer.writeBlockPos(pos));
    }


    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        if(level.isClientSide()){
            return null;
        }

        return createTickerHelper(type, BlockEntitiesRegistry.SPORE_NEXUS_CRAFT_BE.get(), (level1, pos, state, entity) ->
                entity.tick(level1, pos, state));
    }
}
