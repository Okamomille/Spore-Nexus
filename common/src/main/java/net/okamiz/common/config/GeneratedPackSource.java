package net.okamiz.common.config;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;

import java.util.Optional;
import java.util.function.Consumer;

public class GeneratedPackSource implements RepositorySource {

    @Override
    public void loadPacks(Consumer<Pack> consumer) {
        PackLocationInfo info = new PackLocationInfo("sporenexus_generated", Component.literal("Spore Nexus generated"),
                PackSource.BUILT_IN, Optional.empty());
        Pack pack = Pack.readMetaAndCreate(info,
                new PathPackResources.PathResourcesSupplier(GeneratedPack.directory()), PackType.CLIENT_RESOURCES,
                new PackSelectionConfig(true, Pack.Position.TOP, false));
        if (pack != null) {
            consumer.accept(pack);
        }
    }
}
