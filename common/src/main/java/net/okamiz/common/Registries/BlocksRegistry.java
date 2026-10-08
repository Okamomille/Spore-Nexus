package net.okamiz.common.Registries;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.okamiz.SporeNexus;
import net.okamiz.common.blocks.custom.MycelianCoreBlock;
import net.okamiz.common.blocks.custom.MycelianCoreProxy;
import net.okamiz.common.blocks.custom.ResourceMushroomBlock;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlocksRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(SporeNexus.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> FUNGALSTEEL_BLOCK = registerBlock("fungalsteel_block", Block::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.METAL), CreativeTabsRegistry.SPORENEXUS_TAB );

    /* BLOCK ENTITES */

    public static final RegistrySupplier<Block> MYCELIAN_CORE = registerBlock("mycelian_core", MycelianCoreBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK)
                    .noOcclusion(), CreativeTabsRegistry.SPORENEXUS_TAB);

    public static final RegistrySupplier<Block> MYCELIAN_CORE_PROXY = registerBlock("mycelian_core_proxy", MycelianCoreProxy::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK)
                    .noOcclusion().noLootTable(), null);

    /* RESOURCES MUSHROOMS */


    public static final RegistrySupplier<Block> COAL_RESOURCE_MUSHROOM = registerBlock("coal_resource_mushroom",
            props -> new ResourceMushroomBlock(props, ItemsRegistry.COAL_FRAGMENTS, ItemsRegistry.FUNGAL_ESSENCE, 2),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).sound(SoundType.WART_BLOCK), CreativeTabsRegistry.SPORENEXUS_TAB);

    public static final RegistrySupplier<Block> COPPER_RESOURCE_MUSHROOM = registerBlock("copper_resource_mushroom",
            props -> new ResourceMushroomBlock(props, ItemsRegistry.COPPER_FRAGMENTS, ItemsRegistry.FUNGAL_ESSENCE, 4),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).sound(SoundType.WART_BLOCK), CreativeTabsRegistry.SPORENEXUS_TAB);

    public static final RegistrySupplier<Block> DIAMOND_RESOURCE_MUSHROOM = registerBlock("diamond_resource_mushroom",
            props -> new ResourceMushroomBlock(props, ItemsRegistry.DIAMOND_FRAGMENTS, ItemsRegistry.FUNGAL_ESSENCE, 11),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).sound(SoundType.WART_BLOCK), CreativeTabsRegistry.SPORENEXUS_TAB);

    public static final RegistrySupplier<Block> EMERALD_RESOURCE_MUSHROOM = registerBlock("emerald_resource_mushroom",
            props -> new ResourceMushroomBlock(props, ItemsRegistry.EMERALD_FRAGMENTS, ItemsRegistry.FUNGAL_ESSENCE, 11),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).sound(SoundType.WART_BLOCK), CreativeTabsRegistry.SPORENEXUS_TAB);

    public static final RegistrySupplier<Block> FUNGALSTEEL_RESOURCE_MUSHROOM = registerBlock("fungalsteel_resource_mushroom",
            props -> new ResourceMushroomBlock(props, ItemsRegistry.FUNGALSTEEL_FRAGMENTS, ItemsRegistry.FUNGAL_ESSENCE, 7),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).sound(SoundType.WART_BLOCK), CreativeTabsRegistry.SPORENEXUS_TAB);

    public static final RegistrySupplier<Block> GOLD_RESOURCE_MUSHROOM = registerBlock("gold_resource_mushroom",
            props -> new ResourceMushroomBlock(props, ItemsRegistry.GOLD_FRAGMENTS, ItemsRegistry.FUNGAL_ESSENCE, 7),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).sound(SoundType.WART_BLOCK), CreativeTabsRegistry.SPORENEXUS_TAB);

    public static final RegistrySupplier<Block> IRON_RESOURCE_MUSHROOM = registerBlock("iron_resource_mushroom",
            props -> new ResourceMushroomBlock(props, ItemsRegistry.IRON_FRAGMENTS, ItemsRegistry.FUNGAL_ESSENCE, 7),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).sound(SoundType.WART_BLOCK), CreativeTabsRegistry.SPORENEXUS_TAB);

    public static final RegistrySupplier<Block> LAPIS_LAZULI_RESOURCE_MUSHROOM = registerBlock("lapis_lazuli_resource_mushroom",
            props -> new ResourceMushroomBlock(props, ItemsRegistry.LAPIS_LAZULI_FRAGMENTS, ItemsRegistry.FUNGAL_ESSENCE, 6),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).sound(SoundType.WART_BLOCK), CreativeTabsRegistry.SPORENEXUS_TAB);

    public static final RegistrySupplier<Block> NETHERITE_RESOURCE_MUSHROOM = registerBlock("netherite_resource_mushroom",
            props -> new ResourceMushroomBlock(props, ItemsRegistry.NETHERITE_FRAGMENTS, ItemsRegistry.FUNGAL_ESSENCE, 15),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).sound(SoundType.WART_BLOCK), CreativeTabsRegistry.SPORENEXUS_TAB);

    public static final RegistrySupplier<Block> QUARTZ_RESOURCE_MUSHROOM = registerBlock("quartz_resource_mushroom",
            props -> new ResourceMushroomBlock(props, ItemsRegistry.QUARTZ_FRAGMENTS, ItemsRegistry.FUNGAL_ESSENCE, 4),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).sound(SoundType.WART_BLOCK), CreativeTabsRegistry.SPORENEXUS_TAB);

    public static final RegistrySupplier<Block> REDSTONE_RESOURCE_MUSHROOM = registerBlock("redstone_resource_mushroom",
            props -> new ResourceMushroomBlock(props, ItemsRegistry.REDSTONE_FRAGMENTS, ItemsRegistry.FUNGAL_ESSENCE, 4),
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).sound(SoundType.WART_BLOCK), CreativeTabsRegistry.SPORENEXUS_TAB);







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

}
