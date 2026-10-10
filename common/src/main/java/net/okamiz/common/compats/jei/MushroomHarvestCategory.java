package net.okamiz.common.compats.jei;

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
import net.okamiz.SporeNexus;
import net.okamiz.common.Registries.BlocksRegistry;
import net.okamiz.common.blocks.custom.ResourceMushroomBlock;
import org.jetbrains.annotations.Nullable;

public class MushroomHarvestCategory implements IRecipeCategory<MushroomHarvest> {

    public static final IRecipeType<MushroomHarvest> TYPE = IRecipeType.create(
            Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, "textures/gui/mushroom/harvest_gui.png"), MushroomHarvest.class);

    private static final int WIDTH = 120;
    private static final int HEIGHT = 56;

    private final IDrawable background;
    private final IDrawable icon;

    public MushroomHarvestCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(WIDTH, HEIGHT);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                new ItemStack(BlocksRegistry.COAL_RESOURCE_MUSHROOM.get()));
    }

    @Override public IRecipeType<MushroomHarvest> getRecipeType() { return TYPE; }
    @Override public Component getTitle() { return Component.translatable("jei.sporenexus.mushroom_harvest"); }
    @Override public int getWidth() { return WIDTH; }
    @Override public int getHeight() { return HEIGHT; }
    @Override public @Nullable IDrawable getIcon() { return icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MushroomHarvest recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 20).add(new ItemStack(recipe.mushroom()));

        int i = 0;
        for (ResourceMushroomBlock.HarvestDrop drop : recipe.drops()) {
            int x = 50 + (i % 4) * 18;
            int y = 12 + (i / 4) * 18;
            builder.addSlot(RecipeIngredientRole.OUTPUT, x, y)
                    .add(new ItemStack(drop.item().get(), drop.max()))
                    .addRichTooltipCallback((slotView, tooltip) -> {
                        tooltip.add(Component.translatable("jei.sporenexus.drop_chance", drop.chancePercent()));
                        if (drop.min() != drop.max()) {
                            tooltip.add(Component.translatable("jei.sporenexus.drop_amount", drop.min(), drop.max()));
                        }
                    });
            i++;
        }
    }

    @Override
    public void draw(MushroomHarvest recipe, IRecipeSlotsView slotsView, GuiGraphicsExtractor guiGraphics,
                     double mouseX, double mouseY) {
        this.background.draw(guiGraphics, 0, 0);
    }
}
