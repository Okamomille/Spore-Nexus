package net.okamiz.common.compats.jade.providers;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.okamiz.SporeNexus;
import net.okamiz.common.blocks.custom.ResourceMushroomBlock;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class MushroomGrowthProvider implements IBlockComponentProvider {
    public static final MushroomGrowthProvider INSTANCE = new MushroomGrowthProvider();

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (!(accessor.getBlock() instanceof ResourceMushroomBlock mushroom)) {
            return;
        }
        BlockState state = accessor.getBlockState();
        Level level = accessor.getLevel();
        BlockPos pos = accessor.getPosition();

        if (state.getValue(ResourceMushroomBlock.AGE) >= ResourceMushroomBlock.MAX_AGE) {
            tooltip.add(Component.translatable("jade.sporenexus.mature").withColor(TextColor.YELLOW));
        } else if (mushroom.isBlockedByLight(level, pos)) {
            tooltip.add(Component.translatable("jade.sporenexus.too_bright").withColor(TextColor.RED));
        } else {
            int seconds = mushroom.getEstimatedRemainingTicks(level, pos, state) / 20;
            tooltip.add(Component.translatable("jade.sporenexus.growing_time",
                    String.format("%d:%02d", seconds / 60, seconds % 60)).withColor(TextColor.YELLOW));
        }
    }

    @Override
    public Identifier getUid() {
        return Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, "mushroom_growth");
    }
}
