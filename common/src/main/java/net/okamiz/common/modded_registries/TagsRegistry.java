package net.okamiz.common.modded_registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.okamiz.SporeNexus;

public class TagsRegistry {

    public static final TagKey<Item> MUSHROOMS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, "mushrooms"));
    public static final TagKey<Item> FUNGUS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, "fungus"));
    public static final TagKey<Item> SUBSTRATS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, "substrats"));
    public static final TagKey<Block> SPORE_SOILS = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, "spore_soils"));
    public static final TagKey<Item> RESOURCE_MUSHROOMS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, "resource_mushrooms"));

    public static final TagKey<Item> FUNGALSTEEL_REPAIRABLE = TagKey.create(Registries.ITEM,
            Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, "fungalsteel_repairable"));
    public static final TagKey<Block> INCORRECT_FUNGALSTEEL_TOOL = TagKey.create(Registries.BLOCK,
            Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, "incorrect_fungalsteel_tool"));
    public static final TagKey<Block> NEEDS_FUNGALSTEEL_TOOL = TagKey.create(Registries.BLOCK,
            Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, "needs_fungalsteel_tool"));
}