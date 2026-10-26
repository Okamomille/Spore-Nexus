package net.okamiz.common.compats.jade.providers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.okamiz.common.blocks.entity.custom.MushroomHarvesterBlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;

public class HarvesterDataProvider implements IServerDataProvider<BlockAccessor> {
    public static final HarvesterDataProvider INSTANCE = new HarvesterDataProvider();

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        if (accessor.getBlockEntity() instanceof MushroomHarvesterBlockEntity be) {
            data.putInt("used", be.getUsedSlots());
            data.putInt("size", be.getContainerSize());
            data.putInt("cooldown", be.getCooldown());
        }
    }

    @Override
    public ResourceLocation getUid() {
        return HarvesterProvider.UID;
    }
}
