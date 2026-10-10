package net.okamiz;

import net.okamiz.common.Registries.*;
import net.okamiz.common.blocks.TooltipBlockItem;

public final class SporeNexus {
    public static final String MOD_ID = "sporenexus";

    public static void init() {

        BlocksRegistry.BLOCKS.register();
        ItemsRegistry.ITEMS.register();
        CreativeTabsRegistry.TABS.register();
        BlockEntitiesRegistry.BLOCK_ENTITIES.register();
        MenusRegistry.MENU_TYPE.register();
        RecipesRegistry.RECIPE_TYPES.register();
        RecipesRegistry.RECIPE_SERIALIZERS.register();
        TooltipBlockItem.registerTooltipEvent();

    }
}
