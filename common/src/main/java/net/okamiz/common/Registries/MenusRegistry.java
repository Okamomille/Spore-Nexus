package net.okamiz.common.Registries;

import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.okamiz.SporeNexus;
import net.okamiz.common.menus.custom.MycelianCoreMenu;
import net.okamiz.common.menus.custom.SporeNexusCraftMenu;

public class MenusRegistry {

    public static final DeferredRegister<MenuType<?>> MENU_TYPE = DeferredRegister.create(SporeNexus.MOD_ID, Registries.MENU);

    public static final RegistrySupplier<MenuType<MycelianCoreMenu>> MYCELIAN_CORE_MENU = MENU_TYPE.register("mycelian_core_menu",
            () -> MenuRegistry.ofExtended(MycelianCoreMenu::new));

    public static final RegistrySupplier<MenuType<SporeNexusCraftMenu>> SPORE_NEXUS_CRAFT_MENU = MENU_TYPE.register("spore_nexus_craft_menu",
            () -> MenuRegistry.ofExtended(SporeNexusCraftMenu::new));
}
