package net.okamiz.common.compats.jei;

import net.minecraft.world.level.block.Block;
import net.okamiz.common.blocks.custom.ResourceMushroomBlock;

import java.util.List;

public record MushroomHarvest(Block mushroom, List<ResourceMushroomBlock.HarvestDrop> drops) {}
