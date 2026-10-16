package net.okamiz.common.blocks.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.okamiz.common.blocks.entity.custom.MushroomHarvesterBlockEntity;
import net.okamiz.common.modded_registries.BlockEntitiesRegistry;
import org.jetbrains.annotations.Nullable;

public class MushroomHarvesterBlock extends BaseEntityBlock {
    public static final int MAX_RADIUS = 4;
    public static final MapCodec<MushroomHarvesterBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            propertiesCodec(),
            Codec.INT.fieldOf("radius").forGetter(MushroomHarvesterBlock::getRadius)
    ).apply(instance, MushroomHarvesterBlock::new));

    private final int radius;

    public MushroomHarvesterBlock(Properties properties, int radius) {
        super(properties);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MushroomHarvesterBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof MushroomHarvesterBlockEntity harvester) {
            player.openMenu(harvester);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (level.getBlockEntity(pos) instanceof MushroomHarvesterBlockEntity harvester) {
            harvester.drops();
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    private static boolean isBetter(int radiusA, BlockPos posA, int radiusB, BlockPos posB) {
        if (radiusA != radiusB) {
            return radiusA > radiusB;
        }
        return posA.compareTo(posB) < 0;
    }

    private boolean covers(BlockPos self, BlockPos target) {
        return Math.abs(self.getX() - target.getX()) <= radius && Math.abs(self.getZ() - target.getZ()) <= radius && Math.abs(self.getY() - target.getY()) <= 1;
    }

    /** True if this harvester is the one allowed to harvest this mushroom. */
    public boolean isOwnerOf(Level level, BlockPos myPos, BlockPos mushroomPos) {
        BlockPos min = mushroomPos.offset(-MAX_RADIUS, -1, -MAX_RADIUS);
        BlockPos max = mushroomPos.offset(MAX_RADIUS, 1, MAX_RADIUS);
        if (!GrowthAcceleratorBlock.areChunksLoaded(level, min, max)) {
            return false;
        }
        for (BlockPos p : BlockPos.betweenClosed(min, max)) {
            if (!p.equals(myPos) && level.getBlockState(p).getBlock() instanceof MushroomHarvesterBlock other
                    && other.covers(p, mushroomPos) && isBetter(other.radius, p, radius, myPos)
                    && level.getBlockEntity(p) instanceof MushroomHarvesterBlockEntity otherBe && otherBe.hasFreeSlot()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        }
        return createTickerHelper(type, BlockEntitiesRegistry.MUSHROOM_HARVESTER_BE.get(),
                (level1, pos, state1, entity) -> entity.tick(level1, pos, state1));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
