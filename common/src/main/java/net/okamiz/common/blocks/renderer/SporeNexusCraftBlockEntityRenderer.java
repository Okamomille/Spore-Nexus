package net.okamiz.common.blocks.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.okamiz.common.blocks.entity.custom.SporeNexusCraftBlockEntity;


public class SporeNexusCraftBlockEntityRenderer implements BlockEntityRenderer<SporeNexusCraftBlockEntity> {

    private static final double CENTER_Y = 1.2;
    private static final double CENTER_Y_INGREDIENTS = 1.25;
    private static final double ORBIT_RADIUS = 0.45;

    public SporeNexusCraftBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SporeNexusCraftBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        double time = (Util.getMillis() % 100_000L) / 50.0;
        float rotation = (float) ((time * 2.0) % 360.0);
        float orbitAngle = (float) ((time * 1.0) % 360.0);

        renderItem(blockEntity, 0, poseStack, bufferSource, packedLight, 0.5, CENTER_Y, 0.5, 0.4f, rotation);

        for (int i = 0; i < 4; i++) {
            double angle = Math.toRadians(orbitAngle + i * 90);
            double x = 0.5 + Math.cos(angle) * ORBIT_RADIUS;
            double z = 0.5 + Math.sin(angle) * ORBIT_RADIUS;
            renderItem(blockEntity, 1 + i, poseStack, bufferSource, packedLight, x, CENTER_Y_INGREDIENTS, z, 0.15f, rotation);
        }
        for (int i = 0; i < 4; i++) {
            double angle = Math.toRadians(orbitAngle + 45 + i * 90);
            double x = 0.5 + Math.cos(angle) * ORBIT_RADIUS;
            double z = 0.5 + Math.sin(angle) * ORBIT_RADIUS;
            renderItem(blockEntity, 5 + i, poseStack, bufferSource, packedLight, x, CENTER_Y_INGREDIENTS, z, 0.15f, -rotation);
        }
    }

    private void renderItem(SporeNexusCraftBlockEntity blockEntity, int slot, PoseStack poseStack, MultiBufferSource bufferSource,
                            int packedLight, double x, double y, double z, float scale, float yRot) {
        ItemStack stack = blockEntity.inventory.getItem(slot);
        if (stack.isEmpty()) {
            return;
        }
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        poseStack.pushPose();
        poseStack.translate(x, y, z);
        poseStack.scale(scale, scale, scale);
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY,
                poseStack, bufferSource, blockEntity.getLevel(), 0);
        poseStack.popPose();
    }
}
