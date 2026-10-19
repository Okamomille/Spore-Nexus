package net.okamiz.common.records;

import net.minecraft.resources.Identifier;

import java.util.List;

public record AltarDefinition(IngredientRef mushroomInput, List<IngredientRef> resourceInputs, List<IngredientRef> secondaryInputs) {}
