package net.okamiz;

import com.mojang.logging.LogUtils;
import dev.architectury.event.events.common.LifecycleEvent;
import net.okamiz.common.config.CustomMushroomLoader;
import net.okamiz.common.config.CustomMushroomValidator;
import net.okamiz.common.config.GeneratedPack;
import net.okamiz.common.modded_registries.*;
import net.okamiz.common.blocks.TooltipBlockItem;
import org.slf4j.Logger;

public final class SporeNexus {
    public static final String MOD_ID = "sporenexus";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {

        BlocksRegistry.BLOCKS.register();
        ItemsRegistry.ITEMS.register();
        CreativeTabsRegistry.TABS.register();
        BlockEntitiesRegistry.BLOCK_ENTITIES.register();
        MenusRegistry.MENU_TYPE.register();
        RecipesRegistry.RECIPE_TYPES.register();
        RecipesRegistry.RECIPE_SERIALIZERS.register();
        TooltipBlockItem.registerTooltipEvent();

        CustomMushroomLoader.load();
        GeneratedPack.generate();
        LifecycleEvent.SERVER_STARTED.register(server -> CustomMushroomValidator.validate());
    }
}
