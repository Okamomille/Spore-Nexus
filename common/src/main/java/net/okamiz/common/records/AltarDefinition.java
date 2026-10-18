package net.okamiz.common.records;

import net.minecraft.resources.Identifier;

import java.util.List;

public record AltarDefinition(Identifier mushroomInput, List<Identifier> resourceInputs, List<Identifier> secondaryInputs) {}
