package net.okamiz.common.blocks;

import dev.architectury.event.events.client.ClientTooltipEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class TooltipBlockItem extends BlockItem {
    private final String[] tooltipKeys;
    private final TextColor color;
    private final boolean italic;
    private boolean requiresShift = false;

    public TooltipBlockItem(Block block, Properties properties, String tooltipKey, TextColor color) {
        this(block, properties, color, false, tooltipKey);
    }

    public TooltipBlockItem requireShift() {
        this.requiresShift = true;
        return this;
    }

    public TooltipBlockItem(Block block, Properties properties, TextColor color, boolean italic, String... tooltipKeys) {
        super(block, properties);
        this.tooltipKeys = tooltipKeys;
        this.color = color;
        this.italic = italic;
    }

    private List<Component> createTooltips() {
        List<Component> lines = new ArrayList<>();
        for (String key : tooltipKeys) {
            MutableComponent line = Component.translatable(key).withStyle(style -> style.withColor(color));
            if (italic) line = line.withStyle(ChatFormatting.ITALIC);
            lines.add(line);
        }
        return lines;
    }

    public static void registerTooltipEvent() {
        ClientTooltipEvent.ITEM.register((stack, lines, context, flag) -> {
            if (stack.getItem() instanceof TooltipBlockItem item) {
                if (!item.requiresShift || Screen.hasShiftDown()) {
                    lines.addAll(item.createTooltips());
                } else {
                    lines.add(Component.translatable("tooltip.sporenexus.hold_shift")
                            .withStyle(ChatFormatting.DARK_GRAY));
                }
            }
        });
    }
}