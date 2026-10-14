package net.okamiz.common.Registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.okamiz.SporeNexus;
import net.okamiz.common.records.FragmentType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class ItemsRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(SporeNexus.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> FUNGALSTEEL_INGOT = registerItem("fungalsteel_ingot", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> FUNGALSTEEL_NUGGET = registerItem("fungalsteel_nugget", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> FUNGAL_POWDER = registerItem("fungal_powder", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));

    public static final RegistrySupplier<Item> MUSHROOM_SPORES = registerItem("mushroom_spores", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));


    public static final RegistrySupplier<Item> FUNGALSTEEL_SWORD = registerItem("fungalsteel_sword", Item::new,
            () -> new Item.Properties().sword(ToolTiersRegistry.FUNGALSTEEL, 3, -2.4f)
                    .arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> FUNGALSTEEL_PICKAXE = registerItem("fungalsteel_pickaxe", Item::new,
            () -> new Item.Properties().pickaxe(ToolTiersRegistry.FUNGALSTEEL, 1, -2.8f)
                    .arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> FUNGALSTEEL_AXE = registerItem("fungalsteel_axe",
            props -> new AxeItem(ToolTiersRegistry.FUNGALSTEEL, 6.0f, -3.2f, props),
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> FUNGALSTEEL_SHOVEL = registerItem("fungalsteel_shovel",
            props -> new ShovelItem(ToolTiersRegistry.FUNGALSTEEL, 1.5f, -3.0f, props),
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> FUNGALSTEEL_HOE = registerItem("fungalsteel_hoe",
            props -> new HoeItem(ToolTiersRegistry.FUNGALSTEEL, 0f, -3.0f, props),
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> FUNGALSTEEL_SPEAR = registerItem("fungalsteel_spear", Item::new,
            () -> new Item.Properties().spear(ToolTiersRegistry.FUNGALSTEEL, 0.95f,0.7f,0.7f,3.5f,
                            13f,8.5f,5.1f,13.37f,4.67f)
                    .arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));






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
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));

    public static final RegistrySupplier<Item> ETHEREAL_FUNGUS = registerItem("ethereal_fungus", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));

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
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> ETHEREAL_ESSENCE = registerItem("ethereal_essence", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));

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
            new FragmentType("redstone",     () -> Items.REDSTONE,                      4),
            new FragmentType("amethyst",     () -> Items.AMETHYST_SHARD,                      4),


            /* BLOCKS */

            new FragmentType("ice",     () -> Items.ICE,                      2),
            new FragmentType("dirt",     () -> Items.DIRT,                      2),
            new FragmentType("sand",     () -> Items.SAND,                      2),
            new FragmentType("clay",     () -> Items.CLAY,                      2),
            new FragmentType("gravel",     () -> Items.GRAVEL,                      2),
            new FragmentType("stone",     () -> Items.STONE,                      2),
            new FragmentType("mossy_cobblestone",     () -> Items.MOSSY_COBBLESTONE,                      2),
            new FragmentType("moss",     () -> Items.MOSS_BLOCK,                      4),
            new FragmentType("blackstone",     () -> Items.BLACKSTONE,                      2),
            new FragmentType("netherrack",     () -> Items.NETHERRACK,                      2),
            new FragmentType("soul_sand",     () -> Items.SOUL_SAND,                      2),
            new FragmentType("glowstone",     () -> Items.GLOWSTONE,                      2)
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
            map.put(type.name(), registerItem(type.itemName(), Item::new, () -> new Item.Properties().arch$tab(CreativeTabsRegistry.FRAGMENTS_TAB)));
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