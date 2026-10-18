package net.okamiz.common.records;

import net.minecraft.resources.Identifier;

public record MutationDefinition(Identifier parentA, Identifier parentB, double chance) {}
