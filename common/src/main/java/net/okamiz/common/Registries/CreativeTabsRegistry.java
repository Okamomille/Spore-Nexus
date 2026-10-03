package net.okamiz.common.Registries;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.okamiz.SporeNexus;

public class CreativeTabsRegistry {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(SporeNexus.MOD_ID, Registries.CREATIVE_MODE_TAB);


    public static final RegistrySupplier<CreativeModeTab> SPORENEXUS_TAB = TABS.register("sporenexus_tab", () ->
            CreativeTabRegistry.create(builder -> {
                builder.title(Component.translatable("tab.sporenexus"));
                builder.icon(() -> new ItemStack(ItemsRegistry.FUNGALSTEEL_INGOT.get()));
            }));
}
