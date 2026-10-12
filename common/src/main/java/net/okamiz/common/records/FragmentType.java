package net.okamiz.common.records;

import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public record FragmentType(String name, Supplier<Item> craftOutput, int craftCount) {
    public String itemName() { return name + "_fragments"; }
}
