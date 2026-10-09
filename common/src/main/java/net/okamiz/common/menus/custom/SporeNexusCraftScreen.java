package net.okamiz.common.menus.custom;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.okamiz.SporeNexus;

public class SporeNexusCraftScreen extends AbstractContainerScreen<SporeNexusCraftMenu> {

    private static final Identifier GUI_TEXTURE =
            Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, "textures/gui/spore_nexus/spore_nexus_gui.png");
    private static final Identifier ARROW_TEXTURE =
            Identifier.fromNamespaceAndPath(SporeNexus.MOD_ID, "textures/gui/spore_nexus/spore_nexus_progress_bar.png");


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
    protected boolean hasClickedOutside(double mouseX, double mouseY, int left, int top) {
        return mouseX < left || mouseY < top || mouseX >= left + GUI_WIDTH || mouseY >= top + GUI_HEIGHT;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, leftPos, topPos, 0, 0, GUI_WIDTH, GUI_HEIGHT, 256, 256);
        renderProgressArrow(graphics);
    }

    private void renderProgressArrow(GuiGraphicsExtractor graphics) {
        if (menu.isCrafting()) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, ARROW_TEXTURE,
                    leftPos + 103, topPos + 88, 0, 0, menu.getScaledArrowProgress(), 3, 26, 3);
        }
    }


}
