package net.okamiz.common.Registries;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.okamiz.SporeNexus;
import net.okamiz.common.blocks.entity.custom.MycelianCoreBlockEntity;
import net.okamiz.common.blocks.entity.custom.SporeNexusCraftBlockEntity;

import java.util.Set;
import java.util.function.Supplier;

public class BlockEntitiesRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(SporeNexus.MOD_ID, Registries.BLOCK_ENTITY_TYPE);


    public static final Supplier<BlockEntityType<MycelianCoreBlockEntity>> MYCELIAN_CORE_BE = BLOCK_ENTITIES.register("mycelian_core_be",
            () -> new BlockEntityType<>(MycelianCoreBlockEntity::new, Set.of(BlocksRegistry.MYCELIAN_CORE.get())));

    public static final Supplier<BlockEntityType<SporeNexusCraftBlockEntity>> SPORE_NEXUS_CRAFT_BE = BLOCK_ENTITIES.register("spore_nexus_craft_be",
            () -> new BlockEntityType<>(SporeNexusCraftBlockEntity::new, Set.of(BlocksRegistry.SPORE_NEXUS_CRAFT_BLOCK.get())));
}
