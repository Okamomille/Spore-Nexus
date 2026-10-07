package net.okamiz.common.Registries;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.okamiz.SporeNexus;
import net.okamiz.common.blocks.entity.custom.MycelianCoreBlockEntity;

import java.util.Set;
import java.util.function.Supplier;

public class BlockEntitiesRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(SporeNexus.MOD_ID, Registries.BLOCK_ENTITY_TYPE);


    public static final Supplier<BlockEntityType<MycelianCoreBlockEntity>> MYCELIAN_CORE_BE = BLOCK_ENTITIES.register("mycelian_core_be",
            () -> new BlockEntityType<>(MycelianCoreBlockEntity::new, Set.of(BlocksRegistry.MYCELIAN_CORE.get())));

}
