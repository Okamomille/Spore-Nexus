package net.okamiz.common.compats.jei.category;

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
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.okamiz.SporeNexus;
import net.okamiz.common.compats.jei.MushroomMutation;
import org.jetbrains.annotations.Nullable;

public class MushroomMutationCategory implements IRecipeCategory<MushroomMutation> {

    public static final IRecipeType<MushroomMutation> TYPE = IRecipeType.create(
            ResourceLocation.fromNamespaceAndPath(SporeNexus.MOD_ID, "mushroom_mutation"), MushroomMutation.class);

    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;

    private final IDrawable background;
    private final IDrawable icon;

    public MushroomMutationCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(WIDTH, HEIGHT);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.MYCELIUM));
    }

    @Override public IRecipeType<MushroomMutation> getRecipeType() {
        return TYPE;
    }

    @Override public Component getTitle() {
        return Component.translatable("jei.sporenexus.mushroom_mutation");
    }

    @Override public int getWidth() {
        return WIDTH;
    }

    @Override public int getHeight() {
        return HEIGHT;
    }

    @Override public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MushroomMutation recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 12).add(recipe.parentA());
        builder.addSlot(RecipeIngredientRole.INPUT, 40, 12).add(recipe.parentB());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 12).add(recipe.result())

                .addRichTooltipCallback((slotView, tooltip) -> {
                    tooltip.add(Component.translatable("jei.sporenexus.mutation_chance", Math.round(recipe.chance() * 100)).withColor(TextColor.YELLOW));
                    tooltip.add(Component.translatable("jei.sporenexus.mutation_conditions").withColor(TextColor.GRAY));});
    }

    @Override
    public void draw(MushroomMutation recipe, IRecipeSlotsView slotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics, 0, 0);
    }
}
