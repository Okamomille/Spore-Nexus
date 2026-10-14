package net.okamiz.common.compats.jade.providers;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.okamiz.SporeNexus;
import net.okamiz.common.blocks.custom.GrowthAcceleratorBlock;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class GrowthAcceleratorProvider implements IBlockComponentProvider {
    public static final GrowthAcceleratorProvider INSTANCE = new GrowthAcceleratorProvider();

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (!(accessor.getBlock() instanceof GrowthAcceleratorBlock block)) {
            return;
        }

        int side = block.getRangeOfAction() * 2 + 1;
        tooltip.add(Component.translatable("jade.sporenexus.accelerator_range", side, side, side));

        switch (block.getStatus(accessor.getLevel(), accessor.getPosition())) {
            case ACTIVE -> tooltip.add(Component.translatable("jade.sporenexus.accelerator_active").withColor(0x55FF55));
            case NO_MUSHROOM -> tooltip.add(Component.translatable("jade.sporenexus.accelerator_no_mushroom").withColor(0xFF5555));
            case OVERRIDDEN -> tooltip.add(Component.translatable("jade.sporenexus.accelerator_overridden").withColor(0xFFAA00));
        }
    }

    @Override
    public Identifier getUid() {
        return Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, "growth_accelerator");
    }
}
