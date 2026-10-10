package net.okamiz.neoforge.compats.jei.categories;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.okamiz.SporeNexus;
import net.okamiz.common.Registries.BlocksRegistry;
import net.okamiz.common.recipe.spore_nexus_craft.SporeNexusCraftRecipe;
import net.okamiz.neoforge.compats.jei.JEIPluginNeoForge;

import javax.annotation.Nullable;

public class SporeNexusCraftRecipeCategory implements IRecipeCategory<RecipeHolder<SporeNexusCraftRecipe>> {
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID,
            "textures/gui/spore_nexus/spore_nexus_jei.png");
    private final IDrawable icon;
    private final IDrawable overlay;

    private final int width = 176;
    private final int height = 120;

    private final int xOffset = 24;
    private final int yOffset = 9;


    public SporeNexusCraftRecipeCategory(IGuiHelper helper) {
        this.overlay = helper.createDrawable(TEXTURE, xOffset, yOffset, width, height);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlocksRegistry.SPORE_NEXUS_CRAFT_BLOCK.get()));
    }

    @Override
    public IRecipeType<RecipeHolder<SporeNexusCraftRecipe>> getRecipeType() {
        return JEIPluginNeoForge.SPORE_NEXUS_CRAFT;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.sporenexus.spore_nexus");
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<SporeNexusCraftRecipe> recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 80-xOffset, 61-yOffset).add(recipe.value().mushroomInputItem());

        int[][] positions = {{29-xOffset, 61-yOffset},{80-xOffset, 10-yOffset},{131-xOffset, 61-yOffset}, {80-xOffset, 112-yOffset}};
        var resources = recipe.value().resourceInputs();
        for (int i = 0; i < positions.length; i++) {
            var slot = builder.addSlot(RecipeIngredientRole.INPUT, positions[i][0], positions[i][1]);
            if (i < resources.size()) {
                slot.add(resources.get(i));
            }
        }

        int[][] secondaryPositions = {{44-xOffset, 97-yOffset},{116-xOffset, 25-yOffset}, {44-xOffset, 25-yOffset}, {116-xOffset, 97-yOffset}};
        var secondary = recipe.value().secondaryInputs();
        for (int i = 0; i < secondaryPositions.length; i++) {
            var slot = builder.addSlot(RecipeIngredientRole.INPUT, secondaryPositions[i][0], secondaryPositions[i][1]);
            if (i < secondary.size()) {
                slot.add(secondary.get(i));
            }
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 176-xOffset, 60-yOffset).add(recipe.value().output());
    }

    @Override
    public void draw(RecipeHolder<SporeNexusCraftRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.overlay.draw(guiGraphics, 0, 0);
    }
}