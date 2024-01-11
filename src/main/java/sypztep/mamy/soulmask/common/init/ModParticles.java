package sypztep.mamy.soulmask.common.init;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModParticles {
    public static DefaultParticleType SHOCKWAVE;
    public static DefaultParticleType BLOODWAVE;

    public static void init(){
        SHOCKWAVE = Registry.register(Registries.PARTICLE_TYPE, "soulmask:shockwave", FabricParticleTypes.simple(true));
        BLOODWAVE = Registry.register(Registries.PARTICLE_TYPE, "soulmask:bloodwave", FabricParticleTypes.simple(true));
    }
}
