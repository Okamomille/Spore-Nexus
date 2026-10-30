package net.okamiz.common.modded_registries;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.okamiz.SporeNexus;
import net.okamiz.common.blocks.entity.custom.MushroomHarvesterBlockEntity;
import net.okamiz.common.blocks.entity.custom.MycelianCoreBlockEntity;
import net.okamiz.common.blocks.entity.custom.SporeNexusCraftBlockEntity;

import java.util.function.Supplier;


public class BlockEntitiesRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(SporeNexus.MOD_ID, Registries.BLOCK_ENTITY_TYPE);


    public static final Supplier<BlockEntityType<MycelianCoreBlockEntity>> MYCELIAN_CORE_BE = BLOCK_ENTITIES.register("mycelian_core_be",
            () -> BlockEntityType.Builder.of(MycelianCoreBlockEntity::new, BlocksRegistry.MYCELIAN_CORE.get()).build(null));

    public static final Supplier<BlockEntityType<SporeNexusCraftBlockEntity>> SPORE_NEXUS_CRAFT_BE = BLOCK_ENTITIES.register("spore_nexus_craft_be",
            () -> BlockEntityType.Builder.of(SporeNexusCraftBlockEntity::new, BlocksRegistry.SPORE_NEXUS_CRAFT_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<MushroomHarvesterBlockEntity>> MUSHROOM_HARVESTER_BE = BLOCK_ENTITIES.register("mushroom_harvester_be",
            () -> BlockEntityType.Builder.of(MushroomHarvesterBlockEntity::new, BlocksRegistry.MUSHROOM_HARVESTER.get(), BlocksRegistry.FUNGAL_HARVESTER.get(),
                    BlocksRegistry.INFERNAL_HARVESTER.get(), BlocksRegistry.ETHEREAL_HARVESTER.get()).build(null));
}
