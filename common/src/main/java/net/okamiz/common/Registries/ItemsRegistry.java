package net.okamiz.common.Registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.okamiz.SporeNexus;
import net.okamiz.common.blocks.custom.ResourceMushroomBlock;
import net.okamiz.common.records.FragmentType;
import net.okamiz.common.records.ResourceMushroomType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class ItemsRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(SporeNexus.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> FUNGALSTEEL_INGOT = registerItem("fungalsteel_ingot", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> FUNGAL_POWDER = registerItem("fungal_powder", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));

    public static final RegistrySupplier<Item> MUSHROOM_SPORES = registerItem("mushroom_spores", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));



    public static final RegistrySupplier<Item> MINERAL_QUARTZ = registerItem("mineral_quartz", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));

    public static final RegistrySupplier<Item> LUMINESCENT_FIBERS = registerItem("luminescent_fibers", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));

    public static final RegistrySupplier<Item> CONDUCTIVE_INGOT = registerItem("conductive_ingot", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> REINFORCED_FUNGAL_TISSUE = registerItem("reinforced_fungal_tissue", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));




    public static final RegistrySupplier<Item> NEXUS_FUNGUS = registerItem("nexus_fungus", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> GREAT_FUNGUS = registerItem("great_fungus", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));

    public static final RegistrySupplier<Item> INFERNAL_FUNGUS = registerItem("infernal_fungus", Item::new,
            () -> new Item.Properties()/*.arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB)*/);

    public static final RegistrySupplier<Item> ETHEREAL_FUNGUS = registerItem("ethereal_fungus", Item::new,
            () -> new Item.Properties()/*.arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB)*/);

    public static final RegistrySupplier<Item> MINERAL_FUNGUS = registerItem("mineral_fungus", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> CONDUCTIVE_FUNGUS = registerItem("conductive_fungus", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> CRYSTALLIZED_FUNGUS = registerItem("crystallized_fungus", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> GOLDEN_FUNGUS = registerItem("golden_fungus", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));



    /* ESSENCES */

    public static final RegistrySupplier<Item> FUNGAL_ESSENCE = registerItem("fungal_essence", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> INFERNAL_ESSENCE = registerItem("infernal_essence", Item::new,
            () -> new Item.Properties()/*.arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB)*/);
    public static final RegistrySupplier<Item> ETHEREAL_ESSENCE = registerItem("ethereal_essence", Item::new,
            () -> new Item.Properties()/*.arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB)*/);

    /* FRAGMENTS */


    public static final List<FragmentType> FRAGMENT_TYPES = List.of(
            /* ORES */
            new FragmentType("coal",         () -> Items.COAL,                          1),
            new FragmentType("copper",       () -> Items.COPPER_INGOT,                  1),
            new FragmentType("diamond",      () -> Items.DIAMOND,                       1),
            new FragmentType("emerald",      () -> Items.EMERALD,                       1),
            new FragmentType("fungalsteel",  () -> ItemsRegistry.FUNGALSTEEL_INGOT.get(), 1),
            new FragmentType("gold",         () -> Items.GOLD_INGOT,                    1),
            new FragmentType("iron",         () -> Items.IRON_INGOT,                    1),
            new FragmentType("lapis_lazuli", () -> Items.LAPIS_LAZULI,                  4),
            new FragmentType("netherite",    () -> Items.NETHERITE_INGOT,               1),
            new FragmentType("quartz",       () -> Items.QUARTZ,                        1),
            new FragmentType("redstone",     () -> Items.REDSTONE,                      4)
    );


    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, name);
    }

    public static <T extends Item> RegistrySupplier<T> registerItem(
            String name,
            Function<Item.Properties, T> factory,
            Supplier<Item.Properties> properties) {

        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id(name));

        return ITEMS.register(name,
                () -> factory.apply(properties.get().setId(itemKey)));
    }

    public static final Map<String, RegistrySupplier<Item>> FRAGMENTS = registerFragments();

    private static Map<String, RegistrySupplier<Item>> registerFragments() {
        Map<String, RegistrySupplier<Item>> map = new LinkedHashMap<>();
        for (FragmentType type : FRAGMENT_TYPES) {
            map.put(type.name(), registerItem(type.itemName(), Item::new, () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB)));
        }
        return map;
    }

    public static RegistrySupplier<Item> fragment(String name) {
        RegistrySupplier<Item> fragment = FRAGMENTS.get(name);
        if (fragment == null) {
            throw new IllegalArgumentException("UNKNOWN Fragment : " + name);
        }
        return fragment;
    }
}