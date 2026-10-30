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
import net.okamiz.common.blocks.entity.custom.MycelianCoreBlockEntity;

public class MycelianCoreBlockEntityRenderer implements BlockEntityRenderer<MycelianCoreBlockEntity> {

    private static final double CENTER_Y = 1.15;
    private static final double SUBSTRATE_DROP = 0.6;
    private static final double NUTRIENT_RADIUS = 0.275;

    public MycelianCoreBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(MycelianCoreBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        double time = (Util.getMillis() % 100_000L) / 50.0;
        float rotation = (float) ((time * 2.0) % 360.0);
        float orbitAngle = (float) ((time * 1.0) % 360.0);

        renderItem(blockEntity, 0, poseStack, bufferSource, packedLight, 0.5, CENTER_Y, 0.5, 0.45f, rotation);
        renderItem(blockEntity, 1, poseStack, bufferSource, packedLight, 0.5, CENTER_Y - SUBSTRATE_DROP, 0.5, 1.45f, 0f);

        for (int i = 0; i < 3; i++) {
            double angle = Math.toRadians(orbitAngle + i * 120);
            double x = 0.5 + Math.cos(angle) * NUTRIENT_RADIUS;
            double z = 0.5 + Math.sin(angle) * NUTRIENT_RADIUS;
            renderItem(blockEntity, 2 + i, poseStack, bufferSource, packedLight, x, CENTER_Y, z, 0.2f, -rotation);
        }
    }

    private void renderItem(MycelianCoreBlockEntity blockEntity, int slot, PoseStack poseStack, MultiBufferSource bufferSource,
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
