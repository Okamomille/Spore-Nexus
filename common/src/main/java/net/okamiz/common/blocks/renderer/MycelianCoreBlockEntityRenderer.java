package net.okamiz.common.blocks.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.Util;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import net.okamiz.common.blocks.entity.custom.MycelianCoreBlockEntity;
import org.jetbrains.annotations.Nullable;

public class MycelianCoreBlockEntityRenderer implements BlockEntityRenderer<MycelianCoreBlockEntity, MycelianCoreBlockEntityRenderState> {
    private final ItemModelResolver itemModelResolver;

    private static final double CENTER_Y = 1.15;
    private static final double SUBSTRATE_DROP = 0.6;
    private static final double NUTRIENT_RADIUS = 0.275;

    public MycelianCoreBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public MycelianCoreBlockEntityRenderState createRenderState() {
        return new MycelianCoreBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(MycelianCoreBlockEntity blockEntity, MycelianCoreBlockEntityRenderState state, float partialTicks, Vec3 cameraPosition,
                                   ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        double time = (Util.getMillis() % 100_000L) / 50.0;
        state.level = blockEntity.getLevel();

        state.rotation = (float) ((time * 2.0) % 360.0);
        state.orbitAngle = (float) ((time * 1.0) % 360.0);

        updateItem(state.mushroom, blockEntity, 0);
        updateItem(state.substrate, blockEntity, 1);
        for (int i = 0; i < 3; i++) {
            updateItem(state.nutrients[i], blockEntity, 2 + i);
        }
    }

    private void updateItem(ItemStackRenderState target, MycelianCoreBlockEntity be, int slot) {
        itemModelResolver.updateForTopItem(target, be.inventory.getItem(slot), ItemDisplayContext.FIXED, be.getLevel(), null, 0);
    }

    @Override
    public void submit(MycelianCoreBlockEntityRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        submitItem(state.mushroom, state, poseStack, collector, 0.5, CENTER_Y, 0.5, 0.45f, state.rotation);
        submitItem(state.substrate, state, poseStack, collector, 0.5, CENTER_Y - SUBSTRATE_DROP, 0.5, 1.45f, 0f);

        for (int i = 0; i < 3; i++) {
            double angle = Math.toRadians(state.orbitAngle + i * 120);
            double x = 0.5 + Math.cos(angle) * NUTRIENT_RADIUS;
            double z = 0.5 + Math.sin(angle) * NUTRIENT_RADIUS;
            submitItem(state.nutrients[i], state, poseStack, collector, x, CENTER_Y, z, 0.2f, -state.rotation);
        }
    }

    private void submitItem(ItemStackRenderState item, MycelianCoreBlockEntityRenderState state, PoseStack poseStack,
                            SubmitNodeCollector collector, double x, double y, double z, float scale, float yRot) {
        poseStack.pushPose();
        poseStack.translate(x, y, z);
        poseStack.scale(scale, scale, scale);
        poseStack.rotateDegrees(Axis.YP, yRot);
        item.submit(poseStack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }
}
