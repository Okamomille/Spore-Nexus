package net.okamiz.neoforge.compats.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.okamiz.SporeNexus;
import net.okamiz.common.Registries.BlocksRegistry;
import net.okamiz.common.Registries.MenusRegistry;
import net.okamiz.common.Registries.RecipesRegistry;
import net.okamiz.common.menus.custom.MycelianCoreMenu;
import net.okamiz.common.menus.custom.MycelianCoreScreen;
import net.okamiz.common.menus.custom.SporeNexusCraftMenu;
import net.okamiz.common.menus.custom.SporeNexusCraftScreen;
import net.okamiz.common.recipe.mycelian_core.MycelianCoreRecipe;
import net.okamiz.common.recipe.spore_nexus_craft.SporeNexusCraftRecipe;
import net.okamiz.neoforge.compats.jei.categories.MycelianCoreRecipeCategory;
import net.okamiz.neoforge.compats.jei.categories.SporeNexusCraftRecipeCategory;

import java.util.List;

@JeiPlugin
public class JEIPluginNeoForge implements IModPlugin {
    private static RecipeMap syncedRecipes = RecipeMap.EMPTY;

    public static final IRecipeType<RecipeHolder<MycelianCoreRecipe>> MYCELIAN_CORE = createRecipeHolderType("mycelian_core");
    public static final IRecipeType<RecipeHolder<SporeNexusCraftRecipe>> SPORE_NEXUS_CRAFT = createRecipeHolderType("spore_nexus_craft");

    @Override
    public Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, "jei_plugin");
    }


    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new MycelianCoreRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new SporeNexusCraftRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(MYCELIAN_CORE, this.getRecipes(syncedRecipes, RecipesRegistry.MYCELIAN_CORE_RECIPE_TYPE.get()));
        registration.addRecipes(SPORE_NEXUS_CRAFT, this.getRecipes(syncedRecipes, RecipesRegistry.SPORE_NEXUS_CRAFT_RECIPE_TYPE.get()));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(MycelianCoreScreen.class, 91, 36, 24, 16, MYCELIAN_CORE);
        registration.addRecipeClickArea(SporeNexusCraftScreen.class, 103, 83, 26, 13, SPORE_NEXUS_CRAFT);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(MYCELIAN_CORE, new ItemStack(BlocksRegistry.MYCELIAN_CORE.get()));
        registration.addCraftingStation(SPORE_NEXUS_CRAFT, new ItemStack(BlocksRegistry.SPORE_NEXUS_CRAFT_BLOCK.get()));
    }


    // From Occultism
    // Under MIT License
    @SuppressWarnings({"unchecked", "rawtypes"})
    private <I extends RecipeInput, T extends Recipe<I>> List<RecipeHolder<T>> getRecipes(RecipeMap recipeMap, RecipeType<T> type) {
        return (List) recipeMap.byType(type);
    }

    @SuppressWarnings("unchecked")
    public static <T> IRecipeType<T> createRecipeHolderType(String path) {
        return (IRecipeType<T>) IRecipeType.create(Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, path), RecipeHolder.class);
    }

    @EventBusSubscriber(modid = SporeNexus.MOD_ID)
    public static class ServerRecipeSync {
        @SubscribeEvent
        public static void onDatapackSync(OnDatapackSyncEvent event) {
            event.sendRecipes(
                    RecipesRegistry.MYCELIAN_CORE_RECIPE_TYPE.get(),
                    RecipesRegistry.SPORE_NEXUS_CRAFT_RECIPE_TYPE.get()
            );
        }
    }

    @EventBusSubscriber(modid = SporeNexus.MOD_ID, value = Dist.CLIENT)
    public static class ClientRecipeSync {
        @SubscribeEvent
        public static void onRecipeReceived(RecipesReceivedEvent event) {
            syncedRecipes = event.getRecipeMap();
        }
    }


    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(
                MycelianCoreMenu.class, MenusRegistry.MYCELIAN_CORE_MENU.get(), MYCELIAN_CORE,
                36, 5,
                0, 36);

        registration.addRecipeTransferHandler(
                SporeNexusCraftMenu.class, MenusRegistry.SPORE_NEXUS_CRAFT_MENU.get(), SPORE_NEXUS_CRAFT,
                36, 9,
                0, 36);
    }


}
