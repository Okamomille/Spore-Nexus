package net.okamiz.common.records;

import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public record ResourceMushroomType(String ore, Supplier<Item> drop, Supplier<Item> essenceDrop, Supplier<Item> extraDrop, int growthTicks) {
    public String blockName() {
        return ore + "_resource_mushroom";
}
}
