package net.okamiz.common.compats.jade.providers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.okamiz.SporeNexus;
import net.okamiz.common.blocks.custom.MushroomHarvesterBlock;
import net.okamiz.common.blocks.entity.custom.MushroomHarvesterBlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class HarvesterProvider implements IBlockComponentProvider {
    public static final HarvesterProvider INSTANCE = new HarvesterProvider();
    public static final Identifier UID = Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, "mushroom_harvester");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (!(accessor.getBlock() instanceof MushroomHarvesterBlock block)) return;

        int side = block.getRadius() * 2 + 1;
        tooltip.add(Component.translatable("jade.sporenexus.harvester_range", side, 3, side));

        CompoundTag data = accessor.getServerData();
        if (!data.contains("used")) return;   // données pas encore reçues

        int used = data.getIntOr("used", 0);
        int size = data.getIntOr("size", 0);
        boolean full = used >= size;

        tooltip.add(Component.translatable("jade.sporenexus.harvester_storage", used, size)
                .withColor(full ? 0xFF5555 : 0xFFFFFF));
        if (full) {
            tooltip.add(Component.translatable("jade.sporenexus.harvester_full").withColor(0xFF5555));
        } else {
            tooltip.add(Component.translatable("jade.sporenexus.harvester_next_scan", Math.max(1, data.getIntOr("cooldown", 0) / 20)));
        }
    }

    @Override
    public Identifier getUid() {
        return UID;
    }
}
