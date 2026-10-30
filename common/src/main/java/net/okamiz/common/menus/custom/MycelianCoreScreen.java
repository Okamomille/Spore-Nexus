package net.okamiz.common.menus.custom;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.okamiz.SporeNexus;

public class MycelianCoreScreen extends AbstractContainerScreen<MycelianCoreMenu> {

    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(SporeNexus.MOD_ID, "textures/gui/mycelian_core/mycelian_core_gui.png");
    private static final ResourceLocation ARROW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(SporeNexus.MOD_ID, "textures/gui/mycelian_core/mycelian_core_arrow_full.png");

    public MycelianCoreScreen(MycelianCoreMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    // 1.21.1: the background is drawn in renderBg (extractBackground only exists in 26.x).
    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        graphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(graphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(ARROW_TEXTURE, x + 91, y + 36, 0, 0,
                    menu.getScaledArrowProgress(), 16, 24, 16);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }


}
