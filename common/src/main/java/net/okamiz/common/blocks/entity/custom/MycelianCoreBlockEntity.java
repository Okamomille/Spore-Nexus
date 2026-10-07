package net.okamiz.common.blocks.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.okamiz.common.Registries.BlockEntitiesRegistry;
import net.okamiz.common.Registries.ItemsRegistry;
import net.okamiz.common.Registries.RecipesRegistry;
import net.okamiz.common.menus.custom.MycelianCoreMenu;
import net.okamiz.common.recipe.mycelian_core.MycelianCoreRecipe;
import net.okamiz.common.recipe.mycelian_core.MycelianCoreRecipeInput;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class MycelianCoreBlockEntity extends BlockEntity implements MenuProvider {
    public final SimpleContainer inventory = new SimpleContainer(6){
        @Override
        public void setChanged(){
            super.setChanged();
            MycelianCoreBlockEntity.this.setChanged();
        }
    };

    private final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;

    private static final int MUSHROOM_INPUT_SLOT = 0;
    private static final int SUBSTRACT_INPUT_SLOT = 1;
    private static final int NUTRIENT_INPUT_SLOT_1 = 2;
    private static final int NUTRIENT_INPUT_SLOT_2 = 3;
    private static final int NUTRIENT_INPUT_SLOT_3 = 4;
    private static final int OUTPUT_SLOT = 5;


    public MycelianCoreBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(BlockEntitiesRegistry.MYCELIAN_CORE_BE.get(), worldPosition, blockState);
        this.data = new ContainerData(){
            @Override
            public int get(int dataId){
                return switch(dataId){
                    case 0 -> MycelianCoreBlockEntity.this.progress;
                    case 1 -> MycelianCoreBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int dataId, int value){
                switch (dataId){
                    case 0: MycelianCoreBlockEntity.this.progress = value;
                    case 1: MycelianCoreBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount(){
                return 2;
            }
        };
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("block.sporenexus.mycelian_core");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new MycelianCoreMenu(containerId, inventory, this, this.inventory, this.data);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("mycelian_core:progress", progress);
        output.putInt("mycelian_core:max_progress", maxProgress);

        NonNullList<ItemStack> list = NonNullList.withSize(inventory.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, inventory.getItem(i));
        }
        ContainerHelper.saveAllItems(output, list);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        progress = input.getIntOr("mycelian_core:progress", 0);
        maxProgress = input.getIntOr("mycelian_core:max_progress", 72);

        NonNullList<ItemStack> list = NonNullList.withSize(inventory.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, list);
        for (int i = 0; i < list.size(); i++) {
            inventory.setItem(i, list.get(i));
        }
    }


    public void drops() {
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (hasRecipe()) {
            increaseCraftingProgress();
            setChanged(level, pos, state);
            if (hasCraftingFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void craftItem() {
        Optional<RecipeHolder<MycelianCoreRecipe>> holder = getCurrentRecipe();
        if (holder.isEmpty()) {
            return;
        }
        MycelianCoreRecipe recipe = holder.get().value();
        MycelianCoreRecipeInput input = createRecipeInput();

        Optional<int[]> nutrientSlots = recipe.findNutrientSlots(input);
        if (nutrientSlots.isEmpty()) {
            return;
        }
        ItemStack result = recipe.assemble(input);

        inventory.getItem(MUSHROOM_INPUT_SLOT).shrink(1);
        inventory.getItem(SUBSTRACT_INPUT_SLOT).shrink(1);
        for (int slot : nutrientSlots.get()) {
            inventory.getItem(NUTRIENT_INPUT_SLOT_1 + slot).shrink(1);
        }

        ItemStack outputSlot = inventory.getItem(OUTPUT_SLOT);
        if (outputSlot.isEmpty()) {
            inventory.setItem(OUTPUT_SLOT, result);
        } else {
            outputSlot.grow(result.getCount());
        }
        setChanged();
    }

    private MycelianCoreRecipeInput createRecipeInput() {
        return new MycelianCoreRecipeInput(
                inventory.getItem(MUSHROOM_INPUT_SLOT),
                inventory.getItem(SUBSTRACT_INPUT_SLOT),
                inventory.getItem(NUTRIENT_INPUT_SLOT_1),
                inventory.getItem(NUTRIENT_INPUT_SLOT_2),
                inventory.getItem(NUTRIENT_INPUT_SLOT_3));
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<MycelianCoreRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            return false;
        }
        ItemStack output = recipe.get().value().assemble(createRecipeInput());
        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);
    }

    private Optional<RecipeHolder<MycelianCoreRecipe>> getCurrentRecipe() {
        return ((ServerLevel) level).recipeAccess()
                .getRecipeFor(RecipesRegistry.MYCELIAN_CORE_RECIPE_TYPE.get(), createRecipeInput(), level);
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return inventory.getItem(OUTPUT_SLOT).isEmpty() || inventory.getItem(OUTPUT_SLOT).is(output.getItem());
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = inventory.getItem(OUTPUT_SLOT).isEmpty() ? 64 : inventory.getItem(OUTPUT_SLOT).getMaxStackSize();
        int currentCount = inventory.getItem(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount + count;
    }


    private boolean isOutputSlotEmptyOrReceivable(ItemStack result) {
        ItemStack output = inventory.getItem(OUTPUT_SLOT);
        if (output.isEmpty()) {
            return true;
        }
        return ItemStack.isSameItemSameComponents(output, result)
                && output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    private boolean hasCraftingFinished(){
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress(){
        progress++;
    }

    private void resetProgress(){
        progress = 0;
        maxProgress = 72;
    }



    /* BLOCK ENTITY SYNC */

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return super.getUpdatePacket();
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return super.getUpdateTag(registries);
    }

}
