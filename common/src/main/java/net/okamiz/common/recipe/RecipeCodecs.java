package net.okamiz.common.recipe;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Function;

/**
 * Shared codecs for Spore Nexus recipes.
 *
 * <p>1.21.1 vanilla ingredients must be written as objects ({@code {"item": "minecraft:iron_ingot"}} or
 * {@code {"tag": "c:ingots/iron"}}). Spore Nexus recipes (and the modpack config) use the short string
 * form introduced in later versions ({@code "minecraft:iron_ingot"} or {@code "#c:ingots/iron"}).
 * {@link #INGREDIENT} accepts both, so the same JSON works on every Minecraft version of the mod.
 */
public final class RecipeCodecs {

    private RecipeCodecs() {}

    /** Decodes "namespace:item" or "#namespace:tag". Decode only: encoding goes through the vanilla codec. */
    private static final Codec<Ingredient> SHORT_INGREDIENT =
            Codec.either(TagKey.hashedCodec(Registries.ITEM), BuiltInRegistries.ITEM.byNameCodec())
                    .flatComapMap(
                            either -> either.map(RecipeCodecs::ofTag, RecipeCodecs::ofItem),
                            ingredient -> DataResult.error(() -> "Short ingredient form is decode only"));

    public static final Codec<Ingredient> INGREDIENT = Codec.either(SHORT_INGREDIENT, Ingredient.CODEC_NONEMPTY)
            .xmap(either -> either.map(Function.identity(), Function.identity()), Either::right);

    private static Ingredient ofTag(TagKey<Item> tag) {
        return Ingredient.of(tag);
    }

    private static Ingredient ofItem(Item item) {
        return Ingredient.of(item);
    }
}
