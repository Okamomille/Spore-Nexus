package net.okamiz.common.blocks;

import dev.architectury.event.events.client.ClientTooltipEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;


public class TooltipBlockItem extends BlockItem {
    private final String tooltipKey;
    private final TextColor color;

    public TooltipBlockItem(Block block, Item.Properties properties, String tooltipKey, TextColor color) {
        super(block, properties);
        this.tooltipKey = tooltipKey;
        this.color = color;
    }

    private Component createTooltip() {
        return Component.translatable(tooltipKey).withColor(color);
    }

    public static void registerTooltipEvent() {
        ClientTooltipEvent.ITEM.register((stack, lines, context, flag) -> {
            if (stack.getItem() instanceof TooltipBlockItem item) {lines.add(item.createTooltip());}});
    }
}