package net.okamiz.neoforge.compats.jade;

import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.okamiz.SporeNexus;
import net.okamiz.common.compats.jade.ClientMutationRecipes;
import net.okamiz.common.modded_registries.RecipesRegistry;

@EventBusSubscriber(modid = SporeNexus.MOD_ID, value = Dist.CLIENT)
public class RecipesSync {
    @SubscribeEvent
    public static void onRecipesReceived(RecipesReceivedEvent event) {
        ClientMutationRecipes.set(event.getRecipeMap().byType(RecipesRegistry.MUSHROOM_MUTATION_RECIPE_TYPE.get()).stream().map(RecipeHolder::value).toList());
    }
}
