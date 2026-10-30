package net.okamiz.common.modded_registries;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class ToolTiersRegistry {


    public static final Tier FUNGALSTEEL = new Tier() {
        @Override
        public int getUses() {
            return 700;
        }

        @Override
        public float getSpeed() {
            return 6.75f;
        }

        @Override
        public float getAttackDamageBonus() {
            return 2.5f;
        }

        @Override
        public TagKey<Block> getIncorrectBlocksForDrops() {
            return TagsRegistry.INCORRECT_FUNGALSTEEL_TOOL;
        }

        @Override
        public int getEnchantmentValue() {
            return 20;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(TagsRegistry.FUNGALSTEEL_REPAIRABLE);
        }
    };
}
