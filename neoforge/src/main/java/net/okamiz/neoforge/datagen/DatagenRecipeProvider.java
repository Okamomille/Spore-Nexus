package net.okamiz.neoforge.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.okamiz.common.modded_registries.BlocksRegistry;
import net.okamiz.common.modded_registries.ItemsRegistry;
import net.okamiz.common.modded_registries.TagsRegistry;
import net.okamiz.common.records.FragmentType;

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
                .define('X', TagsRegistry.MUSHROOMS)
                .unlockedBy("has_mushroom", has(TagsRegistry.MUSHROOMS))
                .group("fungus")
                .save(output, "sporenexus:nexus_fungus_from_mushrooms");



        shaped(RecipeCategory.MISC, BlocksRegistry.SPORE_NEXUS_CRAFT_BLOCK.get())
                .pattern("OOO")
                .pattern(" X ")
                .pattern("XBX")
                .define('O', Items.IRON_INGOT)
                .define('X', ItemsRegistry.FUNGALSTEEL_INGOT.get())
                .define('B', BlocksRegistry.FUNGALSTEEL_BLOCK.get())
                .unlockedBy("has_fungalsteel_ingot", has(ItemsRegistry.FUNGALSTEEL_INGOT.get()))
                .save(output, "sporenexus:spore_nexus_altar");

        shaped(RecipeCategory.MISC, BlocksRegistry.MYCELIAN_CORE.get())
                .pattern("OSO")
                .pattern(" M ")
                .pattern("OSO")
                .define('O', Items.IRON_INGOT)
                .define('M', Blocks.MYCELIUM)
                .define('S', ItemsRegistry.MUSHROOM_SPORES.get())
                .unlockedBy("has_fungalsteel_ingot", has(ItemsRegistry.MUSHROOM_SPORES.get()))
                .save(output);


        shaped(RecipeCategory.MISC, ItemsRegistry.FUNGALSTEEL_INGOT.get())
                .pattern(" X ")
                .pattern("XOX")
                .pattern(" X ")
                .define('X', ItemsRegistry.FUNGAL_POWDER.get())
                .define('O', Items.IRON_INGOT)
                .unlockedBy("has_fungal_powder", has(ItemsRegistry.FUNGAL_POWDER.get()))
                .group("fungalsteel")
                .save(output, "sporenexus:fungalsteel_from_powder");

        shaped(RecipeCategory.MISC, ItemsRegistry.INFERNAL_ESSENCE.get())
                .pattern(" X ")
                .pattern("XOX")
                .pattern(" X ")
                .define('X', ItemsRegistry.FUNGAL_ESSENCE.get())
                .define('O', Items.BLAZE_POWDER)
                .unlockedBy("has_fungal_essence", has(ItemsRegistry.FUNGAL_ESSENCE.get()))
                .group("essence")
                .save(output);

        shaped(RecipeCategory.MISC, ItemsRegistry.ETHEREAL_ESSENCE.get())
                .pattern(" X ")
                .pattern("XOX")
                .pattern(" X ")
                .define('X', ItemsRegistry.INFERNAL_ESSENCE.get())
                .define('O', Items.CHORUS_FRUIT)
                .unlockedBy("has_infernal_essence", has(ItemsRegistry.INFERNAL_ESSENCE.get()))
                .group("essence")
                .save(output);

        shaped(RecipeCategory.MISC, BlocksRegistry.FUNGAL_LIGHT.get())
                .pattern(" X ")
                .pattern("XEX")
                .pattern(" X ")
                .define('E', Blocks.SHROOMLIGHT)
                .define('X', ItemsRegistry.FUNGAL_ESSENCE.get())
                .unlockedBy("has_fungal_essence", has(ItemsRegistry.FUNGAL_ESSENCE.get()))
                .save(output);


        shaped(RecipeCategory.MISC, BlocksRegistry.MUSHROOM_HARVESTER.get())
                .pattern("XXX")
                .pattern("EOE")
                .pattern("XVX")
                .define('E', Blocks.IRON_BARS)
                .define('X', ItemsRegistry.FUNGALSTEEL_INGOT.get())
                .define('O', Items.CHEST)
                .define('V', Items.HOPPER)
                .unlockedBy("has_fungalsteel_ingot", has(ItemsRegistry.FUNGALSTEEL_INGOT.get()))
                .group("mushroom_harvesters")
                .save(output);
        shaped(RecipeCategory.MISC, BlocksRegistry.FUNGAL_HARVESTER.get())
                .pattern(" X ")
                .pattern("XEX")
                .pattern(" X ")
                .define('E', BlocksRegistry.MUSHROOM_HARVESTER.get())
                .define('X', ItemsRegistry.FUNGAL_ESSENCE.get())
                .unlockedBy("has_fungal_essence", has(ItemsRegistry.FUNGAL_ESSENCE.get()))
                .group("mushroom_harvesters")
                .save(output);
        shaped(RecipeCategory.MISC, BlocksRegistry.INFERNAL_HARVESTER.get())
                .pattern(" X ")
                .pattern("XEX")
                .pattern(" X ")
                .define('E', BlocksRegistry.FUNGAL_HARVESTER.get())
                .define('X', ItemsRegistry.INFERNAL_ESSENCE.get())
                .unlockedBy("has_infernal_essence", has(ItemsRegistry.INFERNAL_ESSENCE.get()))
                .group("mushroom_harvesters")
                .save(output);
        shaped(RecipeCategory.MISC, BlocksRegistry.ETHEREAL_HARVESTER.get())
                .pattern(" X ")
                .pattern("XEX")
                .pattern(" X ")
                .define('E', BlocksRegistry.INFERNAL_HARVESTER.get())
                .define('X', ItemsRegistry.ETHEREAL_ESSENCE.get())
                .unlockedBy("has_ethereal_essence", has(ItemsRegistry.ETHEREAL_ESSENCE.get()))
                .group("mushroom_harvesters")
                .save(output);


        shaped(RecipeCategory.MISC, BlocksRegistry.GROWTH_ACCELERATOR.get())
                .pattern("AXA")
                .pattern("BFB")
                .pattern("AXA")
                .define('F', BlocksRegistry.FERTILIZED_MYCELIUM.get())
                .define('X', BlocksRegistry.FUNGALSTEEL_BLOCK.get())
                .define('A', ItemsRegistry.FUNGALSTEEL_INGOT.get())
                .define('B', Items.IRON_BARS)
                .unlockedBy("has_fertilized_mycelium", has(BlocksRegistry.FERTILIZED_MYCELIUM.get()))
                .group("growth_accelerators")
                .save(output);

        shaped(RecipeCategory.MISC, BlocksRegistry.FUNGAL_GROWTH_ACCELERATOR.get())
                .pattern("AXA")
                .pattern("BFB")
                .pattern("AXA")
                .define('F', BlocksRegistry.FUNGAL_MYCELIUM.get())
                .define('X', BlocksRegistry.FUNGALSTEEL_BLOCK.get())
                .define('A', ItemsRegistry.FUNGALSTEEL_INGOT.get())
                .define('B', Items.IRON_BARS)
                .unlockedBy("has_fungal_mycelium", has(BlocksRegistry.FUNGAL_MYCELIUM.get()))
                .group("growth_accelerators")
                .save(output);

        shaped(RecipeCategory.MISC, BlocksRegistry.FUNGAL_GROWTH_ACCELERATOR.get())
                .pattern(" X ")
                .pattern("XOX")
                .pattern(" X ")
                .define('X', ItemsRegistry.FUNGAL_ESSENCE.get())
                .define('O', BlocksRegistry.GROWTH_ACCELERATOR.get())
                .unlockedBy("has_fungal_essence", has(ItemsRegistry.FUNGAL_ESSENCE.get()))
                .group("growth_accelerators")
                .save(output, "sporenexus:fungal_growth_accelerator_from_previous");

        shaped(RecipeCategory.MISC, BlocksRegistry.INFERNAL_GROWTH_ACCELERATOR.get())
                .pattern("AXA")
                .pattern("BFB")
                .pattern("AXA")
                .define('F', BlocksRegistry.INFERNAL_MYCELIUM.get())
                .define('X', BlocksRegistry.FUNGALSTEEL_BLOCK.get())
                .define('A', ItemsRegistry.FUNGALSTEEL_INGOT.get())
                .define('B', Items.IRON_BARS)
                .unlockedBy("has_infernal_mycelium", has(BlocksRegistry.INFERNAL_MYCELIUM.get()))
                .group("growth_accelerators")
                .save(output);

        shaped(RecipeCategory.MISC, BlocksRegistry.INFERNAL_GROWTH_ACCELERATOR.get())
                .pattern(" X ")
                .pattern("XOX")
                .pattern(" X ")
                .define('X', ItemsRegistry.INFERNAL_ESSENCE.get())
                .define('O', BlocksRegistry.FUNGAL_GROWTH_ACCELERATOR.get())
                .unlockedBy("has_infernal_essence", has(ItemsRegistry.INFERNAL_ESSENCE.get()))
                .group("growth_accelerators")
                .save(output, "sporenexus:infernal_growth_accelerator_from_previous");

        shaped(RecipeCategory.MISC, BlocksRegistry.ETHEREAL_GROWTH_ACCELERATOR.get())
                .pattern("AXA")
                .pattern("BFB")
                .pattern("AXA")
                .define('F', BlocksRegistry.ETHEREAL_MYCELIUM.get())
                .define('X', BlocksRegistry.FUNGALSTEEL_BLOCK.get())
                .define('A', ItemsRegistry.FUNGALSTEEL_INGOT.get())
                .define('B', Items.IRON_BARS)
                .unlockedBy("has_ethereal_mycelium", has(BlocksRegistry.ETHEREAL_MYCELIUM.get()))
                .group("growth_accelerators")
                .save(output);

        shaped(RecipeCategory.MISC, BlocksRegistry.ETHEREAL_GROWTH_ACCELERATOR.get())
                .pattern(" X ")
                .pattern("XOX")
                .pattern(" X ")
                .define('X', ItemsRegistry.ETHEREAL_ESSENCE.get())
                .define('O', BlocksRegistry.INFERNAL_GROWTH_ACCELERATOR.get())
                .unlockedBy("has_ethereal_essence", has(ItemsRegistry.ETHEREAL_ESSENCE.get()))
                .group("growth_accelerators")
                .save(output, "sporenexus:ethereal_growth_accelerator_from_previous");


        createToolsRecipe(ItemsRegistry.FUNGALSTEEL_INGOT.get(),Items.IRON_BARS,ItemsRegistry.FUNGALSTEEL_PICKAXE.get(),ItemsRegistry.FUNGALSTEEL_SWORD.get(),
                ItemsRegistry.FUNGALSTEEL_AXE.get(), ItemsRegistry.FUNGALSTEEL_SHOVEL.get(), ItemsRegistry.FUNGALSTEEL_HOE.get(), ItemsRegistry.FUNGALSTEEL_SPEAR.get());


        shaped(RecipeCategory.MISC, Blocks.MYCELIUM)
                .pattern("XXX")
                .pattern("XOX")
                .pattern("XXX")
                .define('X', ItemsRegistry.MUSHROOM_SPORES.get())
                .define('O', Blocks.DIRT)
                .unlockedBy("has_mushroom_spores", has(ItemsRegistry.MUSHROOM_SPORES.get()))
                .save(output, "sporenexus:mycelium_from_spores");

        shaped(RecipeCategory.MISC, BlocksRegistry.FERTILIZED_MYCELIUM.get())
                .pattern("XXX")
                .pattern("XOX")
                .pattern("XXX")
                .define('X', ItemsRegistry.MUSHROOM_SPORES.get())
                .define('O', Blocks.MYCELIUM)
                .unlockedBy("has_mushroom_spores", has(ItemsRegistry.MUSHROOM_SPORES.get()))
                .save(output);

        shaped(RecipeCategory.MISC, BlocksRegistry.FUNGAL_MYCELIUM.get())
                .pattern(" X ")
                .pattern("XOX")
                .pattern(" X ")
                .define('X', ItemsRegistry.FUNGAL_ESSENCE.get())
                .define('O', BlocksRegistry.FERTILIZED_MYCELIUM.get())
                .unlockedBy("has_fertilized_mycelium", has(BlocksRegistry.FERTILIZED_MYCELIUM.get()))
                .save(output);

        shaped(RecipeCategory.MISC, BlocksRegistry.INFERNAL_MYCELIUM.get())
                .pattern(" X ")
                .pattern("XOX")
                .pattern(" X ")
                .define('X', ItemsRegistry.INFERNAL_ESSENCE.get())
                .define('O', BlocksRegistry.FUNGAL_MYCELIUM.get())
                .unlockedBy("has_fungal_mycelium", has(BlocksRegistry.FUNGAL_MYCELIUM.get()))
                .save(output);

        shaped(RecipeCategory.MISC, BlocksRegistry.ETHEREAL_MYCELIUM.get())
                .pattern(" X ")
                .pattern("XOX")
                .pattern(" X ")
                .define('X', ItemsRegistry.ETHEREAL_ESSENCE.get())
                .define('O', BlocksRegistry.INFERNAL_MYCELIUM.get())
                .unlockedBy("has_infernal_mycelium", has(BlocksRegistry.INFERNAL_MYCELIUM.get()))
                .save(output);


        shaped(RecipeCategory.MISC, ItemsRegistry.GREAT_FUNGUS.get())
                .pattern(" X ")
                .pattern("XOX")
                .pattern(" X ")
                .define('X', ItemsRegistry.FUNGAL_ESSENCE.get())
                .define('O', ItemsRegistry.NEXUS_FUNGUS.get())
                .unlockedBy("has_nexus_fungus", has(ItemsRegistry.NEXUS_FUNGUS.get()))
                .save(output);
        shaped(RecipeCategory.MISC, ItemsRegistry.INFERNAL_FUNGUS.get())
                .pattern(" X ")
                .pattern("XOX")
                .pattern(" X ")
                .define('X', ItemsRegistry.INFERNAL_ESSENCE.get())
                .define('O', ItemsRegistry.GREAT_FUNGUS.get())
                .unlockedBy("has_great_fungus", has(ItemsRegistry.GREAT_FUNGUS.get()))
                .save(output);
        shaped(RecipeCategory.MISC, ItemsRegistry.ETHEREAL_FUNGUS.get())
                .pattern(" X ")
                .pattern("XOX")
                .pattern(" X ")
                .define('X', ItemsRegistry.ETHEREAL_ESSENCE.get())
                .define('O', ItemsRegistry.INFERNAL_FUNGUS.get())
                .unlockedBy("has_infernal_fungus", has(ItemsRegistry.INFERNAL_FUNGUS.get()))
                .save(output);


        shaped(RecipeCategory.MISC, BlocksRegistry.FUNGALSTEEL_BLOCK.get())
                .pattern("XX")
                .pattern("XX")
                .define('X', ItemsRegistry.FUNGALSTEEL_INGOT.get())
                .unlockedBy("has_fungalsteel_ingot", has(ItemsRegistry.FUNGALSTEEL_INGOT.get()))
                .group("fungalsteel")
                .save(output);

        shapeless(RecipeCategory.MISC, ItemsRegistry.FUNGALSTEEL_INGOT.get(), 4)
                .requires(BlocksRegistry.FUNGALSTEEL_BLOCK.get())
                .unlockedBy("has_fungalsteel_block", has(BlocksRegistry.FUNGALSTEEL_BLOCK.get()))
                .group("fungalsteel")
                .save(output);

        shaped(RecipeCategory.MISC, ItemsRegistry.FUNGALSTEEL_INGOT.get())
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', ItemsRegistry.FUNGALSTEEL_NUGGET.get())
                .unlockedBy("has_fungalsteel_ingot", has(ItemsRegistry.FUNGALSTEEL_INGOT.get()))
                .group("fungalsteel")
                .save(output, "sporenexus:"+"fungalsteel_ingot_from_nuggets");

        shapeless(RecipeCategory.MISC, ItemsRegistry.FUNGALSTEEL_NUGGET.get(), 9)
                .requires(ItemsRegistry.FUNGALSTEEL_INGOT.get())
                .unlockedBy("has_fungalsteel_ingot", has(ItemsRegistry.FUNGALSTEEL_INGOT.get()))
                .group("fungalsteel")
                .save(output);

        shapeless(RecipeCategory.MISC, ItemsRegistry.MUSHROOM_SPORES.get(), 2)
                .requires(TagsRegistry.MUSHROOMS)
                .unlockedBy("has_mushroom", has(TagsRegistry.MUSHROOMS))
                .group("mushroom_spores")
                .save(output);


        for (FragmentType type : ItemsRegistry.FRAGMENT_TYPES) {
            Item fragment = ItemsRegistry.fragment(type.name()).get();
            shaped(RecipeCategory.MISC, type.craftOutput().get(), type.craftCount())
                    .pattern("XX")
                    .pattern("XX")
                    .define('X', fragment)
                    .unlockedBy("has_" + type.itemName(), has(fragment))
                    .group("fragments_to_resource")
                    .save(output, "sporenexus:" + type.name() + "_from_fragments");
        }


        shaped(RecipeCategory.MISC, Items.MUD, 2)
                .pattern("DX")
                .pattern("XD")
                .define('X', ItemsRegistry.fragment("clay").get())
                .define('D', ItemsRegistry.fragment("dirt").get())
                .unlockedBy("has_clay_fragments", has(ItemsRegistry.fragment("clay").get()))
                .group("fragments_to_resource")
                .save(output, "sporenexus:"+"mud_from_frags");

        shaped(RecipeCategory.MISC, Items.MOSS_CARPET, 2)
                .pattern("XX")
                .define('X', ItemsRegistry.fragment("moss").get())
                .unlockedBy("has_moss_fragments", has(ItemsRegistry.fragment("moss").get()))
                .group("fragments_to_resource")
                .save(output, "sporenexus:"+"moss_carpet_from_frags");

        shaped(RecipeCategory.MISC, Items.COBBLESTONE, 2)
                .pattern(" X ")
                .pattern("X X")
                .pattern(" X ")
                .define('X', ItemsRegistry.fragment("stone").get())
                .unlockedBy("has_stone_fragments", has(ItemsRegistry.fragment("stone").get()))
                .group("fragments_to_resource")
                .save(output, "sporenexus:"+"cobblestone_from_frags");

        shaped(RecipeCategory.MISC, Items.STONE_BRICKS, 2)
                .pattern("X X")
                .pattern("X X")
                .define('X', ItemsRegistry.fragment("stone").get())
                .unlockedBy("has_stone_fragments", has(ItemsRegistry.fragment("stone").get()))
                .group("fragments_to_resource")
                .save(output, "sporenexus:"+"stone_bricks_from_frags");

        shaped(RecipeCategory.MISC, Items.MOSSY_STONE_BRICKS, 4)
                .pattern("XOX")
                .pattern("XOX")
                .define('X', ItemsRegistry.fragment("stone").get())
                .define('O', ItemsRegistry.fragment("mossy_cobblestone").get())
                .unlockedBy("has_stone_fragments", has(ItemsRegistry.fragment("stone").get()))
                .group("fragments_to_resource")
                .save(output, "sporenexus:"+"mossy_stone_bricks_from_frags");

        shaped(RecipeCategory.MISC, Items.SOUL_SOIL, 2)
                .pattern("X X")
                .pattern("X X")
                .define('X', ItemsRegistry.fragment("soul_sand").get())
                .unlockedBy("has_soul_sand_fragments", has(ItemsRegistry.fragment("soul_sand").get()))
                .group("fragments_to_resource")
                .save(output, "sporenexus:"+"soul_soil_from_frags");

        shaped(RecipeCategory.MISC, Items.WATER_BUCKET, 1)
                .pattern("X X")
                .pattern(" I ")
                .pattern("X X")
                .define('X', ItemsRegistry.fragment("ice").get())
                .define('I', Items.BUCKET)
                .unlockedBy("has_ice_sand_fragments", has(ItemsRegistry.fragment("ice").get()))
                .group("fragments_to_resource")
                .save(output, "sporenexus:"+"water_bucket_from_frags");

    }
    private void createToolsRecipe(Item ingredient, Item stick, Item pickaxe, Item sword, Item axe, Item shovel, Item hoe, Item spear){

        shaped(RecipeCategory.MISC, pickaxe, 1)
                .pattern("XXX")
                .pattern(" I ")
                .pattern(" I ")
                .define('X', ingredient)
                .define('I', stick)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .group("tools")
                .save(output);
        shaped(RecipeCategory.MISC, sword, 1)
                .pattern("X")
                .pattern("X")
                .pattern("I")
                .define('X', ingredient)
                .define('I', stick)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .group("tools")
                .save(output);
        shaped(RecipeCategory.MISC, shovel, 1)
                .pattern("X")
                .pattern("I")
                .pattern("I")
                .define('X', ingredient)
                .define('I', stick)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .group("tools")
                .save(output);
        shaped(RecipeCategory.MISC, axe, 1)
                .pattern("XX")
                .pattern("XI")
                .pattern(" I")
                .define('X', ingredient)
                .define('I', stick)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .group("tools")
                .save(output, "sporenexus:"+getItemName(axe)+"_left");
        shaped(RecipeCategory.MISC, axe, 1)
                .pattern("XX")
                .pattern("IX")
                .pattern("I ")
                .define('X', ingredient)
                .define('I', stick)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .group("tools")
                .save(output, "sporenexus:"+getItemName(axe)+"_right");

        shaped(RecipeCategory.MISC, hoe, 1)
                .pattern("XX")
                .pattern("I ")
                .pattern("I ")
                .define('X', ingredient)
                .define('I', stick)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .group("tools")
                .save(output, "sporenexus:"+getItemName(hoe)+"_right");
        shaped(RecipeCategory.MISC, hoe, 1)
                .pattern("XX")
                .pattern(" I")
                .pattern(" I")
                .define('X', ingredient)
                .define('I', stick)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .group("tools")
                .save(output, "sporenexus:"+getItemName(hoe)+"_left");
        shaped(RecipeCategory.MISC, spear, 1)
                .pattern("  X")
                .pattern(" I ")
                .pattern("I  ")
                .define('X', ingredient)
                .define('I', stick)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .group("tools")
                .save(output);
    }
}
