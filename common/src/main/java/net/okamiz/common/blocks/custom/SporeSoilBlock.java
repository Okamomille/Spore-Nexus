package net.okamiz.common.blocks.custom;

import net.minecraft.world.level.block.Block;

public class SporeSoilBlock extends Block {
    private final float speedMultiplier;

    public SporeSoilBlock(Properties properties, float speedMultiplier) {
        super(properties);
        this.speedMultiplier = speedMultiplier;
    }


    public float getSpeedMultiplier(){
        return speedMultiplier;
    }
}
