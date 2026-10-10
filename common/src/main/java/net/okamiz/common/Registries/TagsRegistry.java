package net.okamiz.common.Registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.okamiz.SporeNexus;

public class TagsRegistry {

    public static final TagKey<Item> MUSHROOMS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, "mushrooms"));


}
