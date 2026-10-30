package net.okamiz.common.blocks.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.okamiz.SporeNexus;

import java.util.*;

public class CustomMushroomBlock extends BushBlock implements BonemealableBlock {

    private static final VoxelShape SHAPE = Block.box(2.5, 0.0, 2.5, 13.5, 11.0, 13.5);

    public static final MapCodec<CustomMushroomBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            propertiesCodec(), Codec.STRING.listOf().fieldOf("giants").forGetter(block -> block.rawIds)).apply(instance, (properties, ids) -> new CustomMushroomBlock(properties, ids.toArray(String[]::new))));

    private final List<String> rawIds;
    private final List<ResourceLocation> giantIds;

    public CustomMushroomBlock(Properties properties, String... ids) {
        super(properties);
        this.rawIds = List.of(ids);
        this.giantIds = Arrays.stream(ids).map(id -> ResourceLocation.fromNamespaceAndPath(SporeNexus.MOD_ID, id)).toList();
    }

    @Override
    protected boolean mayPlaceOn(final BlockState state, final BlockGetter level, final BlockPos pos) {
        return state.isSolidRender(level, pos);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    private boolean tryGrow(ServerLevel level, RandomSource random, BlockPos pos, ResourceLocation id) {
        Optional<StructureTemplate> optional = level.getStructureManager().get(id);
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
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return random.nextFloat() < 0.4F;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        List<ResourceLocation> candidates = new ArrayList<>(giantIds);
        Collections.shuffle(candidates, new Random(random.nextLong()));

        for (ResourceLocation id : candidates) {
            if (tryGrow(level, random, pos, id)) {
                return;
            }
        }
    }
}
