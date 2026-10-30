package net.okamiz.common.blocks.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.okamiz.common.blocks.custom.MushroomHarvesterBlock;
import net.okamiz.common.blocks.custom.ResourceMushroomBlock;
import net.okamiz.common.modded_registries.BlockEntitiesRegistry;
import org.jetbrains.annotations.Nullable;

public class MushroomHarvesterBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer {
    private static final int SCAN_INTERVAL = 100;
    private static final int[] ALL_SLOTS = java.util.stream.IntStream.range(0, 27).toArray();

    private int cooldown = SCAN_INTERVAL;
    public final SimpleContainer inventory = new SimpleContainer(27) {
        @Override
        public void setChanged() {
            super.setChanged();
            MushroomHarvesterBlockEntity.this.setChanged();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public MushroomHarvesterBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntitiesRegistry.MUSHROOM_HARVESTER_BE.get(), pos, state);
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getUsedSlots() {
        int used = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (!inventory.getItem(i).isEmpty()) used++;
        }
        return used;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.sporenexus.mushroom_harvester");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return ChestMenu.threeRows(containerId, playerInventory, inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        NonNullList<ItemStack> list = NonNullList.withSize(inventory.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, inventory.getItem(i));
        }
        ContainerHelper.saveAllItems(tag, list, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        NonNullList<ItemStack> list = NonNullList.withSize(inventory.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, list, registries);
        for (int i = 0; i < list.size(); i++) {
            inventory.setItem(i, list.get(i));
        }
    }

    public void drops() {
        if (level != null && !level.isClientSide()) {
            Containers.dropContents(level, worldPosition, inventory);
            inventory.clearContent();
        }
    }


    private void harvestAround(ServerLevel level, BlockPos center) {
        if (!(getBlockState().getBlock() instanceof MushroomHarvesterBlock harvester)) {
            return;
        }
        int radius = harvester.getRadius();
        for (BlockPos scanned : BlockPos.betweenClosed(center.offset(-radius, -1, -radius), center.offset(radius, 1, radius))) {
            if (!hasFreeSlot()) {
                return;
            }
            BlockPos mushroomPos = scanned.immutable();
            BlockState mushroomState = level.getBlockState(mushroomPos);

            if (mushroomState.getBlock() instanceof ResourceMushroomBlock mushroom && mushroom.isMaxAge(mushroomState)
                    && harvester.isOwnerOf(level, center, mushroomPos)) {

                for (ItemStack drop : mushroom.harvest(level, mushroomPos)) {
                    ItemStack leftover = inventory.addItem(drop);
                    if (!leftover.isEmpty()) {
                        Block.popResource(level, center.above(), leftover);
                    }
                    inventory.setChanged();
                }
            }
        }
    }

    public boolean hasFreeSlot() {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (inventory.getItem(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        if (--cooldown > 0) {
            return;
        }
        cooldown = SCAN_INTERVAL;
        harvestAround(serverLevel, pos);
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return ALL_SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction direction) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return false;
    }

    @Override public int getContainerSize() { return inventory.getContainerSize(); }
    @Override public boolean isEmpty() { return inventory.isEmpty(); }
    @Override public ItemStack getItem(int slot) { return inventory.getItem(slot); }
    @Override public ItemStack removeItem(int slot, int amount) { return inventory.removeItem(slot, amount); }
    @Override public ItemStack removeItemNoUpdate(int slot) { return inventory.removeItemNoUpdate(slot); }
    @Override public void setItem(int slot, ItemStack stack) { inventory.setItem(slot, stack); }
    @Override public void clearContent() { inventory.clearContent(); }
    @Override public boolean stillValid(Player player) { return Container.stillValidBlockEntity(this, player); }
}
