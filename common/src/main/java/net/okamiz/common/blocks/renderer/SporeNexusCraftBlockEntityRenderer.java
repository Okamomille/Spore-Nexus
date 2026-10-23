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
import net.minecraft.util.Util;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import net.okamiz.common.blocks.entity.custom.SporeNexusCraftBlockEntity;
import org.joml.Matrix4fc;
import org.jspecify.annotations.Nullable;

public class SporeNexusCraftBlockEntityRenderer implements BlockEntityRenderer<SporeNexusCraftBlockEntity, SporeNexusCraftBlockEntityRenderState> {
    private final ItemModelResolver itemModelResolver;

    private static final double CENTER_Y = 1.2;
    private static final double CENTER_Y_INGREDIENTS = 1.25;
    private static final double ORBIT_RADIUS = 0.45;

    public SporeNexusCraftBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public SporeNexusCraftBlockEntityRenderState createRenderState() {
        return new SporeNexusCraftBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(SporeNexusCraftBlockEntity blockEntity, SporeNexusCraftBlockEntityRenderState state, float partialTicks, Vec3 cameraPosition,
                                   ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        double time = (Util.getMillis() % 100_000L) / 50.0;
        state.level = blockEntity.getLevel();

        state.rotation = (float) ((time * 2.0) % 360.0);
        state.orbitAngle = (float) ((time * 1.0) % 360.0);

        updateItem(state.mushroom, blockEntity, 0);
        for (int i = 0; i < 4; i++) {
            updateItem(state.resources[i], blockEntity, 1 + i);
        }
        for (int i = 0; i < 4; i++) {
            updateItem(state.secondaryResources[i], blockEntity, 5 + i);
        }
    }

    private void updateItem(ItemStackRenderState target, SporeNexusCraftBlockEntity be, int slot) {
        itemModelResolver.updateForTopItem(target, be.inventory.getItem(slot), ItemDisplayContext.FIXED, be.getLevel(), null, 0);
    }

    @Override
    public void submit(SporeNexusCraftBlockEntityRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        submitItem(state.mushroom, state, poseStack, collector, 0.5, CENTER_Y, 0.5, 0.4f, state.rotation);

        for (int i = 0; i < 4; i++) {
            double angle = Math.toRadians(state.orbitAngle + i * 90);
            double x = 0.5 + Math.cos(angle) * ORBIT_RADIUS;
            double z = 0.5 + Math.sin(angle) * ORBIT_RADIUS;
            submitItem(state.resources[i], state, poseStack, collector, x, CENTER_Y_INGREDIENTS, z, 0.15f, state.rotation);
        }
        for (int i = 0; i < 4; i++) {
            double angle = Math.toRadians(state.orbitAngle+ 45 + i * 90);
            double x = 0.5 + Math.cos(angle) * ORBIT_RADIUS;
            double z = 0.5 + Math.sin(angle) * ORBIT_RADIUS;
            submitItem(state.secondaryResources[i], state, poseStack, collector, x, CENTER_Y_INGREDIENTS, z, 0.15f, -state.rotation);
        }
    }

    private void submitItem(ItemStackRenderState item, SporeNexusCraftBlockEntityRenderState state, PoseStack poseStack,
                            SubmitNodeCollector collector, double x, double y, double z, float scale, float yRot) {
        poseStack.pushPose();
        poseStack.translate(x, y, z);
        poseStack.scale(scale, scale, scale);
        poseStack.rotateDegrees(Axis.YP, yRot);
        item.submit(poseStack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }
}
