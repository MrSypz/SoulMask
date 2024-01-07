package sypztep.mamy.soulmask.common.init;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import sypztep.mamy.soulmask.common.SoulMaskMod;
import sypztep.mamy.soulmask.common.component.VizardComponent;

public class ModEntityComponents implements EntityComponentInitializer {
    public static final ComponentKey<VizardComponent> VIZARD = ComponentRegistry.getOrCreate(SoulMaskMod.id("vizard"), VizardComponent.class);
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {

    }
}
