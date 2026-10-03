package net.okamiz;

import net.okamiz.common.Registries.BlocksRegistry;
import net.okamiz.common.Registries.CreativeTabsRegistry;
import net.okamiz.common.Registries.ItemsRegistry;

public final class SporeNexus {
    public static final String MOD_ID = "sporenexus";

    public static void init() {

        BlocksRegistry.BLOCKS.register();
        ItemsRegistry.ITEMS.register();
        CreativeTabsRegistry.TABS.register();


    }
}
