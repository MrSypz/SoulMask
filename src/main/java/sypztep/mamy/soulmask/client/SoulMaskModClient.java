package sypztep.mamy.soulmask.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;
import sypztep.mamy.soulmask.client.event.MaskHandleTick;
import sypztep.mamy.soulmask.client.event.MaskRenderEvent;
import sypztep.mamy.soulmask.client.packetS2C.AddMaskChargeParticlePacket;
import sypztep.mamy.soulmask.client.packetS2C.AddMaskParticlePacket;
import sypztep.mamy.soulmask.client.particle.BloodwaveParticle;
import sypztep.mamy.soulmask.client.particle.ShockwaveParticle;
import sypztep.mamy.soulmask.common.SoulMaskMod;
import sypztep.mamy.soulmask.common.init.ModParticles;

public class SoulMaskModClient implements ClientModInitializer {
    public static final KeyBinding EQUIPMASK_KEYBINDING = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + SoulMaskMod.MODID + ".equipmask", GLFW.GLFW_KEY_V, "key.categories." + SoulMaskMod.MODID));
    public static final KeyBinding DEBUG_KEYBINDING = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + SoulMaskMod.MODID + ".debug", GLFW.GLFW_KEY_Z, "key.categories." + SoulMaskMod.MODID));

    @Override
    public void onInitializeClient() {
        MaskHandleTick.init();

        HudRenderCallback.EVENT.register(new MaskRenderEvent());

        ClientPlayNetworking.registerGlobalReceiver(AddMaskParticlePacket.ID, new AddMaskParticlePacket.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(AddMaskChargeParticlePacket.ID, new AddMaskChargeParticlePacket.Receiver());

        ParticleFactoryRegistry particleRegistry = ParticleFactoryRegistry.getInstance();
        particleRegistry.register(ModParticles.SHOCKWAVE, ShockwaveParticle.Factory::new);
        particleRegistry.register(ModParticles.BLOODWAVE, BloodwaveParticle.Factory::new);
    }
}
