package net.okamiz.common.compats.jei;

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
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ItemStack;
import net.okamiz.SporeNexus;
import net.okamiz.common.modded_registries.BlocksRegistry;
import net.okamiz.common.blocks.custom.ResourceMushroomBlock;
import org.jetbrains.annotations.Nullable;

public class MushroomHarvestCategory implements IRecipeCategory<MushroomHarvest> {

    public static final RecipeType<MushroomHarvest> TYPE = RecipeType.create(SporeNexus.MOD_ID, "mushroom_harvest", MushroomHarvest.class);

    private static final int WIDTH = 120;
    private static final int HEIGHT = 56;

    private final IDrawable background;
    private final IDrawable icon;

    public MushroomHarvestCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(WIDTH, HEIGHT);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                new ItemStack(BlocksRegistry.RESOURCE_MUSHROOMS.get("redstone").get()));
    }

    @Override public RecipeType<MushroomHarvest> getRecipeType() { return TYPE; }
    @Override public Component getTitle() { return Component.translatable("jei.sporenexus.mushroom_harvest"); }
    @Override public int getWidth() { return WIDTH; }
    @Override public int getHeight() { return HEIGHT; }
    @Override public @Nullable IDrawable getIcon() { return icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MushroomHarvest recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 20).addItemStack(new ItemStack(recipe.mushroom()));

        int i = 0;
        for (ResourceMushroomBlock.HarvestDrop drop : recipe.drops()) {
            int x = 50 + (i % 4) * 18;
            int y = 12 + (i / 4) * 18;
            builder.addSlot(RecipeIngredientRole.OUTPUT, x, y)
                    .addItemStack(new ItemStack(drop.item().get(), drop.max()))
                    .addRichTooltipCallback((slotView, tooltip) -> {
                        if(drop.chancePercent() < 100){
                            tooltip.add(Component.translatable("jei.sporenexus.drop_chance", drop.chancePercent()).withStyle(ChatFormatting.YELLOW));
                        }
                        if (drop.min() != drop.max()) {
                            tooltip.add(Component.translatable("jei.sporenexus.drop_amount", drop.min(), drop.max()).withStyle(ChatFormatting.GOLD));
                        }
                    });
            i++;
        }
    }

    @Override
    public void draw(MushroomHarvest recipe, IRecipeSlotsView slotsView, GuiGraphics guiGraphics,
                     double mouseX, double mouseY) {
        this.background.draw(guiGraphics, 0, 0);
    }
}
