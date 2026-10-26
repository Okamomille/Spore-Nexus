package net.okamiz.common.blocks.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.okamiz.SporeNexus;

import java.util.*;

public class CustomMushroomBlock extends VegetationBlock implements BonemealableBlock {

    private static final VoxelShape SHAPE = Block.column((double)11.0F, (double)0.0F, (double)11.0F);

    private final List<ResourceLocation> giantIds;

    public CustomMushroomBlock(Properties properties, String... ids) {
        super(properties);
        this.giantIds = Arrays.stream(ids).map(id -> ResourceLocation.fromNamespaceAndPath(SporeNexus.MOD_ID, id)).toList();
    }

    @Override
    protected boolean mayPlaceOn(final BlockState state, final BlockGetter level, final BlockPos pos) {
        return state.isSolidRender();
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    private boolean tryGrow(ServerLevel level, RandomSource random, BlockPos pos, ResourceLocation id) {
        Optional<StructureTemplate> optional = level.getStructureTemplateManager().get(id);
        if (optional.isEmpty()) {
            return false;
        }
        StructureTemplate template = optional.get();
        Vec3i size = template.getSize();
        BlockPos origin = pos.offset(-size.getX() / 2, 0, -size.getZ() / 2);

        for (BlockPos p : BlockPos.betweenClosed(origin, origin.offset(size.getX() - 1, size.getY() - 1, size.getZ() - 1))) {
            if (!p.equals(pos) && !level.getBlockState(p).canBeReplaced()) {
                return false;
            }
        }

        level.removeBlock(pos, false);
        template.placeInWorld(level, origin, origin, new StructurePlaceSettings(), random, 2);
        return true;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, BonemealSource source) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source) {
        return random.nextFloat() < 0.4F;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source) {
        List<ResourceLocation> candidates = new ArrayList<>(giantIds);
        Collections.shuffle(candidates, new Random(random.nextLong()));

        for (ResourceLocation id : candidates) {
            if (tryGrow(level, random, pos, id)) {
                return;
            }
        }
    }
}
