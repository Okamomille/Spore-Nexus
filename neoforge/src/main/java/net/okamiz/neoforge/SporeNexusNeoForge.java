package net.okamiz.neoforge;

import net.neoforged.fml.common.Mod;

import net.okamiz.SporeNexus;

@Mod(SporeNexus.MOD_ID)
public final class SporeNexusNeoForge {
    public SporeNexusNeoForge() {
        // Run our common setup.
        SporeNexus.init();
    }
}
