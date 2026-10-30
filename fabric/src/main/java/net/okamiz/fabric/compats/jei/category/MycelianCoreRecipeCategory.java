package net.okamiz.fabric.compats.jei.category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.okamiz.SporeNexus;
import net.okamiz.common.modded_registries.BlocksRegistry;
import net.okamiz.common.recipe.mycelian_core.MycelianCoreRecipe;
import net.okamiz.fabric.compats.jei.JEIRecipesTypesFabric;
import org.jetbrains.annotations.Nullable;

public class MycelianCoreRecipeCategory implements IRecipeCategory<RecipeHolder<MycelianCoreRecipe>> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(SporeNexus.MOD_ID,
            "textures/gui/mycelian_core/mycelian_core_gui.png");
    private final IDrawable icon;
    private final IDrawable overlay;

    private final int width = 156;
    private final int height = 72;

    private final int xOffset = 6;
    private final int yOffset = 6;


    public MycelianCoreRecipeCategory(IGuiHelper helper) {
        this.overlay = helper.createDrawable(TEXTURE, xOffset, yOffset, width, height);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlocksRegistry.MYCELIAN_CORE.get()));
    }

    @Override
    public RecipeType<RecipeHolder<MycelianCoreRecipe>> getRecipeType() {
        return JEIRecipesTypesFabric.MYCELIAN_CORE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.sporenexus.mycelian_core");
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
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<MycelianCoreRecipe> recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 68-xOffset, 21-yOffset).addIngredients(recipe.value().mushroomInputItem());
        builder.addSlot(RecipeIngredientRole.INPUT, 68-xOffset, 51-yOffset).addIngredients(recipe.value().substractInputItem());

        int[][] positions = {{14-xOffset, 23-yOffset}, {14-xOffset, 47-yOffset}, {34-xOffset, 36-yOffset}};
        var nutrients = recipe.value().nutrientInputs();
        for (int i = 0; i < positions.length; i++) {
            var slot = builder.addSlot(RecipeIngredientRole.INPUT, positions[i][0], positions[i][1]);
            if (i < nutrients.size()) {
                slot.addIngredients(nutrients.get(i));
            }
        }


        builder.addSlot(RecipeIngredientRole.OUTPUT, 134-xOffset, 22-yOffset).addItemStack(recipe.value().output());
        recipe.value().assembleSecondary().ifPresent(stack -> builder.addSlot(RecipeIngredientRole.OUTPUT, 134-xOffset, 54-yOffset).addItemStack(stack));
    }

    @Override
    public void draw(RecipeHolder<MycelianCoreRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.overlay.draw(guiGraphics, 0, 0);
    }


}
