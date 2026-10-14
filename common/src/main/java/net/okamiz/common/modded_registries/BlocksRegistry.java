package net.okamiz.common.modded_registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.okamiz.SporeNexus;
import net.okamiz.common.blocks.TooltipBlockItem;
import net.okamiz.common.blocks.custom.*;
import net.okamiz.common.records.ResourceMushroomType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class BlocksRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(SporeNexus.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> FUNGALSTEEL_BLOCK = registerBlock("fungalsteel_block", Block::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.METAL), CreativeTabsRegistry.SPORENEXUS_TAB );



    public static final RegistrySupplier<Block> FERTILIZED_MYCELIUM = registerBlockWithTooltip("fertilized_mycelium",
            props -> new SporeSoilBlock(props, 1.25f),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).sound(SoundType.MOSS), CreativeTabsRegistry.SPORENEXUS_TAB,
            "tooltip.block.sporenexus.fertilized_mycelium", TextColor.YELLOW);

    public static final RegistrySupplier<Block> FUNGAL_MYCELIUM = registerBlockWithTooltip("fungal_mycelium",
            props -> new SporeSoilBlock(props, 1.5f),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).sound(SoundType.MOSS), CreativeTabsRegistry.SPORENEXUS_TAB,
            "tooltip.block.sporenexus.fungal_mycelium", TextColor.YELLOW);

    public static final RegistrySupplier<Block> INFERNAL_MYCELIUM = registerBlockWithTooltip("infernal_mycelium",
            props -> new SporeSoilBlock(props, 1.75f),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).sound(SoundType.MOSS), CreativeTabsRegistry.SPORENEXUS_TAB,
            "tooltip.block.sporenexus.infernal_mycelium", TextColor.YELLOW);

    public static final RegistrySupplier<Block> ETHEREAL_MYCELIUM = registerBlockWithTooltip("ethereal_mycelium",
            props -> new SporeSoilBlock(props, 2f),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).sound(SoundType.MOSS), CreativeTabsRegistry.SPORENEXUS_TAB,
            "tooltip.block.sporenexus.ethereal_mycelium", TextColor.YELLOW);


    /* GIANT MUSHROM BLOCKS */

    public static final RegistrySupplier<Block> BANDED_AGARIC_STEM = registerBlock("banded_agaric_stem", HugeMushroomBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MUSHROOM_STEM), CreativeTabsRegistry.SPORENEXUS_TAB);

    public static final RegistrySupplier<Block> BANDED_AGARIC_FLUFFY_STEM = registerBlock("banded_agaric_fluffy_stem", HugeMushroomBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MUSHROOM_STEM), CreativeTabsRegistry.SPORENEXUS_TAB);

    public static final RegistrySupplier<Block> BANDED_AGARIC_BLOCK = registerBlock("banded_agaric_block", HugeMushroomBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK), CreativeTabsRegistry.SPORENEXUS_TAB);



    public static final RegistrySupplier<Block> ETERNAL_LIGHT_MUSHROOM_STEM = registerBlock("eternal_light_mushroom_stem", HugeMushroomBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MUSHROOM_STEM).noOcclusion().lightLevel(state -> 13), CreativeTabsRegistry.SPORENEXUS_TAB);

    public static final RegistrySupplier<Block> ETERNAL_LIGHT_MUSHROOM_BLOCK = registerBlock("eternal_light_mushroom_block", HugeMushroomBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM_BLOCK), CreativeTabsRegistry.SPORENEXUS_TAB);

    /* BLOCK ENTITIES */

    public static final RegistrySupplier<Block> MYCELIAN_CORE = registerBlock("mycelian_core", MycelianCoreBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK)
                    .noOcclusion(), CreativeTabsRegistry.SPORENEXUS_TAB);

    public static final RegistrySupplier<Block> MYCELIAN_CORE_PROXY = registerBlock("mycelian_core_proxy", MycelianCoreProxy::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK)
                    .noOcclusion().noLootTable(), null);


    public static final RegistrySupplier<Block> SPORE_NEXUS_CRAFT_BLOCK = registerBlock("spore_nexus_craft_block", SporeNexusCraftBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK)
                    .noOcclusion(), CreativeTabsRegistry.SPORENEXUS_TAB);


    /* MACHINES */

    public static final RegistrySupplier<Block> GROWTH_ACCELERATOR = registerBlockWithTooltip("growth_accelerator",
            props -> new GrowthAcceleratorBlock(props, 1.25f, 1),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), CreativeTabsRegistry.SPORENEXUS_TAB,
            "tooltip.block.sporenexus.growth_accelerator",TextColor.YELLOW);

    public static final RegistrySupplier<Block> FUNGAL_GROWTH_ACCELERATOR = registerBlockWithTooltip("fungal_growth_accelerator",
            props -> new GrowthAcceleratorBlock(props, 1.5f, 2),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), CreativeTabsRegistry.SPORENEXUS_TAB,
            "tooltip.block.sporenexus.fungal_growth_accelerator",TextColor.YELLOW);

    public static final RegistrySupplier<Block> INFERNAL_GROWTH_ACCELERATOR = registerBlockWithTooltip("infernal_growth_accelerator",
            props -> new GrowthAcceleratorBlock(props, 1.75f, 3),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), CreativeTabsRegistry.SPORENEXUS_TAB,
            "tooltip.block.sporenexus.infernal_growth_accelerator",TextColor.YELLOW);

    public static final RegistrySupplier<Block> ETHEREAL_GROWTH_ACCELERATOR = registerBlockWithTooltip("ethereal_growth_accelerator",
            props -> new GrowthAcceleratorBlock(props, 2, 4),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), CreativeTabsRegistry.SPORENEXUS_TAB,
            "tooltip.block.sporenexus.ethereal_growth_accelerator",TextColor.YELLOW);



    /* BASIC MUSHROOMS */

    public static RegistrySupplier<Block> BANDED_AGARIC = registerBlock("banded_agaric",
            props -> new CustomMushroomBlock(props, "giant_banded_agaric","giant_banded_agaric_2"),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM).noOcclusion(), CreativeTabsRegistry.SPORENEXUS_TAB);

    public static RegistrySupplier<Block> ETERNAL_LIGHT_MUSHROOM = registerBlock("eternal_light_mushroom",
            props -> new CustomMushroomBlock(props, "giant_eternal_light_mushroom_1","giant_eternal_light_mushroom_2","giant_eternal_light_mushroom_3"),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM).noOcclusion().lightLevel(state -> 5), CreativeTabsRegistry.SPORENEXUS_TAB);



    /* RESOURCES MUSHROOMS */
    public static final List<ResourceMushroomType> RESOURCE_MUSHROOM_TYPES = List.of(
            /* ORES */
            new ResourceMushroomType("coal",  ItemsRegistry.fragment("coal"),   ItemsRegistry.FUNGAL_ESSENCE, null, 1200),
            new ResourceMushroomType("copper",ItemsRegistry.fragment("copper"), ItemsRegistry.FUNGAL_ESSENCE, null, 1800),
            new ResourceMushroomType("iron",   ItemsRegistry.fragment("iron"),   ItemsRegistry.FUNGAL_ESSENCE, null, 2600),
            new ResourceMushroomType("fungalsteel",   ItemsRegistry.fragment("fungalsteel"),   ItemsRegistry.FUNGAL_ESSENCE, null, 2600),
            new ResourceMushroomType("quartz",   ItemsRegistry.fragment("quartz"),   ItemsRegistry.FUNGAL_ESSENCE,null, 1800),
            new ResourceMushroomType("amethyst",   ItemsRegistry.fragment("amethyst"),   ItemsRegistry.FUNGAL_ESSENCE,null, 1800),
            new ResourceMushroomType("lapis_lazuli",   ItemsRegistry.fragment("lapis_lazuli"),   ItemsRegistry.FUNGAL_ESSENCE,null, 1800),
            new ResourceMushroomType("redstone",   ItemsRegistry.fragment("redstone"),   ItemsRegistry.FUNGAL_ESSENCE,null, 1800),
            new ResourceMushroomType("gold",   ItemsRegistry.fragment("gold"),   ItemsRegistry.FUNGAL_ESSENCE,null, 2600),
            new ResourceMushroomType("diamond",   ItemsRegistry.fragment("diamond"),   ItemsRegistry.FUNGAL_ESSENCE,null, 4000),
            new ResourceMushroomType("emerald",   ItemsRegistry.fragment("emerald"),   ItemsRegistry.FUNGAL_ESSENCE,null, 4000),
            new ResourceMushroomType("netherite",   ItemsRegistry.fragment("netherite"),   ItemsRegistry.FUNGAL_ESSENCE,null, 6000),


            /* BLOCKS */
            new ResourceMushroomType("ice",ItemsRegistry.fragment("ice"), ItemsRegistry.FUNGAL_ESSENCE, null, 2000),
            new ResourceMushroomType("dirt",ItemsRegistry.fragment("dirt"), ItemsRegistry.FUNGAL_ESSENCE, null, 1800),
            new ResourceMushroomType("sand",ItemsRegistry.fragment("sand"), ItemsRegistry.FUNGAL_ESSENCE, null, 1800),
            new ResourceMushroomType("clay",ItemsRegistry.fragment("clay"), ItemsRegistry.FUNGAL_ESSENCE, null, 1800),
            new ResourceMushroomType("gravel",ItemsRegistry.fragment("gravel"), ItemsRegistry.FUNGAL_ESSENCE, null, 1800),
            new ResourceMushroomType("stone",ItemsRegistry.fragment("stone"), ItemsRegistry.FUNGAL_ESSENCE,
                    ItemsRegistry.fragment("mossy_cobblestone"), 1800),
            new ResourceMushroomType("moss",ItemsRegistry.fragment("moss"), ItemsRegistry.FUNGAL_ESSENCE, null, 1800),
            new ResourceMushroomType("blackstone",ItemsRegistry.fragment("blackstone"), ItemsRegistry.FUNGAL_ESSENCE, null, 1800),
            new ResourceMushroomType("netherrack",ItemsRegistry.fragment("netherrack"), ItemsRegistry.FUNGAL_ESSENCE, null, 1800),
            new ResourceMushroomType("soul_sand",ItemsRegistry.fragment("soul_sand"), ItemsRegistry.FUNGAL_ESSENCE, null, 1800),
            new ResourceMushroomType("glowstone",ItemsRegistry.fragment("glowstone"), ItemsRegistry.FUNGAL_ESSENCE, null, 1800)
    );




    private static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, name);
    }

    public static <T extends Block> RegistrySupplier<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> factory,
                                                                      Supplier<BlockBehaviour.Properties> properties, DeferredSupplier<CreativeModeTab> creativeTab) {

        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id(name));
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id(name));

        RegistrySupplier<T> toReturn = BLOCKS.register(name,
                () -> factory.apply(properties.get().setId(blockKey)));

        ItemsRegistry.ITEMS.register(name,
                () -> new BlockItem(toReturn.get(),
                        new Item.Properties().setId(itemKey).arch$tab(creativeTab).useBlockDescriptionPrefix()));

        return toReturn;
    }

    public static <T extends Block> RegistrySupplier<T> registerBlockWithTooltip(String name, Function<BlockBehaviour.Properties, T> factory,
                                                                                 Supplier<BlockBehaviour.Properties> properties, DeferredSupplier<CreativeModeTab> creativeTab,
                                                                                 String tooltip, TextColor color) {
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id(name));
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id(name));
        RegistrySupplier<T> toReturn = BLOCKS.register(name,
                () -> factory.apply(properties.get().setId(blockKey)));

        ItemsRegistry.ITEMS.register(name,
                () -> new TooltipBlockItem(toReturn.get(),
                        new Item.Properties().setId(itemKey).arch$tab(creativeTab).useBlockDescriptionPrefix(), tooltip, color));

        return toReturn;
    }




    public static final Map<String, RegistrySupplier<Block>> RESOURCE_MUSHROOMS = registerResourceMushrooms();

    private static Map<String, RegistrySupplier<Block>> registerResourceMushrooms() {
        Map<String, RegistrySupplier<Block>> map = new LinkedHashMap<>();
        for (ResourceMushroomType type : RESOURCE_MUSHROOM_TYPES) {
            map.put(type.ore(), registerBlock(type.blockName(), props -> new ResourceMushroomBlock(props, type.drop(), type.essenceDrop(), type.extraDrop(),type.growthTicks()),
                    () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).sound(SoundType.WART_BLOCK), CreativeTabsRegistry.MUSHROOMS_TAB));
        }
        return map;
    }
}
