package net.okamiz.common.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GrowthAcceleratorBlock extends Block {

    private final float speedMultiplier;
    private final int range;
    public enum Status { ACTIVE, NO_MUSHROOM, OVERRIDDEN }
    private static final int MAX_RANGE = 4;

    public record Best(BlockPos pos, GrowthAcceleratorBlock block) {}
    private record Rival(BlockPos pos, int range) {}


    public GrowthAcceleratorBlock(Properties properties, float speedMultiplier, int range) {
        super(properties);
        this.speedMultiplier = speedMultiplier;
        this.range = range;
    }


    public float getSpeedMultiplier(){
        return speedMultiplier;
    }

    public int getRangeOfAction(){
        return range;
    }
    public static boolean isInRange(BlockPos accelerator, int range, BlockPos target) {
        return Math.abs(accelerator.getX() - target.getX()) <= range
                && Math.abs(accelerator.getY() - target.getY()) <= range
                && Math.abs(accelerator.getZ() - target.getZ()) <= range;
    }

    /** A > B -> Faster or Smaller pos. */
    private static boolean isBetter(float multA, BlockPos posA, float multB, BlockPos posB) {
        if (multA != multB) {
            return multA > multB;
        }
        return posA.compareTo(posB) < 0;
    }

    /** Best speed for mushroom or null. */
    public static @Nullable Best findBestFor(Level level, BlockPos mushroomPos) {
        BlockPos min = mushroomPos.offset(-MAX_RANGE, -MAX_RANGE, -MAX_RANGE);
        BlockPos max = mushroomPos.offset(MAX_RANGE, MAX_RANGE, MAX_RANGE);
        if (!areChunksLoaded(level, min, max)) {
            return null;
        }
        Best best = null;
        for (BlockPos p : BlockPos.betweenClosed(min, max)) {
            if (level.getBlockState(p).getBlock() instanceof GrowthAcceleratorBlock acc && isInRange(p, acc.range, mushroomPos)
                    && (best == null || isBetter(acc.speedMultiplier, p, best.block().speedMultiplier, best.pos()))) {
                best = new Best(p.immutable(), acc);
            }
        }
        return best;
    }
    private static boolean areChunksLoaded(Level level, BlockPos min, BlockPos max) {
        for (int cx = min.getX() >> 4; cx <= max.getX() >> 4; cx++) {
            for (int cz = min.getZ() >> 4; cz <= max.getZ() >> 4; cz++) {
                if (!level.hasChunk(cx, cz)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Check for best. */
    private boolean isBestFor(Level level, BlockPos myPos, BlockPos mushroomPos) {
        BlockPos min = mushroomPos.offset(-MAX_RANGE, -MAX_RANGE, -MAX_RANGE);
        BlockPos max = mushroomPos.offset(MAX_RANGE, MAX_RANGE, MAX_RANGE);
        if (!areChunksLoaded(level, min, max)) {
            return false;
        }
        for (BlockPos p : BlockPos.betweenClosed(min, max)) {
            if (!p.equals(myPos)
                    && level.getBlockState(p).getBlock() instanceof GrowthAcceleratorBlock other && isInRange(p, other.range, mushroomPos)
                    && isBetter(other.speedMultiplier, p, speedMultiplier, myPos)) {
                return false;
            }
        }
        return true;
    }

    /** Active if is selected by at least ONE mushroom. FOR JADE. */
    public Status getStatus(Level level, BlockPos pos) {
        int reach = range + MAX_RANGE;
        BlockPos min = pos.offset(-reach, -reach, -reach);
        BlockPos max = pos.offset(reach, reach, reach);
        if (!areChunksLoaded(level, min, max)) {
            return Status.NO_MUSHROOM;
        }

        List<BlockPos> mushrooms = new ArrayList<>();
        List<Rival> rivals = new ArrayList<>();
        for (BlockPos p : BlockPos.betweenClosed(min, max)) {
            Block block = level.getBlockState(p).getBlock();
            if (block instanceof ResourceMushroomBlock && isInRange(pos, range, p)) {
                mushrooms.add(p.immutable());
            }
            else if (block instanceof GrowthAcceleratorBlock other && !p.equals(pos) && isBetter(other.speedMultiplier, p, speedMultiplier, pos)) {
                rivals.add(new Rival(p.immutable(), other.range));
            }
        }

        for (BlockPos mushroom : mushrooms) {
            boolean covered = false;
            for (Rival rival : rivals) {
                if (isInRange(rival.pos(), rival.range(), mushroom)) {
                    covered = true;
                    break;
                }
            }
            if (!covered) {
                return Status.ACTIVE;
            }
        }
        return mushrooms.isEmpty() ? Status.NO_MUSHROOM : Status.OVERRIDDEN;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        int attempts = range * range * 4;
        for (int i = 0; i < attempts; i++) {
            BlockPos target = pos.offset(
                    random.nextInt(range * 2 + 1) - range,
                    random.nextInt(range * 2 + 1) - range,
                    random.nextInt(range * 2 + 1) - range);
            BlockState targetState = level.getBlockState(target);

            if (!(targetState.getBlock() instanceof ResourceMushroomBlock mushroom)
                    || mushroom.getAge(targetState) >= mushroom.getMaxAge()) {
                continue;
            }

            if (!isBestFor(level, pos, target)) {
                continue;
            }

            level.addParticle(ParticleTypes.COMPOSTER,
                    target.getX() + 0.2 + random.nextDouble() * 0.6,
                    target.getY() + 0.3 + random.nextDouble() * 0.4,
                    target.getZ() + 0.2 + random.nextDouble() * 0.6, 0, 0, 0);
        }
    }
}
