package net.okamiz.neoforge.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.okamiz.common.Registries.BlocksRegistry;
import net.okamiz.common.Registries.ItemsRegistry;

import java.util.concurrent.CompletableFuture;

public class DatagenRecipeProvider extends RecipeProvider {

    public DatagenRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    public static class Runner extends RecipeProvider.Runner{

        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
            return new DatagenRecipeProvider(provider, recipeOutput);
        }

        @Override
        public String getName() {
            return "Spore Nexus Recipes";
        }
    }





    @Override
    protected void buildRecipes() {
        shaped(RecipeCategory.MISC, ItemsRegistry.NEXUS_FUNGUS.get())
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', Items.BROWN_MUSHROOM)
                .unlockedBy("has_brown_mushroom", has(Items.BROWN_MUSHROOM))
                .group("fungus")
                .save(output, "sporenexus:nexus_fungus_from_brown");

        shaped(RecipeCategory.MISC, ItemsRegistry.NEXUS_FUNGUS.get())
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', Items.RED_MUSHROOM)
                .unlockedBy("has_red_mushroom", has(Items.RED_MUSHROOM))
                .group("fungus")
                .save(output, "sporenexus:nexus_fungus_from_red");


        shaped(RecipeCategory.MISC, BlocksRegistry.FUNGALSTEEL_BLOCK.get())
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', ItemsRegistry.FUNGALSTEEL_INGOT.get())
                .unlockedBy("has_fungalsteel_ingot", has(ItemsRegistry.FUNGALSTEEL_INGOT.get()))
                .group("fungalsteel")
                .save(output);
        shapeless(RecipeCategory.MISC, ItemsRegistry.FUNGALSTEEL_INGOT.get(), 9)
                .requires(BlocksRegistry.FUNGALSTEEL_BLOCK.get())
                .unlockedBy("has_fungalsteel_block", has(BlocksRegistry.FUNGALSTEEL_BLOCK.get()))
                .group("fungalsteel")
                .save(output);



        shaped(RecipeCategory.MISC, Items.COAL)
                .pattern("XX")
                .pattern("XX")
                .define('X', ItemsRegistry.COAL_FRAGMENTS.get())
                .unlockedBy("has_coal_fragments", has(ItemsRegistry.COAL_FRAGMENTS.get()))
                .group("fragments_to_resource")
                .save(output, "sporenexus:coal_from_fragments");
        shaped(RecipeCategory.MISC, Items.COPPER_INGOT)
                .pattern("XX")
                .pattern("XX")
                .define('X', ItemsRegistry.COPPER_FRAGMENTS.get())
                .unlockedBy("has_copper_fragments", has(ItemsRegistry.COPPER_FRAGMENTS.get()))
                .group("fragments_to_resource")
                .save(output, "sporenexus:copper_from_fragments");
        shaped(RecipeCategory.MISC, Items.IRON_INGOT)
                .pattern("XX")
                .pattern("XX")
                .define('X', ItemsRegistry.IRON_FRAGMENTS.get())
                .unlockedBy("has_iron_fragments", has(ItemsRegistry.IRON_FRAGMENTS.get()))
                .group("fragments_to_resource")
                .save(output, "sporenexus:iron_from_fragments");
        shaped(RecipeCategory.MISC, Items.GOLD_INGOT)
                .pattern("XX")
                .pattern("XX")
                .define('X', ItemsRegistry.GOLD_FRAGMENTS.get())
                .unlockedBy("has_gold_fragments", has(ItemsRegistry.GOLD_FRAGMENTS.get()))
                .group("fragments_to_resource")
                .save(output, "sporenexus:gold_from_fragments");
        shaped(RecipeCategory.MISC, Items.DIAMOND)
                .pattern("XX")
                .pattern("XX")
                .define('X', ItemsRegistry.DIAMOND_FRAGMENTS.get())
                .unlockedBy("has_diamond_fragments", has(ItemsRegistry.DIAMOND_FRAGMENTS.get()))
                .group("fragments_to_resource")
                .save(output, "sporenexus:diamond_from_fragments");
        shaped(RecipeCategory.MISC, Items.EMERALD)
                .pattern("XX")
                .pattern("XX")
                .define('X', ItemsRegistry.EMERALD_FRAGMENTS.get())
                .unlockedBy("has_emerald_fragments", has(ItemsRegistry.EMERALD_FRAGMENTS.get()))
                .group("fragments_to_resource")
                .save(output, "sporenexus:emerald_from_fragments");
        shaped(RecipeCategory.MISC, Items.NETHERITE_SCRAP)
                .pattern("XX")
                .pattern("XX")
                .define('X', ItemsRegistry.NETHERITE_FRAGMENTS.get())
                .unlockedBy("has_netherite_fragments", has(ItemsRegistry.NETHERITE_FRAGMENTS.get()))
                .group("fragments_to_resource")
                .save(output, "sporenexus:netherite_from_fragments");
        shaped(RecipeCategory.MISC, Items.LAPIS_LAZULI)
                .pattern("XX")
                .pattern("XX")
                .define('X', ItemsRegistry.LAPIS_LAZULI_FRAGMENTS.get())
                .unlockedBy("has_lapis_lazuli_fragments", has(ItemsRegistry.LAPIS_LAZULI_FRAGMENTS.get()))
                .group("fragments_to_resource")
                .save(output, "sporenexus:lapis_lazuli_from_fragments");
        shaped(RecipeCategory.MISC, Items.QUARTZ)
                .pattern("XX")
                .pattern("XX")
                .define('X', ItemsRegistry.QUARTZ_FRAGMENTS.get())
                .unlockedBy("has_quartz_fragments", has(ItemsRegistry.QUARTZ_FRAGMENTS.get()))
                .group("fragments_to_resource")
                .save(output, "sporenexus:quartz_from_fragments");
        shaped(RecipeCategory.MISC, Items.REDSTONE)
                .pattern("XX")
                .pattern("XX")
                .define('X', ItemsRegistry.REDSTONE_FRAGMENTS.get())
                .unlockedBy("has_redstone_fragments", has(ItemsRegistry.REDSTONE_FRAGMENTS.get()))
                .group("fragments_to_resource")
                .save(output, "sporenexus:redstone_from_fragments");
        shaped(RecipeCategory.MISC, ItemsRegistry.FUNGALSTEEL_INGOT.get())
                .pattern("XX")
                .pattern("XX")
                .define('X', ItemsRegistry.FUNGALSTEEL_FRAGMENTS.get())
                .unlockedBy("has_fungalsteel_fragments", has(ItemsRegistry.FUNGALSTEEL_FRAGMENTS.get()))
                .group("fragments_to_resource")
                .save(output, "sporenexus:fungalsteel_from_fragments");






        /* RESOURCE MUSHROOMS */

        shaped(RecipeCategory.MISC, BlocksRegistry.IRON_RESOURCE_MUSHROOM.get())
                .pattern("XOX")
                .pattern("OAO")
                .pattern("XOX")
                .define('X', ItemsRegistry.MINERAL_QUARTZ.get())
                .define('O', Items.IRON_INGOT)
                .define('A', ItemsRegistry.MINERAL_FUNGUS.get())
                .unlockedBy("has_mineral_fungus", has(ItemsRegistry.MINERAL_FUNGUS.get()))
                .group("resource_mushroom")
                .save(output);

        shaped(RecipeCategory.MISC, BlocksRegistry.QUARTZ_RESOURCE_MUSHROOM.get())
                .pattern("XOX")
                .pattern("OAO")
                .pattern("XOX")
                .define('X', ItemsRegistry.MINERAL_QUARTZ.get())
                .define('O', Items.QUARTZ)
                .define('A', ItemsRegistry.MINERAL_FUNGUS.get())
                .unlockedBy("has_mineral_fungus", has(ItemsRegistry.MINERAL_FUNGUS.get()))
                .group("resource_mushroom")
                .save(output);


    }
}
