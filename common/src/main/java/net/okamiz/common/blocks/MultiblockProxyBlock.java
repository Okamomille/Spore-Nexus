package net.okamiz.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public interface MultiblockProxyBlock {

    // CREDITS GOES TO https://github.com/st0x0ef/ for Multiblock Proxy code.

    BlockPos getControllerPos(BlockPos proxyPos, BlockState proxyState);
}
