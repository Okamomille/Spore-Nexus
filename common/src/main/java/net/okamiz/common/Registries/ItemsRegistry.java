package net.okamiz.common.Registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.okamiz.SporeNexus;

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

    /* ORES */
    public static final RegistrySupplier<Item> COAL_FRAGMENTS = registerItem("coal_fragments", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> COPPER_FRAGMENTS = registerItem("copper_fragments", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> DIAMOND_FRAGMENTS = registerItem("diamond_fragments", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> EMERALD_FRAGMENTS = registerItem("emerald_fragments", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> FUNGALSTEEL_FRAGMENTS = registerItem("fungalsteel_fragments", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> GOLD_FRAGMENTS = registerItem("gold_fragments", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> IRON_FRAGMENTS = registerItem("iron_fragments", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> LAPIS_LAZULI_FRAGMENTS = registerItem("lapis_lazuli_fragments", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> NETHERITE_FRAGMENTS = registerItem("netherite_fragments", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> QUARTZ_FRAGMENTS = registerItem("quartz_fragments", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));
    public static final RegistrySupplier<Item> REDSTONE_FRAGMENTS = registerItem("redstone_fragments", Item::new,
            () -> new Item.Properties().arch$tab(CreativeTabsRegistry.SPORENEXUS_TAB));



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
}