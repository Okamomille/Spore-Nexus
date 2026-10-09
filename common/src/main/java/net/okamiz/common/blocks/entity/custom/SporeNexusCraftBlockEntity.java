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
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.okamiz.common.Registries.BlockEntitiesRegistry;
import net.okamiz.common.Registries.RecipesRegistry;
import net.okamiz.common.menus.custom.SporeNexusCraftMenu;
import net.okamiz.common.recipe.spore_nexus_craft.SporeNexusCraftRecipe;
import net.okamiz.common.recipe.spore_nexus_craft.SporeNexusCraftRecipeInput;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SporeNexusCraftBlockEntity extends BlockEntity implements MenuProvider {
    public final SimpleContainer inventory = new SimpleContainer(9){
        @Override
        public void setChanged(){
            super.setChanged();
            SporeNexusCraftBlockEntity.this.setChanged();
        }
    };

    private final ContainerData data;
    private int progress = 0;
    private int maxProgress = 150;

    private static final int MUSHROOM_SLOT = 0;


    public SporeNexusCraftBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(BlockEntitiesRegistry.SPORE_NEXUS_CRAFT_BE.get(), worldPosition, blockState);
        this.data = new ContainerData(){
            @Override
            public int get(int dataId){
                return switch(dataId){
                    case 0 -> SporeNexusCraftBlockEntity.this.progress;
                    case 1 -> SporeNexusCraftBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int dataId, int value) {
                switch (dataId) {
                    case 0 -> SporeNexusCraftBlockEntity.this.progress = value;
                    case 1 -> SporeNexusCraftBlockEntity.this.maxProgress = value;
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
        return Component.translatable("block.sporenexus.spore_nexus");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new SporeNexusCraftMenu(containerId, inventory, this, this.inventory, this.data);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("spore_nexus_craft:progress", progress);
        output.putInt("spore_nexus_craft:max_progress", maxProgress);

        NonNullList<ItemStack> list = NonNullList.withSize(inventory.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, inventory.getItem(i));
        }
        ContainerHelper.saveAllItems(output, list);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        progress = input.getIntOr("spore_nexus_craft:progress", 0);
        maxProgress = input.getIntOr("spore_nexus_craft:max_progress", 150);

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


    private SporeNexusCraftRecipeInput createRecipeInput() {
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            stacks.add(inventory.getItem(i));
        }
        return new SporeNexusCraftRecipeInput(stacks);
    }

    private boolean hasRecipe() {
        return inventory.getItem(MUSHROOM_SLOT).getCount() == 1 && getCurrentRecipe().isPresent();
    }

    private void craftItem() {
        Optional<RecipeHolder<SporeNexusCraftRecipe>> holder = getCurrentRecipe();
        if (holder.isEmpty()) {
            return;
        }
        SporeNexusCraftRecipe recipe = holder.get().value();
        SporeNexusCraftRecipeInput input = createRecipeInput();

        Optional<int[]> resourceSlots = recipe.findResourceSlots(input);
        Optional<int[]> secondarySlots = recipe.findSecondarySlots(input);
        if (resourceSlots.isEmpty() || secondarySlots.isEmpty()) {
            return;
        }

        ItemStack result = recipe.assemble(input);

        for (int slot : resourceSlots.get()) {
            inventory.getItem(slot).shrink(1);
        }
        for (int slot : secondarySlots.get()) {
            inventory.getItem(slot).shrink(1);
        }

        inventory.setItem(MUSHROOM_SLOT, result);
        setChanged();
    }

    private Optional<RecipeHolder<SporeNexusCraftRecipe>> getCurrentRecipe() {
        assert level != null;
        return ((ServerLevel) level).recipeAccess()
                .getRecipeFor(RecipesRegistry.SPORE_NEXUS_CRAFT_RECIPE_TYPE.get(), createRecipeInput(), level);
    }


    private boolean hasCraftingFinished(){
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress(){
        progress++;
    }

    private void resetProgress(){
        progress = 0;
        maxProgress = 150;
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
