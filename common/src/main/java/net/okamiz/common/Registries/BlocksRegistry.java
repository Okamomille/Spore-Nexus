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
import net.okamiz.SporeNexus;
import net.okamiz.common.blocks.custom.ResourceMushroomBlock;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlocksRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(SporeNexus.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> FUNGALSTEEL_BLOCK = registerBlock("fungalsteel_block", Block::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.METAL), CreativeTabsRegistry.SPORENEXUS_TAB );


    public static final RegistrySupplier<Block> COAL_RESOURCE_MUSRHOOM = registerBlock("coal_resource_mushroom", ResourceMushroomBlock::new,
            () -> BlockBehaviour.Properties.of().sound(SoundType.WART_BLOCK), CreativeTabsRegistry.SPORENEXUS_TAB );







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
