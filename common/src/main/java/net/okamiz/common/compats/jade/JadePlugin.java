package net.okamiz.common.compats.jade;

import net.okamiz.common.blocks.custom.GrowthAcceleratorBlock;
import net.okamiz.common.blocks.custom.ResourceMushroomBlock;
import net.okamiz.common.compats.jade.providers.GrowthAcceleratorProvider;
import net.okamiz.common.compats.jade.providers.MushroomGrowthProvider;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(MushroomGrowthProvider.INSTANCE, ResourceMushroomBlock.class);
        registration.registerBlockComponent(GrowthAcceleratorProvider.INSTANCE, GrowthAcceleratorBlock.class);
    }
}
