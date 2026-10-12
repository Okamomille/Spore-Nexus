package net.okamiz.common.blocks.renderer;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.level.Level;

public class MycelianCoreBlockEntityRenderState extends BlockEntityRenderState {
    public Level level;
    public float rotation;
    public float orbitAngle;


    public final ItemStackRenderState mushroom = new ItemStackRenderState();
    public final ItemStackRenderState substrate = new ItemStackRenderState();
    public final ItemStackRenderState[] nutrients = {
            new ItemStackRenderState(), new ItemStackRenderState(), new ItemStackRenderState()
    };
}
