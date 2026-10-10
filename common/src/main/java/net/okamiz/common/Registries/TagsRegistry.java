package net.okamiz.common.Registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.okamiz.SporeNexus;

public class TagsRegistry {

    public static final TagKey<Item> MUSHROOMS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, "mushrooms"));
    public static final TagKey<Block> SPORE_SOILS = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, "spore_soils"));


}