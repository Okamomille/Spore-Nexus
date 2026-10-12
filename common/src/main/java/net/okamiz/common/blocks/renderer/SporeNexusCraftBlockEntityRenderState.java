package net.okamiz.common.blocks.renderer;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.level.Level;

public class SporeNexusCraftBlockEntityRenderState extends BlockEntityRenderState {
    public Level level;
    public float rotation;
    public float orbitAngle;


    public final ItemStackRenderState mushroom = new ItemStackRenderState();
    public final ItemStackRenderState[] resources = {
            new ItemStackRenderState(), new ItemStackRenderState(), new ItemStackRenderState(), new ItemStackRenderState()
    };
    public final ItemStackRenderState[] secondaryResources = {
            new ItemStackRenderState(), new ItemStackRenderState(), new ItemStackRenderState(), new ItemStackRenderState()
    };
}
