package net.okamiz.common.records;

import net.minecraft.resources.ResourceLocation;

public record MutationDefinition(ResourceLocation parentA, ResourceLocation parentB, double chance) {}
