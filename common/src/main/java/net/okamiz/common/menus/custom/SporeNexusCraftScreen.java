package net.okamiz.common.menus.custom;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.okamiz.SporeNexus;

public class SporeNexusCraftScreen extends AbstractContainerScreen<SporeNexusCraftMenu> {

    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(SporeNexus.MOD_ID, "textures/gui/spore_nexus/spore_nexus_gui.png");
    private static final ResourceLocation ARROW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(SporeNexus.MOD_ID, "textures/gui/spore_nexus/spore_nexus_progress_bar.png");


    private static final int GUI_WIDTH = 230;
    private static final int GUI_HEIGHT = 219;

    public SporeNexusCraftScreen(SporeNexusCraftMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width - GUI_WIDTH) / 2;
        this.topPos = (this.height - GUI_HEIGHT) / 2;

        this.inventoryLabelX = 36;
        this.inventoryLabelY = 124;
    }


    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int left, int top, int mouseButton) {
        return mouseX < left || mouseY < top || mouseX >= left + GUI_WIDTH || mouseY >= top + GUI_HEIGHT;
    }

    // 1.21.1: the background is drawn in renderBg (extractBackground only exists in 26.x).
    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.blit(GUI_TEXTURE, leftPos, topPos, 0, 0, GUI_WIDTH, GUI_HEIGHT, 256, 256);
        renderProgressArrow(graphics);
    }

    private void renderProgressArrow(GuiGraphics graphics) {
        if (menu.isCrafting()) {
            graphics.blit(ARROW_TEXTURE,
                    leftPos + 103, topPos + 88, 0, 0, menu.getScaledArrowProgress(), 3, 26, 3);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }


}
